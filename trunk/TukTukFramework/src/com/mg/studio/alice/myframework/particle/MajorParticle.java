/**
 * 
 */
package com.mg.studio.alice.myframework.particle;

import com.mg.studio.engine.MGParticle;
import com.mg.studio.engine.RandUtils;


/**
 * @author Dk Thach
 * 
 */
public class MajorParticle extends MGParticle {
	float vx;
	float vy;
	float gravity;
	float alphaSpeed;
	float inertia;
	float angleSpeed;

	// -----------------

	public MajorParticle() {
	}

	/**
	 * goi lai moi lan phat sinh 1 chum hat goi truoc ham initRanDomAllValue()
	 * va sau khi set cac gia tri min max
	 **/
	public void initMajorValue(ParticleConfig config, float x, float y) {
		config.minPos.set(x, y);
		config.maxPos.set(config.minPos.x + config.positionVariance
				+ config.positionVariance, config.minPos.y
				+ config.positionVariance + config.positionVariance);
		config.minForce.set(-config.explodeTargetForceVarianceX,
				config.explodeMinForceY);
		config.maxForce.set(config.explodeTargetForceVarianceX,
				config.explodeMaxForceY);
	}

	/**
	 * goi lai moi khi sinh 1 hat, ham nay tao su khac nhau cua moi hat random
	 * theo cac thong so min max da cai dat truoc
	 */
	public void initRanDomAllValue(ParticleConfig config) {
		size = RandUtils.getIntBetween(config.minSize, config.maxSize);
		x = RandUtils.getFloatBetween(config.minPos.x, config.maxPos.x);
		y = RandUtils.getFloatBetween(config.minPos.y, config.maxPos.y);
		vx = RandUtils.getFloatBetween(config.minForce.x, config.maxForce.x);
		vy = RandUtils.getFloatBetween(config.minForce.y, config.maxForce.y);

		color.setColor(RandUtils.getFloatBetween(config.minColor.getAlpha(),
				config.maxColor.getAlpha()), RandUtils.getFloatBetween(
				config.minColor.getRed(), config.maxColor.getRed()),
				RandUtils.getFloatBetween(config.minColor.getGreen(),
						config.maxColor.getGreen()), RandUtils
						.getFloatBetween(config.minColor.getBlue(),
								config.maxColor.getBlue()));
		gravity = RandUtils.getFloatBetween(config.minGravity,
				config.maxGravity);
		alphaSpeed = RandUtils.getFloatBetween(config.minAlphaSpeed,
				config.maxAlphaSpeed);
		angleSpeed = RandUtils.getFloatBetween(config.minAngleSpeed,
				config.maxAngleSpeed);
	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGParticle#update()
	 */
	@Override
	public void update() {
		if (angleSpeed != 0) {
			angle += angleSpeed;
			angle = angle % 360;
		}
		vx *= inertia;
		x += vx;
		vy += gravity;
		y += vy;
		if (alphaSpeed > 0) {
			color.setRed(color.getRed() - alphaSpeed);
			color.setGreen(color.getGreen() - alphaSpeed);
			color.setBlue(color.getBlue() - alphaSpeed);
			color.setAlpha(color.getAlpha() - alphaSpeed);
		}
		setActive(color.getAlpha() > 0);

	}

	/*
	 * (non-Javadoc)
	 * @see com.mg.studio.engine.MGParticle#clone()
	 */
	@Override
	public MGParticle clone() {
		// TODO Auto-generated method stub
		return new MajorParticle();
	}

}
