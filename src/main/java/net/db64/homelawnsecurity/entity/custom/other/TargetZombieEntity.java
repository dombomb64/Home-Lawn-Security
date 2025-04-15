package net.db64.homelawnsecurity.entity.custom.other;

import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TargetZombieEntity extends ZombieEntity implements IPvzEntity {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(TargetZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> HAS_LOST_HEADWEAR =
		DataTracker.registerData(TargetZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> HAS_LOST_ARM =
		DataTracker.registerData(TargetZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> HAS_LOST_HEAD =
		DataTracker.registerData(TargetZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public final AnimationState setupAnimationState = new AnimationState();
	//private int setupAnimationTimeout = 0;

	/*
		GENERAL
	 */

	public TargetZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
	}

	public static boolean isPlaceable(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.TARGET_ZOMBIE_PLACEABLE_MARKERS);
		}
		return state.isIn(ModTags.Blocks.TARGET_ZOMBIE_PLACEABLE);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	/*
		ANIMATIONS
	 */

	@Override
	protected void updateAnimations() {
		super.updateAnimations();
		if (!setupAnimationState.isRunning())
			setupAnimationState.start(this.age);
	}

	@Override
	public float getLoseHeadwearHealth() {
		return -1;
	}

	@Override
	public float getLoseArmHealth() {
		return -1;
	}

	@Override
	public float getLoseHeadHealth() {
		return 0;
	}

	@Override
	public TrackedData<Boolean> getUsingAttackTrackedData() {
		return USING_ATTACK;
	}

	@Override
	public TrackedData<Boolean> getHasLostHeadwearTrackedData() {
		return HAS_LOST_HEADWEAR;
	}

	@Override
	public TrackedData<Boolean> getHasLostArmTrackedData() {
		return HAS_LOST_ARM;
	}

	@Override
	public TrackedData<Boolean> getHasLostHeadTrackedData() {
		return HAS_LOST_HEAD;
	}

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		//super.initGoals();

		//this.goalSelector.add(0, new SwimGoal(this));
		//this.goalSelector.add(2, new ZombieMeleeAttackGoal(this, 1, false));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 200 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 5 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 0)
			.add(EntityAttributes.MOVEMENT_SPEED, 0);
	}
}
