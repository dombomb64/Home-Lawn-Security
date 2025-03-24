package net.db64.homelawnsecurity.entity.custom.plant;

import net.db64.homelawnsecurity.entity.ai.plant.PeashooterAttackGoal;
import net.db64.homelawnsecurity.entity.ai.plant.PlantTargetGoal;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.control.LookControl;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

import java.util.Optional;

public class PeashooterEntity extends PlantEntity implements IPvzEntity, ILawnPlant, RangedAttackMob {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(PeashooterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private static final PlantStats STATS = new PlantStats().attackRange(3f).attackTicks(30);

	public final AnimationState setupAnimationState = new AnimationState();
	//public int setupAnimationTimeout = 0;

	//public int attackHeadRotTimeout = 0;

	/*
		GENERAL
	 */

	public PeashooterEntity(EntityType<? extends PlantEntity> entityType, World world) {
		super(entityType, world);
		this.lookControl = new PeashooterLookControl(this);
	}

	/*
		ANIMATIONS
	 */

	protected void updateAnimations() {
		super.updateAnimations();

		if (!setupAnimationState.isRunning())
			setupAnimationState.start(this.age);

		/*if (this.isAttacking()) {
			attackHeadRotTimeout = 20;
		}
		else if (attackHeadRotTimeout > 0) {
			--this.attackHeadRotTimeout;
		}*/
	}

	@Override
	public TrackedData<Boolean> getUsingAttackTrackedData() {
		return USING_ATTACK;
	}

	/*
		BLOCKS
	 */

	/*
		SHOOTING
	 */

	@Override
	public void shootAt(LivingEntity target, float pullProgress) {
		setUsingAttack(true);
		attackTimeout = 10;

		ProjectileEntity projectileEntity = createProjectile(this);
		double d = target.getX() - this.getX();
		//double e = target.getBodyY(0.3333333333333333) - projectileEntity.getY();
		double f = target.getZ() - this.getZ();
		//double g = Math.sqrt(d * d + f * f);
		projectileEntity.setVelocity(d, /*e + g * (double)0.2f*/ 0, f, 0.5f, /*14 - this.getWorld().getDifficulty().getId() * 4*/ 0);
		this.playSound(ModSounds.ENTITY_PEA_THROW, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		this.getWorld().spawnEntity(projectileEntity);
	}

	public static ProjectileEntity createProjectile(PeashooterEntity entity) {
		//ArrowItem arrowItem = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);

		ProjectileEntity projectileEntity = new PeaEntity(entity.getX(), entity.getY() + entity.getHeight() * 0.6, entity.getZ(), entity.getWorld(), STATS.attackRange * 1.5f);
		projectileEntity.setOwner(entity);

		return projectileEntity;
	}

	@Override
	public double getEyeY() {
		return this.getY() + (this.getHeight() * 0.8);
	}

	protected class PeashooterLookControl extends LookControl {
		public PeashooterLookControl(MobEntity entity) {
			super(entity);
		}

		@Override
		protected boolean shouldStayHorizontal() {
			return PeashooterEntity.this.getTarget() != null;
		}

		@Override
		protected Optional<Float> getTargetPitch() {
			if (PeashooterEntity.this.getTarget() != null)
				return Optional.of(0f);
			return super.getTargetPitch();
		}

		@Override
		public void lookAt(double x, double y, double z, float maxYawChange, float maxPitchChange) {
			super.lookAt(x, y, z, maxYawChange, maxPitchChange);
			this.y = PeashooterEntity.this.getEyeY();
		}
	}

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		this.goalSelector.add(2, new PlantTargetGoal<>(this, ZombieEntity.class, true, STATS.attackRange));
		this.goalSelector.add(2, new PeashooterAttackGoal(this, 1, STATS.attackTicks, STATS.attackRange));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 300 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 20 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 3)
			.add(EntityAttributes.MOVEMENT_SPEED, 0);
	}
}
