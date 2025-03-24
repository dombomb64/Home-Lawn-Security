package net.db64.homelawnsecurity.entity.client.zombie;

import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModel;

public class ZombieGravestoneModel extends EntityModel<ZombieGravestoneRenderState> {
	private static final float DEGRADE1_HEALTH = 240 * IPvzEntity.HEALTH_SCALE;
	private static final float DEGRADE2_HEALTH = 200 * IPvzEntity.HEALTH_SCALE;
	private static final float DEGRADE3_HEALTH = 160 * IPvzEntity.HEALTH_SCALE;
	private static final float DEGRADE4_HEALTH = 100 * IPvzEntity.HEALTH_SCALE;

	private final ModelPart zombie_gravestone;
	private final ModelPart dirt;
	private final ModelPart grave;
	private final ModelPart degrade5;
	private final ModelPart degrade4;
	private final ModelPart degrade3;
	private final ModelPart degrade2;
	private final ModelPart degrade1;

	public ZombieGravestoneModel(ModelPart root) {
		super(root);
		this.zombie_gravestone = root.getChild("zombie_gravestone");
		this.dirt = this.zombie_gravestone.getChild("dirt");
		this.grave = this.zombie_gravestone.getChild("grave");
		this.degrade5 = this.grave.getChild("degrade5");
		this.degrade4 = this.grave.getChild("degrade4");
		this.degrade3 = this.grave.getChild("degrade3");
		this.degrade2 = this.grave.getChild("degrade2");
		this.degrade1 = this.grave.getChild("degrade1");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData zombie_gravestone = modelPartData.addChild("zombie_gravestone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData dirt = zombie_gravestone.addChild("dirt", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -1.0F, -5.0F, 14.0F, 1.0F, 10.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData grave = zombie_gravestone.addChild("grave", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData degrade5 = grave.addChild("degrade5", ModelPartBuilder.create().uv(0, 11).cuboid(-6.0F, -3.0F, -4.0F, 12.0F, 3.0F, 8.0F, new Dilation(0.0F))
		.uv(32, 11).cuboid(-5.0F, -5.0F, -3.0F, 10.0F, 2.0F, 6.0F, new Dilation(0.0F))
		.uv(38, 5).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData degrade4 = grave.addChild("degrade4", ModelPartBuilder.create().uv(0, 23).cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 6.0F, 4.0F, new Dilation(0.0F))
		.uv(24, 23).cuboid(-3.0F, -16.0F, -2.0F, 6.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData degrade3 = grave.addChild("degrade3", ModelPartBuilder.create().uv(45, 23).cuboid(-6.0F, -14.0F, -2.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData degrade2 = grave.addChild("degrade2", ModelPartBuilder.create().uv(0, 34).cuboid(-6.0F, -15.0F, -2.0F, 3.0F, 1.0F, 4.0F, new Dilation(0.0F))
		.uv(14, 34).cuboid(-5.0F, -16.0F, -2.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData degrade1 = grave.addChild("degrade1", ModelPartBuilder.create().uv(27, 34).cuboid(3.0F, -15.0F, -2.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F))
		.uv(41, 34).cuboid(3.0F, -16.0F, -2.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(ZombieGravestoneRenderState state) {
		super.setAngles(state);

		this.updateVisibleParts(state);
	}

	private void updateVisibleParts(ZombieGravestoneRenderState state) {
		degrade1.hidden = state.health <= DEGRADE1_HEALTH;
		degrade2.hidden = state.health <= DEGRADE2_HEALTH;
		degrade3.hidden = state.health <= DEGRADE3_HEALTH;
		degrade4.hidden = state.health <= DEGRADE4_HEALTH;
	}
}