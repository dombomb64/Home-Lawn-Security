package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.Spawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class SeedPacketItem extends Item {
	private final EntityType<?> type;
	public int cooldownLength;

	public SeedPacketItem(EntityType<? extends MobEntity> type, int cooldownLength, Settings settings) {
		super(settings);
		this.type = type;
		this.cooldownLength = cooldownLength;
	}

	protected boolean isPlaceable(BlockPos blockPos, World world) {
		return PlantEntity.isPlaceable(blockPos, world, this);
	}

	public static void playPlaceSound(World world, BlockPos pos) {
		if (world.getFluidState(pos.down()).isIn(FluidTags.WATER))
			world.playSound(null, pos, ModSounds.RANDOM_PLANT_WATER, SoundCategory.NEUTRAL);
		else
			world.playSound(null, pos, ModSounds.RANDOM_PLANT, SoundCategory.NEUTRAL);
	}

	public static void playBuzzerSound(World world, BlockPos pos) {
		world.playSound(null, pos, ModSounds.RANDOM_BUZZER, SoundCategory.NEUTRAL);
	}

	protected static boolean shouldCooldownBeUsed(PlayerEntity user) {
		return !user.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.DAVES_PAN);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		EntityType<?> entityType;
		World world = context.getWorld();

		if (!(world instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}

		ItemStack itemStack = context.getStack();
		BlockPos blockPos = context.getBlockPos();
		Direction direction = context.getSide();
		BlockState blockState = world.getBlockState(blockPos);
		BlockEntity blockEntity = world.getBlockEntity(blockPos);

		entityType = this.getEntityType(itemStack.getNbt());

		if (blockEntity instanceof Spawner spawner) {
			spawner.setEntityType(entityType, world.getRandom());
			world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_ALL);
			world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
			itemStack.decrement(1);
			//if (shouldCooldownBeUsed(context.getPlayer()))
				//context.getPlayer().getItemCooldownManager().set(this, cooldownLength);
			return ActionResult.CONSUME;
		}

		if (!isPlaceable(blockPos, world)) {
			playBuzzerSound(world, blockPos);
			return ActionResult.SUCCESS;
		}

		BlockPos blockPos2 = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);

		if (entityType.spawnFromItemStack((ServerWorld)world, itemStack, context.getPlayer(), blockPos2, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP) != null) {
			itemStack.decrement(1);
			world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
			playPlaceSound(world, blockPos);
			if (shouldCooldownBeUsed(context.getPlayer()))
				context.getPlayer().getItemCooldownManager().set(this, cooldownLength);
		}

		return ActionResult.CONSUME;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		EntityType<?> entityType = this.getEntityType(itemStack.getNbt());
		BlockHitResult blockHitResult = SpawnEggItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);

		if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return TypedActionResult.pass(itemStack);
		}
		if (!(world instanceof ServerWorld)) {
			return TypedActionResult.success(itemStack);
		}

		BlockPos blockPos = blockHitResult.getBlockPos();

		if (!isPlaceable(blockPos, world)) {
			playBuzzerSound(world, blockPos);
			return TypedActionResult.success(itemStack);
		}

		if (!(world.getBlockState(blockPos).getBlock() instanceof FluidBlock)) {
			return TypedActionResult.pass(itemStack);
		}
		if (!world.canPlayerModifyAt(user, blockPos) || !user.canPlaceOn(blockPos, blockHitResult.getSide(), itemStack)) {
			return TypedActionResult.fail(itemStack);
		}

		Entity entity = entityType.spawnFromItemStack((ServerWorld)world, itemStack, user, blockPos, SpawnReason.SPAWN_EGG, false, false);
		if (entity == null) {
			return TypedActionResult.pass(itemStack);
		}

		if (!user.getAbilities().creativeMode) {
			itemStack.decrement(1);
		}
		user.incrementStat(Stats.USED.getOrCreateStat(this));
		world.emitGameEvent(user, GameEvent.ENTITY_PLACE, entity.getPos());
		playPlaceSound(world, blockPos);
		if (shouldCooldownBeUsed(user))
			user.getItemCooldownManager().set(this, cooldownLength);

		return TypedActionResult.consume(itemStack);
	}

	public EntityType<?> getEntityType(@Nullable NbtCompound nbt) {
		NbtCompound nbtCompound;
		if (nbt != null && nbt.contains("EntityTag", NbtElement.COMPOUND_TYPE) && (nbtCompound = nbt.getCompound("EntityTag")).contains("id", NbtElement.STRING_TYPE)) {
			return EntityType.get(nbtCompound.getString("id")).orElse(this.type);
		}
		return this.type;
	}

	@Override
	public FeatureSet getRequiredFeatures() {
		return this.type.getRequiredFeatures();
	}
}
