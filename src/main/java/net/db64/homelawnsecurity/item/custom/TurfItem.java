package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.block.custom.lawn.ILawnBlock;
import net.db64.homelawnsecurity.block.custom.lawn.UnsoddedLawnBlockBlock;
import net.db64.homelawnsecurity.block.custom.lawn.UnsoddedLawnMarkerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TurfItem extends Item {
	public TurfItem(Settings settings) {
		super(settings);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos pos = context.getBlockPos();
		BlockState state = world.getBlockState(pos);

		if (!world.isClient()) {
			BlockState newState = null;
			boolean turfAdded = false;

			if (state.getBlock() instanceof UnsoddedLawnBlockBlock) {
				newState = ModBlocks.SODDED_LAWN_BLOCK.getDefaultState()
				.with(ILawnBlock.PATH_ID_MAIN, state.get(ILawnBlock.PATH_ID_MAIN))
				.with(ILawnBlock.PATH_ID_INTERSECTING, state.get(ILawnBlock.PATH_ID_INTERSECTING));
				turfAdded = true;
			}
			else if (state.getBlock() instanceof UnsoddedLawnMarkerBlock) {
				newState = ModBlocks.SODDED_LAWN_MARKER.getDefaultState()
					.with(ILawnBlock.PATH_ID_MAIN, state.get(ILawnBlock.PATH_ID_MAIN))
					.with(ILawnBlock.PATH_ID_INTERSECTING, state.get(ILawnBlock.PATH_ID_INTERSECTING));
				turfAdded = true;
			}

			if (turfAdded) {
				world.setBlockState(pos, newState);
				world.playSound(null, pos, newState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS);
				context.getStack().decrementUnlessCreative(1, context.getPlayer());

				return ActionResult.SUCCESS_SERVER;
			}
		}

		return super.useOnBlock(context);
	}
}
