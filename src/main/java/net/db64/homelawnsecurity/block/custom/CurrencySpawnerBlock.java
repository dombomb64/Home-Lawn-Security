package net.db64.homelawnsecurity.block.custom;

import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.CurrencyEntity;
import net.db64.homelawnsecurity.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CurrencySpawnerBlock extends Block {
	public final ItemStack stack;
	//private static final float SPAWN_CHANCE = 0.1f;
	private static final int SPAWN_DISTANCE = 10;
	private static final int SPAWN_TICKS = 200;
	private static final int INITIAL_SPAWN_TICKS = 20;

	public CurrencySpawnerBlock(Settings settings, ItemStack stack) {
		super(settings);
		this.stack = stack;
	}

	public ItemStack getDefaultItemStack() {
		return new ItemStack(ModItems.SUN);
	}

	@Override
	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return Block.createCuboidShape(2, 0, 2, 14, 12, 14);
	}

	/*@Override
	protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);

		//HomeLawnSecurity.LOGGER.info("currency spawner block at {} ticked", pos.toShortString());

		if (random.nextFloat() < SPAWN_CHANCE) {
			spawnCurrency(world, pos, random);
		}
	}*/

	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.scheduledTick(state, world, pos, random);

		if (world instanceof ServerWorld) {
			//HomeLawnSecurity.LOGGER.info("currency spawner block at {} ticked", pos.toShortString());

			//if (random.nextFloat() < SPAWN_CHANCE) {
			if (!world.isReceivingRedstonePower(pos) && world.isDay()) {
				spawnCurrency(world, pos, random);
			}

			world.scheduleBlockTick(pos, this, SPAWN_TICKS);
		}
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		super.onBlockAdded(state, world, pos, oldState, notify);

		world.scheduleBlockTick(pos, this, INITIAL_SPAWN_TICKS);
	}

	/*@Override
	protected boolean hasRandomTicks(BlockState state) {
		return true;
	}*/

	public void spawnCurrency(ServerWorld world, BlockPos pos, Random random) {
		Iterable<BlockPos> iterable = BlockPos.iterateRandomly(random, 30, pos, SPAWN_DISTANCE);

		if (world.getBlockState(pos).getBlock() != ModBlocks.BRAINPOWER_BEACON) {
			for (BlockPos pos2 : iterable) {
				BlockPos spawnPos = getGroundPos(world, pos2);
				if (isValidSunSpawnLocation(world, spawnPos)) {
					world.spawnEntity(new CurrencyEntity(spawnPos.getX() + 0.5, spawnPos.getY() + 16, spawnPos.getZ() + 0.5, world, stack.copy()));
					break;
				}
			}
		}
		else {
			for (BlockPos pos2 : iterable) {
				BlockPos spawnPos = getGroundPos(world, pos2);
				if (isValidBrainpowerSpawnLocation(world, spawnPos)) {
					world.spawnEntity(new CurrencyEntity(spawnPos.getX() + 0.5, spawnPos.getY() + 16, spawnPos.getZ() + 0.5, world, stack.copy()));
					break;
				}
			}
		}
	}

	public BlockPos getGroundPos(ServerWorld world, BlockPos pos) {
		BlockPos pos2 = pos;
		BlockState state = world.getBlockState(pos2);

		while (pos.getY() > world.getBottomY() && state.isTransparent())
		{
			pos2 = pos2.down();
			state = world.getBlockState(pos2);
		}

		//HomeLawnSecurity.LOGGER.info("block at {} is not transparent", pos2.toShortString());

		return pos2;
	}

	public static boolean isValidSunSpawnLocation(ServerWorld world, BlockPos pos) {
		/*BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.REVEALS_MARKERS)) {
			return markerState.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN_MARKERS);
		}
		return state.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN);*/

		return PlantEntity.isPlaceableLawn(pos, world) || PlantEntity.isPlaceablePath(pos, world);
	}

	public static boolean isValidBrainpowerSpawnLocation(ServerWorld world, BlockPos pos) {
		/*BlockState state = world.getBlockState(pos);
		BlockState markerState = world.getBlockState(pos.up());

		if (markerState.isIn(ModTags.Blocks.REVEALS_MARKERS)) {
			return markerState.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN_MARKERS);
		}
		return state.isIn(ModTags.Blocks.PLANT_PLACEABLE_LAWN);*/

		return ZombieEntity.isPlaceable(pos, world);
	}
}
