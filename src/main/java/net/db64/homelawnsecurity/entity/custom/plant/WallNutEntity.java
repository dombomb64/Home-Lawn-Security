package net.db64.homelawnsecurity.entity.custom.plant;

import net.db64.homelawnsecurity.entity.custom.IDegradableEntity;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;

import java.util.ArrayList;

public class WallNutEntity extends PlantEntity implements IPathPlant, IDegradableEntity {
	private static final TrackedData<Boolean> USING_ATTACK =
		DataTracker.registerData(WallNutEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	private static final PlantStats STATS = new PlantStats().attackRange(3f).attackTicks(30);

	public final AnimationState idleAnimationState = new AnimationState();

	ArrayList<DegradationStage> degradationStages = new ArrayList<>();

	/*
		GENERAL
	 */

	public WallNutEntity(EntityType<? extends PlantEntity> entityType, World world) {
		super(entityType, world);

		ArrayList<DegradationStage> degradationStages = getDegradationStageList();
		degradationStages.add(new DegradationStage("degrade1", 2667 * IPvzEntity.HEALTH_SCALE, false, null));
		degradationStages.add(new DegradationStage("degrade2", 1333 * IPvzEntity.HEALTH_SCALE, false, null));

		if (getEntityWorld().isClient()) {
			idleAnimationState.startIfNotRunning(this.age);
		}
	}

	/*
		ANIMATIONS
	 */

	protected void updateAnimations() {
		super.updateAnimations();
	}

	@Override
	public TrackedData<Boolean> getUsingAttackTrackedData() {
		return USING_ATTACK;
	}

	@Override
	public ArrayList<DegradationStage> getDegradationStageList() {
		return degradationStages;
	}

	/*
		BLOCKS
	 */

	/*
		BEHAVIOR
	 */

	@Override
	public void tick() {
		super.tick();
		tickDegradation(this);

		if (!(getEntityWorld() instanceof ServerWorld)) {
			return;
		}
	}

	@Override
	public double getEyeY() {
		return this.getY() + (this.getHeight() * 0.8);
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		writeDegradationNbt(view);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		readDegradationNbt(view);
	}

	/*
		STATS
	 */

	@Override
	protected void initGoals() {
		//goalSelector.add(2, new LookAtEntityGoal(this, ZombieEntity.class, 3));
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 4000 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 20 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 3)
			.add(EntityAttributes.MOVEMENT_SPEED, 0);
	}
}
