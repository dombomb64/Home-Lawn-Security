package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.entity.ai.StayOnPathGoal;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;

public class LawnMowerStayOnPathGoal extends StayOnPathGoal {
	public LawnMowerStayOnPathGoal(LawnMowerEntity mob, double speed) {
		super(mob, speed);
	}

	@Override
	public boolean canStart() {
		LawnMowerEntity mower = ((LawnMowerEntity) mob);
		return mower.mowing && !mower.pathFinished && super.canStart();
	}

	@Override
	public boolean shouldContinue() {
		return ((LawnMowerEntity) mob).mowing && super.shouldContinue();
	}
}
