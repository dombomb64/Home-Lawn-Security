package net.db64.homelawnsecurity.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class PeashooterAttackGoal extends Goal {
	private final MobEntity mob;
	private final RangedAttackMob owner;
	@Nullable
	private LivingEntity target;
	private int updateCountdownTicks = -1;
	private final double mobSpeed;
	private int seenTargetTicks;
	private final int minIntervalTicks;
	private final int maxIntervalTicks;
	private final float maxShootRange;
	private final float squaredMaxShootRange;

	public Predicate<LivingEntity> rangePredicate;

	public PeashooterAttackGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
		this(mob, mobSpeed, intervalTicks, intervalTicks, maxShootRange);
	}

	public PeashooterAttackGoal(RangedAttackMob mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
		if (!(mob instanceof LivingEntity)) {
			throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
		}
		this.owner = mob;
		this.mob = (MobEntity) mob;
		this.mobSpeed = mobSpeed;
		this.minIntervalTicks = minIntervalTicks;
		this.maxIntervalTicks = maxIntervalTicks;
		this.maxShootRange = maxShootRange;
		this.squaredMaxShootRange = maxShootRange * maxShootRange;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));

		rangePredicate = entity -> {
			// Cubic attack range for a tile-based game
			return (Math.abs(this.mob.getX() - entity.getX()) < maxShootRange)
				&& (Math.abs(this.mob.getY() - entity.getY()) < maxShootRange)
				&& (Math.abs(this.mob.getZ() - entity.getZ()) < maxShootRange);
		};
	}

	@Override
	public boolean canStart() {
		LivingEntity livingEntity = this.mob.getTarget();
		if (livingEntity == null || !livingEntity.isAlive()) {
			return false;
		}
		this.target = livingEntity;
		return true;
	}

	@Override
	public boolean shouldContinue() {
		return this.canStart() || this.target.isAlive() && !this.mob.getNavigation().isIdle() && rangePredicate.test(target);
	}

	@Override
	public void stop() {
		this.target = null;
		this.seenTargetTicks = 0;
		this.updateCountdownTicks = -1;
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		double d = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
		boolean bl = this.mob.getVisibilityCache().canSee(this.target);
		this.seenTargetTicks = bl ? ++this.seenTargetTicks : 0;
		if (d > (double)this.squaredMaxShootRange || this.seenTargetTicks < 5) {
			this.mob.getNavigation().startMovingTo(this.target, this.mobSpeed);
		} else {
			this.mob.getNavigation().stop();
		}
		this.mob.getLookControl().lookAt(this.target.getX(), this.mob.getEyeY(), this.target.getZ(),
			30.0f, 30.0f); // Oh boy I sure love plagiarizing an entire Java class to change one instruction // Never mind
		if (--this.updateCountdownTicks == 0) {
			if (!bl) {
				return;
			}
			float f = (float)Math.sqrt(d) / this.maxShootRange;
			float g = MathHelper.clamp(f, 0.1f, 1.0f);
			this.owner.shootAt(this.target, g);
			this.updateCountdownTicks = MathHelper.floor(f * (float)(this.maxIntervalTicks - this.minIntervalTicks) + (float)this.minIntervalTicks);
		} else if (this.updateCountdownTicks < 0) {
			this.updateCountdownTicks = MathHelper.floor(MathHelper.lerp(Math.sqrt(d) / (double)this.maxShootRange, (double)this.minIntervalTicks, (double)this.maxIntervalTicks));
		}
	}
}
