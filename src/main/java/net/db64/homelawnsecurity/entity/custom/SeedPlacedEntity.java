package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.entity.ai.PvzNavigation;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class SeedPlacedEntity extends PathAwareEntity implements IPvzEntity, IPathBoundEntity {
	public boolean shouldDropSpawnItem = false;
	public ItemStack spawnItem = ItemStack.EMPTY;

	protected SeedPlacedEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
		super(entityType, world);
	}

	/*
		DROPS
	*/

	@Override
	protected void drop(ServerWorld world, DamageSource damageSource) {
		super.drop(world, damageSource);

		if (isDead())
			dropSpawnItemOnDeath();
	}

	protected void dropSpawnItemOnDeath() {
		if (shouldDropSpawnItem) {
			ItemEntity itemEntity = dropStack((ServerWorld) getWorld(), getSpawnItem());
			if (itemEntity != null) {
				itemEntity.setVelocity(random.nextFloat() * 0.1f - 0.05f, 0.1f, random.nextFloat() * 0.1f - 0.05f);
			}
		}
	}

	protected void dropSpawnItemOnFeed() {
		ItemEntity itemEntity = dropStack((ServerWorld) getWorld(), getSpawnItem());
		if (itemEntity != null) {
			itemEntity.setVelocity(random.nextFloat() * 0.1f - 0.05f, 0.1f, random.nextFloat() * 0.1f - 0.05f);
		}
		playFeedSound();
	}

	public ItemStack getSpawnItem() {
		if (spawnItem != null) {
			return spawnItem;
		}
		return ItemStack.EMPTY.copy();
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (getWorld().isClient())
			return ActionResult.PASS;

		ItemStack stack = player.getStackInHand(hand);
		if (itemDuplicatesSpawnItem(stack)) {
			dropSpawnItemOnFeed();
			stack.decrementUnlessCreative(1, player);

			return ActionResult.SUCCESS_SERVER;
		}

		return ActionResult.PASS;
	}

	protected abstract boolean itemDuplicatesSpawnItem(ItemStack stack);

	protected abstract void playFeedSound();

	/*
		IPATHBOUNDENTITY STUFF
	 */

	public int pathId = 1;

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
		EntityData result = super.initialize(world, difficulty, spawnReason, entityData);

		//HomeLawnSecurity.LOGGER.info("new seed-placed entity");
		BlockPos pos = getSteppingPos();
		Iterable<BlockPos> iterable = BlockPos.iterateOutwards(pos, 5, 5, 5);
		for (BlockPos blockPos : iterable) {
			//HomeLawnSecurity.LOGGER.info("block pos {} is:", blockPos.toShortString());
			if (LawnUtil.isAnyPath(blockPos, getWorld())) {
				for (int i = 1; i <= LawnUtil.getPathTypeAmount(); i++) {
					if (LawnUtil.isCertainPath(blockPos, getWorld(), i)) {
						//HomeLawnSecurity.LOGGER.info("path {}", i);
						pathId = i;
					}
				}
				break;
			}
			//HomeLawnSecurity.LOGGER.info("not a path");
		}

		return result;
	}

	public int getPathId() {
		return pathId;
	}

	public void setPathId(int pathId) {
		this.pathId = pathId;
	}

	public boolean isThisPath(BlockPos pos) {
		return isCertainPath(pos, pathId);
	}

	public boolean isCertainPath(BlockPos pos, int pathId) {
		return LawnUtil.isCertainPath(pos, getWorld(), pathId);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PvzNavigation(this, world);
	}

	/*
		NBT
	*/

	@Override
	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);

		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
		}
		nbt.putInt("path_id", pathId);

		nbt.putBoolean("should_drop_spawn_item", shouldDropSpawnItem);

		nbt.put("spawn_item", spawnItem.toNbt(getRegistryManager()));
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);

		pathId = nbt.getInt("path_id").orElse(1);
		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
			nbt.putInt("path_id", pathId);
		}

		shouldDropSpawnItem = nbt.getBoolean("should_drop_spawn_item").orElse(false);

		spawnItem = nbt.get("spawn_item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
	}
}
