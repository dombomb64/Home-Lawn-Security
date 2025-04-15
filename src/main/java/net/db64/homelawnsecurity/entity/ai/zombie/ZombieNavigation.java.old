package net.db64.homelawnsecurity.entity.ai.zombie;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.world.World;

public class ZombieNavigation extends MobNavigation {
	public ZombieNavigation(ZombieEntity entity, World world) {
		super(entity, world);
	}

	@Override
	protected PathNodeNavigator createPathNodeNavigator(int range) {
		this.nodeMaker = new ZombiePathNodeMaker();
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
		if (zombie.isPath(!this.world.getBlockState(BlockPos.ofFloored(this.entity.getX(), this.entity.getY() + 0.5, this.entity.getZ())))) {
			return;
		}
		for (int i = 0; i < this.currentPath.getLength(); ++i) {
			PathNode pathNode = this.currentPath.getNode(i);
			if (zombie.isPath(this.world.getBlockState(new BlockPos(pathNode.x, pathNode.y, pathNode.z)))) continue;
			this.currentPath.setLength(i);
			return;
		}
	}*/
}
