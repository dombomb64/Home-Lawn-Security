package net.db64.homelawnsecurity.entity.ai;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.predicate.entity.EntityPredicates;

public class ZombieTargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
	public ZombieTargetGoal(MobEntity mob, Class<T> targetClass, boolean checkVisibility) {
		super(mob, targetClass, checkVisibility, entity -> {
			ZombieEntity zombie = (ZombieEntity) mob;
			//BlockState state = entity.getWorld().getBlockState(entity.getBlockPos().down());
			return zombie.isPath(entity.getBlockPos().down());
		});
	}

	@Override
	protected double getFollowRange() {
		return 1f;
	}
}
