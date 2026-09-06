package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.custom.PathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.db64.homelawnsecurity.particle.ModParticles;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.MiscUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
	@Shadow
	private ItemStack selectedItem;

	@Inject(
		method = "tick",
		at = @At(
			value = "HEAD"
		)
	)
	public void makeItemSounds(CallbackInfo ci) {
		World world = getEntityWorld();
		if (world.isClient()) {
			ItemStack itemStack = this.getMainHandStack();
			if (!ItemStack.areEqual(this.selectedItem, itemStack)) {
				if (itemStack.contains(ModDataComponentTypes.SHOVEL)) {
					world.playSoundFromEntityClient(
						(PlayerEntity) (Object) this,
						ModSounds.RANDOM_SHOVEL,
						getSoundCategory(),
						0.25f,
						MiscUtil.getSoundPitch(getRandom())
					);
				}
				else if (itemStack.contains(ModDataComponentTypes.SEED_PACKET)) {
					world.playSoundFromEntityClient(
						(PlayerEntity) (Object) this,
						ModSounds.RANDOM_SELECT_SEED,
						getSoundCategory(),
						0.25f,
						MiscUtil.getSoundPitch(getRandom())
					);
				}
			}
		}
	}

	@Definition(id = "target", local = @Local(type = Entity.class, argsOnly = true))
	@Definition(id = "LivingEntity", type = LivingEntity.class)
	@Expression("target instanceof LivingEntity")
	@ModifyExpressionValue(
		method = "handleAttackDamage",
		at = @At(
			value = "MIXINEXTRAS:EXPRESSION"
		)
	)
	public boolean mowerBlockDamageStatAndParticles(boolean original, @Local(argsOnly = true) Entity target) {
		boolean replaceParticles = getMainHandStack().get(ModDataComponentTypes.SHOVEL) != null
			&& (target instanceof LawnMowerEntity);
		if (replaceParticles && getEntityWorld() instanceof ServerWorld world) {
			spawnBonkParticle(world, target);
		}
		return original && !(target instanceof LawnMowerEntity);
	}

	@Definition(id = "f", local = @Local(type = float.class, ordinal = 1))
	@Expression("f > 2.0")
	@ModifyExpressionValue(
		method = "handleAttackDamage",
		at = @At(
			value = "MIXINEXTRAS:EXPRESSION"
		)
	)
	public boolean shovelReplaceDamageParticles(boolean original, @Local(argsOnly = true) Entity target) {
		boolean replaceParticles = getMainHandStack().get(ModDataComponentTypes.SHOVEL) != null
			&& (target instanceof PathBoundEntity);
		if (replaceParticles && getEntityWorld() instanceof ServerWorld world) {
			spawnBonkParticle(world, target);
			return false;
		}
		return original;
	}

	@Unique
	public void spawnBonkParticle(ServerWorld world, Entity target) {
		world.spawnParticles(
			ModParticles.BONK,
			target.getX(),
			target.getBodyY(0.5),
			target.getZ(),
			1,
			0d,
			0d,
			0d,
			0d
		);
	}
}
