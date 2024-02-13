package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.entity.ai.PathNavigation;
import net.db64.homelawnsecurity.entity.ai.StayOnPathGoal;
import net.db64.homelawnsecurity.entity.ai.ZombieMoveGoal;
import net.db64.homelawnsecurity.entity.ai.ZombieTargetGoal;
import net.db64.homelawnsecurity.entity.custom.plant.IPathPlant;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.List;

public abstract class ZombieEntity extends PathAwareEntity {
	public TagKey<Block> pathTag = ModTags.Blocks.ZOMBIE_PATH_1;

	protected ZombieEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
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
		BlockState state = world.getBlockState(pos.down());
		if (state.isIn(pathTag)) {
			return 0f; // Path
		}
		else if (state.isIn(ModTags.Blocks.ZOMBIE_GOAL)) {
			return 0f; // Goal
		}
		return Float.NEGATIVE_INFINITY; // Off-road
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(0, new StayOnPathGoal(this, 1f));
		this.goalSelector.add(2, new ZombieTargetGoal<PlantEntity>(this, PlantEntity.class, true));
		this.goalSelector.add(3, new ZombieMoveGoal(this, 1f));
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PathNavigation(this, world);
	}

	@Override
	public void pushAwayFrom(Entity entity) {
		if (entity instanceof PlantEntity) {
			super.pushAwayFrom(entity);
		}
	}

	@Override
	public void takeKnockback(double strength, double x, double z) {
		// oh no--i hope this really heavy beach ball doesn't break my leg
	}
}
