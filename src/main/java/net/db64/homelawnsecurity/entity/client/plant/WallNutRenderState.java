package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.entity.client.PvzRenderState;
import net.minecraft.entity.AnimationState;

public class WallNutRenderState extends PvzRenderState {
	public final AnimationState setupAnimationState = new AnimationState();
	public byte degradation = 0;

	public WallNutRenderState() {

	}
}
