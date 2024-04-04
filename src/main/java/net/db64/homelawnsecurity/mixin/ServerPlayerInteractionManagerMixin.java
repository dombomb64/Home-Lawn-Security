package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.item.custom.SeedPacketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
	@Inject(
		method = "interactBlock",
		at = @At(
			value = "RETURN",
			ordinal = 4
		)
	)
	private void playBuzzerSound(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
		if (!stack.isEmpty() && player.getItemCooldownManager().isCoolingDown(stack.getItem()) && stack.getItem() instanceof SeedPacketItem) {
			SeedPacketItem.playBuzzerSound(world, hitResult.getBlockPos());
		}
	}
}
