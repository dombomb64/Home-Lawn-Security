package net.db64.homelawnsecurity.block.entity;

import net.db64.homelawnsecurity.block.custom.LawnManagerBlock;
import net.db64.homelawnsecurity.util.ModGameEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import net.minecraft.world.event.listener.GameEventListener;

public class LawnManagerBlockEntity extends BlockEntity implements GameEventListener.Holder<LawnManagerBlockEntity.Listener> {
	private final Listener eventListener;

	public LawnManagerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.LAWN_MANAGER, pos, state);
		eventListener = new Listener(new BlockPositionSource(pos));
	}

	@Override
	public Listener getEventListener() {
		return eventListener;
	}

	public class Listener implements GameEventListener {
		private final PositionSource positionSource;

		public Listener(PositionSource positionSource) {
			this.positionSource = positionSource;
		}

		@Override
		public PositionSource getPositionSource() {
			return positionSource;
		}

		@Override
		public int getRange() {
			return 32;
		}

		@Override
		public boolean listen(ServerWorld world, RegistryEntry<GameEvent> event, GameEvent.Emitter emitter, Vec3d emitterPos) {
			if (event.matchesKey(ModGameEvents.ZOMBIE_REACH_GOAL.registryKey())) {
				// Zombies win
				//HomeLawnSecurity.LOGGER.info("Zombies Win!");
				((LawnManagerBlock) world.getBlockState(pos).getBlock()).zombieVictory(world.getBlockState(pos), world, pos);
				return true;
			}
			else if (event.matchesKey(ModGameEvents.TARGET_ZOMBIE_DEATH.registryKey())) {
				// Plant score increases
				BlockState state = world.getBlockState(pos);
				//HomeLawnSecurity.LOGGER.info("Plants have killed {}/3 target zombies!", state.get(GameManagerBlock.PLANT_SCORE) + 1);
				if (state.get(LawnManagerBlock.PLANT_SCORE) < 3) {
					world.setBlockState(pos, state.with(LawnManagerBlock.PLANT_SCORE, state.get(LawnManagerBlock.PLANT_SCORE) + 1));
				}
				if (world.getBlockState(pos).get(LawnManagerBlock.PLANT_SCORE) == 3) {
					// Plants win
					//HomeLawnSecurity.LOGGER.info("Plants Win!");
					((LawnManagerBlock) world.getBlockState(pos).getBlock()).plantVictory(world.getBlockState(pos), world, pos);
				}
				return true;
			}
			return false;
		}
	}
}
