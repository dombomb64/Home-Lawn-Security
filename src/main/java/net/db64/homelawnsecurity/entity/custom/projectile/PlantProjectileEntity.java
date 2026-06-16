package net.db64.homelawnsecurity.entity.custom.projectile;

import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.db64.homelawnsecurity.util.MiscUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class PlantProjectileEntity extends ProjectileEntity implements IPvzEntity {
	private static final TrackedData<Float> MAX_DISTANCE =
		DataTracker.registerData(PlantProjectileEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private static final TrackedData<Float> PROJECTILE_DISTANCE =
		DataTracker.registerData(PlantProjectileEntity.class, TrackedDataHandlerRegistry.FLOAT);
	private int pathId = 0;
	private int intersectingPath = 0;

	public PlantProjectileEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		setMaxDistance(0f);
	}

	public PlantProjectileEntity(EntityType<? extends ProjectileEntity> entityType, double x, double y, double z, World world, float maxDistance) {
		this(entityType, world);
		this.setPosition(x, y, z);
		setMaxDistance(maxDistance);
	}

	public float getMaxDistance() {
		return getDataTracker().get(MAX_DISTANCE);
	}

	public void setMaxDistance(float maxDistance) {
		getDataTracker().set(MAX_DISTANCE, maxDistance);
	}

	public float getProjectileDistance() {
		return getDataTracker().get(PROJECTILE_DISTANCE);
	}

	public void setProjectileDistance(float projectileDistance) {
		getDataTracker().set(PROJECTILE_DISTANCE, projectileDistance);
	}

	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public int getIntersectingPath() {
		return intersectingPath;
	}

	public void setIntersectingPath(int intersectingPath) {
		this.intersectingPath = intersectingPath;
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(MAX_DISTANCE, 0f);
		builder.add(PROJECTILE_DISTANCE, 0f);
	}

	@Override
	protected boolean canHit(Entity entity) {
		if (!(entity instanceof ZombieEntity zombie))
			return false;
		return /*super.canHit(entity) &&*/
			(
				getPathId() == 0 ||
					getPathId() == zombie.getPathId() ||
					(
						getIntersectingPath() == zombie.getPathId() &&
							getIntersectingPath() != 0
					)
			);
	}

	@Override
	public void tick() {
		this.tickInitialBubbleColumnCollision();
		this.applyGravity();
		//this.applyDrag();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		Vec3d vec3d;
		if (hitResult.getType() != HitResult.Type.MISS) {
			vec3d = hitResult.getPos();
		} else {
			vec3d = this.getEntityPos().add(this.getVelocity());
		}

		//double d = this.getX() + vec3d.x;
		//double e = this.getY() + vec3d.y;
		//double f = this.getZ() + vec3d.z;

		this.setPosition(vec3d);
		this.updateRotation();
		this.tickBlockCollision();
		super.tick();
		if (hitResult.getType() != HitResult.Type.MISS && this.isAlive()) {
			this.hitOrDeflect(hitResult);
		}

		Vec3d velocity = this.getVelocity();
		setProjectileDistance(getProjectileDistance() + (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z));
		//this.setPosition(d, e, f);
	}

	private void tickInitialBubbleColumnCollision() {
		if (this.firstUpdate) {
			for (BlockPos blockPos : BlockPos.iterate(this.getBoundingBox())) {
				BlockState blockState = this.getEntityWorld().getBlockState(blockPos);
				if (blockState.isOf(Blocks.BUBBLE_COLUMN)) {
					blockState.onEntityCollision(this.getEntityWorld(), blockPos, this, EntityCollisionHandler.DUMMY, true);
				}
			}
		}
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		if (!(getEntityWorld() instanceof ServerWorld))
			return;
		entity.damage((ServerWorld) entity.getEntityWorld(), this.getDamageSources().create(getDamageType(), this, this.getOwner()), 20 * IPvzEntity.HEALTH_SCALE);
		//entity.damage(ModDamageTypes.of(entity.getEntityWorld(), getDamageType()), 20);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.getEntityWorld().isClient()) {
			SoundEvent sound = getHitSound();
			if (sound != null) {
				this.playSound(sound, 1.0f, MiscUtil.getSoundPitch(this.getRandom()));
			}
			this.discard();
		}
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		view.putInt("path_id", pathId % (LawnUtil.getPathTypeAmount() + 1));
		view.putInt("intersecting_path", intersectingPath % (LawnUtil.getPathTypeAmount() + 1));
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		this.pathId = view.getInt("path_id", 0) % (LawnUtil.getPathTypeAmount() + 1);
		this.intersectingPath = view.getInt("intersecting_path", 0) % (LawnUtil.getPathTypeAmount() + 1);
	}

	/**
	 * @return The gravity value for this projectile.
	 * @implNote By default, the projectile has no gravity until it reaches its max distance,
	 * but this may be overridden for things like catapult projectiles.
	 */
	@Override
	protected double getGravity() {
		if (getProjectileDistance() <= getMaxDistance())
			return 0d;
		return 0.08d;
	}

	public abstract SoundEvent getHitSound();

	public abstract RegistryKey<DamageType> getDamageType();
}
