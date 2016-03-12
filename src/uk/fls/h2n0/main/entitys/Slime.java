package uk.fls.h2n0.main.entitys;

import uk.fls.h2n0.main.util.Loader;

public class Slime extends Entity{
	
	private int jumpTime;
	private boolean canJump;
	private double za,zz;
	public Slime(){
		for(int i = 0; i < 2; i++){
			this.frames[i] = Loader.getFrameData(i, "slime.txt");
		}
		
		this.jumpTime = 100;
		this.canJump = true;
		this.zz = 2f;
		this.za = this.rand.nextFloat() * 0.7f + 2;
	}

	@Override
	public void update() {
		if(this.z <= 0){
			this.canJump = true;
			this.currentFrame = 0;
		}
		
		this.zz += this.za;
		if(this.zz < 0){
			this.zz = 0;
			this.za *= -0.5;
		}
		this.za -= 0.15;
		
		if(canJump){
			if(--jumpTime == 0)jump();
		}
		
		if(Math.abs(this.zz) < 0.1){
			this.zz = 0;
		}
		
		this.z = (float)this.zz;
		
		if(this.zz <= 0)this.currentFrame = 0;
		else this.currentFrame = 1;
		
		
		int spos = (int)this.z / 2;
		int swidth = 10;
		int soff = 0;
		this.shadeBase(soff + spos,(8-soff) - spos * 2);
		
	}
	
	private void jump(){
		this.jumpTime = 100;
		this.za = 1.5f;
	}

}
