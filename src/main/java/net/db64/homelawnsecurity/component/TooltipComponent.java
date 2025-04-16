package net.db64.homelawnsecurity.component;


import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.text.Texts;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

import java.util.List;
import java.util.function.Consumer;

public record TooltipComponent(List<Text> lines, List<Text> styledLines) implements TooltipAppender {
	public static final TooltipComponent DEFAULT = new TooltipComponent(List.of());
	public static final int MAX_TOOLTIPS = 256;
	private static final Style STYLE;
	public static final Codec<TooltipComponent> CODEC;
	public static final PacketCodec<RegistryByteBuf, TooltipComponent> PACKET_CODEC;

	public TooltipComponent(List<Text> lines) {
		this(lines, Lists.transform(lines, style -> Texts.setStyleIfAbsent(style.copy(), STYLE)));
	}

	public TooltipComponent(List<Text> lines, List<Text> styledLines) {
		if (lines.size() > MAX_TOOLTIPS) {
			throw new IllegalArgumentException("Got " + lines.size() + " lines, but maximum is 256");
		} else {
			this.lines = lines;
			this.styledLines = styledLines;
		}
	}

	public TooltipComponent with(Text line) {
		return new TooltipComponent(Util.withAppended(this.lines, line));
	}

	@Override
	public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type, ComponentsAccess components) {
		this.styledLines.forEach(tooltip);
	}

	static {
		STYLE = Style.EMPTY.withColor(Formatting.GRAY);
		CODEC = TextCodecs.CODEC.sizeLimitedListOf(256).xmap(TooltipComponent::new, TooltipComponent::lines);
		PACKET_CODEC = TextCodecs.REGISTRY_PACKET_CODEC.collect(PacketCodecs.toList(256)).xmap(TooltipComponent::new, TooltipComponent::lines);
	}
}