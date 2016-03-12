package uk.fls.h2n0.main.entitys.item;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.entitys.Entity;

public class ItemEntity extends Entity{
	
	
	private int step;
	private int stepDel;
	public ItemEntity(int x,int y,Item i){
		this.frames[0] = i.getData();
		this.pos = new Point(x * 8,y * 8);
		shadeBase(1, 5);
		this.step = 0;
		this.stepDel = 0;
	}

	@Override
	public void update() {
		if(stepDel == 0){
			this.z = (step & 0x4)>0?1:0;
			this.stepDel = 10;
			this.step ++;
		}else this.stepDel --;
	}

}
