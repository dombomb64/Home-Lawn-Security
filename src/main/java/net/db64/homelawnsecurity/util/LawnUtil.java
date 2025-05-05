package net.db64.homelawnsecurity.util;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.block.custom.lawn.ILawnBlock;
import net.db64.homelawnsecurity.block.custom.lawn.LawnMarkerBlock;
import net.db64.homelawnsecurity.block.custom.MarkerBlock;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.db64.homelawnsecurity.entity.custom.other.TargetZombieEntity;
import net.db64.homelawnsecurity.entity.custom.plant.ILawnPlant;
import net.db64.homelawnsecurity.entity.custom.plant.IPathPlant;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LawnUtil {
	/**
	 * @return If this block can be walked on with the provided path id.
	 */
	public static boolean isWalkable(BlockPos pos, World world, int entityPathId, boolean isZombie) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());
		Block block = state.getBlock();

		if (markerState.getBlock() instanceof MarkerBlock)
			return isWalkableMarker(markerState, entityPathId, isZombie);
		else if (block instanceof ILawnBlock || block == ModBlocks.GARDEN_BLOCK || block == ModBlocks.GRAVEYARD_BLOCK)
			return isWalkableBlock(state, entityPathId, isZombie);
		return false;
	}

	private static boolean isWalkableBlock(BlockState state, int entityPathId, boolean isZombie) {
		if (isZombie && state.getBlock() == ModBlocks.GARDEN_BLOCK) {
			return true;
		}
		else if (!isZombie && state.getBlock() == ModBlocks.GRAVEYARD_BLOCK) {
			return true;
		}
		else {
			return isCertainPathHelper(state, entityPathId);
		}
	}

	private static boolean isWalkableMarker(BlockState state, int entityPathId, boolean isZombie) {
		if (isZombie && state.getBlock() == ModBlocks.GARDEN_MARKER) {
			return true;
		}
		else if (!isZombie && state.getBlock() == ModBlocks.GRAVEYARD_MARKER) {
			return true;
		}
		else {
			return isCertainPathHelper(state, entityPathId);
		}
	}

	/**
	 * @return If this block has any path.
	 */
	public static boolean isAnyPath(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.getBlock() instanceof LawnMarkerBlock) {
			return ILawnBlock.getMainPathId(markerState) != 0;
		}
		else if (state.getBlock() instanceof ILawnBlock) {
			return ILawnBlock.getMainPathId(state) != 0;
		}
		return false;
	}

	/**
	 * @return If this block has a path matching entityPathId.
	 */
	public static boolean isCertainPath(BlockPos pos, World world, int entityPathId) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.getBlock() instanceof LawnMarkerBlock) {
			return isCertainPathHelper(markerState, entityPathId);
		}
		else if (state.getBlock() instanceof ILawnBlock) {
			return isCertainPathHelper(state, entityPathId);
		}
		return false;
	}

	private static boolean isCertainPathHelper(BlockState state, int entityPathId) {
		if (state.getBlock() instanceof ILawnBlock)
			return ILawnBlock.getMainPathId(state) == entityPathId || ILawnBlock.getIntersectingPathId(state) == entityPathId;
		return false;
	}

	/**
	 * @return If this kind of entity can be placed at this spot. Does not check distance from graveyard or if a plant is in the way.
	 */
	public static boolean isPlaceable(BlockPos pos, World world, Class<? extends PathAwareEntity> entityClass) {
		if (TargetZombieEntity.class.isAssignableFrom(entityClass)) {
			return TargetZombieEntity.isPlaceable(pos, world);
		}
		else if (ZombieEntity.class.isAssignableFrom(entityClass)) {
			return ZombieEntity.isPlaceable(pos, world);
		}
		else if (PlantEntity.class.isAssignableFrom(entityClass)) {
			if (ILawnPlant.class.isAssignableFrom(entityClass)) {
				return PlantEntity.isPlaceableLawn(pos, world);
			}
			else if (IPathPlant.class.isAssignableFrom(entityClass)) {
				return PlantEntity.isPlaceablePath(pos, world);
			}
			HomeLawnSecurity.LOGGER.error("We have a plant class ({}) that does not implement any placement interface! This is something ALL plant classes should do...", entityClass.getSimpleName());
		}
		else if (LawnMowerEntity.class.isAssignableFrom(entityClass)) {
			return LawnMowerEntity.isPlaceable(pos, world);
		}
		return true;
	}

	public static int getMainPathId(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.getBlock() instanceof LawnMarkerBlock) {
			return ILawnBlock.getMainPathId(markerState);
		}
		else if (state.getBlock() instanceof ILawnBlock) {
			return ILawnBlock.getMainPathId(state);
		}
		return 0;
	}

	public static int getIntersectingPathId(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.getBlock() instanceof LawnMarkerBlock) {
			return ILawnBlock.getIntersectingPathId(markerState);
		}
		else if (state.getBlock() instanceof ILawnBlock) {
			return ILawnBlock.getIntersectingPathId(state);
		}
		return 0;
	}

	public static boolean hasTurf(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.getBlock() instanceof LawnMarkerBlock) {
			return ILawnBlock.getTurf(markerState);
		}
		else if (state.getBlock() instanceof ILawnBlock) {
			return ILawnBlock.getTurf(state);
		}
		return false;
	}

	public static int getPathTypeAmount() {
		return ILawnBlock.PATH_TYPE_AMOUNT;
	}
}
