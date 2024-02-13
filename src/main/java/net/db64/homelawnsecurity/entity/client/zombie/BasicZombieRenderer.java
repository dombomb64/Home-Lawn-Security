package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BasicZombieRenderer extends MobEntityRenderer<BasicZombieEntity, BasicZombieModel<BasicZombieEntity>> {
	private static final Identifier TEXTURE = new Identifier(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/basic_zombie.png");

	public BasicZombieRenderer(EntityRendererFactory.Context context) {
		super(context, new BasicZombieModel<>(context.getPart(ModModelLayers.Zombie.BASIC_ZOMBIE)), 0.5f);
	}

	@Override
	public Identifier getTexture(BasicZombieEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(BasicZombieEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
