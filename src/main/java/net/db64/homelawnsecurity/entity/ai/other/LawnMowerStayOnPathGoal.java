package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class LawnMowerStayOnPathGoal extends Goal {
	protected final LawnMowerEntity mob;
	private double targetX;
	private double targetY;
	private double targetZ;
	private final double speed;
	private final World world;

	public LawnMowerStayOnPathGoal(LawnMowerEntity mob, double speed) {
		this.mob = mob;
		this.speed = speed;
		this.world = mob.getWorld();
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public boolean canStart() {
		return mob.mowing
			&& !(mob.isPathOrGoal(this.mob.getBlockPos().down())
			|| mob.isStart(this.mob.getBlockPos().down())) // If the entity is on a starting block, this goal shouldn't be activated even though this block isn't pathable! This goal randomly chooses a block to path to, remember?
			&& this.checkForExistingPath();
	}

	private boolean checkForExistingPath() {
		BlockPos targetPos = new BlockPos(MathHelper.floor(this.targetX), MathHelper.floor(this.targetY), MathHelper.floor(this.targetZ));
		HomeLawnSecurity.LOGGER.info("lawnmower's current path pos: " + targetX + ", " + targetY + ", " + targetZ + " | " + targetPos.toShortString() + " lawnmower's current pos: " + mob.getPos().toString() + " | " + mob.getBlockPos().toShortString());
		if (mob.isPathOrGoal(targetPos.down())) {
			return true; // Target still exists
		}
		return targetPathPos(); // Choose a new target
	}

	protected boolean targetPathPos() {
		HomeLawnSecurity.LOGGER.info("lawnmower is picking new path pos, old: " + targetX + ", " + targetY + ", " + targetZ);
		Vec3d vec3d = this.locatePathPos();
		if (vec3d == null) {
			HomeLawnSecurity.LOGGER.info("lawnmower couldn't pick new path pos, switching path tag");
			mob.switchPathTag();
			return false;
		}
		this.targetX = vec3d.x;
		this.targetY = vec3d.y;
		this.targetZ = vec3d.z;
		HomeLawnSecurity.LOGGER.info("lawnmower has picked new path pos, new: " + targetX + ", " + targetY + ", " + targetZ);
		return true;
	}

	@Override
	public boolean shouldContinue() {
		return mob.mowing
			&& !(mob.isPathOrGoal(this.mob.getBlockPos().down())
			|| mob.isStart(this.mob.getBlockPos().down()) // If the entity is on a starting block, this goal shouldn't be activated even though this block isn't pathable! This goal randomly chooses a block to path to, remember?
			|| mob.getWorld().getBlockState(mob.getBlockPos().down()).isAir()) // Prevent stopping while over air
			&& checkForExistingPath();
	}

	@Override
	public void start() {
		HomeLawnSecurity.LOGGER.info("lawnmower started moving towards path of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
		this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
	}

	/*@Override
	public void stop() {
		HomeLawnSecurity.LOGGER.info("lawnmower stopped moving towards path of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
	}*/

	@Nullable
	protected Vec3d locatePathPos() {
		int rangeH = 2;
		int rangeV = 2;
		Iterable<BlockPos> iterable = BlockPos.iterateRandomly(mob.getRandom(), ((rangeH * 2 + 1) * 2) * (rangeV * 2 + 1), MathHelper.floor(this.mob.getX() - rangeH), MathHelper.floor(this.mob.getY() - rangeV), MathHelper.floor(this.mob.getZ() - rangeH), MathHelper.floor(this.mob.getX() + rangeH), MathHelper.floor(this.mob.getY() + rangeV), MathHelper.floor(this.mob.getZ() + rangeH));
		//Iterable<BlockPos> iterable = BlockPos.iterateOutwards(mob.getBlockPos(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("lawnmower is checking for if block {}, {}, {} is path", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!mob.isPathOrGoal(blockPos.down())) continue;
			HomeLawnSecurity.LOGGER.info("lawnmower is checking for if path at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (this.mob.getNavigation().findPathTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1) == null) continue;

			this.mob.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.speed);
			return Vec3d.ofBottomCenter(blockPos);
		}
		return null;
	}
}
