package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

public class SunflowerModel extends EntityModel<SunflowerRenderState> {
	private final ModelPart sunflower;
	private final ModelPart stem;
	private final ModelPart head;
	private final ModelPart base;

	private final Animation setupAnimation;

	public SunflowerModel(ModelPart root) {
		super(root);
		this.sunflower = root.getChild("sunflower");
		this.stem = sunflower.getChild("stem");
		this.head = stem.getChild("head");
		this.base = sunflower.getChild("base");

		setupAnimation = ModAnimations.Plant.Sunflower.SETUP.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData sunflower = modelPartData.addChild("sunflower", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData stem = sunflower.addChild("stem", ModelPartBuilder.create().uv(17, 21).cuboid(-1.0F, -14.0F, -1.0F, 2.0F, 14.0F, 2.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData head = stem.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -6.0F, -4.0F, 10.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -11.0F, 0.0F));

		ModelPartData petals1 = head.addChild("petals1", ModelPartBuilder.create().uv(28, 0).cuboid(-7.0F, -4.0F, 0.0F, 14.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, -2.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData petals2 = head.addChild("petals2", ModelPartBuilder.create().uv(28, 4).cuboid(-7.0F, 0.0F, 0.0F, 14.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, -2.0F, -0.1745F, 0.0F, 0.0F));

		ModelPartData petals3 = head.addChild("petals3", ModelPartBuilder.create().uv(56, 0).cuboid(-4.0F, -6.0F, 0.0F, 4.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-5.0F, -2.0F, -2.0F, 0.0F, -0.1745F, 0.0F));

		ModelPartData petals4 = head.addChild("petals4", ModelPartBuilder.create().uv(56, 12).cuboid(0.0F, -6.0F, 0.0F, 4.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(5.0F, -2.0F, -2.0F, 0.0F, 0.1745F, 0.0F));

		ModelPartData base = sunflower.addChild("base", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData leaf1 = base.addChild("leaf1", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r1 = leaf1.addChild("cube_r1", ModelPartBuilder.create().uv(0, 12).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, -0.7854F, 0.0F));

		ModelPartData leaf2 = base.addChild("leaf2", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r2 = leaf2.addChild("cube_r2", ModelPartBuilder.create().uv(0, 12).mirrored().cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, 0.7854F, 0.0F));

		ModelPartData leaf3 = base.addChild("leaf3", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r3 = leaf3.addChild("cube_r3", ModelPartBuilder.create().uv(0, 12).mirrored().cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, -2.3562F, 0.0F));

		ModelPartData leaf4 = base.addChild("leaf4", ModelPartBuilder.create(), ModelTransform.origin(0.0F, -3.0F, 0.0F));

		ModelPartData cube_r4 = leaf4.addChild("cube_r4", ModelPartBuilder.create().uv(0, 12).cuboid(-3.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.48F, 2.3562F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(SunflowerRenderState state) {
		super.setAngles(state);

		this.setHeadAngles(state, state.relativeHeadYaw, state.pitch);

		this.updateVisibleParts(state);

		setupAnimation.apply(state.setupAnimationState, state.age, 1f);
	}

	private void setHeadAngles(SunflowerRenderState state, float headYaw, float headPitch) {
		this.stem.yaw = headYaw * ((float) Math.PI / 180);
		this.head.pitch = headPitch * ((float) Math.PI / 180);

		this.base.yaw = -state.bodyYaw * ((float) Math.PI / 180);
	}

	private void updateVisibleParts(SunflowerRenderState state) {

	}
}
