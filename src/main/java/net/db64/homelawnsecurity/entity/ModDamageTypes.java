package net.db64.homelawnsecurity.entity;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes implements DamageTypes {
	public static final RegistryKey<DamageType> PEA = register("pea");
	public static final RegistryKey<DamageType> CHOMPER_SWALLOW = register("chomper_swallow");
	public static final RegistryKey<DamageType> ZOMBIE_EAT = register("zombie_eat");
	public static final RegistryKey<DamageType> ZOMBIE_HEADLESS = register("zombie_headless");

	/*public static DamageSource of(World world, RegistryKey<DamageType> key) {
		return new DamageSource(world.getRegistryManager().getOrThrow(RegistryKeys.DAMAGE_TYPE).getEntry(key.));
	}*/

	private static RegistryKey<DamageType> register(String name) {
		return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(HomeLawnSecurity.MOD_ID, name));
	}

	public static void registerDamageTypes() {
		HomeLawnSecurity.logRegistration("damage types");
	}
}
