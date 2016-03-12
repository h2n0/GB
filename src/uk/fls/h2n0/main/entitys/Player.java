package uk.fls.h2n0.main.entitys;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.entitys.effects.SwordSlash;
import uk.fls.h2n0.main.util.Loader;

public class Player extends Entity{
	
	public int health = 10;
	public int maxHealth = 10;
	public boolean canMove;
	public int inputDel = 0;
	private int step;
	public Player(int x,int y){
		this.pos = new Point(x,y);
		this.tx = x / 8;
		this.ty = y / 8;
		
		for(int i = 0; i < 6; i++){
			this.frames[i] = Loader.getFrameData(i, "player.txt");
		}
		this.canMove = false;
		this.dir = 4;
		this.pdir = -1;
		this.inputDel = 0;
		this.step = 0;
		this.shadeBase(0, 8);
		this.currentFrame = dir + (this.step & 1);
	}

	@Override
	public void update() {
		
		if(this.inputDel > 0)this.inputDel--;
		
		boolean moving = !canMove;
		if(moving){
			float speed = (7/30f)/2f;
			
			
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
		}else{
			
		}
	}
	
	//BRB
	
	public void move(int x,int y){
		if(canMove && this.inputDel == 0){
			if(x > 0){
				dir = 2;
				setXFlag();
			}
			else if(x < 0){
				dir = 2;
				remXFlag();
			}
			else if(y > 0)dir = 4;
			else if(y < 0)dir = 0;
			
			int nextX = (this.pos.getIX() / 8) + x;
			int nextY = (this.pos.getIY() / 8) + y;
			
			
			if(this.dir == this.pdir && this.w.getTile(nextX, nextY).canPassThrough){
				this.tx = (this.pos.getIX() / 8) + x;
				this.ty = (this.pos.getIY() / 8) + y;
				
				if(this.tx < 0)this.tx = 0;
				if(this.ty < 0)this.ty = 0;
				
				canMove = false;
				this.step++;
				if(dir != 2){
					if((this.step & 0x2) > 0){
						setXFlag();
					}else{
						remXFlag();
					}
				}
			}
			this.currentFrame = dir + (this.step & 1);
			this.pdir = this.dir;
			this.inputDel = 5;
		}
	}
	
	public void attack(){
		this.w.addEntity(new SwordSlash(this.dir,(this.data & 0x2) > 0,this.pos));
	}
}
