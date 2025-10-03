package net.db64.homelawnsecurity.entity.custom.plant;

import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.db64.homelawnsecurity.item.ModItems;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class SunflowerEntity extends PlantEntity implements IPvzEntity, ILawnPlant {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(SunflowerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private static final PlantStats STATS = new PlantStats().attackRange(3f).attackTicks(30);

	public final AnimationState setupAnimationState = new AnimationState();

	public final int MAX_SUN_TICKS = 480;
	public int sunTicks = 120;

	/*
		GENERAL
	 */

	public SunflowerEntity(EntityType<? extends PlantEntity> entityType, World world) {
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
	public void tick() {
		super.tick();

		if (!(getWorld() instanceof ServerWorld)) {
			return;
		}

		sunTicks--;

		if (sunTicks <= 0) {
			sunTicks = MAX_SUN_TICKS;

			getWorld().spawnEntity(createProjectile(this));
		}
	}

	public static ProjectileEntity createProjectile(SunflowerEntity entity) {
		//ArrowItem arrowItem = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
		Random random = entity.getRandom();

		ProjectileEntity projectileEntity = new CurrencyEntity(entity.getX(), entity.getY() + entity.getHeight() * 0.6, entity.getZ(), entity.getWorld(), new ItemStack(ModItems.SUN));
		projectileEntity.setOwner(entity);
		projectileEntity.setVelocity(random.nextFloat() * 0.2 - 0.1, 0.2, random.nextFloat() * 0.2 - 0.1);

		return projectileEntity;
	}

	@Override
	public double getEyeY() {
		return this.getY() + (this.getHeight() * 0.8);
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		view.putInt("sun_ticks", sunTicks);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		sunTicks = view.getInt("sun_ticks", 0);
	}

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		goalSelector.add(2, new LookAtEntityGoal(this, ZombieEntity.class, 3));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 300 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 20 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 3)
			.add(EntityAttributes.MOVEMENT_SPEED, 0);
	}
}
