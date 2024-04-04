package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class StayOnPathGoal extends Goal {
	protected final ZombieEntity mob;
	private double targetX;
	private double targetY;
	private double targetZ;
	private final double speed;
	private final World world;

	public StayOnPathGoal(ZombieEntity mob, double speed) {
		this.mob = mob;
		this.speed = speed;
		this.world = mob.getWorld();
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}

	@Override
	public boolean canStart() {
		return !mob.isPathOrGoal(this.mob.getBlockPos().down()) && this.checkForExistingPath();
	}

	private boolean checkForExistingPath() {
		BlockPos targetPos = new BlockPos(MathHelper.floor(this.targetX), MathHelper.floor(this.targetY), MathHelper.floor(this.targetZ));
		//HomeLawnSecurity.LOGGER.info("zombie's current path pos: " + targetX + ", " + targetY + ", " + targetZ + " | " + targetPos.toShortString() + " zombie's current pos: " + mob.getPos().toString() + " | " + mob.getBlockPos().toShortString());
		if (mob.isPathOrGoal(targetPos.down())) {
			return true; // Target still exists
		}
		return targetPathPos(); // Choose a new target
	}

	protected boolean targetPathPos() {
		//HomeLawnSecurity.LOGGER.info("zombie is picking new path pos, old: " + targetX + ", " + targetY + ", " + targetZ);
		Vec3d vec3d = this.locatePathPos();
		if (vec3d == null) {
			//HomeLawnSecurity.LOGGER.info("zombie couldn't pick new path pos, switching path tag");
			mob.switchPathTag();
			return false;
		}
		this.targetX = vec3d.x;
		this.targetY = vec3d.y;
		this.targetZ = vec3d.z;
		//HomeLawnSecurity.LOGGER.info("zombie has picked new path pos, new: " + targetX + ", " + targetY + ", " + targetZ);
		return true;
	}

	@Override
	public boolean shouldContinue() {
		return !mob.isPathOrGoal(this.mob.getBlockPos().down()) && checkForExistingPath();
	}

	@Override
	public void start() {
		//HomeLawnSecurity.LOGGER.info("zombie started moving towards path of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
		this.mob.getNavigation().startMovingTo(this.targetX, this.targetY, this.targetZ, this.speed);
	}

	/*@Override
	public void stop() {
		HomeLawnSecurity.LOGGER.info("zombie stopped moving towards path of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
	}*/

	@Nullable
	protected Vec3d locatePathPos() {
		int rangeH = 2;
		int rangeV = 2;
		Iterable<BlockPos> iterable = BlockPos.iterateRandomly(mob.getRandom(), rangeH * rangeV * rangeH, MathHelper.floor(this.mob.getX() - rangeH), MathHelper.floor(this.mob.getY() - rangeV), MathHelper.floor(this.mob.getZ() - rangeH), MathHelper.floor(this.mob.getX() + rangeH), MathHelper.floor(this.mob.getY() + rangeV), MathHelper.floor(this.mob.getZ() + rangeH));
		for (BlockPos blockPos : iterable) {
			if (!mob.isPathOrGoal(blockPos.down())) continue;
			if (!this.mob.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.speed)) continue;
			return Vec3d.ofBottomCenter(blockPos);
		}
		return null;
	}
}
