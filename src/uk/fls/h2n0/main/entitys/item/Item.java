package uk.fls.h2n0.main.entitys.item;

import uk.fls.h2n0.main.util.Loader;

public class Item {
	
	protected final String dataPos;
	public static Item[] items = new Item[64];
	public static Item apple = new Item(0).setFrame(0);
	
	public final byte id;
	protected int[] data;
	public Item(int id){
		if(items[id] != null)throw new RuntimeException("Duplicate IDs:" + id);
		this.id = (byte)id;
		this.data = new int[8 * 8];
		items[id] = this;
		this.dataPos = "items.txt";
	}
	
	public int[] getData(){
		return this.data;
	}
	
	private Item setFrame(int p){
		this.data = Loader.getFrameData(p, dataPos);
		return this;
	}
}
