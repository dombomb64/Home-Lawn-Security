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
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class SeedPacketItem extends Item {
	private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAP_CODEC;
	private final EntityType<?> type;

	public static final Predicate<ItemStack> SUN_BAG_PREDICATE = stack -> stack.isIn(ModTags.Items.BAG_OF_SUN);
	public static final Predicate<ItemStack> BRAINPOWER_BAG_PREDICATE = stack -> stack.isIn(ModTags.Items.BAG_OF_BRAINPOWER);

	public SeedPacketItem(EntityType<? extends MobEntity> type, net.minecraft.item.Item.Settings settings) {
		super(settings);
		this.type = type;
	}

	protected boolean isPlaceable(BlockPos blockPos, World world) {
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

	protected static boolean isWearingDavesPan(PlayerEntity user) {
		return user.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.DAVES_PAN);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		// Reject clients, return to servr
		World world = context.getWorld();
		if (!(world instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}

		// Variables
		ItemStack itemStack = context.getStack();
		BlockPos blockPos = context.getBlockPos();
		Direction direction = context.getSide();
		BlockState blockState = world.getBlockState(blockPos);
		BlockEntity blockEntity = world.getBlockEntity(blockPos);

		EntityType<?> entityType = this.getEntityType(itemStack);

		// Spawner
		if (blockEntity instanceof Spawner spawner) {
			spawner.setEntityType(entityType, world.getRandom());
			world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_ALL);
			world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
			itemStack.decrement(1);
			//if (shouldCooldownBeUsed(context.getPlayer()))
				//context.getPlayer().getItemCooldownManager().set(this, cooldownLength);
			return ActionResult.CONSUME;
		}

		// Invalid spawn position
		if (!isPlaceable(blockPos, world)) {
			playBuzzerSound(world, blockPos);
			return ActionResult.SUCCESS;
		}

		BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);

		PlayerEntity player = context.getPlayer();
		if (player != null) {
			boolean wearingPan = isWearingDavesPan(player);
			if (!wearingPan) {
				// Cost
				SeedPacketComponent seedData = itemStack.get(ModDataComponentTypes.SEED_PACKET);
				int cost = 0;
				if (seedData != null) {
					cost = seedData.cost();
				}

				ItemStack bag = getCurrentBag(player);
				if (bag != null) {
					CurrencyComponent currency = bag.get(ModDataComponentTypes.CURRENCY);
					if (currency != null) {
						if (currency.amount() < cost) {
							playBuzzerSound(world, blockPos);
							return ActionResult.SUCCESS;
						}

						bag.set(ModDataComponentTypes.CURRENCY, new CurrencyComponent(currency.amount() - cost, currency.name()));
					}
				}

				// Cooldown
				if (seedData != null) {
					player.getItemCooldownManager().set(itemStack, seedData.cooldown());
				}
			}
		}

		if (entityType.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_ITEM_USE, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
			itemStack.decrement(1);
			world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
			playPlaceSound(world, blockPos);
		}

		return ActionResult.CONSUME;
	}

	@Override
	public ActionResult use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		EntityType<?> entityType = this.getEntityType(itemStack);
		BlockHitResult blockHitResult = SpawnEggItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);

		if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return ActionResult.PASS;
		}
		if (!(world instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}

		BlockPos blockPos = blockHitResult.getBlockPos();

		// Invalid spawn position
		if (!isPlaceable(blockPos, world)) {
			playBuzzerSound(world, blockPos);
			return ActionResult.SUCCESS;
		}

		if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
			return ActionResult.PASS;
		}
		if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
			return ActionResult.FAIL;
		}

		Entity entity = entityType.spawnFromItemStack((ServerWorld)world, itemStack, user, blockPos, SpawnReason.SPAWN_ITEM_USE, false, false);
		if (entity == null) {
			return ActionResult.PASS;
		}

		boolean wearingPan = isWearingDavesPan(user);
		if (!wearingPan) {
			// Cost
			SeedPacketComponent seedData = itemStack.get(ModDataComponentTypes.SEED_PACKET);
			int cost = 0;
			if (seedData != null) {
				cost = seedData.cost();
			}

			ItemStack bag = getCurrentBag(user);
			if (bag != null) {
				CurrencyComponent currency = bag.get(ModDataComponentTypes.CURRENCY);
				if (currency != null) {
					if (currency.amount() < cost) {
						playBuzzerSound(world, blockPos);
						return ActionResult.SUCCESS;
					}

					bag.set(ModDataComponentTypes.CURRENCY, new CurrencyComponent(currency.amount() - cost, currency.name()));
				}
			}

			// Cooldown
			if (seedData != null) {
				user.getItemCooldownManager().set(itemStack, seedData.cooldown());
			}
		}

		if (!user.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		world.emitGameEvent(user, GameEvent.ENTITY_PLACE, entity.getPos());
		playPlaceSound(world, blockPos);

		return ActionResult.CONSUME;
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

	static {
		ENTITY_TYPE_MAP_CODEC = Registries.ENTITY_TYPE.getCodec().fieldOf("id");
	}
}
