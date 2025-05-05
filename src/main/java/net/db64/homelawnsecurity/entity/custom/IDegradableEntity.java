package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtByte;
import net.minecraft.nbt.NbtByteArray;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public interface IDegradableEntity {
	//ArrayList<DegradationStage> degradationStages = new ArrayList<>();
	ArrayList<DegradationStage> getDegradationStageList();

	@Nullable
	default DegradationStage getDegradationStage(String name) {
		for (DegradationStage stage : getDegradationStageList()) {
			if (stage.getName().equals(name)) {
				return stage;
			}
		}
		return null;
	}

	default int getLastDegradationStage() {
		return getDegradationStageList().size() - 1;
	}

	default boolean hasTriggeredDegradationStage(String name) {
		DegradationStage stage = getDegradationStage(name);

		if (stage != null)
			return stage.hasTriggered;

		return false;
	}

	default void tickDegradation(LivingEntity entity) {
		for (DegradationStage stage : getDegradationStageList()) {
			if (entity.getHealth() < stage.getHealth()) {
				if (!stage.hasTriggered) {
					stage.hasTriggered = true;

					if (stage.getSound() != null) {
						entity.getWorld().playSound(
							null,
							entity.getBlockPos(),
							stage.getSound(),
							entity.getSoundCategory(),
							1f,
							1.0f / (entity.getRandom().nextFloat() * 0.4f + 0.8f)
						);
					}
				}

				if (!entity.getWorld().isClient() && stage.getShouldDrainHealth()) {
					entity.damage((ServerWorld) entity.getWorld(), entity.getDamageSources().create(ModDamageTypes.ZOMBIE_HEADLESS), 0.25f);
				}
			}
		}
	}

	default void writeDegradationNbt(NbtCompound nbt) {
		ArrayList<DegradationStage> stages = getDegradationStageList();
		NbtByteArray degradationNbt = new NbtByteArray(new byte[0]);

		for (int i = 0; i < stages.size(); i++) {
			degradationNbt.addElement(i, NbtByte.of(stages.get(i).hasTriggered));
		}

		nbt.put("degradation_stages", degradationNbt);
	}

	default void readDegradationNbt(NbtCompound nbt) {
		ArrayList<DegradationStage> stages = getDegradationStageList();
		byte[] degradationNbt = nbt.getByteArray("degradation_stages").orElse(new byte[0]);

		for (int i = 0; i < degradationNbt.length; i++) {
			DegradationStage stage = stages.get(i);
			if (stage != null) {
				stage.hasTriggered = degradationNbt[i] == NbtByte.ONE.byteValue();
			}
		}
	}

	class DegradationStage {
		private final String name;
		private final float health;
		private final boolean shouldDrainHealth;
		@Nullable
		private final SoundEvent sound;

		public boolean hasTriggered = false;

		public DegradationStage(String name, float health, boolean shouldDrainHealth, @Nullable SoundEvent sound) {
			this.name = name;
			this.health = health;
			this.shouldDrainHealth = shouldDrainHealth;
			this.sound = sound;
		}

		public DegradationStage(String name, float health, boolean shouldDrainHealth) {
			this(name, health, shouldDrainHealth, null);
		}

		public DegradationStage(String name, float health) {
			this(name, health, false);
		}

		public String getName() {
			return name;
		}

		public float getHealth() {
			return health;
		}

		public boolean getShouldDrainHealth() {
			return shouldDrainHealth;
		}

		@Nullable
		public SoundEvent getSound() {
			return sound;
		}
	}
}
