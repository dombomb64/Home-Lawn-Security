package net.db64.homelawnsecurity.entity.custom;

import net.db64.homelawnsecurity.entity.ai.PvzNavigation;
import net.db64.homelawnsecurity.util.LawnUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
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
			ItemEntity itemEntity = dropStack((ServerWorld) getEntityWorld(), getSpawnItem());
			if (itemEntity != null) {
				itemEntity.setVelocity(random.nextFloat() * 0.1f - 0.05f, 0.1f, random.nextFloat() * 0.1f - 0.05f);
			}
		}
	}

	protected void dropSpawnItemOnFeed() {
		ItemEntity itemEntity = dropStack((ServerWorld) getEntityWorld(), getSpawnItem());
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
		if (getEntityWorld().isClient())
			return ActionResult.PASS;

		ItemStack stack = player.getStackInHand(hand);
		if (stack != null && itemDuplicatesSpawnItem(stack)) {
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
			if (LawnUtil.isAnyPath(blockPos, getEntityWorld())) {
				for (int i = 1; i <= LawnUtil.getPathTypeAmount(); i++) {
					if (LawnUtil.isCertainPath(blockPos, getEntityWorld(), i)) {
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
		return LawnUtil.isCertainPath(pos, getEntityWorld(), pathId);
	}

	@Override
	protected EntityNavigation createNavigation(World world) {
		return new PvzNavigation(this, world);
	}

	/*
		NBT
	*/

	@Override
	public void writeCustomData(WriteView view) {
		super.writeCustomData(view);

		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
		}
		view.putInt("path_id", pathId);

		view.putBoolean("should_drop_spawn_item", shouldDropSpawnItem);

		view.put("spawn_item", ItemStack.CODEC, spawnItem);
	}

	@Override
	public void readCustomData(ReadView view) {
		super.readCustomData(view);

		pathId = view.getInt("path_id", 1);
		if (pathId < 1 || pathId > LawnUtil.getPathTypeAmount()) {
			pathId = 1;
			//view.putInt("path_id", pathId);
		}

		shouldDropSpawnItem = view.getBoolean("should_drop_spawn_item", false);

		ItemStack errorItem = new ItemStack(Items.POISONOUS_POTATO);
		errorItem.remove(DataComponentTypes.CONSUMABLE);
		errorItem.remove(DataComponentTypes.FOOD);
		errorItem.set(DataComponentTypes.ITEM_MODEL, Identifier.ofVanilla("barrier"));
		errorItem.set(DataComponentTypes.ITEM_NAME, Text.translatable("error.homelawnsecurity.invalid_spawn_item").setStyle(Style.EMPTY.withColor(16711680)));
		spawnItem = view.read("spawn_item", ItemStack.CODEC).orElse(errorItem);
	}
}
