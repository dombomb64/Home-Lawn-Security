package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.client.plant.PeashooterRenderState;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BasicZombieRenderer extends MobEntityRenderer<BasicZombieEntity, BasicZombieRenderState, BasicZombieModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/basic_zombie.png");

	public BasicZombieRenderer(EntityRendererFactory.Context context) {
		super(context, new BasicZombieModel(context.getPart(ModModelLayers.Zombie.BASIC_ZOMBIE)), 0.5f);
	}

	@Override
	public Identifier getTexture(BasicZombieRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(
		BasicZombieRenderState state,
		MatrixStack matrixStack,
		OrderedRenderCommandQueue orderedRenderCommandQueue,
		CameraRenderState cameraRenderState
	) {
		super.render(state, matrixStack, orderedRenderCommandQueue, cameraRenderState);
	}

	@Override
	public BasicZombieRenderState createRenderState() {
		return new BasicZombieRenderState();
	}

	@Override
	public void updateRenderState(BasicZombieEntity entity, BasicZombieRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.hasLostArm = entity.hasTriggeredDegradationStage("arm");
		state.hasLostHead = entity.hasTriggeredDegradationStage("head");
		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.attackAnimationState.copyFrom(entity.attackAnimationState);
	}
}
