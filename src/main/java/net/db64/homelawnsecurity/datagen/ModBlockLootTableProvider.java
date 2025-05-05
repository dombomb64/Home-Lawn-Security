package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.block.custom.lawn.ILawnBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyStateLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
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

		/*addDrop(ModBlocks.FERTILE_PATH_BLOCK_1);
		addDrop(ModBlocks.FERTILE_PATH_BLOCK_2);
		addDrop(ModBlocks.FERTILE_PATH_BLOCK_CROSS);

		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_1);
		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_2);
		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS);

		addDrop(ModBlocks.UNSODDED_LAWN_BLOCK);*/

		addDrop(ModBlocks.SODDED_LAWN_BLOCK, lawnBlockDrops(ModBlocks.SODDED_LAWN_BLOCK));
		addDrop(ModBlocks.UNSODDED_LAWN_BLOCK, lawnBlockDrops(ModBlocks.UNSODDED_LAWN_BLOCK));

		addDrop(ModBlocks.GARDEN_MARKER);
		addDrop(ModBlocks.GRAVEYARD_MARKER);

		/*addDrop(ModBlocks.FERTILE_PATH_MARKER_1);
		addDrop(ModBlocks.FERTILE_PATH_MARKER_2);
		addDrop(ModBlocks.FERTILE_PATH_MARKER_CROSS);

		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_1);
		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_2);
		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_CROSS);

		addDrop(ModBlocks.UNSODDED_LAWN_MARKER);*/

		addDrop(ModBlocks.SODDED_LAWN_MARKER, lawnBlockDrops(ModBlocks.SODDED_LAWN_MARKER));
		addDrop(ModBlocks.UNSODDED_LAWN_MARKER, lawnBlockDrops(ModBlocks.UNSODDED_LAWN_MARKER));

		addDrop(ModBlocks.SUN_SPAWNER);
		addDrop(ModBlocks.BRAINPOWER_BEACON);
	}

	public LootTable.Builder lawnBlockDrops(Block drop) {
		return LootTable.builder()
			.pool(
				LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1f))
					.with(
						ItemEntry.builder(drop)
							.apply(CopyStateLootFunction.builder(drop)
								//.addProperty(ILawnBlock.TURF)
								.addProperty(ILawnBlock.PATH_ID_MAIN)
								.addProperty(ILawnBlock.PATH_ID_INTERSECTING)
								.conditionally(createSilkTouchCondition())
							)
					)
			);
	}
}
