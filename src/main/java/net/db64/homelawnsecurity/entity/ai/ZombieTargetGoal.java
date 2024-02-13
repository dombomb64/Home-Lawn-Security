package net.db64.homelawnsecurity.entity.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;

public class ZombieTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
	public ZombieTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility) {
		super(mob, targetClass, checkVisibility);
	}

	@Override
	protected double getFollowRange() {
		return 1.0f;
	}
}
