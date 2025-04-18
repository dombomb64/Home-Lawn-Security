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
import net.minecraft.util.dynamic.Codecs;

import java.util.function.Consumer;

public record CurrencyComponent(int amount, String name) implements TooltipAppender {
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

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type, ComponentsAccess components) {
		tooltip.accept(Text.translatable("tooltip.homelawnsecurity.currency." + name, amount));
	}

	public static class Builder {
		private int amount;
		private String name;

		public CurrencyComponent.Builder amount(int amount) {
			this.amount = amount;
			return this;
		}

		public CurrencyComponent.Builder name(String name) {
			this.name = name;
			return this;
		}

		public CurrencyComponent build() {
			return new CurrencyComponent(this.amount, this.name);
		}
	}
}
