package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSingleton(ModBlocks.GARDEN_BLOCK, BlockModels.lawnBlock(ModBlocks.GARDEN_BLOCK), Models.CUBE_BOTTOM_TOP);

		blockStateModelGenerator.registerSingleton(ModBlocks.LAWN_BLOCK, BlockModels.lawnBlock(ModBlocks.LAWN_BLOCK), Models.CUBE_BOTTOM_TOP);

		blockStateModelGenerator.registerSingleton(ModBlocks.FERTILE_PATH_BLOCK_1, BlockModels.lawnBlock(ModBlocks.FERTILE_PATH_BLOCK_1), Models.CUBE_BOTTOM_TOP);
		blockStateModelGenerator.registerSingleton(ModBlocks.FERTILE_PATH_BLOCK_2, BlockModels.lawnBlock(ModBlocks.FERTILE_PATH_BLOCK_2), Models.CUBE_BOTTOM_TOP);
		blockStateModelGenerator.registerSingleton(ModBlocks.FERTILE_PATH_BLOCK_CROSS, BlockModels.lawnBlock(ModBlocks.FERTILE_PATH_BLOCK_CROSS), Models.CUBE_BOTTOM_TOP);

		blockStateModelGenerator.registerSingleton(ModBlocks.ZOMBIE_PATH_BLOCK_1, BlockModels.unsoddedPath(ModBlocks.ZOMBIE_PATH_BLOCK_1), Models.CUBE_BOTTOM_TOP);
		blockStateModelGenerator.registerSingleton(ModBlocks.ZOMBIE_PATH_BLOCK_2, BlockModels.unsoddedPath(ModBlocks.ZOMBIE_PATH_BLOCK_2), Models.CUBE_BOTTOM_TOP);
		blockStateModelGenerator.registerSingleton(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS, BlockModels.unsoddedPath(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS), Models.CUBE_BOTTOM_TOP);

		blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.UNSODDED_LAWN_BLOCK);

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.GARDEN_MARKER, ModBlocks.GARDEN_MARKER.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.LAWN_MARKER, ModBlocks.LAWN_MARKER.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_1, ModBlocks.FERTILE_PATH_MARKER_1.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_2, ModBlocks.FERTILE_PATH_MARKER_2.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_CROSS, ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_1, ModBlocks.ZOMBIE_PATH_MARKER_1.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_2, ModBlocks.ZOMBIE_PATH_MARKER_2.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_CROSS, ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.UNSODDED_LAWN_MARKER, ModBlocks.UNSODDED_LAWN_MARKER.asItem());

		/*blockStateModelGenerator.registerLog(ModBlocks.RUBBER_LOG).log(ModBlocks.RUBBER_LOG).wood(ModBlocks.RUBBER_WOOD);
		blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_RUBBER_LOG).log(ModBlocks.STRIPPED_RUBBER_LOG).wood(ModBlocks.STRIPPED_RUBBER_WOOD);
		blockStateModelGenerator.registerLog(ModBlocks.DRIPPING_RUBBER_LOG).log(ModBlocks.DRIPPING_RUBBER_LOG).wood(ModBlocks.DRIPPING_RUBBER_WOOD);

		BlockStateModelGenerator.BlockTexturePool rubberwoodPlanksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBBERWOOD_PLANKS);
		rubberwoodPlanksPool.stairs(ModBlocks.RUBBERWOOD_STAIRS);
		rubberwoodPlanksPool.slab(ModBlocks.RUBBERWOOD_SLAB);
		rubberwoodPlanksPool.fence(ModBlocks.RUBBERWOOD_FENCE);
		rubberwoodPlanksPool.fenceGate(ModBlocks.RUBBERWOOD_FENCE_GATE);
		blockStateModelGenerator.registerDoor(ModBlocks.RUBBERWOOD_DOOR);
		blockStateModelGenerator.registerTrapdoor(ModBlocks.RUBBERWOOD_TRAPDOOR);
		rubberwoodPlanksPool.pressurePlate(ModBlocks.RUBBERWOOD_PRESSURE_PLATE);
		rubberwoodPlanksPool.button(ModBlocks.RUBBERWOOD_BUTTON);

		blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.RUBBER_LEAVES);
		blockStateModelGenerator.registerTintableCrossBlockState(ModBlocks.RUBBER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
		blockStateModelGenerator.registerItemModel(ModBlocks.RUBBER_SAPLING); // So that the sapling is 2D in the inventory*/
	}

	private static class BlockModels {
		public static TextureMap lawnBlock(Block block) {
			return new TextureMap().put(TextureKey.SIDE, TextureMap.getSubId(ModBlocks.LAWN_BLOCK, "_side")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
		}

		public static TextureMap unsoddedPath(Block block) {
			return new TextureMap().put(TextureKey.SIDE, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK)).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
		}
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.PEASHOOTER_SPAWN_EGG,
			new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));

		itemModelGenerator.register(ModItems.BASIC_ZOMBIE_SPAWN_EGG,
			new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));

		itemModelGenerator.register(ModItems.DAVES_PAN, Models.GENERATED);

		itemModelGenerator.register(ModBlocks.GARDEN_MARKER.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.LAWN_MARKER.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_1.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_2.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_1.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_2.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.UNSODDED_LAWN_MARKER.asItem(), Models.GENERATED);



		itemModelGenerator.register(ModItems.SEED_PACKET_PEASHOOTER, Models.GENERATED);



		itemModelGenerator.register(ModItems.SEED_PACKET_BASIC_ZOMBIE, Models.GENERATED);

		itemModelGenerator.register(ModItems.SEED_PACKET_CONEHEAD_ZOMBIE, Models.GENERATED);
	}

	private static class ItemModels {
	}
}
