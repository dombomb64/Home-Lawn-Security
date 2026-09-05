package net.db64.homelawnsecurity.entity.custom.other;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.debug.ModDebugSubscriptionTypes;
import net.db64.homelawnsecurity.entity.ai.other.LawnMowerMoveGoal;
import net.db64.homelawnsecurity.entity.ai.other.LawnMowerStayOnPathGoal;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.PathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ZombieGravestoneEntity;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.networking.PlayerDestroyedMowerS2CPayload;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.db64.homelawnsecurity.util.MiscUtil;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.debug.data.EntityPathDebugData;
import org.jetbrains.annotations.Nullable;

public class LawnMowerEntity extends PathBoundEntity implements IPvzEntity {
	//public int pathId = 1;

	/*@Deprecated
	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
	@Deprecated
	public TagKey<Block> pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;*/

	public static final int MOW_MIN_TIME = 50;
	public static final int LEAVE_TIME = 10;
	public boolean mowing;
	public boolean leaving;
	public int mowTicks;
	public int leaveTicks;
	public boolean canLeave;
	public boolean pathFinished;
	//private boolean velocityStored;
	//public Vec3d leaveVelocity;
	private int wallCrashTicks;

	public boolean shouldDropSpawnItem = false;
	public ItemStack customSpawnItem = getDefaultSpawnItem();

	/*
		GENERAL
	 */

	public LawnMowerEntity(EntityType<LawnMowerEntity> entityType, World world) {
		super(entityType, world);

		/*if (!getEntityWorld().isClient) {
			setPathTagNbt(pathTag);
		}*/
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

		//HomeLawnSecurity.LOGGER.info("new lawn mower");
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(getSteppingPos(), 5, 5, 5);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("block pos {} is:", blockPos.toShortString());
			if (LawnUtil.isAnyPath(blockPos, getEntityWorld())) {
				for (int i = 1; i <= LawnUtil.getPathTypeAmount(); i++) {
					if (LawnUtil.isCertainPath(blockPos, getEntityWorld(), i)) {
						//HomeLawnSecurity.LOGGER.info("path {}", i);
						pathId = i;
					}
				}
				break;
			}
			//HomeLawnSecurity.LOGGER.info("not a path");
		}

		mowing = false;
		mowTicks = -1;
		leaving = false;
		leaveTicks = -1;
		canLeave = false;
		pathFinished = false;
		//velocityStored = false;
		//leaveVelocity = new Vec3d(0d, 0d, 0d);
		wallCrashTicks = -1;

		return result;
	}

	@Override
	public void tick() {
		super.tick();

		World world = getEntityWorld();
		if (world instanceof ServerWorld serverWorld) {
			boolean wasMowing = mowing;

			for (ZombieEntity entity : world
				.getEntitiesByClass(ZombieEntity.class, this.getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR)) {
				if (!(entity instanceof TargetZombieEntity) && !(entity instanceof ZombieGravestoneEntity) && entity.isInPlay()) {
					mowing = true;
					entity.kill(serverWorld);
				}
			}

			if (mowing && !wasMowing) {
				world.playSoundFromEntity(
					null,
					this,
					ModSounds.ENTITY_LAWN_MOWER_ACTIVATE,
					getSoundCategory(),
					1f,
					MiscUtil.getSoundPitch(getRandom())
				);
				mowTicks = 0;
			}
			else {
				if (leaving) {
					if (leaveTicks >= LEAVE_TIME) {
						disappear();
					}
					else {
						leaveTicks++;
						keepMoving();
					}
				}
				else if (mowing) {
					if (mowTicks >= MOW_MIN_TIME) {
						canLeave = true;
					}
					else if (mowTicks >= 0) {
						mowTicks++;
					}
					if (pathFinished) {
						/*Vec3d motion = getVelocity();
						if (!velocityStored) {
							leaveVelocity = new Vec3d(motion.x, motion.y, motion.z);
							velocityStored = true;
						}
						setVelocity(leaveVelocity.x, motion.y, leaveVelocity.z);*/
						keepMoving();

						if (canLeave) {
							leaving = true;
							leaveTicks = 0;
						}
					}
				}
			}
		}
	}

	private void keepMoving() {
		World world = getEntityWorld();
		if (!(world instanceof ServerWorld)) return;
		ServerWorld serverWorld = (ServerWorld) getEntityWorld();

		//setVelocity(leaveVelocity.x, getVelocity().y, leaveVelocity.z);
		Vec3d movementInput = new Vec3d(0, 0, getAttributeValue(EntityAttributes.MOVEMENT_SPEED));
		travel(movementInput);

		if (wallCrashTicks > 0) {
			wallCrashTicks--;
			if (wallCrashTicks <= 0) {
				kill(serverWorld);
			}
			return;
		}

		Vec3d velocity = movementInputToVelocity(movementInput, 0.1f, getYaw());
		Box box = getBoundingBox();
		Vec3d adjusted = adjustMovementForCollisions(
			this, velocity, box, serverWorld,
			serverWorld.getEntityCollisions(this, box.stretch(velocity))
		);
		if (!MathHelper.approximatelyEquals(adjusted.x, velocity.x)
			|| !MathHelper.approximatelyEquals(adjusted.z, velocity.z)) {
			wallCrashTicks = 4;
		}
	}

	public void disappear() {
		World world = getEntityWorld();
		if (!(world instanceof ServerWorld serverWorld)) return;

		serverWorld.spawnParticles(ParticleTypes.POOF,
			getBodyX(0.0), getBodyY(0.5), getBodyZ(0.0), 20,
			getWidth() * 0.5, getHeight() * 0.5, getWidth() * 0.5, 0.02);

		dropSpawnItemOnDeath(null);
		this.discard();
	}

	public void destroy() {
		World world = getEntityWorld();
		if (!(world instanceof ServerWorld serverWorld)) return;

		serverWorld.spawnParticles(
			new ItemStackParticleEffect(ParticleTypes.ITEM, ModItems.LAWN_MOWER.getDefaultStack()),
			getBodyX(0.0), getBodyY(0.5), getBodyZ(0.0), 20,
			getWidth() * 0.5, getHeight() * 0.5, getWidth() * 0.5, 0.05);
		serverWorld.playSound(
			null,
			this.getBlockPos(),
			ModSounds.ENTITY_LAWN_MOWER_DESTROY,
			getSoundCategory(),
			1f,
			MiscUtil.getSoundPitch(getRandom())
		);

		this.discard();
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);
		destroy();
	}

	@Override
	protected void drop(ServerWorld world, DamageSource damageSource) {
		super.drop(world, damageSource);

		dropSpawnItemOnDeath(damageSource);
	}

	protected void dropSpawnItemOnDeath() {
		if (shouldDropSpawnItem) {
			ItemEntity itemEntity = dropStack((ServerWorld) getEntityWorld(), getSpawnItem());
			if (itemEntity != null) {
				itemEntity.setVelocity(random.nextFloat() * 0.1f - 0.05f, 0.1f, random.nextFloat() * 0.1f - 0.05f);
			}
		}
	}

	public ItemStack getSpawnItem() {
		if (customSpawnItem != null) {
			return customSpawnItem;
		}
		return getDefaultSpawnItem();
	}

	protected ItemStack getDefaultSpawnItem() {
		return ModItems.LAWN_MOWER.getDefaultStack().copy();
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		/*if (pathTag == ModTags.Blocks.ZOMBIE_PATH_2)
			view.putInt("path_tag", 2);
		else
			view.putInt("path_tag", 1);*/

		/*if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
		}
		view.putInt("path_id", pathId);*/

		view.putBoolean("should_drop_spawn_item", shouldDropSpawnItem);

		view.putBoolean("force_no_drops", forceNoDrops);

		view.put("spawn_item", ItemStack.CODEC, customSpawnItem);

		view.putBoolean("mowing", mowing);

		view.putBoolean("leaving", leaving);

		view.putInt("mow_ticks", mowTicks);

		view.putInt("leave_ticks", leaveTicks);

		view.putBoolean("can_leave", canLeave);

		view.putBoolean("path_finished", pathFinished);

		//view.putBoolean("velocity_stored", velocityStored);

		//view.put("leave_velocity", Vec3d.CODEC, leaveVelocity);

		view.putInt("wall_crash_ticks", wallCrashTicks);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		/*// path_tag
		int path = view.getInt("path_tag", 1);
		if (path == 2) {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_2;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		} else {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}*/

		/*pathId = view.getInt("path_id", 1);
		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
			//view.putInt("path_id", pathId);
		}*/

		shouldDropSpawnItem = view.getBoolean("should_drop_spawn_item", false);

		customSpawnItem = view.read("spawn_item", ItemStack.CODEC).orElse(getDefaultSpawnItem());

		// mowing
		this.mowing = view.getBoolean("mowing", false);

		// leaving
		this.leaving = view.getBoolean("leaving", false);

		// mow_ticks
		this.mowTicks = view.getInt("mow_ticks", -1);

		// leave_ticks
		this.leaveTicks = view.getInt("leave_ticks", -1);

		// can_leave
		this.canLeave = view.getBoolean("can_leave", false);

		// path_finished
		this.pathFinished = view.getBoolean("path_finished", false);

		// velocity_stored
		//this.velocityStored = view.getBoolean("velocity_stored", false);

		// path_finished
		//this.leaveVelocity = view.read("leave_velocity", Vec3d.CODEC).orElse(new Vec3d(0d, 0d, 0d));

		// wall_crash_ticks
		this.wallCrashTicks = view.getInt("wall_crash_ticks", -1);
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		// oh no--i hope this really heavy beach ball doesn't break my leg

		if (getAttacker() != null && getAttacker().getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			super.takeKnockback(strength * 3, x, z);
		}
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		Entity attacker = source.getAttacker();
		boolean damaged;

		if (attacker instanceof ServerPlayerEntity player && player.isCreative()) {
			damaged = super.damage(world, source, 1000000);

			this.hurtTime = 0;
			this.timeUntilRegen = 0;

			if (source.isOf(DamageTypes.PLAYER_ATTACK)) {
				ServerPlayNetworking.send(
					player,
					new PlayerDestroyedMowerS2CPayload(getUuid())
				);
			}
		}
		else {
			damaged = super.damage(world, source, amount);

			if (attacker instanceof IPvzEntity || attacker instanceof PlayerEntity) {
				this.hurtTime = 0;
				this.timeUntilRegen = 0;
			}
		}

		return damaged;
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		// Don't let the player duplicate lawn mowers
		return ActionResult.PASS;
	}

	/**
	 * Mowers cannot despawn naturally.
	 * @return Whether this entity's "intermediate range" despawning is disabled or not.
	 */
	@Override
	public boolean cannotDespawn() {
		return false;
	}

	/**
	 * Mowers cannot despawn naturally.
	 * @return Whether this entity can naturally despawn at all or not.
	 */
	@Override
	public boolean canImmediatelyDespawn(double distanceSquared) {
		return false;
	}

	/*
		ANIMATIONS
	 */

	/*
		BLOCKS
	 */

	@Override
	protected boolean shouldPathLikeZombie() {
		return false;
	}

	@Override
	protected TagKey<Block> getGoalBlockTag() {
		return ModTags.Blocks.ZOMBIE_START;
	}

	@Override
	protected TagKey<Block> getGoalMarkerTag() {
		return ModTags.Blocks.ZOMBIE_START_MARKERS;
	}

	@Override
	protected TagKey<Block> getStartBlockTag() {
		return ModTags.Blocks.ZOMBIE_GOAL;
	}

	@Override
	protected TagKey<Block> getStartMarkerTag() {
		return ModTags.Blocks.ZOMBIE_GOAL_MARKERS;
	}

	/*public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public boolean isWalkable(BlockPos pos) {
		return LawnUtil.isWalkable(pos, getEntityWorld(), pathId, getSteppingPos(), false);
	}

	public boolean isThisPath(BlockPos pos) {
		return isCertainPath(pos, pathId);
	}

	public boolean isCertainPath(BlockPos pos, int pathId) {
		return LawnUtil.isCertainPath(pos, getEntityWorld(), pathId);
	}*/

	/**
	 * @return Whether a lawn mower can be placed on top of this block.
	 */
	public static boolean isPlaceable(BlockPos pos, World world) {
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.getBlock() == ModBlocks.GARDEN_MARKER;
		}
		return world.getBlockState(pos).getBlock() == ModBlocks.GARDEN_BLOCK;
	}

	/*/**
	 * @return Whether this block is a lawn mower goal.
	 *
	public boolean isGoal(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_START_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_START);
	}

	/**
	 * @return Whether this block is a lawn mower start.
	 *
	public boolean isStart(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_GOAL_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_GOAL);
	}*/

	/*@Override
	public float getPathfindingPenalty(PathNodeType nodeType) {
		if (nodeType == PathNodeType.) {
			return 0f; // Path
		}
		else if (state.isIn(ModTags.Blocks.ZOMBIE_GOAL)) {
			return 0f; // Goal
		}
		return -1f; // Off-road
	}*/

	/*@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		//BlockState state = world.getBlockState(pos.down());
		if (isThisPath(pos.down())) {
			return 0f; // Path
		}
		else if (isGoal(pos.down())) {
			return 0f; // Goal
		}
		return Float.NEGATIVE_INFINITY; // Off-road
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PvzNavigation(this, world);
	}*/

	/*
		STATS
	 */

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 50 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 10000)
			.add(EntityAttributes.FOLLOW_RANGE, 64)
			.add(EntityAttributes.MOVEMENT_SPEED, 0.3);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new LawnMowerStayOnPathGoal(this, 1f));
		//this.goalSelector.add(2, new LawnMowerTargetGoal<>(this, ZombieEntity.class, true));
		//this.goalSelector.add(2, new ZombieMeleeAttackGoal(this, 1, false));
		this.goalSelector.add(3, new LawnMowerMoveGoal(this, 1f));
	}

	/*
		SOUNDS
	 */

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		//super.playStepSound(pos, state);
	}

	@Override
	protected void playSecondaryStepSound(BlockState state) {
		//super.playSecondaryStepSound(state);
	}

	@Override
	protected @org.jspecify.annotations.Nullable SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.ENTITY_LAWN_MOWER_DAMAGE;
	}

	@Override
	protected @org.jspecify.annotations.Nullable SoundEvent getDeathSound() {
		return ModSounds.ENTITY_LAWN_MOWER_DESTROY;
	}

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}

	/*
		DEBUG RENDERING
	*/

	@Override
	public void registerTracking(ServerWorld world, Tracker tracker) {
		super.registerTracking(world, tracker);

		tracker.track(ModDebugSubscriptionTypes.PVZ_PATHS, () -> {
			Path path = this.getNavigation().getCurrentPath();
			return path != null && path.getDebugNodeInfos() != null ? new EntityPathDebugData(path.copy(), this.getNavigation().getNodeReachProximity()) : null;
		});
	}
}
