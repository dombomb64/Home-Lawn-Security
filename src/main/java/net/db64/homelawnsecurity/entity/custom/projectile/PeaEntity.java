package net.db64.homelawnsecurity.entity.custom.projectile;

import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class PeaEntity extends ProjectileEntity {
	public PeaEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public PeaEntity(LivingEntity livingEntity, World world) {
		super(ModEntities.Projectile.PEA, world);
	}

	@Override
	protected boolean canHit(Entity entity) {
		return entity instanceof ZombieEntity && super.canHit(entity);
	}

	@Override
	protected void initDataTracker() {

	}
}
