package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZombieSeedPacketItem extends SeedPacketItem {
	public ZombieSeedPacketItem(EntityType<? extends MobEntity> type, int cooldownLength, Settings settings) {
		super(type, cooldownLength, settings);
	}

	@Override
	protected boolean isPlaceable(BlockPos blockPos, World world) {
		return PlantEntity.isPlaceablePath(blockPos, world);
	}
}
