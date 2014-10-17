package com.mg.studio.tuktuk.particle;

import android.graphics.PointF;

import com.mg.studio.engine.MGColor;

public class ParticleConfig {
	float vx;
	float vy;
	float gravity;
	float alphaSpeed;
	float inertia;
	public float angleSpeed;
	float ratio;
	// -----------------
	public int minSize;
	public int maxSize ;
	public float minSpeed ;
	public float maxSpeed ;
	public float minGravity;
	public float maxGravity ;
	public float positionVariance;
	public float explodeForceVarianceX ;
	public float explodeMinForceY ;
	public float explodeMaxForceY ;
	public float explodeTargetForceVarianceX ;
	public float explodeTargetForceVarianceY ;

	public float minAlphaSpeed;
	public float maxAlphaSpeed;
	public float minAngleSpeed;
	public float maxAngleSpeed;
	
	public PointF startPos = new PointF();
	public PointF endPos = new PointF();
	public PointF minPos = new PointF();
	public PointF maxPos = new PointF();
	public PointF minForce = new PointF();
	public PointF maxForce = new PointF();
	public PointF minDes = new PointF();
	public PointF maxDes = new PointF();
	public MGColor minColor = new MGColor();
	public MGColor maxColor = new MGColor();
	public ParticleConfig() {
		
	}
	/*setup suc gio*/
	public void setInertia(float inertia) {
		this.inertia = inertia;
	}

	public void setMaxAlphaSpeed(float maxAlphaSpeed) {
		this.maxAlphaSpeed = maxAlphaSpeed;
	}

	public void setMinAlphaSpeed(float minAlphaSpeed) {
		this.minAlphaSpeed = minAlphaSpeed;
	}

	public void setMinAngleSpeed(float minAngleSpeed) {
		this.minAngleSpeed = minAngleSpeed;
	}

	public void setMaxAngleSpeed(float maxAngleSpeed) {
		this.maxAngleSpeed = maxAngleSpeed;
	}

	public void setExplodeForceVarianceX(float explodeForceVarianceX) {
		this.explodeForceVarianceX = explodeForceVarianceX*ratio;
	}

	public void setExplodeMaxForceY(float explodeMaxForceY) {
		this.explodeMaxForceY = explodeMaxForceY*ratio;
	}

	public void setExplodeTargetForceVarianceX(
			float explodeTargetForceVarianceX) {
		this.explodeTargetForceVarianceX = explodeTargetForceVarianceX*ratio;
	}

	public void setExplodeTargetForceVarianceY(
			float explodeTargetForceVarianceY) {
		this.explodeTargetForceVarianceY = explodeTargetForceVarianceY*ratio;
	}

	public void setExplodeMinForceY(float explodeMinForceY) {
		this.explodeMinForceY = explodeMinForceY*ratio;
	}

	public void setPositionVariance(float positionVariance) {
		this.positionVariance = positionVariance*ratio;
	}


	public void setMinColor(float alpha, float red,float  green,float  blue) {
		this.minColor.setColor(alpha, red, green, blue);
	}

	public void setMaxColor(float alpha, float red,float  green,float  blue) {
		this.maxColor.setColor(alpha, red, green, blue);
	}

	public void setMinGravity(float minGravity) {
		this.minGravity = minGravity;
	}

	public void setMaxGravity(float maxGravity) {
		this.maxGravity = maxGravity;
	}
	public void setMinSize(int minSize) {
		this.minSize = (int)(minSize*ratio);
	}
	public void setMaxSize(int maxSize) {
		this.maxSize = (int)(maxSize*ratio);
	}
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}


}
