package net.db64.homelawnsecurity.entity.ai.other;

import net.db64.homelawnsecurity.entity.ai.StayOnPathGoal;
import net.db64.homelawnsecurity.entity.custom.other.LawnMowerEntity;

public class LawnMowerStayOnPathGoal extends StayOnPathGoal {
	public LawnMowerStayOnPathGoal(LawnMowerEntity mob, double speed) {
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
}
