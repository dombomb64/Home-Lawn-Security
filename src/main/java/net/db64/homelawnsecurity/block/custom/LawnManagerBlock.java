package net.db64.homelawnsecurity.block.custom;

import com.mojang.serialization.MapCodec;
import net.db64.homelawnsecurity.block.entity.LawnManagerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import org.jspecify.annotations.Nullable;

public class LawnManagerBlock extends BlockWithEntity {
	public static final MapCodec<LawnManagerBlock> CODEC = createCodec(LawnManagerBlock::new);

	public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
	public static final BooleanProperty POWERED = Properties.POWERED;
	public static final EnumProperty<Winner> WINNER = EnumProperty.of("winner", Winner.class);
	public static final IntProperty PLANT_SCORE = IntProperty.of("plant_score", 0, 3);

	public LawnManagerBlock(Settings settings) {
		super(settings);
		setDefaultState(
			getDefaultState()
				.with(FACING, Direction.NORTH)
				.with(POWERED, false)
				.with(WINNER, Winner.NONE)
				.with(PLANT_SCORE, 0)
		);
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);

		builder.add(FACING);
		builder.add(POWERED);
		builder.add(WINNER);
		builder.add(PLANT_SCORE);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite());
	}

	@Override
	protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
		if (!world.isClient()) {
			//updateNeighbors(state, world, pos);
			world.updateNeighborsExcept(pos.north(), this, Direction.SOUTH, null);
			world.updateNeighborsExcept(pos.south(), this, Direction.NORTH, null);
			world.updateNeighborsExcept(pos.east(), this, Direction.WEST, null);
			world.updateNeighborsExcept(pos.west(), this, Direction.EAST, null);
		}
		super.onStateReplaced(state, world, pos, moved);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (world.isClient()) {
			return ActionResult.SUCCESS;
		}
		resetState(state, world, pos);
		return ActionResult.SUCCESS;
	}

	@Override
	protected int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		Winner winner = state.get(WINNER);
		if (winner == Winner.PLANTS) {
			return direction == getPlantDirection(state) ? 15 : 0;
		}
		else if (winner == Winner.ZOMBIES) {
			return direction == getZombieDirection(state) ? 15 : 0;
		}
		return 0;
	}

	@Override
	protected int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
		return getStrongRedstonePower(state, world, pos, direction);
	}

	public void plantVictory(BlockState state, World world, BlockPos pos) {
		world.setBlockState(pos, world.getBlockState(pos).with(WINNER, Winner.PLANTS));
		updateNeighbors(state, world, pos);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
	}

	public void zombieVictory(BlockState state, World world, BlockPos pos) {
		world.setBlockState(pos, world.getBlockState(pos).with(WINNER, Winner.ZOMBIES));
		updateNeighbors(state, world, pos);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
	}

	public void resetState(BlockState state, World world, BlockPos pos) {
		world.setBlockState(pos, getDefaultState().with(FACING, world.getBlockState(pos).get(FACING)));
		updateNeighbors(state, world, pos);
		world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
	}

	@Override
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, @Nullable WireOrientation wireOrientation, boolean notify) {
		if (!world.isClient()) {
			boolean newPowered = world.isReceivingRedstonePower(pos);
			if (state.get(POWERED) != newPowered) {
				world.setBlockState(pos, state.cycle(POWERED), Block.NOTIFY_LISTENERS);
				if (newPowered) {
					resetState(state, world, pos);
				}
			}
		}
	}

	private void updateNeighbors(BlockState state, World world, BlockPos pos) {
		/*Direction direction = getPlantDirection(state);
		WireOrientation wireOrientation = OrientationHelper.getEmissionOrientation(
			world, direction, state.get(FACING)
		);
		world.updateNeighborsAlways(pos, this, wireOrientation);
		world.updateNeighborsAlways(pos.offset(direction), this, wireOrientation);

		direction = getZombieDirection(state);
		wireOrientation = OrientationHelper.getEmissionOrientation(
			world, direction, state.get(FACING)
		);
		world.updateNeighborsAlways(pos, this, wireOrientation);
		world.updateNeighborsAlways(pos.offset(direction), this, wireOrientation);*/

		Direction plantDir = getPlantDirection(state);
		Direction zombieDir = getZombieDirection(state);
		world.updateNeighborsExcept(pos.offset(plantDir), this, plantDir.getOpposite(), null);
		world.updateNeighborsExcept(pos.offset(zombieDir), this, zombieDir.getOpposite(), null);
	}

	private Direction getPlantDirection(BlockState state) {
		return switch (state.get(FACING)) {
			case SOUTH -> Direction.EAST;
			case WEST -> Direction.SOUTH;
			case EAST -> Direction.NORTH;
			default -> Direction.WEST;
		};
	}

	private Direction getZombieDirection(BlockState state) {
		return switch (state.get(FACING)) {
			case SOUTH -> Direction.WEST;
			case WEST -> Direction.NORTH;
			case EAST -> Direction.SOUTH;
			default -> Direction.EAST;
		};
	}

	@Override
	public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new LawnManagerBlockEntity(pos, state);
	}

	public enum Winner implements StringIdentifiable {
		PLANTS,
		ZOMBIES,
		NONE;

		@Override
		public String asString() {
			return switch (this) {
				case PLANTS -> "plants";
				case ZOMBIES -> "zombies";
				case NONE -> "none";
			};
		}
	}
}
