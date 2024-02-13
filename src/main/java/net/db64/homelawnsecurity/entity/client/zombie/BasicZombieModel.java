package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.db64.homelawnsecurity.entity.custom.zombie.BasicZombieEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.joml.Vector3f;

public class BasicZombieModel<T extends BasicZombieEntity> extends SinglePartEntityModel<T> {
	private final ModelPart basicZombie;
	private final ModelPart head;

	public BasicZombieModel(ModelPart root) {
		this.basicZombie = root.getChild("basicZombie");
		this.head = basicZombie.getChild("body").getChild("head");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData basicZombie = modelPartData.addChild("basicZombie", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData body = basicZombie.addChild("body", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.0F, 0.0F));

		ModelPartData rightArm = body.addChild("rightArm", ModelPartBuilder.create().uv(36, 9).cuboid(-2.0F, 6.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
			.uv(32, 32).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -10.0F, 0.0F));

		ModelPartData leftArm = body.addChild("leftArm", ModelPartBuilder.create().uv(13, 44).cuboid(-1.0F, 6.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
			.uv(16, 32).cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -10.0F, 0.0F));

		ModelPartData rightLeg = basicZombie.addChild("rightLeg", ModelPartBuilder.create().uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -12.0F, 0.0F));

		ModelPartData leftLeg = basicZombie.addChild("leftLeg", ModelPartBuilder.create().uv(32, 0).cuboid(-2.0F, 7.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F))
			.uv(40, 20).cuboid(-1.5F, 5.0F, -1.5F, 3.0F, 2.0F, 3.0F, new Dilation(0.0F))
			.uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.Zombie.BasicZombie.WALK, limbSwing * 2, limbSwingAmount * 2, 2f, 2f);
		this.updateAnimation(entity.setupAnimationState, ModAnimations.Zombie.BasicZombie.SETUP, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * ((float)Math.PI / 180);
		this.head.pitch = headPitch * ((float)Math.PI / 180);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		basicZombie.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return basicZombie;
	}
}