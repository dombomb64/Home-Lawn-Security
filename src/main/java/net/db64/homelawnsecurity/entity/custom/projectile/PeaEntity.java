package net.db64.homelawnsecurity.entity.custom.projectile;

import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class PeaEntity extends PlantProjectileEntity {
	public PeaEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
	}

	public PeaEntity(LivingEntity livingEntity, World world, float maxDistance) {
		this(livingEntity.getX(), livingEntity.getEyeY() - (double) 0.1f, livingEntity.getZ(), world, maxDistance);
	}

	public PeaEntity(double x, double y, double z, World world, float maxDistance) {
		super(ModEntities.Projectile.PEA, x, y, z, world, maxDistance);
	}

	public static void register(EntityType<PeaEntity> entityType) {

	}

	@Override
	public SoundEvent getHitSound() {
		return ModSounds.ENTITY_PEA_HIT;
	}

	@Override
	public RegistryKey<DamageType> getDamageType() {
		return ModDamageTypes.PEA;
	}
}
