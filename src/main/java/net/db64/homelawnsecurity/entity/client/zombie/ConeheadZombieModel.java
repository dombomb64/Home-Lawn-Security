package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.db64.homelawnsecurity.entity.custom.zombie.ConeheadZombieEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ConeheadZombieModel<T extends ConeheadZombieEntity> extends SinglePartEntityModel<T> {
	private final ModelPart coneheadZombie;
	private final ModelPart head;
	private final ModelPart leftForearm;
	private final ModelPart headwear;

	public ConeheadZombieModel(ModelPart root) {
		this.coneheadZombie = root.getChild("coneheadZombie");
		this.head = coneheadZombie.getChild("body").getChild("head");
		this.headwear = head.getChild("headwear");
		this.leftForearm = coneheadZombie.getChild("body").getChild("leftArm").getChild("leftForearm");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData coneheadZombie = modelPartData.addChild("coneheadZombie", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = coneheadZombie.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData headwear = head.addChild("headwear", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = headwear.addChild("cube_r1", ModelPartBuilder.create().uv(52, 48).cuboid(-2.25F, -16.75F, -2.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
			.uv(43, 39).cuboid(-3.25F, -12.75F, -3.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F))
			.uv(16, 44).cuboid(-5.25F, -8.75F, -5.0F, 9.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0436F, -0.0873F, 0.1309F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(36, 9).cuboid(-2.0F, 6.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
			.uv(32, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -10.0F, 0.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(16, 32).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -10.0F, 0.0F));

		ModelPartData leftForearm = leftArm.addChild("leftForearm", ModelPartBuilder.create().uv(13, 44).cuboid(4.0F, -16.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 41).cuboid(4.0F, -18.0F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 22.0F, 0.0F));

		ModelPartData rightLeg = coneheadZombie.addChild("rightLeg", ModelPartBuilder.create().uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -12.0F, 0.0F));

		ModelPartData leftLeg = coneheadZombie.addChild("leftLeg", ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
			.uv(40, 20).cuboid(-1.5F, 5.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		this.setHeadAngles(netHeadYaw, headPitch);
		this.animateMovement(ModAnimations.Zombie.BasicZombie.WALK, limbSwing, limbSwingAmount, 8f, 8f);

		this.updateAnimation(entity.setupAnimationState, ModAnimations.Zombie.BasicZombie.SETUP, ageInTicks, 1f);
		this.updateAnimation(entity.attackAnimationState, ModAnimations.Zombie.BasicZombie.EAT, ageInTicks, 1f);

		headwear.visible = !entity.getHasLostHeadwear();
		leftForearm.hidden = entity.getHasLostArm();
		head.hidden = entity.getHasLostHead();
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		coneheadZombie.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return coneheadZombie;
	}
}