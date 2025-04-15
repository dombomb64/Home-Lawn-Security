package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_START)
			.add(ModBlocks.GRAVEYARD_BLOCK);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_1)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_1)
			.add(ModBlocks.FERTILE_PATH_BLOCK_1);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_2)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_2)
			.add(ModBlocks.FERTILE_PATH_BLOCK_2);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_CROSS)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS)
			.add(ModBlocks.FERTILE_PATH_BLOCK_CROSS);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PLACEABLE)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS);



		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_GOAL_MARKERS)
			.add(ModBlocks.GARDEN_MARKER);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_START_MARKERS)
			.add(ModBlocks.GRAVEYARD_MARKER);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_1)
			.add(ModBlocks.FERTILE_PATH_MARKER_1);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_2)
			.add(ModBlocks.FERTILE_PATH_MARKER_2);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_CROSS)
			.add(ModBlocks.FERTILE_PATH_MARKER_CROSS);

		getOrCreateTagBuilder(ModTags.Blocks.ZOMBIE_PLACEABLE_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_START_MARKERS);



		getOrCreateTagBuilder(ModTags.Blocks.TARGET_ZOMBIE_PLACEABLE)
			.add(ModBlocks.GRAVEYARD_BLOCK);

		getOrCreateTagBuilder(ModTags.Blocks.TARGET_ZOMBIE_PLACEABLE_MARKERS)
			.add(ModBlocks.GRAVEYARD_MARKER);



		/*getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_GOAL)
			.add(ModBlocks.GRAVEYARD_BLOCK);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_START)
			.add(ModBlocks.GARDEN_BLOCK);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_1)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_1)
			.add(ModBlocks.FERTILE_PATH_BLOCK_1);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_2)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_2)
			.add(ModBlocks.FERTILE_PATH_BLOCK_2);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_CROSS)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS)
			.add(ModBlocks.FERTILE_PATH_BLOCK_CROSS);*/

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PLACEABLE)
			//.addTag(ModTags.Blocks.LAWN_MOWER_START);
			.add(ModBlocks.GARDEN_BLOCK);



		/*getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_GOAL_MARKERS)
			.add(ModBlocks.GRAVEYARD_MARKER);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_START_MARKERS)
			.add(ModBlocks.GARDEN_MARKER);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_1_MARKERS)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_1)
			.add(ModBlocks.FERTILE_PATH_MARKER_1);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_2_MARKERS)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_2)
			.add(ModBlocks.FERTILE_PATH_MARKER_2);

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_CROSS_MARKERS)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_CROSS)
			.add(ModBlocks.FERTILE_PATH_MARKER_CROSS);*/

		getOrCreateTagBuilder(ModTags.Blocks.LAWN_MOWER_PLACEABLE_MARKERS)
			//.addTag(ModTags.Blocks.LAWN_MOWER_START_MARKERS);
			.add(ModBlocks.GARDEN_MARKER);



		getOrCreateTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_LAWN) // Examples: Peashooter, Sunflower, Fume-shroom
			.add(ModBlocks.LAWN_BLOCK)
			.add(ModBlocks.FERTILE_PATH_BLOCK_1)
			.add(ModBlocks.FERTILE_PATH_BLOCK_2)
			.add(ModBlocks.FERTILE_PATH_BLOCK_CROSS);

		getOrCreateTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_PATH) // Examples: Wall-nut, Garlic, Pumpkin
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS);



		getOrCreateTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_LAWN_MARKERS) // Examples: Peashooter, Sunflower, Fume-shroom
			.add(ModBlocks.LAWN_MARKER)
			.add(ModBlocks.FERTILE_PATH_MARKER_1)
			.add(ModBlocks.FERTILE_PATH_MARKER_2)
			.add(ModBlocks.FERTILE_PATH_MARKER_CROSS);

		getOrCreateTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_PATH_MARKERS) // Examples: Wall-nut, Garlic, Pumpkin
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);



		getOrCreateTagBuilder(ModTags.Blocks.MARKERS)
			.add(ModBlocks.GARDEN_MARKER)
			.add(ModBlocks.GRAVEYARD_MARKER)
			.add(ModBlocks.LAWN_MARKER)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_1)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_2)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_CROSS)
			.add(ModBlocks.FERTILE_PATH_MARKER_1)
			.add(ModBlocks.FERTILE_PATH_MARKER_2)
			.add(ModBlocks.FERTILE_PATH_MARKER_CROSS)
			.add(ModBlocks.UNSODDED_LAWN_MARKER);



		//getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.HOE_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		//getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
			//.add(ModBlocks.RUBBERWOOD_PLANKS);

		getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
			.add(ModBlocks.GARDEN_BLOCK)
			.add(ModBlocks.GRAVEYARD_BLOCK)
			.add(ModBlocks.LAWN_BLOCK)
			.add(ModBlocks.FERTILE_PATH_BLOCK_1)
			.add(ModBlocks.FERTILE_PATH_BLOCK_2)
			.add(ModBlocks.FERTILE_PATH_BLOCK_CROSS)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_1)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_2)
			.add(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS)
			.add(ModBlocks.UNSODDED_LAWN_BLOCK)

			.add(ModBlocks.GARDEN_MARKER)
			.add(ModBlocks.GRAVEYARD_MARKER)
			.add(ModBlocks.LAWN_MARKER)
			.add(ModBlocks.FERTILE_PATH_MARKER_1)
			.add(ModBlocks.FERTILE_PATH_MARKER_2)
			.add(ModBlocks.FERTILE_PATH_MARKER_CROSS)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_1)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_2)
			.add(ModBlocks.ZOMBIE_PATH_MARKER_CROSS)
			.add(ModBlocks.UNSODDED_LAWN_MARKER);

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
