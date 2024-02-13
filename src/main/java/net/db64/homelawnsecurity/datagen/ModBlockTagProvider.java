package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(WrapperLookup arg) {
		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_GOAL)
			.add(ModBlocks.GARDEN_BLOCK);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_1)
			.add(Blocks.COBBLESTONE);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_2)
			.add(Blocks.COBBLED_DEEPSLATE);

		//getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(FabricMineableTags.SHEARS_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(FabricMineableTags.SWORD_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
			//.add(ModBlocks.RUBBERWOOD_PLANKS);
	}
}
