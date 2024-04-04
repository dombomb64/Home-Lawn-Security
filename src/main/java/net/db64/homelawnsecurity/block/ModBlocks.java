package net.db64.homelawnsecurity.block;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.custom.MarkerBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {
	public static final Block GARDEN_BLOCK = registerBlock("garden_block",
		new Block(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK)));

	public static final Block LAWN_BLOCK = registerBlock("lawn_block",
		new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.WART_BLOCK)));

	public static final Block FERTILE_PATH_BLOCK_1 = registerBlock("fertile_path_block_1",
		new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)));
	public static final Block FERTILE_PATH_BLOCK_2 = registerBlock("fertile_path_block_2",
		new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)));
	public static final Block FERTILE_PATH_BLOCK_CROSS = registerBlock("fertile_path_block_cross",
		new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.MUDDY_MANGROVE_ROOTS)));

	public static final Block ZOMBIE_PATH_BLOCK_1 = registerBlock("zombie_path_block_1",
		new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.ROOTED_DIRT)));
	public static final Block ZOMBIE_PATH_BLOCK_2 = registerBlock("zombie_path_block_2",
		new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.ROOTED_DIRT)));
	public static final Block ZOMBIE_PATH_BLOCK_CROSS = registerBlock("zombie_path_block_cross",
		new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.ROOTED_DIRT)));

	public static final Block UNSODDED_LAWN_BLOCK = registerBlock("unsodded_lawn_block",
		new Block(FabricBlockSettings.copyOf(Blocks.DIRT).sounds(BlockSoundGroup.GRAVEL)));

	public static final Block GARDEN_MARKER = registerBlock("garden_marker",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.MOSS_BLOCK).strength(-1.0f, 3600000.8f).nonOpaque()));

	public static final Block LAWN_MARKER = registerBlock("lawn_marker",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.NETHER_SPROUTS).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));

	public static final Block FERTILE_PATH_MARKER_1 = registerBlock("fertile_path_marker_1",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.HANGING_ROOTS).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));
	public static final Block FERTILE_PATH_MARKER_2 = registerBlock("fertile_path_marker_2",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.HANGING_ROOTS).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));
	public static final Block FERTILE_PATH_MARKER_CROSS = registerBlock("fertile_path_marker_cross",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.HANGING_ROOTS).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));

	public static final Block ZOMBIE_PATH_MARKER_1 = registerBlock("zombie_path_marker_1",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.HANGING_ROOTS).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));
	public static final Block ZOMBIE_PATH_MARKER_2 = registerBlock("zombie_path_marker_2",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.HANGING_ROOTS).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));
	public static final Block ZOMBIE_PATH_MARKER_CROSS = registerBlock("zombie_path_marker_cross",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.HANGING_ROOTS).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));

	public static final Block UNSODDED_LAWN_MARKER = registerBlock("unsodded_lawn_marker",
		new MarkerBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.MUD).replaceable().strength(-1.0f, 3600000.8f).nonOpaque()));

	private static Block registerBlock(String name, Block block, boolean obtainable) {
		if (obtainable)
			registerBlockItem(name, block);
		return Registry.register(Registries.BLOCK, new Identifier(HomeLawnSecurity.MOD_ID, name), block);
	}

	private static Block registerBlock(String name, Block block) {
		return registerBlock(name, block, true);
	}

	private static Item registerBlockItem(String name, Block block) {
		return Registry.register(Registries.ITEM, new Identifier(HomeLawnSecurity.MOD_ID, name),
			new BlockItem(block, new FabricItemSettings()));
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
