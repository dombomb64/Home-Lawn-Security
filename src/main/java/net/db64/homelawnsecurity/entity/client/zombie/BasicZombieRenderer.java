package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.client.plant.PeashooterRenderState;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
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
	public void render(BasicZombieRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(state, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public BasicZombieRenderState createRenderState() {
		return new BasicZombieRenderState();
	}

	@Override
	public void updateRenderState(BasicZombieEntity entity, BasicZombieRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.hasLostHeadwear = entity.getHasLostHeadwear();
		state.hasLostArm = entity.getHasLostArm();
		state.hasLostHead = entity.getHasLostHead();
		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.attackAnimationState.copyFrom(entity.attackAnimationState);
	}
}
