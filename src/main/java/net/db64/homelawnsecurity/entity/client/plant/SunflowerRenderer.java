package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.plant.SunflowerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SunflowerRenderer extends MobEntityRenderer<SunflowerEntity, SunflowerRenderState, SunflowerModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/plant/sunflower.png");

	public SunflowerRenderer(EntityRendererFactory.Context context) {
		super(context, new SunflowerModel(context.getPart(ModModelLayers.Plant.SUNFLOWER)), 0.5f);
	}

	@Override
	public Identifier getTexture(SunflowerRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		SunflowerRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public SunflowerRenderState createRenderState() {
		return new SunflowerRenderState();
	}

	@Override
	public void updateRenderState(SunflowerEntity entity, SunflowerRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.setupAnimationState.copyFrom(entity.setupAnimationState);
	}
}
