package net.db64.homelawnsecurity.mixin;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.component.BagOfCurrencyComponent;
import net.db64.homelawnsecurity.component.CurrencyComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.item.custom.BagOfCurrencyItem;
import net.db64.homelawnsecurity.item.custom.SeedPacketItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow
	private ItemStack currentStack;

	@Final
	@Shadow
	private MinecraftClient client;

	@ModifyVariable(
		method = "renderHeldItemTooltip",
		at = @At(
			value = "STORE"
		),
		ordinal = 0
	)
	public MutableText modifyItemName(MutableText value, DrawContext context) {
		// Seed Packet
		if (currentStack.contains(ModDataComponentTypes.SEED_PACKET)) {
			MutableText entityName = Text.empty();

			// Custom name
			if (currentStack.contains(DataComponentTypes.ENTITY_DATA)) {
				NbtComponent entityDataComponent = currentStack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
				NbtElement customName = entityDataComponent.copyNbt().get("CustomName");
				if (customName != null && client.world != null) {
					try {
						entityName = Text.Serialization.fromJson(customName.toString(), client.world.getRegistryManager());
					} catch (Exception e) {
						HomeLawnSecurity.LOGGER.warn("Failed to parse entity custom name {}", customName.asString(), e);
					}
				}
			}

			// No custom name
			else {
				EntityType<?> entityType;
				if (currentStack.getItem() instanceof SeedPacketItem) {
					entityType = ((SeedPacketItem) currentStack.getItem()).getEntityType(currentStack);
				} else {
					entityType = ((SeedPacketItem) ModItems.SEED_PACKET_PEASHOOTER).getEntityType(currentStack);
				}
				if (entityType != null) {
					entityName = Text.empty().append(entityType.getName());
				}
			}

			value = Text.empty().append(entityName);
			//HomeLawnSecurity.LOGGER.info(value.toString());
		}

		// Currency
		else if (currentStack.contains(ModDataComponentTypes.CURRENCY)) {
			MutableText currencyText = Text.empty();
			CurrencyComponent currencyComponent = currentStack.getOrDefault(ModDataComponentTypes.CURRENCY, new CurrencyComponent(25, "sun"));

			// 25 Sun
			currencyText = currencyText.append(Text.translatable("item.homelawnsecurity.currency.name." + currencyComponent.name(), currencyComponent.amount()));

			value = Text.empty().append(currencyText);
		}

		// Bag of Currency
		else if (currentStack.contains(ModDataComponentTypes.BAG_OF_CURRENCY)) {
			MutableText currencyText = Text.empty();
			BagOfCurrencyComponent bagComponent = currentStack.getOrDefault(ModDataComponentTypes.BAG_OF_CURRENCY, new BagOfCurrencyComponent(25, "sun"));

			// Bag of Sun - 25 Sun
			currencyText = currencyText
				.append(currentStack.getName())
				.append(Text.translatable("item.homelawnsecurity.currency.name.separator"))
				.append(Text.translatable("item.homelawnsecurity.currency.name." + bagComponent.name(), bagComponent.amount()));

			value = Text.empty().append(currencyText);
		}

		return value;
	}
}
