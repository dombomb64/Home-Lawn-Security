package net.db64.homelawnsecurity.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record SnapLookDirS2CPayload(UUID uuid, float pitch, float yaw) implements CustomPayload {
	public static final CustomPayload.Id<SnapLookDirS2CPayload> ID = new CustomPayload.Id<>(ModNetworkingConstants.SNAP_LOOK_DIR_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, SnapLookDirS2CPayload> CODEC = PacketCodec.tuple(
		Uuids.PACKET_CODEC, SnapLookDirS2CPayload::uuid,
		PacketCodecs.FLOAT, SnapLookDirS2CPayload::pitch,
		PacketCodecs.FLOAT, SnapLookDirS2CPayload::yaw,
		SnapLookDirS2CPayload::new);

	@Override
	public CustomPayload.Id<? extends CustomPayload> getId() {
		return ID;
	}
}