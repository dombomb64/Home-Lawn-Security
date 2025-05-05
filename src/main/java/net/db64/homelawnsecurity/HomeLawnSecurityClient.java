package net.db64.homelawnsecurity;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.client.other.CurrencyRenderer;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.client.other.LawnMowerModel;
import net.db64.homelawnsecurity.entity.client.other.LawnMowerRenderer;
import net.db64.homelawnsecurity.entity.client.plant.*;
import net.db64.homelawnsecurity.entity.client.projectile.PeaModel;
import net.db64.homelawnsecurity.entity.client.projectile.PeaRenderer;
import net.db64.homelawnsecurity.entity.client.zombie.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;

public class HomeLawnSecurityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		/*Item[] otherMarkers = ClientWorld.BLOCK_MARKER_ITEMS.toArray(new Item[0]);
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
		ClientWorld.BLOCK_MARKER_ITEMS = Set.of(ArrayUtils.addAll(otherMarkers, modMarkers));*/

		//ParticleFactoryRegistry.getInstance().register(ModParticles.MARKER, new MarkerParticle.Factory());

		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SODDED_LAWN_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.UNSODDED_LAWN_BLOCK, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SUN_SPAWNER, RenderLayer.getCutout());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BRAINPOWER_BEACON, RenderLayer.getCutout());

		registerOther();
		registerProjectiles();
		registerPlants();
		registerZombies();
	}

	private void registerOther() {
		EntityRendererRegistry.register(ModEntities.Other.CURRENCY, CurrencyRenderer::new);

		EntityRendererRegistry.register(ModEntities.Other.LAWN_MOWER, LawnMowerRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_MOWER, LawnMowerModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.Other.TARGET_ZOMBIE, TargetZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.TARGET_ZOMBIE, TargetZombieModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.Other.PLANT_SEED_PACKET_PATHFINDING, EmptyEntityRenderer::new);

		EntityRendererRegistry.register(ModEntities.Other.ZOMBIE_SEED_PACKET_PATHFINDING, EmptyEntityRenderer::new);

		//BlockEntityRendererRegistry.register(, CurrencyRenderer::new);
		//EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_BLOCK, LawnMowerModel::getTexturedModelData);
		//EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_BLOCK_MAIN_PATH, LawnMowerModel::getTexturedModelData);
		//EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_BLOCK_INTERSECTING_PATH, LawnMowerModel::getTexturedModelData);
	}

	private void registerProjectiles() {
		EntityRendererRegistry.register(ModEntities.Projectile.PEA, PeaRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Projectile.PEA, PeaModel::getTexturedModelData);
	}

	private void registerPlants() {
		EntityRendererRegistry.register(ModEntities.Plant.SUNFLOWER, SunflowerRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.SUNFLOWER, SunflowerModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.Plant.PEASHOOTER, PeashooterRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.PEASHOOTER, PeashooterModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.Plant.WALL_NUT, WallNutRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.WALL_NUT, WallNutModel::getTexturedModelData);
	}

	private void registerZombies() {
		EntityRendererRegistry.register(ModEntities.Zombie.ZOMBIE_GRAVESTONE, ZombieGravestoneRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.ZOMBIE_GRAVESTONE, ZombieGravestoneModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.ZOMBIE_GRAVESTONE_CRACKS, ZombieGravestoneModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.Zombie.BASIC_ZOMBIE, BasicZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.BASIC_ZOMBIE, BasicZombieModel::getTexturedModelData);

		EntityRendererRegistry.register(ModEntities.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieModel::getTexturedModelData);
	}
}
