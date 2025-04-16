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

public record BagOfCurrencyComponent(int amount, String name) implements TooltipAppender {
	public static final Codec<BagOfCurrencyComponent> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(BagOfCurrencyComponent::amount),
				Codecs.NON_EMPTY_STRING.fieldOf("name").forGetter(BagOfCurrencyComponent::name)
			)
			.apply(instance, BagOfCurrencyComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, BagOfCurrencyComponent> PACKET_CODEC = PacketCodec.tuple(
		PacketCodecs.VAR_INT,
		BagOfCurrencyComponent::amount,
		PacketCodecs.STRING,
		BagOfCurrencyComponent::name,
		BagOfCurrencyComponent::new
	);

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type, ComponentsAccess components) {
		tooltip.accept(Text.translatable("tooltip.homelawnsecurity.bag_of_" + name, amount));
	}

	public static class Builder {
		private int amount;
		private String name;

		public BagOfCurrencyComponent.Builder amount(int amount) {
			this.amount = amount;
			return this;
		}

		public BagOfCurrencyComponent.Builder name(String name) {
			this.name = name;
			return this;
		}

		public BagOfCurrencyComponent build() {
			return new BagOfCurrencyComponent(this.amount, this.name);
		}
	}
}
