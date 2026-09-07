package net.db64.homelawnsecurity.particle;

import net.db64.homelawnsecurity.HomeLawnSecurity;
import net.db64.homelawnsecurity.particle.custom.BonkParticle;
import net.db64.homelawnsecurity.particle.custom.EntityParticleRenderer;
import net.db64.homelawnsecurity.particle.custom.MarkerParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleRendererRegistry;
import net.minecraft.client.particle.ParticleTextureSheet;

public class ModParticlesClient {
	public static final ParticleTextureSheet SHEET_ENTITY = new ParticleTextureSheet("pvz_entity");

	public static void registerParticles() {
		HomeLawnSecurity.logRegistration("client-side particle data");

		ParticleRendererRegistry.register(SHEET_ENTITY, EntityParticleRenderer::new);
		ParticleFactoryRegistry particleFactoryRegistry = ParticleFactoryRegistry.getInstance();
		particleFactoryRegistry.register(ModParticles.MARKER, new MarkerParticle.Factory());
		particleFactoryRegistry.register(ModParticles.BONK, BonkParticle.Factory::new);
	}
}
