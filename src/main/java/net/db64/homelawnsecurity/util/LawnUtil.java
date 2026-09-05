package net.db64.homelawnsecurity.util;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.block.custom.lawn.ILawnBlock;
import net.db64.homelawnsecurity.block.custom.lawn.LawnBlockBlock;
import net.db64.homelawnsecurity.block.custom.lawn.LawnMarkerBlock;
import net.db64.homelawnsecurity.block.custom.MarkerBlock;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.db64.homelawnsecurity.entity.custom.other.TargetZombieEntity;
import net.db64.homelawnsecurity.entity.custom.plant.ILawnPlant;
import net.db64.homelawnsecurity.entity.custom.plant.IPathPlant;
import net.db64.homelawnsecurity.entity.custom.projectile.PlantProjectileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LawnUtil {
	/**
	 * @return If this block can be walked on with the provided path id.
	 */
	public static boolean isWalkable(BlockPos pos, World world, int entityPathId, BlockPos entitySteppingPos, boolean isZombie) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());
		Block block = state.getBlock();

		if (pos.equals(entitySteppingPos))
			return true;
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

	public static boolean isCertainPath(BlockPos pos, World world, int entityPathId) {
		return isCertainPath(pos, world, entityPathId, false, false);
	}

	/**
	 * @return If this block has a path matching entityPathId.
	 */
	public static boolean isCertainPath(BlockPos pos, World world, int entityPathId, boolean onlyMain, boolean onlyIntersecting) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.getBlock() instanceof LawnMarkerBlock) {
			return isCertainPathHelper(markerState, entityPathId, onlyMain, onlyIntersecting);
		}
		else if (state.getBlock() instanceof ILawnBlock) {
			return isCertainPathHelper(state, entityPathId, onlyMain, onlyIntersecting);
		}
		return false;
	}

	private static boolean isCertainPathHelper(BlockState state, int entityPathId) {
		if (state.getBlock() instanceof ILawnBlock)
			return ILawnBlock.getMainPathId(state) == entityPathId || ILawnBlock.getIntersectingPathId(state) == entityPathId;
		return false;
	}

	private static boolean isCertainPathHelper(BlockState state, int entityPathId, boolean onlyMain, boolean onlyIntersecting) {
		if (state.getBlock() instanceof ILawnBlock)
			return (!onlyIntersecting && ILawnBlock.getMainPathId(state) == entityPathId)
				|| (!onlyMain && ILawnBlock.getIntersectingPathId(state) == entityPathId);
		return false;
	}

	/**
	 * @return Whether the {@code plant}'s path placement allows for targeting of entities of the {@code zombie}'s path.
	 * @apiNote This only checks for path IDs. The rest is handled by the attacking entity.
	 */
	public static boolean canTargetEntity(PlantEntity plant, ZombieEntity zombie) {
		if (!plant.onPath) {
			return true;
		}
		int plantPath = plant.getPathId();
		int plantIntPath = plant.intersectingPath;
		int zombiePath = zombie.getPathId();
		if (plantIntPath == 0) {
			return plantPath == zombiePath;
		}
		return plantPath == zombiePath || plantIntPath == zombiePath;
	}

	/**
	 * @return Whether the {@code zombie}'s path placement allows for targeting of entities of the {@code plant}'s path.
	 * @apiNote This only checks for path IDs. The rest is handled by the attacking entity.
	 */
	public static boolean canTargetEntity(ZombieEntity zombie, PlantEntity plant) {
		if (!plant.onPath) {
			return true;
		}
		int plantPath = plant.getPathId();
		int plantIntPath = plant.intersectingPath;
		int zombiePath = zombie.getPathId();
		if (plantIntPath == 0) {
			return plantPath == zombiePath;
		}
		return plantPath == zombiePath || plantIntPath == zombiePath;
	}

	/**
	 * @return Whether the {@code projectile}'s path values allow for attacking of entities of the {@code zombie}'s path.
	 * @apiNote This only checks for path IDs. The rest is handled by the attacking entity.
	 */
	public static boolean canTargetEntity(PlantProjectileEntity projectile, ZombieEntity zombie) {
		int plantPath = projectile.getPathId();
		if (!projectile.ownerOnPath) {
			return true;
		}
		int plantIntPath = projectile.getIntersectingPath();
		int zombiePath = zombie.getPathId();
		if (plantIntPath == 0) {
			return plantPath == zombiePath;
		}
		return plantPath == zombiePath || plantIntPath == zombiePath;
	}

	/**
	 * @return If this kind of entity can be placed at this spot.
	 * @apiNote Does not check distance from graveyards or if a plant is in the way.
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

	public static int getShadeAmount() {
		return LawnBlockBlock.SHADE_AMOUNT;
	}
}
