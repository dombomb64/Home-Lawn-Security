package net.db64.homelawnsecurity.util;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
	public static final String dataId = "homelawnsecurity:data";
	NbtCompound getPersistentData();
}
