package net.db64.homelawnsecurity.entity;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
	public static class Projectile {
		public static final EntityType<PeaEntity> PEA = Registry.register(Registries.ENTITY_TYPE,
			new Identifier(HomeLawnSecurity.MOD_ID, "pea"),
			FabricEntityTypeBuilder.<PeaEntity>create(SpawnGroup.MISC, PeaEntity::new)
				.dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
	}

	public static class Plant {
		public static final EntityType<PeashooterEntity> PEASHOOTER = Registry.register(Registries.ENTITY_TYPE,
			new Identifier(HomeLawnSecurity.MOD_ID, "peashooter"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, PeashooterEntity::new)
				.dimensions(EntityDimensions.fixed(0.8f, 1.8f)).build());
	}

	public static class Zombie {
		public static final EntityType<BasicZombieEntity> BASIC_ZOMBIE = Registry.register(Registries.ENTITY_TYPE,
			new Identifier(HomeLawnSecurity.MOD_ID, "basic_zombie"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, BasicZombieEntity::new)
				.dimensions(EntityDimensions.fixed(0.8f, 1.8f)).build());
	}
}
