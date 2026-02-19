package net.db64.homelawnsecurity.debug;

import net.db64.homelawnsecurity.entity.custom.PathBoundEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.debug.PathfindingDebugRenderer;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.world.debug.DebugDataStore;

public class PvzPathfindingDebugRenderer extends PathfindingDebugRenderer {
	@Override
	public void render(double cameraX, double cameraY, double cameraZ, DebugDataStore store, Frustum frustum, float tickProgress) {
		store.forEachEntityData(
			ModDebugSubscriptionTypes.PVZ_PATHS, (entity, debugData) -> {
				if (entity instanceof PathBoundEntity)
					render(cameraX, cameraY, cameraZ, debugData.path(), debugData.maxNodeDistance());
			}
		);
	}

	private static void render(double cameraX, double cameraY, double cameraZ, Path path, float maxNodeDistance) {
		drawPath(path, maxNodeDistance, true, true, cameraX, cameraY, cameraZ);
	}
}
