package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.zombie.ZombieGravestoneEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ZombieGravestoneRenderer extends MobEntityRenderer<ZombieGravestoneEntity, ZombieGravestoneRenderState, ZombieGravestoneModel> {
	private static final Identifier TEXTURE = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/zombie_gravestone.png");
	private static final Identifier TEXTURE_CRACKS = Identifier.of(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/zombie_gravestone_cracks.png");

	public ZombieGravestoneRenderer(EntityRendererFactory.Context context) {
		super(context, new ZombieGravestoneModel(context.getPart(ModModelLayers.Zombie.ZOMBIE_GRAVESTONE)), 0.5f);
		this.addFeature(
			new ZombieGravestoneCracksFeatureRenderer<>(
				this,
				new ZombieGravestoneModel(context.getPart(ModModelLayers.Zombie.ZOMBIE_GRAVESTONE_CRACKS)),
				TEXTURE_CRACKS
			)
		);
	}

	@Override
	public Identifier getTexture(ZombieGravestoneRenderState state) {
		return TEXTURE;
	}

	@Override
	public void render(ZombieGravestoneRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(state, matrixStack, vertexConsumerProvider, i);
	}

	@Override
	public ZombieGravestoneRenderState createRenderState() {
		return new ZombieGravestoneRenderState();
	}

	@Override
	public void updateRenderState(ZombieGravestoneEntity entity, ZombieGravestoneRenderState state, float f) {
		super.updateRenderState(entity, state, f);

		state.setupAnimationState.copyFrom(entity.setupAnimationState);
		state.health = entity.getHealth();
		state.cracks = entity.hasTriggeredDegradationStage("cracks");
		state.break1 = entity.hasTriggeredDegradationStage("break1");
		state.break2 = entity.hasTriggeredDegradationStage("break2");
		state.break3 = entity.hasTriggeredDegradationStage("break3");
		state.break4 = entity.hasTriggeredDegradationStage("break4");
	}
}
