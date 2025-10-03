package net.db64.homelawnsecurity.block.custom;

import net.db64.homelawnsecurity.item.custom.LawnGadgetItem;
import net.db64.homelawnsecurity.util.ModTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class MarkerBlock extends Block implements Waterloggable {
	public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
	public static final Box PARTICLE_DISTANCE = new Box(-16, -16, -16, 16, 16, 16);

	public MarkerBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
	}

	/*@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.scheduledTick(state, world, pos, random);

		if (world instanceof ServerWorld) {
			//HomeLawnSecurity.LOGGER.info("ticking marker block");

			for (ServerPlayerEntity player : world.getEntitiesByClass(ServerPlayerEntity.class, PARTICLE_DISTANCE.offset(pos), EntityPredicates.EXCEPT_SPECTATOR.and(entity -> !entity.canAvoidTraps()))) {
				//HomeLawnSecurity.LOGGER.info("player found by marker block");

				for (ItemStack stack : player.getHandItems()) {
					if (stack.isIn(ModTags.Items.REVEALS_MARKERS)) {
						world.spawnParticles(player, new BlockStateParticleEffect(ModParticles.MARKER, state), true, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, 0, 0, 0, 0);

						//HomeLawnSecurity.LOGGER.info("holding marker item");
						break;
					}
				}
			}

			world.scheduleBlockTick(pos, this, 2);
		}
	}*/

	/*@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		super.onBlockAdded(state, world, pos, oldState, notify);

		if (world instanceof ServerWorld) {
			world.scheduleBlockTick(pos, this, 2);
		}
	}*/

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	/*@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		return ActionResult.CONSUME;
	}*/

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if (!(context instanceof EntityShapeContext entityContext)) return VoxelShapes.empty();

		ItemStack stack = entityContext.heldItem;
		Entity entity = entityContext.getEntity();

		if (entity != null)
			return shouldRevealMarkers(stack, entity.isSneaking())
				? VoxelShapes.fullCube() : VoxelShapes.empty();

		return shouldRevealMarkers(stack)
			? VoxelShapes.fullCube() : VoxelShapes.empty();
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.empty();
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return 1.0f;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
		if (state.get(WATERLOGGED)) {
			tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}
		return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		if (state.get(WATERLOGGED)) {
			return Fluids.WATER.getStill(false);
		}
		return super.getFluidState(state);
	}

	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state, boolean includeData) {
		ItemStack stack = new ItemStack(this.asItem());
		return stack;
	}

	public static boolean shouldRevealMarkers(ItemStack stack, boolean isSneaking) {
		if (stack == null) return false;
		return stack.isIn(ModTags.Items.REVEALS_MARKERS)
			|| (isSneaking && stack.isIn(ModTags.Items.REVEALS_MARKERS_WHILE_SNEAKING));
	}

	public static boolean shouldRevealMarkers(ItemStack stack) {
		return shouldRevealMarkers(stack, false);
	}
}
