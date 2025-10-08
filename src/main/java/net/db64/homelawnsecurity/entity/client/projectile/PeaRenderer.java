package net.db64.homelawnsecurity.entity.client.projectile;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.client.plant.PeashooterRenderState;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class PeaRenderer extends EntityRenderer<PeaEntity, PeaRenderState> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/projectile/pea.png");
	private final PeaModel model;

	public PeaRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.model = new PeaModel(context.getPart(ModModelLayers.Projectile.PEA));
	}

	public Identifier getTexture(PeaRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		PeaRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		matrixStack.push();

		matrixStack.translate(0f, -1.25f, 0f);

		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw - 90.0F));
		matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch));

		orderedRenderCommandQueue.submitModel(
			this.model,
			state,
			matrixStack,
			RenderLayer.getEntityCutout(this.getTexture(state)),
			state.light,
			OverlayTexture.DEFAULT_UV,
			state.outlineColor,
			null
		);

		matrixStack.pop();
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public PeaRenderState createRenderState() {
		return new PeaRenderState();
	}

	@Override
	public void updateRenderState(PeaEntity entity, PeaRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.pitch = entity.getPitch();
		state.yaw = entity.getYaw();
	}
}