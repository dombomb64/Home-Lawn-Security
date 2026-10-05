package net.db64.homelawnsecurity.item.custom;

import net.db64.homelawnsecurity.component.IgnoredPlacementRulesComponent;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.other.SeedPacketPathfindingEntity;
import net.db64.homelawnsecurity.entity.custom.ZombieEntity;
import net.db64.homelawnsecurity.entity.custom.other.ZombieSeedPacketPathfindingEntity;
import net.db64.homelawnsecurity.entity.custom.zombie.ZombieGravestoneEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class ZombieSeedPacketItem extends SeedPacketItem {
	public ZombieSeedPacketItem(EntityType<? extends MobEntity> type, Settings settings) {
		super(type, settings);
	}

	@Override
	protected boolean isPlaceable(BlockPos blockPos, World world, ServerPlayerEntity player, IgnoredPlacementRulesComponent ignores) {
		if (!(world instanceof ServerWorld serverWorld)) return false;

		// Return if it's not on a path
		if (!(ignores.nearPath() || ZombieEntity.isPlaceable(blockPos, world))) {
			sendMessage(player, Text.translatable("item.homelawnsecurity.seed_packet.zombie.placement.error.path"));
			return false;
		}

		// Check if it's too close to the goal
		SeedPacketPathfindingEntity entity = new ZombieSeedPacketPathfindingEntity(ModEntities.Other.ZOMBIE_SEED_PACKET_PATHFINDING, world);
		world.spawnEntity(entity);
		entity.refreshPositionAndAngles(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), entity.getYaw(), entity.getPitch());
		entity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.SPAWN_ITEM_USE, null);

		// Return if it's too close to the garden
		if (!(ignores.teamSide() || isValidPositionOnPath(entity))) {
			sendMessage(player, Text.translatable("item.homelawnsecurity.seed_packet.zombie.placement.error.too_close"));
			return false;
		}

		//entity.discard();
		return true;
	}

	public boolean isValidPositionOnPath(SeedPacketPathfindingEntity entity) {
		Path path = entity.findPathToStart(SeedPacketPathfindingEntity.searchDistanceFromClose());

		if (path == null || !path.reachesTarget()) return false;
		else return path.getLength() <= SeedPacketPathfindingEntity.searchDistanceFromClose();
	}

	@Override
	public Predicate<ItemStack> getBagPredicate() {
		return BRAINPOWER_BAG_PREDICATE;
	}

	@Override
	public void playPlaceSound(Entity placed, BlockPos pos) {
		World world = placed.getEntityWorld();
		if (world.getFluidState(pos.down()).isIn(FluidTags.WATER))
			world.playSound(null, pos, ModSounds.ENTITY_ZOMBIE_PLACE_WATER, placed.getSoundCategory());
		else
			world.playSound(null, pos, ModSounds.ENTITY_ZOMBIE_PLACE, placed.getSoundCategory());
	}

	@Override
	public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
		// Reject clients, return to servr
		World world = player.getEntityWorld();
		if (!(world instanceof ServerWorld)) {
			return ActionResult.SUCCESS;
		}

		if (entity instanceof ZombieGravestoneEntity && !(getEntityType(stack).equals(ModEntities.Zombie.ZOMBIE_GRAVESTONE)))
			return useSeedPacket(stack, player, entity.getSteppingPos(), (ServerWorld) world, Direction.UP);

		return ActionResult.PASS;
	}
}
