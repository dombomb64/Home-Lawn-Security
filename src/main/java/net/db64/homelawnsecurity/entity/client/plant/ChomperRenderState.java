package net.db64.homelawnsecurity.entity.client.plant;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class ChomperRenderState extends LivingEntityRenderState {
	public final AnimationState setupAnimationState = new AnimationState();
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();
	public final AnimationState chompAnimationState = new AnimationState();
	public final AnimationState chewingAnimationState = new AnimationState();
	public final AnimationState swallowAnimationState = new AnimationState();

	public ChomperRenderState() {

	}
}
