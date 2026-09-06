package net.db64.homelawnsecurity;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.debug.ModDebugSubscriptionTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.client.other.CurrencyRenderer;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.client.other.LawnMowerModel;
import net.db64.homelawnsecurity.entity.client.other.LawnMowerRenderer;
import net.db64.homelawnsecurity.entity.client.plant.*;
import net.db64.homelawnsecurity.entity.client.projectile.PeaModel;
import net.db64.homelawnsecurity.entity.client.projectile.PeaRenderer;
import net.db64.homelawnsecurity.entity.client.zombie.*;
import net.db64.homelawnsecurity.entity.custom.PathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.plant.ChomperEntity;
import net.db64.homelawnsecurity.networking.*;
import net.db64.homelawnsecurity.particle.ModParticles;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.debug.DebugHudEntries;
import net.minecraft.client.gui.hud.debug.RendererDebugHudEntry;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactories;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class HomeLawnSecurityClient implements ClientModInitializer {
	public static final Identifier DEBUG_PATHFINDING =
		DebugHudEntries.register(Identifier.of(HomeLawnSecurity.MOD_ID, "pvz_pathfinding"), new RendererDebugHudEntry(true));


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

		//ModDebugSubscriptionTypes.registerDebugSubscriptionTypes();
		//ModParticles.registerParticles();

		BlockRenderLayerMap.putBlock(ModBlocks.SODDED_LAWN_BLOCK, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.UNSODDED_LAWN_BLOCK, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.SUN_SPAWNER, BlockRenderLayer.CUTOUT);
		BlockRenderLayerMap.putBlock(ModBlocks.BRAINPOWER_BEACON, BlockRenderLayer.CUTOUT);

		registerPackets();

		registerOther();
		registerProjectiles();
		registerPlants();
		registerZombies();
	}

	private void registerPackets() {
		ClientPlayNetworking.registerGlobalReceiver(SnapLookDirS2CPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = context.client();

				if (client.world == null)
					return;

				Entity entity = client.world.getEntity(payload.uuid());
				if (entity == null)
					return;

				entity.setPitch(payload.pitch());
				entity.setYaw(payload.yaw());
				entity.lastPitch = payload.pitch();
				entity.lastYaw = payload.yaw();
				if (entity instanceof LivingEntity living) {
					living.setHeadYaw(payload.yaw());
					living.setBodyYaw(payload.yaw());
					living.lastHeadYaw = payload.yaw();
					living.lastBodyYaw = payload.yaw();
				}
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(ChangeEntityStateS2CPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = context.client();

				if (client.world == null)
					return;

				Entity entity = client.world.getEntity(payload.uuid());
				if (entity == null)
					return;

				if (entity instanceof ChomperEntity chomper) {
					chomper.setState(ChomperEntity.ChomperState.fromString(payload.state()));
				}
				// Add more cases for future state-based entities
			});
		});

		ClientPlayNetworking.registerGlobalReceiver(EntityDamagedS2CPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = context.client();

				if (client.world == null)
					return;

				Entity entity = client.world.getEntity(payload.uuid());
				if (entity == null)
					return;

				if (entity instanceof PathBoundEntity entity2) {
					entity2.lastAttackedByPvzEntity = payload.lastAttackedByPvzEntity();
					entity2.hurtFlashTime = payload.hurtFlashTime();
				}
			});
		});

		/*ClientPlayNetworking.registerGlobalReceiver(InitAnimationsS2CPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = context.client();

				if (client.world == null)
					return;

				Entity entity = client.world.getEntity(payload.uuid());
				if (entity == null)
					return;

				if (entity instanceof PlantEntity entity2) {
					entity2.initAnimations();
				}
				else if (entity instanceof ZombieEntity entity2) {
					entity2.initAnimations();
				}
			});
		});*/

		ClientPlayNetworking.registerGlobalReceiver(PlayerDestroyedMowerS2CPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient client = context.client();

				if (client.interactionManager == null)
					return;
				
				client.interactionManager.blockBreakingCooldown = 5;
			});
		});
	}

	private void registerOther() {
		EntityRendererFactories.register(ModEntities.Other.CURRENCY, CurrencyRenderer::new);

		EntityRendererFactories.register(ModEntities.Other.LAWN_MOWER, LawnMowerRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_MOWER, LawnMowerModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Other.TARGET_ZOMBIE, TargetZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.TARGET_ZOMBIE, TargetZombieModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Other.PLANT_SEED_PACKET_PATHFINDING, EmptyEntityRenderer::new);

		EntityRendererFactories.register(ModEntities.Other.ZOMBIE_SEED_PACKET_PATHFINDING, EmptyEntityRenderer::new);

		//BlockEntityRendererRegistry.register(, CurrencyRenderer::new);
		//EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_BLOCK, LawnMowerModel::getTexturedModelData);
		//EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_BLOCK_MAIN_PATH, LawnMowerModel::getTexturedModelData);
		//EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Other.LAWN_BLOCK_INTERSECTING_PATH, LawnMowerModel::getTexturedModelData);
	}

	private void registerProjectiles() {
		EntityRendererFactories.register(ModEntities.Projectile.PEA, PeaRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Projectile.PEA, PeaModel::getTexturedModelData);
	}

	private void registerPlants() {
		EntityRendererFactories.register(ModEntities.Plant.SUNFLOWER, SunflowerRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.SUNFLOWER, SunflowerModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Plant.PEASHOOTER, PeashooterRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.PEASHOOTER, PeashooterModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Plant.WALL_NUT, WallNutRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.WALL_NUT, WallNutModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Plant.CHOMPER, ChomperRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Plant.CHOMPER, ChomperModel::getTexturedModelData);
	}

	private void registerZombies() {
		EntityRendererFactories.register(ModEntities.Zombie.ZOMBIE_GRAVESTONE, ZombieGravestoneRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.ZOMBIE_GRAVESTONE, ZombieGravestoneModel::getTexturedModelData);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.ZOMBIE_GRAVESTONE_CRACKS, ZombieGravestoneModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Zombie.BASIC_ZOMBIE, BasicZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.BASIC_ZOMBIE, BasicZombieModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieModel::getTexturedModelData);

		EntityRendererFactories.register(ModEntities.Zombie.BUCKETHEAD_ZOMBIE, BucketheadZombieRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(ModModelLayers.Zombie.BUCKETHEAD_ZOMBIE, BucketheadZombieModel::getTexturedModelData);
	}
}
