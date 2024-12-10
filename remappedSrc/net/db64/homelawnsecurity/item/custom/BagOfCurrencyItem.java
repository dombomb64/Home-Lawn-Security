package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class BagOfCurrencyItem extends Item {
	public BagOfCurrencyItem(net.minecraft.item.Item.Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);

		var currencyComponent = stack.get(ModDataComponentTypes.CURRENCY);
		if (currencyComponent != null)
			tooltip.add(Text.translatable("tooltip.homelawnsecurity.bag_of_" + currencyComponent.name(), currencyComponent.amount()));
	}
}
