package net.db64.homelawnsecurity.entity.custom.other;

import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.entity.ai.PvzNavigation;
import net.db64.homelawnsecurity.entity.ai.other.LawnMowerMoveGoal;
import net.db64.homelawnsecurity.entity.ai.other.LawnMowerStayOnPathGoal;
import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ZombieGravestoneEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class LawnMowerEntity extends PathAwareEntity implements IPvzEntity, IPathBoundEntity {
	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
	public TagKey<Block> pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;

	public boolean mowing;

	/*
		GENERAL
	 */

	public LawnMowerEntity(EntityType<LawnMowerEntity> entityType, World world) {
		super(entityType, world);

		/*if (!getWorld().isClient) {
			setPathTagNbt(pathTag);
		}*/
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

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
				//HomeLawnSecurity.LOGGER.info("path a");
				break;
			}
			if (isOtherPath(blockPos)) {
				//HomeLawnSecurity.LOGGER.info("path b");
				switchPathTag();
				break;
			}
			//HomeLawnSecurity.LOGGER.info("not a path");
		}

		mowing = false;

		return result;
	}

	@Override
	public void tick() {
		super.tick();

		World world = getWorld();
		if (world instanceof ServerWorld serverWorld) {
			boolean wasMowing = mowing;

			for (ZombieEntity entity : world
				.getEntitiesByClass(ZombieEntity.class, this.getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR)) {
				if (!(entity instanceof TargetZombieEntity) && !(entity instanceof ZombieGravestoneEntity)) {
					mowing = true;
					entity.kill(serverWorld);
				}
			}

			if (mowing && !wasMowing)
				world.playSoundFromEntity(null, this, ModSounds.ENTITY_LAWN_MOWER_ACTIVATE, SoundCategory.NEUTRAL, 1f, world.getRandom().nextFloat() * 0.4f + 1f);
		}
	}

	public void disappear() {
		((ServerWorld) getWorld()).spawnParticles(ParticleTypes.POOF, getBodyX(0.5), getBodyY(0.5), getBodyZ(0.5), 20, getWidth() * 0.5, getHeight() * 0.5, getWidth() * 0.5, 0.02);

		this.discard();
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		if (pathTag == ModTags.Blocks.ZOMBIE_PATH_2)
			nbt.putInt("path_tag", 2);
		else
			nbt.putInt("path_tag", 1);

		if (this.mowing) {
			nbt.putBoolean("mowing", true);
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		// path_tag
		int path = nbt.getInt("path_tag").orElse(1);
		if (path == 2) {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_2;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_2_MARKERS;
		} else {
			pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
			pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;
		}

		// mowing
		this.mowing = nbt.getBoolean("mowing").orElse(false);
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
		if (attacker instanceof LivingEntity && ((LivingEntity) attacker).getMainHandStack().contains(ModDataComponentTypes.SHOVEL)) {
			world.playSound(this, getBlockPos(), ModSounds.RANDOM_SHOVEL_ATTACK, attacker.getSoundCategory(), 1, 1);
			damaged = super.damage(world, source, 1000000);
		}
		else if (attacker instanceof IPvzEntity) {
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

	public TagKey<Block> iGetPathTag() {
		return pathTag;
	}

	public void iSetPathTag(TagKey<Block> value) {
		pathTag = value;
	}

	public TagKey<Block> iGetPathMarkerTag() {
		return pathMarkerTag;
	}

	public void iSetPathMarkerTag(TagKey<Block> value) {
		pathMarkerTag = value;
	}

	@Override
	public World iGetWorld() {
		return getWorld();
	}

	public void iStopNavigation() {
		navigation.stop();
	}

	@Override
	public Entity iGetSelf() {
		return this;
	}

	/**
	 * @return Whether a lawn mower can be placed on top of this block.
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
		return new PvzNavigation(this, world);
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
}
