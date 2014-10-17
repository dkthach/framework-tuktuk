/**
 * 
 */
package com.mg.studio.alice.myframework.particle;

import android.opengl.GLES20;

import com.mg.studio.alice.game.resoucredata.RM;
import com.mg.studio.engine.MGGraphic;
import com.mg.studio.engine.MGImage;
import com.mg.studio.engine.MGParticleManager;

public class MGDropParticle {
	static final float PARTICLE_PER_SPAWN = 10;
	static final int MIN_SIZE = 50;
	static final int MAX_SIZE = 60;
	static final float MIN_SPEED = 1.0f;
	static final float MAX_SPEED = 2.0f;
	static final float MIN_GRAVITY = 0.3f;
	static final float MAX_GRAVITY = 0.4f;
	static final float POSITION_VARIANCE = 10.0f;
	static final float EXPLODE_FORCE_VARIANCE_X = 2.0f;
	static final float EXPLODE_MIN_FORCE_Y = 0f;
	static final float EXPLODE_MAX_FORCE_Y = 5.0f;
	static final float EXPLODE_TARGET_FORCE_VARIANCE_X = 5.0f;
	static final float EXPLODE_TARGET_FORCE_VARIANCE_Y = 5.0f;
	
	
	MGParticleManager particleManager;
	ParticleConfig config;
	public MGDropParticle(MGImage particleImage) {
		initConfig();
		particleManager = new MGParticleManager(new MajorParticle(), 30,
				particleImage);
		//khong hoa tron mau
		particleManager.setBlendFunc(GLES20.GL_ONE,GLES20.GL_ONE_MINUS_SRC_ALPHA );
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
		config.setInertia(0.2f);
		config.setMinColor(1f,1f, 1f, 1f);
		config.setMaxColor(1f, 1f, 1f, 1f);
		config.setMinSize(MIN_SIZE);
		config.setMaxSize(MAX_SIZE);
		config.setMaxGravity(MAX_GRAVITY);
		config.setMinGravity(MIN_GRAVITY);
		config.setPositionVariance(POSITION_VARIANCE);
		config.setMinAlphaSpeed(0.02f);
		config.setMaxAlphaSpeed(0.05f);
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
