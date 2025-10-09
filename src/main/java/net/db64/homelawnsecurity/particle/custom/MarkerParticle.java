package net.db64.homelawnsecurity.particle.custom;

import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.client.other.CurrencyRenderState;
import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.db64.homelawnsecurity.particle.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class MarkerParticle extends Particle {
	//protected List<Sprite> sprites;
	//protected int curSprite = 0;

	protected int ticksExisted;
	public float alpha; // HOW DO I FORCE AN ITEM TO RENDER TRANSLUCENT???

	//private final Entity itemEntity;
	protected final EntityRenderState renderState;

	public MarkerParticle(ClientWorld world, EntityRenderState renderState, Entity itemEntity) {
		super(world, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ());
		//this.itemEntity = this.getOrCopy(itemEntity);
		this.renderState = renderState;
		//setSprite(MinecraftClient.getInstance().getBlockRenderManager().getModels().getModelParticleSprite(stack));
		//this.sprites = sprites;
		gravityStrength = 0.0F;
		//maxAge = 2;
		maxAge = 0;
		collidesWithWorld = false;

		alpha = 0.5f;
	}

	@Override
	public ParticleTextureSheet textureSheet() {
		return ModParticles.SHEET_ENTITY;
	}

	/*private Entity getOrCopy(Entity entity) {
		return !(entity instanceof ItemEntity) ? entity : ((ItemEntity)entity).copy();
	}*/

	/*@Override
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
	}*/

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

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<ItemStackParticleEffect> {
		@Nullable
		@Override
		public Particle createParticle(ItemStackParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Random random) {
			CurrencyEntity entity = new CurrencyEntity(x, y, z, world, parameters.getItemStack());
			//CurrencyRenderState renderState = new CurrencyRenderState();
			//renderState.entityType = ModEntities.Other.CURRENCY;
			EntityRenderState renderState = MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).getAndUpdateRenderState(entity, 0f);
			return new MarkerParticle(world, renderState, entity);
		}
	}
}
