package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ModItemTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
		super(output, completableFuture);
	}

	@Override
	protected void configure(WrapperLookup arg) {
		/*getTagBuilder(ModTags.Items.BAG_OF_SUN)
			.add(Registries.ITEM.getId(ModItems.BAG_OF_SUN));*/

		/*getTagBuilder(ModTags.Items.BAG_OF_BRAINPOWER)
			.add(Registries.ITEM.getId(ModItems.BAG_OF_BRAINPOWER));*/

		getTagBuilder(ModTags.Items.REVEALS_MARKERS)
			.add(Registries.ITEM.getId(ModBlocks.GARDEN_MARKER.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.GRAVEYARD_MARKER.asItem()))
			/*.add(Registries.ITEM.getId(ModBlocks.LAWN_MARKER.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.ZOMBIE_PATH_MARKER_1.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.ZOMBIE_PATH_MARKER_2.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.ZOMBIE_PATH_MARKER_CROSS.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.FERTILE_PATH_MARKER_1.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.FERTILE_PATH_MARKER_2.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.FERTILE_PATH_MARKER_CROSS.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.UNSODDED_LAWN_MARKER.asItem()))*/
			.add(Registries.ITEM.getId(ModBlocks.SODDED_LAWN_MARKER.asItem()))
			.add(Registries.ITEM.getId(ModBlocks.UNSODDED_LAWN_MARKER.asItem()));

		getTagBuilder(ModTags.Items.REVEALS_MARKERS_WHILE_SNEAKING)
			.add(Registries.ITEM.getId(ModItems.LAWN_GADGET))
			.add(Registries.ITEM.getId(ModItems.TURF))
			.add(Registries.ITEM.getId(Items.SHEARS));

		getTagBuilder(ModTags.Items.PLANT_FEEDABLE_FOR_DUPLICATE)
			//.add(Registries.ITEM.getId(Items.BONE_MEAL));
			.addOptionalTag(Identifier.of("c", "fertilizers"));

		getTagBuilder(ModTags.Items.ZOMBIE_FEEDABLE_FOR_DUPLICATE)
			.add(Registries.ITEM.getId(ModItems.BRAIN));

		getTagBuilder(ModTags.Items.PLANT_SEED_PACKETS)
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_SUNFLOWER))
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_PEASHOOTER))
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_WALL_NUT))
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_CHOMPER));

		getTagBuilder(ModTags.Items.ZOMBIE_SEED_PACKETS)
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_ZOMBIE_GRAVESTONE))
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_BASIC_ZOMBIE))
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_CONEHEAD_ZOMBIE))
			.add(Registries.ITEM.getId(ModItems.SEED_PACKET_BUCKETHEAD_ZOMBIE));

		getTagBuilder(ModTags.Items.SEED_PACKETS)
			.addTag(Identifier.of(HomeLawnSecurity.MOD_ID, "plant_seed_packets"))
			.addTag(Identifier.of(HomeLawnSecurity.MOD_ID, "zombie_seed_packets"));

		getTagBuilder(ModTags.Items.RECIPE_TURF_GRASS)
			.add(Registries.ITEM.getId(Items.SHORT_GRASS))
			.add(Registries.ITEM.getId(Items.TALL_GRASS))
			.add(Registries.ITEM.getId(Items.FERN))
			.add(Registries.ITEM.getId(Items.LARGE_FERN))
			.add(Registries.ITEM.getId(Items.SHORT_DRY_GRASS))
			.add(Registries.ITEM.getId(Items.TALL_DRY_GRASS))
			.add(Registries.ITEM.getId(Items.BUSH));

		getTagBuilder(ModTags.Items.RECIPE_GRASSLESS_DIRT)
			.add(Registries.ITEM.getId(Items.DIRT))
			.add(Registries.ITEM.getId(Items.COARSE_DIRT))
			.add(Registries.ITEM.getId(Items.ROOTED_DIRT))
			.add(Registries.ITEM.getId(Items.PODZOL))
			.add(Registries.ITEM.getId(Items.MYCELIUM));

		getTagBuilder(ModTags.Items.RECIPE_PLANT_FEEDABLE)
			.addOptionalTag(Identifier.of("c", "fertilizers"));

		getTagBuilder(ModTags.Items.RECIPE_ZOMBIE_FEEDABLE)
			.add(Registries.ITEM.getId(ModItems.BRAIN));
	}
}
