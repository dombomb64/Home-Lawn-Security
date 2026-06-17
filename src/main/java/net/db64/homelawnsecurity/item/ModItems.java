package net.db64.homelawnsecurity.item;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.component.*;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.item.custom.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
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

	public static final Item DUMMY_ITEM = register("dummy_item",
		Item::new, new Item.Settings().component(ModDataComponentTypes.TOOLTIP, new TooltipComponent(List.of(
			Text.translatable("tooltip.item.homelawnsecurity.dummy_item")))));

	public static final Item PEASHOOTER_SPAWN_EGG = register("peashooter_spawn_egg",
		SpawnEggItem::new,
		new Item.Settings().spawnEgg(ModEntities.Plant.PEASHOOTER).component(ModDataComponentTypes.TOOLTIP, new TooltipComponent(List.of(
			OBSOLETE))));
	public static final Item BASIC_ZOMBIE_SPAWN_EGG = register("basic_zombie_spawn_egg",
		SpawnEggItem::new,
		new Item.Settings().spawnEgg(ModEntities.Zombie.BASIC_ZOMBIE).component(ModDataComponentTypes.TOOLTIP, new TooltipComponent(List.of(
			OBSOLETE))));

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
		new Item(settings), new Item.Settings().maxCount(1).component(ModDataComponentTypes.SHOVEL, Unit.INSTANCE)
		/*.shovel(ToolMaterial.IRON, 1.5f, -3f)*/, List.of( // Lol oops it got durability
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



	public static final Item SEED_PACKET_SUNFLOWER =
		registerLawnSeedPacket("sunflower", ModEntities.Plant.SUNFLOWER, 50, 150);

	public static final Item SEED_PACKET_PEASHOOTER =
		registerLawnSeedPacket("peashooter", ModEntities.Plant.PEASHOOTER, 100, 150);

	public static final Item SEED_PACKET_WALL_NUT =
		registerPathSeedPacket("wall_nut", ModEntities.Plant.WALL_NUT, 50, 600);

	public static final Item SEED_PACKET_CHOMPER =
		registerPathSeedPacket("chomper", ModEntities.Plant.CHOMPER, 150, 150);



	public static final Item SEED_PACKET_ZOMBIE_GRAVESTONE =
		registerZombieSeedPacket("zombie_gravestone", ModEntities.Zombie.ZOMBIE_GRAVESTONE, 25, 150);

	public static final Item SEED_PACKET_BASIC_ZOMBIE =
		registerZombieSeedPacket("basic_zombie", ModEntities.Zombie.BASIC_ZOMBIE, 25, 150);

	public static final Item SEED_PACKET_CONEHEAD_ZOMBIE =
		registerZombieSeedPacket("conehead_zombie", ModEntities.Zombie.CONEHEAD_ZOMBIE, 75, 600);

	public static final Item SEED_PACKET_BUCKETHEAD_ZOMBIE =
		registerZombieSeedPacket("buckethead_zombie", ModEntities.Zombie.BUCKETHEAD_ZOMBIE, 100, 600);

	public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
		HomeLawnSecurity.LOGGER.debug("Registering item with key {}", key.getValue());

		return Items.register(key, factory, settings);
	}

	public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings) {
		HomeLawnSecurity.LOGGER.debug("Registering item {}:{}", HomeLawnSecurity.MOD_ID, id);

		return Items.register(keyOf(id), factory, settings);
	}

	public static Item register(String id, Function<Item.Settings, Item> factory, Item.Settings settings, @Nullable List<Text> tooltip) {
		if (tooltip == null)
			return register(id, factory, settings);
		else
			return register(id, factory, settings.component(ModDataComponentTypes.TOOLTIP, new TooltipComponent(tooltip)));
	}

	/*public static Item register(Block block, Item.Settings settings, @Nullable List<Text> tooltip) {
		if (tooltip != null)
			settings = settings.component(ModDataComponentTypes.TOOLTIP, new TooltipComponent(tooltip));

		return register(block.getRegistryEntry().registryKey(), settings1 -> new BlockItem(block, settings1), settings.useBlockPrefixedTranslationKey());
	}*/

	public static Item register(Block block, String id) {
		return register(block, id, BlockItem::new);
	}

	public static Item register(Block block, String id, BiFunction<Block, Item.Settings, Item> factory) {
		return register(block, id, factory, new Item.Settings());
	}

	public static Item register(Block block, String id, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
		return register(
			keyOf(id), itemSettings -> factory.apply(block, itemSettings), settings.useBlockPrefixedTranslationKey()
		);
	}

	public static Item registerLawnSeedPacket(String unitName, EntityType<? extends MobEntity> entityType, int cost, int cooldown) {
		return registerSeedPacket(unitName, cost, cooldown, "sun", settings ->
			new LawnSeedPacketItem(entityType, settings));
	}

	public static Item registerPathSeedPacket(String unitName, EntityType<? extends MobEntity> entityType, int cost, int cooldown) {
		return registerSeedPacket(unitName, cost, cooldown, "sun", settings ->
			new PathSeedPacketItem(entityType, settings));
	}

	public static Item registerZombieSeedPacket(String unitName, EntityType<? extends MobEntity> entityType, int cost, int cooldown) {
		return registerSeedPacket(unitName, cost, cooldown, "brainpower", settings ->
			new ZombieSeedPacketItem(entityType, settings));
	}

	public static Item registerSeedPacket(String unitName, int cost, int cooldown, String currencyName, Function<Item.Settings, Item> settingsFunction) {
		return register("seed_packet_" + unitName, settingsFunction, new Item.Settings()
			.component(ModDataComponentTypes.SEED_PACKET, new SeedPacketComponent(cost, cooldown, currencyName)));
	}

	private static RegistryKey<Item> keyOf(RegistryKey<Block> blockKey) {
		return RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
	}

	private static RegistryKey<Item> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(HomeLawnSecurity.MOD_ID, id));
	}

	public static void registerItems() {
		HomeLawnSecurity.logRegistration("items");

		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			HomeLawnSecurity.LOGGER.info("Modifying vanilla item groups for Home Lawn Security ({})", HomeLawnSecurity.MOD_ID);
		}
		else {
			HomeLawnSecurity.LOGGER.debug("Modifying vanilla item groups for Home Lawn Security ({})", HomeLawnSecurity.MOD_ID);
		}

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
