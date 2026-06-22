package net.db64.homelawnsecurity.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record EntityDamagedS2CPayload(UUID uuid, boolean lastAttackedByPvzEntity, int hurtFlashTime) implements CustomPayload {
	public static final Id<EntityDamagedS2CPayload> ID = new Id<>(ModNetworkingConstants.ENTITY_DAMAGED_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, EntityDamagedS2CPayload> CODEC = PacketCodec.tuple(
		Uuids.PACKET_CODEC, EntityDamagedS2CPayload::uuid,
		PacketCodecs.BOOLEAN, EntityDamagedS2CPayload::lastAttackedByPvzEntity,
		PacketCodecs.INTEGER, EntityDamagedS2CPayload::hurtFlashTime,
		EntityDamagedS2CPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}