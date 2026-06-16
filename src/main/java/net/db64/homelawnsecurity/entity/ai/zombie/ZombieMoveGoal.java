package net.db64.homelawnsecurity.entity.ai.zombie;

import net.db64.homelawnsecurity.entity.ai.MoveToGoalGoal;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.util.ModGameEvents;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.event.GameEvent;

public class ZombieMoveGoal extends MoveToGoalGoal {
	public ZombieMoveGoal(ZombieEntity mob, double speed) {
		super(mob, speed);
	}

	@Override
	public boolean canStart() {
		return mob instanceof ZombieEntity zombie && !zombie.movementBlocked && super.canStart();
	}

	@Override
	public boolean shouldContinue() {
		return mob instanceof ZombieEntity zombie && !zombie.movementBlocked && super.shouldContinue();
	}

	@Override
	public void stop() {
		super.stop();
		if (mob.isGoal(mob.getSteppingPos()) && world instanceof ServerWorld serverWorld
			&& mob instanceof ZombieEntity zombie && zombie.isInPlay()) {
			serverWorld.emitGameEvent(ModGameEvents.ZOMBIE_REACH_GOAL, mob.getEntityPos(), GameEvent.Emitter.of(mob));
		}
	}
}
