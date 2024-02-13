package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.block.ModBlocks;
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
		/*getOrCreateTagBuilder(ModTags.Items.RUBBER_LOGS)
			.add(ModBlocks.RUBBER_LOG.asItem())
			.add(ModBlocks.RUBBER_WOOD.asItem())
			.add(ModBlocks.STRIPPED_RUBBER_LOG.asItem())
			.add(ModBlocks.STRIPPED_RUBBER_WOOD.asItem())
			.add(ModBlocks.DRIPPING_RUBBER_LOG.asItem())
			.add(ModBlocks.DRIPPING_RUBBER_WOOD.asItem());*/
	}
}
