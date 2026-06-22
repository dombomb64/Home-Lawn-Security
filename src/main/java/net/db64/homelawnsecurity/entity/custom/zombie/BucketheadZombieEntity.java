package net.db64.homelawnsecurity.entity.custom.zombie;

import net.db64.homelawnsecurity.entity.ai.zombie.ZombieMeleeAttackGoal;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class BucketheadZombieEntity extends ZombieEntity {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(BucketheadZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public final AnimationState setupAnimationState = new AnimationState();
	public final AnimationState bucketSetupAnimationState = new AnimationState();
	public final AnimationState idleAnimationState = new AnimationState();

	/*
		GENERAL
	 */

	public BucketheadZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);

		ArrayList<DegradationStage> degradationStages = getDegradationStageList();
		degradationStages.add(new DegradationStage("headwear", 270 * IPvzEntity.HEALTH_SCALE, false, ModSounds.ENTITY_ZOMBIE_DETACH_HEADWEAR));
		degradationStages.add(new DegradationStage("arm", 180 * IPvzEntity.HEALTH_SCALE, false, ModSounds.ENTITY_ZOMBIE_DETACH_LIMB));
		degradationStages.add(new DegradationStage("head", 90 * IPvzEntity.HEALTH_SCALE, true, ModSounds.ENTITY_ZOMBIE_DETACH_HEAD));

		if (getEntityWorld().isClient()) {
			setupAnimationState.startIfNotRunning(this.age);
			bucketSetupAnimationState.startIfNotRunning(this.age);
			idleAnimationState.startIfNotRunning(this.age);
		}
	}

	/*
		ANIMATIONS
	 */

	@Override
	protected void updateAnimations() {
		super.updateAnimations();
	}

	@Override
	public TrackedData<Boolean> getUsingAttackTrackedData() {
		return USING_ATTACK;
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
			.add(EntityAttributes.MAX_HEALTH, 1370 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 50 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 64)
			.add(EntityAttributes.MOVEMENT_SPEED, 0.0748);
	}

	/*
		SOUNDS
	 */

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return hasTriggeredDegradationStage("headwear") ? null : ModSounds.ENTITY_BUCKETHEAD_ZOMBIE_HURT;
	}
}
