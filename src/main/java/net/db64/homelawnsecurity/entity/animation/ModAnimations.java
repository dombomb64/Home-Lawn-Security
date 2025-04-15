package net.db64.homelawnsecurity.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class ModAnimations {
	public static class Other {
		public static class LawnMower {
			public static final Animation MOVE = Animation.Builder.create(1.0F).looping()
				.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("body", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0417F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5417F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.625F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7917F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.875F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.9583F, AnimationHelper.createScalingVector(1.025F, 0.975F, 1.025F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("exhaust", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0417F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.125F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2083F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.2917F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5417F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.625F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.7917F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.875F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.9583F, AnimationHelper.createScalingVector(0.8F, 1.2F, 1.2F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("wheel0", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(360.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("wheel1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(360.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("wheel3", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(360.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("wheel2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(360.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.build();
		}
	}
	public static class Plant {
		public static class Peashooter {
			public static final Animation SETUP = Animation.Builder.create(1.5f).looping()
				.addBoneAnimation("base",
					new Transformation(Transformation.Targets.SCALE,
						new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.75f, AnimationHelper.createScalingVector(0.95f, 1.1f, 0.95f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.5f, AnimationHelper.createScalingVector(1f, 1f, 1f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("stem",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.75f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.5f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("head",
					new Transformation(Transformation.Targets.TRANSLATE,
						new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -1f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, -1f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.5f, AnimationHelper.createTranslationalVector(0f, -1f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("head",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.375f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.75f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.125f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.5f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("headLeaf",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.08343333f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.5f, AnimationHelper.createRotationalVector(2.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.625f, AnimationHelper.createRotationalVector(7.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.8343334f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.2083433f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.4167667f, AnimationHelper.createRotationalVector(0.15f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.5f, AnimationHelper.createRotationalVector(-2.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC))).build();

			public static final Animation SHOOT = Animation.Builder.create(0.5f)
				.addBoneAnimation("head",
					new Transformation(Transformation.Targets.SCALE,
						new Keyframe(0f, AnimationHelper.createScalingVector(0.9f, 0.9f, 1.2f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.08343333f, AnimationHelper.createScalingVector(0.95f, 0.95f, 1.1f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.25f, AnimationHelper.createScalingVector(1.05f, 1.05f, 0.9f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.375f, AnimationHelper.createScalingVector(1f, 1f, 1f),
							Transformation.Interpolations.LINEAR)))
				.addBoneAnimation("mouth",
					new Transformation(Transformation.Targets.SCALE,
						new Keyframe(0f, AnimationHelper.createScalingVector(1f, 1f, 1f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.08343333f, AnimationHelper.createScalingVector(1.2f, 1.2f, 0.8f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.2916767f, AnimationHelper.createScalingVector(0.95f, 0.95f, 1.1f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.4167667f, AnimationHelper.createScalingVector(1f, 1f, 1f),
							Transformation.Interpolations.LINEAR))).build();
		}
		public static class Sunflower {
			public static final Animation SETUP = Animation.Builder.create(1.5F).looping()
				.addBoneAnimation("stem", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 5.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 5.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.125F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 5.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("petals1", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("petals2", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("petals3", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(2.5F, 0.0F, -2.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(2.5F, 0.0F, -2.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("petals3", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0.25F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.25F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("petals4", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.125F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("petals4", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.125F, AnimationHelper.createTranslationalVector(-0.25F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createTranslationalVector(-0.25F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("base", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createScalingVector(0.95F, 1.1F, 0.95F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC)
				))
				.build();
		}

		public static class WallNut {
			public static final Animation SETUP = Animation.Builder.create(1.1667F).looping()
				.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("body", new Transformation(Transformation.Targets.SCALE,
					new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.2917F, AnimationHelper.createScalingVector(1.05F, 0.95F, 1.05F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.875F, AnimationHelper.createScalingVector(1.05F, 0.95F, 1.05F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.1667F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC)
				))
				.build();
		}
	}
	public static class Zombie {
		public static class TargetZombie {
			public static final Animation SETUP = Animation.Builder.create(2.0F).looping()
				.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-25.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(25.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(30.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.9583F, AnimationHelper.createRotationalVector(25.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("rightArm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-55.0F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-65.0F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-55.0F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("leftArm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-55.0F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-65.0F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createRotationalVector(-55.0F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("hat", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("hat", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("target", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.build();

			public static final Animation WALK = Animation.Builder.create(1.0F).looping()
				.addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 5.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("rightLeg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.625F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("rightLeg", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.addBoneAnimation("leftLeg", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
				))
				.build();

			public static final Animation EAT = Animation.Builder.create(1.0F).looping()
				.addBoneAnimation("rightArm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createRotationalVector(-152.5F, 0.0F, 40.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(-101.67F, -30.0F, 60.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("rightArm", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -1.33F, -0.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("leftArm", new Transformation(Transformation.Targets.ROTATE,
					new Keyframe(0.0F, AnimationHelper.createRotationalVector(-101.67F, 30.0F, -60.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.875F, AnimationHelper.createRotationalVector(-152.5F, 0.0F, -40.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createRotationalVector(-101.67F, 30.0F, -60.0F), Transformation.Interpolations.LINEAR)
				))
				.addBoneAnimation("leftArm", new Transformation(Transformation.Targets.TRANSLATE,
					new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -1.33F, -0.67F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.0F, -2.0F, -1.0F), Transformation.Interpolations.LINEAR),
					new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -1.33F, -0.67F), Transformation.Interpolations.LINEAR)
				))
				.build();
		}
		
		public static class BasicZombie {
			public static final Animation SETUP = Animation.Builder.create(2f).looping()
				.addBoneAnimation("head",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(-20f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(2f, AnimationHelper.createRotationalVector(-20f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("body",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(30f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1.9583433f, AnimationHelper.createRotationalVector(25f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("rightArm",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(-32.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(2f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("leftArm",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(-32.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(2f, AnimationHelper.createRotationalVector(-25f, 0f, 0f),
							Transformation.Interpolations.CUBIC))).build();

			public static final Animation WALK = Animation.Builder.create(1f).looping()
				.addBoneAnimation("head",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.7916766f, AnimationHelper.createRotationalVector(-5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("body",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.75f, AnimationHelper.createRotationalVector(7.5f, 0f, 5f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("rightLeg",
					new Transformation(Transformation.Targets.TRANSLATE,
						new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 2f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("rightLeg",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.625f, AnimationHelper.createRotationalVector(-7.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(5f, 0f, 0f),
							Transformation.Interpolations.CUBIC)))
				.addBoneAnimation("leftLeg",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(0.75f, AnimationHelper.createRotationalVector(12.5f, 0f, 0f),
							Transformation.Interpolations.CUBIC),
						new Keyframe(1f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.CUBIC))).build();

			public static final Animation EAT = Animation.Builder.create(1f).looping()
				.addBoneAnimation("rightArm",
					new Transformation(Transformation.Targets.TRANSLATE,
						new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.375f, AnimationHelper.createTranslationalVector(0f, -2f, -1f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, -1.33f, -0.67f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.75f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR)))
				.addBoneAnimation("rightArm",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.375f, AnimationHelper.createRotationalVector(-152.5f, 0f, 40f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.5f, AnimationHelper.createRotationalVector(-101.67f, -30f, 60f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.75f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR)))
				.addBoneAnimation("leftArm",
					new Transformation(Transformation.Targets.TRANSLATE,
						new Keyframe(0f, AnimationHelper.createTranslationalVector(0f, -1.33f, -0.67f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.25f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.5f, AnimationHelper.createTranslationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.875f, AnimationHelper.createTranslationalVector(0f, -2f, -1f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(1f, AnimationHelper.createTranslationalVector(0f, -1.33f, -0.67f),
							Transformation.Interpolations.LINEAR)))
				.addBoneAnimation("leftArm",
					new Transformation(Transformation.Targets.ROTATE,
						new Keyframe(0f, AnimationHelper.createRotationalVector(-101.67f, 30f, -60f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.25f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.5f, AnimationHelper.createRotationalVector(0f, 0f, 0f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(0.875f, AnimationHelper.createRotationalVector(-152.5f, 0f, -40f),
							Transformation.Interpolations.LINEAR),
						new Keyframe(1f, AnimationHelper.createRotationalVector(-101.67f, 30f, -60f),
							Transformation.Interpolations.LINEAR))).build();
		}
	}
}
