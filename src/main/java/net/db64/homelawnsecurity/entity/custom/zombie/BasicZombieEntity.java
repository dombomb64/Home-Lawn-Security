package net.db64.homelawnsecurity.entity.custom.zombie;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class BasicZombieEntity extends ZombieEntity {
	public final AnimationState setupAnimationState = new AnimationState();
	//private int setupAnimationTimeout = 0;

	//public final AnimationState eatingAnimationState = new AnimationState();
	//private int eatingAnimationTimeout = 0;

	public BasicZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
	}

	private void updateAnimations() {
		if (!setupAnimationState.isRunning())
			setupAnimationState.start(this.age);

		/*if (this.eatingAnimationTimeout == 0) {
			this.eatingAnimationState.stop();
		} else {
			this.eatingAnimationTimeout = -1;
			this.eatingAnimationState.start(this.age);
		}*/
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getWorld().isClient()) {
			updateAnimations();
		}
	}

	@Override
	protected void initGoals() {
		super.initGoals();

		this.goalSelector.add(0, new SwimGoal(this));
		this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 1)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2);
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BAT_DEATH;
	}
}
