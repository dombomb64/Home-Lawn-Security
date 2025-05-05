package net.db64.homelawnsecurity.entity.client;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
	public static class Other {
		public static final EntityModelLayer LAWN_MOWER =
			register(HomeLawnSecurity.MOD_ID, "lawn_mower");
		public static final EntityModelLayer LAWN_BLOCK =
			register("lawn_block");
		public static final EntityModelLayer LAWN_BLOCK_MAIN_PATH =
			register("lawn_block", "main_path");
		public static final EntityModelLayer LAWN_BLOCK_INTERSECTING_PATH =
			register("lawn_block", "intersecting_path");
	}

	public static class Projectile {
		public static final EntityModelLayer PEA =
			register(HomeLawnSecurity.MOD_ID, "pea");
	}

	public static class Plant {
		public static final EntityModelLayer SUNFLOWER =
			register(HomeLawnSecurity.MOD_ID, "sunflower");
		public static final EntityModelLayer PEASHOOTER =
			register(HomeLawnSecurity.MOD_ID, "peashooter");
		public static final EntityModelLayer WALL_NUT =
			register(HomeLawnSecurity.MOD_ID, "wall_nut");
	}

	public static class Zombie {
		public static final EntityModelLayer TARGET_ZOMBIE =
			register(HomeLawnSecurity.MOD_ID, "target_zombie");
		public static final EntityModelLayer ZOMBIE_GRAVESTONE =
			register("zombie_gravestone");
		public static final EntityModelLayer ZOMBIE_GRAVESTONE_CRACKS =
			register("zombie_gravestone_cracks", "cracks");
		public static final EntityModelLayer BASIC_ZOMBIE =
			register(HomeLawnSecurity.MOD_ID, "basic_zombie");
		public static final EntityModelLayer CONEHEAD_ZOMBIE =
			register(HomeLawnSecurity.MOD_ID, "conehead_zombie");
	}

	private static EntityModelLayer register(String name, String layer) {
		return new EntityModelLayer(Identifier.of(HomeLawnSecurity.MOD_ID, name), layer);
	}

	private static EntityModelLayer register(String name) {
		return register(name, "main");
	}
}
