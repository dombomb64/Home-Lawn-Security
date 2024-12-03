package net.db64.homelawnsecurity.component;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
	public static final ComponentType<CurrencyComponent> CURRENCY =
		register("currency", builder -> builder.codec(CurrencyComponent.CODEC).packetCodec(CurrencyComponent.PACKET_CODEC));

	public static final ComponentType<SeedPacketComponent> SEED_PACKET =
		register("seed_packet", builder -> builder.codec(SeedPacketComponent.CODEC).packetCodec(SeedPacketComponent.PACKET_CODEC));

	private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(HomeLawnSecurity.MOD_ID, name),
			builderOperator.apply(ComponentType.builder()).build());
	}

	public static void registerDataComponentTypes() {
		HomeLawnSecurity.LOGGER.info("Registering Item Components for " + HomeLawnSecurity.MOD_ID);
	}
}
