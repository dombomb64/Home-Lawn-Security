package net.db64.homelawnsecurity.block.custom;

import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import org.jspecify.annotations.Nullable;

public class SpeakerBlock extends Block {
	public static final BooleanProperty POWERED = Properties.POWERED;

	public SoundEvent sound;

	public SpeakerBlock(Settings settings, SoundEvent sound) {
		super(settings);
		this.sound = sound;
		setDefaultState(
			getDefaultState()
				.with(POWERED, false)
		);
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(POWERED);
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		super.onBlockAdded(state, world, pos, oldState, notify);
		if (state.get(POWERED)) {
			playSound(world, pos);
		}
	}

	@Override
	public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
		boolean powered = ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos());
		return getDefaultState().with(POWERED, powered);
	}

	@Override
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
		if (!world.isClient()) {
			boolean newPowered = world.isReceivingRedstonePower(pos);
			if (state.get(POWERED) != newPowered) {
				world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
				if (newPowered) {
					playSound(world, pos);
				}
			}
		}
	}

	protected void playSound(World world, BlockPos pos) {
		world.playSound(null, pos, getSound(), SoundCategory.BLOCKS, 1.0f, 1.0f);
	}

	protected SoundEvent getSound() {
		return sound;
	}
}
