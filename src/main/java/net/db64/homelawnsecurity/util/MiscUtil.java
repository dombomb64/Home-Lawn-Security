package net.db64.homelawnsecurity.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

public class MiscUtil {
	public static float getSoundPitch(Random random) {
		return 1.0f / (random.nextFloat() * 0.4f + 0.8f);
	}

	/**
	 * Used to determine whether a unit should attack if its target is in the same tile.
	 * @param entity The attacking entity.
	 * @param target The target entity.
	 * @return Whether the entity is facing the target.
	 */
	public static boolean isFacingEntity(Entity entity, Entity target) {
		Vec3d entityPos = entity.getEntityPos();
		Vec3d targetPos = target.getEntityPos();
		return switch (entity.getHorizontalFacing()) {
			case SOUTH -> entityPos.z < targetPos.z;
			case NORTH -> entityPos.z > targetPos.z;
			case EAST -> entityPos.x < targetPos.x;
			case WEST -> entityPos.x > targetPos.x;
			default -> false;
		};
	}
}
