package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;

public class LawnMowerTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
	public LawnMowerTargetGoal(LawnMowerEntity mob, Class<T> targetClass, boolean checkVisibility) {
		super(mob, targetClass, checkVisibility, (target, world) -> {
			LawnMowerEntity lawnMower = mob;
			//BlockState state = world.getBlockState(target.getBlockPos().down());
			return lawnMower.isPath(target.getBlockPos().down());
		});
	}

	@Override
	public boolean canStart() {
		return ((LawnMowerEntity) mob).mowing && super.canStart();
	}

	@Override
	protected double getFollowRange() {
		return 1f;
	}
}
