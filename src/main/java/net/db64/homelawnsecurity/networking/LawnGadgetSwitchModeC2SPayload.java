package net.db64.homelawnsecurity.networking;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record LawnGadgetSwitchModeC2SPayload(UUID uuid) implements CustomPayload {
	public static final Id<LawnGadgetSwitchModeC2SPayload> ID = new Id<>(ModNetworkingConstants.LAWN_GADGET_SWITCH_MODE_PACKET_ID);
	public static final PacketCodec<RegistryByteBuf, LawnGadgetSwitchModeC2SPayload> CODEC = PacketCodec.tuple(
		Uuids.PACKET_CODEC, LawnGadgetSwitchModeC2SPayload::uuid,
		LawnGadgetSwitchModeC2SPayload::new);

	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}