package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.db64.homelawnsecurity.debug.ModDebugSubscriptionTypes;
import net.minecraft.client.network.ClientDebugSubscriptionManager;
import net.minecraft.world.debug.DebugSubscriptionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(ClientDebugSubscriptionManager.class)
public abstract class ClientDebugSubscriptionManagerMixin {
	@Shadow
	private static void addDebugSubscription(Set<DebugSubscriptionType<?>> types, DebugSubscriptionType<?> type, boolean enable) {
	}

	@Inject(
		method = "getRequestedSubscriptions",
		at = @At(
			value = "RETURN"
		)
	)
	public void addPvzDebugSubscription(CallbackInfoReturnable<Set<DebugSubscriptionType<?>>> cir, @Local Set<DebugSubscriptionType<?>> set) {
		addDebugSubscription(set, ModDebugSubscriptionTypes.PVZ_PATHS, true);
	}
}
