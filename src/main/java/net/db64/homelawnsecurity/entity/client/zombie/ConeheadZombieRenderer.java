package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.client.ModModelLayers;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ConeheadZombieEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ConeheadZombieRenderer extends MobEntityRenderer<ConeheadZombieEntity, ConeheadZombieModel<ConeheadZombieEntity>> {
	private static final Identifier TEXTURE = new Identifier(HomeLawnSecurity.MOD_ID, "textures/entity/zombie/conehead_zombie.png");

	public ConeheadZombieRenderer(EntityRendererFactory.Context context) {
		super(context, new ConeheadZombieModel<>(context.getPart(ModModelLayers.Zombie.CONEHEAD_ZOMBIE)), 0.5f);
	}

	@Override
	public Identifier getTexture(ConeheadZombieEntity entity) {
		return TEXTURE;
	}

	@Override
	public void render(ConeheadZombieEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}
}
