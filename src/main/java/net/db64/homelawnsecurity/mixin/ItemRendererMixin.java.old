package net.db64.homelawnsecurity.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.item.ModItems;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	@ModifyVariable(
		method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;Z)V",
		at = @At(
			value = "HEAD"
		),
		ordinal = 0,
		argsOnly = true
	)
	public BakedModel useDavesPanModel(BakedModel value, @Local(ordinal = 0) ItemStack stack, @Local(ordinal = 0) ModelTransformationMode transformationMode) {
		if (stack.isOf(ModItems.DAVES_PAN) && transformationMode == ModelTransformationMode.HEAD) {
			return ((ItemRendererAccessor) this).mccourse$getModels().getModel(Identifier.of(HomeLawnSecurity.MOD_ID, "daves_pan_3d"));
		}
		return value;
	}
}
