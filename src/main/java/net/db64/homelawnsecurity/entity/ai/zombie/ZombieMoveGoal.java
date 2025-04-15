package net.db64.homelawnsecurity.entity.ai.zombie;

import net.db64.homelawnsecurity.entity.ai.MoveToGoalGoal;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;

public class ZombieMoveGoal extends MoveToGoalGoal {
	public ZombieMoveGoal(ZombieEntity mob, double speed) {
		super(mob, speed);
	}
}
