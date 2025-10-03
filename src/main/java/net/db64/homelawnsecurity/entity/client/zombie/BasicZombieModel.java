package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

public class BasicZombieModel extends EntityModel<BasicZombieRenderState> {
	private final ModelPart basicZombie;
	private final ModelPart head;
	private final ModelPart leftForearm;

	private final Animation moveAnimation;
	private final Animation setupAnimation;
	private final Animation attackAnimation;

	public BasicZombieModel(ModelPart root) {
		super(root);
		this.basicZombie = root.getChild("basicZombie");
		this.head = basicZombie.getChild("body").getChild("head");
		this.leftForearm = basicZombie.getChild("body").getChild("leftArm").getChild("leftForearm");

		moveAnimation = ModAnimations.Zombie.BasicZombie.WALK.createAnimation(root);
		setupAnimation = ModAnimations.Zombie.BasicZombie.SETUP.createAnimation(root);
		attackAnimation = ModAnimations.Zombie.BasicZombie.EAT.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData basicZombie = modelPartData.addChild("basicZombie", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData body = basicZombie.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -12.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -12.0F, 0.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(36, 9).cuboid(-2.0F, 6.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
			.uv(32, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, -10.0F, 0.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(16, 32).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(5.0F, -10.0F, 0.0F));

		ModelPartData leftForearm = leftArm.addChild("leftForearm", ModelPartBuilder.create().uv(13, 44).cuboid(4.0F, -16.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 41).cuboid(4.0F, -18.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 22.0F, 0.0F));

		ModelPartData rightLeg = basicZombie.addChild("rightLeg", ModelPartBuilder.create().uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-2.0F, -12.0F, 0.0F));

		ModelPartData leftLeg = basicZombie.addChild("leftLeg", ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
			.uv(40, 20).cuboid(-1.5F, 5.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(2.0F, -12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(BasicZombieRenderState state) {
		this.resetTransforms();

		this.setHeadAngles(state.relativeHeadYaw, state.pitch);
		moveAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 8f, 8f);

		setupAnimation.apply(state.setupAnimationState, state.age, 1f);
		attackAnimation.apply(state.attackAnimationState, state.age, 1f);

		this.updateVisibleParts(state);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
	}

	private void updateVisibleParts(BasicZombieRenderState state) {
		leftForearm.hidden = state.getHasLostArm();
		head.hidden = state.getHasLostHead();
	}
}