package com.mg.studio.alice.myframework.particle;

import com.mg.studio.alice.game.resoucredata.RM;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.engine.MGParticleManager;


public class MGGalaxyParticle {

	
	
static final float PARTICLE_PER_SPAWN = 10;
	

	static final int MIN_SIZE = 27;
	static final int MAX_SIZE = 47;
	static final float MIN_SPEED = 50f;
	static final float MAX_SPEED = 70f;
	static final float MIN_GRAVITY = 0.2f;
	static final float MAX_GRAVITY = 0.6f;
	static final float POSITION_VARIANCE = 0.0f;
	static final float EXPLODE_FORCE_VARIANCE_X = 10.0f;
	static final float EXPLODE_MIN_FORCE_Y = -10.0f;
	static final float EXPLODE_MAX_FORCE_Y = 10.0f;
	static final float EXPLODE_TARGET_FORCE_VARIANCE_X = 80f;
	static final float EXPLODE_TARGET_FORCE_VARIANCE_Y = 80f;
	
	
	
	MGParticleManager particleManager;
	ParticleConfig config;
	public MGGalaxyParticle(MGImage particleImage) {
		initConfig();
		particleManager = new MGParticleManager(new MajorParticle(), 500,
				particleImage);
	}
	private void initConfig() {
		//thiet ke cac thong so particle
		config = new ParticleConfig();
		config.setRatio(RM.rate);
		config.setExplodeForceVarianceX(EXPLODE_FORCE_VARIANCE_X);
		config.setExplodeMinForceY(EXPLODE_MIN_FORCE_Y);
		config.setExplodeMaxForceY(EXPLODE_MAX_FORCE_Y);
		config.setExplodeTargetForceVarianceX(EXPLODE_TARGET_FORCE_VARIANCE_X);
		config.setExplodeTargetForceVarianceY(EXPLODE_TARGET_FORCE_VARIANCE_Y);
		config.setInertia(-0.6f);
		config.setMinColor(1f, 1f, .94f, .3f);
		config.setMaxColor(1f, 1f, .94f, .3f);
		config.setMinSize(MIN_SIZE);
		config.setMaxSize(MAX_SIZE);
		config.setMaxGravity(MAX_GRAVITY);
		config.setMinGravity(MIN_GRAVITY);
		config.setPositionVariance(POSITION_VARIANCE);
		config.setMinAlphaSpeed(0.02f);
		config.setMaxAlphaSpeed(0.08f);
		config.setMaxAngleSpeed(0.5f);
		config.setMinAngleSpeed(0.2f);
	}

	public void spawn(float x, float y) {
		MajorParticle particle = (MajorParticle) particleManager
				.availableParticle();
		if (particle == null)
			return;
		particle.initMajorValue(config, x, y);
		for (int i = 0; i < PARTICLE_PER_SPAWN; i++) {
			particle.initRanDomAllValue(config);
			particleManager.increaseActiveCount();
			particle = (MajorParticle) particleManager.availableParticle();
			if (particle == null)
				break;
		}
	}

	public void update() {
		if (particleManager != null) {
			particleManager.update();
		}

	}

	public void pain(MGGraphic g) {
		if (particleManager != null) {

			particleManager.paint(g);
		}
	}

}
