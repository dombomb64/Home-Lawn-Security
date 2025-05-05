package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.IPathBoundEntity;
import net.db64.homelawnsecurity.entity.custom.PlantEntity;
import net.db64.homelawnsecurity.entity.custom.other.PlantSeedPacketPathfindingEntity;
import net.db64.homelawnsecurity.entity.custom.other.SeedPacketPathfindingEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class LawnSeedPacketItem extends SeedPacketItem {
	public LawnSeedPacketItem(EntityType<? extends MobEntity> type, Settings settings) {
		super(type, settings);
	}

	@Override
	protected boolean isPlaceable(BlockPos blockPos, World world, boolean devMode, ServerPlayerEntity player) {
		if (!(world instanceof ServerWorld serverWorld)) return false;

		// Return if there is already a plant there
		if (!devMode && !world.getOtherEntities(null, new Box(0, 0, 0, 1, 1, 1).offset(blockPos.up()), entity -> entity instanceof PlantEntity).isEmpty())
			return false;

		// Return if it's not on turf
		if (!PlantEntity.isPlaceableLawn(blockPos, world)) {
			sendMessage(player, Text.translatable("item.homelawnsecurity.seed_packet.plant.placement.error.turf"));
			return false;
		}

		// Return if it's not next to or on a path
		if (!(devMode || LawnUtil.isAnyPath(blockPos, world))) {
			boolean nearPath = false;
			Iterable<BlockPos> iterable = BlockPos.iterateOutwards(blockPos, 1, 1, 1);
			for (BlockPos pos : iterable) {
				if (LawnUtil.isAnyPath(pos, world)) {
					nearPath = true;
					break;
				}
			}
			if (!nearPath) {
				sendMessage(player, Text.translatable("item.homelawnsecurity.seed_packet.plant.placement.error.near_path"));
				return false;
			}
		}

		// Check if it's too far away from the garden
		SeedPacketPathfindingEntity entity = new PlantSeedPacketPathfindingEntity(ModEntities.Other.PLANT_SEED_PACKET_PATHFINDING, world);
		world.spawnEntity(entity);
		entity.refreshPositionAndAngles(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), entity.getYaw(), entity.getPitch());
		entity.initialize(serverWorld, world.getLocalDifficulty(blockPos), SpawnReason.SPAWN_ITEM_USE, null);

		// Check if it's on a path
		boolean isOffPath = !LawnUtil.isAnyPath(blockPos, world); // We need lawn plants to still be placeable off the path

		// Return if it's too far away from the garden and on a path
		if (!(devMode || isOffPath || isValidPositionOnPath(entity))) {
			sendMessage(player, Text.translatable("item.homelawnsecurity.seed_packet.plant.placement.error.too_far"));
			return false;
		}

		//entity.discard();
		return true;
	}

	public boolean isValidPositionOnPath(SeedPacketPathfindingEntity entity) {
		Path path = entity.findPathToGoal(SeedPacketPathfindingEntity.searchDistanceFromOtherEnd());

		if (path == null) return false;
		else return path.getLength() > SeedPacketPathfindingEntity.searchDistanceFromClose();
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
