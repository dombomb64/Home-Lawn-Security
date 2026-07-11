package net.db64.homelawnsecurity.entity.client.plant;

import net.db64.homelawnsecurity.entity.animation.ModAnimations;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.model.EntityModel;

public class WallNutModel extends EntityModel<WallNutRenderState> {
	private final ModelPart wallNut;
	private final ModelPart body;
	private final ModelPart shell;
	private final ModelPart inside;

	private final Animation setupAnimation;

	public WallNutModel(ModelPart root) {
		super(root);
		this.wallNut = root.getChild("wallNut");
		this.body = this.wallNut.getChild("body");
		this.shell = this.body.getChild("shell");
		this.inside = this.body.getChild("inside");

		setupAnimation = ModAnimations.Plant.WallNut.IDLE.createAnimation(root);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData wallNut = modelPartData.addChild("wallNut", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 24.0F, 0.0F));

		ModelPartData body = wallNut.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData shell = body.addChild("shell", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -18.0F, -7.0F, 14.0F, 18.0F, 14.0F, new Dilation(0.0F))
			.uv(22, 32).cuboid(-7.0F, -2.0F, -7.0F, 14.0F, 0.0F, 14.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData inside = body.addChild("inside", ModelPartBuilder.create().uv(0, 34).cuboid(-6.0F, -17.0F, -6.0F, 12.0F, 16.0F, 12.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(WallNutRenderState state) {
		super.setAngles(state);

		this.setHeadAngles(state, state.relativeHeadYaw, state.pitch);

		this.updateVisibleParts(state);

		setupAnimation.apply(state.setupAnimationState, state.age, 1f);
	}

	private void setHeadAngles(WallNutRenderState state, float headYaw, float headPitch) {
		//headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		//headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);
		this.body.yaw = headYaw * ((float) Math.PI / 180);
	}

	private void updateVisibleParts(WallNutRenderState state) {
		inside.hidden = true;
	}
}