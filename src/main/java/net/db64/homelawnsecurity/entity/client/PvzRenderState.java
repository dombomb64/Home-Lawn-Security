package net.db64.homelawnsecurity.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public abstract class PvzRenderState extends LivingEntityRenderState {
	public boolean lastAttackedByPvzEntity = true;
	public boolean actuallyTakingDamage = false;
	public int hurtFlashTime = 0;

	public float getAnimationCounter() {
		if (lastAttackedByPvzEntity && actuallyTakingDamage) {
			return 0.25f * (hurtFlashTime * 0.1f);
		}
		else return 0f;
	}
}
