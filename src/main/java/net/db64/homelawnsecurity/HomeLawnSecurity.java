package net.db64.homelawnsecurity;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ConeheadZombieEntity;
import net.db64.homelawnsecurity.item.ModItemGroups;
import net.db64.homelawnsecurity.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.render.entity.EntityRenderers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeLawnSecurity implements ModInitializer {
	public static final String MOD_ID = "homelawnsecurity";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("if you see this read sunny side skies");

		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModDamageTypes.registerModDamageTypes();

		registerPlants();
		registerZombies();
	}

	private void registerPlants() {
		FabricDefaultAttributeRegistry.register(ModEntities.Plant.PEASHOOTER, PeashooterEntity.createAttributes());
	}

	private void registerZombies() {
		FabricDefaultAttributeRegistry.register(ModEntities.Zombie.BASIC_ZOMBIE, BasicZombieEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieEntity.createAttributes());
	}
}