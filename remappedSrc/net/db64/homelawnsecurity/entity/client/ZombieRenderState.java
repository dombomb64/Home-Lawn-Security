package net.db64.homelawnsecurity.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public class ZombieRenderState extends LivingEntityRenderState {
	public boolean hasLostHeadwear = false;
	public boolean hasLostArm = false;
	public boolean hasLostHead = false;

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
