package net.db64.homelawnsecurity.entity.client;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
	public static class Projectile {
		public static final EntityModelLayer PEA =
			new EntityModelLayer(new Identifier(HomeLawnSecurity.MOD_ID, "pea"), "main");
	}

	public static class Plant {
		public static final EntityModelLayer PEASHOOTER =
			new EntityModelLayer(new Identifier(HomeLawnSecurity.MOD_ID, "peashooter"), "main");
	}

	public static class Zombie {
		public static final EntityModelLayer BASIC_ZOMBIE =
			new EntityModelLayer(new Identifier(HomeLawnSecurity.MOD_ID, "basic_zombie"), "main");
		public static final EntityModelLayer CONEHEAD_ZOMBIE =
			new EntityModelLayer(new Identifier(HomeLawnSecurity.MOD_ID, "conehead_zombie"), "main");
	}
}
