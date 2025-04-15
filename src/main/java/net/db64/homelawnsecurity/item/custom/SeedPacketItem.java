package net.db64.homelawnsecurity.item.custom;

import com.mojang.serialization.MapCodec;
import net.db64.homelawnsecurity.component.CurrencyComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.component.SeedPacketComponent;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Spawner;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class SeedPacketItem extends Item {
	private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAP_CODEC;
	private final EntityType<?> type;

	public static final Predicate<ItemStack> SUN_BAG_PREDICATE = stack -> stack.isIn(ModTags.Items.BAG_OF_SUN);
	public static final Predicate<ItemStack> BRAINPOWER_BAG_PREDICATE = stack -> stack.isIn(ModTags.Items.BAG_OF_BRAINPOWER);

	public SeedPacketItem(EntityType<? extends MobEntity> type, Settings settings) {
		super(settings);
		this.type = type;
	}

	protected boolean isPlaceable(BlockPos blockPos, World world, boolean devMode, ServerPlayerEntity player) {
		return PlantEntity.isPlaceable(blockPos, world, this);
	}

	public void playPlaceSound(World world, BlockPos pos) {
		if (world.getFluidState(pos.down()).isIn(FluidTags.WATER))
			world.playSound(null, pos, ModSounds.RANDOM_PLANT_WATER, SoundCategory.NEUTRAL);
		else
			world.playSound(null, pos, ModSounds.RANDOM_PLANT, SoundCategory.NEUTRAL);
	}

	public static void playBuzzerSound(World world, BlockPos pos) {
		world.playSound(null, pos, ModSounds.RANDOM_BUZZER, SoundCategory.NEUTRAL);
	}

	protected static boolean isWearingDavesPan(@Nullable PlayerEntity user) {
		if (user == null) return false;

		return user.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.DAVES_PAN);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		Direction direction = context.getSide();

		// Reject clients, return to servr
		World world = context.getWorld();
		if (!(world instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}

		// Variables
		ItemStack stack = context.getStack();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);
		BlockEntity blockEntity = world.getBlockEntity(pos);

		EntityType<?> entityType = this.getEntityType(stack);

		// Spawner
		if (blockEntity instanceof Spawner spawner) {
			spawner.setEntityType(entityType, world.getRandom());
			world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
			world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, pos);
			stack.decrement(1);
			//if (shouldCooldownBeUsed(context.getPlayer()))
				//context.getPlayer().getItemCooldownManager().set(this, cooldownLength);
			return ActionResult.CONSUME;
		}

		return useSeedPacket(stack, context.getPlayer(), pos, (ServerWorld) context.getWorld(), direction);
	}

	@Override
	public ActionResult use(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getStackInHand(hand);
		BlockHitResult blockHitResult = SpawnEggItem.raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);

		if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return ActionResult.PASS;
		}
		if (!(world instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}

		BlockPos pos = blockHitResult.getBlockPos();

		if (!(world.getBlockState(pos).getBlock() instanceof FluidBlock)) {
			return ActionResult.PASS;
		}
		if (!world.canPlayerModifyAt(player, pos) || !player.canPlaceOn(pos, blockHitResult.getSide(), stack)) {
			return ActionResult.FAIL;
		}

		return useSeedPacket(stack, player, pos, (ServerWorld) world, blockHitResult.getSide());
	}

	public EntityType<?> getEntityType(ItemStack stack) {
		NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
		return !nbtComponent.isEmpty() ? nbtComponent.get(ENTITY_TYPE_MAP_CODEC).result().orElse(this.type) : this.type;
	}

	@Override
	public FeatureSet getRequiredFeatures() {
		return this.type.getRequiredFeatures();
	}

	public ItemStack getCurrentBag(LivingEntity entity) {
		var predicate = getBagPredicate();
		if (predicate.test(entity.getStackInHand(Hand.OFF_HAND))) {
			return entity.getStackInHand(Hand.OFF_HAND);
		}
		else if (predicate.test(entity.getStackInHand(Hand.MAIN_HAND))) {
			return entity.getStackInHand(Hand.MAIN_HAND);
		}
		else if (entity instanceof ServerPlayerEntity player) {
			PlayerInventory inventory = player.getInventory();
			for (int i = 0; i < inventory.size(); i++) {
				ItemStack itemStack2 = inventory.getStack(i);
				if (predicate.test(itemStack2)) {
					return itemStack2;
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public abstract Predicate<ItemStack> getBagPredicate();

	public ActionResult useSeedPacket(ItemStack stack, PlayerEntity player, BlockPos usePos, ServerWorld world, Direction direction) {
		BlockPos groundPos = usePos.offset(direction).down();
		BlockState state = world.getBlockState(usePos);
		EntityType<?> entityType = this.getEntityType(stack);

		// Invalid spawn position
		if (!isPlaceable(groundPos, world, isWearingDavesPan(player), (ServerPlayerEntity) player)) {
			//HomeLawnSecurity.LOGGER.warn("BlockPos {} is not a valid position for a seed packet of type {}!", pos, getClass().getSimpleName());
			playBuzzerSound(world, usePos);
			return ActionResult.SUCCESS;
		}

		BlockPos spawnPos = state.getCollisionShape(world, usePos).isEmpty() ? usePos : usePos.offset(direction);

		if (player != null) {
			boolean wearingPan = isWearingDavesPan(player);
			if (!wearingPan) {
				// Cost
				SeedPacketComponent seedData = stack.get(ModDataComponentTypes.SEED_PACKET);
				int cost = 0;
				if (seedData != null) {
					cost = seedData.cost();
				}

				ItemStack bag = getCurrentBag(player);
				if (bag != null) {
					CurrencyComponent currency = bag.get(ModDataComponentTypes.CURRENCY);
					if (currency != null) {
						if (currency.amount() < cost) {
							playBuzzerSound(world, usePos);
							return ActionResult.SUCCESS;
						}

						bag.set(ModDataComponentTypes.CURRENCY, new CurrencyComponent(currency.amount() - cost, currency.name()));
					}
				}

				// Cooldown
				if (seedData != null) {
					player.getItemCooldownManager().set(stack, seedData.cooldown());
				}
			}
		}

		if (entityType.spawnFromItemStack(world, stack, player, spawnPos, SpawnReason.SPAWN_ITEM_USE, true, !Objects.equals(usePos, spawnPos) && direction == Direction.UP) != null) {
			if (!player.getAbilities().creativeMode) {
				stack.decrement(1);
			}
			world.emitGameEvent(player, GameEvent.ENTITY_PLACE, usePos);
			playPlaceSound(world, usePos);
		}

		return ActionResult.CONSUME;
	}

	protected static void sendMessage(PlayerEntity player, Text message) {
		((ServerPlayerEntity) player).sendMessageToClient(message, true);
	}

	static {
		ENTITY_TYPE_MAP_CODEC = Registries.ENTITY_TYPE.getCodec().fieldOf("id");
	}
}
