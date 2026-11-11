package net.db64.homelawnsecurity.entity.client;

import net.db64.homelawnsecurity.entity.custom.PathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

public abstract class PvzEntityRenderer<
	T extends PathBoundEntity,
	S extends PvzRenderState,
	M extends EntityModel<? super S>> extends MobEntityRenderer<T, S, M> {
	public PvzEntityRenderer(EntityRendererFactory.Context context, M model, float shadowRadius) {
		super(context, model, shadowRadius);
	}

	@Override
	protected float getAnimationCounter(S state) {
		return state.getAnimationCounter();
	}

	@Override
	public void updateRenderState(T entity, S state, float tickProgress) {
		super.updateRenderState(entity, state, tickProgress);
		state.actuallyTakingDamage = state.hurt;
		if (state.actuallyTakingDamage) {
			state.lastAttackedByPvzEntity = entity instanceof PathBoundEntity entity2 && entity2.lastAttackedByPvzEntity;
		}
		if (state.lastAttackedByPvzEntity) {
			state.hurt = false;
		}
		state.hurtFlashTime = MathHelper.lerp(tickProgress, (float) entity.previousHurtFlashTime, (float) entity.hurtFlashTime);
	}
}
