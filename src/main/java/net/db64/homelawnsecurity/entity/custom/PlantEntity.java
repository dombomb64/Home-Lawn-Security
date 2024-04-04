package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.item.custom.LawnSeedPacketItem;
import net.db64.homelawnsecurity.item.custom.PathSeedPacketItem;
import net.db64.homelawnsecurity.item.custom.SeedPacketItem;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PlantEntity extends PathAwareEntity implements IPvzEntity {
	//private abstract static final TrackedData<Boolean> USING_ATTACK =
		//DataTracker.registerData(PlantEntityClassHere.class, TrackedDataHandlerRegistry.BOOLEAN);

	public final AnimationState attackAnimationState = new AnimationState();
	public int attackAnimationTimeout = 0;
	public int maxAttackAnimationTimeout = 10;
	public int attackTimeout = 0;

	/*
		GENERAL
	 */

	protected PlantEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
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
	}

	@Override
	public boolean damage(DamageSource source, float amount) {
		if (source.getAttacker() != null && source.getAttacker() instanceof IPvzEntity) {
			boolean damaged = super.damage(source, amount);

			this.hurtTime = 0;
			this.timeUntilRegen = 0;

			return damaged;
		}
		return super.damage(source, amount * 10);
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
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(getUsingAttackTrackedData(), false);
	}

	/*
		BLOCKS
	 */

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

	/*
		STATS
	 */

	/**
	 * A class that has all the generic stats of the plant.
	 * For organization reasons.
	 */
	public static class PlantStats {
		public float attackRange = 3f;
		public int attackTicks = 20;

		public PlantStats() {
		}

		public PlantStats attackRange(float value) {
			attackRange = value;
			return this;
		}

		public PlantStats attackTicks(int value) {
			attackTicks = value;
			return this;
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

	@Override
	public SoundEvent getEatSound(ItemStack stack) {
		return null;
	}

	@Override
	protected SoundEvent getDrinkSound(ItemStack stack) {
		return null;
	}

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}
}
