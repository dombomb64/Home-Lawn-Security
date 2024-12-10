package net.db64.homelawnsecurity.entity;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDamageTypes implements DamageTypes {
	public static final RegistryKey<DamageType> PEA = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(HomeLawnSecurity.MOD_ID, "pea"));
	public static final RegistryKey<DamageType> ZOMBIE_EAT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(HomeLawnSecurity.MOD_ID, "zombie_eat"));
	public static final RegistryKey<DamageType> ZOMBIE_HEADLESS = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(HomeLawnSecurity.MOD_ID, "zombie_headless"));

	/*public static DamageSource of(World world, RegistryKey<DamageType> key) {
		return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getEntry(key.));
	}*/

	public static void registerModDamageTypes() {
		HomeLawnSecurity.LOGGER.debug("Registering damage types for " + HomeLawnSecurity.MOD_ID);
	}
}
