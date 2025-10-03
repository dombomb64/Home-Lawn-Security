package net.db64.homelawnsecurity.entity.custom.projectile;

import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;

public class PeaEntity extends ProjectileEntity implements IPvzEntity {
	private static final TrackedData<Float> MAX_DISTANCE =
		DataTracker.registerData(PeaEntity.class, TrackedDataHandlerRegistry.FLOAT);

	public PeaEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		setMaxDistance(0f);
	}

	public PeaEntity(LivingEntity livingEntity, World world, float maxDistance) {
		this(livingEntity.getX(), livingEntity.getEyeY() - (double) 0.1f, livingEntity.getZ(), world, maxDistance);
	}

	public PeaEntity(double x, double y, double z, World world, float maxDistance) {
		this(ModEntities.Projectile.PEA, world);
		this.setPosition(x, y, z);
		setMaxDistance(maxDistance);
	}

	@Override
	protected boolean canHit(Entity entity) {
		return entity instanceof ZombieEntity && super.canHit(entity);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		builder.add(MAX_DISTANCE, 0f);
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
			vec3d = this.getPos().add(this.getVelocity());
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
		this.distanceTraveled += (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);
		//this.setPosition(d, e, f);
	}

	protected double getGravity() {
		if (distanceTraveled <= getMaxDistance()) // Bro why doesn't this variable work with projectiles
			return 0d;
		return 0.08d;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		if (!(getWorld() instanceof ServerWorld))
			return;
		entity.damage((ServerWorld) entity.getWorld(), this.getDamageSources().create(ModDamageTypes.PEA, this, this.getOwner()), 20 * IPvzEntity.HEALTH_SCALE);
		//entity.damage(ModDamageTypes.of(entity.getWorld(), ModDamageTypes.PEA), 20);
	}

	@Override
	protected void onCollision(HitResult hitResult) {
		super.onCollision(hitResult);
		if (!this.getWorld().isClient) {
			this.playSound(ModSounds.ENTITY_PEA_HIT, 1.0f, 1.0f);
			this.discard();
		}
	}

	private void tickInitialBubbleColumnCollision() {
		if (this.firstUpdate) {
			Iterator var1 = BlockPos.iterate(this.getBoundingBox()).iterator();

			while(var1.hasNext()) {
				BlockPos blockPos = (BlockPos)var1.next();
				BlockState blockState = this.getWorld().getBlockState(blockPos);
				if (blockState.isOf(Blocks.BUBBLE_COLUMN)) {
					blockState.onEntityCollision(this.getWorld(), blockPos, this, EntityCollisionHandler.DUMMY);
				}
			}
		}
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		view.putFloat("distance_traveled", distanceTraveled);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		this.distanceTraveled = view.getFloat("distance_traveled", 0f);
	}

	public float getMaxDistance() {
		return getDataTracker().get(MAX_DISTANCE);
	}

	public void setMaxDistance(float maxDistance) {
		getDataTracker().set(MAX_DISTANCE, maxDistance);
	}

	public static void register(EntityType<PeaEntity> entityType) {

	}
}
