package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.block.custom.lawn.ILawnBlock;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.model.EmptyItemModel;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		ModBlockModels.registerCubeBottomTop(
			blockStateModelGenerator,
			ModBlocks.GARDEN_BLOCK,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/garden_block_top"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/side_sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded")
		);
		ModBlockModels.registerCubeBottomTop(
			blockStateModelGenerator,
			ModBlocks.GRAVEYARD_BLOCK,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/graveyard_block_top"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/side_sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded")
		);

		/*ModBlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.LAWN_BLOCK);

		ModBlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.FERTILE_PATH_BLOCK_1);
		ModBlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.FERTILE_PATH_BLOCK_2);
		ModBlockModels.registerLawnBlock(blockStateModelGenerator, ModBlocks.FERTILE_PATH_BLOCK_CROSS);

		ModBlockModels.registerUnsoddedPathBlock(blockStateModelGenerator, ModBlocks.ZOMBIE_PATH_BLOCK_1);
		ModBlockModels.registerUnsoddedPathBlock(blockStateModelGenerator, ModBlocks.ZOMBIE_PATH_BLOCK_2);
		ModBlockModels.registerUnsoddedPathBlock(blockStateModelGenerator, ModBlocks.ZOMBIE_PATH_BLOCK_CROSS);

		blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.UNSODDED_LAWN_BLOCK);
		//blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.UNSODDED_LAWN_BLOCK);*/

		ModBlockModels.registerLawnBlockV2(
			blockStateModelGenerator,
			ModBlocks.SODDED_LAWN_BLOCK,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/top_sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/top_sodded_path"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/side_sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded")
		);
		ModBlockModels.registerLawnBlockV2(
			blockStateModelGenerator,
			ModBlocks.UNSODDED_LAWN_BLOCK,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/top_unsodded_path"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded")
		);

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.GRAVEYARD_MARKER, ModBlocks.GRAVEYARD_MARKER.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.GARDEN_MARKER, ModBlocks.GARDEN_MARKER.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.SODDED_LAWN_MARKER, ModBlocks.SODDED_LAWN_MARKER.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.UNSODDED_LAWN_MARKER, ModBlocks.UNSODDED_LAWN_MARKER.asItem());

		/*blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.LAWN_MARKER, ModBlocks.LAWN_MARKER.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_1, ModBlocks.FERTILE_PATH_MARKER_1.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_2, ModBlocks.FERTILE_PATH_MARKER_2.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.FERTILE_PATH_MARKER_CROSS, ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_1, ModBlocks.ZOMBIE_PATH_MARKER_1.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_2, ModBlocks.ZOMBIE_PATH_MARKER_2.asItem());
		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.ZOMBIE_PATH_MARKER_CROSS, ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem());

		blockStateModelGenerator.registerBuiltinWithParticle(ModBlocks.UNSODDED_LAWN_MARKER, ModBlocks.UNSODDED_LAWN_MARKER.asItem());*/

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

	private static class ModBlockModels {
		/*public static void registerLawnBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
			TextureMap textureMap = new TextureMap()
				.put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK))
				.put(TextureKey.TOP, TextureMap.getSubId(block, "_top"))
				.put(TextureKey.SIDE, TextureMap.getSubId(ModBlocks.LAWN_BLOCK, "_side"));
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, BlockStateModelGenerator.createWeightedVariant(Models.CUBE_BOTTOM_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector))));
		}

		public static void registerUnsoddedPathBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
			TextureMap textureMap = new TextureMap()
				.put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK))
				.put(TextureKey.TOP, TextureMap.getSubId(block, "_top"))
				.put(TextureKey.SIDE, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
			blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, BlockStateModelGenerator.createWeightedVariant(Models.CUBE_BOTTOM_TOP.upload(block, textureMap, blockStateModelGenerator.modelCollector))));
		}*/

		public static void registerCubeBottomTop(BlockStateModelGenerator generator, Block block, Identifier topTexture, Identifier sideTexture, Identifier bottomTexture) {
			TextureMap textureMap = new TextureMap()
				.put(TextureKey.TOP, topTexture)
				.put(TextureKey.SIDE, sideTexture)
				.put(TextureKey.BOTTOM, bottomTexture);
			generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, BlockStateModelGenerator.createWeightedVariant(Models.CUBE_BOTTOM_TOP.upload(block, textureMap, generator.modelCollector))));
		}



		public static final TextureKey textureKeyMainPath = TextureKey.of("main_path");
		public static final TextureKey textureKeyIntersectingPath = TextureKey.of("intersecting_path");
		public static final Model LAWN_BLOCK_V2 = new Model(Optional.of(Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block_v2")), Optional.empty(), TextureKey.BOTTOM, TextureKey.SIDE, TextureKey.TOP, textureKeyMainPath, textureKeyIntersectingPath);

		public static void registerLawnBlockV2(BlockStateModelGenerator generator, Block block, Identifier topTexture, Identifier topDarkTexture, Identifier sideTexture, Identifier bottomTexture) {
			Identifier noPath = Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/no_path");
			Identifier[][] identifiers = new Identifier[LawnUtil.getPathTypeAmount() + 1][LawnUtil.getPathTypeAmount() + 1];



			TextureMap textureMap = new TextureMap()
				.put(TextureKey.BOTTOM, bottomTexture)
				.put(TextureKey.SIDE, sideTexture);



			for (int mainPathId = 0; mainPathId <= LawnUtil.getPathTypeAmount(); mainPathId++) {
				Identifier mainPathNormalTexture = Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/main_path/normal_" + mainPathId);
				Identifier mainPathIntersectingTexture = Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/main_path/intersecting_" + mainPathId);



				for (int intersectingPathId = 0; intersectingPathId <= LawnUtil.getPathTypeAmount(); intersectingPathId++) {
					Identifier intersectingPathTexture = Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/intersecting_path/" + intersectingPathId);

					if (mainPathId == 0) {
						textureMap.put(TextureKey.TOP, topTexture)
							.put(textureKeyMainPath, noPath);
					}
					else {
						textureMap.put(TextureKey.TOP, topDarkTexture);
						if (intersectingPathId == 0) {
							textureMap.put(textureKeyMainPath, mainPathNormalTexture);
						} else {
							textureMap.put(textureKeyMainPath, mainPathIntersectingTexture);
						}
					}

					if (intersectingPathId == 0) {
						textureMap.put(textureKeyIntersectingPath, noPath);
					}
					else {
						textureMap.put(textureKeyIntersectingPath, intersectingPathTexture);
					}

					identifiers[mainPathId][intersectingPathId] = LAWN_BLOCK_V2.upload(block,
						"_" + mainPathId + "_" + intersectingPathId,
						textureMap,
						generator.modelCollector);
				}
			}

			generator.blockStateCollector
				.accept(
					VariantsBlockModelDefinitionCreator.of(block)
						.with(BlockStateVariantMap.models(ILawnBlock.PATH_ID_MAIN, ILawnBlock.PATH_ID_INTERSECTING)
							.generate((mainPath, intersectingPath)
								-> BlockStateModelGenerator.createWeightedVariant(identifiers[mainPath][intersectingPath])
							)
						)
				);
			//generator.registerParentedItemModel(block, identifiers[1][0][0]);

			/*generator.blockStateCollector
				.accept(
					VariantsBlockModelDefinitionCreator.of(block)
						.with(
							BlockStateVariantMap.models(turfProperty, mainPathIdProperty, intersectingPathIdProperty)
								.register(false, 0, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_0_0")))
								.register(false, 1, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_1_0")))
								.register(false, 2, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_2_0")))
								.register(false, 3, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_3_0")))
								.register(false, 4, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_4_0")))

								.register(false, 0, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_0_1")))
								.register(false, 1, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_1_1")))
								.register(false, 2, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_2_1")))
								.register(false, 3, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_3_1")))
								.register(false, 4, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_4_1")))

								.register(false, 0, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_0_2")))
								.register(false, 1, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_1_2")))
								.register(false, 2, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_2_2")))
								.register(false, 3, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_3_2")))
								.register(false, 4, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_4_2")))

								.register(false, 0, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_0_3")))
								.register(false, 1, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_1_3")))
								.register(false, 2, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_2_3")))
								.register(false, 3, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_3_3")))
								.register(false, 4, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_4_3")))

								.register(false, 0, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_0_4")))
								.register(false, 1, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_1_4")))
								.register(false, 2, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_2_4")))
								.register(false, 3, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_3_4")))
								.register(false, 4, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/false_4_4")))



								.register(true, 0, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_0_0")))
								.register(true, 1, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_1_0")))
								.register(true, 2, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_2_0")))
								.register(true, 3, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_3_0")))
								.register(true, 4, 0,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_4_0")))

								.register(true, 0, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_0_1")))
								.register(true, 1, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_1_1")))
								.register(true, 2, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_2_1")))
								.register(true, 3, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_3_1")))
								.register(true, 4, 1,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_4_1")))

								.register(true, 0, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_0_2")))
								.register(true, 1, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_1_2")))
								.register(true, 2, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_2_2")))
								.register(true, 3, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_3_2")))
								.register(true, 4, 2,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_4_2")))

								.register(true, 0, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_0_3")))
								.register(true, 1, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_1_3")))
								.register(true, 2, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_2_3")))
								.register(true, 3, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_3_3")))
								.register(true, 4, 3,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_4_3")))

								.register(true, 0, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_0_4")))
								.register(true, 1, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_1_4")))
								.register(true, 2, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_2_4")))
								.register(true, 3, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_3_4")))
								.register(true, 4, 4,
									BlockStateModelGenerator.createWeightedVariant(Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_block/true_4_4")))
						)
				);*/
		}

		/*public static final TexturedModel.Factory LAWN_BLOCK = makeFactory(TextureMap::all, Models.CUBE_BOTTOM_TOP);
		public static final TexturedModel.Factory UNSODDED_PATH = makeFactory(TextureMap::all, Models.CUBE_BOTTOM_TOP);


		public static TextureMap lawnBlock(Block block) {
			return new TextureMap().put(TextureKey.SIDE, TextureMap.getSubId(ModBlocks.LAWN_BLOCK, "_side")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
		}

		public static TextureMap unsoddedPath(Block block) {
			return new TextureMap().put(TextureKey.SIDE, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK)).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.BOTTOM, TextureMap.getId(ModBlocks.UNSODDED_LAWN_BLOCK));
		}

		public static TexturedModel.Factory makeFactory(Function<Block, TextureMap> texturesGetter, Model model) {
			return (block) -> {
				return new TexturedModel((TextureMap)texturesGetter.apply(block), model);
			};
		}*/
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		//itemModelGenerator.register(ModItems.PEASHOOTER_SPAWN_EGG,
			//new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
		//itemModelGenerator.registerSpawnEgg(ModItems.PEASHOOTER_SPAWN_EGG, 0xD7FF37, 0x3BC10E);

		//itemModelGenerator.register(ModItems.BASIC_ZOMBIE_SPAWN_EGG,
			//new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
		//itemModelGenerator.registerSpawnEgg(ModItems.BASIC_ZOMBIE_SPAWN_EGG, 0x6D7858, 0x4A341D);

		//ModItemModels.registerHeadgear(itemModelGenerator, ModItems.DAVES_PAN, , );

		itemModelGenerator.register(ModItems.BAG_OF_SUN, Models.GENERATED);
		itemModelGenerator.register(ModItems.BAG_OF_BRAINPOWER, Models.GENERATED);

		itemModelGenerator.register(ModItems.SUN, Models.GENERATED);
		itemModelGenerator.register(ModItems.BRAINPOWER, Models.GENERATED);

		itemModelGenerator.register(ModItems.LAWN_MOWER, Models.GENERATED);

		itemModelGenerator.register(ModItems.TARGET, Models.GENERATED);

		itemModelGenerator.register(ModItems.SHOVEL, Models.HANDHELD);
		itemModelGenerator.register(ModItems.LAWN_GADGET, Models.HANDHELD);

		itemModelGenerator.register(ModItems.TURF, Models.GENERATED);

		itemModelGenerator.register(ModItems.BRAIN, Models.GENERATED);

		itemModelGenerator.register(ModBlocks.GARDEN_MARKER.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.GRAVEYARD_MARKER.asItem(), Models.GENERATED);

		/*itemModelGenerator.register(ModBlocks.LAWN_MARKER.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_1.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_2.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_1.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_2.asItem(), Models.GENERATED);
		itemModelGenerator.register(ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem(), Models.GENERATED);

		itemModelGenerator.register(ModBlocks.UNSODDED_LAWN_MARKER.asItem(), Models.GENERATED);*/

		ModItemModels.registerLawnMarkerV2(
			itemModelGenerator,
			ModBlocks.SODDED_LAWN_MARKER,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/marker_sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/marker_sodded_path"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_marker/sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_marker/sodded_path")
		);
		ModItemModels.registerLawnMarkerV2(
			itemModelGenerator,
			ModBlocks.UNSODDED_LAWN_MARKER,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/marker_unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/marker_unsodded_path"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_marker/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_marker/unsodded_path")
		);

		ModItemModels.registerLawnBlockItemV2(
			itemModelGenerator,
			ModBlocks.SODDED_LAWN_BLOCK,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/top_sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/top_sodded_path"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/side_sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_block/sodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_block/sodded_path")
		);
		ModItemModels.registerLawnBlockItemV2(
			itemModelGenerator,
			ModBlocks.UNSODDED_LAWN_BLOCK,
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/top_unsodded_path"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_block/unsodded"),
			Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_block/unsodded_path")
		);



		HashMap<String, Identifier> seedPacketBases = new HashMap<>();
		{
			seedPacketBases.put("plantBase", ModItemModels.registerSeedPacketBase(itemModelGenerator, "plant"));
			seedPacketBases.put("zombieBase", ModItemModels.registerSeedPacketBase(itemModelGenerator, "zombie"));
			seedPacketBases.put("imitaterBase", ModItemModels.registerSeedPacketBase(itemModelGenerator, "imitater"));
			seedPacketBases.put("upgradeBase", ModItemModels.registerSeedPacketBase(itemModelGenerator, "upgrade"));
		}

		HashMap<String, Identifier> seedPacketImages = new HashMap<>();
		{
			seedPacketImages.put("peashooter", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "peashooter"));
			seedPacketImages.put("sunflower", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "sunflower"));
			seedPacketImages.put("cherryBomb", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "cherry_bomb"));
			seedPacketImages.put("wallNut", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "wall_nut"));
			seedPacketImages.put("potatoMine", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "potato_mine"));
			seedPacketImages.put("snowPea", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "snow_pea"));
			seedPacketImages.put("chomper", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "chomper"));
			seedPacketImages.put("repeater", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "repeater"));
			seedPacketImages.put("puffShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "puff_shroom"));
			seedPacketImages.put("sunShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "sun_shroom"));
			seedPacketImages.put("fumeShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "fume_shroom"));
			seedPacketImages.put("graveBuster", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "grave_buster"));
			seedPacketImages.put("hypnoShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "hypno_shroom"));
			seedPacketImages.put("scaredyShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "scaredy_shroom"));
			seedPacketImages.put("iceShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "ice_shroom"));
			seedPacketImages.put("doomShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "doom_shroom"));
			seedPacketImages.put("lilyPad", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "lily_pad"));
			seedPacketImages.put("squash", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "squash"));
			seedPacketImages.put("threepeater", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "threepeater"));
			seedPacketImages.put("tangleKelp", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "tangle_kelp"));
			seedPacketImages.put("jalapeno", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "jalapeno"));
			seedPacketImages.put("spikeweed", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "spikeweed"));
			seedPacketImages.put("torchwood", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "torchwood"));
			seedPacketImages.put("tallNut", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "tall_nut"));
			seedPacketImages.put("seaShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "sea_shroom"));
			seedPacketImages.put("plantern", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "plantern"));
			seedPacketImages.put("cactus", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "cactus"));
			seedPacketImages.put("blover", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "blover"));
			seedPacketImages.put("splitPea", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "split_pea"));
			seedPacketImages.put("starfruit", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "starfruit"));
			seedPacketImages.put("pumpkin", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "pumpkin"));
			seedPacketImages.put("magnetShroom", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "magnet_shroom"));
			seedPacketImages.put("cabbagePult", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "cabbage_pult"));
			seedPacketImages.put("flowerPot", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "flower_pot"));
			seedPacketImages.put("kernelPult", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "kernel_pult"));
			seedPacketImages.put("coffeeBean", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "coffee_bean"));
			seedPacketImages.put("garlic", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "garlic"));
			seedPacketImages.put("umbrellaLeaf", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "umbrella_leaf"));
			seedPacketImages.put("marigold", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "marigold"));
			seedPacketImages.put("melonPult", ModItemModels.registerSeedPacketImage(itemModelGenerator, "plant", "melon_pult"));

			seedPacketImages.put("zombieGravestone", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "zombie_gravestone"));
			seedPacketImages.put("basicZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "basic_zombie"));
			seedPacketImages.put("trashCanZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "trash_can_zombie"));
			seedPacketImages.put("coneheadZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "conehead_zombie"));
			seedPacketImages.put("poleVaultingZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "pole_vaulting_zombie"));
			seedPacketImages.put("bucketheadZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "buckethead_zombie"));
			seedPacketImages.put("flagZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "flag_zombie"));
			seedPacketImages.put("newspaperZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "newspaper_zombie"));
			seedPacketImages.put("screenDoorZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "screen_door_zombie"));
			seedPacketImages.put("footballZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "football_zombie"));
			seedPacketImages.put("dancingZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "dancing_zombie"));
			seedPacketImages.put("zomboni", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "zomboni"));
			seedPacketImages.put("jackInTheBoxZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "jack_in_the_box_zombie"));
			seedPacketImages.put("diggerZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "digger_zombie"));
			seedPacketImages.put("pogoZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "pogo_zombie"));
			seedPacketImages.put("bungeeZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "bungee_zombie"));
			seedPacketImages.put("ladderZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "ladder_zombie"));
			seedPacketImages.put("catapultZombie", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "catapult_zombie"));
			seedPacketImages.put("gargantuar", ModItemModels.registerSeedPacketImage(itemModelGenerator, "zombie", "gargantuar"));
		}

		HashMap<String, Identifier> seedPacketCosts = new HashMap<>();
		{
			seedPacketCosts.put("plant0", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "0"));
			seedPacketCosts.put("plant25", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "25"));
			seedPacketCosts.put("plant50", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "50"));
			seedPacketCosts.put("plant75", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "75"));
			seedPacketCosts.put("plant100", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "100"));
			seedPacketCosts.put("plant125", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "125"));
			seedPacketCosts.put("plant150", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "150"));
			seedPacketCosts.put("plant175", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "175"));
			seedPacketCosts.put("plant300", ModItemModels.registerSeedPacketCost(itemModelGenerator, "plant", "300"));

			seedPacketCosts.put("zombie25", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "25"));
			seedPacketCosts.put("zombie50", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "50"));
			seedPacketCosts.put("zombie75", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "75"));
			seedPacketCosts.put("zombie100", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "100"));
			seedPacketCosts.put("zombie125", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "125"));
			seedPacketCosts.put("zombie150", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "150"));
			seedPacketCosts.put("zombie175", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "175"));
			seedPacketCosts.put("zombie200", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "200"));
			seedPacketCosts.put("zombie225", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "225"));
			seedPacketCosts.put("zombie250", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "250"));
			seedPacketCosts.put("zombie300", ModItemModels.registerSeedPacketCost(itemModelGenerator, "zombie", "300"));
		}

		// Plant seed packets
		{
			ModItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_PEASHOOTER,
				seedPacketBases.get("plantBase"),
				seedPacketImages.get("peashooter"),
				seedPacketCosts.get("plant100")
			);

			ModItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_SUNFLOWER,
				seedPacketBases.get("plantBase"),
				seedPacketImages.get("sunflower"),
				seedPacketCosts.get("plant50")
			);

			ModItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_WALL_NUT,
				seedPacketBases.get("plantBase"),
				seedPacketImages.get("wallNut"),
				seedPacketCosts.get("plant50")
			);
		}

		// Zombie seed packets
		{
			ModItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_ZOMBIE_GRAVESTONE,
				seedPacketBases.get("zombieBase"),
				seedPacketImages.get("zombieGravestone"),
				seedPacketCosts.get("zombie50")
			);

			ModItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_BASIC_ZOMBIE,
				seedPacketBases.get("zombieBase"),
				seedPacketImages.get("basicZombie"),
				seedPacketCosts.get("zombie25")
			);

			ModItemModels.registerSeedPacket(itemModelGenerator, ModItems.SEED_PACKET_CONEHEAD_ZOMBIE,
				seedPacketBases.get("zombieBase"),
				seedPacketImages.get("coneheadZombie"),
				seedPacketCosts.get("zombie75")
			);
		}
	}

	private static class ModItemModels {
		public static final Model TEMPLATE_SEED_PACKET_BASE = item("template_seed_packet_base", TextureKey.LAYER0);
		public static final Model TEMPLATE_SEED_PACKET_IMAGE = item("template_seed_packet_image", TextureKey.LAYER0);
		public static final Model TEMPLATE_SEED_PACKET_COST = item("template_seed_packet_cost", TextureKey.LAYER0);

		private static Model item(String parent, TextureKey... requiredTextureKeys) {
			return new Model(Optional.of(Identifier.of(HomeLawnSecurity.MOD_ID, "item/" + parent)), Optional.empty(), requiredTextureKeys);
		}

		/*public static void registerHeadgear(ItemModelGenerator itemModelGenerator, Item item, Model model, ItemModel.Unbaked onHeadModel) {
			ItemModel.Unbaked unbaked = net.minecraft.client.data.ItemModels.select(
				new DisplayContextProperty(),
				net.minecraft.client.data.ItemModels.basic(itemModelGenerator.upload(item, model)),
				net.minecraft.client.data.ItemModels.switchCase(List.of(ModelTransformationMode.HEAD), onHeadModel)
			);
			itemModelGenerator.output.accept(item, unbaked);
		}*/

		public static void registerSeedPacket(ItemModelGenerator itemModelGenerator, Item item, Identifier baseModel, Identifier imageModel, Identifier costModel) {
			ItemModel.Unbaked unbaked = net.minecraft.client.data.ItemModels.composite(
				net.minecraft.client.data.ItemModels.basic(baseModel),
				net.minecraft.client.data.ItemModels.basic(imageModel),
				net.minecraft.client.data.ItemModels.basic(costModel)
			);

			itemModelGenerator.output.accept(item, unbaked);
		}

		public static Identifier registerSeedPacketBase(ItemModelGenerator itemModelGenerator, String base) {
			Identifier id = Identifier.of(HomeLawnSecurity.MOD_ID, "item/seed_packet/base/" + base);
			return Models.GENERATED.upload(id, TextureMap.layer0(id), itemModelGenerator.modelCollector);
		}

		public static Identifier registerSeedPacketImage(ItemModelGenerator itemModelGenerator, String team, String image) {
			Identifier id = Identifier.of(HomeLawnSecurity.MOD_ID, "item/seed_packet/image/" + team + "/" + image);
			return Models.GENERATED.upload(id, TextureMap.layer0(id), itemModelGenerator.modelCollector);
		}

		public static Identifier registerSeedPacketCost(ItemModelGenerator itemModelGenerator, String team, String cost) {
			Identifier id = Identifier.of(HomeLawnSecurity.MOD_ID, "item/seed_packet/cost/" + team + "/" + cost);
			return Models.GENERATED.upload(id, TextureMap.layer0(id), itemModelGenerator.modelCollector);
		}

		private static boolean generatedMarkerPathModels = false;

		public static void registerLawnMarkerV2(ItemModelGenerator generator, Block block, Identifier lawnTexture, Identifier lawnDarkTexture, Identifier lawnModel, Identifier lawnDarkModel) {
			Models.GENERATED.upload(lawnModel, TextureMap.layer0(lawnTexture), generator.modelCollector);
			Models.GENERATED.upload(lawnDarkModel, TextureMap.layer0(lawnDarkTexture), generator.modelCollector);

			ItemModel.Unbaked empty = new EmptyItemModel.Unbaked();

			HashMap<Integer, ItemModel.Unbaked> mainPathNormalModels = new HashMap<>();
			HashMap<Integer, ItemModel.Unbaked> mainPathIntersectingModels = new HashMap<>();
			HashMap<Integer, ItemModel.Unbaked> tempMap = new HashMap<>();
			for (int mainPathId = 1; mainPathId <= LawnUtil.getPathTypeAmount(); mainPathId++) {
				Identifier mainPathNormalModel = Identifier.of(HomeLawnSecurity.MOD_ID, "item/marker_path/main_path/normal_" + mainPathId);
				mainPathNormalModels.put(mainPathId, ItemModels.basic(mainPathNormalModel));
				if (!generatedMarkerPathModels)
					Models.GENERATED.upload(mainPathNormalModel, TextureMap.layer0(Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/main_path/normal_" + mainPathId)), generator.modelCollector);

				Identifier mainPathIntersectingModel = Identifier.of(HomeLawnSecurity.MOD_ID, "item/marker_path/main_path/intersecting_" + mainPathId);
				mainPathIntersectingModels.put(mainPathId, ItemModels.basic(mainPathIntersectingModel));
				if (!generatedMarkerPathModels)
					Models.GENERATED.upload(mainPathIntersectingModel, TextureMap.layer0(Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/main_path/intersecting_" + mainPathId)), generator.modelCollector);

				tempMap.put(mainPathId, ItemModels.basic(lawnDarkModel));
			}

			HashMap<Integer, ItemModel.Unbaked> intersectingPathModels = new HashMap<>();
			for (int intersectingPathId = 1; intersectingPathId <= LawnUtil.getPathTypeAmount(); intersectingPathId++) {
				Identifier intersectingPathModel = Identifier.of(HomeLawnSecurity.MOD_ID, "item/marker_path/intersecting_path/" + intersectingPathId);
				intersectingPathModels.put(intersectingPathId, ItemModels.basic(intersectingPathModel));
				if (!generatedMarkerPathModels)
					Models.GENERATED.upload(intersectingPathModel, TextureMap.layer0(Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/intersecting_path/" + intersectingPathId)), generator.modelCollector);
			}

			generatedMarkerPathModels = true;

			generator.output.accept(block.asItem(),
				ItemModels.composite(
					// Base
					ItemModels.select(
						ILawnBlock.PATH_ID_MAIN,
						ItemModels.basic(lawnModel),
						tempMap
					),
					// Main path
					ItemModels.select(
						ILawnBlock.PATH_ID_INTERSECTING,
						ItemModels.select(
							ILawnBlock.PATH_ID_MAIN,
							empty,
							mainPathIntersectingModels
						),
						Map.of(0, ItemModels.select(
							ILawnBlock.PATH_ID_MAIN,
							empty,
							mainPathNormalModels
						))
					),
					// Intersecting path
					ItemModels.select(
						ILawnBlock.PATH_ID_INTERSECTING,
						empty,
						intersectingPathModels
					)
				)
			);
		}

		private static boolean generatedBlockPathModels = false;

		public static void registerLawnBlockItemV2(ItemModelGenerator generator, Block block, Identifier topTexture, Identifier topDarkTexture, Identifier sideTexture, Identifier bottomTexture, Identifier lawnModel, Identifier lawnDarkModel) {
			Models.CUBE_BOTTOM_TOP.upload(lawnModel,
				new TextureMap()
					.put(TextureKey.TOP, topTexture)
					.put(TextureKey.SIDE, sideTexture)
					.put(TextureKey.BOTTOM, bottomTexture),
				generator.modelCollector);

			Models.CUBE_BOTTOM_TOP.upload(lawnDarkModel,
				new TextureMap()
					.put(TextureKey.TOP, topDarkTexture)
					.put(TextureKey.SIDE, sideTexture)
					.put(TextureKey.BOTTOM, bottomTexture),
				generator.modelCollector);

			ItemModel.Unbaked empty = new EmptyItemModel.Unbaked();

			HashMap<Integer, ItemModel.Unbaked> mainPathNormalModels = new HashMap<>();
			HashMap<Integer, ItemModel.Unbaked> mainPathIntersectingModels = new HashMap<>();
			HashMap<Integer, ItemModel.Unbaked> tempMap = new HashMap<>();
			for (int mainPathId = 1; mainPathId <= LawnUtil.getPathTypeAmount(); mainPathId++) {
				Identifier mainPathNormalModel = Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_block/main_path/normal_" + mainPathId);
				mainPathNormalModels.put(mainPathId, ItemModels.basic(mainPathNormalModel));
				if (!generatedBlockPathModels)
					Models.CUBE_TOP.upload(mainPathNormalModel,
						new TextureMap()
							.put(TextureKey.TOP, Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/main_path/normal_" + mainPathId))
							.put(TextureKey.SIDE, Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/no_path")),
						generator.modelCollector);

				Identifier mainPathIntersectingModel = Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_block/main_path/intersecting_" + mainPathId);
				mainPathIntersectingModels.put(mainPathId, ItemModels.basic(mainPathIntersectingModel));
				if (!generatedBlockPathModels)
					Models.CUBE_TOP.upload(mainPathIntersectingModel,
						new TextureMap()
							.put(TextureKey.TOP, Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/main_path/intersecting_" + mainPathId))
							.put(TextureKey.SIDE, Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/no_path")),
						generator.modelCollector);

				tempMap.put(mainPathId, ItemModels.basic(lawnDarkModel));
			}

			HashMap<Integer, ItemModel.Unbaked> intersectingPathModels = new HashMap<>();
			for (int intersectingPathId = 1; intersectingPathId <= LawnUtil.getPathTypeAmount(); intersectingPathId++) {
				Identifier intersectingPathModel = Identifier.of(HomeLawnSecurity.MOD_ID, "item/lawn_block/intersecting_path/" + intersectingPathId);
				intersectingPathModels.put(intersectingPathId, ItemModels.basic(intersectingPathModel));
				if (!generatedBlockPathModels)
					Models.CUBE_TOP.upload(intersectingPathModel,
						new TextureMap()
							.put(TextureKey.TOP, Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/intersecting_path/" + intersectingPathId))
							.put(TextureKey.SIDE, Identifier.of(HomeLawnSecurity.MOD_ID, "block/lawn_block/no_path")),
						generator.modelCollector);
			}

			generatedBlockPathModels = true;

			generator.output.accept(block.asItem(),
				ItemModels.composite(
					// Base
					ItemModels.select(
						ILawnBlock.PATH_ID_MAIN,
						ItemModels.basic(lawnModel),
						tempMap
					),
					// Main path
					ItemModels.select(
						ILawnBlock.PATH_ID_INTERSECTING,
						ItemModels.select(
							ILawnBlock.PATH_ID_MAIN,
							empty,
							mainPathIntersectingModels
						),
						Map.of(0, ItemModels.select(
							ILawnBlock.PATH_ID_MAIN,
							empty,
							mainPathNormalModels
						))
					),
					// Intersecting path
					ItemModels.select(
						ILawnBlock.PATH_ID_INTERSECTING,
						empty,
						intersectingPathModels
					)
				)
			);
		}
	}
}
