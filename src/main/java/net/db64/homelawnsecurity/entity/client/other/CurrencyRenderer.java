package net.db64.homelawnsecurity.entity.client.other;

import net.db64.homelawnsecurity.component.CurrencyComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class CurrencyRenderer extends EntityRenderer<CurrencyEntity, CurrencyRenderState> {
	private final ItemModelManager itemModelManager;

	/*public CurrencyRenderer(EntityRendererFactory.Context context, boolean lit) {
		super(context);
		this.itemModelManager = context.getItemModelManager();
		this.lit = lit;
	}*/

	public CurrencyRenderer(EntityRendererFactory.Context context) {
		//this(context, true);
		super(context);
		this.itemModelManager = context.getItemModelManager();
	}

	/*@Override
	protected int getBlockLight(CurrencyEntity entity, BlockPos pos) {
		return this.lit ? 15 : super.getBlockLight(entity, pos);
	}*/

	/*@Override
	protected int getSkyLight(CurrencyEntity entity, BlockPos pos) {
		return 15;
	}*/

	public void render(
		CurrencyRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		matrixStack.push();
		matrixStack.scale(state.scale, state.scale, state.scale);
		matrixStack.multiply(cameraRenderState.orientation);
		/*if (state.model != null) {
			this.itemModelManager
				.renderItem(
					state.stack,
					ModelTransformationMode.GROUND,
					false,
					matrixStack,
					vertexConsumerProvider,
					i,
					OverlayTexture.DEFAULT_UV,
					state.model
				);
		}*/
		state.itemRenderState.render(matrixStack, orderedRenderCommandQueue, state.light, OverlayTexture.DEFAULT_UV, state.outlineColor);

		matrixStack.pop();
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public CurrencyRenderState createRenderState() {
		return new CurrencyRenderState();
	}

	@Override
	public void updateRenderState(CurrencyEntity entity, CurrencyRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		ItemStack stack = entity.getStack();

		this.itemModelManager.updateForNonLivingEntity(state.itemRenderState, stack, ItemDisplayContext.GROUND, entity);

		/*state.model = !stack.isEmpty() ? this.itemModelManager.getModel(stack, entity.getEntityWorld(), null, entity.getId()) : null;
		state.stack = stack.copy();*/
		CurrencyComponent currency = stack.get(ModDataComponentTypes.CURRENCY);
		if (currency != null) {
			state.scale = MathHelper.clampedMap(currency.amount(), 5f, 50f, 0.5f, 5f);
			state.lit = true;
		}
		else {
			state.scale = 1.0f;
			state.lit = false;
		}

		if (state.lit)
			state.light = 15728880;
	}
}