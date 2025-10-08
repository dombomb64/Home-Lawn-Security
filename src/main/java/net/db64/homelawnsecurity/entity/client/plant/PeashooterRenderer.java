package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class PeashooterRenderer extends MobEntityRenderer<PeashooterEntity, PeashooterRenderState, PeashooterModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/plant/peashooter.png");

	public PeashooterRenderer(EntityRendererFactory.Context context) {
		super(context, new PeashooterModel(context.getPart(ModModelLayers.Plant.PEASHOOTER)), 0.5f);
	}

	@Override
	public Identifier getTexture(PeashooterRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		PeashooterRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public PeashooterRenderState createRenderState() {
		return new PeashooterRenderState();
	}

	@Override
	public void updateRenderState(PeashooterEntity entity, PeashooterRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.attackAnimationState.copyFrom(entity.attackAnimationState);
	}
}
