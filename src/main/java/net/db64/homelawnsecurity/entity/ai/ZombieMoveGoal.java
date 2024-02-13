package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class ZombieMoveGoal extends Goal {
	protected final ZombieEntity mob;
	private double targetX;
	private double targetY;
	private double targetZ;
	private final double speed;
	private final World world;

	public ZombieMoveGoal(ZombieEntity mob, double speed) {
		this.mob = mob;
		this.speed = speed;
		this.world = mob.getWorld();
		this.setControls(EnumSet.of(Control.MOVE));
	}

	@Override
	public boolean canStart() {
		return !isGoal(this.world.getBlockState(this.mob.getBlockPos().down())) && this.checkForExistingGoal();
	}

	private boolean checkForExistingGoal() {
		BlockPos targetPos = new BlockPos(MathHelper.floor(this.targetX), MathHelper.floor(this.targetY), MathHelper.floor(this.targetZ));
		//HomeLawnSecurity.LOGGER.info("zombie's current goal pos: " + targetX + ", " + targetY + ", " + targetZ + " | " + targetPos.toShortString() + " zombie's current pos: " + mob.getPos().toString() + " | " + mob.getBlockPos().toShortString());
		if (isGoal(this.world.getBlockState(targetPos.down()))) {
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
		return !isGoal(this.world.getBlockState(this.mob.getBlockPos().down())) && checkForExistingGoal();
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
		int rangeH = 10;
		int rangeV = 3;
		Iterable<BlockPos> iterable = BlockPos.iterate(MathHelper.floor(this.mob.getX() - rangeH), MathHelper.floor(this.mob.getY() - rangeV), MathHelper.floor(this.mob.getZ() - rangeH), MathHelper.floor(this.mob.getX() + rangeH), MathHelper.floor(this.mob.getY() + rangeV), MathHelper.floor(this.mob.getZ() + rangeH));
		for (BlockPos blockPos : iterable) {
			if (!isGoal(this.world.getBlockState(blockPos.down()))) continue;
			if (!this.mob.getNavigation().startMovingTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), this.speed)) continue;
			return Vec3d.ofBottomCenter(blockPos);
		}
		return null;
	}

	private boolean isGoal(BlockState state) {
		return state.isIn(ModTags.Blocks.ZOMBIE_GOAL);
	}
}
