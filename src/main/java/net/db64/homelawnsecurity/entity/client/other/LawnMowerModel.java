package net.db64.homelawnsecurity.entity.client.other;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class LawnMowerModel extends EntityModel<LawnMowerRenderState> {
	public LawnMowerModel(ModelPart root) {
		super(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -2.0F, -5.0F, 8.0F, 2.0F, 10.0F, new Dilation(0.0F))
			.uv(32, 20).cuboid(-5.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.0F, 0.0F));

		ModelPartData engine = body.addChild("engine", ModelPartBuilder.create().uv(16, 27).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
			.uv(0, 12).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F))
			.uv(24, 12).cuboid(-2.0F, -5.0F, -1.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F))
			.uv(12, 32).cuboid(-4.0F, -2.0F, -2.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));

		ModelPartData exhaust = engine.addChild("exhaust", ModelPartBuilder.create().uv(22, 32).cuboid(-2.0F, -0.5F, 0.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, -0.5F, 1.0F));

		ModelPartData handle = body.addChild("handle", ModelPartBuilder.create().uv(0, 20).cuboid(-4.0F, -12.0F, 0.0F, 8.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 5.0F, -0.4363F, 0.0F, 0.0F));

		ModelPartData cord0 = body.addChild("cord0", ModelPartBuilder.create().uv(16, 20).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 2.0F, 0.6981F, -0.3491F, 0.0F));

		ModelPartData cord1 = cord0.addChild("cord1", ModelPartBuilder.create().uv(32, 24).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 7.0F, -1.2654F, 0.0F, 0.0F));

		ModelPartData cord2 = cord1.addChild("cord2", ModelPartBuilder.create().uv(24, 18).cuboid(-0.5F, 0.0F, 1.0F, 3.0F, 0.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 0.0F, 1.0F));

		ModelPartData wheel0 = body.addChild("wheel0", ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -1.0F, -4.0F));

		ModelPartData wheel1 = body.addChild("wheel1", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(0.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-6.0F, -1.0F, -4.0F));

		ModelPartData wheel2 = body.addChild("wheel2", ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -1.0F, 4.0F));

		ModelPartData wheel3 = body.addChild("wheel3", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(0.0F, -2.0F, -2.0F, 2.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-6.0F, -1.0F, 4.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(LawnMowerRenderState state) {
		super.setAngles(state);

		this.animateWalking(ModAnimations.Other.LawnMower.MOVE, state.limbFrequency, state.limbAmplitudeMultiplier, 8f, 8f);

		this.updateVisibleParts(state);
	}

	private void updateVisibleParts(LawnMowerRenderState state) {

	}
}