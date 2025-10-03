package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.BlockTags;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(WrapperLookup arg) {
		getTagBuilder(ModTags.Blocks.ZOMBIE_GOAL)
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_BLOCK));

		getTagBuilder(ModTags.Blocks.ZOMBIE_START)
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_BLOCK));

		/*getTagBuilder(ModTags.Blocks.ZOMBIE_PATH_1)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_1));

		getTagBuilder(ModTags.Blocks.ZOMBIE_PATH_2)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_2));

		getTagBuilder(ModTags.Blocks.ZOMBIE_PATH_CROSS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_CROSS));

		getTagBuilder(ModTags.Blocks.ZOMBIE_PLACEABLE)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS);*/



		getTagBuilder(ModTags.Blocks.ZOMBIE_GOAL_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_MARKER));

		getTagBuilder(ModTags.Blocks.ZOMBIE_START_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_MARKER));

		/*getTagBuilder(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_1));

		getTagBuilder(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_2));

		getTagBuilder(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_CROSS));

		getTagBuilder(ModTags.Blocks.ZOMBIE_PLACEABLE_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_START_MARKERS);*/



		/*getTagBuilder(ModTags.Blocks.TARGET_ZOMBIE_PLACEABLE)
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_BLOCK));

		getTagBuilder(ModTags.Blocks.TARGET_ZOMBIE_PLACEABLE_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_MARKER));*/



		/*getTagBuilder(ModTags.Blocks.LAWN_MOWER_GOAL)
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_BLOCK));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_START)
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_BLOCK));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_1)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_1));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_2)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_2));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_CROSS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_CROSS));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PLACEABLE)
			//.addTag(ModTags.Blocks.LAWN_MOWER_START);
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_BLOCK));*/



		/*getTagBuilder(ModTags.Blocks.LAWN_MOWER_GOAL_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_MARKER));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_START_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_MARKER));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_1_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_1));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_2_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_2));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PATH_CROSS_MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_CROSS));

		getTagBuilder(ModTags.Blocks.LAWN_MOWER_PLACEABLE_MARKERS)
			//.addTag(ModTags.Blocks.LAWN_MOWER_START_MARKERS);
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_MARKER));*/



		/*getTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_LAWN) // Examples: Peashooter, Sunflower, Fume-shroom
			.add(Registries.BLOCK.getId(ModBlocks.LAWN_BLOCK))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_CROSS));

		getTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_PATH) // Examples: Wall-nut, Garlic, Pumpkin
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS);*/



		/*getTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_LAWN_MARKERS) // Examples: Peashooter, Sunflower, Fume-shroom
			.add(Registries.BLOCK.getId(ModBlocks.LAWN_MARKER))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_CROSS));

		getTagBuilder(ModTags.Blocks.PLANT_PLACEABLE_PATH_MARKERS) // Examples: Wall-nut, Garlic, Pumpkin
			.addTag(ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_2_MARKERS)
			.addTag(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);*/



		getTagBuilder(ModTags.Blocks.MARKERS)
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_MARKER))
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_MARKER))
			/*.add(Registries.BLOCK.getId(ModBlocks.LAWN_MARKER))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_1))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_2))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.UNSODDED_LAWN_MARKER))*/
			.add(Registries.BLOCK.getId(ModBlocks.SODDED_LAWN_MARKER))
			.add(Registries.BLOCK.getId(ModBlocks.UNSODDED_LAWN_MARKER));



		//getTagBuilder(BlockTags.AXE_MINEABLE)
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));

		//getTagBuilder(BlockTags.HOE_MINEABLE)
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));

		getTagBuilder(BlockTags.PICKAXE_MINEABLE)
			.add(Registries.BLOCK.getId(ModBlocks.SUN_SPAWNER))
			.add(Registries.BLOCK.getId(ModBlocks.BRAINPOWER_BEACON));

		getTagBuilder(BlockTags.SHOVEL_MINEABLE)
			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_BLOCK))
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_BLOCK))
			/*.add(Registries.BLOCK.getId(ModBlocks.LAWN_BLOCK))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_BLOCK_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_1))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_2))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.UNSODDED_LAWN_BLOCK))*/
			.add(Registries.BLOCK.getId(ModBlocks.SODDED_LAWN_BLOCK))
			.add(Registries.BLOCK.getId(ModBlocks.UNSODDED_LAWN_BLOCK))

			.add(Registries.BLOCK.getId(ModBlocks.GARDEN_MARKER))
			.add(Registries.BLOCK.getId(ModBlocks.GRAVEYARD_MARKER))
			/*.add(Registries.BLOCK.getId(ModBlocks.LAWN_MARKER))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_1))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_2))
			.add(Registries.BLOCK.getId(ModBlocks.FERTILE_PATH_MARKER_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_1))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_2))
			.add(Registries.BLOCK.getId(ModBlocks.ZOMBIE_PATH_MARKER_CROSS))
			.add(Registries.BLOCK.getId(ModBlocks.UNSODDED_LAWN_MARKER))*/
			.add(Registries.BLOCK.getId(ModBlocks.SODDED_LAWN_MARKER))
			.add(Registries.BLOCK.getId(ModBlocks.UNSODDED_LAWN_MARKER));

		//getTagBuilder(FabricMineableTags.SHEARS_MINEABLE)
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));

		//getTagBuilder(FabricMineableTags.SWORD_MINEABLE)
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));

		//getTagBuilder(BlockTags.NEEDS_STONE_TOOL)
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));

		//getTagBuilder(BlockTags.NEEDS_IRON_TOOL)
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));

		//getTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));

		//getTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
			//.add(Registries.BLOCK.getId(ModBlocks.RUBBERWOOD_PLANKS));
	}
}
