package net.db64.homelawnsecurity.block.custom.lawn;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ILawnBlock {
	int PATH_TYPE_AMOUNT = 4;

	IntProperty PATH_ID_MAIN = IntProperty.of("path_id_main", 0, PATH_TYPE_AMOUNT);
	IntProperty PATH_ID_INTERSECTING = IntProperty.of("path_id_intersecting", 0, PATH_TYPE_AMOUNT);
	//BooleanProperty TURF = BooleanProperty.of("turf");

	static boolean isPath(BlockState state) {
		return state.getBlock() instanceof ILawnBlock && getMainPathId(state) != 0;
	}

	static void setMainPathId(BlockPos pos, World world, int pathId) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof ILawnBlock) {
			if (pathId == 0) {
				world.setBlockState(pos, world.getBlockState(pos).with(PATH_ID_MAIN, pathId).with(PATH_ID_INTERSECTING, 0));
			}
			else if (pathId <= PATH_TYPE_AMOUNT && pathId >= 0) {
				if (pathId == getIntersectingPathId(state))
					world.setBlockState(pos, state.with(PATH_ID_MAIN, pathId).with(PATH_ID_INTERSECTING, 0));
				else
					world.setBlockState(pos, state.with(PATH_ID_MAIN, pathId));
			}
			else {
				HomeLawnSecurity.LOGGER.warn("Attempted to set main path of block {} to value outside of bounds!", pos.toShortString());
			}
		}
		else {
			HomeLawnSecurity.LOGGER.warn("Attempted to set main path of block {}, but the block isn't a lawn block!", pos.toShortString());
		}
	}

	static void setIntersectingPathId(BlockPos pos, World world, int pathId) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof ILawnBlock) {
			if (pathId <= PATH_TYPE_AMOUNT && pathId >= 0) {
				if (getMainPathId(state) != 0) {
					if (pathId == getMainPathId(state))
						world.setBlockState(pos, state.with(PATH_ID_MAIN, pathId).with(PATH_ID_INTERSECTING, 0));
					else
						world.setBlockState(pos, state.with(PATH_ID_INTERSECTING, pathId));
				}
			}
			else {
				HomeLawnSecurity.LOGGER.warn("Attempted to set intersecting path of block {} to value outside of bounds!", pos.toShortString());
			}
		}
		else {
			HomeLawnSecurity.LOGGER.warn("Attempted to set intersecting path of block {}, but the block isn't a lawn block!", pos.toShortString());
		}
	}

	/**
	 * Expects the block to use this class!
	 */
	static void removePath(BlockPos pos, World world) {
		setMainPathId(pos, world, 0);
	}

	/**
	 * Expects the block to use this class!
	 */
	static int getMainPathId(BlockState state) {
		return state.get(PATH_ID_MAIN);
	}

	/**
	 * Expects the block to use this class!
	 */
	static int getIntersectingPathId(BlockState state) {
		return state.get(PATH_ID_INTERSECTING);
	}

	/**
	 * Expects the block to use this class!
	 */
	@Deprecated
	static void setTurf(BlockPos pos, World world, boolean turf) {
		//world.setBlockState(pos, world.getBlockState(pos).with(TURF, turf));
	}

	static boolean getTurf(BlockState state) {
		//return state.get(TURF);
		Block block = state.getBlock();
		return block instanceof SoddedLawnBlockBlock || block instanceof SoddedLawnMarkerBlock;
	}


	/**
	 * Expects the block to use this class!
	 */
	static int getNextMainPathId(BlockState state) {
		int result = (getMainPathId(state) + 1) % (LawnUtil.getPathTypeAmount() + 1);
		if (result != 0 && result == getIntersectingPathId(state)) return (result + 1) % (LawnUtil.getPathTypeAmount() + 1);
		return result;
	}


	/**
	 * Expects the block to use this class!
	 */
	static int getNextIntersectingPathId(BlockState state) {
		int result = (getIntersectingPathId(state) + 1) % (LawnUtil.getPathTypeAmount() + 1);
		if (result != 0 && result == getMainPathId(state)) return (result + 1) % (LawnUtil.getPathTypeAmount() + 1);
		return result;
	}
}
