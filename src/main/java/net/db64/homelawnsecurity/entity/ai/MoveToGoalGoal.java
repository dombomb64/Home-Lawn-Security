package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class MoveToGoalGoal extends Goal {
	protected final PathAwareEntity mob;
	protected double targetX;
	protected double targetY;
	protected double targetZ;
	protected final double speed;
	protected final World world;

	public MoveToGoalGoal(PathAwareEntity mob, double speed) {
		if (!(mob instanceof IPathBoundEntity))
			HomeLawnSecurity.LOGGER.error("Entities using MoveToGoalGoal must implement IPathBoundEntity!");

		this.mob = mob;
		this.speed = speed;
		this.world = mob.getWorld();
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public void tick() {
		super.tick();
		if (!mob.isNavigating()) {
			this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
		}
	}

	@Override
	public boolean canStart() {
		return !((IPathBoundEntity) mob).isGoal(this.mob.getBlockPos().down()) && this.checkForExistingGoal();
	}

	private boolean checkForExistingGoal() {
		BlockPos targetPos = new BlockPos(MathHelper.floor(this.targetX), MathHelper.floor(this.targetY), MathHelper.floor(this.targetZ));
		//HomeLawnSecurity.LOGGER.info("zombie's current goal pos: " + targetX + ", " + targetY + ", " + targetZ + " | " + targetPos.toShortString() + " zombie's current pos: " + mob.getPos().toString() + " | " + mob.getBlockPos().toShortString());
		if (((IPathBoundEntity) mob).isGoal(targetPos.down())) {
			//HomeLawnSecurity.LOGGER.info("zombie is checking for if the block at {}, {}, {} is a goal", targetX, targetY, targetZ);
			return true; // Target still exists
		}
		return targetGoalPos(); // Choose a new target
	}

	protected boolean targetGoalPos() {
		//HomeLawnSecurity.LOGGER.info("zombie is picking new goal pos, old: " + targetX + ", " + targetY + ", " + targetZ);
		Vec3d vec3d = this.locateGoalPos();
		if (vec3d == null) {
			//HomeLawnSecurity.LOGGER.info("zombie couldn't pick new goal pos");
			return false;
		}
		this.targetX = vec3d.x;
		this.targetY = vec3d.y;
		this.targetZ = vec3d.z;
		//HomeLawnSecurity.LOGGER.info("zombie has picked new goal pos, new: " + targetX + ", " + targetY + ", " + targetZ);
		return true;
	}

	@Override
	public boolean shouldContinue() {
		return !((IPathBoundEntity) mob).isGoal(this.mob.getBlockPos().down()) && checkForExistingGoal();
	}

	@Override
	public void start() {
		//HomeLawnSecurity.LOGGER.info("zombie started moving towards goal of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
		this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
	}

	/*@Override
	public void stop() {
		HomeLawnSecurity.LOGGER.info("zombie stopped moving towards goal of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
	}*/

	@Nullable
	protected Vec3d locateGoalPos() {
		int rangeH = 16;
		int rangeV = 5;
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(mob.getBlockPos(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("zombie is checking for if the block at {}, {}, {} is a goal", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!((IPathBoundEntity) mob).isGoal(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("zombie is checking for if the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!this.mob.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.speed)) continue;
			return Vec3d.ofBottomCenter(blockPos);
		}
		return null;
	}
}
