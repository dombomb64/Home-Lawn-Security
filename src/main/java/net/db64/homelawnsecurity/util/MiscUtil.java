package net.db64.homelawnsecurity.util;

import net.minecraft.util.math.random.Random;

public class MiscUtil {
	public static float getSoundPitch(Random random) {
		return 1.0f / (random.nextFloat() * 0.4f + 0.8f);
	}
}
