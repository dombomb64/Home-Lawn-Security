package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
	@Inject(
		method = "canMine",
		at = @At(
			value = "RETURN"
		),
		cancellable = true
	)
	public void restrictShovelMiningInCreative(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity user, CallbackInfoReturnable<Boolean> cir) {
		if (stack.get(ModDataComponentTypes.SHOVEL) != null && user.isInCreativeMode()) {
			// Item is a plant/zombie shovel, prevent the creative player from breaking things if they could instantly break something on miss
			cir.setReturnValue(false);
		}
	}
}
