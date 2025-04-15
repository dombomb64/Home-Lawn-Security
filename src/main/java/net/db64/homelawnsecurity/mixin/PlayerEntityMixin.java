package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
		World world = getWorld();
		if (world.isClient) {
			ItemStack itemStack = this.getMainHandStack();
			if (!ItemStack.areEqual(this.selectedItem, itemStack)) {
				if (itemStack.contains(ModDataComponentTypes.SHOVEL)) {
					world.playSoundFromEntity((PlayerEntity) (Object) this, ModSounds.RANDOM_SHOVEL, SoundCategory.PLAYERS, 0.25f, random.nextFloat() * 0.4f + 0.8f);
				}
				else if (itemStack.contains(ModDataComponentTypes.SEED_PACKET)) {
					world.playSoundFromEntity((PlayerEntity) (Object) this, ModSounds.RANDOM_SELECT_SEED, SoundCategory.PLAYERS, 0.25f, random.nextFloat() * 0.4f + 0.8f);
				}
			}
		}
	}
}
