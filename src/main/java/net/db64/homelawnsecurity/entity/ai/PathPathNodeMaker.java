package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class PathPathNodeMaker extends LandPathNodeMaker {
	@Override
	public PathNodeType getNodeType(BlockView world, int x, int y, int z, MobEntity mob) {
		ZombieEntity zombie = (ZombieEntity) mob;
		BlockPos pos = new BlockPos(x, y, z);

		while (pos.getY() > world.getBottomY() && (world.getBlockState(pos).isIn(BlockTags.REPLACEABLE) || world.getBlockState(pos).isIn(ModTags.Blocks.MARKERS))) {
			pos = pos.down();
		}

		//BlockState stateMob = world.getBlockState(zombie.getBlockPos().down());
		//BlockState state = world.getBlockState(pos);

		if (zombie.isPathOrGoal(zombie.getBlockPos().down()) // Standing on path (so that it can get back on track)
			&& !zombie.isPathOrGoal(pos)) // Block is not path
			return PathNodeType.BLOCKED;

		return super.getNodeType(world, x, y, z, mob);
	}

	@Override
	public PathNodeType getDefaultNodeType(BlockView world, int x, int y, int z) {
		return PathPathNodeMaker.getPathNodeType(world, new BlockPos.Mutable(x, y, z), (ZombieEntity) this.entity);
	}

	public static PathNodeType getPathNodeType(BlockView world, BlockPos.Mutable pos, ZombieEntity zombie) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		BlockPos pos2 = new BlockPos(i, j, k);

		while (pos2.getY() > world.getBottomY() && (world.getBlockState(pos2).isIn(BlockTags.REPLACEABLE) || world.getBlockState(pos2).isIn(ModTags.Blocks.MARKERS))) {
			pos2 = pos2.down();
		}

		BlockState stateMob = world.getBlockState(zombie.getBlockPos().down());
		BlockState state = world.getBlockState(pos2);

		//BlockState state = world.getBlockState(pos.down());
		PathNodeType pathNodeType;
		/*if (zombie.isPath(pos.down())) {
			pathNodeType = PathNodeType.OPEN;
		}
		else if (zombie.isGoal(pos.down())) {
			pathNodeType = PathNodeType.WALKABLE;
		}
		else {
			pathNodeType = PathNodeType.BLOCKED;
		}

		return pathNodeType;*/
		if (zombie.isPathOrGoal(zombie.getBlockPos().down()) // Standing on path (so that it can get back on track)
			&& !zombie.isPathOrGoal(pos2)) { // Block is not path
			pathNodeType = PathNodeType.BLOCKED;
		}
		else {
			pathNodeType = LandPathNodeMaker.getCommonNodeType(world, pos);
		}

		if (pathNodeType != PathNodeType.OPEN || j < world.getBottomY() + 1) {
			return pathNodeType;
		}
		return switch (LandPathNodeMaker.getCommonNodeType(world, pos.set(i, j - 1, k))) {
			case OPEN, WATER, LAVA, WALKABLE -> PathNodeType.OPEN;
			case DAMAGE_FIRE -> PathNodeType.DAMAGE_FIRE;
			case DAMAGE_OTHER -> PathNodeType.DAMAGE_OTHER;
			case STICKY_HONEY -> PathNodeType.STICKY_HONEY;
			case POWDER_SNOW -> PathNodeType.DANGER_POWDER_SNOW;
			case DAMAGE_CAUTIOUS -> PathNodeType.DAMAGE_CAUTIOUS;
			case TRAPDOOR -> PathNodeType.DANGER_TRAPDOOR;
			default -> LandPathNodeMaker.getNodeTypeFromNeighbors(world, pos.set(i, j, k), PathNodeType.WALKABLE);
		};
	}
}
