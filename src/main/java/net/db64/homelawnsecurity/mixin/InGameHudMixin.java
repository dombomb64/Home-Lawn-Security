package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.component.BagOfCurrencyComponent;
import net.db64.homelawnsecurity.component.CurrencyComponent;
import net.db64.homelawnsecurity.component.ModDataComponentTypes;
import net.db64.homelawnsecurity.item.ModItems;
import net.db64.homelawnsecurity.item.custom.SeedPacketItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TypedEntityData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	@Shadow
	private ItemStack currentStack;

	@Final
	@Shadow
	private MinecraftClient client;

	@Shadow
	protected abstract void renderHotbarItem(DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed);

	@Unique
	private boolean renderingItemsBehindSelector = false;

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
			NbtElement customName = null;

			// Custom name
			if (currentStack.contains(DataComponentTypes.ENTITY_DATA)) {
				TypedEntityData<EntityType<?>> entityDataComponent = currentStack.get(DataComponentTypes.ENTITY_DATA);
				if (entityDataComponent != null) {
					customName = entityDataComponent.copyNbtWithoutId().get("CustomName");
					if (customName != null && client.world != null) {
						try {
							entityName = Text.empty().append(TextCodecs.CODEC.parse(client.world.getRegistryManager().getOps(NbtOps.INSTANCE), customName).getOrThrow());
						} catch (Exception e) {
							HomeLawnSecurity.LOGGER.warn("Failed to parse entity custom name {}", customName.asString(), e);
						}
					}
				}
			}

			// No custom name
			if (customName == null) {
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

	@ModifyVariable(
		method = "renderHotbarItem",
		at = @At(
			value = "HEAD",
			ordinal = 0
		),
		argsOnly = true
	)
	public ItemStack changeModelInHotbar(ItemStack stack) {
		ItemStack newStack = stack.copy();

		/*// Cancel rendering if the item is not selected and is a seed packet
		if (newStack.get(ModDataComponentTypes.SEED_PACKET) != null && !stack.equals(currentStack) && !renderingItemsBehindSelector) {
			newStack.set(DataComponentTypes.ITEM_MODEL, Identifier.of(HomeLawnSecurity.MOD_ID, "blank"));
			return newStack;
		}*/

		// It's a little jank but Wii Ball
		CustomModelDataComponent component = newStack.get(DataComponentTypes.CUSTOM_MODEL_DATA);
		String tag = HomeLawnSecurity.MOD_ID + ":hotbar";
		if (component == null) {
			component = new CustomModelDataComponent(List.of(), List.of(), List.of(tag), List.of());
		}
		else {
			ArrayList<String> strings = new ArrayList<>(component.strings());
			strings.add(tag);
			component = new CustomModelDataComponent(component.floats(), component.flags(), List.copyOf(strings), component.colors());
		}
		newStack.set(DataComponentTypes.CUSTOM_MODEL_DATA, component);
		return newStack;
	}

	@Inject(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
			ordinal = 0,
			shift = At.Shift.AFTER
		)
	)
	public void renderItemsBehindSelector(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci, @Local(ordinal = 0) PlayerEntity playerEntity) {
		int i = context.getScaledWindowWidth() / 2;
		int l = 1;
		int selectedSlot = playerEntity.getInventory().getSelectedSlot();

		renderingItemsBehindSelector = true;
		for (int m = 0; m < 9; m++) {
			ItemStack stack = playerEntity.getInventory().getStack(m);
			// Don't render here if it's selected
			//if (stack.get(ModDataComponentTypes.SEED_PACKET) == null || m == selectedSlot) {
			if (m == selectedSlot) {
				l++; // Increment the seed
				continue;
			}

			int n = i - 90 + m * 20 + 2;
			int o = context.getScaledWindowHeight() - 16 - 3;
			this.renderHotbarItem(context, n, o, tickCounter, playerEntity, stack, l++);
		}
		renderingItemsBehindSelector = false;
	}

	@WrapWithCondition(
		method = "renderHotbar",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/client/render/RenderTickCounter;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V",
			ordinal = 0
		)
	)
	public boolean cancelItemRendering(InGameHud instance, DrawContext context, int x, int y, RenderTickCounter tickCounter, PlayerEntity player, ItemStack stack, int seed, @Local(ordinal = 4) int m, @Local(ordinal = 0) PlayerEntity playerEntity, @Local(ordinal = 3) LocalIntRef l) {
		// Only render here if it's selected
		//boolean result = stack.get(ModDataComponentTypes.SEED_PACKET) == null || m == playerEntity.getInventory().getSelectedSlot();
		boolean result = m == playerEntity.getInventory().getSelectedSlot();
		if (!result) l.set(l.get()+1); // Increment the seed
		return result;
	}
}
