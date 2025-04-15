package net.db64.homelawnsecurity.entity.ai.zombie;

import net.db64.homelawnsecurity.entity.ai.StayOnPathGoal;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;

public class ZombieStayOnPathGoal extends StayOnPathGoal {
	public ZombieStayOnPathGoal(ZombieEntity mob, double speed) {
		super(mob, speed);
	}
}
