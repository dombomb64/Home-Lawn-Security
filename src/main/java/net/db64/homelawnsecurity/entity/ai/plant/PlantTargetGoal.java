package net.db64.homelawnsecurity.entity.ai.plant;

import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.other.TargetZombieEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PlantTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
	public float attackRange;
	public float attackRangePath;

	public Predicate<LivingEntity> rangePredicate = entity -> {
		// Cubic attack range for a tile-based game

		float range = ((PlantEntity) mob).onPath ? attackRangePath : attackRange;

		/*HomeLawnSecurity.LOGGER.info("range: {}, in range: {}", range, (Math.abs(mob.getX() - entity.getX()) < range)
			&& (Math.abs(mob.getY() - entity.getY()) < range)
			&& (Math.abs(mob.getZ() - entity.getZ()) < range));*/

		return (Math.abs(mob.getX() - entity.getX()) < range)
			&& (Math.abs(mob.getY() - entity.getY()) < range)
			&& (Math.abs(mob.getZ() - entity.getZ()) < range);
	};

	public Predicate<LivingEntity> intersectionPredicate = entity -> {
		boolean result = false;

		for (int pathId = 1; pathId <= 2; pathId++) {
			// Find paths to the enemy and to the goal
			Path targetPath = mob.getNavigation().findPathTo(entity, 1);
			Path goalPath = findGoalPath();
			if (targetPath == null || goalPath == null) {
				((PlantEntity) mob).switchPathTag();
				continue;
			}
			//HomeLawnSecurity.LOGGER.info("targetPath: {}, goalPath: {}", targetPath, goalPath);

			/*// Get the path node that the enemy is at and get its position
			PathNode targetNode = targetPath.getEnd();
			if (targetNode == null) {
				((PlantEntity) mob).switchPathTag();
				continue;
			}
			BlockPos targetPos = targetNode.getBlockPos();*/
			BlockPos targetPos = targetPath.getTarget();
			//HomeLawnSecurity.LOGGER.info("targetPos: {}", targetPos.toString());

			// Go through the goal path and see if the enemy is in front or not
			if (targetPos.equals(entity.getBlockPos())) return true;
			for (int i = 0; i < goalPath.getLength(); i++) {
				//HomeLawnSecurity.LOGGER.info("checking for if goalPath has position {} at node #{}", targetPos.toShortString(), i);
				if (goalPath.getNode(i).getBlockPos().equals(targetPos)) {
					//HomeLawnSecurity.LOGGER.info("goalPath does share a node position with targetPos");

					result = true;
				}
			}
			//HomeLawnSecurity.LOGGER.info("goalPath did not share a node position with targetPos");

			// We switch the path because we need to trick the pathfinding into looking down the other path
			// And we do it in such a way that it triggers exactly twice
			((PlantEntity) mob).switchPathTag();
		}

		return result;
	};

	public Predicate<LivingEntity> pathPredicate = entity -> {
		// If it was planted on a path, it should only target entities that are between it and its goal

		//HomeLawnSecurity.LOGGER.info("checking pathPredicate");

		if (((PlantEntity) mob).onIntersection)
			// This plant needs to target entities on both paths, redirect them to a different predicate
			return intersectionPredicate.test(entity);

		if (!(entity instanceof IPathBoundEntity) || !((IPathBoundEntity) mob).getPathTagNbt().equals(((IPathBoundEntity) entity).getPathTagNbt()))
			// It's on a different path, the enemy is of no concern
			return false;
		//HomeLawnSecurity.LOGGER.info("pathTag of mob ({}) is the same as pathTag of entity ({})", ((IPathBoundEntity) mob).getPathTagNbt(), ((IPathBoundEntity) entity).getPathTagNbt());

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

	public PlantTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, float attackRange, float attackRangePath) {
		super(mob, targetClass, checkVisibility);
		this.attackRange = attackRange;
		this.attackRangePath = attackRangePath;
	}

	@Override
	protected double getFollowRange() {
		if (((PlantEntity) mob).onPath) {
			return this.attackRangePath * 1.42;
		}

		return this.attackRange * 1.42; // Just barely enough to fill the cube
	}

	protected Box getSearchBox(double distance) {
		float range = ((PlantEntity) mob).onPath ? attackRangePath : attackRange;

		return new Box(mob.getX() - range, mob.getY() - range, mob.getZ() - range,
			mob.getX() + range, mob.getY() + range, mob.getZ() + range);
	}

	@Override
	public boolean shouldContinue() {
		//HomeLawnSecurity.LOGGER.info("target: " + target + ", targetEntity: " + targetEntity);
		/*if (((PlantEntity) mob).onPath) {
			return (targetEntity == null || rangePredicate.test(targetEntity))
				&& super.shouldContinue()
				&& pathPredicate.test(targetEntity);
		}*/

		return (targetEntity == null || rangePredicate.test(targetEntity))
			&& super.shouldContinue();
	}

	@Override
	public boolean canStart() {
		/*if (((PlantEntity) mob).onPath) {
			return super.canStart() && rangePredicate.test(targetEntity) && pathPredicate.test(targetEntity);
		}

		return super.canStart() && rangePredicate.test(targetEntity);*/

		return super.canStart();
	}

	@Override
	protected boolean canTrack(@Nullable LivingEntity target, TargetPredicate targetPredicate) {
		if (target != null && ((PlantEntity) mob).onPath) {
			return super.canTrack(target, targetPredicate) && rangePredicate.test(target) && pathPredicate.test(target);
		}

		return super.canTrack(target, targetPredicate) && rangePredicate.test(target);
	}

	protected void findClosestTarget() {
		ServerWorld serverWorld = getServerWorld(this.mob);
		if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
			if (((PlantEntity) mob).onPath) {
				this.targetEntity = serverWorld.getClosestEntity(
					this.mob.getWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()),
						livingEntity -> rangePredicate.test(livingEntity) && pathPredicate.test(livingEntity)),
					this.getAndUpdateTargetPredicate(),
					this.mob,
					this.mob.getX(),
					this.mob.getEyeY(),
					this.mob.getZ()
				);
			}
			else {
				this.targetEntity = serverWorld.getClosestEntity(
					this.mob.getWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()),
						livingEntity -> !(livingEntity instanceof TargetZombieEntity) && rangePredicate.test(livingEntity)),
					this.getAndUpdateTargetPredicate(),
					this.mob,
					this.mob.getX(),
					this.mob.getEyeY(),
					this.mob.getZ()
				);
			}
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
			//HomeLawnSecurity.LOGGER.info("plant is checking for if the block at {}, {}, {} is a goal", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!((IPathBoundEntity) mob).isGoal(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("plant is checking for if the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			Path path = mob.getNavigation().findPathTo(blockPos, 1);
			if (path == null) continue;
			//HomeLawnSecurity.LOGGER.info("plant has decided the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			return path;
		}
		return null;
	}
}
