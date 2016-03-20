package uk.fls.h2n0.main.core;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.core.tiles.Tile;
import uk.fls.h2n0.main.util.Renderer;

public class Dungeon extends World{

	public Dungeon(Renderer r, int w) {
		super(r, (int)Math.pow(w,2), (int)Math.pow(w,2), false);
		spawn = false;
		generate();
	}
	
	private void generate(){
		
		//Step 1
		for(int i = 0; i < this.tiles.length; i++){
			this.setTile(Tile.rock, i%this.w, i/this.w);
		}
		
		//Step 2
		int startW = 15;
		int startH = 15;
		int sx = (this.w - startW)/2;
		int sy = (this.h - startH)/2;
		for(int xx = sx - startW/2; xx < sx + startW/2; xx++){
			for(int yy = sy - startH/2; yy < sy + startH/2; yy++){
				if(xx == sx - startW/2 || xx == (sx + startW/2) - 1 || yy == (sy - startH/2) || yy == (sy + startH/2) - 1)this.setTile(Tile.wall,  startW/2 + xx, startH/2 + yy);
				else this.setTile(Tile.grass, startW/2 + xx, startH/2 + yy);
			}
		}
		
		//Step 3
		int[] space = getFreeSpace();
		buildRoom(space[0],space[1],10,4,space[2]);
	}
	
	public int[] getFreeSpace(){
		Point pos = new Point(0,0);
		int dir = -1;
		while(true){
			int tx = this.rand.nextInt(w);
			int ty = this.rand.nextInt(w);
			pos = new Point(tx,ty);
			Tile top = getTile(pos.getIX(), pos.getIY() - 1);
			Tile bottom = getTile(pos.getIX(), pos.getIY() + 1);
			Tile left = getTile(pos.getIX() - 1, pos.getIY());
			Tile right = getTile(pos.getIX() + 1, pos.getIY());
			
			boolean bt = top != Tile.rock && bottom == Tile.wall;
			boolean bb = bottom != Tile.rock && top == Tile.wall;
			boolean bl = left != Tile.rock && right == Tile.wall;
			boolean br = right != Tile.rock && left == Tile.wall;
			
			if(bt){
				dir = 0;
				break;
			}else if(bb){
				dir = 2;
				break;
			}else if(bl){
				dir = 1;
				break;
			}else if(br){
				dir = 3;
				break;
			}
		}
		return new int[]{pos.getIX(),pos.getIY(),dir};
	}
	
	public void buildRoom(int x,int y,int w,int h,int dir){
		for(int xx = x; xx <= x + w; xx++){
			for(int yy = y; yy <= y + h; yy++){
				if(xx == x || yy == y || xx == x + w || yy == y + h){
					Tile t = this.getTile(xx, yy);
					if(t != Tile.grass)this.setTile(Tile.wall, xx, yy);
				}else this.setTile(Tile.grass, xx, yy);
			}
		}
	}
}
