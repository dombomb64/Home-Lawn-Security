package net.db64.homelawnsecurity;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.block.entity.ModBlockEntities;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.component.TooltipComponent;
import net.db64.homelawnsecurity.debug.ModDebugSubscriptionTypes;
import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.db64.homelawnsecurity.entity.custom.other.PlantSeedPacketPathfindingEntity;
import net.db64.homelawnsecurity.entity.custom.other.ZombieSeedPacketPathfindingEntity;
import net.db64.homelawnsecurity.entity.custom.plant.ChomperEntity;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.entity.custom.plant.SunflowerEntity;
import net.db64.homelawnsecurity.entity.custom.plant.WallNutEntity;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BucketheadZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ConeheadZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.TargetZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ZombieGravestoneEntity;
import net.db64.homelawnsecurity.item.ModItemGroups;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.networking.ChangeEntityStateS2CPayload;
import net.db64.homelawnsecurity.networking.SnapLookDirS2CPayload;
import net.db64.homelawnsecurity.util.ModGameEvents;
import net.db64.homelawnsecurity.util.ModLootTableModifiers;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HomeLawnSecurity implements ModInitializer {
	public static final String MOD_ID = "homelawnsecurity";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("if you see this read sunny side skies");

		ModItemGroups.registerItemGroups();

		ModItems.registerItems();
		ModBlocks.registerBlocks();
		ModBlockEntities.registerBlockEntities();
		ModDataComponentTypes.registerDataComponentTypes();
		ModDamageTypes.registerDamageTypes();
		ModGameEvents.registerGameEvents();
		ModDebugSubscriptionTypes.registerDebugSubscriptionTypes();

		registerPackets();

		ModLootTableModifiers.modifyLootTables();
		addVanillaItemComponents();
		addWanderingTraderOffers();

		registerOther();
		registerProjectiles();
		registerPlants();
		registerZombies();
	}

	private void registerPackets() {
		PayloadTypeRegistry.playS2C().register(SnapLookDirS2CPayload.ID, SnapLookDirS2CPayload.CODEC);
		PayloadTypeRegistry.playS2C().register(ChangeEntityStateS2CPayload.ID, ChangeEntityStateS2CPayload.CODEC);
		//PayloadTypeRegistry.playS2C().register(InitAnimationsS2CPayload.ID, InitAnimationsS2CPayload.CODEC);
	}

	private void addVanillaItemComponents() {
		DefaultItemComponentEvents.MODIFY.register(context -> {
			context.modify(Items.BONE_MEAL, builder -> {
				builder.add(ModDataComponentTypes.TOOLTIP, new TooltipComponent(List.of(
					Text.translatable("tooltip.item.homelawnsecurity.bone_meal")
				)));
			});
		});

		DefaultItemComponentEvents.MODIFY.register(context -> {
			context.modify(Items.SHEARS, builder -> {
				builder.add(ModDataComponentTypes.TOOLTIP, new TooltipComponent(List.of(
					Text.translatable("tooltip.item.homelawnsecurity.shears")
				)));
			});
		});
	}

	private void addWanderingTraderOffers() {
		TradeOfferHelper.registerWanderingTraderOffers(factories -> {
			int price = 2;
			int count = 1;
			int maxUses = 5;

			factories.pool(Identifier.of(HomeLawnSecurity.MOD_ID, "plant_seed_packets"), 3,
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_PEASHOOTER, price, count, maxUses, 1),
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_SUNFLOWER, price, count, maxUses, 1),
				//new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_CHERRY_BOMB, price, count, maxUses, 1),
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_WALL_NUT, price, count, maxUses, 1),
				//new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_POTATO_MINE, price, count, maxUses, 1),
				//new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_SNOW_PEA, price, count, maxUses, 1),
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_CHOMPER, price, count, maxUses, 1)
			);

			factories.pool(Identifier.of(HomeLawnSecurity.MOD_ID, "zombie_seed_packets"), 3,
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_ZOMBIE_GRAVESTONE, price, count, maxUses, 1),
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_BASIC_ZOMBIE, price, count, maxUses, 1),
				//new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_TRASH_CAN_ZOMBIE, price, count, maxUses, 1),
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_CONEHEAD_ZOMBIE, price, count, maxUses, 1),
				//new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_POLE_VAULTING_ZOMBIE, price, count, maxUses, 1),
				new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_BUCKETHEAD_ZOMBIE, price, count, maxUses, 1)
				//new TradeOffers.SellItemFactory(ModItems.SEED_PACKET_FLAG_ZOMBIE, price, count, maxUses, 1)
			);
		});
	}

	private void registerOther() {
		FabricDefaultAttributeRegistry.register(ModEntities.Other.LAWN_MOWER, LawnMowerEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Other.TARGET_ZOMBIE, TargetZombieEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Other.PLANT_SEED_PACKET_PATHFINDING, PlantSeedPacketPathfindingEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Other.ZOMBIE_SEED_PACKET_PATHFINDING, ZombieSeedPacketPathfindingEntity.createAttributes());
	}

	private void registerProjectiles() {
		PeaEntity.register(ModEntities.Projectile.PEA);
	}

	private void registerPlants() {
		FabricDefaultAttributeRegistry.register(ModEntities.Plant.SUNFLOWER, SunflowerEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Plant.PEASHOOTER, PeashooterEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Plant.WALL_NUT, WallNutEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Plant.CHOMPER, ChomperEntity.createAttributes());
	}

	private void registerZombies() {
		FabricDefaultAttributeRegistry.register(ModEntities.Zombie.ZOMBIE_GRAVESTONE, ZombieGravestoneEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Zombie.BASIC_ZOMBIE, BasicZombieEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Zombie.CONEHEAD_ZOMBIE, ConeheadZombieEntity.createAttributes());

		FabricDefaultAttributeRegistry.register(ModEntities.Zombie.BUCKETHEAD_ZOMBIE, BucketheadZombieEntity.createAttributes());
	}

	/**
	 * A shortcut to log a line when running modded registry functions.
	 * <li>If the game is being run by the IDE, an <b>info</b> line will be logged.</li>
	 * <li>Otherwise, a <b>debug</b> line will be logged as to not flood as much.</li>
	 * @param registryType The plural form of the kind of registry you are using.
	 *                     For instance, "items", "blocks", "entity types", etc.
	 */
	public static void logRegistration(String registryType) {
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			HomeLawnSecurity.LOGGER.info("Registering {} for Home Lawn Security ({})", registryType, MOD_ID);
		}
		else {
			HomeLawnSecurity.LOGGER.debug("Registering {} for Home Lawn Security ({})", registryType, MOD_ID);
		}
	}
}