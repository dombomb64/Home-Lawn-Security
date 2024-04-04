package net.db64.homelawnsecurity;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.client.plant.PeashooterModel;
import net.db64.homelawnsecurity.entity.client.plant.PeashooterRenderer;
import net.db64.homelawnsecurity.entity.client.projectile.PeaModel;
import net.db64.homelawnsecurity.entity.client.projectile.PeaRenderer;
import net.db64.homelawnsecurity.entity.client.zombie.BasicZombieModel;
import net.db64.homelawnsecurity.entity.client.zombie.BasicZombieRenderer;
import net.db64.homelawnsecurity.entity.client.zombie.ConeheadZombieModel;
import net.db64.homelawnsecurity.entity.client.zombie.ConeheadZombieRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Set;
import java.util.stream.Stream;

public class HomeLawnSecurityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Item[] otherMarkers = ClientWorld.BLOCK_MARKER_ITEMS.toArray(new Item[0]);
		Item[] modMarkers = {
			ModBlocks.GARDEN_MARKER.asItem(),
			ModBlocks.LAWN_MARKER.asItem(),
			ModBlocks.FERTILE_PATH_MARKER_1.asItem(),
			ModBlocks.FERTILE_PATH_MARKER_2.asItem(),
			ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem(),
			ModBlocks.ZOMBIE_PATH_MARKER_1.asItem(),
			ModBlocks.ZOMBIE_PATH_MARKER_2.asItem(),
			ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem(),
			ModBlocks.UNSODDED_LAWN_MARKER.asItem()
		};
		ClientWorld.BLOCK_MARKER_ITEMS = Set.of(ArrayUtils.addAll(otherMarkers, modMarkers));

		registerProjectiles();
		registerPlants();
		registerZombies();
	}

	private void registerProjectiles() {
		EntityRendererRegistry.register(ModEntities.Projectile.PEA, PeaRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Projectile.PEA, PeaModel::getTexturedModelData);
	}

	private void registerPlants() {
		EntityRendererRegistry.register(ModEntities.Plant.PEASHOOTER, PeashooterRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.PEASHOOTER, PeashooterModel::getTexturedModelData);
	}

	private void registerZombies() {
		EntityRendererRegistry.register(ModEntities.Zombie.BASIC_ZOMBIE, BasicZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.BASIC_ZOMBIE, BasicZombieModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieModel::getTexturedModelData);
	}
}
