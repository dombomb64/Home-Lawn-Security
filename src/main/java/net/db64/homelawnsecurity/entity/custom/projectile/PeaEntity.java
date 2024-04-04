package net.db64.homelawnsecurity.entity.custom.projectile;

import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PeaEntity extends ProjectileEntity {
	public float maxDistance;

	public PeaEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		this.maxDistance = 3f;
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
	protected void initDataTracker() {

	}


	@Override
	public void tick() {
		float h;
		super.tick();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
		boolean bl = false;
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			BlockState blockState = this.getWorld().getBlockState(blockPos);
			if (blockState.isOf(Blocks.NETHER_PORTAL)) {
				this.setInNetherPortal(blockPos);
				bl = true;
			} else if (blockState.isOf(Blocks.END_GATEWAY)) {
				BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
				if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
					EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
				}
				bl = true;
			}
		}
		if (hitResult.getType() != HitResult.Type.MISS && !bl) {
			this.onCollision(hitResult);
		}
		this.checkBlockCollision();
		Vec3d vec3d = this.getVelocity();
		double d = this.getX() + vec3d.x;
		double e = this.getY() + vec3d.y;
		double f = this.getZ() + vec3d.z;
		this.updateRotation();
		if (this.isTouchingWater()) {
			for (int i = 0; i < 4; ++i) {
				//float g = 0.25f; // ????????
				this.getWorld().addParticle(ParticleTypes.BUBBLE, d - vec3d.x * 0.25, e - vec3d.y * 0.25, f - vec3d.z * 0.25, vec3d.x, vec3d.y, vec3d.z);
			}
			h = 0.8f;
		} else {
			h = 0.99f;
		}
		this.setVelocity(vec3d.multiply(h));
		if (!this.hasNoGravity()) {
			Vec3d vec3d2 = this.getVelocity();
			this.setVelocity(vec3d2.x, vec3d2.y - (double)this.getGravity(), vec3d2.z);
		}
		Vec3d velocity = this.getVelocity();
		this.distanceTraveled += (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);
		this.setPosition(d, e, f);
	}

	protected float getGravity() {
		if (distanceTraveled <= 2) // Bro why doesn't this variable work with projectiles
			return 0f;
		return 0.08f;
	}

	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		Entity entity = entityHitResult.getEntity();
		entity.damage(this.getDamageSources().create(ModDamageTypes.PEA, this, this.getOwner()), 20);
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
}
