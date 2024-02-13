package net.db64.homelawnsecurity.block;

import net.db64.homelawnsecurity.HomeLawnSecurity;
//import net.db64.homelawnsecurity.block.custom.*;
import net.db64.homelawnsecurity.item.ModItems;
//import net.db64.homelawnsecurity.sound.ModSounds;
//import net.db64.homelawnsecurity.world.ModConfiguredFeatures;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction.Axis;

import java.util.Optional;

public class ModBlocks {
	public static final Block GARDEN_BLOCK = registerBlock("garden_block", new Block(FabricBlockSettings.copyOf(Blocks.MOSS_BLOCK)));

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
