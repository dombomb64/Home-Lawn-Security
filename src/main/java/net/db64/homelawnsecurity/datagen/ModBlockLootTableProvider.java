package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
	public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
		super(dataOutput);
	}

	@Override
	public void generate() {
		addDrop(ModBlocks.GARDEN_BLOCK);

		addDrop(ModBlocks.FERTILE_PATH_BLOCK_1);
		addDrop(ModBlocks.FERTILE_PATH_BLOCK_2);
		addDrop(ModBlocks.FERTILE_PATH_BLOCK_CROSS);

		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_1);
		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_2);
		addDrop(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS);

		addDrop(ModBlocks.UNSODDED_LAWN_BLOCK);

		addDrop(ModBlocks.GARDEN_MARKER);

		addDrop(ModBlocks.FERTILE_PATH_MARKER_1);
		addDrop(ModBlocks.FERTILE_PATH_MARKER_2);
		addDrop(ModBlocks.FERTILE_PATH_MARKER_CROSS);

		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_1);
		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_2);
		addDrop(ModBlocks.ZOMBIE_PATH_MARKER_CROSS);

		addDrop(ModBlocks.UNSODDED_LAWN_MARKER);
	}
}
