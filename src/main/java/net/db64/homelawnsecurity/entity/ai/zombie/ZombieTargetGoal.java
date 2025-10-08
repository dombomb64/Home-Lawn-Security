package net.db64.homelawnsecurity.entity.ai.zombie;

import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
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
	public float attackRange;

	public Predicate<LivingEntity> rangePredicate = entity -> {
		// Cubic attack range for a tile-based game

		/*HomeLawnSecurity.LOGGER.info("range: {}, in range: {}", attackRange, (Math.abs(mob.getBlockX() - entity.getBlockX()) <= attackRange)
			&& (Math.abs(mob.getBlockY() - entity.getBlockY()) <= attackRange)
			&& (Math.abs(mob.getBlockZ() - entity.getBlockZ()) <= attackRange));*/

		return (Math.abs(mob.getBlockX() - entity.getBlockX()) <= attackRange)
			&& (Math.abs(mob.getBlockY() - entity.getBlockY()) <= attackRange)
			&& (Math.abs(mob.getBlockZ() - entity.getBlockZ()) <= attackRange);
	};

	public Predicate<LivingEntity> notBehindPredicate = entity -> {
		// It should not target entities that are between it and its start

		//HomeLawnSecurity.LOGGER.info("checking notBehindPredicate");

		// Check for if the enemy is on a different path
		if (entity instanceof PlantEntity && ((PlantEntity) entity).onPath && !((PlantEntity) entity).isThisPath(mob.getSteppingPos()))
			return false;

		// Find paths to the enemy and to the start
		Path targetPath = mob.getNavigation().findPathTo(entity, 1);
		Path startPath = findStartPath();
		if (targetPath == null || startPath == null) return false;
		//HomeLawnSecurity.LOGGER.info("targetPath: {}, startPath: {}", targetPath, startPath);

		// Get the position of the enemy
		BlockPos targetPos = targetPath.getTarget();
		//HomeLawnSecurity.LOGGER.info("targetPos: {}", targetPos.toString());

		// Go through the start path and see if the enemy is behind or not
		if (startPath.getTarget().equals(entity.getBlockPos())) return false; // *tells predicate to return true if the pos at the end of the path to the enemy is the same as the enemy's pos* "Why is it ignoring the pathfinding part of the predicate?" -Me, apparently
		for (int i = 0; i < startPath.getLength(); i++) {
			//HomeLawnSecurity.LOGGER.info("checking for if startPath has position {} at node #{}", targetPos.toShortString(), i);
			if (startPath.getNode(i).getBlockPos().equals(targetPos)) {
				//HomeLawnSecurity.LOGGER.info("startPath does share a node position with targetPos");
				return false;
			}
		}
		//HomeLawnSecurity.LOGGER.info("startPath did not share a node position with targetPos");
		return true;
	};

	public Predicate<LivingEntity> hasHeadPredicate = entity -> {
		if (mob instanceof ZombieEntity zombie) {
			return zombie.hasHead();
		}
		return true;
	};

	public ZombieTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, float attackRange) {
		super(mob, targetClass, checkVisibility/*, (target, world) -> {
			ZombieEntity zombie = (ZombieEntity) mob;
			//BlockState state = world.getBlockState(target.getBlockPos().down());
			return zombie.isThisPath(target.getBlockPos().down());
		}*/);
		this.attackRange = attackRange;
	}

	@Override
	protected double getFollowRange() {
		return this.attackRange * 1.42; // Just barely enough to fill the cube
	}

	protected void findClosestTarget() {
		ServerWorld serverWorld = getServerWorld(this.mob);
		if (this.targetClass != PlayerEntity.class && this.targetClass != ServerPlayerEntity.class) {
			this.targetEntity = serverWorld.getClosestEntity(
				this.mob.getEntityWorld().getEntitiesByClass(this.targetClass, this.getSearchBox(this.getFollowRange()),
					livingEntity -> rangePredicate.test(livingEntity) && notBehindPredicate.test(livingEntity) && hasHeadPredicate.test(livingEntity)),
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
	private Path findStartPath() {
		int rangeH = 16;
		int rangeV = 5;
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(mob.getSteppingPos().up(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("zombie is checking for if the block at {}, {}, {} is a goal", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!((IPathBoundEntity) mob).isStart(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("zombie is checking for if the start at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			Path path = mob.getNavigation().findPathTo(blockPos, 1);
			if (path == null) continue;
			//HomeLawnSecurity.LOGGER.info("zombie has decided the start at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			return path;
		}
		return null;
	}

	@Override
	public boolean shouldContinue() {
		if (mob instanceof ZombieEntity zombie && !zombie.hasHead())
			mob.setTarget(null);
		return super.shouldContinue()
			&& (targetEntity == null || hasHeadPredicate.test(targetEntity));
	}
}
