package net.db64.homelawnsecurity.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record PlayerDestroyedMowerS2CPayload(UUID uuid) implements CustomPayload {
	public static final Id<PlayerDestroyedMowerS2CPayload> ID = new Id<>(ModNetworkingConstants.PLAYER_DESTROYED_MOWER_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, PlayerDestroyedMowerS2CPayload> CODEC = PacketCodec.tuple(
		Uuids.PACKET_CODEC, PlayerDestroyedMowerS2CPayload::uuid,
		PlayerDestroyedMowerS2CPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}