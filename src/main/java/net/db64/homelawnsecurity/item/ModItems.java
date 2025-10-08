package net.db64.homelawnsecurity.item;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.component.*;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModItems {
	public static final MutableText OBSOLETE = Text.translatable("tooltip.item.homelawnsecurity.obsolete")
		.setStyle(Style.EMPTY.withColor(Formatting.RED).withBold(true));

	public static final Item PEASHOOTER_SPAWN_EGG = register("peashooter_spawn_egg",
		SpawnEggItem::new,
		new Item.Settings().spawnEgg(ModEntities.Plant.PEASHOOTER));
	public static final Item BASIC_ZOMBIE_SPAWN_EGG = register("basic_zombie_spawn_egg",
		SpawnEggItem::new,
		new Item.Settings().spawnEgg(ModEntities.Zombie.BASIC_ZOMBIE));

	//public static final Item DAVES_PAN = registerItem("daves_pan",
		//new ArmorItem(ModArmorMaterials.DAVES_PAN, ArmorItem.Type.HELMET, new FabricItemSettings()));
	public static final Item DAVES_PAN = register("daves_pan", settings ->
		new DavesPanItem(settings), new Item.Settings().component(DataComponentTypes.EQUIPPABLE, EquippableComponent.builder(EquipmentSlot.HEAD).equipSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON).damageOnHurt(false).build()), List.of(
			Text.translatable("tooltip.item.homelawnsecurity.daves_pan"),
			Text.translatable("tooltip.item.homelawnsecurity.daves_pan.warning").setStyle(Style.EMPTY.withColor(Formatting.YELLOW))));

	public static final Item SUN = register("sun", settings ->
		new CurrencyItem(settings, SeedPacketItem.SUN_BAG_PREDICATE), new Item.Settings().component(ModDataComponentTypes.CURRENCY,
		new CurrencyComponent(25, "sun")));
	public static final Item BRAINPOWER = register("brainpower", settings ->
		new CurrencyItem(settings, SeedPacketItem.BRAINPOWER_BAG_PREDICATE), new Item.Settings().component(ModDataComponentTypes.CURRENCY,
		new CurrencyComponent(25, "brainpower")));

	public static final Item BAG_OF_SUN = register("bag_of_sun", settings ->
		new BagOfCurrencyItem(settings), new Item.Settings().maxCount(1).component(ModDataComponentTypes.BAG_OF_CURRENCY,
		new BagOfCurrencyComponent(0, "sun")));
	public static final Item BAG_OF_BRAINPOWER = register("bag_of_brainpower", settings ->
		new BagOfCurrencyItem(settings), new Item.Settings().maxCount(1).component(ModDataComponentTypes.BAG_OF_CURRENCY,
		new BagOfCurrencyComponent(0, "brainpower")));

	public static final Item LAWN_MOWER = register("lawn_mower", settings ->
		new LawnMowerItem(ModEntities.Other.LAWN_MOWER, settings), new Item.Settings());
	public static final Item TARGET = register("target", settings ->
		new TargetItem(ModEntities.Other.TARGET_ZOMBIE, settings), new Item.Settings(), List.of(
			Text.translatable("tooltip.item.homelawnsecurity.target")));

	public static final Item SHOVEL = register("shovel", settings ->
		new Item(settings), new Item.Settings().maxCount(1).component(ModDataComponentTypes.SHOVEL, Unit.INSTANCE), List.of(
			Text.translatable("tooltip.item.homelawnsecurity.shovel.use"),
			Text.translatable("tooltip.item.homelawnsecurity.shovel.attack")));
	public static final Item LAWN_GADGET = register("lawn_gadget", settings ->
		new LawnGadgetItem(settings), new Item.Settings().maxCount(1)
		.component(ModDataComponentTypes.LAWN_GADGET, new LawnGadgetComponent("turf_toggle")), List.of(
			Text.translatable("tooltip.item.homelawnsecurity.lawn_gadget.use"),
			Text.translatable("tooltip.item.homelawnsecurity.lawn_gadget.switch")));

	public static final Item TURF = register("turf", settings ->
		new TurfItem(settings), new Item.Settings(), List.of(
		Text.translatable("tooltip.item.homelawnsecurity.turf")));

	public static final Item BRAIN = register("brain", settings ->
		new Item(settings), new Item.Settings(), List.of(
		Text.translatable("tooltip.item.homelawnsecurity.brain")));



	public static final Item SEED_PACKET_SUNFLOWER = register("seed_packet_sunflower", settings ->
		new LawnSeedPacketItem(ModEntities.Plant.SUNFLOWER, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(50, 150)));

	public static final Item SEED_PACKET_PEASHOOTER = register("seed_packet_peashooter", settings ->
		new LawnSeedPacketItem(ModEntities.Plant.PEASHOOTER, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(100, 150)));

	public static final Item SEED_PACKET_WALL_NUT = register("seed_packet_wall_nut", settings ->
		new PathSeedPacketItem(ModEntities.Plant.WALL_NUT, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(50, 600)));



	public static final Item SEED_PACKET_ZOMBIE_GRAVESTONE = register("seed_packet_zombie_gravestone", settings ->
		new ZombieSeedPacketItem(ModEntities.Zombie.ZOMBIE_GRAVESTONE, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(25, 150)));

	public static final Item SEED_PACKET_BASIC_ZOMBIE = register("seed_packet_basic_zombie", settings ->
		new ZombieSeedPacketItem(ModEntities.Zombie.BASIC_ZOMBIE, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(25, 150)));

	public static final Item SEED_PACKET_CONEHEAD_ZOMBIE = register("seed_packet_conehead_zombie", settings ->
		new ZombieSeedPacketItem(ModEntities.Zombie.CONEHEAD_ZOMBIE, settings), new Item.Settings()
		.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(75, 600)));

	public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
		HomeLawnSecurity.LOGGER.debug("Registering item with key {}", key.getValue());

		return Items.register(key, factory, settings);
	}

	public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
		HomeLawnSecurity.LOGGER.debug("Registering item {}:{}", HomeLawnSecurity.MOD_ID, id);

		return Items.register(keyOf(id), factory, settings);
	}

	public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings, @Nullable List<Text> tooltip) {
		return register(id, factory, settings.component(ModDataComponentTypes.TOOLTIP, new TooltipComponent(tooltip)));
	}

	/*public static Item register(Block block, Item.Settings settings, @Nullable List<Text> tooltip) {
		if (tooltip != null)
			settings = settings.component(ModDataComponentTypes.TOOLTIP, new TooltipComponent(tooltip));

		return register(block.getRegistryEntry().registryKey(), settings1 -> new BlockItem(block, settings1), settings.useBlockPrefixedTranslationKey());
	}*/

	public static Item register(Block block) {
		return register(block, BlockItem::new);
	}

	public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory) {
		return register(block, factory, new Item.Settings());
	}

	public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
		return register(
			keyOf(block.getRegistryEntry().registryKey()), itemSettings -> (Item)factory.apply(block, itemSettings), settings.useBlockPrefixedTranslationKey()
		);
	}

	private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
		return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
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
