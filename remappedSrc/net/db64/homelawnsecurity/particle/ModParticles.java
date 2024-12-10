package net.db64.homelawnsecurity.particle;

import com.mojang.serialization.MapCodec;
import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModParticles {
	public static final ParticleType<BlockStateParticleEffect>
		MARKER = register(
		"marker", true, BlockStateParticleEffect::createCodec, BlockStateParticleEffect::createPacketCodec
	);

	public static void registerParticles() {
		HomeLawnSecurity.LOGGER.info("Registering particles for {}", HomeLawnSecurity.MOD_ID);
	}

	private static SimpleParticleType register(String name, boolean alwaysShow) {
		return Registry.register(Registries.PARTICLE_TYPE, name, FabricParticleTypes.simple(alwaysShow));
	}

	private static <T extends ParticleEffect> ParticleType<T> register(
		String name,
		boolean alwaysShow,
		Function<ParticleType<T>, MapCodec<T>> codecGetter,
		Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodecGetter
	) {
		return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(HomeLawnSecurity.MOD_ID, name), new ParticleType<T>(alwaysShow) {
			@Override
			public MapCodec<T> getCodec() {
				return codecGetter.apply(this);
			}

			@Override
			public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
				return packetCodecGetter.apply(this);
			}
		});
	}
}
