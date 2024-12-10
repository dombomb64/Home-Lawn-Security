package net.db64.homelawnsecurity.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.BlockStateParticleEffect;
import org.jetbrains.annotations.Nullable;

public class MarkerParticle extends SpriteBillboardParticle {
	protected MarkerParticle(ClientWorld world, double x, double y, double z, BlockState state) {
		super(world, x, y, z);
		setSprite(MinecraftClient.getInstance().getBlockRenderManager().getModels().getModelParticleSprite(state));
		gravityStrength = 0.0F;
		maxAge = 2;
		collidesWithWorld = false;

		alpha = 0.5f;
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.TERRAIN_SHEET;
	}

	@Override
	public float getSize(float tickDelta) {
		return 0.3f;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<BlockStateParticleEffect> {
		@Nullable
		@Override
		public Particle createParticle(BlockStateParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			return new MarkerParticle(world, x, y, z, parameters.getBlockState());
		}
	}
}
