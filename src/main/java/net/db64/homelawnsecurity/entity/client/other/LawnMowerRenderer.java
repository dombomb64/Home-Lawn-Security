package net.db64.homelawnsecurity.entity.client.other;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LawnMowerRenderer extends MobEntityRenderer<LawnMowerEntity, LawnMowerRenderState, LawnMowerModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/other/lawn_mower.png");

	public LawnMowerRenderer(EntityRendererFactory.Context context) {
		super(context, new LawnMowerModel(context.getPart(ModModelLayers.Other.LAWN_MOWER)), 0.5f);
	}

	@Override
	public Identifier getTexture(LawnMowerRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		LawnMowerRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public LawnMowerRenderState createRenderState() {
		return new LawnMowerRenderState();
	}

	@Override
	public void updateRenderState(LawnMowerEntity entity, LawnMowerRenderState state, float f) {
		super.updateRenderState(entity, state, f);
	}
}
