package net.db64.homelawnsecurity.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.db64.homelawnsecurity.util.ModFonts;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.Consumer;

public record SeedPacketComponent(int cost, int cooldown, String currencyName) implements TooltipAppender {
	public static final Codec<SeedPacketComponent> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codecs.NON_NEGATIVE_INT.fieldOf("cost").forGetter(SeedPacketComponent::cost),
				Codecs.NON_NEGATIVE_INT.fieldOf("cooldown").forGetter(SeedPacketComponent::cooldown),
				Codecs.NON_EMPTY_STRING.fieldOf("currency_name").forGetter(SeedPacketComponent::currencyName)
			)
			.apply(instance, SeedPacketComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, SeedPacketComponent> PACKET_CODEC = PacketCodec.tuple(
		PacketCodecs.VAR_INT,
		SeedPacketComponent::cost,
		PacketCodecs.VAR_INT,
		SeedPacketComponent::cooldown,
		PacketCodecs.STRING,
		SeedPacketComponent::currencyName,
		SeedPacketComponent::new
	);

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type, ComponentsAccess components) {
		tooltip.accept(Text.translatable(
			"tooltip.item.homelawnsecurity.seed_packet.cost",
			Text.literal("" + cost()),
			Text.translatable("tooltip.item.homelawnsecurity.seed_packet.currency." + currencyName()).setStyle(Style.EMPTY.withFont(ModFonts.EMOJI))
		));
		tooltip.accept(Text.translatable(
			"tooltip.item.homelawnsecurity.seed_packet.cooldown",
			Text.literal("" + (cooldown() * 0.05))
		));
	}

	public static class Builder {
		private int cost;
		private int cooldown;
		private String currencyName;

		public SeedPacketComponent.Builder cost(int cost) {
			this.cost = cost;
			return this;
		}

		public SeedPacketComponent.Builder cooldown(int cooldown) {
			this.cooldown = cooldown;
			return this;
		}

		public SeedPacketComponent.Builder currencyName(String currencyName) {
			this.currencyName = currencyName;
			return this;
		}

		public SeedPacketComponent build() {
			return new SeedPacketComponent(this.cost, this.cooldown, this.currencyName);
		}
	}
}
