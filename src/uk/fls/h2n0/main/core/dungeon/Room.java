package uk.fls.h2n0.main.core.dungeon;

import java.util.Random;

import uk.fls.h2n0.main.core.Dungeon;
import uk.fls.h2n0.main.core.tiles.Tile;

public class Room {

	public int x,y,w,h;
	public int ix,iy,iw,ih;
	public Room[] children;
	private Random rand;
	private Room parent;
	private Room sibling;
	private Corridor connector;
	private boolean v;
	
	private int cx1,cy1,cx2,cy2;//Corridor things
	public Room(int x,int y,int w,int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.rand = new Random();
		this.parent = null;
		
		this.cx1 = this.cx2 = this.cy1 = this.cy2 = -1;
		this.connector = null;
	}
	
	public void split(boolean v){
		int min = 5;
		if(this.children == null){
			this.v = v;
			int nw = (int)(this.w) / 2;
			int nh = (int)(this.h) / 2;
			if(!v){// Horz split
				Room r1 = new Room(this.x,this.y,this.w,nh);
				Room r2 = new Room(this.x,this.y+nh,this.w,this.h - nh);
				
				if(r1.h  < min || r2.h < min)return;
				
				this.children = new Room[2];
				this.children[0] = r1;
				this.children[1] = r2;
				initialiseChildren(r1, r2);
				
			}else{//Vert split
				Room r1 = new Room(this.x + nw,this.y,nw,this.h);
				Room r2 = new Room(this.x,this.y,this.w - nw,this.h);
				
				if(r1.w  < min || r2.w < min)return;
				
				
				this.children = new Room[2];
				this.children[0] = r1;
				this.children[1] = r2;
				initialiseChildren(r1, r2);
			}
		}else{
			this.children[0].split(v);
			this.children[1].split(!v);
		}
	}
	
	public void decorate(Dungeon dun){
		if(this.children == null){
			for(int x = ix; x <= ix + iw; x ++){
				for(int y = iy; y <= iy + ih; y++){
					if(x == ix || y == iy || x == ix + iw || y == iy + ih){
						dun.setTile(Tile.wall, this.x + x, this.y + y);
					}else{
						dun.setTile(Tile.grass, this.x + x, this.y + y);
					}
				}
			}
		}else{
			for(int i = 0; i < 2; i++){
				this.children[i].decorate(dun);
			}
		}
		
		if(this.connector != null){
			this.connector.decorate(dun);
		}
	}
	
	private void makeInner(){
		if(parent == null){
			this.ix = 0;
			this.iy = 0;
			this.iw = this.w;
			this.ih = this.h;
		}else{
			int min = 5;
			int max = 15;
			this.ix = this.rand.nextInt(min);
			this.iy = this.rand.nextInt(min);
			this.iw = min + this.rand.nextInt(this.w);
			this.ih = min + this.rand.nextInt(this.h);
			if(this.ix + this.iw > this.w)this.iw = this.w - this.ix;
			if(this.iy + this.ih > this.h)this.ih = this.h - this.iy;
			
			if(this.iw > max)this.iw = max;
			if(this.ih > max)this.ih = max;
			
		}
	}
	
	public void connect(){
		if(children != null){//let the children connect first
			for(int i = 0; i < 2; i++){
				this.children[i].connect();
			}
		}else{//lowest room in tree
			this.connector = new Corridor(this,this.sibling);
		}
	}
	
	private void initialiseChildren(Room r1,Room r2){
		r1.parent = this;
		r2.parent = this;
		
		r1.makeInner();
		r2.makeInner();
		
		r1.sibling = r2;
		r2.sibling = r1;
	}
	
	public boolean isVertSplit(){
		return this.v;
	}
}
