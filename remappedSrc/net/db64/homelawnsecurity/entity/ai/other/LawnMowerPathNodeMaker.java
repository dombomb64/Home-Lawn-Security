package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathContext;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.CollisionView;

public class LawnMowerPathNodeMaker extends LandPathNodeMaker {
	@Override
	public PathNodeType getNodeType(PathContext context, int x, int y, int z, MobEntity mob) {
		LawnMowerEntity lawnMower = (LawnMowerEntity) mob;
		BlockPos pos = new BlockPos(x, y, z);
		CollisionView world = context.getWorld();

		while (pos.getY() > world.getBottomY() && (world.getBlockState(pos).isIn(BlockTags.REPLACEABLE) || world.getBlockState(pos).isIn(ModTags.Blocks.MARKERS))) {
			pos = pos.down();
		}

		if (lawnMower.isPathOrGoal(lawnMower.getBlockPos().down()) // Standing on path (so that it can get back on track)
			&& !lawnMower.isPathOrGoal(pos)) // Block is not path

			return PathNodeType.BLOCKED;

		return super.getNodeType(context, x, y, z, mob);
	}

	@Override
	public PathNodeType getDefaultNodeType(PathContext context, int x, int y, int z) {
		return LawnMowerPathNodeMaker.getPvzNodeType(context, new BlockPos.Mutable(x, y, z), (LawnMowerEntity) this.entity);
	}

	public static PathNodeType getPvzNodeType(PathContext context, BlockPos.Mutable pos, LawnMowerEntity lawnMower) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		BlockPos pos2 = new BlockPos(i, j, k);

		CollisionView world = context.getWorld();

		while (pos2.getY() > world.getBottomY() && (world.getBlockState(pos2).isIn(BlockTags.REPLACEABLE) || world.getBlockState(pos2).isIn(ModTags.Blocks.MARKERS))) {
			pos2 = pos2.down();
		}

		PathNodeType pathNodeType;

		if (lawnMower.isPathOrGoal(lawnMower.getBlockPos().down()) // Standing on path (so that it can get back on track)
			&& !lawnMower.isPathOrGoal(pos2)) { // Block is not path
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
			default -> LandPathNodeMaker.getNodeTypeFromNeighbors(context, i, j, k, PathNodeType.WALKABLE);
		};
	}
}