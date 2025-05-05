package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.other.TargetZombieEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class TargetZombieRenderer extends MobEntityRenderer<TargetZombieEntity, TargetZombieRenderState, TargetZombieModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/target_zombie.png");

	public TargetZombieRenderer(EntityRendererFactory.Context context) {
		super(context, new TargetZombieModel(context.getPart(ModModelLayers.Zombie.TARGET_ZOMBIE)), 0.5f);
	}

	@Override
	public Identifier getTexture(TargetZombieRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(TargetZombieRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(state, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public TargetZombieRenderState createRenderState() {
		return new TargetZombieRenderState();
	}

	@Override
	public void updateRenderState(TargetZombieEntity entity, TargetZombieRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.hasLostArm = entity.hasTriggeredDegradationStage("arm");
		state.hasLostHead = entity.hasTriggeredDegradationStage("head");
		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.attackAnimationState.copyFrom(entity.attackAnimationState);
	}
}
