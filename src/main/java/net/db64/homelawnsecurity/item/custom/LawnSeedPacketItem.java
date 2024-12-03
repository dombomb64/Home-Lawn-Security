package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class LawnSeedPacketItem extends SeedPacketItem {
	public LawnSeedPacketItem(EntityType<? extends MobEntity> type, Settings settings) {
		super(type, settings);
	}

	@Override
	public Predicate<ItemStack> getBagPredicate() {
		return SUN_BAG_PREDICATE;
	}

	@Override
	public void playPlaceSound(World world, BlockPos pos) {
		if (world.getFluidState(pos.down()).isIn(FluidTags.WATER))
			world.playSound(null, pos, ModSounds.ENTITY_PLANT_PLACE_WATER, SoundCategory.NEUTRAL);
		else
			world.playSound(null, pos, ModSounds.ENTITY_PLANT_PLACE, SoundCategory.NEUTRAL);
	}
}
