package net.db64.homelawnsecurity.entity.client.projectile;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class PeaModel extends EntityModel<PeaRenderState> {
	private final ModelPart pea;

	public PeaModel(ModelPart root) {
		super(root);
		this.pea = root.getChild("pea");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData pea = modelPartData.addChild("pea", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 16, 16);
	}

	@Override
	public void setAngles(PeaRenderState state) {
		super.resetTransforms();
		this.updateVisibleParts(state);
	}

	private void updateVisibleParts(PeaRenderState state) {

	}
}