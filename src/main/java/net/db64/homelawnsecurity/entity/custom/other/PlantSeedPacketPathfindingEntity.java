package net.db64.homelawnsecurity.entity.custom.other;


import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantSeedPacketPathfindingEntity extends SeedPacketPathfindingEntity {
	public PlantSeedPacketPathfindingEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	/**
	 * @return Whether this block is a goal.
	 */
	public boolean isGoal(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_START_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_START);
	}

	/**
	 * @return Whether this block is a start.
	 */
	public boolean isStart(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_GOAL_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_GOAL);
	}
}
