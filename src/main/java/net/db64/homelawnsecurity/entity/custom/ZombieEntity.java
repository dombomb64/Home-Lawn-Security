package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieStayOnPathGoal;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieMoveGoal;
import net.db64.homelawnsecurity.entity.ai.zombie.ZombieTargetGoal;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
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
	protected int eatingSoundTimeout = 0;

	protected ZombieEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);

		//limbAnimator.setSpeed(limbAnimator.getSpeed() * 10f);

		//setPathTagNbt(pathTag);
		/*World world2 = getWorld();
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

		if (this.getWorld().isClient()) {
			updateAnimations();
		}
		else {
			if (this.getUsingAttack()) {
				if (!hasHead()) {
					setUsingAttack(false);
					setTarget(null);
				}
				else {
					if (this.eatingSoundTimeout <= 0) {
						this.playSound(ModSounds.ENTITY_ZOMBIE_EAT, 0.5f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 1f));
						this.eatingSoundTimeout = 10;
						//HomeLawnSecurity.LOGGER.info("zombie took bite");
					}
					else {
						--this.eatingSoundTimeout;
					}
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

			/*if (getHasLostHead()) {
				damage((ServerWorld) getWorld(), this.getDamageSources().create(ModDamageTypes.ZOMBIE_HEADLESS), 2);
			}*/
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
			world.playSound(this, getBlockPos(), ModSounds.RANDOM_SHOVEL_ATTACK, attacker.getSoundCategory(), 1, 1);
			damaged = super.damage(world, source, 1000000);
		}
		else {
			damaged = super.damage(world, source, amount);
		}

		if (attacker instanceof IPvzEntity
			|| source.isOf(ModDamageTypes.ZOMBIE_HEADLESS)) {
			//damaged = super.damage(world, source, amount);

			this.hurtTime = 0;
			this.timeUntilRegen = 0;
		}
		//else {
			//damaged = super.damage(world, source, amount * 10);
		//}

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
		return (other.isCollidable(this) || (other instanceof PlantEntity && this.hasHead())) && !this.isConnectedThroughVehicle(other);
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		/*if (pathTag == ModTags.Blocks.ZOMBIE_PATH_2)
			view.putInt("path_tag", 2);
		else
			view.putInt("path_tag", 1);*/

		writeDegradationNbt(view);
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
	protected void updateLimbs(float posDelta) {
		float f = Math.min(posDelta * 16.0f, 1.0f);
		this.limbAnimator.updateLimbs(f, 0.8f, this.isBaby() ? 3.0f : 1.0f);
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
		if (getDegradationStage("head") != null) {
			return !hasTriggeredDegradationStage("head");
		}
		return true;
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

	public boolean isWalkable(BlockPos pos) {
		return LawnUtil.isWalkable(pos, getWorld(), pathId, true);
	}

	/**
	 * @return Whether a zombie can be placed on top of this block.
	 */
	public static boolean isPlaceable(BlockPos pos, World world) {
		return LawnUtil.isAnyPath(pos, world);
	}

	/**
	 * @return Whether this block is a goal.
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
	 * @return Whether this block is a start.
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
		this.goalSelector.add(2, new ZombieTargetGoal<PlantEntity>(this, PlantEntity.class, true, 1));
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
	protected void playFeedSound() {
		getWorld().playSoundFromEntity(null, this, ModSounds.ENTITY_ZOMBIE_FEED, getSoundCategory(), 1f, 1f);
	}

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.HOSTILE;
	}
}
