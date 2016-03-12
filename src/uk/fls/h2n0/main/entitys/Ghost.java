package uk.fls.h2n0.main.entitys;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.entitys.item.Item;
import uk.fls.h2n0.main.entitys.item.ItemEntity;
import uk.fls.h2n0.main.util.Loader;

public class Ghost extends Entity{
	
	private int step;
	private int frameDel;
	private boolean canMove;
	private int inpDel;
	private int appleTime;
	public Ghost(){
		this.step = 0;
		for(int i = 0; i < 6; i++){
			this.frames[i] = Loader.getFrameData(i, "ghost.txt");
		}
		this.currentFrame = 0;
		this.frameDel = 0;
		this.canMove = false;
		
		this.inpDel = 0;
		this.appleTime = 100;
		shadeBase(0,5);
	}

	@Override
	public void update() {
		if(this.frameDel == 0){
			this.step ++;
			this.z = (this.step & 0x4) > 0?1:0;
			this.currentFrame = this.dir + (this.step & 1);
			this.frameDel = 5;
		}else{
			this.frameDel--;
		}
		
		
		if(canMove && inpDel <= 0){
			int dir = (int)Math.floor(Math.random() * 8)/2;
			if(dir == 0)move(0,-1);
			else if(dir == 1)move(1,0);
			else if(dir == 2)move(0,1);
			else move(-1,0);
			
			inpDel = 10 + (int)Math.floor(Math.random() * 25);
			
		}else{
			inpDel --;
		}
		
		if(this.appleTime == 0){
			this.w.addEntity(new ItemEntity(this.pos.getIX() / 8,this.pos.getIY()/8,Item.apple));
			this.appleTime = 100;
			this.alive = false;
		}else{
			this.appleTime--;
		}
		
		boolean moving = !canMove;
		if(moving){
			float speed = (7/30f)/8f;
			
			
			float cx = this.pos.x / 8;
			float cy = this.pos.y / 8;
			if(Math.abs(cx - this.tx) > 0.2){
				if(cx < this.tx){
					cx += speed;
				}
				if(cx > this.tx){
					cx -= speed;
				}
				this.pos.x = cx * 8;
			}else{
				this.pos.x = tx * 8;
			}
			
			if(Math.abs(cy - this.ty) > 0.2){
				if(cy < this.ty){
					cy += speed;
				}
				
				if(cy > this.ty){
					cy -= speed;
				}
				this.pos.y = cy * 8;
			}else{
				this.pos.y = ty * 8;
			}
			
			
			
			if(this.pos.x == this.tx * 8 && this.pos.y == this.ty * 8){
				canMove = true;
			}
		}
	}
	
	public void move(int x,int y){
		if(canMove){
			if(x > 0){
				dir = 2;
				setXFlag();
			}
			else if(x < 0){
				dir = 2;
				remXFlag();
			}
			else if(y > 0)dir = 0;
			else if(y < 0)dir = 4;
			
			int nextX = (this.pos.getIX() / 8) + x;
			int nextY = (this.pos.getIY() / 8) + y;
			
			
			if(this.dir == this.pdir && this.w.getTile(nextX, nextY).canPassThrough){
				this.tx = (this.pos.getIX() / 8) + x;
				this.ty = (this.pos.getIY() / 8) + y;
				
				if(this.tx < 0)this.tx = 0;
				if(this.ty < 0)this.ty = 0;
				
				canMove = false;
				this.currentFrame = dir;
			}
			this.pdir = this.dir;
		}
	}

}
