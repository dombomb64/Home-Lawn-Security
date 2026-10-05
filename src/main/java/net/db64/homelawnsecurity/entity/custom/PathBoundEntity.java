package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.ModDamageTypes;
import net.db64.homelawnsecurity.entity.ai.PvzNavigation;
import net.db64.homelawnsecurity.networking.EntityDamagedS2CPayload;
import net.db64.homelawnsecurity.networking.SnapLookDirS2CPayload;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

/**
 * Extended by any entity that needs to have lawn-based pathfinding, like zombies and lawnmowers.
 */
public abstract class PathBoundEntity extends PathAwareEntity {
	protected int pathId = 1;
	protected float defaultOrientation = 0f;

	public boolean lastAttackedByPvzEntity = true;
	public int hurtFlashTime = 0;

	private static final TrackedData<Float> DEFAULT_ORIENTATION =
		DataTracker.registerData(PathBoundEntity.class, TrackedDataHandlerRegistry.FLOAT);

	protected PathBoundEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}



	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

		//HomeLawnSecurity.LOGGER.info("new path-bound entity");
		BlockPos pos = getSteppingPos();
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(pos, 5, 5, 5);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("block pos {} is:", blockPos.toShortString());
			if (LawnUtil.isAnyPath(blockPos, getEntityWorld())) {
				for (int i = 1; i <= LawnUtil.getPathTypeAmount(); i++) {
					if (LawnUtil.isCertainPath(blockPos, getEntityWorld(), i, true, false)) {
						//HomeLawnSecurity.LOGGER.info("path {}", i);
						pathId = i;
						break;
					}
				}
				break;
			}
			//HomeLawnSecurity.LOGGER.info("not a path");
		}

		return result;
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(DEFAULT_ORIENTATION, 0f);
	}

	public void faceSpawnDirection() {
		if (this instanceof PlantEntity plant && !plant.onPath) {
			facePath();
		}
		else {
			faceGoal();
		}
	}

	private void faceGoal() {
		// Find the goal and turn to face forward
		int rangeH = 16;
		int rangeV = 5;
		Iterable<BlockPos> iterable2 = BlockPos.iterateOutwards(getSteppingPos().up(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable2) {
			//HomeLawnSecurity.LOGGER.info("entity is checking for if the block at {}, {}, {} is a goal", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!isGoal(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("entity is checking for if the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			Path path = getNavigation().findPathTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1);
			/*if (path == null)
				HomeLawnSecurity.LOGGER.info("the goal at {}, {}, {} could not generate a path", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			else if (!path.reachesTarget())
				HomeLawnSecurity.LOGGER.info("the goal at {}, {}, {} is not reachable with this path", blockPos.getX(), blockPos.getY(), blockPos.getZ());*/
			if (path == null || !path.reachesTarget()) continue;
			// TODO: Iterate through path nodes here to make sure the path doesn't have a goal block before the last node?

			Vec3d pos = getEntityPos().floorAlongAxes(EnumSet.of(Direction.Axis.X, Direction.Axis.Z));
			Vec3d facingPos;
			if (path.getLength() > 1) {
				facingPos = path.getNode(1).getPos();
			}
			else {
				BlockPos facingBlock = path.getTarget();
				facingPos = new Vec3d(facingBlock.getX(), facingBlock.getY(), facingBlock.getZ());
			}
			// Get the correct direction
			float newYaw = getOrdinalYaw(pos, facingPos);
			//HomeLawnSecurity.LOGGER.info("Setting default orientation to {} (entity pos: ({}, {}), path pos: ({}, {}))", newYaw, pos.getX(), pos.getZ(), facingPos.getX(), facingPos.getZ());
			setYaw(newYaw);
			setHeadYaw(newYaw);
			setBodyYaw(newYaw);
			/*headYaw = newYaw;
			bodyYaw = newYaw;
			refreshPositionAndAngles(pos, newYaw, getPitch());*/
			lastYaw = newYaw;
			lastHeadYaw = newYaw;
			lastBodyYaw = newYaw;
			//serverHeadYaw = newYaw;
			defaultOrientation = newYaw;
			dataTracker.set(DEFAULT_ORIENTATION, defaultOrientation);

			for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) getEntityWorld(), getBlockPos())) {
				ServerPlayNetworking.send(player, new SnapLookDirS2CPayload(uuid, getPitch(), getYaw()));
			}

			break;
		}
	}

	private void facePath() {
		// Find the path and turn to face it
		int rangeH = 5;
		int rangeV = 5;
		Iterable<BlockPos> iterable2 = BlockPos.iterateOutwards(getSteppingPos().up(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable2) {
			//HomeLawnSecurity.LOGGER.info("entity is checking for if the block at {}, {}, {} is a path", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!LawnUtil.isAnyPath(blockPos.down(), getEntityWorld())) continue;
			//HomeLawnSecurity.LOGGER.info("entity is checking for if the path at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			Path path = getNavigation().findPathTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1);
			/*if (path == null)
				HomeLawnSecurity.LOGGER.info("the path at {}, {}, {} could not generate a path", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			else if (!path.reachesTarget())
				HomeLawnSecurity.LOGGER.info("the path at {}, {}, {} is not reachable with this path", blockPos.getX(), blockPos.getY(), blockPos.getZ());*/
			if (path == null || !path.reachesTarget()) continue;

			Vec3d pos = getEntityPos().floorAlongAxes(EnumSet.of(Direction.Axis.X, Direction.Axis.Z));
			Vec3d facingPos;
			if (path.getLength() > 1) {
				facingPos = path.getNode(1).getPos();
			}
			else {
				BlockPos facingBlock = path.getTarget();
				facingPos = new Vec3d(facingBlock.getX(), facingBlock.getY(), facingBlock.getZ());
			}
			// Get the correct direction
			float newYaw = getOrdinalYaw(pos, facingPos);
			//HomeLawnSecurity.LOGGER.info("Setting default orientation to {} (entity pos: ({}, {}), path pos: ({}, {}))", newYaw, pos.getX(), pos.getZ(), facingPos.getX(), facingPos.getZ());
			setYaw(newYaw);
			setHeadYaw(newYaw);
			setBodyYaw(newYaw);
			/*headYaw = newYaw;
			bodyYaw = newYaw;
			refreshPositionAndAngles(pos, newYaw, getPitch());*/
			lastYaw = newYaw;
			lastHeadYaw = newYaw;
			lastBodyYaw = newYaw;
			//serverHeadYaw = newYaw;
			defaultOrientation = newYaw;
			dataTracker.set(DEFAULT_ORIENTATION, defaultOrientation);

			for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) getEntityWorld(), getBlockPos())) {
				ServerPlayNetworking.send(player, new SnapLookDirS2CPayload(uuid, getPitch(), getYaw()));
			}

			break;
		}
	}

	private float getOrdinalYaw(Vec3d pos, Vec3d facingPos) {
		if (pos.getZ() < facingPos.getZ()) {
			if (pos.getX() < facingPos.getX()) {
				// Southeast
				return -45f;
			}
			else if (pos.getX() > facingPos.getX()) {
				// Southwest
				return 45f;
			}
			else {
				// South
				return 0f;
			}
		}
		else if (pos.getZ() > facingPos.getZ()) {
			if (pos.getX() < facingPos.getX()) {
				// Northeast
				return -135f;
			}
			else if (pos.getX() > facingPos.getX()) {
				// Northwest
				return 135f;
			}
			else {
				// North
				return 180f;
			}
		}
		else if (pos.getX() < facingPos.getX()) {
			// East
			return -90f;
		}
		else {
			// West
			return 90f;
		}
	}

	@Override
	public void tick() {
		/*if (getEntityWorld().isClient() && age == 1) {
			float newYaw = dataTracker.get(DEFAULT_ORIENTATION);
			setYaw(newYaw);
			setHeadYaw(newYaw);
			setBodyYaw(newYaw);
			lastYaw = getYaw();
			lastHeadYaw = getHeadYaw();
			lastBodyYaw = getBodyYaw();
			//serverHeadYaw = newYaw;
		}*/
		super.tick();
	}

	@Override
	public void baseTick() {
		super.baseTick();

		if (getEntityWorld().isClient()) {
			if (this.hurtFlashTime > 0) {
				this.hurtFlashTime--;
			}
		}
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		// oh no, i hope this really heavy beach ball doesn't break my leg, ahaha- *snap*

		LivingEntity attacker = getAttacker();
		if (attacker != null && attacker.getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			super.takeKnockback(strength * 3, x, z);
			addVelocity(0d, 0.2d, 0d);
		}
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		Entity attacker = source.getAttacker();
		boolean damaged;

		if (!source.isOf(ModDamageTypes.ZOMBIE_HEADLESS)) {
			lastAttackedByPvzEntity = attacker instanceof IPvzEntity || source.getSource() instanceof IPvzEntity;
			for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) getEntityWorld(), getBlockPos())) {
				ServerPlayNetworking.send(player, new EntityDamagedS2CPayload(getUuid(), lastAttackedByPvzEntity, 10));
			}
		}

		if (attacker instanceof LivingEntity living && living.getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			damaged = super.damage(world, source, 1000000);
			world.playSound(this, getBlockPos(), ModSounds.RANDOM_SHOVEL_ATTACK, attacker.getSoundCategory(), 1, 1);
		}
		else {
			damaged = super.damage(world, source, amount);
		}

		return damaged;
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PvzNavigation(this, world);
	}

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
		}
		view.putInt("path_id", pathId);

		view.putFloat("default_orientation", defaultOrientation);
		dataTracker.set(DEFAULT_ORIENTATION, defaultOrientation);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		pathId = view.getInt("path_id", 1);
		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
		}

		defaultOrientation = view.getFloat("default_orientation", defaultOrientation);
	}



	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}



	/**
	 * @return Whether this entity should path towards the garden or not.
	 * @implSpec Should be <code>true</code> for Team Zombies and <code>false</code> for Team Plants.
	 */
	protected abstract boolean shouldPathLikeZombie();

	/**
	 * @return The tag that this entity checks for when looking for valid goal blocks.
	 */
	protected abstract TagKey<Block> getGoalBlockTag();

	/**
	 * @return The tag that this entity checks for when looking for valid goal markers.
	 */
	protected abstract TagKey<Block> getGoalMarkerTag();

	/**
	 * @return The tag that this entity checks for when looking for valid start blocks.
	 */
	protected abstract TagKey<Block> getStartBlockTag();

	/**
	 * @return The tag that this entity checks for when looking for valid start markers.
	 */
	protected abstract TagKey<Block> getStartMarkerTag();



	/**
	 * @param pos The block to check.
	 * @return Whether the block at <code>pos</code> is the kind of block this entity aspires to step on.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isGoal(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(getGoalMarkerTag());
		}
		return state.isIn(getGoalBlockTag());
	}

	/**
	 * @param pos The block to check.
	 * @return Whether the block at <code>pos</code> is the antithesis to this entity's goal block.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isStart(BlockPos pos) {
		World world = getEntityWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(getStartMarkerTag());
		}
		return state.isIn(getStartBlockTag());
	}



	/**
	 * @param pos The block to check.
	 * @return Whether this entity can currently path over this block.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isWalkable(BlockPos pos) {
		return LawnUtil.isWalkable(pos, getEntityWorld(), pathId, shouldPathLikeZombie());
	}

	/**
	 * @param pos The block to check.
	 * @return Whether the block at <code>pos</code> is a lawn block with this entity's path ID.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isThisPath(BlockPos pos) {
		return isCertainPath(pos, pathId);
	}

	/**
	 * @param pos The block to check.
	 * @param pathId The ID of the path to look for in the block.
	 * @return Whether the block at <code>pos</code> is a lawn block with a specific path ID.
	 * @apiNote Will prioritize any markers placed at <code>pos.up()</code>.
	 */
	public boolean isCertainPath(BlockPos pos, int pathId) {
		return LawnUtil.isCertainPath(pos, getEntityWorld(), pathId);
	}
}
