package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.client.ZombieRenderState;
import net.minecraft.entity.AnimationState;

public class TargetZombieRenderState extends ZombieRenderState {
	public final AnimationState setupAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();

	public TargetZombieRenderState() {
		super();
	}
}
