package net.db64.homelawnsecurity.item.custom;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equipment;
import net.minecraft.item.Item;

public class DavesPanItem extends Item implements Equipment {
	public DavesPanItem(Settings settings) {
		super(settings);
	}

	@Override
	public EquipmentSlot getSlotType() {
		return EquipmentSlot.HEAD;
	}
}
