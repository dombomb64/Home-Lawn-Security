package net.db64.homelawnsecurity.debug;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.debug.DebugSubscriptionType;
import net.minecraft.world.debug.data.EntityPathDebugData;

public class ModDebugSubscriptionTypes {
	public static final DebugSubscriptionType<EntityPathDebugData> PVZ_PATHS = register("pvz_paths", EntityPathDebugData.PACKET_CODEC);

	private static <T> DebugSubscriptionType<T> register(String id, PacketCodec<? super RegistryByteBuf, T> packetCodec) {
		return Registry.register(Registries.DEBUG_SUBSCRIPTION, Identifier.of(HomeLawnSecurity.MOD_ID, id), new DebugSubscriptionType<>(packetCodec));
	}

	public static void initialize() {

	}
}
