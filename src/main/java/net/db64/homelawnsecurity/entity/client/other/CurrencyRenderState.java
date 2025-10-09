package net.db64.homelawnsecurity.entity.client.other;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.item.ItemStack;

public class CurrencyRenderState extends EntityRenderState {
	public ItemStack stack = ItemStack.EMPTY;
	public float scale = 1f;
	public float alpha = 1f; // HOW DO I FORCE AN ITEM TO RENDER TRANSLUCENT???
	public boolean lit = true;

	public final ItemRenderState itemRenderState = new ItemRenderState();

	public CurrencyRenderState() {

	}
}
