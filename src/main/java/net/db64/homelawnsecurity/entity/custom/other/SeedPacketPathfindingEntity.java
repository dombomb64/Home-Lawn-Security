package net.db64.homelawnsecurity.entity.custom.other;

import net.db64.homelawnsecurity.entity.ai.PvzNavigation;
import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.IPvzEntity;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class SeedPacketPathfindingEntity extends PathAwareEntity implements IPathBoundEntity {
	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;
	public TagKey<Block> pathMarkerTag = ModTags.Blocks.ZOMBIE_PATH_1_MARKERS;

	protected SeedPacketPathfindingEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

		//HomeLawnSecurity.LOGGER.info("new seed packet pathfinding entity");
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

		return result;
	}

	/*@Override
	public void tick() {
		super.tick();

		discard();
	}*/

	@Override
	public void checkDespawn() {
		this.discard();
	}

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

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PvzNavigation(this, world);
	}

	public static int searchDistanceFromClose() {
		return 3;
	}

	public static int searchDistanceFromOtherEnd() {
		return 16;
	}

	@Nullable
	public Path findPathToGoal(int searchDistance) {
		int rangeH = searchDistance;
		int rangeV = 5;
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(getBlockPos(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("seed packet is checking for if the block at {}, {}, {} is a goal", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!isGoal(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("seed packet is checking for if the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			Path path = getNavigation().findPathTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1);
			if (path == null) continue;
			//HomeLawnSecurity.LOGGER.info("seed packet has determined that the goal at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			return path;
		}
		return null;
	}

	@Nullable
	public Path findPathToStart(int searchDistance) {
		int rangeH = searchDistance;
		int rangeV = 5;
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(getBlockPos(), rangeH, rangeV, rangeH);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("seed packet is checking for if the block at {}, {}, {} is a start", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			if (!isStart(blockPos.down())) continue;
			//HomeLawnSecurity.LOGGER.info("seed packet is checking for if the start at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			Path path = getNavigation().findPathTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1);
			if (path == null) continue;
			//HomeLawnSecurity.LOGGER.info("seed packet has determined that the start at {}, {}, {} is reachable", blockPos.getX(), blockPos.getY(), blockPos.getZ());
			return path;
		}
		return null;
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return MobEntity.createMobAttributes()
			.add(EntityAttributes.MAX_HEALTH, 270 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.ATTACK_DAMAGE, 5 * IPvzEntity.HEALTH_SCALE)
			.add(EntityAttributes.FOLLOW_RANGE, 64)
			.add(EntityAttributes.MOVEMENT_SPEED, 0.1);
	}
}
