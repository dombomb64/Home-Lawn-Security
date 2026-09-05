package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.entity.ai.MoveToGoalGoal;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class LawnMowerMoveGoal extends MoveToGoalGoal {
	protected double goalBlockX;
	protected double goalBlockY;
	protected double goalBlockZ;

	public LawnMowerMoveGoal(LawnMowerEntity mob, double speed) {
		super(mob, speed);
	}

	@Override
	public boolean canStart() {
		LawnMowerEntity mower = ((LawnMowerEntity) mob);
		return mower.mowing && !mower.pathFinished && super.canStart();
	}

	@Override
	public boolean shouldContinue() {
		BlockPos mobPos = mob.getBlockPos();
		return ((LawnMowerEntity) mob).mowing
			&& (mobPos.getX() != targetX || mobPos.getZ() != targetZ)
			&& super.shouldContinue();
	}

	@Override
	public void stop() {
		super.stop();
		//HomeLawnSecurity.LOGGER.info("lawnmower stopped moving towards goal of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
		((LawnMowerEntity) mob).pathFinished = true; // I must go, my people need me
	}

	@Override
	protected boolean targetGoalPos() {
		boolean result = super.targetGoalPos();
		this.goalBlockX = this.targetX;
		this.goalBlockY = this.targetY;
		this.goalBlockZ = this.targetZ;
		return result;
	}

	@Override
	protected boolean checkForExistingGoal() {
		// Check for the goal block position we saved instead of the early ending
		BlockPos goalBlockPos = new BlockPos(MathHelper.floor(this.goalBlockX), MathHelper.floor(this.goalBlockY), MathHelper.floor(this.goalBlockZ));
		if (targetChosenBefore && mob.isGoal(goalBlockPos.down())) {
			return true; // Target still exists
		}
		return targetGoalPos(); // Choose a new target
	}

	@Override
	protected void startMoving(double x, double y, double z) {
		Path fullPath = this.mob.getNavigation().findPathTo(x, y, z, 0);
		if (fullPath == null || !fullPath.reachesTarget()) return;
		if (fullPath.getLength() < 3) {
			this.mob.getNavigation().startMovingAlong(fullPath, this.speed);
			return;
		}

		// Cut off the path early
		Path cutPath = null;
		PathNode prevNode = fullPath.getNode(0);
		PathNode secondNode = fullPath.getNode(1);
		Vec3i prevDiff = new Vec3i(secondNode.x-prevNode.x, secondNode.y-prevNode.y, secondNode.z-prevNode.z);
		prevNode = secondNode;
		for (int i = 2; i < fullPath.getLength(); i++) {
			PathNode curNode = fullPath.getNode(i);
			Vec3i curDiff = new Vec3i(curNode.x-prevNode.x, curNode.y-prevNode.y, curNode.z-prevNode.z);

			if (!curDiff.equals(prevDiff)) {
				cutPath = this.mob.getNavigation().findPathTo(prevNode.x, prevNode.y, prevNode.z, 0);
				break;
			}

			prevNode = curNode;
			prevDiff = curDiff;
		}
		if (cutPath == null || !cutPath.reachesTarget() || cutPath.getLength() < 1) {
			this.mob.getNavigation().startMovingAlong(fullPath, this.speed);
			return;
		}

		BlockPos target = cutPath.getTarget();
		// Re-assign the target position
		this.targetX = target.getX();
		this.targetY = target.getY();
		this.targetZ = target.getZ();
		// Move along the new path
		this.mob.getNavigation().startMovingAlong(cutPath, this.speed);
	}
}
