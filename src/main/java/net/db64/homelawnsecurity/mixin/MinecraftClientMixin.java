package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.db64.homelawnsecurity.component.LawnGadgetComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.item.custom.LawnGadgetItem;
import net.db64.homelawnsecurity.networking.LawnGadgetSwitchModeC2SPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
	@Shadow
	@Nullable
	public ClientPlayerEntity player;

	@Inject(
		method = "doAttack",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/item/ItemStack;get(Lnet/minecraft/component/ComponentType;)Ljava/lang/Object;",
			ordinal = 0
		),
		cancellable = true
	)
	public void redirectAttacksForLawnGadget(CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) ItemStack itemStack) {
		if (itemStack.getItem() instanceof LawnGadgetItem) {
			ClientPlayNetworking.send(new LawnGadgetSwitchModeC2SPayload(player.getUuid()));
			cir.setReturnValue(false);
		}
	}
}
