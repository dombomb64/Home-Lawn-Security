package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ZombieGravestoneCracksFeatureRenderer<S extends ZombieGravestoneRenderState, M extends EntityModel<? super S>> extends FeatureRenderer<S, M> {
	private static final float CRACK_HEALTH = 320 * IPvzEntity.HEALTH_SCALE;

	private final Identifier texture;
	private final M model;

	public ZombieGravestoneCracksFeatureRenderer(FeatureRendererContext<S, M> context, M model, Identifier texture) {
		super(context);
		this.model = model;
		this.texture = texture;
	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, S state, float f, float g) {
		if (state.health <= CRACK_HEALTH) {
			M entityModel = this.model;
			entityModel.setAngles(state);
			//VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.texture));
			//entityModel.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);

			renderModel(entityModel, texture, matrixStack, vertexConsumerProvider, i, state, -1);
		}
	}
}
