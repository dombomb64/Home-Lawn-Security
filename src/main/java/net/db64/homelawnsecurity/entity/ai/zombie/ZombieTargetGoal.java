package net.db64.homelawnsecurity.entity.ai.zombie;

import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ZombieTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
	public Predicate<LivingEntity> inTheWayPredicate = entity -> {
		// It should only target entities that are between it and its goal

		//HomeLawnSecurity.LOGGER.info("checking inTheWayPredicate");

		// Find paths to the enemy and to the goal
		Path targetPath = mob.getNavigation().findPathTo(entity, 1);
		Path goalPath = findGoalPath();
		if (targetPath == null || goalPath == null) return false;
		//HomeLawnSecurity.LOGGER.info("targetPath: {}, goalPath: {}", targetPath, goalPath);

		/*// Get the path node that the enemy is at and get its position
		PathNode targetNode = targetPath.getEnd();
		if (targetNode == null) return false;
		BlockPos targetPos = targetNode.getBlockPos();*/
		BlockPos targetPos = targetPath.getTarget();
		//HomeLawnSecurity.LOGGER.info("targetPos: {}", targetPos.toString());

		// Go through the goal path and see if the enemy is in front or not
		if (targetPos.equals(entity.getBlockPos())) return true;
		for (int i = 0; i < goalPath.getLength(); i++) {
			//HomeLawnSecurity.LOGGER.info("checking for if goalPath has position {} at node #{}", targetPos.toShortString(), i);
			if (goalPath.getNode(i).getBlockPos().equals(targetPos)) {
				//HomeLawnSecurity.LOGGER.info("goalPath does share a node position with targetPos");
				return true;
			}
		}
		//HomeLawnSecurity.LOGGER.info("goalPath did not share a node position with targetPos");
		return false;
	};

	public ZombieTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility) {
		super(mob, targetClass, checkVisibility, (target, world) -> {
			ZombieEntity zombie = (ZombieEntity) mob;
			//BlockState state = world.getBlockState(target.getBlockPos().down());
			return zombie.isPath(target.getBlockPos().down());
		});
	}

	@Override
	protected double getFollowRange() {
		return 1f;
	}

	protected void findClosestTarget() {
		ServerWorld serverWorld = getServerWorld(this.mob);
		if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
			this.targetEntity = serverWorld.getClosestEntity(
				this.mob.getWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()),
					livingEntity -> inTheWayPredicate.test(livingEntity)),
				this.getAndUpdateTargetPredicate(),
				this.mob,
				this.mob.getX(),
				this.mob.getEyeY(),
				this.mob.getZ()
			);
		} else {
			this.targetEntity = serverWorld.getClosestPlayer(this.getAndUpdateTargetPredicate(), this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		}
	}

	private TargetPredicate getAndUpdateTargetPredicate() {
		return this.targetPredicate.setBaseMaxDistance(this.getFollowRange());
	}

	@Nullable
	private Path findGoalPath() {
		int rangeH = 16;
		int rangeV = 5;
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(mob.getBlockPos(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("zombie is checking for if the block at {}, {}, {} is a goal", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!((IPathBoundEntity) mob).isGoal(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("zombie is checking for if the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			Path path = mob.getNavigation().findPathTo(blockPos, 1);
			if (path == null) continue;
			//HomeLawnSecurity.LOGGER.info("zombie has decided the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			return path;
		}
		return null;
	}
}
