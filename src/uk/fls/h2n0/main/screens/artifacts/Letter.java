package uk.fls.h2n0.main.screens.artifacts;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.util.Loader;
import uk.fls.h2n0.main.util.Renderer;

public class Letter{
	
	
	private int[] frame;
	private final String chars = "abcdefghijklmnopqrstuvwxyz1234567890!";
	private Point pos;
	public Letter(String c,int x,int y,int shade){
		if(c.indexOf(" ")!=-1){
			this.frame = new int[8*8];
			for(int i = 0; i < 8*8; i++){
				this.frame[i] = -1;
			}
		}else{
			this.frame = Loader.getFrameData(chars.indexOf(c.toLowerCase()), "letters.txt");
			for(int i = 0; i < 8*8; i++){
				if(this.frame[i] == -1)continue;
				if(shade > 3)shade = 3;
				this.frame[i] = shade;
			}
		}
		this.pos = new Point(x,y);
	}
	
	
	public void render(Renderer r){
		for(int y = 0; y < 8; y++){
			for(int x = 0; x < 8; x++){
				r.setPixel(this.pos.getIX() + x, this.pos.getIY() + y, this.frame[x + y * 8]);
			}
		}
	}

}
