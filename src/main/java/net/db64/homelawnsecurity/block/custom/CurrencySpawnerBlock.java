package net.db64.homelawnsecurity.block.custom;

import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class CurrencySpawnerBlock extends Block {
	public final ItemStack stack;
	private static final float SPAWN_CHANCE = 0.05f;
	private static final int SPAWN_DISTANCE = 10;

	public CurrencySpawnerBlock(Settings settings, ItemStack stack) {
		super(settings);
		this.stack = stack;
	}

	public ItemStack getDefaultItemStack() {
		return new ItemStack(ModItems.SUN);
	}

	@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);

		if (random.nextFloat() < SPAWN_CHANCE) {
			spawnCurrency(world, pos, random);
		}
	}

	/*@Override
	protected boolean hasRandomTicks(BlockState state) {
		return true;
	}*/

	public void spawnCurrency(ServerWorld world, BlockPos pos, Random random) {
		Iterable<BlockPos> iterable = BlockPos.iterateRandomly(random, 30, pos, SPAWN_DISTANCE);

		for (BlockPos pos2 : iterable) {
			if (isValidSpawnLocation(world, pos)) {
				world.spawnEntity(new CurrencyEntity(pos2.getX(), pos2.getY() + 16, pos.getZ(), world, stack.copy()));
			}
		}
	}

	public boolean isValidSpawnLocation(ServerWorld world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.MARKERS)) {
			return markerState.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN_MARKERS);
		}
		return state.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN);
	}
}
