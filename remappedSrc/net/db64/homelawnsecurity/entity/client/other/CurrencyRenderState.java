package net.db64.homelawnsecurity.entity.client.other;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CurrencyRenderState extends EntityRenderState {
	@Nullable
	public BakedModel model;
	public ItemStack stack = ItemStack.EMPTY;
	public float scale = 1.0f;

	public CurrencyRenderState() {

	}
}
