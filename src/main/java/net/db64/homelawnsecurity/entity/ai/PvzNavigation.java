package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.debug.ModDebugSubscriptionTypes;
import net.db64.homelawnsecurity.entity.custom.PathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.other.SeedPacketPathfindingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.server.debug.SubscriberTracker;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class PvzNavigation extends MobNavigation {
	public PvzNavigation(PathBoundEntity mario, World world) {
		super(mario, world); // Lmao

		if (world instanceof ServerWorld serverWorld) {
			assert serverWorld.getServer() != null;
			SubscriberTracker subscriberTracker = serverWorld.getServer().getSubscriberTracker();
			this.pathNodeNavigator.setShouldSendDebugData(() -> subscriberTracker.hasSubscriber(ModDebugSubscriptionTypes.PVZ_PATHS));
		}
	}

	@Override
	public void tick() {
		super.tick();

		currentNodeMs = 1;
	}

	@Override
	protected PathNodeNavigator createPathNodeNavigator(int range) {
		this.nodeMaker = new PvzPathNodeMaker();
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
		return path != null && path.reachesTarget() && this.startMovingAlong(path, speed);
	}

	/*@Override
	protected void adjustPath() {
		super.adjustPath();
		ZombieEntity zombie = (ZombieEntity) this.entity;
		if (zombie.isCertainPath(!this.world.getBlockState(BlockPos.ofFloored(this.entity.getX(), this.entity.getY() + 0.5, this.entity.getZ())))) {
			return;
		}
		for (int i = 0; i < this.currentPath.getLength(); ++i) {
			PathNode pathNode = this.currentPath.getNode(i);
			if (zombie.isCertainPath(this.world.getBlockState(new BlockPos(pathNode.x, pathNode.y, pathNode.z)))) continue;
			this.currentPath.setLength(i);
			return;
		}
	}*/

	@Override
	protected boolean isAtValidPosition() {
		return super.isAtValidPosition() || entity instanceof SeedPacketPathfindingEntity || entity.age < 2;
	}

	@Override
	protected void continueFollowingPath() {
		Vec3d vec3d = this.getPos();
		this.nodeReachProximity = 0.1f;
		Vec3i vec3i = this.currentPath.getCurrentNodePos();
		double d = Math.abs(this.entity.getX() - (vec3i.getX() + 0.5));
		double e = Math.abs(this.entity.getY() - vec3i.getY());
		double f = Math.abs(this.entity.getZ() - (vec3i.getZ() + 0.5));
		boolean bl = d < this.nodeReachProximity && f < this.nodeReachProximity && e < 1.0;
		if (bl || this.canJumpToNext(this.currentPath.getCurrentNode().type) && this.shouldJumpToNextNode(vec3d)) {
			this.currentPath.next();
		}

		this.checkTimeouts(vec3d);
	}
}
