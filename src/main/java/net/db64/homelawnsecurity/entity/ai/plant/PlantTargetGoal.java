package net.db64.homelawnsecurity.entity.ai.plant;

import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.TargetZombieEntity;
import net.db64.homelawnsecurity.util.LawnUtil;
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

		/*HomeLawnSecurity.LOGGER.info("range: {}, in range: {}", range, (Math.abs(mob.getBlockX() - entity.getBlockX()) <= range)
			&& (Math.abs(mob.getBlockY() - entity.getBlockY()) <= range)
			&& (Math.abs(mob.getBlockZ() - entity.getBlockZ()) <= range));*/

		return (Math.abs(mob.getBlockX() - entity.getBlockX()) <= range)
			&& (Math.abs(mob.getBlockY() - entity.getBlockY()) <= range)
			&& (Math.abs(mob.getBlockZ() - entity.getBlockZ()) <= range);
	};

	public Predicate<LivingEntity> intersectionPredicate = entity -> {
		boolean result = false;

		for (int i = 1; i <= 2; i++) {
			int mainPathId = LawnUtil.getMainPathId(entity.getBlockPos(), entity.getEntityWorld());
			int intersectingPathId = LawnUtil.getIntersectingPathId(entity.getBlockPos(), entity.getEntityWorld());
			//int pathId = i == 1 ? mainPathId : intersectingPathId;
			int otherPathId = i == 1 ? intersectingPathId : mainPathId;

			// Find paths to the enemy and to the goal
			Path targetPath = mob.getNavigation().findPathTo(entity, 1);
			Path goalPath = findGoalPath();
			if (targetPath == null || goalPath == null) {
				((PlantEntity) mob).setPathId(otherPathId);
				continue;
			}
			//HomeLawnSecurity.LOGGER.info("targetPath: {}, goalPath: {}", targetPath, goalPath);

			// Get the position of the enemy
			BlockPos targetPos = targetPath.getTarget();
			//HomeLawnSecurity.LOGGER.info("targetPos: {}", targetPos.toString());

			// Go through the goal path and see if the enemy is in front or not
			if (goalPath.getTarget().equals(entity.getBlockPos())) return true; // *tells predicate to return true if the pos at the end of the path to the enemy is the same as the enemy's pos* "Why is it ignoring the pathfinding part of the predicate?" -Me, apparently
			for (int j = 0; j < goalPath.getLength(); j++) {
				//HomeLawnSecurity.LOGGER.info("checking for if goalPath has position {} at node #{}", targetPos.toShortString(), j);
				if (goalPath.getNode(j).getBlockPos().equals(targetPos)) {
					//HomeLawnSecurity.LOGGER.info("goalPath does share a node position with targetPos");

					result = true;
				}
			}
			//HomeLawnSecurity.LOGGER.info("goalPath did not share a node position with targetPos");

			// We switch the path because we need to trick the pathfinding into looking down the other path
			// And we do it in such a way that it triggers exactly twice
			((PlantEntity) mob).setPathId(otherPathId);
		}

		return result;
	};

	public Predicate<LivingEntity> pathPredicate = entity -> {
		// If it was planted on a path, it should only target entities that are between it and its goal

		//HomeLawnSecurity.LOGGER.info("checking pathPredicate");

		if (mob instanceof PlantEntity plant && !plant.onPath) {
			// This plant is not on a path, behave as such
			return !(entity instanceof TargetZombieEntity);
		}

		if (LawnUtil.getIntersectingPathId(entity.getBlockPos().down(), entity.getEntityWorld()) != 0)
			// This plant needs to target entities on both paths, redirect them to a different predicate
			return intersectionPredicate.test(entity);

		if (!(entity instanceof IPathBoundEntity) || ((IPathBoundEntity) mob).getPathId() != ((IPathBoundEntity) entity).getPathId())
			// It's on a different path, the enemy is of no concern
			return false;
		//HomeLawnSecurity.LOGGER.info("pathTag of mob ({}) is the same as pathTag of entity ({})", ((IPathBoundEntity) mob).getPathTagNbt(), ((IPathBoundEntity) entity).getPathTagNbt());

		// Find paths to the enemy and to the goal
		Path targetPath = mob.getNavigation().findPathTo(entity, 1);
		Path goalPath = findGoalPath();
		if (targetPath == null || goalPath == null) return false;
		//HomeLawnSecurity.LOGGER.info("targetPath: {}, goalPath: {}", targetPath, goalPath);

		// Get the position of the enemy
		BlockPos targetPos = targetPath.getTarget();
		//HomeLawnSecurity.LOGGER.info("targetPos: {}", targetPos.toString());

		// Go through the goal path and see if the enemy is in front or not
		if (goalPath.getTarget().equals(entity.getBlockPos())) return true; // *tells predicate to return true if the pos at the end of the path to the enemy is the same as the enemy's pos* "Why is it ignoring the pathfinding part of the predicate?" -Me, apparently
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

		if (targetEntity != null && (!rangePredicate.test(targetEntity) || !pathPredicate.test(targetEntity)))
			mob.setTarget(null);
		return (targetEntity == null || rangePredicate.test(targetEntity) && pathPredicate.test(targetEntity))
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
		return super.canTrack(target, targetPredicate) && rangePredicate.test(target) && pathPredicate.test(target);
	}

	protected void findClosestTarget() {
		ServerWorld serverWorld = getServerWorld(this.mob);
		if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
			this.targetEntity = serverWorld.getClosestEntity(
				this.mob.getEntityWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()),
					livingEntity -> rangePredicate.test(livingEntity) && pathPredicate.test(livingEntity)),
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
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(mob.getSteppingPos().up(), rangeH, rangeV, rangeH);
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
