package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.client.ZombieRenderState;
import net.minecraft.entity.AnimationState;

public class BucketheadZombieRenderState extends ZombieRenderState {
	public boolean hasLostHeadwear = false;
	public boolean hasLostArm = false;
	public boolean hasLostHead = false;

	public final AnimationState setupAnimationState = new AnimationState();
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();
	public final AnimationState bucketSetupAnimationState = new AnimationState();

	public BucketheadZombieRenderState() {
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
