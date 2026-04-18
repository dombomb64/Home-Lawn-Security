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
	public void stop() {
		super.stop();
		if (mob.isGoal(mob.getSteppingPos()) && world instanceof ServerWorld serverWorld) {
			serverWorld.emitGameEvent(ModGameEvents.ZOMBIE_REACH_GOAL, mob.getEntityPos(), GameEvent.Emitter.of(mob));
		}
	}
}
