package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.entity.ai.PvzNavigation;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Extended by any entity that needs to have lawn-based pathfinding, like zombies and lawnmowers.
 */
public abstract class PathBoundEntity extends PathAwareEntity {
	protected int pathId = 1;

	protected PathBoundEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}



	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

		//HomeLawnSecurity.LOGGER.info("new path-bound entity");
		BlockPos pos = getSteppingPos();
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(pos, 5, 5, 5);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("block pos {} is:", blockPos.toShortString());
			if (LawnUtil.isAnyPath(blockPos, getEntityWorld())) {
				for (int i = 1; i <= LawnUtil.getPathTypeAmount(); i++) {
					if (LawnUtil.isCertainPath(blockPos, getEntityWorld(), i)) {
						//HomeLawnSecurity.LOGGER.info("path {}", i);
						pathId = i;
					}
				}
				break;
			}
			//HomeLawnSecurity.LOGGER.info("not a path");
		}

		return result;
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PvzNavigation(this, world);
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
		}
		view.putInt("path_id", pathId);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		pathId = view.getInt("path_id", 1);
		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
		}
	}



	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}



	/**
	 * @return Whether this entity should path towards the garden or not.
	 * @implSpec Should be <code>true</code> for Team Zombies and <code>false</code> for Team Plants.
	 */
	protected abstract boolean shouldPathLikeZombie();

	/**
	 * @return The tag that this entity checks for when looking for valid goal blocks.
	 */
	protected abstract TagKey<Block> getGoalBlockTag();

	/**
	 * @return The tag that this entity checks for when looking for valid goal markers.
	 */
	protected abstract TagKey<Block> getGoalMarkerTag();

	/**
	 * @return The tag that this entity checks for when looking for valid start blocks.
	 */
	protected abstract TagKey<Block> getStartBlockTag();

	/**
	 * @return The tag that this entity checks for when looking for valid start markers.
	 */
	protected abstract TagKey<Block> getStartMarkerTag();



	/**
	 * @param pos The block to check.
	 * @return Whether the block at <code>pos</code> is the kind of block this entity aspires to step on.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isGoal(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(getGoalMarkerTag());
		}
		return state.isIn(getGoalBlockTag());
	}

	/**
	 * @param pos The block to check.
	 * @return Whether the block at <code>pos</code> is the antithesis to this entity's goal block.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isStart(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(getStartMarkerTag());
		}
		return state.isIn(getStartBlockTag());
	}



	/**
	 * @param pos The block to check.
	 * @return Whether this entity can currently path over this block.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isWalkable(BlockPos pos) {
		return LawnUtil.isWalkable(pos, getEntityWorld(), pathId, getSteppingPos(), shouldPathLikeZombie());
	}

	/**
	 * @param pos The block to check.
	 * @return Whether the block at <code>pos</code> is a lawn block with this entity's path ID.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isThisPath(BlockPos pos) {
		return isCertainPath(pos, pathId);
	}

	/**
	 * @param pos The block to check.
	 * @param pathId The ID of the path to look for in the block.
	 * @return Whether the block at <code>pos</code> is a lawn block with a specific path ID.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isCertainPath(BlockPos pos, int pathId) {
		return LawnUtil.isCertainPath(pos, getEntityWorld(), pathId);
	}
}
