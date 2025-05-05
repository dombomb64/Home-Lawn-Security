package net.db64.homelawnsecurity.item.custom;

import com.mojang.serialization.MapCodec;
import net.db64.homelawnsecurity.entity.ModEntities;
import net.db64.homelawnsecurity.entity.custom.other.TargetZombieEntity;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class TargetItem extends SeedPacketItem {
	private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAP_CODEC = Registries.ENTITY_TYPE.getCodec().fieldOf("id");
	private final EntityType<?> type;

	public TargetItem(EntityType<? extends MobEntity> type, Settings settings) {
		super(ModEntities.Other.TARGET_ZOMBIE, settings);
		this.type = type;
	}

	@Override
	protected boolean isPlaceable(BlockPos blockPos, World world, boolean devMode, ServerPlayerEntity player) {
		return TargetZombieEntity.isPlaceable(blockPos, world);
	}

	@Override
	public void playPlaceSound(World world, BlockPos pos) {

	}

	public static void playBuzzerSound(World world, BlockPos pos) {
		world.playSound(null, pos, ModSounds.RANDOM_BUZZER, SoundCategory.NEUTRAL);
	}

	/*@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		if (world.isClient) {
			return ActionResult.SUCCESS;
		} else {
			ItemStack itemStack = context.getStack();
			BlockPos blockPos = context.getBlockPos();
			Direction direction = context.getSide();
			BlockState blockState = world.getBlockState(blockPos);
			if (world.getBlockEntity(blockPos) instanceof Spawner spawner) {
				EntityType<?> entityType = this.getEntityType(itemStack);
				spawner.setEntityType(entityType, world.getRandom());
				world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_ALL);
				world.emitGameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
				itemStack.decrement(1);
				return ActionResult.SUCCESS;
			} else {
				BlockPos blockPos2;
				if (blockState.getCollisionShape(world, blockPos).isEmpty()) {
					blockPos2 = blockPos;
				} else {
					blockPos2 = blockPos.offset(direction);
				}

				// Invalid spawn position
				if (!TargetZombieEntity.isPlaceable(blockPos, world)) {
					playBuzzerSound(world, blockPos);
					sendMessage(context.getPlayer(), Text.translatable("item.homelawnsecurity.target.placement.error"));
					return ActionResult.SUCCESS;
				}

				PlayerEntity player = context.getPlayer();
				EntityType<?> entityType = this.getEntityType(itemStack);
				Entity entity = entityType.spawnFromItemStack(
					(ServerWorld)world,
					itemStack,
					player,
					blockPos2,
					SpawnReason.SPAWN_ITEM_USE,
					true,
					!Objects.equals(blockPos, blockPos2) && direction == Direction.UP
				);
				if (entity != null) {
					if (entity instanceof SeedPlacedEntity seedPlacedEntity) {
						if (player != null && !player.isCreative()) {
							seedPlacedEntity.shouldDropSpawnItem = true;
						}
						seedPlacedEntity.spawnItem = stack.copy();
					}
					world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
				}

				return ActionResult.SUCCESS;
			}
		}
	}*/

	@Override
	protected boolean setCooldownAndCurrencyAndReturnIfInsufficient(@Nullable PlayerEntity player, ItemStack stack, World world, BlockPos usePos) {
		return true;
	}

	/*@Override
	protected void setEntityData(ItemStack stack, Entity entity, @Nullable Entity source) {
		if (entity instanceof SeedPlacedEntity seedPlacedEntity) {
			if (source instanceof PlayerEntity player && player.isCreative()) {
				seedPlacedEntity.shouldDropSpawnItem = true;
			}
			else if (!(source instanceof PlayerEntity)) {
				seedPlacedEntity.shouldDropSpawnItem = true;
			}
			seedPlacedEntity.spawnItem = stack.copyWithCount(1);
		}
	}*/

	protected static void sendMessage(PlayerEntity player, Text message) {
		((ServerPlayerEntity) player).sendMessageToClient(message, true);
	}

	public EntityType<?> getEntityType(ItemStack stack) {
		NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
		return !nbtComponent.isEmpty() ? (EntityType)nbtComponent.get(ENTITY_TYPE_MAP_CODEC).result().orElse(this.type) : this.type;
	}

	@Override
	public Predicate<ItemStack> getBagPredicate() {
		return null;
	}
}
