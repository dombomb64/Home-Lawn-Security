package net.db64.homelawnsecurity.block.custom.lawn;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Map;

public abstract class LawnBlockBlock extends Block implements ILawnBlock {
	public static int SHADE_AMOUNT = 2;
	public static IntProperty SHADE = IntProperty.of("shade", 0, SHADE_AMOUNT);

	public LawnBlockBlock(Settings settings) {
		super(settings);
		this.setDefaultState(
			this.getDefaultState()
				//.with(TURF, true)
				.with(PATH_ID_MAIN, 0)
				.with(PATH_ID_INTERSECTING, 0)
				.with(SHADE, 0)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		//builder.add(TURF);
		builder.add(PATH_ID_MAIN);
		builder.add(PATH_ID_INTERSECTING);
		builder.add(SHADE);
	}

	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
		ItemStack stack = super.getPickStack(world, pos, state, includeData);
		stack.set(DataComponentTypes.BLOCK_STATE, new BlockStateComponent(Map.of(
			//"turf", state.get(TURF).toString(),
			"path_id_main", state.get(PATH_ID_MAIN).toString(),
			"path_id_intersecting", state.get(PATH_ID_INTERSECTING).toString(),
			"shade", ILawnBlock.getTurf(state) ? state.get(SHADE).toString() : "0"
		)));
		return stack;
	}

	public static void setShade(BlockPos pos, World world, int shade) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof LawnBlockBlock) {
			if (shade >= 0 && shade <= SHADE_AMOUNT) {
				world.setBlockState(pos, world.getBlockState(pos).with(SHADE, shade));
			} else {
				HomeLawnSecurity.LOGGER.warn("Attempted to set shade of block {} to value outside of bounds!", pos.toShortString());
			}
		}
		else {
			HomeLawnSecurity.LOGGER.warn("Attempted to set shade of block {}, but the block isn't a lawn block!", pos.toShortString());
		}
	}

	/**
	 * Expects the block to use this class!
	 */
	public static int getShade(BlockState state) {
		return state.get(SHADE);
	}

	/**
	 * Expects the block to use this class!
	 */
	public static int getNextShade(BlockState state) {
		return (state.get(SHADE) + 1) % (SHADE_AMOUNT + 1);
	}
}
