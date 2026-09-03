package net.db64.homelawnsecurity.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;

import java.util.Objects;
import java.util.function.Consumer;

public record LawnGadgetComponent(String mode) implements TooltipAppender {
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

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type, ComponentsAccess components) {
		if (!Objects.equals(mode, "uninitialized"))
			tooltip.accept(Text.translatable("tooltip.item.homelawnsecurity.lawn_gadget.mode").formatted(Formatting.BLUE)
				.append(Text.translatable("item.homelawnsecurity.lawn_gadget.mode." + mode).formatted(Formatting.WHITE)));
	}

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
