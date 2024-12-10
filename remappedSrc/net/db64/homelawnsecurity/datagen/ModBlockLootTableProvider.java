package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
	public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		addDrop(ModBlocks.GARDEN_BLOCK);
		addDrop(ModBlocks.GRAVEYARD_BLOCK);

		addDrop(ModBlocks.FERTILE_PATH_BLOCK_1);
		addDrop(ModBlocks.FERTILE_PATH_BLOCK_2);
		addDrop(ModBlocks.FERTILE_PATH_BLOCK_CROSS);

		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_1);
		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_2);
		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS);

		addDrop(ModBlocks.UNSODDED_LAWN_BLOCK);

		addDrop(ModBlocks.GARDEN_MARKER);
		addDrop(ModBlocks.GRAVEYARD_MARKER);

		addDrop(ModBlocks.FERTILE_PATH_MARKER_1);
		addDrop(ModBlocks.FERTILE_PATH_MARKER_2);
		addDrop(ModBlocks.FERTILE_PATH_MARKER_CROSS);

		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_1);
		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_2);
		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_CROSS);

		addDrop(ModBlocks.UNSODDED_LAWN_MARKER);

		addDrop(ModBlocks.SUN_SPAWNER);
		addDrop(ModBlocks.BRAINPOWER_BEACON);
	}
}
