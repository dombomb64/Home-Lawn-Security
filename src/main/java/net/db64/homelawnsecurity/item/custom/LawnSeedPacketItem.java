package net.db64.homelawnsecurity.item.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;

public class LawnSeedPacketItem extends SeedPacketItem {
	public LawnSeedPacketItem(EntityType<? extends MobEntity> type, int cooldownLength, Settings settings) {
		super(type, cooldownLength, settings);
	}
}
