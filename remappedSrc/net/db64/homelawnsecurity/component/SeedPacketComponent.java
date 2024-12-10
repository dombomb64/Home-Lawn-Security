package net.db64.homelawnsecurity.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;

public record SeedPacketComponent(int cost, int cooldown) {
	public static final Codec<SeedPacketComponent> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codecs.NON_NEGATIVE_INT.fieldOf("cost").forGetter(SeedPacketComponent::cost),
				Codecs.NON_NEGATIVE_INT.fieldOf("cooldown").forGetter(SeedPacketComponent::cooldown)
			)
			.apply(instance, SeedPacketComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, SeedPacketComponent> PACKET_CODEC = PacketCodec.tuple(
		PacketCodecs.VAR_INT,
		SeedPacketComponent::cost,
		PacketCodecs.VAR_INT,
		SeedPacketComponent::cooldown,
		SeedPacketComponent::new
	);

	public static class Builder {
		private int cost;
		private int cooldown;

		public SeedPacketComponent.Builder cost(int cost) {
			this.cost = cost;
			return this;
		}

		public SeedPacketComponent.Builder cooldown(int cooldown) {
			this.cooldown = cooldown;
			return this;
		}

		public SeedPacketComponent build() {
			return new SeedPacketComponent(this.cost, this.cooldown);
		}
	}
}
