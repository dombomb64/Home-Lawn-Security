package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class LawnMowerMoveGoal extends Goal {
	protected final LawnMowerEntity mob;
	private double targetX;
	private double targetY;
	private double targetZ;
	private final double speed;
	private final World world;

	public LawnMowerMoveGoal(LawnMowerEntity mob, double speed) {
		this.mob = mob;
		this.speed = speed;
		this.world = mob.getWorld();
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public boolean canStart() {
		return mob.mowing && this.checkForExistingGoal();
	}

	private boolean checkForExistingGoal() {
		BlockPos targetPos = new BlockPos(MathHelper.floor(this.targetX), MathHelper.floor(this.targetY), MathHelper.floor(this.targetZ));
		//HomeLawnSecurity.LOGGER.info("lawnmower's current goal pos: " + targetX + ", " + targetY + ", " + targetZ + " | " + targetPos.toShortString() + " lawnmower's current pos: " + mob.getPos().toString() + " | " + mob.getBlockPos().toShortString());
		if (mob.isPathOrGoal(targetPos.down())) {
			//HomeLawnSecurity.LOGGER.info("lawnmower is checking for if the block at {}, {}, {} is a goal", targetX, targetY, targetZ);
			return true; // Target still exists
		}
		return targetGoalPos(); // Choose a new target
	}

	protected boolean targetGoalPos() {
		//HomeLawnSecurity.LOGGER.info("lawnmower is picking new goal pos, old: " + targetX + ", " + targetY + ", " + targetZ);
		Vec3d vec3d = this.locateGoalPos();
		if (vec3d == null) {
			//HomeLawnSecurity.LOGGER.info("lawnmower couldn't pick new goal pos");
			return false;
		}
		this.targetX = vec3d.x;
		this.targetY = vec3d.y;
		this.targetZ = vec3d.z;
		//HomeLawnSecurity.LOGGER.info("lawnmower has picked new goal pos, new: " + targetX + ", " + targetY + ", " + targetZ);
		return true;
	}

	@Override
	public boolean shouldContinue() {
		return mob.mowing
			&& (MathHelper.floor(targetX) != mob.getBlockX()
			|| MathHelper.floor(targetY) != mob.getBlockY()
			|| MathHelper.floor(targetZ) != mob.getBlockZ())
			&& checkForExistingGoal();
	}

	@Override
	public void start() {
		//HomeLawnSecurity.LOGGER.info("lawnmower started moving towards goal of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
		this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
	}

	@Override
	public void stop() {
		//HomeLawnSecurity.LOGGER.info("lawnmower stopped moving towards goal of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
		mob.disappear(); // I must go, my people need me
	}

	@Nullable
	protected Vec3d locateGoalPos() {
		int rangeH = 16;
		int rangeV = 5;
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(mob.getBlockPos(), rangeH, rangeV, rangeH);
		Vec3d start = null;
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("lawnmower is checking for if the block at {}, {}, {} is a goal", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!mob.isGoal(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("lawnmower is checking for if the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (this.mob.getNavigation().findPathTo(blockPos, 1) == null) continue;
			//HomeLawnSecurity.LOGGER.info("the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			start = Vec3d.ofBottomCenter(blockPos);
			break;
		}
		return start;
	}
}
