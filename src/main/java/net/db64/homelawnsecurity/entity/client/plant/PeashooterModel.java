package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class PeashooterModel extends EntityModel<PeashooterRenderState> {
	private final ModelPart peashooter;
	private final ModelPart stem;
	private final ModelPart head;
	private final ModelPart base;

	public PeashooterModel(ModelPart root) {
		super(root);
		this.peashooter = root.getChild("peashooter");
		this.stem = peashooter.getChild("stem");
		this.head = stem.getChild("head");
		this.base = peashooter.getChild("base");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData peashooter = modelPartData.addChild("peashooter", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData stem = peashooter.addChild("stem", ModelPartBuilder.create().uv(16, 28).cuboid(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = stem.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 10.0F, new Dilation(0.0F))
			.uv(26, 0).cuboid(-3.0F, -7.0F, -8.0F, 6.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, 0.0F));

		ModelPartData mouth = head.addChild("mouth", ModelPartBuilder.create().uv(24, 18).cuboid(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, -8.0F));

		ModelPartData headLeaf = head.addChild("headLeaf", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -6.0F, 4.0F));

		ModelPartData cube_r1 = headLeaf.addChild("cube_r1", ModelPartBuilder.create().uv(0, 28).cuboid(-2.0F, 0.0F, 1.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData base = peashooter.addChild("base", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData leaf1 = base.addChild("leaf1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r2 = leaf1.addChild("cube_r2", ModelPartBuilder.create().uv(0, 18).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, -0.7854F, 0.0F));

		ModelPartData leaf2 = base.addChild("leaf2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r3 = leaf2.addChild("cube_r3", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, 0.7854F, 0.0F));

		ModelPartData leaf3 = base.addChild("leaf3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r4 = leaf3.addChild("cube_r4", ModelPartBuilder.create().uv(0, 18).mirrored().cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, -2.3562F, 0.0F));

		ModelPartData leaf4 = base.addChild("leaf4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r5 = leaf4.addChild("cube_r5", ModelPartBuilder.create().uv(0, 18).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, 2.3562F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(PeashooterRenderState state) {
		super.setAngles(state);

		this.setHeadAngles(state, state.yawDegrees, state.pitch);

		this.updateVisibleParts(state);

		this.animate(state.setupAnimationState, ModAnimations.Plant.Peashooter.SETUP, state.age, 1f);
		this.animate(state.attackAnimationState, ModAnimations.Plant.Peashooter.SHOOT, state.age, 1f);
	}

	private void setHeadAngles(PeashooterRenderState state, float headYaw, float headPitch) {
		//headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		//headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.stem.yaw = headYaw * ((float) Math.PI / 180);
		//if (entity.attackHeadRotTimeout <= 0) {
			this.head.pitch = headPitch * ((float) Math.PI / 180);
		//}

		this.base.yaw = -state.bodyYaw * ((float) Math.PI / 180);
	}

	private void updateVisibleParts(PeashooterRenderState state) {

	}
}