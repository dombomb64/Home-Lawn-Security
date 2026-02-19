package net.db64.homelawnsecurity.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EntityParticleRenderer extends ParticleRenderer<MarkerParticle> {
	public EntityParticleRenderer(ParticleManager particleManager) {
		super(particleManager);
	}

	@Override
	public Submittable render(Frustum frustum, Camera camera, float tickProgress) {
		return new EntityParticleRenderer.Result(
			this.particles.stream().map(particle -> State.create(particle, camera, tickProgress)).toList()
		);
	}

	@Environment(EnvType.CLIENT)
	record Result(List<State> states) implements Submittable {
		@Override
		public void submit(OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
			MatrixStack matrixStack = new MatrixStack();
			EntityRenderManager entityRenderManager = MinecraftClient.getInstance().getEntityRenderDispatcher();


			for (State state : this.states) {
				entityRenderManager.render(
					state.entityRenderState, cameraRenderState, state.xOffset, state.yOffset, state.zOffset, matrixStack, orderedRenderCommandQueue
				);

				/*orderedRenderCommandQueue.submitModel(
					state.model,
					Unit.INSTANCE,
					state.matrices,
					state.renderLayer,
					LightmapTextureManager.MAX_LIGHT_COORDINATE,
					OverlayTexture.DEFAULT_UV,
					state.color,
					null,
					0,
					null
				);*/
			}
		}
	}

	@Environment(EnvType.CLIENT)
	record State(EntityRenderState entityRenderState, double xOffset, double yOffset, double zOffset/*, Model<Unit> model, MatrixStack matrices, RenderLayer renderLayer, int color*/) {
		public static State create(MarkerParticle particle, Camera camera, float tickProgress) {
			Vec3d vec3d = camera.getCameraPos();
			return new State(
				particle.renderState,
				particle.renderState.x - vec3d.getX(),
				particle.renderState.y - vec3d.getY(),
				particle.renderState.z - vec3d.getZ()/*,
				particle.model,
				matrices,
				particle.renderLayer,
				ColorHelper.whiteWithAlpha(50)*/
			);
		}
	}
}
