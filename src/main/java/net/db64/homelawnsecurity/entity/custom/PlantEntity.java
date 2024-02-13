package net.db64.homelawnsecurity.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public abstract class PlantEntity extends PathAwareEntity {
	protected PlantEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void pushAwayFrom(Entity entity) {
		// i am jus--just sitting here :)
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		// ahaha--*snap*
	}
}
