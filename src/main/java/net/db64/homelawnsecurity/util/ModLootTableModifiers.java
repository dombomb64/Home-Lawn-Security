package net.db64.homelawnsecurity.util;

import net.db64.homelawnsecurity.item.ModItems;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {
	private static final Identifier ZOMBIE =
		Identifier.ofVanilla("entities/zombie");
	private static final Identifier HUSK =
		Identifier.ofVanilla("entities/husk");
	private static final Identifier DROWNED =
		Identifier.ofVanilla("entities/drowned");
	private static final Identifier ZOMBIE_VILLAGER =
		Identifier.ofVanilla("entities/zombie_villager");

	public static void modifyLootTables() {
		LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
			Identifier value = key.getValue();
			if (ZOMBIE.equals(value) || HUSK.equals(value) || DROWNED.equals(value) || ZOMBIE_VILLAGER.equals(value)) {
				LootPool.Builder poolBuilder = LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1))
					.conditionally(RandomChanceLootCondition.builder(0.5f))
					.with(ItemEntry.builder(ModItems.BRAIN))
					.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

				tableBuilder.pool(poolBuilder.build());
			}
		});
	}
}
