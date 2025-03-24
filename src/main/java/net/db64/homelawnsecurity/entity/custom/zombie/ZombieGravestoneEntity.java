package net.db64.homelawnsecurity.entity.custom.zombie;

import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.db64.homelawnsecurity.item.ModItems;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ZombieGravestoneEntity extends ZombieEntity implements IPvzEntity {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(ZombieGravestoneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public final AnimationState setupAnimationState = new AnimationState();

	public final int MAX_BRAINPOWER_TICKS = 480;
	public int brainpowerTicks = 480;

	/*
		GENERAL
	 */

	public ZombieGravestoneEntity(EntityType<? extends ZombieEntity> entityType, World world) {
		super(entityType, world);
	}

	/*
		ANIMATIONS
	 */

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
		return -1;
	}

	@Override
	public TrackedData<Boolean> getUsingAttackTrackedData() {
		return USING_ATTACK;
	}

	@Override
	public TrackedData<Boolean> getHasLostHeadwearTrackedData() {
		return null;
	}

	@Override
	public TrackedData<Boolean> getHasLostArmTrackedData() {
		return null;
	}

	@Override
	public TrackedData<Boolean> getHasLostHeadTrackedData() {
		return null;
	}

	/*
		BLOCKS
	 */

	/*
		SHOOTING
	 */

	@Override
	public void tick() {
		super.tick();

		if (getWorld() instanceof ClientWorld) {
			return;
		}

		brainpowerTicks--;

		if (brainpowerTicks <= 0) {
			brainpowerTicks = MAX_BRAINPOWER_TICKS;

			getWorld().spawnEntity(createProjectile(this));
		}
	}

	public static ProjectileEntity createProjectile(ZombieGravestoneEntity entity) {
		//ArrowItem arrowItem = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
		Random random = entity.getRandom();

		ProjectileEntity projectileEntity = new CurrencyEntity(entity.getX(), entity.getY() + entity.getHeight() * 0.6, entity.getZ(), entity.getWorld(), new ItemStack(ModItems.BRAINPOWER));
		projectileEntity.setOwner(entity);
		projectileEntity.setVelocity(random.nextFloat() * 0.2 - 0.1, 0.2, random.nextFloat() * 0.2 - 0.1);

		return projectileEntity;
	}

	@Override
	public double getEyeY() {
		return this.getY() + (this.getHeight() * 0.8);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putInt("brainpower_ticks", brainpowerTicks);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		if (nbt.contains("brainpower_ticks", NbtElement.INT_TYPE)) {
			brainpowerTicks = nbt.getInt("brainpower_ticks");
		} else {
			brainpowerTicks = 0;
		}
	}

	/*
		SOUND
	 */

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return null;
	}

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		//goalSelector.add(2, new LookAtEntityGoal(this, ZombieEntity.class, 3));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 400 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 20 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 3)
			.add(EntityAttributes.MOVEMENT_SPEED, 0);
	}
}
