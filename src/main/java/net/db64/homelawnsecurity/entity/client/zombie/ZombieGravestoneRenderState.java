package net.db64.homelawnsecurity.entity.client.zombie;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class ZombieGravestoneRenderState extends LivingEntityRenderState {
	public final AnimationState setupAnimationState = new AnimationState();

	public float health = 100;

	public ZombieGravestoneRenderState() {

	}
}
