package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class ZombieSeedPacketItem extends SeedPacketItem {
	public ZombieSeedPacketItem(EntityType<? extends MobEntity> type, Settings settings) {
		super(type, settings);
	}

	@Override
	protected boolean isPlaceable(BlockPos blockPos, World world) {
		return ZombieEntity.isPlaceable(blockPos, world);
	}

	@Override
	public Predicate<ItemStack> getBagPredicate() {
		return BRAINPOWER_BAG_PREDICATE;
	}

	@Override
	public void playPlaceSound(World world, BlockPos pos) {
		if (world.getFluidState(pos.down()).isIn(FluidTags.WATER))
			world.playSound(null, pos, ModSounds.ENTITY_ZOMBIE_PLACE_WATER, SoundCategory.NEUTRAL);
		else
			world.playSound(null, pos, ModSounds.ENTITY_ZOMBIE_PLACE, SoundCategory.NEUTRAL);
	}
}
