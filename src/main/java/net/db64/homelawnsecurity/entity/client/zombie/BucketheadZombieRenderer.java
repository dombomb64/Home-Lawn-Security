package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.zombie.BucketheadZombieEntity;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BucketheadZombieRenderer extends MobEntityRenderer<BucketheadZombieEntity, BucketheadZombieRenderState, BucketheadZombieModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/buckethead_zombie.png");

	public BucketheadZombieRenderer(EntityRendererFactory.Context context) {
		super(context, new BucketheadZombieModel(context.getPart(ModModelLayers.Zombie.BUCKETHEAD_ZOMBIE)), 0.5f);
	}

	@Override
	public Identifier getTexture(BucketheadZombieRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		BucketheadZombieRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public BucketheadZombieRenderState createRenderState() {
		return new BucketheadZombieRenderState();
	}

	@Override
	public void updateRenderState(BucketheadZombieEntity entity, BucketheadZombieRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.hasLostHeadwear = entity.hasTriggeredDegradationStage("headwear");
		state.hasLostArm = entity.hasTriggeredDegradationStage("arm");
		state.hasLostHead = entity.hasTriggeredDegradationStage("head");
		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.idleAnimationState.copyFrom(entity.idleAnimationState);
		state.attackAnimationState.copyFrom(entity.attackAnimationState);
		state.bucketSetupAnimationState.copyFrom(entity.bucketSetupAnimationState);
	}
}
