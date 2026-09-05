package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieStayOnPathGoal;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieMoveGoal;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieTargetGoal;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.db64.homelawnsecurity.util.MiscUtil;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public abstract class ZombieEntity extends SeedPlacedEntity implements IDegradableEntity {
	//private abstract static final TrackedData<Boolean> USING_ATTACK =
		//DataTracker.registerData(ZombieEntityClassHere.class, TrackedDataHandlerRegistry.BOOLEAN);

	/*@Deprecated
	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
	@Deprecated
	public TagKey<Block> pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;*/

	public final AnimationState attackAnimationState = new AnimationState();
	public int attackAnimationTimeout = 0;
	public int maxAttackAnimationTimeout = 10;
	public int attackTimeout = 0;
	//protected int biteTimeout = 0;

	public boolean movementBlocked = false;

	protected ZombieEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);

		//limbAnimator.setSpeed(limbAnimator.getSpeed() * 10f);

		//setPathTagNbt(pathTag);
		/*World world2 = getEntityWorld();
		BlockState groundState = world2.getBlockState(this.getBlockPos().down());
		BlockState markerState = world2.getBlockState(this.getBlockPos());
		if (markerState.isIn(this.getOtherPathMarkerTag(pathMarkerTag)) // The block is marked as the other path
			|| (!markerState.isIn(ModTags.Blocks.REVEALS_MARKERS) && groundState.isIn(this.getOtherPathTag(pathTag)))) { // The block is not marked, but is the other path
			switchPathTag();
		}*/

		/*if (age == 0) {
			Iterable<BlockPos> iterable = BlockPos.iterateOutwards(getBlockPos().down(), 5, 5, 5);
			for (BlockPos blockPos : iterable) {
				if (isThisPath(blockPos)) {
					break;
				}
				if (isOtherPath(blockPos)) {
					switchPathTag();
					break;
				}
			}
		}*/

		/*if (getEntityWorld() instanceof ServerWorld) {
			for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) getEntityWorld(), getBlockPos())) {
				ServerPlayNetworking.send(player, new InitAnimationsS2CPayload(uuid));
			}
		}*/
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

		return result;
	}

	@Override
	public void tick() {
		super.tick();
		tickDegradation(this);

		if (this.getEntityWorld().isClient()) {
			updateAnimations();
		}
		else {
			if (this.getUsingAttack()) {
				if (!isInPlay()) {
					setUsingAttack(false);
					setTarget(null);
				}
				/*else {
					if (this.biteTimeout <= 0) {
						this.playSound(ModSounds.ENTITY_ZOMBIE_EAT, 0.5f, MiscUtil.getSoundPitch(getRandom()));
						this.biteTimeout = 10;
						//HomeLawnSecurity.LOGGER.info("zombie took bite");
					}
					--this.biteTimeout;
				}*/
			}
			/*else {
				this.biteTimeout = -1;
			}*/

			if (attackTimeout > 0) {
				--this.attackTimeout;
			}

			if (attackTimeout <= 0) {
				setUsingAttack(false);
			}

			//HomeLawnSecurity.LOGGER.info("attackTimeout is " + attackTimeout + " and isUsingAttack() is " + isUsingAttack());

			/*if (getHasLostHead()) {
				damage((ServerWorld) getEntityWorld(), this.getDamageSources().create(ModDamageTypes.ZOMBIE_HEADLESS), 2);
			}*/
		}
	}

	@Override
	public void tickMovement() {
		if (getEntityWorld() instanceof ServerWorld && isInPlay()) {
			// Stop moving when coming into contact with a plant
			movementBlocked = false;
			for (Entity plant : getEntityWorld().getOtherEntities(this, getBoundingBox().contract(0.2, 0.5, 0.2),
				EntityPredicates.EXCEPT_SPECTATOR.and(entity -> this.stopsMovingAt(entity, false)))) {
				if (MiscUtil.isFacingEntity(this, plant)) {
					movementBlocked = true;
					//setVelocity(0d, getVelocity().y, 0d);
					getNavigation().stop();
					break;
				}
			}
			if (!movementBlocked) {
				for (Entity plant : getEntityWorld().getOtherEntities(this, getBoundingBox().expand(0.4, -0.5, 0.4),
					EntityPredicates.EXCEPT_SPECTATOR.and(entity -> this.stopsMovingAt(entity, true)))) {
					if (MiscUtil.isFacingEntity(this, plant)) {
						movementBlocked = true;
						getNavigation().stop();
						break;
					}
				}
			}
		}

		super.tickMovement();
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

		if (attacker instanceof IPvzEntity
			|| source.isOf(ModDamageTypes.ZOMBIE_HEADLESS)) {
			damaged = super.damage(world, source, amount);

			this.hurtTime = 0;
			this.timeUntilRegen = 0;
		}
		else {
			damaged = super.damage(world, source, amount);
		}

		/*if (!getHasLostHeadwear() && getHealth() < getLoseHeadwearHealth()) {
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
		}*/

		return damaged;
	}

	@Override
	public boolean collidesWith(Entity other) {
		//return (other.isCollidable(this) || (other instanceof PlantEntity && this.hasHead())) && !this.isConnectedThroughVehicle(other);
		return false;
	}

	/**
	 * @param other The entity to check against.
	 * @return Whether this entity should stop moving forward when reaching that entity.
	 */
	public boolean stopsMovingAt(Entity other, boolean checkOffPath) {
		if (checkOffPath)
			return other instanceof PlantEntity plant
				&& !plant.onPath
				&& this.isInPlay();
		return other instanceof PlantEntity plant
			&& (plant.getPathId() == this.getPathId()
			|| plant.intersectingPath == this.getPathId())
			&& this.isInPlay();
	}

	/**
	 * Zombies that have lost their head cannot do any harm.
	 * They are "out of play" besides plants targeting and damaging them.
	 * @return If the zombie is a threat to the plant team.
	 */
	public boolean isInPlay() {
		return this.hasHead();
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		/*if (pathTag == ModTags.Blocks.ZOMBIE_PATH_2)
			view.putInt("path_tag", 2);
		else
			view.putInt("path_tag", 1);*/

		writeDegradationNbt(view);

		view.putBoolean("movement_blocked", movementBlocked);

		//view.putInt("bite_timeout", biteTimeout);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		/*int path = view.getInt("path_tag", 1);
		if (path == 2) {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_2;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		} else {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}*/

		readDegradationNbt(view);

		movementBlocked = view.getBoolean("movement_blocked", false);

		//biteTimeout = view.getInt("bite_timeout", 0);
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

		if (attackAnimationTimeout <= 0 && (isInPlay() || !isAlive())) {
			attackAnimationState.stop();
		}
	}

	@Override
	protected void updateLimbs(float posDelta) {
		float f = Math.min(posDelta * 16.0f, 1.0f);
		this.limbAnimator.updateLimbs(f, 0.8f, this.isBaby() ? 3.0f : 1.0f);
	}

	@Override
	public void onAttacking(Entity target) {
		super.onAttacking(target);
		setUsingAttack(true);
		attackTimeout = 2;
	}

	/*
		DAMAGE
	 */

	/*
	 * @return The health at which this zombie will lose its headwear upon going below, or -1 if it doesn't normally spawn with any.
	 */
	//public abstract float getLoseHeadwearHealth();

	/*
	 * @return The health at which this zombie will lose its arm upon going below, or -1 if it never does.
	 * Typically, this is two thirds of the zombie's max health.
	 */
	//public abstract float getLoseArmHealth();

	/*
	 * @return The health at which this zombie will lose its head and start to die upon going below.
	 * Typically, this is a third of the zombie's max health.
	 */
	//public abstract float getLoseHeadHealth();

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

	ArrayList<DegradationStage> degradationStages = new ArrayList<>();

	@Override
	public ArrayList<DegradationStage> getDegradationStageList() {
		return degradationStages;
	}

	public boolean hasHead() {
		return !hasTriggeredDegradationStage("head");
	}

	/*public abstract TrackedData<Boolean> getHasLostHeadwearTrackedData();

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
	}*/

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);

		var usingAttack = getUsingAttackTrackedData();
		if (usingAttack != null)
			builder.add(usingAttack, false);

		/*var lostHeadwear = getHasLostHeadwearTrackedData();
		if (lostHeadwear != null)
			builder.add(lostHeadwear, false);

		var lostArm = getHasLostArmTrackedData();
		if (lostArm != null)
			builder.add(lostArm, false);

		var lostHead = getHasLostHeadTrackedData();
		if (lostHead != null)
			builder.add(lostHead, false);*/
	}

	/*
		BLOCKS
	 */

	@Override
	protected boolean shouldPathLikeZombie() {
		return true;
	}

	@Override
	protected TagKey<Block> getGoalBlockTag() {
		return ModTags.Blocks.ZOMBIE_GOAL;
	}

	@Override
	protected TagKey<Block> getGoalMarkerTag() {
		return ModTags.Blocks.ZOMBIE_GOAL_MARKERS;
	}

	@Override
	protected TagKey<Block> getStartBlockTag() {
		return ModTags.Blocks.ZOMBIE_START;
	}

	@Override
	protected TagKey<Block> getStartMarkerTag() {
		return ModTags.Blocks.ZOMBIE_START_MARKERS;
	}

	/*public boolean isWalkable(BlockPos pos) {
		return LawnUtil.isWalkable(pos, getEntityWorld(), pathId, getSteppingPos(), true);
	}*/

	/**
	 * @return Whether a zombie can be placed on top of this block.
	 */
	public static boolean isPlaceable(BlockPos pos, World world) {
		return LawnUtil.isAnyPath(pos, world);
	}

	/*/**
	 * @return Whether this block is a goal.
	 *
	public boolean isGoal(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_GOAL_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_GOAL);
	}

	/**
	 * @return Whether this block is a start.
	 *
	public boolean isStart(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_START_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_START);
	}*/

	/*@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		//BlockState state = world.getBlockState(pos.down());
		if (isThisPath(pos.down())) {
			return 0f; // Path
		}
		else if (isGoal(pos.down())) {
			return 0f; // Goal
		}
		return Float.NEGATIVE_INFINITY; // Off-road
	}*/

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new ZombieStayOnPathGoal(this, 1f));
		this.goalSelector.add(2, new ZombieTargetGoal<>(this, PlantEntity.class, true, 1f));
		this.goalSelector.add(3, new ZombieMoveGoal(this, 1f));
	}

	@Override
	protected boolean itemDuplicatesSpawnItem(ItemStack stack) {
		if (stack == null) return false;
		return stack.isIn(ModTags.Items.ZOMBIE_FEEDABLE_FOR_DUPLICATE);
	}

	/*
		SOUNDS
	 */

	@Override
	protected void playAttackSound() {
		playEatingSoundsOnAttack();
	}

	public void playEatingSoundsOnAttack() {
		Entity target = getTarget();
		if (target instanceof LivingEntity living && living.getHealth() <= 0) {
			SoundEvent sound = getGulpSound();
			if (sound != null)
				this.playSound(sound, 1.0f, MiscUtil.getSoundPitch(getRandom()));
		}
		else {
			SoundEvent sound = getBiteSound();
			if (sound != null)
				this.playSound(sound, 0.5f, MiscUtil.getSoundPitch(getRandom()));
		}
	}

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
		if (this.getEntityWorld().getFluidState(this.getBlockPos()).isIn(FluidTags.WATER))
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

	protected @Nullable SoundEvent getBiteSound() {
		return ModSounds.ENTITY_ZOMBIE_EAT;
	}

	protected @Nullable SoundEvent getGulpSound() {
		return ModSounds.ENTITY_ZOMBIE_GULP;
	}

	@Override
	protected void playFeedSound() {
		getEntityWorld().playSoundFromEntity(null, this, ModSounds.ENTITY_ZOMBIE_FEED, getSoundCategory(), 1f, 1f);
	}

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}
}
