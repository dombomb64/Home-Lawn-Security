package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.math.MathHelper;

public class BucketheadZombieModel extends EntityModel<BucketheadZombieRenderState> {
	private final ModelPart bucketheadZombie;
	private final ModelPart head;
	private final ModelPart headwear;
	private final ModelPart leftForearm;

	private final Animation moveAnimation;
	private final Animation setupAnimation;
	private final Animation attackAnimation;
	private final Animation bucketSetupAnimation;

	public BucketheadZombieModel(ModelPart root) {
		super(root);
		this.bucketheadZombie = root.getChild("bucketheadZombie");
		this.head = this.bucketheadZombie.getChild("body").getChild("head");
		this.headwear = this.head.getChild("headwear");
		this.leftForearm = this.bucketheadZombie.getChild("body").getChild("leftArm").getChild("leftForearm");

		moveAnimation = ModAnimations.Zombie.BasicZombie.WALK.createAnimation(root);
		setupAnimation = ModAnimations.Zombie.BasicZombie.SETUP.createAnimation(root);
		attackAnimation = ModAnimations.Zombie.BasicZombie.EAT.createAnimation(root);
		bucketSetupAnimation = ModAnimations.Zombie.BucketheadZombie.SETUP_BUCKET.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bucketheadZombie = modelPartData.addChild("bucketheadZombie", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData body = bucketheadZombie.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -12.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -12.0F, 0.0F));

		ModelPartData headwear = head.addChild("headwear", ModelPartBuilder.create().uv(28, 44).cuboid(-5.25F, -13.75F, -5.0F, 9.0F, 8.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0873F, -0.0436F, 0.1309F));

		ModelPartData bucketRim = headwear.addChild("bucketRim", ModelPartBuilder.create().uv(1, 53).cuboid(-5.25F, -6.75F, -5.0F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData bucketHandle = headwear.addChild("bucketHandle", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = bucketHandle.addChild("cube_r1", ModelPartBuilder.create().uv(1, 63).cuboid(-5.25F, -0.75F, -9.0F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 0.0F, 1.0036F, 0.0F, 0.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(36, 9).cuboid(-2.0F, 6.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(32, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, -10.0F, 0.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(16, 32).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(5.0F, -10.0F, 0.0F));

		ModelPartData leftForearm = leftArm.addChild("leftForearm", ModelPartBuilder.create().uv(13, 44).cuboid(4.0F, -16.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 41).cuboid(4.0F, -18.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-5.0F, 22.0F, 0.0F));

		ModelPartData rightLeg = bucketheadZombie.addChild("rightLeg", ModelPartBuilder.create().uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(-2.0F, -12.0F, 0.0F));

		ModelPartData leftLeg = bucketheadZombie.addChild("leftLeg", ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
		.uv(40, 20).cuboid(-1.5F, 5.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(2.0F, -12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 128);
	}

	@Override
	public void setAngles(BucketheadZombieRenderState state) {
		this.resetTransforms();

		this.setHeadAngles(state.relativeHeadYaw, state.pitch);
		moveAnimation.applyWalking(state.limbSwingAnimationProgress, state.limbSwingAmplitude, 8f, 8f);

		setupAnimation.apply(state.setupAnimationState, state.age, 1f);
		bucketSetupAnimation.apply(state.bucketSetupAnimationState, state.age, 1f);
		attackAnimation.apply(state.attackAnimationState, state.age, 1f);

		this.updateVisibleParts(state);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
	}

	private void updateVisibleParts(BucketheadZombieRenderState state) {
		headwear.visible = !state.getHasLostHeadwear();
		leftForearm.hidden = state.getHasLostArm();
		head.hidden = state.getHasLostHead();
	}
}