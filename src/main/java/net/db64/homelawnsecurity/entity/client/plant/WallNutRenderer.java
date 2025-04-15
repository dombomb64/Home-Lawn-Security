package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.plant.WallNutEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WallNutRenderer extends MobEntityRenderer<WallNutEntity, WallNutRenderState, WallNutModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/plant/wall_nut.png");

	public WallNutRenderer(EntityRendererFactory.Context context) {
		super(context, new WallNutModel(context.getPart(ModModelLayers.Plant.WALL_NUT)), 0.5f);
	}

	@Override
	public Identifier getTexture(WallNutRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(WallNutRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(state, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public WallNutRenderState createRenderState() {
		return new WallNutRenderState();
	}

	@Override
	public void updateRenderState(WallNutEntity entity, WallNutRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.setupAnimationState.copyFrom(entity.setupAnimationState);
	}
}
