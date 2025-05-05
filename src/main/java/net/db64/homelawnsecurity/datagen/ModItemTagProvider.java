package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void configure(WrapperLookup arg) {
		getOrCreateTagBuilder(ModTags.Items.BAG_OF_SUN)
			.add(ModItems.BAG_OF_SUN);

		getOrCreateTagBuilder(ModTags.Items.BAG_OF_BRAINPOWER)
			.add(ModItems.BAG_OF_BRAINPOWER);

		getOrCreateTagBuilder(ModTags.Items.REVEALS_MARKERS)
			.add(ModBlocks.GARDEN_MARKER.asItem())
			.add(ModBlocks.GRAVEYARD_MARKER.asItem())
			/*.add(ModBlocks.LAWN_MARKER.asItem())
			.add(ModBlocks.ZOMBIE_PATH_MARKER_1.asItem())
			.add(ModBlocks.ZOMBIE_PATH_MARKER_2.asItem())
			.add(ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem())
			.add(ModBlocks.FERTILE_PATH_MARKER_1.asItem())
			.add(ModBlocks.FERTILE_PATH_MARKER_2.asItem())
			.add(ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem())
			.add(ModBlocks.UNSODDED_LAWN_MARKER.asItem())*/
			.add(ModBlocks.SODDED_LAWN_MARKER.asItem())
			.add(ModBlocks.UNSODDED_LAWN_MARKER.asItem());

		getOrCreateTagBuilder(ModTags.Items.REVEALS_MARKERS_WHILE_SNEAKING)
			.add(ModItems.LAWN_GADGET)
			.add(ModItems.TURF)
			.add(Items.SHEARS);

		getOrCreateTagBuilder(ModTags.Items.PLANT_FEEDABLE_FOR_DUPLICATE)
			.add(Items.BONE_MEAL);

		getOrCreateTagBuilder(ModTags.Items.ZOMBIE_FEEDABLE_FOR_DUPLICATE)
			.add(ModItems.BRAIN);

		getOrCreateTagBuilder(ModTags.Items.RECIPE_TURF_GRASS)
			.add(Items.SHORT_GRASS)
			.add(Items.TALL_GRASS)
			.add(Items.FERN)
			.add(Items.LARGE_FERN)
			.add(Items.SHORT_DRY_GRASS)
			.add(Items.TALL_DRY_GRASS)
			.add(Items.BUSH);

		getOrCreateTagBuilder(ModTags.Items.RECIPE_GRASSLESS_DIRT)
			.add(Items.DIRT)
			.add(Items.COARSE_DIRT)
			.add(Items.ROOTED_DIRT)
			.add(Items.PODZOL)
			.add(Items.MYCELIUM);
	}
}
