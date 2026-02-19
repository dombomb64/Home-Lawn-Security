package net.db64.homelawnsecurity.entity.custom.other;


import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.World;

public class PlantSeedPacketPathfindingEntity extends SeedPacketPathfindingEntity {
	public PlantSeedPacketPathfindingEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

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
}
