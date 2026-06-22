package net.db64.homelawnsecurity.entity.client;

import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;

public abstract class PvzFeatureRenderer<S extends PvzRenderState, M extends EntityModel<? super S>> extends FeatureRenderer<S, M> {
	public PvzFeatureRenderer(FeatureRendererContext<S, M> context) {
		super(context);
	}

	/*public static <S extends LivingEntityRenderState> void renderModel(
		Model<? super S> model, Identifier texture, MatrixStack matrices, OrderedRenderCommandQueue queue, int light, S state, int color, int queueOrder
	) {
		queue.getBatchingQueue(queueOrder)
			.submitModel(
				model,
				state,
				matrices,
				RenderLayers.entityCutoutNoCull(texture),
				light,
				LivingEntityRenderer.getOverlay(state, state.getAnimationCounter()),
				color,
				null,
				state.outlineColor,
				null
			);
	}*/
}
