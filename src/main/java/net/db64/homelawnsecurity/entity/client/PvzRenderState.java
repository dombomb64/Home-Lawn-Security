package net.db64.homelawnsecurity.entity.client;

import net.minecraft.client.render.entity.state.LivingEntityRenderState;

public abstract class PvzRenderState extends LivingEntityRenderState {
	public boolean lastAttackedByPvzEntity = true;
	public boolean actuallyTakingDamage = false;
	public float hurtFlashTime = 0;

	public float getAnimationCounter() {
		float oneOverDuration = 0.1f;
		if (lastAttackedByPvzEntity && actuallyTakingDamage) {
			return 0.45f * (hurtFlashTime * oneOverDuration);
		}
		else return 0f;
	}
}
