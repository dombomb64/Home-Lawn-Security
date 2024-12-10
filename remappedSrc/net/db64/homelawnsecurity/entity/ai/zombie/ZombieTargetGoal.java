package net.db64.homelawnsecurity.entity.ai.zombie;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;

public class ZombieTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
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
}
