package uk.fls.h2n0.main.entitys.effects;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.util.Loader;

public class SwordSlash extends Effect{
	
	private int maxLife = 0;
	
	private Point[] trans;
	
	public SwordSlash(int dir,boolean x,Point p){
		this.pos = p;
		this.frames = new int[3][8*8];
		this.maxLife = this.life = 3 * 4;
		this.trans = new Point[3];
		int off = 8;
		if(dir==0){//Up
			this.setYFlag();
			for(int i = 0; i < 3; i++){
				this.frames[2-i] = Loader.getFrameData(i, "effects.txt");
				
				float ox = (float) Math.round(Math.cos(0 + (i * 45)));
				float oy = (float) Math.round(Math.sin(0 + (i * 45)));
				
				this.trans[i] = new Point(this.pos.getIX() - (ox * off),this.pos.getIY() - (oy * off));
			}
			this.pos = new Point(this.pos.getIX() - off,this.pos.getIY() - off);
		}else if(dir == 2){//Left/Right
			for(int i = 0; i < 3; i++){
				this.frames[i] = Loader.getFrameData(i, "effects.txt");
			}
			if(x){//Right
				this.setXFlag();
				this.setYFlag();
				for(int i = 0; i < 3; i++){
					float ox = (float) Math.round(Math.sin(0 + (i * 45)));
					float oy = (float) Math.round(Math.cos(0 + (i * 45)));
					this.trans[i] = new Point(this.pos.getIX() + (ox * off),this.pos.getIY() - (oy * off));
				}
				this.pos = new Point(this.pos.getIX() + off,this.pos.getIY() - off);
			}else{//Left
				//this.setYFlag();
				for(int i = 0; i < 3; i++){
					float ox = (float) Math.round(Math.sin(0 + (i * 45)));
					float oy = (float) Math.round(Math.cos(0 + (i * 45)));
					this.trans[i] = new Point(this.pos.getIX() - (ox * off),this.pos.getIY() + (oy * off));
				}
				this.pos = new Point(this.pos.getIX() - off,this.pos.getIY() + off);
			}
		}else if(dir == 4){//Down
			this.setXFlag();
			for(int i = 0; i < 3; i++){
				this.frames[2-i] = Loader.getFrameData(i, "effects.txt");
				
				float ox = (float) Math.round(Math.cos(0 + (i * 45)));
				float oy = (float) Math.round(Math.sin(0 + (i * 45)));
				
				this.trans[i] = new Point(this.pos.getIX() + (ox * off),this.pos.getIY() + (oy * off));
			}
			this.pos = new Point(this.pos.getIX() + off,this.pos.getIY() + off);
		}
	}

	@Override
	public void tick() {
		int sec = this.maxLife / 3;
		
		if(this.life >= sec * 2)this.currentFrame = 0;
		else if(this.life >= sec && this.life < sec * 2)this.currentFrame = 1;
		else if(this.life < sec)this.currentFrame = 2;
		
		this.pos = this.trans[this.currentFrame];
	}

}
