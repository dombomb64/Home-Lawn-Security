package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.entity.client.PvzRenderState;
import net.minecraft.entity.AnimationState;

public class ChomperRenderState extends PvzRenderState {
	public final AnimationState setupAnimationState = new AnimationState();
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();
	public final AnimationState chompAnimationState = new AnimationState();
	public final AnimationState chewingAnimationState = new AnimationState();
	public final AnimationState swallowAnimationState = new AnimationState();

	public ChomperRenderState() {

	}
}
