package net.db64.homelawnsecurity.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record IgnoredPlacementRulesComponent(boolean checkWhenHeld, boolean cost, boolean cooldown, boolean occupied, boolean nearPath, boolean teamSide, boolean turf) {
	public static final Codec<IgnoredPlacementRulesComponent> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				Codec.BOOL.optionalFieldOf("check_when_held", true).forGetter(IgnoredPlacementRulesComponent::checkWhenHeld),
				Codec.BOOL.optionalFieldOf("cost", false).forGetter(IgnoredPlacementRulesComponent::cost),
				Codec.BOOL.optionalFieldOf("cooldown", false).forGetter(IgnoredPlacementRulesComponent::cooldown),
				Codec.BOOL.optionalFieldOf("occupied", false).forGetter(IgnoredPlacementRulesComponent::occupied),
				Codec.BOOL.optionalFieldOf("near_path", false).forGetter(IgnoredPlacementRulesComponent::nearPath),
				Codec.BOOL.optionalFieldOf("team_side", false).forGetter(IgnoredPlacementRulesComponent::teamSide),
				Codec.BOOL.optionalFieldOf("turf", false).forGetter(IgnoredPlacementRulesComponent::turf)
			)
			.apply(instance, IgnoredPlacementRulesComponent::new)
	);
	public static final PacketCodec<RegistryByteBuf, IgnoredPlacementRulesComponent> PACKET_CODEC = PacketCodec.tuple(
		PacketCodecs.BOOLEAN,
		IgnoredPlacementRulesComponent::checkWhenHeld,
		PacketCodecs.BOOLEAN,
		IgnoredPlacementRulesComponent::cost,
		PacketCodecs.BOOLEAN,
		IgnoredPlacementRulesComponent::cooldown,
		PacketCodecs.BOOLEAN,
		IgnoredPlacementRulesComponent::occupied,
		PacketCodecs.BOOLEAN,
		IgnoredPlacementRulesComponent::nearPath,
		PacketCodecs.BOOLEAN,
		IgnoredPlacementRulesComponent::teamSide,
		PacketCodecs.BOOLEAN,
		IgnoredPlacementRulesComponent::turf,
		IgnoredPlacementRulesComponent::new
	);

	public static class Builder {
		private boolean checkWhenHeld = true;
		private boolean cost = false;
		private boolean cooldown = false;
		private boolean occupied = false;
		private boolean nearPath = false;
		private boolean teamSide = false;
		private boolean turf = false;

		public IgnoredPlacementRulesComponent.Builder checkWhenHeld(boolean checkWhenHeld) {
			this.checkWhenHeld = checkWhenHeld;
			return this;
		}

		public IgnoredPlacementRulesComponent.Builder cost(boolean cost) {
			this.cost = cost;
			return this;
		}

		public IgnoredPlacementRulesComponent.Builder cooldown(boolean cooldown) {
			this.cooldown = cooldown;
			return this;
		}

		public IgnoredPlacementRulesComponent.Builder occupied(boolean occupied) {
			this.occupied = occupied;
			return this;
		}

		public IgnoredPlacementRulesComponent.Builder nearPath(boolean nearPath) {
			this.nearPath = nearPath;
			return this;
		}

		public IgnoredPlacementRulesComponent.Builder teamSide(boolean teamSide) {
			this.teamSide = teamSide;
			return this;
		}

		public IgnoredPlacementRulesComponent.Builder turf(boolean turf) {
			this.turf = turf;
			return this;
		}

		public IgnoredPlacementRulesComponent build() {
			return new IgnoredPlacementRulesComponent(
				this.checkWhenHeld,
				this.cost,
				this.cooldown,
				this.occupied,
				this.nearPath,
				this.teamSide,
				this.turf
			);
		}
	}
}
