package net.db64.homelawnsecurity.item.custom;

import com.google.common.collect.ImmutableMap;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.component.LawnGadgetComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.sound.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Map;

public class LawnGadgetItem extends Item {
	protected static final Map<Block, BlockState> STATES_TURF_TOGGLE;
	protected static final Map<Block, BlockState> STATES_PATH_TOGGLE;
	protected static final Map<Block, BlockState> STATES_PATH_TYPE;
	protected static final Map<Block, BlockState> STATES_MARKER_TURF_TOGGLE;
	protected static final Map<Block, BlockState> STATES_MARKER_PATH_TOGGLE;
	protected static final Map<Block, BlockState> STATES_MARKER_PATH_TYPE;

	public LawnGadgetItem(Settings settings) {
		super(settings);
	}

	@Override
	public boolean canMine(ItemStack stack, BlockState state, World world, BlockPos pos, LivingEntity miner) {
		if (!world.isClient) {
			Random random = miner.getRandom();
			if (miner.isSneaking()) {
				toggleMarkerMode(miner.getStackInHand(Hand.MAIN_HAND), miner);
				world.playSound(null, miner.getBlockPos(), ModSounds.ITEM_LAWN_GADGET_SWITCH, SoundCategory.PLAYERS, 0.5f, (random.nextFloat() * 0.4f) + 1.6f);
			}
			else {
				switchMode(miner.getStackInHand(Hand.MAIN_HAND), miner);
				world.playSound(null, miner.getBlockPos(), ModSounds.ITEM_LAWN_GADGET_SWITCH, SoundCategory.PLAYERS, 0.5f, (random.nextFloat() * 0.4f) + 0.8f);
			}
		}

		return false;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (context.getWorld().isClient) return ActionResult.SUCCESS;

		ItemStack stack = context.getStack();
		ServerWorld world = (ServerWorld) context.getWorld();
		BlockPos pos = context.getBlockPos();

		LawnGadgetComponent component = stack.get(ModDataComponentTypes.LAWN_GADGET);
		if (component == null) {
			// If the component is null, set it to its default value
			//setComponentToDefault(stack);
			// Actually no, play a funny sound and return
			world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK.value(), SoundCategory.PLAYERS, 0.5f, 1.0f);
			sendMessage(context.getPlayer(), Text.translatable(getTranslationKey() + ".use.error"));
			return ActionResult.CONSUME;
		}

		BlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		String mode = component.mode();
		// Which map should it use?
		Map<Block, BlockState> map = switch (mode) {
			case "turf_toggle" -> STATES_TURF_TOGGLE;
			case "path_toggle" -> STATES_PATH_TOGGLE;
			case "path_type" -> STATES_PATH_TYPE;
			case "turf_toggle_marker" -> STATES_MARKER_TURF_TOGGLE;
			case "path_toggle_marker" -> STATES_MARKER_PATH_TOGGLE;
			case "path_type_marker" -> STATES_MARKER_PATH_TYPE;
			default -> STATES_TURF_TOGGLE;
		};

		// Set the block state to the new one
		if (map.containsKey(block)) {
			world.setBlockState(pos, map.get(block));
			world.playSound(null, pos, world.getBlockState(pos).getSoundGroup().getPlaceSound(), SoundCategory.PLAYERS, 1.0f, 1.0f);
		}

		return ActionResult.CONSUME;
	}

	public void switchMode(ItemStack stack, LivingEntity entity) {
		LawnGadgetComponent component = stack.get(ModDataComponentTypes.LAWN_GADGET);
		if (component == null) { // Lawn gadget component is null for some reason, make it the default value
			setComponentToDefault(stack);
			if (entity instanceof PlayerEntity player)
				sendMessage(player, Text.translatable(getTranslationKey() + ".switch.error"));
		}
		else { // Component is not null, continue as normal
			String mode = component.mode();
			mode = switch (mode) { // case "currentMode": mode = "nextMode";
				case "turf_toggle" -> "path_toggle";
				case "path_toggle" -> "path_type";
				case "path_type" -> "turf_toggle";
				case "turf_toggle_marker" -> "path_toggle_marker";
				case "path_toggle_marker" -> "path_type_marker";
				case "path_type_marker" -> "turf_toggle_marker";
				default -> "turf_toggle";
			};
			stack.set(ModDataComponentTypes.LAWN_GADGET, new LawnGadgetComponent(mode));
			if (entity instanceof PlayerEntity player)
				sendMessage(player,
					Text.translatable(getTranslationKey() + ".switch",
						Text.translatable(getTranslationKey() + ".mode." + mode)));
		}
	}

	public void toggleMarkerMode(ItemStack stack, LivingEntity entity) {
		LawnGadgetComponent component = stack.get(ModDataComponentTypes.LAWN_GADGET);
		if (component == null) { // Lawn gadget component is null for some reason, make it the default value
			setComponentToDefault(stack);
			if (entity instanceof PlayerEntity player)
				sendMessage(player, Text.translatable(getTranslationKey() + ".switch.error"));
		}
		else { // Component is not null, continue as normal
			String mode = component.mode();
			mode = switch (mode) { // case "currentMode": mode = "nextMode";
				case "turf_toggle" -> "turf_toggle_marker";
				case "turf_toggle_marker" -> "turf_toggle";
				case "path_toggle" -> "path_toggle_marker";
				case "path_toggle_marker" -> "path_toggle";
				case "path_type" -> "path_type_marker";
				case "path_type_marker" -> "path_type";
				default -> "turf_toggle";
			};
			stack.set(ModDataComponentTypes.LAWN_GADGET, new LawnGadgetComponent(mode));
			if (entity instanceof PlayerEntity player)
				sendMessage(player,
					Text.translatable(getTranslationKey() + ".switch",
						Text.translatable(getTranslationKey() + ".mode." + mode)));
		}
	}

	public void setComponentToDefault(ItemStack stack) {
		stack.set(ModDataComponentTypes.LAWN_GADGET, new LawnGadgetComponent("turf_toggle"));
	}

	private static void sendMessage(PlayerEntity player, Text message) {
		((ServerPlayerEntity) player).sendMessageToClient(message, true);
	}

	public static boolean shouldRevealMarkers(ItemStack stack) {
		LawnGadgetComponent component = stack.get(ModDataComponentTypes.LAWN_GADGET);
		if (component == null) { // Lawn gadget component is null for some reason
			return false;
		}
		else { // Component is not null, continue as normal
			return component.mode().endsWith("_marker");
		}
	}

	static {
		// Blocks

		STATES_TURF_TOGGLE = new ImmutableMap.Builder<Block, BlockState>()

			.put(ModBlocks.LAWN_BLOCK, ModBlocks.UNSODDED_LAWN_BLOCK.getDefaultState())
			.put(ModBlocks.UNSODDED_LAWN_BLOCK, ModBlocks.LAWN_BLOCK.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_BLOCK_1, ModBlocks.ZOMBIE_PATH_BLOCK_1.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_1, ModBlocks.FERTILE_PATH_BLOCK_1.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_BLOCK_2, ModBlocks.ZOMBIE_PATH_BLOCK_2.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_2, ModBlocks.FERTILE_PATH_BLOCK_2.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_BLOCK_CROSS, ModBlocks.ZOMBIE_PATH_BLOCK_CROSS.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS, ModBlocks.FERTILE_PATH_BLOCK_CROSS.getDefaultState())

			.build();

		STATES_PATH_TOGGLE = new ImmutableMap.Builder<Block, BlockState>()

			.put(ModBlocks.LAWN_BLOCK, ModBlocks.FERTILE_PATH_BLOCK_1.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_BLOCK_1, ModBlocks.LAWN_BLOCK.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_BLOCK_2, ModBlocks.LAWN_BLOCK.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_BLOCK_CROSS, ModBlocks.LAWN_BLOCK.getDefaultState())

			.put(ModBlocks.UNSODDED_LAWN_BLOCK, ModBlocks.ZOMBIE_PATH_BLOCK_1.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_1, ModBlocks.UNSODDED_LAWN_BLOCK.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_2, ModBlocks.UNSODDED_LAWN_BLOCK.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS, ModBlocks.UNSODDED_LAWN_BLOCK.getDefaultState())

			.build();

		STATES_PATH_TYPE = new ImmutableMap.Builder<Block, BlockState>()

			.put(ModBlocks.LAWN_BLOCK, ModBlocks.FERTILE_PATH_BLOCK_1.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_BLOCK_1, ModBlocks.FERTILE_PATH_BLOCK_2.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_BLOCK_2, ModBlocks.FERTILE_PATH_BLOCK_CROSS.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_BLOCK_CROSS, ModBlocks.FERTILE_PATH_BLOCK_1.getDefaultState())

			.put(ModBlocks.UNSODDED_LAWN_BLOCK, ModBlocks.ZOMBIE_PATH_BLOCK_1.getDefaultState())

			.put(ModBlocks.ZOMBIE_PATH_BLOCK_1, ModBlocks.ZOMBIE_PATH_BLOCK_2.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_2, ModBlocks.ZOMBIE_PATH_BLOCK_CROSS.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS, ModBlocks.ZOMBIE_PATH_BLOCK_1.getDefaultState())

			.build();

		// Markers

		STATES_MARKER_TURF_TOGGLE = new ImmutableMap.Builder<Block, BlockState>()

			.put(ModBlocks.LAWN_MARKER, ModBlocks.UNSODDED_LAWN_MARKER.getDefaultState())
			.put(ModBlocks.UNSODDED_LAWN_MARKER, ModBlocks.LAWN_MARKER.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_MARKER_1, ModBlocks.ZOMBIE_PATH_MARKER_1.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_1, ModBlocks.FERTILE_PATH_MARKER_1.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_MARKER_2, ModBlocks.ZOMBIE_PATH_MARKER_2.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_2, ModBlocks.FERTILE_PATH_MARKER_2.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_MARKER_CROSS, ModBlocks.ZOMBIE_PATH_MARKER_CROSS.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_CROSS, ModBlocks.FERTILE_PATH_MARKER_CROSS.getDefaultState())

			.build();

		STATES_MARKER_PATH_TOGGLE = new ImmutableMap.Builder<Block, BlockState>()

			.put(ModBlocks.LAWN_MARKER, ModBlocks.FERTILE_PATH_MARKER_1.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_MARKER_1, ModBlocks.LAWN_MARKER.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_MARKER_2, ModBlocks.LAWN_MARKER.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_MARKER_CROSS, ModBlocks.LAWN_MARKER.getDefaultState())

			.put(ModBlocks.UNSODDED_LAWN_MARKER, ModBlocks.ZOMBIE_PATH_MARKER_1.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_1, ModBlocks.UNSODDED_LAWN_MARKER.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_2, ModBlocks.UNSODDED_LAWN_MARKER.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_CROSS, ModBlocks.UNSODDED_LAWN_MARKER.getDefaultState())

			.build();

		STATES_MARKER_PATH_TYPE = new ImmutableMap.Builder<Block, BlockState>()

			.put(ModBlocks.LAWN_MARKER, ModBlocks.FERTILE_PATH_MARKER_1.getDefaultState())

			.put(ModBlocks.FERTILE_PATH_MARKER_1, ModBlocks.FERTILE_PATH_MARKER_2.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_MARKER_2, ModBlocks.FERTILE_PATH_MARKER_CROSS.getDefaultState())
			.put(ModBlocks.FERTILE_PATH_MARKER_CROSS, ModBlocks.FERTILE_PATH_MARKER_1.getDefaultState())

			.put(ModBlocks.UNSODDED_LAWN_MARKER, ModBlocks.ZOMBIE_PATH_MARKER_1.getDefaultState())

			.put(ModBlocks.ZOMBIE_PATH_MARKER_1, ModBlocks.ZOMBIE_PATH_MARKER_2.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_2, ModBlocks.ZOMBIE_PATH_MARKER_CROSS.getDefaultState())
			.put(ModBlocks.ZOMBIE_PATH_MARKER_CROSS, ModBlocks.ZOMBIE_PATH_MARKER_1.getDefaultState())

			.build();
	}
}
