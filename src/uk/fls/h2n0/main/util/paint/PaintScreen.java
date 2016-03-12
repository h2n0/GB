package uk.fls.h2n0.main.util.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import fls.engine.main.screen.Screen;
import uk.fls.h2n0.main.util.Renderer;

public class PaintScreen extends Screen {
	
	public final int cells = 8;
	public final int size = 64;
	public int[] data;
	public int inputdelay = 0;
	
	public int mx = 0;
	public int my = 0;
	
	public PaintScreen(){
		this.data = new int[cells*cells];
	}

	@Override
	public void render(Graphics g) {
		int cx = (this.game.width - (cells * size))/2;
		int cy = (this.game.height - (cells * size))/2;
		
		g.setColor(Color.gray);
		g.fillRect(0, 0, 600 * 2, 400 * 2);
		
		
		for(int x = 0; x < cells; x++){
			for(int y = 0; y < cells; y++){
				int bx = cx + (x * size);
				int by = cy + (y * size);
				setColor(g,data[x + y * cells]);
				g.fillRect(bx, by, size, size);
			}
		}
		
		g.setColor(Color.BLUE);
		g.fillRect(mx - 5, my - 5, 10, 10);
	}

	@Override
	public void update() {
		if(this.inputdelay > 0)this.inputdelay--;
		this.mx = this.input.mouse.getX();
		this.my = this.input.mouse.getY();
		
		int reset = 10;
		
		if(this.input.leftMouseButton.justClicked() && this.inputdelay == 0){
			int tx = mx - (this.game.width - (cells * size))/2;
			int ty = my - (this.game.height - (cells * size))/2;
			if(tx > 0 && ty > 0  && tx <= 512 && ty <= 512){//Over a tile
				int dx = tx / size;
				int dy = ty / size;
				
				int cur = data[dx + dy * cells];
				cur ++;
				if(cur > 3)cur = -1;
				data[dx + dy * cells] = cur;

				this.inputdelay = reset;
			}
		}
		
		if(this.input.rightMouseButton.justClicked() && this.inputdelay == 0){
			int tx = mx - (this.game.width - (cells * size))/2;
			int ty = my - (this.game.height - (cells * size))/2;
			if(tx > 0 && ty > 0  && tx <= 512 && ty <= 512){//Over a tile
				int dx = tx / size;
				int dy = ty / size;
				
				int cur = data[dx + dy * cells];
				cur --;
				if(cur < -1)cur = 3;
				data[dx + dy * cells] = cur;

				this.inputdelay = reset;
			}
		}
		
		if(this.input.isKeyPressed(this.input.q)){
			for(int i = 0; i <  cells * cells; i++)this.data[i] = -1;
		}
		
		if(this.input.isKeyPressed(this.input.space)){
			System.out.println("--- Reprint ---\n");
			for(int i = 0; i < 8; i++){
				String row = "[";
				for(int j = 0; j < 8; j++){
					row += "" + data[j + i * 8] + ",";
				}
				row = row.substring(0, row.length() - 1);
				row += "]";
				System.out.println(row);
			}
			System.out.println("-------------\n");
		}
	}
	
	public void setColor(Graphics g,int shade){
		switch(shade){
		case 0:
			g.setColor(Color.white);
			break;
		case 1:
			g.setColor(Color.lightGray);
			break;
		case 2:
			g.setColor(Color.darkGray);
			break;
		case 3:
			g.setColor(Color.black);
			break;
		case -1:
			g.setColor(Color.ORANGE);
			break;
		}
	}

}
