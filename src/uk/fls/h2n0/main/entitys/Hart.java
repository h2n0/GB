package uk.fls.h2n0.main.entitys;

import uk.fls.h2n0.main.util.Loader;

public class Hart extends Entity {
	
	public Hart(){
		this.frames[0] = Loader.getFrameData(0,"hart.txt");
		this.frames[1] = Loader.getFrameData(1,"hart.txt");
	}

	@Override
	public void update() {
		
	}

}
