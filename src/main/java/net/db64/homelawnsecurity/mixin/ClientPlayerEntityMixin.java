package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.block.custom.MarkerBlock;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.db64.homelawnsecurity.item.custom.LawnGadgetItem;
import net.db64.homelawnsecurity.particle.custom.MarkerParticle;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntityMixin {
	@Final
	@Shadow
	protected MinecraftClient client;

	@Shadow public abstract boolean isSneaking();

	@Inject(
		method = "tick",
		at = @At("RETURN")
	)
	public void tickMarkerParticles(CallbackInfo ci) {
		List<ItemStack> handItems = List.of(getMainHandStack(), getOffHandStack());
		for (ItemStack stack : handItems){
			if (stack != null && MarkerBlock.shouldRevealMarkers(stack, isSneaking())) {
				Iterable<BlockPos> iterable = BlockPos.iterate(MarkerBlock.PARTICLE_DISTANCE.offset(getBlockPos()));
				for (BlockPos blockPos : iterable) {
					World world = getWorld();
					BlockState state = world.getBlockState(blockPos);
					if (state.isIn(ModTags.Blocks.MARKERS)) {
						//world.addParticleClient(new ItemStackParticleEffect(ModParticles.MARKER, stack), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 0, 0, 0);
						CurrencyEntity entity = new CurrencyEntity(ModEntities.Other.CURRENCY, world);
						entity.setStack(world.getBlockState(blockPos).getPickStack(world, blockPos, true));
						entity.refreshPositionAndAngles(blockPos.getX() + 0.5, blockPos.getY() + 0.4, blockPos.getZ() + 0.5, 0, 0);
						client.particleManager.addParticle(new MarkerParticle(client.getEntityRenderDispatcher(), (ClientWorld) world, entity));

						/*float tickProgress = client.getRenderTickCounter().getTickProgress(!world.getTickManager().shouldSkipTick(entity));
						client.getEntityRenderDispatcher().render(entity, entity.lastRenderX, entity.lastRenderY, entity.lastRenderZ, tickProgress);*/

						entity.discard();
					}
				}
				break;
			}
		}
	}
}
