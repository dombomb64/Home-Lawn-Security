package net.db64.homelawnsecurity;

import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.client.plant.PeashooterModel;
import net.db64.homelawnsecurity.entity.client.plant.PeashooterRenderer;
import net.db64.homelawnsecurity.entity.client.projectile.PeaRenderer;
import net.db64.homelawnsecurity.entity.client.zombie.BasicZombieModel;
import net.db64.homelawnsecurity.entity.client.zombie.BasicZombieRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HomeLawnSecurityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.Projectile.PEA, PeaRenderer::new);

		registerPlants();
		registerZombies();
	}

	private void registerPlants() {
		EntityRendererRegistry.register(ModEntities.Plant.PEASHOOTER, PeashooterRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.PEASHOOTER, PeashooterModel::getTexturedModelData);
	}

	private void registerZombies() {
		EntityRendererRegistry.register(ModEntities.Zombie.BASIC_ZOMBIE, BasicZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.BASIC_ZOMBIE, BasicZombieModel::getTexturedModelData);
	}
}
