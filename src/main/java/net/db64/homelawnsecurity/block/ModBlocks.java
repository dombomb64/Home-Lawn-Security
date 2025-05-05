package net.db64.homelawnsecurity.block;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.custom.CurrencySpawnerBlock;
import net.db64.homelawnsecurity.block.custom.MarkerBlock;
import net.db64.homelawnsecurity.block.custom.lawn.SoddedLawnBlockBlock;
import net.db64.homelawnsecurity.block.custom.lawn.SoddedLawnMarkerBlock;
import net.db64.homelawnsecurity.block.custom.lawn.UnsoddedLawnBlockBlock;
import net.db64.homelawnsecurity.block.custom.lawn.UnsoddedLawnMarkerBlock;
import net.db64.homelawnsecurity.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

public class ModBlocks {
	public static final Block GARDEN_BLOCK = register("garden_block", Block::new,
		AbstractBlock.Settings.copyShallow(Blocks.MOSS_BLOCK), List.of(
			Text.translatable("tooltip.block.homelawnsecurity.garden")));
	public static final Block GRAVEYARD_BLOCK = register("graveyard_block", Block::new,
		AbstractBlock.Settings.copyShallow(Blocks.MOSS_BLOCK), List.of(
			Text.translatable("tooltip.block.homelawnsecurity.graveyard")));

	/*public static final Block LAWN_BLOCK = register("lawn_block", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.WART_BLOCK), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants")));

	public static final Block FERTILE_PATH_BLOCK_1 = register("fertile_path_block_1", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block FERTILE_PATH_BLOCK_2 = register("fertile_path_block_2", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block FERTILE_PATH_BLOCK_CROSS = register("fertile_path_block_cross", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));

	public static final Block ZOMBIE_PATH_BLOCK_1 = register("zombie_path_block_1", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.ROOTED_DIRT), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block ZOMBIE_PATH_BLOCK_2 = register("zombie_path_block_2", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.ROOTED_DIRT), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block ZOMBIE_PATH_BLOCK_CROSS = register("zombie_path_block_cross", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.ROOTED_DIRT), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));

	public static final Block UNSODDED_LAWN_BLOCK = register("unsodded_lawn_block", settings ->
		new Block(settings), AbstractBlock.Settings.copyShallow(Blocks.DIRT).sounds(BlockSoundGroup.GRAVEL), List.of(
			ModItems.OBSOLETE,
			Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
			Text.translatable("tooltip.block.homelawnsecurity.placement.none")));*/

	public static final Block SODDED_LAWN_BLOCK = register("sodded_lawn_block", SoddedLawnBlockBlock::new,
		AbstractBlock.Settings.copyShallow(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.WART_BLOCK), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.lawn_block")));
	public static final Block UNSODDED_LAWN_BLOCK = register("unsodded_lawn_block", UnsoddedLawnBlockBlock::new,
		AbstractBlock.Settings.copyShallow(Blocks.DIRT).sounds(BlockSoundGroup.GRAVEL), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.lawn_block")));

	public static final Block GARDEN_MARKER = register("garden_marker", MarkerBlock::new,
		AbstractBlock.Settings.create().sounds(BlockSoundGroup.MOSS_BLOCK).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.garden")));
	public static final Block GRAVEYARD_MARKER = register("graveyard_marker", MarkerBlock::new,
		AbstractBlock.Settings.create().sounds(BlockSoundGroup.MOSS_BLOCK).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.graveyard")));

	/*public static final Block LAWN_MARKER = register("lawn_marker", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.NETHER_SPROUTS).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants")));

	public static final Block FERTILE_PATH_MARKER_1 = register("fertile_path_marker_1", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.HANGING_ROOTS).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block FERTILE_PATH_MARKER_2 = register("fertile_path_marker_2", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.HANGING_ROOTS).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block FERTILE_PATH_MARKER_CROSS = register("fertile_path_marker_cross", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.HANGING_ROOTS).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.lawn_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));

	public static final Block ZOMBIE_PATH_MARKER_1 = register("zombie_path_marker_1", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.HANGING_ROOTS).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block ZOMBIE_PATH_MARKER_2 = register("zombie_path_marker_2", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.HANGING_ROOTS).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));
	public static final Block ZOMBIE_PATH_MARKER_CROSS = register("zombie_path_marker_cross", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.HANGING_ROOTS).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.path_plants"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.zombies")));

	public static final Block UNSODDED_LAWN_MARKER = register("unsodded_lawn_marker", settings ->
		new MarkerBlock(settings), AbstractBlock.Settings.create().sounds(BlockSoundGroup.MUD).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		ModItems.OBSOLETE,
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.allows"),
		Text.translatable("tooltip.block.homelawnsecurity.placement.none")));*/

	public static final Block SODDED_LAWN_MARKER = register("sodded_lawn_marker", SoddedLawnMarkerBlock::new,
		AbstractBlock.Settings.create().sounds(BlockSoundGroup.WART_BLOCK).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.lawn_block")));
	public static final Block UNSODDED_LAWN_MARKER = register("unsodded_lawn_marker", UnsoddedLawnMarkerBlock::new,
		AbstractBlock.Settings.create().sounds(BlockSoundGroup.MUD).strength(-1.0f, 3600000.8f).nonOpaque(), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.marker"),
		Text.translatable("tooltip.block.homelawnsecurity.lawn_block")));

	public static final Block SUN_SPAWNER = register("sun_spawner", settings ->
		new CurrencySpawnerBlock(settings, new ItemStack(ModItems.SUN)), AbstractBlock.Settings.copyShallow(Blocks.STONE).sounds(BlockSoundGroup.HEAVY_CORE).nonOpaque(), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.sun_spawner")));
	public static final Block BRAINPOWER_BEACON = register("brainpower_beacon", settings ->
		new CurrencySpawnerBlock(settings, new ItemStack(ModItems.BRAINPOWER)), AbstractBlock.Settings.copyShallow(Blocks.STONE).sounds(BlockSoundGroup.HEAVY_CORE).nonOpaque(), List.of(
		Text.translatable("tooltip.block.homelawnsecurity.brainpower_beacon")));

	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean obtainable, List<Text> tooltip)
	{
		Block block = Blocks.register(keyOf(id), factory, settings);
		if (obtainable)
			ModItems.register(id, itemSettings -> new BlockItem(block, itemSettings), new Item.Settings().useBlockPrefixedTranslationKey(), tooltip);
		return block;
	}

	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean obtainable)
	{
		return register(id, factory, settings, obtainable, null);
	}

	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
		return register(id, factory, settings, true);
	}

	private static Block register(String id, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, @Nullable List<Text> tooltip) {
		return register(id, factory, settings, true, tooltip);
	}

	/*private static Item registerBlockItem(String name, Block block) {
		return Registry.register(Registries.ITEM, RegistryKey.of(RegistryKeys.ITEM, Identifier.of(HomeLawnSecurity.MOD_ID, name)),
			new BlockItem(block, new Item.Settings()));
	}*/

	private static RegistryKey<Block> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(HomeLawnSecurity.MOD_ID, id));
	}

	public static void registerModBlocks() {
		HomeLawnSecurity.LOGGER.debug("Registering blocks for " + HomeLawnSecurity.MOD_ID);
	}

	public static class BlockSetTypes {
		//public static BlockSetType RUBBER_WOOD = new BlockSetType("rubber_wood", true, true, true, BlockSetType.ActivationRule.EVERYTHING, ModSounds.BlockSoundGroups.RUBBER_WOOD, ModSounds.RUBBERWOOD_DOOR_CLOSE, ModSounds.RUBBERWOOD_DOOR_OPEN, ModSounds.RUBBERWOOD_TRAPDOOR_CLOSE, ModSounds.RUBBERWOOD_TRAPDOOR_OPEN, ModSounds.RUBBERWOOD_PRESSURE_PLATE_CLICK_OFF, ModSounds.RUBBERWOOD_PRESSURE_PLATE_CLICK_ON, ModSounds.RUBBERWOOD_BUTTON_CLICK_OFF, ModSounds.RUBBERWOOD_BUTTON_CLICK_ON);
	}

	public static class WoodTypes {
		//public static WoodType RUBBER_WOOD = new WoodType("rubber_wood", BlockSetTypes.RUBBER_WOOD, ModSounds.BlockSoundGroups.RUBBER_WOOD, BlockSoundGroup.CHERRY_WOOD_HANGING_SIGN, ModSounds.RUBBERWOOD_FENCE_GATE_CLOSE, ModSounds.RUBBERWOOD_FENCE_GATE_OPEN);
	}
}
