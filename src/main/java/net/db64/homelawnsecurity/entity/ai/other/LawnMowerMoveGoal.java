package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.entity.ai.MoveToGoalGoal;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;

public class LawnMowerMoveGoal extends MoveToGoalGoal {
	public LawnMowerMoveGoal(LawnMowerEntity mob, double speed) {
		super(mob, speed);
	}

	@Override
	public boolean canStart() {
		return ((LawnMowerEntity) mob).mowing && super.canStart();
	}

	@Override
	public boolean shouldContinue() {
		return ((LawnMowerEntity) mob).mowing && super.shouldContinue();
	}

	@Override
	public void stop() {
		//HomeLawnSecurity.LOGGER.info("lawnmower stopped moving towards goal of " + targetX + ", " + targetY + ", " + targetZ + " while at " + mob.getPos().toString());
		((LawnMowerEntity) mob).disappear(); // I must go, my people need me
	}
}
