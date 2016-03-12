package uk.fls.h2n0.main.entitys;

import java.util.Random;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.entitys.inv.Stack;
import uk.fls.h2n0.main.entitys.item.Item;

public abstract class Entity {
	
	public int[][] frames;
	public Point pos;
	protected int currentFrame;
	public World w;
	public float z;
	public byte data;
	public Stack[] inv;

	protected byte dir;
	protected byte pdir;
	public float tx,ty;
	public boolean shadeBase;
	public int shadeX;
	public int shadeW;
	protected Random rand;
	public boolean alive;
	
	public Entity(){
		this.frames = new int[8][8 * 8];
		this.pos = new Point(0,0);
		this.currentFrame = 0;
		this.data = 0;
		this.shadeBase = false;
		this.inv = new Stack[0];
		this.alive = true;
		this.rand = new Random();
	}
	
	public abstract void update();
	
	protected void setFrame(int frame){
		this.currentFrame = frame;
	}
	
	public int getCurrentFrame(){
		return this.currentFrame;
	}
	
	public byte getData(){
		return this.data;
	}
	
	protected void setXFlag(){
		this.data |= 0x2;
	}
	
	protected void setYFlag(){
		this.data |= 0x4; 	
	}
	
	protected void remXFlag(){
		if((this.data & 0x2) > 0){
			this.data ^= 0x2;
		}
	}
	
	protected void remYFlag(){
		if((this.data & 0x4) > 0){
			this.data ^= 0x4;
		}
	}
	
	public void shadeBase(int sx,int sw){
		this.shadeBase = true;
		this.shadeX = sx;
		this.shadeW = sw;
	}
	
	public boolean giveItem(Item it){
		for(int i = 0; i < this.inv.length; i++){
			Stack s = this.inv[i];
			if(s.addItem(it))return true;
		}
		return false;
	}
	
	public boolean giveItem(Item it,int amt){
		for(int i = 0; i < amt; i++){
			if(!giveItem(it))return false;
		}
		return true;
	}
	
	public void die(){}

}
