package net.db64.homelawnsecurity.block.custom.lawn;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Map;

public abstract class LawnBlockBlock extends Block implements ILawnBlock {
	public LawnBlockBlock(Settings settings) {
		super(settings);
		this.setDefaultState(
			this.getDefaultState()
				//.with(TURF, true)
				.with(PATH_ID_MAIN, 0)
				.with(PATH_ID_INTERSECTING, 0)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		//builder.add(TURF);
		builder.add(PATH_ID_MAIN);
		builder.add(PATH_ID_INTERSECTING);
	}

	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
		ItemStack stack = super.getPickStack(world, pos, state, includeData);
		stack.set(DataComponentTypes.BLOCK_STATE, new BlockStateComponent(Map.of(
			//"turf", state.get(TURF).toString(),
			"path_id_main", state.get(PATH_ID_MAIN).toString(),
			"path_id_intersecting", state.get(PATH_ID_INTERSECTING).toString()
		)));
		return stack;
	}
}
