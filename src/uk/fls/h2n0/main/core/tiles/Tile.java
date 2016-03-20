package uk.fls.h2n0.main.core.tiles;

import uk.fls.h2n0.main.core.World;

public class Tile {

	public static Tile[] tiles = new Tile[256];
	public static Tile grass = new GrassTile(0);
	public static Tile wall = new WallTile(1);
	public static Tile rock = new RockTile(2);
	public static Tile water = new WaterTile(3);
	public static Tile voidTile = new VoidTile(4);
	
	
	public final String dataPos;
	public int[][] frames;
	
	
	public boolean canPassThrough;
	public final byte id;
	public int[] data;
	public Tile(int id){
		if(tiles[id] != null)throw new RuntimeException("Duplicate IDs:" + id);
		this.id = (byte)id;
		this.data = new int[8 * 8];
		tiles[id] = this;
		dataPos = "tiles.txt";
		this.canPassThrough = true;
	}
	
	public void update(World w,int tx,int ty){
		
	}
	
	public int[] render(World w,int x,int y){
		return this.data;
	}
	
	public static Tile getById(int id){
		return tiles[id];
	}
}
