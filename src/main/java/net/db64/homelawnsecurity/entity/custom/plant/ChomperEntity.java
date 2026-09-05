package net.db64.homelawnsecurity.entity.custom.plant;

import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ai.plant.ChomperAttackGoal;
import net.db64.homelawnsecurity.entity.ai.plant.PlantTargetGoal;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.networking.ChangeEntityStateS2CPayload;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.MiscUtil;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;

public class ChomperEntity extends PlantEntity implements IPathPlant {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(ChomperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private static final PlantStats STATS = new PlantStats().attackRange(1f).attackRangePath(2f).attackTicks(30);

	public final AnimationState setupAnimationState = new AnimationState();
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();
	public final AnimationState chompAnimationState = new AnimationState();
	public final AnimationState chewingAnimationState = new AnimationState();
	public final AnimationState swallowAnimationState = new AnimationState();

	private ChomperState state = ChomperState.IDLE;
	public int stateTimer = 0;

	private boolean landedAttack = false;

	/*
		GENERAL
	 */

	public ChomperEntity(EntityType<? extends PlantEntity> entityType, World world) {
		super(entityType, world);

		if (getEntityWorld().isClient()) {
			setupAnimationState.startIfNotRunning(this.age);
			idleAnimationState.startIfNotRunning(this.age);
		}
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		view.putString("chomper_state", state.toString());

		view.putInt("chomper_state_timer", stateTimer);

		view.putBoolean("landed_attack", landedAttack);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		setState(ChomperState.fromString(view.getString("chomper_state", "idle")));

		stateTimer = view.getInt("chomper_state_timer", 0);

		landedAttack = view.getBoolean("landed_attack", false);
	}

	@Override
	public void tick() {
		super.tick();

		LivingEntity target = getTarget();
		World world = getEntityWorld();
		switch (state) {
			case IDLE:
				break;

			case CHOMP:
				if (stateTimer == 0 && world instanceof ServerWorld) {
					world.playSound(this, getBlockPos(), ModSounds.ENTITY_CHOMPER_EAT, getSoundCategory(), 1f, MiscUtil.getSoundPitch(getRandom()));
				}
				else if (stateTimer == 10) {
					if (target != null && MiscUtil.isFacingEntity(this, target)) {
						// Condition's a bit janky but it's not like the zombie's gonna backflip 9 blocks
						target.onDeath(this.getDamageSources().create(ModDamageTypes.CHOMPER_SWALLOW, this));
						target.remove(RemovalReason.KILLED);
						landedAttack = true;
					}
					else {
						// YOU MISSED HE WAS THREE FEET IN FRONT OF YOU
						landedAttack = false;
					}
				}
				else if (stateTimer >= 20) {
					if (landedAttack) {
						setState(ChomperState.CHEWING);
					}
					else {
						setState(ChomperState.IDLE);
					}
					stateTimer--;
				}
				break;

			case ATTACK:
				if (stateTimer == 0 && world instanceof ServerWorld) {
					world.playSound(
						this,
						getBlockPos(),
						ModSounds.ENTITY_CHOMPER_EAT,
						getSoundCategory(),
						1f,
						MiscUtil.getSoundPitch(getRandom())
					);
				}
				else if (stateTimer >= 10) {
					if (target != null && MiscUtil.isFacingEntity(this, target)) {
						if (world instanceof ServerWorld serverWorld)
							tryAttack(serverWorld, target);
						landedAttack = true;
					}
					else {
						landedAttack = false;
					}
					setState(ChomperState.IDLE);
					stateTimer--;
				}
				break;

			case CHEWING:
				if (stateTimer >= 780) {
					setState(ChomperState.SWALLOW);
					stateTimer--;
				}
				break;

			case SWALLOW:
				if (stateTimer >= 60) {
					setState(ChomperState.IDLE);
					stateTimer--;
				}
				break;
		}
		stateTimer++;
	}

	public ChomperState getState() {
		return state;
	}

	public void setState(ChomperState state) {
		this.state = state;
		stateTimer = 0;
		if (getEntityWorld() instanceof ServerWorld) {
			for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) getEntityWorld(), getBlockPos())) {
				ServerPlayNetworking.send(player, new ChangeEntityStateS2CPayload(this.getUuid(), state.toString()));
			}
		}
	}

	/*
		ANIMATIONS
	 */

	protected void updateAnimations() {
		super.updateAnimations();

		switch (state) {
			case IDLE:
				idleAnimationState.startIfNotRunning(this.age);
				attackAnimationState.stop();
				chompAnimationState.stop();
				chewingAnimationState.stop();
				swallowAnimationState.stop();
				break;

			case ATTACK:
				attackAnimationState.startIfNotRunning(this.age);
				idleAnimationState.stop();
				chompAnimationState.stop();
				chewingAnimationState.stop();
				swallowAnimationState.stop();
				break;

			case CHOMP:
				chompAnimationState.startIfNotRunning(this.age);
				idleAnimationState.stop();
				attackAnimationState.stop();
				chewingAnimationState.stop();
				swallowAnimationState.stop();
				break;

			case CHEWING:
				chewingAnimationState.startIfNotRunning(this.age);
				idleAnimationState.stop();
				attackAnimationState.stop();
				chompAnimationState.stop();
				swallowAnimationState.stop();
				break;

			case SWALLOW:
				swallowAnimationState.startIfNotRunning(this.age);
				idleAnimationState.stop();
				attackAnimationState.stop();
				chompAnimationState.stop();
				chewingAnimationState.stop();
				break;
		}
	}

	@Override
	public TrackedData<Boolean> getUsingAttackTrackedData() {
		return USING_ATTACK;
	}

	/*
		BLOCKS
	 */

	/*
		ATTACKING
	 */

	public enum ChomperState {
		IDLE,
		CHOMP,
		ATTACK,
		CHEWING,
		SWALLOW;

		@Override
		public String toString() {
			return switch (this) {
				case IDLE -> "idle";
				case CHOMP -> "chomp";
				case ATTACK -> "attack";
				case CHEWING -> "chewing";
				case SWALLOW -> "swallow";
			};
		}

		public static ChomperState fromString(String value) {
			return switch (value) {
				case "chomp" -> CHOMP;
				case "attack" -> ATTACK;
				case "chewing" -> CHEWING;
				case "swallow" -> SWALLOW;
				default -> IDLE;
			};
		}
	}

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		this.goalSelector.add(2, new PlantTargetGoal<>(this, ZombieEntity.class, false, STATS.attackRange, STATS.attackRangePath));
		this.goalSelector.add(2, new ChomperAttackGoal(this, 1, false));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 300 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 40 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, STATS.getLargerAttackRange())
			.add(EntityAttributes.MOVEMENT_SPEED, 0);
	}
}
