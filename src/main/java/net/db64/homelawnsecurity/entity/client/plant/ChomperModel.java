package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

public class ChomperModel extends EntityModel<ChomperRenderState> {
	private final ModelPart chomper;
	private final ModelPart stem1;
	private final ModelPart head;
	private final ModelPart base;

	private final Animation setupAnimation;
	private final Animation idleAnimation;
	private final Animation attackAnimation;
	private final Animation chompAnimation;
	private final Animation chewingAnimation;
	private final Animation swallowAnimation;

	public ChomperModel(ModelPart root) {
		super(root);
		this.chomper = root.getChild("chomper");
		this.stem1 = this.chomper.getChild("stem1");
		this.head = this.stem1.getChild("stem2").getChild("head");
		this.base = this.chomper.getChild("base");

		setupAnimation = ModAnimations.Plant.Chomper.SETUP.createAnimation(root);
		idleAnimation = ModAnimations.Plant.Chomper.IDLE.createAnimation(root);
		attackAnimation = ModAnimations.Plant.Chomper.ATTACK.createAnimation(root);
		chompAnimation = ModAnimations.Plant.Chomper.CHOMP.createAnimation(root);
		chewingAnimation = ModAnimations.Plant.Chomper.CHEWING.createAnimation(root);
		swallowAnimation = ModAnimations.Plant.Chomper.SWALLOW.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData chomper = modelPartData.addChild("chomper", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData stem1 = chomper.addChild("stem1", ModelPartBuilder.create().uv(94, 0).cuboid(-1.0F, -10.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData stem2 = stem1.addChild("stem2", ModelPartBuilder.create(), ModelTransform.of(0.0F, -10.0F, 1.0F, 1.3963F, 0.0F, 0.0F));

		ModelPartData stemScale = stem2.addChild("stemScale", ModelPartBuilder.create().uv(86, 0).cuboid(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, -1.0F));

		ModelPartData head = stem2.addChild("head", ModelPartBuilder.create(), ModelTransform.of(0.0F, -7.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		ModelPartData headLeaves = head.addChild("headLeaves", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 1.0F, 0.0F));

		ModelPartData cube_r1 = headLeaves.addChild("cube_r1", ModelPartBuilder.create().uv(28, 60).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 0.0F, 0.6109F, 0.0F, 0.0F));

		ModelPartData cube_r2 = headLeaves.addChild("cube_r2", ModelPartBuilder.create().uv(28, 56).cuboid(-3.0F, -3.0F, 0.0F, 6.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

		ModelPartData cube_r3 = headLeaves.addChild("cube_r3", ModelPartBuilder.create().uv(42, 56).mirrored().cuboid(-3.0F, -3.0F, 0.0F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-1.0F, 0.0F, 0.0F, 0.0F, 0.6109F, 0.0F));

		ModelPartData cube_r4 = headLeaves.addChild("cube_r4", ModelPartBuilder.create().uv(42, 56).cuboid(0.0F, -3.0F, 0.0F, 3.0F, 6.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 0.0F, 0.0F, -0.6109F, 0.0F));

		ModelPartData upperJaw = head.addChild("upperJaw", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -5.0F, -11.0F, 12.0F, 5.0F, 12.0F, new Dilation(0.0F))
			.uv(48, 11).cuboid(-1.0F, -8.0F, -10.0F, 1.0F, 5.0F, 13.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 1.0F, -1.0F));

		ModelPartData upperLip = upperJaw.addChild("upperLip", ModelPartBuilder.create().uv(0, 17).cuboid(-6.0F, 0.5F, -6.0F, 12.0F, 2.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -2.5F, -5.0F));

		ModelPartData upperTeeth = upperJaw.addChild("upperTeeth", ModelPartBuilder.create().uv(42, 44).cuboid(-5.0F, 0.0F, -10.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData lowerJaw = head.addChild("lowerJaw", ModelPartBuilder.create().uv(0, 31).cuboid(-5.0F, 0.0F, -10.0F, 10.0F, 7.0F, 11.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 1.0F, -1.0F));

		ModelPartData lowerLip = lowerJaw.addChild("lowerLip", ModelPartBuilder.create().uv(42, 31).cuboid(-5.0F, -3.5F, -5.5F, 10.0F, 2.0F, 11.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 3.5F, -4.5F));

		ModelPartData lowerTeeth = lowerJaw.addChild("lowerTeeth", ModelPartBuilder.create().uv(48, 0).cuboid(-4.0F, -2.0F, -9.0F, 8.0F, 2.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData tongue = head.addChild("tongue", ModelPartBuilder.create().uv(102, 0).cuboid(-1.0F, -1.0F, -8.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 1.0F, -1.0F));

		ModelPartData base = chomper.addChild("base", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData leaf1 = base.addChild("leaf1", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r5 = leaf1.addChild("cube_r5", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -2.5F, 1.0F, 6.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, -0.3491F, -0.7854F, 0.0F));

		ModelPartData leaf2 = base.addChild("leaf2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r6 = leaf2.addChild("cube_r6", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -2.5F, 1.0F, 6.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, -0.3491F, 0.7854F, 0.0F));

		ModelPartData leaf3 = base.addChild("leaf3", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r7 = leaf3.addChild("cube_r7", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -2.5F, 1.0F, 6.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, -0.3491F, -2.3562F, 0.0F));

		ModelPartData leaf4 = base.addChild("leaf4", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r8 = leaf4.addChild("cube_r8", ModelPartBuilder.create().uv(0, 49).cuboid(-3.0F, -2.5F, 1.0F, 6.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, -0.3491F, 2.3562F, 0.0F));

		ModelPartData swallow = chomper.addChild("swallow", ModelPartBuilder.create().uv(102, 10).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -2.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public void setAngles(ChomperRenderState state) {
		super.setAngles(state);

		this.setHeadAngles(state, state.relativeHeadYaw, state.pitch);

		this.updateVisibleParts(state);

		setupAnimation.apply(state.setupAnimationState, state.age, 1f);
		idleAnimation.apply(state.idleAnimationState, state.age, 1f);
		attackAnimation.apply(state.attackAnimationState, state.age, 1f);
		chompAnimation.apply(state.chompAnimationState, state.age, 1f);
		chewingAnimation.apply(state.chewingAnimationState, state.age, 1f);
		swallowAnimation.apply(state.swallowAnimationState, state.age, 1f);
	}

	private void setHeadAngles(ChomperRenderState state, float headYaw, float headPitch) {
		this.stem1.yaw += headYaw * ((float) Math.PI / 180);
		this.head.pitch += headPitch * ((float) Math.PI / 180);

		this.base.yaw = -state.bodyYaw * ((float) Math.PI / 180);
	}

	private void updateVisibleParts(ChomperRenderState state) {

	}
}
