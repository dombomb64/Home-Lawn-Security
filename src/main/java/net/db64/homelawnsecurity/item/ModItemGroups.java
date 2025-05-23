package net.db64.homelawnsecurity.item;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.component.TooltipComponent;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItemGroups {
	public static final ItemGroup BLOCKS_AND_ITEMS = Registry.register(Registries.ITEM_GROUP,
		Identifier.of(HomeLawnSecurity.MOD_ID, "blocks_and_items"),
		FabricItemGroup.builder().displayName(Text.translatable("itemgroup.homelawnsecurity.blocks_and_items"))
			.icon(() -> new ItemStack(ModBlocks.SODDED_LAWN_BLOCK)).entries((displayContext, entries) -> {
				entries.add(ModBlocks.GARDEN_BLOCK);
				entries.add(ModBlocks.GRAVEYARD_BLOCK);
				entries.add(ModBlocks.SODDED_LAWN_BLOCK);
				entries.add(ModBlocks.UNSODDED_LAWN_BLOCK);

				/*entries.add(ModBlocks.LAWN_BLOCK);

				entries.add(ModBlocks.FERTILE_PATH_BLOCK_1);
				entries.add(ModBlocks.FERTILE_PATH_BLOCK_2);
				entries.add(ModBlocks.FERTILE_PATH_BLOCK_CROSS);

				entries.add(ModBlocks.ZOMBIE_PATH_BLOCK_1);
				entries.add(ModBlocks.ZOMBIE_PATH_BLOCK_2);
				entries.add(ModBlocks.ZOMBIE_PATH_BLOCK_CROSS);

				entries.add(ModBlocks.UNSODDED_LAWN_BLOCK);*/

				entries.add(ModBlocks.GARDEN_MARKER);
				entries.add(ModBlocks.GRAVEYARD_MARKER);
				entries.add(ModBlocks.SODDED_LAWN_MARKER);
				entries.add(ModBlocks.UNSODDED_LAWN_MARKER);

				/*entries.add(ModBlocks.LAWN_MARKER);

				entries.add(ModBlocks.FERTILE_PATH_MARKER_1);
				entries.add(ModBlocks.FERTILE_PATH_MARKER_2);
				entries.add(ModBlocks.FERTILE_PATH_MARKER_CROSS);

				entries.add(ModBlocks.ZOMBIE_PATH_MARKER_1);
				entries.add(ModBlocks.ZOMBIE_PATH_MARKER_2);
				entries.add(ModBlocks.ZOMBIE_PATH_MARKER_CROSS);

				entries.add(ModBlocks.UNSODDED_LAWN_MARKER);*/

				//entries.add(ModBlocks.GRAVEYARD_BLOCK);

				entries.add(ModItems.TURF);

				entries.add(ModItems.BRAIN);

				entries.add(ModBlocks.SUN_SPAWNER);
				entries.add(ModBlocks.BRAINPOWER_BEACON);

				entries.add(ModItems.BAG_OF_SUN);
				entries.add(ModItems.BAG_OF_BRAINPOWER);

				entries.add(ModItems.SUN);
				entries.add(ModItems.BRAINPOWER);

				entries.add(ModItems.SHOVEL);
				entries.add(ModItems.LAWN_GADGET);

				//entries.add(ModBlocks.GRAVEYARD_MARKER);

				entries.add(ModItems.LAWN_MOWER);
				ItemStack tempItem = ModItems.LAWN_MOWER.getDefaultStack().copy();
				tempItem.set(DataComponentTypes.ITEM_NAME, Text.literal("Temporary Item"));
				tempItem.set(ModDataComponentTypes.TOOLTIP, new TooltipComponent(List.of(
					Text.literal("Warning:"),
					Text.literal("Comsumption of WIP Pool Cleaner's fuel may result in instant death."))));
				tempItem.set(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK);
				entries.add(tempItem);

				entries.add(ModItems.TARGET);
			}).build());

	public static final ItemGroup PLANTS = Registry.register(Registries.ITEM_GROUP,
		Identifier.of(HomeLawnSecurity.MOD_ID, "plants"),
		FabricItemGroup.builder().displayName(Text.translatable("itemgroup.homelawnsecurity.plants"))
			.icon(() -> new ItemStack(ModItems.SEED_PACKET_PEASHOOTER)).entries((displayContext, entries) -> {
				entries.add(ModItems.SEED_PACKET_SUNFLOWER);
				entries.add(ModItems.SEED_PACKET_PEASHOOTER);
				entries.add(ModItems.SEED_PACKET_WALL_NUT);
			}).build());

	public static final ItemGroup ZOMBIES = Registry.register(Registries.ITEM_GROUP,
		Identifier.of(HomeLawnSecurity.MOD_ID, "zombies"),
		FabricItemGroup.builder().displayName(Text.translatable("itemgroup.homelawnsecurity.zombies"))
			.icon(() -> new ItemStack(ModItems.SEED_PACKET_BASIC_ZOMBIE)).entries((displayContext, entries) -> {
				entries.add(ModItems.SEED_PACKET_ZOMBIE_GRAVESTONE);
				entries.add(ModItems.SEED_PACKET_BASIC_ZOMBIE);
				entries.add(ModItems.SEED_PACKET_CONEHEAD_ZOMBIE);
			}).build());

	public static void registerItemGroups() {
		HomeLawnSecurity.LOGGER.debug("Registering item groups for Home Lawn Security (" + HomeLawnSecurity.MOD_ID + ")");
	}
}
