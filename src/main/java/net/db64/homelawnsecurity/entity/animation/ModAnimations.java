package net.db64.homelawnsecurity.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class ModAnimations {
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
	}
	public static class Zombie {
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
