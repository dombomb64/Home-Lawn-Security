package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PathNavigation extends MobNavigation {
	public PathNavigation(ZombieEntity zombieEntity, World world) {
		super(zombieEntity, world);
	}

	@Override
	protected PathNodeNavigator createPathNodeNavigator(int range) {
		this.nodeMaker = new PathPathNodeMaker();
		this.nodeMaker.setCanEnterOpenDoors(true);
		return new PathNodeNavigator(this.nodeMaker, range);
	}

	@Override
	public void recalculatePath() {
		if (this.world.getTime() - this.lastRecalculateTime > 10L) {
			if (this.currentTarget != null) {
				this.currentPath = null;
				this.currentPath = this.findPathTo(this.currentTarget, this.currentDistance);
				this.lastRecalculateTime = this.world.getTime();
				this.inRecalculationCooldown = false;
			}
		} else {
			this.inRecalculationCooldown = true;
		}
	}

	@Override
	public boolean startMovingTo(double x, double y, double z, double speed) {
		return this.startMovingAlong(this.findPathTo(x, y, z, 0), speed);
	}

	@Override
	public boolean startMovingTo(Entity entity, double speed) {
		Path path = this.findPathTo(entity, 0);
		return path != null && this.startMovingAlong(path, speed);
	}

	/*@Override
	protected void adjustPath() {
		super.adjustPath();
		ZombieEntity zombie = (ZombieEntity) this.entity;
		if (!this.world.getBlockState(BlockPos.ofFloored(this.entity.getX(), this.entity.getY() + 0.5, this.entity.getZ())).isIn(zombie.pathTag)) {
			return;
		}
		for (int i = 0; i < this.currentPath.getLength(); ++i) {
			PathNode pathNode = this.currentPath.getNode(i);
			if (this.world.getBlockState(new BlockPos(pathNode.x, pathNode.y, pathNode.z)).isIn(zombie.pathTag)) continue;
			this.currentPath.setLength(i);
			return;
		}
	}*/
}
