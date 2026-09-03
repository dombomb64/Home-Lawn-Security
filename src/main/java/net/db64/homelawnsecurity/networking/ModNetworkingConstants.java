package net.db64.homelawnsecurity.networking;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.minecraft.util.Identifier;

public class ModNetworkingConstants {
	// Client-bound
	public static final Identifier SNAP_LOOK_DIR_PACKET_ID = Identifier.of(HomeLawnSecurity.MOD_ID, "snap_look_dir");
	public static final Identifier CHANGE_ENTITY_STATE_PACKET_ID = Identifier.of(HomeLawnSecurity.MOD_ID, "change_entity_state");
	public static final Identifier ENTITY_DAMAGED_PACKET_ID = Identifier.of(HomeLawnSecurity.MOD_ID, "entity_damaged");
	//public static final Identifier INIT_ANIMATIONS_PACKET_ID = Identifier.of(HomeLawnSecurity.MOD_ID, "init_animations");

	// Server-bound
	public static final Identifier LAWN_GADGET_SWITCH_MODE_PACKET_ID = Identifier.of(HomeLawnSecurity.MOD_ID, "lawn_gadget_switch_mode");
}
