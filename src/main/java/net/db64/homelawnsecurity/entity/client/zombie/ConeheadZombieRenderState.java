package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.client.ZombieRenderState;
import net.minecraft.entity.AnimationState;

public class ConeheadZombieRenderState extends ZombieRenderState {
	public boolean hasLostHeadwear = false;
	public boolean hasLostArm = false;
	public boolean hasLostHead = false;

	public final AnimationState setupAnimationState = new AnimationState();
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();

	public ConeheadZombieRenderState() {
		super();
	}

	public boolean getHasLostHeadwear() {
		return hasLostHeadwear;
	}

	public boolean getHasLostArm() {
		return hasLostArm;
	}

	public boolean getHasLostHead() {
		return hasLostHead;
	}
}
