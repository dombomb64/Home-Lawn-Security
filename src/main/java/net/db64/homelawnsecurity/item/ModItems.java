package net.db64.homelawnsecurity.item;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.item.custom.DavesPanItem;
import net.db64.homelawnsecurity.item.custom.LawnSeedPacketItem;
import net.db64.homelawnsecurity.item.custom.ZombieSeedPacketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
	public static final Item PEASHOOTER_SPAWN_EGG = registerItem("peashooter_spawn_egg",
		new SpawnEggItem(ModEntities.Plant.PEASHOOTER, 0xD7FF37, 0x3BC10E, new FabricItemSettings()));
	public static final Item BASIC_ZOMBIE_SPAWN_EGG = registerItem("basic_zombie_spawn_egg",
			new SpawnEggItem(ModEntities.Zombie.BASIC_ZOMBIE, 0x6D7858, 0x4A341D, new FabricItemSettings()));

	//public static final Item DAVES_PAN = registerItem("daves_pan",
		//new ArmorItem(ModArmorMaterials.DAVES_PAN, ArmorItem.Type.HELMET, new FabricItemSettings()));
	public static final Item DAVES_PAN = registerItem("daves_pan",
		new DavesPanItem(new FabricItemSettings()));



	public static final Item SEED_PACKET_PEASHOOTER = registerItem("seed_packet_peashooter",
		new LawnSeedPacketItem(ModEntities.Plant.PEASHOOTER, 150, new FabricItemSettings()));



	public static final Item SEED_PACKET_BASIC_ZOMBIE = registerItem("seed_packet_basic_zombie",
		new ZombieSeedPacketItem(ModEntities.Zombie.BASIC_ZOMBIE, 150, new FabricItemSettings()));

	public static final Item SEED_PACKET_CONEHEAD_ZOMBIE = registerItem("seed_packet_conehead_zombie",
		new ZombieSeedPacketItem(ModEntities.Zombie.CONEHEAD_ZOMBIE, 600, new FabricItemSettings()));

	private static Item registerItem(String name, Item item) {
		HomeLawnSecurity.LOGGER.debug("Registering item " + HomeLawnSecurity.MOD_ID + ":" + name);
		return Registry.register(Registries.ITEM, new Identifier(HomeLawnSecurity.MOD_ID, name), item);
	}

	public static void registerModItems() {
		HomeLawnSecurity.LOGGER.debug("Registering items for Home Lawn Security (" + HomeLawnSecurity.MOD_ID + ")");

		HomeLawnSecurity.LOGGER.debug("Registering vanilla item groups for Home Lawn Security (" + HomeLawnSecurity.MOD_ID + ")");

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(VanillaItemGroups::addItemsToBuildingBlocksItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(VanillaItemGroups::addItemsToColoredBlocksItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(VanillaItemGroups::addItemsToNaturalBlocksItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(VanillaItemGroups::addItemsToFunctionalBlocksItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(VanillaItemGroups::addItemsToRedstoneBlocksItemGroup);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(VanillaItemGroups::addItemsToToolsAndUtilitiesItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(VanillaItemGroups::addItemsToCombatItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(VanillaItemGroups::addItemsToFoodAndDrinksItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(VanillaItemGroups::addItemsToIngredientsItemGroup);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(VanillaItemGroups::addItemsToSpawnEggsItemGroup);

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.OPERATOR).register(VanillaItemGroups::addItemsToOperatorUtilitiesItemGroup);
	}

	private static class VanillaItemGroups {
		public static void addItemsToBuildingBlocksItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToColoredBlocksItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToNaturalBlocksItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToFunctionalBlocksItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToRedstoneBlocksItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToToolsAndUtilitiesItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToCombatItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToFoodAndDrinksItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToIngredientsItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToSpawnEggsItemGroup(FabricItemGroupEntries entries) {
		}

		public static void addItemsToOperatorUtilitiesItemGroup(FabricItemGroupEntries entries) {
		}
	}
}
