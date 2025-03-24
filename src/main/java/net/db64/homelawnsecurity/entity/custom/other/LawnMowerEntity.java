package net.db64.homelawnsecurity.entity.custom.other;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.entity.ai.other.LawnMowerMoveGoal;
import net.db64.homelawnsecurity.entity.ai.other.LawnMowerNavigation;
import net.db64.homelawnsecurity.entity.ai.other.LawnMowerStayOnPathGoal;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.IEntityDataSaver;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class LawnMowerEntity extends PathAwareEntity implements IPvzEntity {
	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
	public TagKey<Block> pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;

	public boolean mowing;

	/*
		GENERAL
	 */

	public LawnMowerEntity(EntityType<LawnMowerEntity> entityType, World world) {
		super(entityType, world);

		if (!getWorld().isClient) {
			setPathTagNbt(pathTag);
		}
	}

	/**
	 * Runs on the first tick so that it has access to the position.
	 */
	public void firstTick() {

		/*World world2 = getWorld();
		BlockState groundState = world2.getBlockState(this.getBlockPos().down());
		BlockState markerState = world2.getBlockState(this.getBlockPos());
		if (markerState.isIn(this.getOtherPathMarkerTag(pathMarkerTag)) // The block is marked as the other path
			|| (!markerState.isIn(ModTags.Blocks.MARKERS) && groundState.isIn(this.getOtherPathTag(pathTag)))) { // The block is not marked, but is the other path
			switchPathTag();
		}*/

		//HomeLawnSecurity.LOGGER.info("new lawnmower");
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(getBlockPos().down(), 5, 5, 5);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("block pos {} is:", blockPos.toShortString());
			if (isPath(blockPos)) {
				HomeLawnSecurity.LOGGER.info("path a");
				break;
			}
			if (isOtherPath(blockPos)) {
				HomeLawnSecurity.LOGGER.info("path b");
				switchPathTag();
				break;
			}
			//HomeLawnSecurity.LOGGER.info("not a path");
		}

		mowing = false;
	}

	@Override
	public void tick() {
		super.tick();

		World world = getWorld();
		if (world instanceof ServerWorld serverWorld) {
			if (age == 1) {
				firstTick();
			}

			boolean wasMowing = mowing;

			for (ZombieEntity entity : world
				.getEntitiesByClass(ZombieEntity.class, this.getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR)) {
				mowing = true;
				entity.kill(serverWorld);
			}

			if (mowing && !wasMowing)
				world.playSoundFromEntity(null, this, ModSounds.ENTITY_LAWN_MOWER_ACTIVATE, SoundCategory.NEUTRAL, 1f, world.getRandom().nextFloat() * 0.4f + 1f);
		}
	}

	public void disappear() {
		// TODO: Add poof particles?
		this.discard();
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if (this.mowing) {
			nbt.putBoolean("mowing", true);
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		// pathTag
		/*if (nbt.contains("pathTag", NbtElement.INT_TYPE)) {
			if (nbt.getInt("pathTag") == 2) {
				pathTag = ModTags.Blocks.ZOMBIE_PATH_2;
				pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
			}
			else {
				pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
				pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
			}
		}*/

		// mowing
		if (nbt.contains("mowing", NbtElement.NUMBER_TYPE)) {
			this.mowing = nbt.getBoolean("mowing");
		}
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		// oh no--i hope this really heavy beach ball doesn't break my leg
	}

	@Override
	public boolean damage(ServerWorld world, DamageSource source, float amount) {
		boolean damaged;
		if ((source.getAttacker() != null && source.getAttacker() instanceof IPvzEntity)) {
			damaged = super.damage(world, source, amount);

			this.hurtTime = 0;
			this.timeUntilRegen = 0;
		}
		else {
			damaged = super.damage(world, source, amount * 10);
		}

		return damaged;
	}

	/*
		ANIMATIONS
	 */

	/*
		BLOCKS
	 */

	/**
	 * @return Whether a zombie can be placed on top of this block.
	 */
	public static boolean isPlaceable(BlockPos pos, World world) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.LAWN_MOWER_PLACEABLE_MARKERS);
		}
		return state.isIn(ModTags.Blocks.LAWN_MOWER_PLACEABLE);
	}

	/**
	 * @return Whether this lawn mower can use this block as a path.
	 */
	public boolean isPath(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(pathMarkerTag) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(pathTag) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}

	/**
	 * @return Whether this lawn mower can't use this block as a path but the block is still a path.
	 */
	public boolean isOtherPath(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(getOtherPathMarkerTag(pathMarkerTag)) || markerState.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS_MARKERS);
		}
		return state.isIn(getOtherPathTag(pathTag)) || state.isIn(ModTags.Blocks.ZOMBIE_PATH_CROSS);
	}

	/**
	 * @return Whether this block is a lawn mower goal.
	 */
	public boolean isGoal(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_START_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_START);
	}

	/**
	 * @return Whether this block is a lawn mower start.
	 */
	public boolean isStart(BlockPos pos) {
		World world = getWorld();
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.ZOMBIE_GOAL_MARKERS);
		}
		return state.isIn(ModTags.Blocks.ZOMBIE_GOAL);
	}

	/**
	 * @return Whether this lawn mower can go on this block if they have not been put off-course.
	 */
	public boolean isPathOrGoal(BlockPos pos) {
		return isPath(pos) || isGoal(pos);
	}

	public void switchPathTag() {
		if (getWorld().isClient) {
			HomeLawnSecurity.LOGGER.info("tried switching path but was on the client for some reason!");
			return;
		}
		TagKey<Block> tagKey = getPathTagNbt();
		//if (this.getWorld().getBlockState(this.getBlockPos().down()).isIn(tagKey))
		//return; // shut up you are standing on it

		HomeLawnSecurity.LOGGER.info("switching path from {} to {}", tagKey.toString(), getOtherPathTag(tagKey).toString());
		navigation.stop();
		setPathTagNbt(getOtherPathTag(tagKey));
	}

	public TagKey<Block> getOtherPathTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1)
			return ModTags.Blocks.ZOMBIE_PATH_2; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1; // flop
	}

	public TagKey<Block> getOtherPathMarkerTag(TagKey<Block> tagKey) {
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_1_MARKERS)
			return ModTags.Blocks.ZOMBIE_PATH_2_MARKERS; // flip
		return ModTags.Blocks.ZOMBIE_PATH_1_MARKERS; // flop
	}

	private TagKey<Block> getPathTagNbt() {
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		if (nbt.getInt("pathTag") == 2) {
			this.pathTag = ModTags.Blocks.ZOMBIE_PATH_2;
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		}
		else {
			this.pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}
		return this.pathTag;
	}

	private TagKey<Block> getPathMarkerTagNbt() {
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		if (nbt.getInt("pathTag") == 2) {
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		}
		else {
			this.pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}
		return this.pathMarkerTag;
	}

	private void setPathTagNbt(TagKey<Block> tagKey) {
		this.pathTag = tagKey;
		NbtCompound nbt = ((IEntityDataSaver)this).getPersistentData();
		if (tagKey == ModTags.Blocks.ZOMBIE_PATH_2) {
			HomeLawnSecurity.LOGGER.info("setting to path b");
			nbt.putInt("pathTag", 2);
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		}
		else {
			HomeLawnSecurity.LOGGER.info("setting to path a");
			nbt.putInt("pathTag", 1);
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}
	}

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

	@Override
	public float getPathfindingFavor(BlockPos pos, WorldView world) {
		//BlockState state = world.getBlockState(pos.down());
		if (isPath(pos.down())) {
			return 0f; // Path
		}
		else if (isGoal(pos.down())) {
			return 0f; // Goal
		}
		return Float.NEGATIVE_INFINITY; // Off-road
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new LawnMowerNavigation(this, world);
	}

	/*
		STATS
	 */

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 100)
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
}
