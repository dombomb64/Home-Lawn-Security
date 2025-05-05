package net.db64.homelawnsecurity.entity.client.zombie;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

public class ZombieGravestoneRenderState extends LivingEntityRenderState {
	public boolean cracks = false;
	public boolean break1 = false;
	public boolean break2 = false;
	public boolean break3 = false;
	public boolean break4 = false;

	public final AnimationState setupAnimationState = new AnimationState();

	public float health = 100;

	public ZombieGravestoneRenderState() {

	}

	public boolean getCracks() {
		return cracks;
	}

	public boolean getBreak1() {
		return break1;
	}

	public boolean getBreak2() {
		return break2;
	}

	public boolean getBreak3() {
		return break3;
	}

	public boolean getBreak4() {
		return break4;
	}
}
