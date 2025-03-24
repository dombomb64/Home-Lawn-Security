package net.db64.homelawnsecurity.entity.custom;

/**
 * This interface should be implemented on any entity that is part of the mod's PvZ gameplay.
 */
public interface IPvzEntity {
	/**
	 * In PvZ, a zombie has 300 health. In Minecraft, a zombie has 20 health.
	 * This constant exists to make it easier to copy health stats from the PvZ wiki.
	 */
	float HEALTH_SCALE = 0.1f;
}
