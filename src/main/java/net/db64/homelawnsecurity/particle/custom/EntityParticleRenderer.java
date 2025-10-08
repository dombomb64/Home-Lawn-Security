package net.db64.homelawnsecurity.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.Submittable;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class EntityParticleRenderer extends ParticleRenderer<MarkerParticle> {
	public EntityParticleRenderer(ParticleManager particleManager) {
		super(particleManager);
	}

	@Override
	public Submittable render(Frustum frustum, Camera camera, float tickProgress) {
		return new EntityParticleRenderer.Result(
			this.particles.stream().map(particle -> EntityParticleRenderer.Instance.create(particle, camera, tickProgress)).toList()
		);
	}

	@Environment(EnvType.CLIENT)
	record Instance(EntityRenderState entityRenderState, double xOffset, double yOffset, double zOffset) {
		public static EntityParticleRenderer.Instance create(MarkerParticle particle, Camera camera, float tickProgress) {
			Vec3d vec3d = camera.getPos();
			return new EntityParticleRenderer.Instance(particle.renderState,
				particle.renderState.x - vec3d.getX(),
				particle.renderState.y - vec3d.getY(),
				particle.renderState.z - vec3d.getZ());
		}
	}

	@Environment(EnvType.CLIENT)
	record Result(List<EntityParticleRenderer.Instance> instances) implements Submittable {
		@Override
		public void submit(OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
			MatrixStack matrixStack = new MatrixStack();
			EntityRenderManager entityRenderManager = MinecraftClient.getInstance().getEntityRenderDispatcher();

			for (EntityParticleRenderer.Instance instance : this.instances) {
				entityRenderManager.render(
					instance.entityRenderState, cameraRenderState, instance.xOffset, instance.yOffset, instance.zOffset, matrixStack, orderedRenderCommandQueue
				);
			}
		}
	}
}
