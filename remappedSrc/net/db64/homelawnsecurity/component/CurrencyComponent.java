package net.db64.homelawnsecurity.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;

public record CurrencyComponent(int amount, String name) {
	public static final Codec<CurrencyComponent> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(CurrencyComponent::amount),
				Codecs.NON_EMPTY_STRING.fieldOf("name").forGetter(CurrencyComponent::name)
			)
			.apply(instance, CurrencyComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, CurrencyComponent> PACKET_CODEC = PacketCodec.tuple(
		PacketCodecs.VAR_INT,
		CurrencyComponent::amount,
		PacketCodecs.STRING,
		CurrencyComponent::name,
		CurrencyComponent::new
	);

	public static class Builder {
		private int amount;
		private String name;

		public CurrencyComponent.Builder cost(int amount) {
			this.amount = amount;
			return this;
		}

		public CurrencyComponent.Builder cooldown(String name) {
			this.name = name;
			return this;
		}

		public CurrencyComponent build() {
			return new CurrencyComponent(this.amount, this.name);
		}
	}
}
