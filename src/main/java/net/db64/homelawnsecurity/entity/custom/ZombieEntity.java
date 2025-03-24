package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieNavigation;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieStayOnPathGoal;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieMoveGoal;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieTargetGoal;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.IEntityDataSaver;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public abstract class ZombieEntity extends PathAwareEntity implements IPvzEntity {
	//private abstract static final TrackedData<Boolean> USING_ATTACK =
		//DataTracker.registerData(ZombieEntityClassHere.class, TrackedDataHandlerRegistry.BOOLEAN);

	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
	public TagKey<Block> pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;

	public boolean offTrack = false;

	public final AnimationState attackAnimationState = new AnimationState();
	public int attackAnimationTimeout = 0;
	public int maxAttackAnimationTimeout = 10;
	public int attackTimeout = 0;
	protected int eatingSoundTimeout = 0;

	protected ZombieEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
		setPathTagNbt(pathTag);
		setOffTrackNbt(offTrack);
		/*World world2 = getWorld();
		BlockState groundState = world2.getBlockState(this.getBlockPos().down());
		BlockState markerState = world2.getBlockState(this.getBlockPos());
		if (markerState.isIn(this.getOtherPathMarkerTag(pathMarkerTag)) // The block is marked as the other path
			|| (!markerState.isIn(ModTags.Blocks.MARKERS) && groundState.isIn(this.getOtherPathTag(pathTag)))) { // The block is not marked, but is the other path
			switchPathTag();
		}*/

		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(getBlockPos().down(), 5, 5, 5);
		for (BlockPos blockPos : iterable) {
			if (isPath(blockPos)) {
				break;
			}
			if (isOtherPath(blockPos)) {
				switchPathTag();
				break;
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.getWorld().isClient()) {
			updateAnimations();
		}
		else {
			if (this.getUsingAttack()) {
				if (this.eatingSoundTimeout <= 0) {
					this.playSound(ModSounds.ENTITY_ZOMBIE_EAT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 1f));
					this.eatingSoundTimeout = 10;
					//HomeLawnSecurity.LOGGER.info("zombie took bite");
				}
				else {
					--this.eatingSoundTimeout;
				}
			}
			else {
				this.eatingSoundTimeout = 0;
			}

			if (attackTimeout > 0) {
				--this.attackTimeout;
			}

			if (attackTimeout <= 0) {
				setUsingAttack(false);
			}

			//HomeLawnSecurity.LOGGER.info("attackTimeout is " + attackTimeout + " and isUsingAttack() is " + isUsingAttack());

			if (getHasLostHead()) {
				damage((ServerWorld) getWorld(), this.getDamageSources().create(ModDamageTypes.ZOMBIE_HEADLESS), 2);
			}
		}
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		// oh no--i hope this really heavy beach ball doesn't break my leg

		if (getAttacker() != null && getAttacker().getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			super.takeKnockback(strength * 3, x, z);
		}
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		Entity attacker = source.getAttacker();
		boolean damaged;
		if (attacker instanceof LivingEntity && ((LivingEntity) attacker).getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			world.playSound(this, getBlockPos(), ModSounds.RANDOM_SHOVEL_ATTACK, SoundCategory.PLAYERS, 1, 1);
			damaged = super.damage(world, source, 1000000);
		}
		else {
			damaged = super.damage(world, source, amount);
		}

		if ((attacker != null && attacker instanceof IPvzEntity)
			|| source.isOf(ModDamageTypes.ZOMBIE_HEADLESS)) {
			//damaged = super.damage(world, source, amount);

			this.hurtTime = 0;
			this.timeUntilRegen = 0;
		}
		//else {
			//damaged = super.damage(world, source, amount * 10);
		//}

		if (!getHasLostHeadwear() && getHealth() < getLoseHeadwearHealth()) {
			setHasLostHeadwear(true);
			this.playSound(ModSounds.ENTITY_ZOMBIE_DETACH_HEADWEAR, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		}
		if (!getHasLostArm() && getHealth() < getLoseArmHealth()) {
			setHasLostArm(true);
			this.playSound(ModSounds.ENTITY_ZOMBIE_DETACH_LIMB, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		}
		if (!getHasLostHead() && getHealth() < getLoseHeadHealth()) {
			setHasLostHead(true);
			this.playSound(ModSounds.ENTITY_ZOMBIE_DETACH_HEAD, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		}

		return damaged;
	}

	@Override
	public boolean collidesWith(Entity other) {
		return (other.isCollidable() || other instanceof PlantEntity) && !this.isConnectedThroughVehicle(other);
	}

	/*
		ANIMATIONS
	 */

	protected void updateAnimations() {
		if (this.getUsingAttack()) {
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

	@Override
	public void onAttacking(Entity target) {
		super.onAttacking(target);
		setUsingAttack(true);
		attackTimeout = 2;

		if (target instanceof LivingEntity && ((LivingEntity) target).getHealth() <= 0) {
			this.playSound(ModSounds.ENTITY_ZOMBIE_GULP, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		}
	}

	/**
	 * @return The health at which this zombie will lose its headwear upon going below, or -1 if it doesn't normally spawn with any.
	 */
	public abstract float getLoseHeadwearHealth();

	/**
	 * @return The health at which this zombie will lose its arm upon going below, or -1 if it never does.
	 * Typically, this is two thirds of the zombie's max health.
	 */
	public abstract float getLoseArmHealth();

	/**
	 * @return The health at which this zombie will lose its head and start to die upon going below.
	 * Typically, this is a third of the zombie's max health.
	 */
	public abstract float getLoseHeadHealth();

	public abstract TrackedData<Boolean> getUsingAttackTrackedData();

	public void setUsingAttack(boolean usingAttack) {
		var usingAttackData = getUsingAttackTrackedData();
		if (usingAttackData != null)
			this.dataTracker.set(usingAttackData, usingAttack);
	}

	public boolean getUsingAttack() {
		var usingAttack = getUsingAttackTrackedData();
		if (usingAttack == null)
			return false;
		return this.dataTracker.get(usingAttack);
	}

	public abstract TrackedData<Boolean> getHasLostHeadwearTrackedData();

	public void setHasLostHeadwear(boolean hasLostHeadwear) {
		var lostHeadwear = getHasLostHeadwearTrackedData();
		if (lostHeadwear != null)
			this.dataTracker.set(lostHeadwear, hasLostHeadwear);
	}

	public boolean getHasLostHeadwear() {
		var lostHeadwear = getHasLostHeadwearTrackedData();
		if (lostHeadwear == null)
			return false;
		return this.dataTracker.get(lostHeadwear);
	}

	public abstract TrackedData<Boolean> getHasLostArmTrackedData();

	public void setHasLostArm(boolean hasLostArm) {
		var lostArm = getHasLostArmTrackedData();
		if (lostArm != null)
			this.dataTracker.set(lostArm, hasLostArm);
	}

	public boolean getHasLostArm() {
		var lostArm = getHasLostArmTrackedData();
		if (lostArm == null)
			return false;
		return this.dataTracker.get(lostArm);
	}

	public abstract TrackedData<Boolean> getHasLostHeadTrackedData();

	public void setHasLostHead(boolean hasLostHead) {
		var lostHead = getHasLostHeadTrackedData();
		if (lostHead != null)
			this.dataTracker.set(lostHead, hasLostHead);
	}

	public boolean getHasLostHead() {
		var lostHead = getHasLostHeadTrackedData();
		if (lostHead == null)
			return false;
		return this.dataTracker.get(lostHead);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);

		var usingAttack = getUsingAttackTrackedData();
		if (usingAttack != null)
			builder.add(usingAttack, false);

		var lostHeadwear = getHasLostHeadwearTrackedData();
		if (lostHeadwear != null)
			builder.add(lostHeadwear, false);

		var lostArm = getHasLostArmTrackedData();
		if (lostArm != null)
			builder.add(lostArm, false);

		var lostHead = getHasLostHeadTrackedData();
		if (lostHead != null)
			builder.add(lostHead, false);
	}

	/*
		BLOCKS
	 */

	/**
	 * @return Whether a zombie can be placed on top of this block.
	 */
	public static boolean isPlaceable(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_PLACEABLE_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_PLACEABLE);
	}

	/**
	 * @return Whether this zombie can use this block as a path.
	 */
	public boolean isPath(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(pathMarkerTag) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(pathTag) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}

	/**
	 * @return Whether this zombie can't use this block as a path but the block is still a path.
	 */
	public boolean isOtherPath(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(getOtherPathMarkerTag(pathMarkerTag)) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(getOtherPathTag(pathTag)) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}

	/**
	 * @return Whether this block is a zombie goal.
	 */
	public boolean isGoal(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_GOAL_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_GOAL);
	}

	/**
	 * @return Whether this block is a zombie start.
	 */
	public boolean isStart(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_START_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_START);
	}

	/**
	 * @return Whether this zombie can walk on this block if they have not been put off-course.
	 */
	public boolean isPathOrGoal(BlockPos pos) {
		return isPath(pos) || isGoal(pos);
	}

	public void switchPathTag() {
		TagKey<Block> tagKey = getPathTagNbt();
		//if (this.getWorld().getBlockState(this.getBlockPos().down()).isIn(tagKey))
			//return; // shut up you are standing on it

		navigation.stop();
		setPathTagNbt(getOtherPathTag(tagKey));
	}

	public TagKey<Block> getOtherPathTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1)
			return ModTags.Blocks.ZOMBIE_PATH_2; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1; // flop
	}

	public TagKey<Block> getOtherPathMarkerTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			return ModTags.Blocks.ZOMBIE_PATH_2_MARKERS; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1_MARKERS; // flop
	}

	private TagKey<Block> getPathTagNbt() {
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		if (nbt.getInt("pathTag") == 2) {
			this.pathTag = ModTags.Blocks.ZOMBIE_PATH_2;
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		}
		else {
			this.pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}
		return this.pathTag;
	}

	private TagKey<Block> getPathMarkerTagNbt() {
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		if (nbt.getInt("pathTag") == 2) {
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		}
		else {
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}
		return this.pathMarkerTag;
	}

	private void setPathTagNbt(TagKey<Block> tagKey) {
		this.pathTag = tagKey;
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_2) {
			nbt.putInt("pathTag", 2);
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		}
		else {
			nbt.putInt("pathTag", 1);
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}
	}

	public boolean getOffTrackNbt() {
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		this.offTrack = nbt.getBoolean("offTrack");
		return this.offTrack;
	}

	public void setOffTrackNbt(boolean bool) {
		this.offTrack = bool;
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		nbt.putBoolean("offTrack", offTrack);
	}

	/*@Override
	public float getPathfindingPenalty(PathNodeType nodeType) {
		if (nodeType == PathNodeType.) {
			return 0f; // Path
		}
		else if (state.isIn(ModTags.Blocks.ZOMBIE_GOAL)) {
			return 0f; // Goal
		}
		return -1f; // Off-road
	}*/

	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		//BlockState state = world.getBlockState(pos.down());
		if (isPath(pos.down())) {
			return 0f; // Path
		}
		else if (isGoal(pos.down())) {
			return 0f; // Goal
		}
		return Float.NEGATIVE_INFINITY; // Off-road
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new ZombieNavigation(this, world);
	}

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new ZombieStayOnPathGoal(this, 1f));
		this.goalSelector.add(2, new ZombieTargetGoal<PlantEntity>(this, PlantEntity.class, true));
		this.goalSelector.add(3, new ZombieMoveGoal(this, 1f));
	}

	/*
		SOUNDS
	 */

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.ENTITY_ZOMBIE_IDLE;
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		if (this.getWorld().getFluidState(this.getBlockPos()).isIn(FluidTags.WATER))
			return ModSounds.ENTITY_ZOMBIE_COLLAPSE_UNDERWATER;
		return ModSounds.ENTITY_ZOMBIE_COLLAPSE;
	}

	@Override
	protected SoundEvent getSwimSound() {
		return ModSounds.ENTITY_ZOMBIE_ENTER_WATER;
	}

	@Override
	protected SoundEvent getSplashSound() {
		return ModSounds.ENTITY_ZOMBIE_ENTER_WATER;
	}

	@Override
	protected SoundEvent getHighSpeedSplashSound() {
		return ModSounds.ENTITY_ZOMBIE_ENTER_WATER;
	}

	/*@Override
	public SoundEvent getEatSound(ItemStack stack) {
		return ModSounds.ENTITY_ZOMBIE_EAT;
	}

	@Override
	protected SoundEvent getDrinkSound(ItemStack stack) {
		return ModSounds.ENTITY_ZOMBIE_EAT;
	}*/

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}
}
