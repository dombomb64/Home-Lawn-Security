package net.db64.homelawnsecurity.entity;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.custom.other.*;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.entity.custom.plant.SunflowerEntity;
import net.db64.homelawnsecurity.entity.custom.plant.WallNutEntity;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ConeheadZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ZombieGravestoneEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {
	public static class Other {
		public static final EntityType<CurrencyEntity> CURRENCY = register(
			"currency",
			EntityType.Builder.<CurrencyEntity>create(CurrencyEntity::new, SpawnGroup.MISC)
				.dimensions(1.0f, 1.0f));
		public static final EntityType<LawnMowerEntity> LAWN_MOWER = register(
			"lawn_mower",
			EntityType.Builder.<LawnMowerEntity>create(LawnMowerEntity::new, SpawnGroup.MISC)
				.dimensions(0.75f, 0.5f));
		public static final EntityType<TargetZombieEntity> TARGET_ZOMBIE = register(
			"target_zombie",
			EntityType.Builder.create(TargetZombieEntity::new, SpawnGroup.MISC)
				.dimensions(0.8f, 1.8f));
		public static final EntityType<PlantSeedPacketPathfindingEntity> PLANT_SEED_PACKET_PATHFINDING = register(
			"plant_seed_packet_pathfinding",
			EntityType.Builder.<PlantSeedPacketPathfindingEntity>create(PlantSeedPacketPathfindingEntity::new, SpawnGroup.MISC)
				.dimensions(0.5f, 0.5f));
		public static final EntityType<ZombieSeedPacketPathfindingEntity> ZOMBIE_SEED_PACKET_PATHFINDING = register(
			"zombie_seed_packet_pathfinding",
			EntityType.Builder.<ZombieSeedPacketPathfindingEntity>create(ZombieSeedPacketPathfindingEntity::new, SpawnGroup.MISC)
				.dimensions(0.5f, 0.5f));
	}

	public static class Projectile {
		public static final EntityType<PeaEntity> PEA = register(
			"pea",
			EntityType.Builder.<PeaEntity>create(PeaEntity::new, SpawnGroup.MISC)
				.dimensions(0.25f, 0.25f));
	}



	public static class Plant {
		public static final EntityType<SunflowerEntity> SUNFLOWER = register(
			"sunflower",
			EntityType.Builder.create(SunflowerEntity::new, SpawnGroup.MISC)
				.dimensions(0.8f, 1.25f)
		);

		public static final EntityType<PeashooterEntity> PEASHOOTER = register(
			"peashooter",
			EntityType.Builder.create(PeashooterEntity::new, SpawnGroup.MISC)
				.dimensions(0.8f, 1.25f)
		);

		public static final EntityType<WallNutEntity> WALL_NUT = register(
			"wall_nut",
			EntityType.Builder.create(WallNutEntity::new, SpawnGroup.MISC)
				.dimensions(0.8f, 1.25f)
		);
	}



	public static class Zombie {
		public static final EntityType<ZombieGravestoneEntity> ZOMBIE_GRAVESTONE = register(
			"zombie_gravestone",
			EntityType.Builder.create(ZombieGravestoneEntity::new, SpawnGroup.MISC)
				.dimensions(0.8f, 1.25f));

		public static final EntityType<BasicZombieEntity> BASIC_ZOMBIE = register(
			"basic_zombie",
			EntityType.Builder.create(BasicZombieEntity::new, SpawnGroup.MISC)
				.dimensions(0.8f, 1.8f));

		public static final EntityType<ConeheadZombieEntity> CONEHEAD_ZOMBIE = register(
			"conehead_zombie",
			EntityType.Builder.create(ConeheadZombieEntity::new, SpawnGroup.MISC)
				.dimensions(0.8f, 1.8f));
	}



	private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
		return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
	}

	private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
		return register(keyOf(id), type);
	}

	private static RegistryKey<EntityType<?>> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(HomeLawnSecurity.MOD_ID, id));
	}
}
