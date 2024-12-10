package net.db64.homelawnsecurity.datagen;

import net.db64.homelawnsecurity.util.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagProvider<Biome> {
	public ModBiomeTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> completableFuture) {
		super(output, RegistryKeys.BIOME, completableFuture);
	}

	@Override
	protected void configure(WrapperLookup arg) {
		/*getOrCreateTagBuilder(ModTags.Biomes.IS_NON_SPARSE_JUNGLE)
			.add(BiomeKeys.JUNGLE)
			.add(BiomeKeys.BAMBOO_JUNGLE);*/
	}
}
