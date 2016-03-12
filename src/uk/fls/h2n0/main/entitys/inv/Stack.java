package uk.fls.h2n0.main.entitys.inv;

import uk.fls.h2n0.main.entitys.item.Item;

public class Stack {
	
	private final int maxStack;
	private int currentSize;
	public final Item stackItem;
	
	public Stack(Item i, int ms){
		this.maxStack = ms;
		this.stackItem = i;
		this.currentSize = 0;
	}
	
	public boolean addItem(Item i){
		if(i == this.stackItem && this.currentSize < this.maxStack){
			this.currentSize++;
			return true;
		}else{
			return false;
		}
	}

}
