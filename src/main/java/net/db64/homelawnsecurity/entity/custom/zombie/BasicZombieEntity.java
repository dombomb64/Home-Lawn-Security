package net.db64.homelawnsecurity.entity.custom.zombie;

import net.db64.homelawnsecurity.entity.ai.zombie.ZombieMeleeAttackGoal;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;

public class BasicZombieEntity extends ZombieEntity implements IPvzEntity {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(BasicZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> HAS_LOST_HEADWEAR =
		DataTracker.registerData(BasicZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> HAS_LOST_ARM =
		DataTracker.registerData(BasicZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	private static final TrackedData<Boolean> HAS_LOST_HEAD =
		DataTracker.registerData(BasicZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public final AnimationState setupAnimationState = new AnimationState();
	//private int setupAnimationTimeout = 0;

	/*
		GENERAL
	 */

	public BasicZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
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
		return 180 * IPvzEntity.HEALTH_SCALE;
	}

	@Override
	public float getLoseHeadHealth() {
		return 90 * IPvzEntity.HEALTH_SCALE;
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
		super.initGoals();

		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(2, new ZombieMeleeAttackGoal(this, 1, false));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 270 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 5 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 64)
			.add(EntityAttributes.MOVEMENT_SPEED, 0.1);
	}
}
