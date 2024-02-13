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

		private static TagKey<Block> createTag(String name) {
			return TagKey.of(RegistryKeys.BLOCK, new Identifier(HomeLawnSecurity.MOD_ID, name));
		}
	}

	public static class Items {
		//public static final TagKey<Item> RUBBER_LOGS = createTag("rubber_logs");

		private static TagKey<Item> createTag(String name) {
			return TagKey.of(RegistryKeys.ITEM, new Identifier(HomeLawnSecurity.MOD_ID, name));
		}
	}

	public static class Biomes {
		//public static final TagKey<Biome> IS_NON_SPARSE_JUNGLE = createTag("is_non_sparse_jungle");

		private static TagKey<Biome> createTag(String name) {
			return TagKey.of(RegistryKeys.BIOME, new Identifier(HomeLawnSecurity.MOD_ID, name));
		}
	}
}
