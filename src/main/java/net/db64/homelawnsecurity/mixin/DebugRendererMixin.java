package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.HomeLawnSecurityClient;
import net.db64.homelawnsecurity.debug.PvzPathfindingDebugRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugRenderer.class)
public abstract class DebugRendererMixin {
	@Shadow
	@Final
	private List<DebugRenderer.Renderer> renderers;

	@Inject(
		method = "initRenderers",
		at = @At(
			value = "RETURN"
		)
	)
	public void addPvzDebugRenderer(CallbackInfo ci) {
		if (MinecraftClient.getInstance().debugHudEntryList.isEntryVisible(HomeLawnSecurityClient.DEBUG_PATHFINDING)) {
			renderers.add(new PvzPathfindingDebugRenderer());
		}
	}
}
