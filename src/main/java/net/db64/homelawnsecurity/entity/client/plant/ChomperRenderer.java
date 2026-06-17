package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.plant.ChomperEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ChomperRenderer extends MobEntityRenderer<ChomperEntity, ChomperRenderState, ChomperModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/plant/chomper.png");

	public ChomperRenderer(EntityRendererFactory.Context context) {
		super(context, new ChomperModel(context.getPart(ModModelLayers.Plant.CHOMPER)), 0.5f);
	}

	@Override
	public Identifier getTexture(ChomperRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		ChomperRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public ChomperRenderState createRenderState() {
		return new ChomperRenderState();
	}

	@Override
	public void updateRenderState(ChomperEntity entity, ChomperRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.idleAnimationState.copyFrom(entity.idleAnimationState);
		state.attackAnimationState.copyFrom(entity.attackAnimationState);
		state.chompAnimationState.copyFrom(entity.chompAnimationState);
		state.chewingAnimationState.copyFrom(entity.chewingAnimationState);
		state.swallowAnimationState.copyFrom(entity.swallowAnimationState);
	}
}
