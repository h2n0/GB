package uk.fls.h2n0.main.core.tiles;

import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.util.Loader;

public class WaterTile extends Tile {

	private int[][] frames;

	public WaterTile(int id) {
		super(id);
		this.frames = new int[2][8 * 8];
		for(int i = 0; i < 2; i++){
			this.frames[i] = Loader.getFrameData(3 + i, "tiles.txt");
		}
	}

	public void update(World w, int x, int y) {
		int age = w.getData(x, y);
		age++;
		w.setData(x, y, age);
	}
	
	public int[] render(World w, int x,int y){
		if(w.getData(x, y) % 2 == 0){
			return this.frames[0];
		}
		else {
			return this.frames[1];
		}
	}

}
