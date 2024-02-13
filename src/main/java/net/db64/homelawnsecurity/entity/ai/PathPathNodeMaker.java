package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;

public class PathPathNodeMaker extends LandPathNodeMaker {
	@Override
	public PathNodeType getNodeType(BlockView world, int x, int y, int z, MobEntity mob) {
		ZombieEntity zombie = (ZombieEntity) mob;
		BlockPos pos = new BlockPos(x, y, z);

		while (pos.getY() > world.getBottomY() && world.getBlockState(pos).isIn(BlockTags.REPLACEABLE)) {
			pos = pos.down();
		}

		BlockState stateMob = world.getBlockState(zombie.getBlockPos().down());
		BlockState state = world.getBlockState(pos);

		if ((stateMob.isIn(zombie.pathTag) || stateMob.isIn(ModTags.Blocks.ZOMBIE_GOAL)) // Standing on path (so that it can get back on track)
			&& (!state.isIn(zombie.pathTag) && !state.isIn(ModTags.Blocks.ZOMBIE_GOAL))) // Block is not path
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

		while (pos2.getY() > world.getBottomY() && world.getBlockState(pos2).isIn(BlockTags.REPLACEABLE)) {
			pos2 = pos2.down();
		}

		BlockState stateMob = world.getBlockState(zombie.getBlockPos().down());
		BlockState state = world.getBlockState(pos2);

		//BlockState state = world.getBlockState(pos.down());
		PathNodeType pathNodeType;
		/*if (state.isIn(zombie.pathTag)) {
			pathNodeType = PathNodeType.OPEN;
		}
		else if (state.isIn(ModTags.Blocks.ZOMBIE_GOAL)) {
			pathNodeType = PathNodeType.WALKABLE;
		}
		else {
			pathNodeType = PathNodeType.BLOCKED;
		}

		return pathNodeType;*/
		if ((stateMob.isIn(zombie.pathTag) || stateMob.isIn(ModTags.Blocks.ZOMBIE_GOAL))
			&& !state.isIn(zombie.pathTag) && !state.isIn(ModTags.Blocks.ZOMBIE_GOAL)) {
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
