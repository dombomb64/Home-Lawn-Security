package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
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

		getOrCreateTagBuilder(ModTags.Items.MARKERS)
			.add(ModBlocks.GARDEN_MARKER.asItem())
			.add(ModBlocks.GRAVEYARD_MARKER.asItem())
			.add(ModBlocks.LAWN_MARKER.asItem())
			.add(ModBlocks.ZOMBIE_PATH_MARKER_1.asItem())
			.add(ModBlocks.ZOMBIE_PATH_MARKER_2.asItem())
			.add(ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem())
			.add(ModBlocks.FERTILE_PATH_MARKER_1.asItem())
			.add(ModBlocks.FERTILE_PATH_MARKER_2.asItem())
			.add(ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem())
			.add(ModBlocks.UNSODDED_LAWN_MARKER.asItem());
	}
}
