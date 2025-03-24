package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.block.custom.MarkerBlock;
import net.db64.homelawnsecurity.particle.ModParticles;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntityMixin {
	@Inject(
		method = "tick",
		at = @At("RETURN")
	)
	public void tickMarkerParticles(CallbackInfo ci) {
		for (ItemStack stack : getHandItems()) {
			if (stack.isIn(ModTags.Items.MARKERS)) {
				Iterable<BlockPos> iterable = BlockPos.iterate(MarkerBlock.PARTICLE_DISTANCE.offset(getBlockPos()));
				for (BlockPos blockPos : iterable) {
					World world = getWorld();
					BlockState state = world.getBlockState(blockPos);
					if (state.isIn(ModTags.Blocks.MARKERS)) {
						world.addParticle(new BlockStateParticleEffect(ModParticles.MARKER, state), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 0, 0, 0);
					}
				}
				break;
			}
		}
	}
}
