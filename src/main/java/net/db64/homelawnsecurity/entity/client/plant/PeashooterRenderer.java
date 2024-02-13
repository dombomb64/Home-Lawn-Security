package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.plant.PeashooterEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class PeashooterRenderer extends MobEntityRenderer<PeashooterEntity, PeashooterModel<PeashooterEntity>> {
	private static final Identifier TEXTURE = new Identifier(HomeLawnSecurity.MOD_ID, "textures/entity/plant/peashooter.png");

	public PeashooterRenderer(EntityRendererFactory.Context context) {
		super(context, new PeashooterModel<>(context.getPart(ModModelLayers.Plant.PEASHOOTER)), 0.5f);
	}

	@Override
	public Identifier getTexture(PeashooterEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(PeashooterEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
