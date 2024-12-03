package net.db64.homelawnsecurity.item.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.Spawner;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class LawnMowerItem extends Item {
	private static final MapCodec<EntityType<?>> ENTITY_TYPE_MAP_CODEC = Registries.ENTITY_TYPE.getCodec().fieldOf("id");
	private final EntityType<?> type;

	public LawnMowerItem(EntityType<? extends MobEntity> type, Settings settings) {
		super(settings);
		this.type = type;
	}

	@Override
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

				EntityType<?> entityType = this.getEntityType(itemStack);
				if (entityType.spawnFromItemStack(
					(ServerWorld)world,
					itemStack,
					context.getPlayer(),
					blockPos2,
					SpawnReason.SPAWN_ITEM_USE,
					true,
					!Objects.equals(blockPos, blockPos2) && direction == Direction.UP
				)
					!= null) {
					itemStack.decrement(1);
					world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
				}

				return ActionResult.SUCCESS;
			}
		}
	}

	public EntityType<?> getEntityType(ItemStack stack) {
		NbtComponent nbtComponent = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
		return !nbtComponent.isEmpty() ? (EntityType)nbtComponent.get(ENTITY_TYPE_MAP_CODEC).result().orElse(this.type) : this.type;
	}
}
