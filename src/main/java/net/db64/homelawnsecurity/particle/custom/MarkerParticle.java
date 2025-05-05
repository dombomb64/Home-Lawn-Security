package net.db64.homelawnsecurity.particle.custom;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class MarkerParticle extends Particle {
	//protected List<Sprite> sprites;
	//protected int curSprite = 0;

	private final Entity itemEntity;
	private final EntityRenderDispatcher dispatcher;

	public MarkerParticle(EntityRenderDispatcher dispatcher, ClientWorld world, Entity itemEntity) {
		super(world, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
		this.itemEntity = this.getOrCopy(itemEntity);
		this.dispatcher = dispatcher;
		//setSprite(MinecraftClient.getInstance().getBlockRenderManager().getModels().getModelParticleSprite(stack));
		//this.sprites = sprites;
		gravityStrength = 0.0F;
		//maxAge = 2;
		maxAge = 0;
		collidesWithWorld = false;

		alpha = 0.5f;
	}

	private Entity getOrCopy(Entity entity) {
		return !(entity instanceof ItemEntity) ? entity : ((ItemEntity)entity).copy();
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.CUSTOM;
	}

	@Override
	public void render(VertexConsumer vertexConsumer, Camera camera, float tickProgress) {
	}

	@Override
	public void renderCustom(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Camera camera, float tickProgress) {
		Vec3d cameraPos = camera.getPos();
		this.dispatcher
			.render(
				this.itemEntity,
				itemEntity.getX() - cameraPos.getX(),
				itemEntity.getY() - cameraPos.getY(),
				itemEntity.getZ() - cameraPos.getZ(),
				tickProgress,
				new MatrixStack(),
				vertexConsumers,
				this.dispatcher.getLight(this.itemEntity, tickProgress)
			);
	}

	/*@Override
	public float getSize(float tickDelta) {
		return 0.3f;
	}*/

	/*@Override
	protected float getMinU() {
		return this.sprites.get(curSprite).getMinU();
	}

	@Override
	protected float getMaxU() {
		return this.sprites.get(curSprite).getMaxU();
	}

	@Override
	protected float getMinV() {
		return this.sprites.get(curSprite).getMinV();
	}

	@Override
	protected float getMaxV() {
		return this.sprites.get(curSprite).getMaxV();
	}*/

	/*@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<ItemStackParticleEffect> {
		@Nullable
		@Override
		public Particle createParticle(ItemStackParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			return new MarkerParticle(world, x, y, z, parameters.getItemStack());
		}
	}*/
}
