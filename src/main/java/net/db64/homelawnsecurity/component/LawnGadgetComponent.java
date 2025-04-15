package net.db64.homelawnsecurity.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.dynamic.Codecs;

public record LawnGadgetComponent(String mode) {
	public static final Codec<LawnGadgetComponent> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codecs.NON_EMPTY_STRING.fieldOf("mode").forGetter(LawnGadgetComponent::mode)
			)
			.apply(instance, LawnGadgetComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, LawnGadgetComponent> PACKET_CODEC = PacketCodec.tuple(
		PacketCodecs.STRING,
		LawnGadgetComponent::mode,
		LawnGadgetComponent::new
	);

	public static class Builder {
		private String mode;

		public LawnGadgetComponent.Builder mode(String mode) {
			this.mode = mode;
			return this;
		}

		public LawnGadgetComponent build() {
			return new LawnGadgetComponent(this.mode);
		}
	}
}
