package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.db64.homelawnsecurity.entity.client.PvzRenderState;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FeatureRenderer.class)
public abstract class FeatureRendererMixin {
	@ModifyConstant(
		method = "renderModel",
		constant = @Constant(
			floatValue = 0f,
			ordinal = 0
		)
	)
	private static float applyWhiteOverlay(float constant, @Local(ordinal = 0, argsOnly = true) LivingEntityRenderState state) {
		if (state instanceof PvzRenderState state2) {
			return state2.getAnimationCounter();
		}
		return constant;
	}
}
