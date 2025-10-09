package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.particle.ModParticles;
import net.db64.homelawnsecurity.particle.custom.EntityParticleRenderer;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.ParticleRenderer;
import net.minecraft.client.particle.ParticleTextureSheet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin {
	@Inject(
		method = "Lnet/minecraft/client/particle/ParticleManager;createParticleRenderer(Lnet/minecraft/client/particle/ParticleTextureSheet;)Lnet/minecraft/client/particle/ParticleRenderer;",
		at = @At(
			value = "RETURN",
			ordinal = 2
		)
	)
	public void addParticleTypes(ParticleTextureSheet textureSheet, CallbackInfoReturnable<ParticleRenderer<?>> cir) {
		if (textureSheet == ModParticles.SHEET_ENTITY) {
			cir.setReturnValue(new EntityParticleRenderer((ParticleManager) (Object) this));
		}
	}
}
