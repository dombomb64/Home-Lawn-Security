package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
	//private static List<ItemConvertible> LIST_NAME = List.of(ModItems.RAW_BACON, ModBlocks.BACON_BLOCK, ModItems.LIVE_BABY);
	//private static List<ItemConvertible> RUBBER_LOGS = List.of(ModBlocks.RUBBER_LOG, ModBlocks.RUBBER_WOOD,
		//ModBlocks.STRIPPED_RUBBER_LOG, ModBlocks.STRIPPED_RUBBER_WOOD,
		//ModBlocks.DRIPPING_RUBBER_LOG, ModBlocks.DRIPPING_RUBBER_WOOD);

	public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
		return new RecipeGenerator(wrapperLookup, recipeExporter) {
			private final ModRecipeTemplates templates = new ModRecipeTemplates();

			@Override
			public void generate() {
				//offerSmelting(exporter, LIST_NAME, RecipeCategory.FOOD, ModItems.DOG_FOOD, 0.7f, 200, "dog_food");
				//offerSmokingUhhhh(exporter, LIST_NAME, RecipeCategory.FOOD, ModItems.DOG_FOOD, 0.7f, 100, "dog_food"); // Smoking isn't a default thing??

				// Turf
				createShapeless(RecipeCategory.MISC, ModItems.TURF, 4)
					.input(ModTags.Items.RECIPE_TURF_GRASS)
					.input(ModTags.Items.RECIPE_TURF_GRASS)
					.input(ModTags.Items.RECIPE_TURF_GRASS)
					.input(ModTags.Items.RECIPE_TURF_GRASS)
					.input(Items.GRAVEL)
					.criterion("has_grass", conditionsFromTag(ModTags.Items.RECIPE_TURF_GRASS))
					.offerTo(exporter);

				// Unsodded Lawn Block
				createShaped(RecipeCategory.MISC, ModBlocks.UNSODDED_LAWN_BLOCK, 4)
					.input('d', ModTags.Items.RECIPE_GRASSLESS_DIRT)
					.pattern("dd")
					.pattern("dd")
					.criterion("has_dirt", conditionsFromTag(ModTags.Items.RECIPE_GRASSLESS_DIRT))
					.offerTo(exporter);

				// Sodded Lawn Block
				createShapeless(RecipeCategory.MISC, ModBlocks.SODDED_LAWN_BLOCK, 1)
					.input(ModItems.TURF)
					.input(ModBlocks.UNSODDED_LAWN_BLOCK)
					.criterion(hasItem(ModItems.TURF), conditionsFromItem(ModItems.TURF))
					.offerTo(exporter, getRecipeName(ModBlocks.SODDED_LAWN_BLOCK) + "_from_" + getItemPath(ModBlocks.UNSODDED_LAWN_BLOCK));

				// Sodded Lawn Block (Quick)
				createShaped(RecipeCategory.MISC, ModBlocks.SODDED_LAWN_BLOCK, 4)
					.input('d', ModTags.Items.RECIPE_GRASSLESS_DIRT)
					.input('t', ModItems.TURF)
					.pattern("tt ")
					.pattern("ddt") // Dark Dirigible Titan reference??
					.pattern("ddt")
					.criterion("has_dirt", conditionsFromTag(ModTags.Items.RECIPE_GRASSLESS_DIRT))
					.offerTo(exporter, getRecipeName(ModBlocks.SODDED_LAWN_BLOCK) + "_from_" + getItemPath(Items.DIRT));

				// Sodded Lawn Marker
				createShapeless(RecipeCategory.MISC, ModBlocks.SODDED_LAWN_MARKER, 1)
					.input(ModItems.TURF)
					.input(ModBlocks.UNSODDED_LAWN_MARKER)
					.criterion(hasItem(ModItems.TURF), conditionsFromItem(ModItems.TURF))
					.offerTo(exporter, getRecipeName(ModBlocks.SODDED_LAWN_MARKER) + "_from_" + getItemPath(ModBlocks.UNSODDED_LAWN_MARKER));

				// Garden Block
				createShapeless(RecipeCategory.MISC, ModBlocks.GARDEN_BLOCK, 1)
					.input(ModBlocks.SODDED_LAWN_BLOCK)
					.input(ItemTags.SMALL_FLOWERS)
					.criterion(hasItem(ModBlocks.SODDED_LAWN_BLOCK), conditionsFromItem(ModBlocks.SODDED_LAWN_BLOCK))
					.offerTo(exporter, getRecipeName(ModBlocks.GARDEN_BLOCK) + "_from_" + getItemPath(ModBlocks.SODDED_LAWN_BLOCK));

				// Garden Block (Quick)
				createShapeless(RecipeCategory.MISC, ModBlocks.GARDEN_BLOCK, 1)
					.input(ModBlocks.UNSODDED_LAWN_BLOCK)
					.input(ModItems.TURF)
					.input(ItemTags.SMALL_FLOWERS)
					.criterion(hasItem(ModBlocks.UNSODDED_LAWN_BLOCK), conditionsFromItem(ModBlocks.UNSODDED_LAWN_BLOCK))
					.offerTo(exporter, getRecipeName(ModBlocks.GARDEN_BLOCK) + "_from_" + getItemPath(ModBlocks.UNSODDED_LAWN_BLOCK));

				// Graveyard Block
				createShapeless(RecipeCategory.MISC, ModBlocks.GRAVEYARD_BLOCK, 1)
					.input(Items.FERMENTED_SPIDER_EYE)
					.input(ModBlocks.GARDEN_BLOCK)
					.criterion(hasItem(ModBlocks.GARDEN_BLOCK), conditionsFromItem(ModBlocks.GARDEN_BLOCK))
					.offerTo(exporter, getRecipeName(ModBlocks.GRAVEYARD_BLOCK) + "_from_" + getItemPath(ModBlocks.GARDEN_BLOCK));

				// Graveyard Block (Quick)
				createShapeless(RecipeCategory.MISC, ModBlocks.GRAVEYARD_BLOCK, 1)
					.input(ModBlocks.SODDED_LAWN_BLOCK)
					.input(ItemTags.SMALL_FLOWERS)
					.input(Items.FERMENTED_SPIDER_EYE)
					.criterion(hasItem(ModBlocks.SODDED_LAWN_BLOCK), conditionsFromItem(ModBlocks.SODDED_LAWN_BLOCK))
					.offerTo(exporter, getRecipeName(ModBlocks.GRAVEYARD_BLOCK) + "_from_" + getItemPath(ModBlocks.SODDED_LAWN_BLOCK));

				// Graveyard Block (Quicker)
				createShapeless(RecipeCategory.MISC, ModBlocks.GRAVEYARD_BLOCK, 1)
					.input(ModItems.TURF)
					.input(ModBlocks.UNSODDED_LAWN_BLOCK)
					.input(ItemTags.SMALL_FLOWERS)
					.input(Items.FERMENTED_SPIDER_EYE)
					.criterion(hasItem(ModItems.TURF), conditionsFromItem(ModItems.TURF))
					.offerTo(exporter, getRecipeName(ModBlocks.GRAVEYARD_BLOCK) + "_from_" + getItemPath(ModBlocks.UNSODDED_LAWN_BLOCK));

				// Garden Marker
				createShapeless(RecipeCategory.MISC, ModBlocks.GARDEN_MARKER, 1)
					.input(ModBlocks.SODDED_LAWN_MARKER)
					.input(ItemTags.SMALL_FLOWERS)
					.criterion(hasItem(ModBlocks.SODDED_LAWN_MARKER), conditionsFromItem(ModBlocks.SODDED_LAWN_MARKER))
					.offerTo(exporter, getRecipeName(ModBlocks.GARDEN_MARKER) + "_from_" + getItemPath(ModBlocks.SODDED_LAWN_MARKER));

				// Garden Marker (Quick)
				createShapeless(RecipeCategory.MISC, ModBlocks.GARDEN_MARKER, 1)
					.input(ModBlocks.UNSODDED_LAWN_MARKER)
					.input(ModItems.TURF)
					.input(ItemTags.SMALL_FLOWERS)
					.criterion(hasItem(ModBlocks.UNSODDED_LAWN_MARKER), conditionsFromItem(ModBlocks.UNSODDED_LAWN_MARKER))
					.offerTo(exporter, getRecipeName(ModBlocks.GARDEN_MARKER) + "_from_" + getItemPath(ModBlocks.UNSODDED_LAWN_MARKER));

				// Graveyard Marker
				createShapeless(RecipeCategory.MISC, ModBlocks.GRAVEYARD_MARKER, 1)
					.input(Items.FERMENTED_SPIDER_EYE)
					.input(ModBlocks.GARDEN_MARKER)
					.criterion(hasItem(ModBlocks.GARDEN_MARKER), conditionsFromItem(ModBlocks.GARDEN_MARKER))
					.offerTo(exporter, getRecipeName(ModBlocks.GRAVEYARD_MARKER) + "_from_" + getItemPath(ModBlocks.GARDEN_MARKER));

				// Graveyard Marker (Quick)
				createShapeless(RecipeCategory.MISC, ModBlocks.GRAVEYARD_MARKER, 1)
					.input(ModBlocks.SODDED_LAWN_MARKER)
					.input(ItemTags.SMALL_FLOWERS)
					.input(Items.FERMENTED_SPIDER_EYE)
					.criterion(hasItem(ModBlocks.SODDED_LAWN_MARKER), conditionsFromItem(ModBlocks.SODDED_LAWN_MARKER))
					.offerTo(exporter, getRecipeName(ModBlocks.GRAVEYARD_MARKER) + "_from_" + getItemPath(ModBlocks.SODDED_LAWN_MARKER));

				// Graveyard Marker (Quicker)
				createShapeless(RecipeCategory.MISC, ModBlocks.GRAVEYARD_MARKER, 1)
					.input(ModItems.TURF)
					.input(ModBlocks.UNSODDED_LAWN_MARKER)
					.input(ItemTags.SMALL_FLOWERS)
					.input(Items.FERMENTED_SPIDER_EYE)
					.criterion(hasItem(ModItems.TURF), conditionsFromItem(ModItems.TURF))
					.offerTo(exporter, getRecipeName(ModBlocks.GRAVEYARD_MARKER) + "_from_" + getItemPath(ModBlocks.UNSODDED_LAWN_MARKER));

				// Lawn Mower
				createShapeless(RecipeCategory.MISC, ModItems.LAWN_MOWER, 1)
					.input(Items.IRON_INGOT)
					.input(Items.RED_DYE)
					.input(ItemTags.STONE_CRAFTING_MATERIALS)
					.input(ItemTags.COALS)
					.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
					.offerTo(exporter, getRecipeName(ModItems.LAWN_MOWER));

				// Shovel
				createShaped(RecipeCategory.MISC, ModItems.SHOVEL, 1)
					.input('i', Items.IRON_INGOT)
					.input('d', Items.RED_DYE)
					.input('s', Items.STICK)
					.input('n', Items.IRON_NUGGET)
					.pattern("i ")
					.pattern("s ")
					.pattern("nd")
					.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
					.offerTo(exporter, getRecipeName(ModItems.SHOVEL));

				// Lawn Gadget
				createShaped(RecipeCategory.MISC, ModItems.LAWN_GADGET, 1)
					.input('i', Items.IRON_INGOT)
					.input('d', Items.RED_DYE)
					.input('n', Items.IRON_NUGGET)
					.pattern("n n")
					.pattern("ndn")
					.pattern(" i ")
					.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
					.offerTo(exporter, getRecipeName(ModItems.LAWN_GADGET));

				// Sun Spawner
				createShaped(RecipeCategory.MISC, ModBlocks.SUN_SPAWNER, 1)
					.input('g', Items.GLASS)
					.input('i', Items.IRON_INGOT)
					.input('b', Items.BONE_MEAL)
					.pattern("bbb")
					.pattern("bgb")
					.pattern("bib")
					.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
					.offerTo(exporter, getRecipeName(ModBlocks.SUN_SPAWNER));

				// Brainpower Beacon
				createShaped(RecipeCategory.MISC, ModBlocks.BRAINPOWER_BEACON, 1)
					.input('g', Items.GLASS)
					.input('i', Items.IRON_INGOT)
					.input('b', ModItems.BRAIN)
					.pattern("bbb")
					.pattern("bgb")
					.pattern("bib")
					.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
					.offerTo(exporter, getRecipeName(ModBlocks.BRAINPOWER_BEACON));

				// Bag of Sun
				createShapeless(RecipeCategory.MISC, ModItems.BAG_OF_SUN, 1)
					.input(Items.LEATHER)
					.input(Items.BONE_MEAL)
					.criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
					.offerTo(exporter, getRecipeName(ModItems.BAG_OF_SUN));

				// Bag of Brainpower
				createShapeless(RecipeCategory.MISC, ModItems.BAG_OF_BRAINPOWER, 1)
					.input(Items.LEATHER)
					.input(ModItems.BRAIN)
					.criterion(hasItem(Items.LEATHER), conditionsFromItem(Items.LEATHER))
					.offerTo(exporter, getRecipeName(ModItems.BAG_OF_BRAINPOWER));

				// Target
				createShapeless(RecipeCategory.MISC, ModItems.TARGET, 1)
					.input(ItemTags.PLANKS)
					.input(Items.WHITE_DYE)
					.input(Items.RED_DYE)
					.criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
					.offerTo(exporter, getRecipeName(ModItems.TARGET));

				/*// Rubber wood set
				templates.makeWoodType(exporter, new Item[][] {{ModBlocks.RUBBER_LOG.asItem(), ModBlocks.RUBBER_WOOD.asItem()},
					{ModBlocks.STRIPPED_RUBBER_LOG.asItem(), ModBlocks.STRIPPED_RUBBER_WOOD.asItem()},
					{ModBlocks.DRIPPING_RUBBER_LOG.asItem(), ModBlocks.DRIPPING_RUBBER_WOOD.asItem()}},
					ModTags.Items.RUBBER_LOGS, ModBlocks.RUBBERWOOD_PLANKS.asItem(),
					ModBlocks.RUBBERWOOD_STAIRS.asItem(), ModBlocks.RUBBERWOOD_SLAB.asItem(),
					ModBlocks.RUBBERWOOD_FENCE.asItem(), ModBlocks.RUBBERWOOD_FENCE_GATE.asItem(),
					ModBlocks.RUBBERWOOD_DOOR.asItem(), ModBlocks.RUBBERWOOD_TRAPDOOR.asItem(),
					ModBlocks.RUBBERWOOD_PRESSURE_PLATE.asItem(), ModBlocks.RUBBERWOOD_BUTTON.asItem());*/
			}

			private class ModRecipeTemplates {
				public ModRecipeTemplates() {} // Why do datagen providers need to be created like you would need multiple of the same type????

				private void makeWoodType(RecipeExporter exporter, Item[][] logToWood, TagKey<Item> logTag, Item planks, Item stairs, Item slab, Item fence, Item fenceGate, Item door, Item trapdoor, Item pressurePlate, Item button) {
					for (Item[] items : logToWood) {
						makeBark(exporter, items[1], items[0]); // logToWood is formatted like {{log, wood}, {strippedLog, strippedWood}}
					}

					offerPlanksRecipe(planks, logTag, 4);

					makeStairs(exporter, stairs, planks);
					offerSlabRecipe(RecipeCategory.BUILDING_BLOCKS, slab, planks);

					makeWoodenFence(exporter, fence, planks);
					makeWoodenFenceGate(exporter, fenceGate, planks);

					makeDoor(exporter, door, planks);
					makeTrapdoor(exporter, trapdoor, planks);

					offerPressurePlateRecipe(pressurePlate, planks);
					makeButton(exporter, button, planks);
				}

				private void makeBark(RecipeExporter exporter, Item output, Item input) {
					createShaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
						.pattern("##")
						.pattern("##")
						.input('#', input)
						.criterion(hasItem(input), conditionsFromItem(input))
						.offerTo(exporter, getRecipeName(output));
				}

				private void makeStairs(RecipeExporter exporter, Item output, Item input) {
					createShaped(RecipeCategory.BUILDING_BLOCKS, output, 4)
						.pattern("#  ")
						.pattern("## ")
						.pattern("###")
						.input('#', input)
						.criterion(hasItem(input), conditionsFromItem(input))
						.offerTo(exporter, getRecipeName(output));
				}

				private void makeFence(RecipeExporter exporter, Item output, Item input, Item stick, int count, String group) {
					createShaped(RecipeCategory.DECORATIONS, output, count)
						.pattern("W#W")
						.pattern("W#W")
						.input('W', input)
						.input('#', stick)
						.criterion(hasItem(input), conditionsFromItem(input))
						.group(group)
						.offerTo(exporter, getRecipeName(output));
				}

				private void makeWoodenFence(RecipeExporter exporter, Item output, Item input) {
					makeFence(exporter, output, input, Items.STICK, 3, "wooden_fence");
				}

				private void makeFenceGate(RecipeExporter exporter, Item output, Item input, Item stick, int count, String group) {
					createShaped(RecipeCategory.REDSTONE, output, count)
						.pattern("#W#")
						.pattern("#W#")
						.input('W', input)
						.input('#', stick)
						.criterion(hasItem(input), conditionsFromItem(input))
						.group(group)
						.offerTo(exporter, getRecipeName(output));
				}

				private void makeWoodenFenceGate(RecipeExporter exporter, Item output, Item input) {
					makeFenceGate(exporter, output, input, Items.STICK, 1, "wooden_fence_gate");
				}

				private void makeDoor(RecipeExporter exporter, Item output, Item input) {
					createShaped(RecipeCategory.REDSTONE, output, 3)
						.pattern("##")
						.pattern("##")
						.pattern("##")
						.input('#', input)
						.criterion(hasItem(input), conditionsFromItem(input))
						.offerTo(exporter, getRecipeName(output));
				}

				private void makeTrapdoor(RecipeExporter exporter, Item output, Item input) {
					createShaped(RecipeCategory.REDSTONE, output, 2)
						.pattern("###")
						.pattern("###")
						.input('#', input)
						.criterion(hasItem(input), conditionsFromItem(input))
						.offerTo(exporter, getRecipeName(output));
				}

				private void makeButton(RecipeExporter exporter, Item output, Item input) {
					createShapeless(RecipeCategory.REDSTONE, output, 1)
						.input(input)
						.criterion(hasItem(input), conditionsFromItem(input))
						.offerTo(exporter, getRecipeName(output));
				}
			}
		};
	}

	@Override
	public String getName() {
		return "Recipe Provider from " + HomeLawnSecurity.MOD_ID;
	}
}
