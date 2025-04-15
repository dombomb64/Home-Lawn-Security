package net.db64.homelawnsecurity.entity.custom.projectile;

import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;

public class PeaEntity extends ProjectileEntity implements IPvzEntity {
	public float maxDistance;

	public PeaEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		this.maxDistance = 16f;
	}

	public PeaEntity(LivingEntity livingEntity, World world, float maxDistance) {
		this(livingEntity.getX(), livingEntity.getEyeY() - (double) 0.1f, livingEntity.getZ(), world, maxDistance);
	}

	public PeaEntity(double x, double y, double z, World world, float maxDistance) {
		super(ModEntities.Projectile.PEA, world);
		this.setPosition(x, y, z);
		this.maxDistance = maxDistance;
	}

	@Override
	protected boolean canHit(Entity entity) {
		return entity instanceof ZombieEntity && super.canHit(entity);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {

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
		if (distanceTraveled <= maxDistance) // Bro why doesn't this variable work with projectiles
			return 0d;
		return 0.08d;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		if (entity.getWorld() instanceof ClientWorld)
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
					blockState.onEntityCollision(this.getWorld(), blockPos, this);
				}
			}
		}
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		nbt.putFloat("max_distance", maxDistance);
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		if (nbt.contains("max_distance", NbtElement.FLOAT_TYPE)) {
			this.maxDistance = nbt.getFloat("max_distance");
		}
	}
}
