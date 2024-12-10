package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {
	@Shadow
	public abstract Iterable<ItemStack> getHandItems();

	@Inject(
		method = "damage",
		at = @At(
			value = "HEAD"
		)
	)
	private void changeZombieAttacksToEating(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0, argsOnly = true) LocalRef<DamageSource> sourceRef) {
		// If the damage is from a zombie's melee attack...
		//HomeLawnSecurity.LOGGER.info("zombie melee attack check, damage type name: " + sourceRef.get().getName() + ", attacker: " + sourceRef.get().getAttacker());
		if (sourceRef.get().isOf(DamageTypes.MOB_ATTACK) && (sourceRef.get().getAttacker() != null && sourceRef.get().getAttacker() instanceof ZombieEntity)) {
			// Change the damage type to zombie eat
			//HomeLawnSecurity.LOGGER.info("zombie melee attack.");
			DamageSource sauce = ((Entity) (Object) this).getDamageSources().create(ModDamageTypes.ZOMBIE_EAT, sourceRef.get().getAttacker());
			sourceRef.set(sauce);
		}
	}
}
