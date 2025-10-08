package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ConeheadZombieEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ConeheadZombieRenderer extends MobEntityRenderer<ConeheadZombieEntity, ConeheadZombieRenderState, ConeheadZombieModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/conehead_zombie.png");

	public ConeheadZombieRenderer(EntityRendererFactory.Context context) {
		super(context, new ConeheadZombieModel(context.getPart(ModModelLayers.Zombie.CONEHEAD_ZOMBIE)), 0.5f);
	}

	@Override
	public Identifier getTexture(ConeheadZombieRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		ConeheadZombieRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public ConeheadZombieRenderState createRenderState() {
		return new ConeheadZombieRenderState();
	}

	@Override
	public void updateRenderState(ConeheadZombieEntity entity, ConeheadZombieRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.hasLostHeadwear = entity.hasTriggeredDegradationStage("headwear");
		state.hasLostArm = entity.hasTriggeredDegradationStage("arm");
		state.hasLostHead = entity.hasTriggeredDegradationStage("head");
		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.attackAnimationState.copyFrom(entity.attackAnimationState);
	}
}
