package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	/*@Shadow
	protected abstract <T extends TooltipAppender> void appendTooltip(
		ComponentType<T> componentType, Item.TooltipContext context, Consumer<Text> textConsumer, TooltipType type
	);*/

	@Shadow public abstract <T extends TooltipAppender> void appendComponentTooltip(ComponentType<T> componentType, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type);

	@Inject(
		method = "appendTooltip",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;appendComponentTooltip(Lnet/minecraft/component/ComponentType;Lnet/minecraft/item/Item$TooltipContext;Lnet/minecraft/component/type/TooltipDisplayComponent;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V",
			ordinal = 1
		)
	)
	public void appendTooltipFromComponents(Item.TooltipContext context, TooltipDisplayComponent displayComponent, @Nullable PlayerEntity player, TooltipType type, Consumer<Text> textConsumer, CallbackInfo ci, @Local Consumer<Text> consumer) {
		appendComponentTooltip(ModDataComponentTypes.TOOLTIP, context, displayComponent, consumer, type);
		appendComponentTooltip(ModDataComponentTypes.CURRENCY, context, displayComponent, consumer, type);
		appendComponentTooltip(ModDataComponentTypes.BAG_OF_CURRENCY, context, displayComponent, consumer, type);
	}
}
