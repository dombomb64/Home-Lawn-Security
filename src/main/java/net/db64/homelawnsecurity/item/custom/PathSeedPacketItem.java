package net.db64.homelawnsecurity.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

public class PathSeedPacketItem extends SeedPacketItem {
	public PathSeedPacketItem(EntityType<? extends MobEntity> type, int cooldownLength, Settings settings) {
		super(type, cooldownLength, settings);
	}
}
