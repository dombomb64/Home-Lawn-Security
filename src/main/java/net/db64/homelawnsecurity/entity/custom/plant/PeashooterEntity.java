package net.db64.homelawnsecurity.entity.custom.plant;

import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class PeashooterEntity extends PlantEntity implements RangedAttackMob {
	private static final TrackedData<Boolean> ATTACKING =
		DataTracker.registerData(PeashooterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public final AnimationState setupAnimationState = new AnimationState();
	//public int setupAnimationTimeout = 0;

	public final AnimationState attackAnimationState = new AnimationState();
	public int attackAnimationTimeout = 0;
	public int attackTimeout = 0;

	public PeashooterEntity(EntityType<? extends PlantEntity> entityType, World world) {
		super(entityType, world);
	}

	private void updateAnimations() {
		if (!setupAnimationState.isRunning())
			setupAnimationState.start(this.age);

		if (this.isAttacking() && attackAnimationTimeout <= 0) {
			attackAnimationTimeout = 40;
			attackAnimationState.start(this.age);
		}
		else {
			--this.attackAnimationTimeout;
		}

		if (!this.isAttacking()) {
			attackAnimationState.stop();
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getWorld().isClient()) {
			updateAnimations();
		}
		else {
			if (this.isAttacking() && attackTimeout <= 0) {
				attackTimeout = 40;
				setAttacking(false);
			}
			else {
				--this.attackTimeout;
			}
		}
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(2, new ActiveTargetGoal<ZombieEntity>(this, ZombieEntity.class, true));
		this.goalSelector.add(2, new ProjectileAttackGoal(this, 1, 20, 3));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.GENERIC_MAX_HEALTH, 5)
			.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2)
			.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 2)
			.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0);
	}

	public void setAttacking(boolean attacking) {
		this.dataTracker.set(ATTACKING, attacking);
	}

	@Override
	public boolean isAttacking() {
		return this.dataTracker.get(ATTACKING);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ATTACKING, false);
	}

	@Override
	public void shootAt(LivingEntity target, float pullProgress) {
		setAttacking(true);

		ItemStack itemStack = this.getProjectileType(this.getStackInHand(ProjectileUtil.getHandPossiblyHolding(this, Items.BOW)));
		ProjectileEntity projectileEntity = createProjectile(this, itemStack, pullProgress);
		double d = target.getX() - this.getX();
		double e = target.getBodyY(0.3333333333333333) - projectileEntity.getY();
		double f = target.getZ() - this.getZ();
		double g = Math.sqrt(d * d + f * f);
		projectileEntity.setVelocity(d, e + g * (double)0.2f, f, 1.6f, 14 - this.getWorld().getDifficulty().getId() * 4);
		this.playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
		this.getWorld().spawnEntity(projectileEntity);
	}

	public static ProjectileEntity createProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
		//ArrowItem arrowItem = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);

		ProjectileEntity projectileEntity = new PeaEntity(entity, entity.getWorld());

		return projectileEntity;
	}
}
