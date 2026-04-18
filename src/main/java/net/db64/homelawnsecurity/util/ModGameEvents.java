package net.db64.homelawnsecurity.util;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.event.GameEvent;

public class ModGameEvents {
	public static final RegistryEntry.Reference<GameEvent> ZOMBIE_REACH_GOAL = register("zombie_reach_goal", 32);
	public static final RegistryEntry.Reference<GameEvent> TARGET_ZOMBIE_DEATH = register("target_zombie_death", 32);

	private static RegistryEntry.Reference<GameEvent> register(String id) {
		return register(id, 16);
	}

	private static RegistryEntry.Reference<GameEvent> register(String id, int range) {
		return Registry.registerReference(Registries.GAME_EVENT, Identifier.of(HomeLawnSecurity.MOD_ID, id), new GameEvent(range));
	}

	public static void registerGameEvents() {
		HomeLawnSecurity.logRegistration("game events");
	}
}
