package uk.fls.h2n0.main.screens.artifacts;

import uk.fls.h2n0.main.util.Renderer;

public class ScanLine {

	private float y = 0;
	private float speed;
	private int changeTime;
	private int col;

	public ScanLine() {
		this.speed = +0.5f;
		this.changeTime = 10;
		this.col = 1;
		this.y = 150;
	}

	public void renderer(Renderer r) {
		for (int x = 0; x < r.w; x++) {
			r.setPixel(x, (int) y, col);
		}
	}

	public void update() {
		if ((this.y > -1)) {
			if (changeTime-- <= 0) {
				this.changeTime = (int) Math.floor(Math.random() * 10);
				this.col = (int) Math.floor(Math.random() * 3);
			}
			y -= speed;
		}else{
			this.y = 144 + (int)Math.floor(Math.random() * 25);
		}
	}

}
