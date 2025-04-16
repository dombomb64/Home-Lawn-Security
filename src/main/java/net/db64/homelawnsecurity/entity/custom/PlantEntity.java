package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.ai.PvzNavigation;
import net.db64.homelawnsecurity.item.custom.LawnSeedPacketItem;
import net.db64.homelawnsecurity.item.custom.PathSeedPacketItem;
import net.db64.homelawnsecurity.item.custom.SeedPacketItem;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class PlantEntity extends PathAwareEntity implements IPvzEntity, IPathBoundEntity {
	//private abstract static final TrackedData<Boolean> USING_ATTACK =
		//DataTracker.registerData(PlantEntityClassHere.class, TrackedDataHandlerRegistry.BOOLEAN);

	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
	public TagKey<Block> pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;

	public final AnimationState attackAnimationState = new AnimationState();
	public int attackAnimationTimeout = 0;
	public int maxAttackAnimationTimeout = 10;
	public int attackTimeout = 0;

	public boolean onPath;
	public boolean onIntersection;

	/*
		GENERAL
	 */

	protected PlantEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

		//HomeLawnSecurity.LOGGER.info("new plant");
		BlockPos pos = getBlockPos().down();
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(pos, 5, 5, 5);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("block pos {} is:", blockPos.toShortString());
			if (isPath(blockPos)) {
				//HomeLawnSecurity.LOGGER.info("path a");
				break;
			}
			if (isOtherPath(blockPos)) {
				//HomeLawnSecurity.LOGGER.info("path b");
				switchPathTag();
				break;
			}
			//HomeLawnSecurity.LOGGER.info("not a path");
		}



		// Where was this planted?
		boolean isPath = isPath(pos);
		boolean isOtherPath = isOtherPath(pos);

		onPath = false;
		onIntersection = false;

		if (isPath || isOtherPath) { // It's some kind of path
			onPath = true;

			if (isPath && isOtherPath) { // It's both paths in one block! Intersection plants should target both lanes
				onIntersection = true;
			}
		}

		return result;
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getWorld().isClient()) {
			updateAnimations();
		}
		else {
			if (attackTimeout > 0) {
				--this.attackTimeout;
			}

			if (attackTimeout <= 0) {
				setUsingAttack(false);
			}
		}
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		// ahaha--*snap*

		if (getAttacker() != null && getAttacker().getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			super.takeKnockback(strength * 3, x, z);
		}
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		Entity attacker = source.getAttacker();
		if (attacker instanceof LivingEntity && ((LivingEntity) attacker).getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			world.playSound(this, getBlockPos(), ModSounds.RANDOM_SHOVEL_ATTACK, attacker.getSoundCategory(), 0.5f, 1);
			return super.damage(world, source, 1000000);
		}
		else if (attacker instanceof IPvzEntity) {
			//boolean damaged = super.damage(world, source, amount);

			this.hurtTime = 0;
			this.timeUntilRegen = 0;

			//return damaged;
		}
		//return super.damage(world, source, amount * 10);
		return super.damage(world, source, amount);
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		ActionResult result = super.interactMob(player, hand);

		if (player.getStackInHand(hand).contains(ModDataComponentTypes.SHOVEL)) {
			World world = getWorld();
			if (world.isClient) {
				for (int i = 0; i < 5; i++) {
					world.addParticleClient(new BlockStateParticleEffect(ParticleTypes.BLOCK, getSteppingBlockState()), false, true, getX(), getY(), getZ(), 0, 0, 0);
				}
				world.playSoundClient(getX(), getY(), getZ(), ModSounds.ENTITY_PLANT_SHOVEL, SoundCategory.NEUTRAL, 1, 1, false);
			}
			else {
				discard();
			}

			result = ActionResult.SUCCESS;
		}

		return result;
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		if (pathTag == ModTags.Blocks.ZOMBIE_PATH_2)
			nbt.putInt("path_tag", 2);
		else
			nbt.putInt("path_tag", 1);

		nbt.putBoolean("on_path", onPath);
		nbt.putBoolean("on_intersection", onIntersection);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		// path_tag
		int path = nbt.getInt("path_tag").orElse(1);
		if (path == 2) {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_2;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		} else {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}

		onPath = nbt.getBoolean("on_path").orElse(false);

		onIntersection = nbt.getBoolean("on_intersection").orElse(false);
	}

	/*
		ANIMATIONS
	 */

	protected void updateAnimations() {
		if (this.isUsingAttack()) {
			attackAnimationTimeout = maxAttackAnimationTimeout;
			if (!attackAnimationState.isRunning()) {
				attackAnimationState.start(this.age);
			}
		}
		else if (attackAnimationTimeout > 0) {
			--this.attackAnimationTimeout;
		}

		if (attackAnimationTimeout <= 0) {
			attackAnimationState.stop();
		}
	}

	public abstract TrackedData<Boolean> getUsingAttackTrackedData();

	public void setUsingAttack(boolean attacking) {
		this.dataTracker.set(getUsingAttackTrackedData(), attacking);
	}

	public boolean isUsingAttack() {
		return this.dataTracker.get(getUsingAttackTrackedData());
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(getUsingAttackTrackedData(), false);
	}

	/*
		BLOCKS
	 */

	public TagKey<Block> iGetPathTag() {
		return pathTag;
	}

	public void iSetPathTag(TagKey<Block> value) {
		pathTag = value;
	}

	public TagKey<Block> iGetPathMarkerTag() {
		return pathMarkerTag;
	}

	public void iSetPathMarkerTag(TagKey<Block> value) {
		pathMarkerTag = value;
	}

	@Override
	public World iGetWorld() {
		return getWorld();
	}

	public void iStopNavigation() {
		navigation.stop();
	}

	@Override
	public Entity iGetSelf() {
		return this;
	}

	/**
	 * @return Whether a lawn plant can be placed on top of this block.
	 */
	public static boolean isPlaceableLawn(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN_MARKERS);
		}
		return state.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN);
	}

	/**
	 * @return Whether a path plant can be placed on top of this block.
	 */
	public static boolean isPlaceablePath(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.PLANT_PLACEABLE_PATH_MARKERS);
		}
		return state.isIn(ModTags.Blocks.PLANT_PLACEABLE_PATH);
	}

	/**
	 * @return Whether this kind of seed packet can be placed on top of this block.
	 */
	public static boolean isPlaceable(BlockPos pos, World world, SeedPacketItem item) {
		if (item instanceof LawnSeedPacketItem) {
			//HomeLawnSecurity.LOGGER.info(item.getClass() + " has a lawn plant");
			return isPlaceableLawn(pos, world);
		}
		else if (item instanceof PathSeedPacketItem) {
			//HomeLawnSecurity.LOGGER.info(item.getClass() + " has a path plant");
			return isPlaceablePath(pos, world);
		}
		//HomeLawnSecurity.LOGGER.info(item.getClass() + " has an idiot plant");
		return false;
	}

	/**
	 * @return Whether this block is a lawn mower goal.
	 */
	public boolean isGoal(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_START_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_START);
	}

	/**
	 * @return Whether this block is a lawn mower start.
	 */
	public boolean isStart(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_GOAL_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_GOAL);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PvzNavigation(this, world);
	}

	/*
		STATS
	 */

	/**
	 * A class that has all the generic stats of the plant.
	 * For organization reasons.
	 */
	public static class PlantStats {
		public float attackRange = 3f;
		public float attackRangePath = 16f;
		public int attackTicks = 20;

		public PlantStats() {
		}

		public PlantStats attackRange(float value) {
			attackRange = value;
			return this;
		}

		public PlantStats attackRangePath(float value) {
			attackRangePath = value;
			return this;
		}

		public PlantStats attackTicks(int value) {
			attackTicks = value;
			return this;
		}

		public float getLargerAttackRange() {
			return Math.max(attackRange, attackRangePath);
		}
	}

	/*
		SOUNDS
	 */

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return null;
	}

	@Override
	protected SoundEvent getSplashSound() {
		return null;
	}

	@Override
	protected SoundEvent getHighSpeedSplashSound() {
		return null;
	}

	/*@Override
	public SoundEvent getEatSound(ItemStack stack) {
		return null;
	}

	@Override
	protected SoundEvent getDrinkSound(ItemStack stack) {
		return null;
	}*/

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}
}
