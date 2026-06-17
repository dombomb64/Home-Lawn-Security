package net.db64.homelawnsecurity.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record ChangeEntityStateS2CPayload(UUID uuid, String state) implements CustomPayload {
	public static final Id<ChangeEntityStateS2CPayload> ID = new Id<>(ModNetworkingConstants.CHANGE_ENTITY_STATE_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, ChangeEntityStateS2CPayload> CODEC = PacketCodec.tuple(
		Uuids.PACKET_CODEC, ChangeEntityStateS2CPayload::uuid,
		PacketCodecs.STRING, ChangeEntityStateS2CPayload::state,
		ChangeEntityStateS2CPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}