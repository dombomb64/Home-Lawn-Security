package net.db64.homelawnsecurity.entity.client.projectile;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.projectile.PeaEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class PeaRenderer extends EntityRenderer<PeaEntity> {
	private static final Identifier TEXTURE = new Identifier(HomeLawnSecurity.MOD_ID, "textures/entity/projectile/pea.png");
	private static final RenderLayer LAYER = RenderLayer.getEntityTranslucent(TEXTURE);
	private final PeaModel<PeaEntity> model;

	public PeaRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.model = new PeaModel<>(context.getPart(ModModelLayers.Projectile.PEA));
	}

	@Override
	public Identifier getTexture(PeaEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(PeaEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();

		float h = MathHelper.lerpAngleDegrees(g, mobEntity.prevYaw, mobEntity.getYaw());
		float j = MathHelper.lerp(g, mobEntity.prevPitch, mobEntity.getPitch());

		this.model.setAngles(mobEntity, 0.0f, 0.0f, 0.0f, h, j);

		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(TEXTURE));

		this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

		matrixStack.pop();
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}