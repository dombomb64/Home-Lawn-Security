package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.block.custom.lawn.ILawnBlock;
import net.db64.homelawnsecurity.block.custom.lawn.SoddedLawnBlockBlock;
import net.db64.homelawnsecurity.block.custom.lawn.SoddedLawnMarkerBlock;
import net.db64.homelawnsecurity.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public abstract class ShearsItemMixin {
	@Inject(
		method = "Lnet/minecraft/item/ShearsItem;useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;",
		at = @At(
			value = "RETURN",
			ordinal = 1
		),
		cancellable = true
	)
	public void removeTurf(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
		World world = context.getWorld();
		if (!world.isClient()) {
			BlockPos pos = context.getBlockPos();
			BlockState state = world.getBlockState(pos);

			boolean turfRemoved = false;
			if (state.getBlock() instanceof SoddedLawnBlockBlock) {
				world.setBlockState(pos, ModBlocks.UNSODDED_LAWN_BLOCK.getDefaultState()
					.with(ILawnBlock.PATH_ID_MAIN, state.get(ILawnBlock.PATH_ID_MAIN))
					.with(ILawnBlock.PATH_ID_INTERSECTING, state.get(ILawnBlock.PATH_ID_INTERSECTING))
				);
				turfRemoved = true;
			}
			else if (state.getBlock() instanceof SoddedLawnMarkerBlock) {
				world.setBlockState(pos, ModBlocks.UNSODDED_LAWN_MARKER.getDefaultState()
					.with(ILawnBlock.PATH_ID_MAIN, state.get(ILawnBlock.PATH_ID_MAIN))
					.with(ILawnBlock.PATH_ID_INTERSECTING, state.get(ILawnBlock.PATH_ID_INTERSECTING))
				);
				turfRemoved = true;
			}

			if (turfRemoved) {
				world.playSound(null, pos, state.getSoundGroup().getBreakSound(), SoundCategory.BLOCKS);

				boolean creative = false;
				PlayerEntity user = context.getPlayer();
				if (user != null) {
					context.getStack().damage(1, user);
					creative = user.isCreative();
				}

				if (!creative) {
					Vec3d itemPos = context.getHitPos();
					ItemEntity itemEntity = new ItemEntity(world, itemPos.x, itemPos.y, itemPos.z, ModItems.TURF.getDefaultStack().copy());
					Random random = itemEntity.getRandom();
					itemEntity.setVelocity(random.nextFloat() * 0.1f - 0.05f, 0.1f, random.nextFloat() * 0.1f - 0.05f);
					world.spawnEntity(itemEntity);
				}

				cir.setReturnValue(ActionResult.SUCCESS_SERVER);
			}
		}
	}
}
