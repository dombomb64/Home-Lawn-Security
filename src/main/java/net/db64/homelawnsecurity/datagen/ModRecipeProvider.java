package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
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
