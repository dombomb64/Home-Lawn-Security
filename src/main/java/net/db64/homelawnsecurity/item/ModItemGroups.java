package net.db64.homelawnsecurity.item;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ModItemGroups {
	public static final ItemGroup MISCFEATURES_GROUP = Registry.register(Registries.ITEM_GROUP,
		new Identifier(HomeLawnSecurity.MOD_ID, "homelawnsecurity"),
		FabricItemGroup.builder().displayName(Text.translatable("itemgroup.homelawnsecurity"))
		.icon(() -> new ItemStack(Items.MANGROVE_TRAPDOOR)).entries((displayContext, entries) -> {
			//entries.add(ModBlocks.RUBBER_LOG);
		}).build());

	public static void registerItemGroups() {
		HomeLawnSecurity.LOGGER.debug("Registering item groups for Home Lawn Security (" + HomeLawnSecurity.MOD_ID + ")");
	}
}
