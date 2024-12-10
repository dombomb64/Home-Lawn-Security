package net.db64.homelawnsecurity.item;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.component.CurrencyComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.component.SeedPacketComponent;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {
	public static final Item PEASHOOTER_SPAWN_EGG = register("peashooter_spawn_egg", settings ->
		new SpawnEggItem(ModEntities.Plant.PEASHOOTER, settings), new Item.Settings());
	public static final Item BASIC_ZOMBIE_SPAWN_EGG = register("basic_zombie_spawn_egg", settings ->
		new SpawnEggItem(ModEntities.Zombie.BASIC_ZOMBIE, settings), new Item.Settings());

	//public static final Item DAVES_PAN = registerItem("daves_pan",
		//new ArmorItem(ModArmorMaterials.DAVES_PAN, ArmorItem.Type.HELMET, new FabricItemSettings()));
	public static final Item DAVES_PAN = register("daves_pan", settings ->
		new DavesPanItem(settings), new Item.Settings().component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.HEAD).equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON).damageOnHurt(false).build()));

	public static final Item SUN = register("sun", settings ->
		new CurrencyItem(settings, SeedPacketItem.SUN_BAG_PREDICATE), new Item.Settings().component(ModDataComponentTypes.CURRENCY,
		new CurrencyComponent(25, "sun")));
	public static final Item BRAINPOWER = register("brainpower", settings ->
		new CurrencyItem(settings, SeedPacketItem.BRAINPOWER_BAG_PREDICATE), new Item.Settings().component(ModDataComponentTypes.CURRENCY,
		new CurrencyComponent(25, "brainpower")));

	public static final Item BAG_OF_SUN = register("bag_of_sun", settings ->
		new BagOfCurrencyItem(settings), new Item.Settings().maxCount(1).component(ModDataComponentTypes.CURRENCY,
		new CurrencyComponent(0, "sun")));
	public static final Item BAG_OF_BRAINPOWER = register("bag_of_brainpower", settings ->
		new BagOfCurrencyItem(settings), new Item.Settings().maxCount(1).component(ModDataComponentTypes.CURRENCY,
		new CurrencyComponent(0, "brainpower")));

	public static final Item LAWN_MOWER = register("lawn_mower", settings ->
		new LawnMowerItem(ModEntities.Other.LAWN_MOWER, settings), new Item.Settings());



	public static final Item SEED_PACKET_PEASHOOTER = register("seed_packet_peashooter", settings ->
		new LawnSeedPacketItem(ModEntities.Plant.PEASHOOTER, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(100, 150)));



	public static final Item SEED_PACKET_BASIC_ZOMBIE = register("seed_packet_basic_zombie", settings ->
		new ZombieSeedPacketItem(ModEntities.Zombie.BASIC_ZOMBIE, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(25, 150)));

	public static final Item SEED_PACKET_CONEHEAD_ZOMBIE = register("seed_packet_conehead_zombie", settings ->
		new ZombieSeedPacketItem(ModEntities.Zombie.CONEHEAD_ZOMBIE, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(75, 600)));

	private static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
		HomeLawnSecurity.LOGGER.debug("Registering item " + HomeLawnSecurity.MOD_ID + ":" + id);

		return Items.register(keyOf(id), factory, settings);
	}

	private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(HomeLawnSecurity.MOD_ID, id));
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
