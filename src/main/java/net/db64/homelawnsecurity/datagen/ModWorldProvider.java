package net.db64.homelawnsecurity.datagen;

import java.util.concurrent.CompletableFuture;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class ModWorldProvider extends FabricDynamicRegistryProvider {
	public ModWorldProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected void configure(WrapperLookup registries, Entries entries) {
		entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
		entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
	}

	@Override
	public String getName() {
		return HomeLawnSecurity.MOD_ID;
	}
}
