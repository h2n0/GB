package uk.fls.h2n0.main.util.leveledit.customentitys;

import uk.fls.h2n0.main.entitys.Entity;
import uk.fls.h2n0.main.util.Loader;

public class TileSelector extends Entity{
	
	private int frameAnim;
	private final int frameRest;
	
	public TileSelector() {
		for(int i = 0; i < 2; i++){
			this.frames[i] = Loader.getFrameData(i, "tileSelect.txt");
			for(int j = 0; j < 8*8; j++){
				int s = this.frames[i][j];
				if(s != -1)s = s + 1;
				if(s < 3 && s!= -1)s=3;
				this.frames[i][j] = s;
			}
		}
		this.currentFrame = 0;
		this.frameRest = 30;
		this.frameAnim = this.frameRest;
	}

	@Override
	public void update() {
		
		
		if(this.frameAnim-- < 0){
			swapFrames();
		}
	}
	
	private void swapFrames(){
		this.currentFrame = ++this.currentFrame % 2;
		this.frameAnim = this.frameRest;
	}

}
