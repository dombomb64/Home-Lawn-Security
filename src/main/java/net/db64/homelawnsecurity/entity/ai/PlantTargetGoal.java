package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PlantTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
	public float attackRange;
	public Predicate<LivingEntity> rangePredicate = entity -> {
		// Cubic attack range for a tile-based game
		return (Math.abs(mob.getX() - entity.getX()) < attackRange)
			&& (Math.abs(mob.getY() - entity.getY()) < attackRange)
			&& (Math.abs(mob.getZ() - entity.getZ()) < attackRange);
	};

	public PlantTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility, float attackRange) {
		super(mob, targetClass, checkVisibility);
		this.attackRange = attackRange;
	}

	@Override
	protected double getFollowRange() {
		return this.attackRange * 1.42; // Just barely enough to fill the cube
	}

	protected Box getSearchBox(double distance) {
		return new Box(mob.getX() - attackRange, mob.getY() - attackRange, mob.getZ() - attackRange,
			mob.getX() + attackRange, mob.getY() + attackRange, mob.getZ() + attackRange);
	}

	@Override
	public boolean shouldContinue() {
		//HomeLawnSecurity.LOGGER.info("target: " + target + ", targetEntity: " + targetEntity);
		return (targetEntity == null || rangePredicate.test(targetEntity))
			&& super.shouldContinue();
	}

	@Override
	public boolean canStart() {
		return super.canStart() && rangePredicate.test(targetEntity);
	}

	@Override
	protected boolean canTrack(@Nullable LivingEntity target, TargetPredicate targetPredicate) {
		return super.canTrack(target, targetPredicate) && rangePredicate.test(target);
	}
}
