package net.db64.homelawnsecurity.util;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
	public static class Blocks {
		public static final TagKey<Block> ZOMBIE_GOAL = createTag("zombie_goal");
		public static final TagKey<Block> ZOMBIE_PATH_1 = createTag("zombie_path_1");
		public static final TagKey<Block> ZOMBIE_PATH_2 = createTag("zombie_path_2");
		public static final TagKey<Block> ZOMBIE_PATH_CROSS = createTag("zombie_path_cross");
		public static final TagKey<Block> ZOMBIE_START = createTag("zombie_start");
		public static final TagKey<Block> ZOMBIE_PLACEABLE = createTag("zombie_placeable");

		public static final TagKey<Block> ZOMBIE_GOAL_MARKERS = createTag("zombie_goal_markers");
		public static final TagKey<Block> ZOMBIE_PATH_1_MARKERS = createTag("zombie_path_1_markers");
		public static final TagKey<Block> ZOMBIE_PATH_2_MARKERS = createTag("zombie_path_2_markers");
		public static final TagKey<Block> ZOMBIE_PATH_CROSS_MARKERS = createTag("zombie_path_cross_markers");
		public static final TagKey<Block> ZOMBIE_START_MARKERS = createTag("zombie_start_markers");
		public static final TagKey<Block> ZOMBIE_PLACEABLE_MARKERS = createTag("zombie_placeable_markers");

		public static final TagKey<Block> LAWN_MOWER_GOAL = createTag("lawn_mower_goal");
		public static final TagKey<Block> LAWN_MOWER_START = createTag("lawn_mower_start");
		public static final TagKey<Block> LAWN_MOWER_PATH_1 = createTag("lawn_mower_path_1");
		public static final TagKey<Block> LAWN_MOWER_PATH_2 = createTag("lawn_mower_path_2");
		public static final TagKey<Block> LAWN_MOWER_PATH_CROSS = createTag("lawn_mower_path_cross");
		public static final TagKey<Block> LAWN_MOWER_PLACEABLE = createTag("lawn_mower_placeable");

		public static final TagKey<Block> LAWN_MOWER_GOAL_MARKERS = createTag("lawn_mower_goal_markers");
		public static final TagKey<Block> LAWN_MOWER_START_MARKERS = createTag("lawn_mower_start_markers");
		public static final TagKey<Block> LAWN_MOWER_PATH_1_MARKERS = createTag("lawn_mower_path_1_markers");
		public static final TagKey<Block> LAWN_MOWER_PATH_2_MARKERS = createTag("lawn_mower_path_2_markers");
		public static final TagKey<Block> LAWN_MOWER_PATH_CROSS_MARKERS = createTag("lawn_mower_path_cross_markers");
		public static final TagKey<Block> LAWN_MOWER_PLACEABLE_MARKERS = createTag("lawn_mower_placeable_markers");

		public static final TagKey<Block> PLANT_PLACEABLE_LAWN = createTag("plant_placeable_lawn");
		public static final TagKey<Block> PLANT_PLACEABLE_PATH = createTag("plant_placeable_path");

		public static final TagKey<Block> PLANT_PLACEABLE_LAWN_MARKERS = createTag("plant_placeable_lawn_markers");
		public static final TagKey<Block> PLANT_PLACEABLE_PATH_MARKERS = createTag("plant_placeable_path_markers");

		public static final TagKey<Block> MARKERS = createTag("markers");

		private static TagKey<Block> createTag(String name) {
			return TagKey.of(RegistryKeys.BLOCK, Identifier.of(HomeLawnSecurity.MOD_ID, name));
		}
	}

	public static class Items {
		public static final TagKey<Item> BAG_OF_SUN = createTag("bag_of_sun");
		public static final TagKey<Item> BAG_OF_BRAINPOWER = createTag("bag_of_brainpower");

		public static final TagKey<Item> MARKERS = createTag("markers");

		private static TagKey<Item> createTag(String name) {
			return TagKey.of(RegistryKeys.ITEM, Identifier.of(HomeLawnSecurity.MOD_ID, name));
		}
	}

	public static class Biomes {
		//public static final TagKey<Biome> IS_NON_SPARSE_JUNGLE = createTag("is_non_sparse_jungle");

		private static TagKey<Biome> createTag(String name) {
			return TagKey.of(RegistryKeys.BIOME, Identifier.of(HomeLawnSecurity.MOD_ID, name));
		}
	}
}
