package uk.fls.h2n0.main.util.leveledit;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import fls.engine.main.io.FileIO;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Point;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.core.tiles.Tile;
import uk.fls.h2n0.main.util.Renderer;
import uk.fls.h2n0.main.util.leveledit.customentitys.TileSelector;

public class LevelEditScreen extends Screen {
	
	public byte[] tiles;
	public final int w;
	public final int h;
	public final BufferedImage image;
	public final Renderer rend;
	private World lm;
	private Camera cam;
	private TileSelector ts;
	private int s;
	
	private int offX,offY;
	
	
	public LevelEditScreen(){
		this.w = this.h = 64;
		this.s = 8;
		this.image = new BufferedImage(this.w * s,this.h * s,BufferedImage.TYPE_INT_ARGB);
		this.rend = new Renderer(this.image);
		this.lm = new World(rend,this.w,this.h,true);
		
		this.cam = new Camera(160,144);
		this.cam.wh = this.rend.h;
		this.cam.ww = this.rend.w;
		
		this.ts = new TileSelector();
		this.lm.addEntity(this.ts);
		this.lm.spawn = false;
		this.offX = 0;
		this.offY = 0;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.game.width, this.game.height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, 200, this.game.height);
		
		this.rend.fill(3);
		
		this.lm.render(this.cam);
		
		this.rend.render();
		g.drawImage(this.image, 200, 0,this.w * s,this.h * s, null);
		g.setColor(Color.RED);
		g.drawRect(200+this.cam.pos.getIX(), this.cam.pos.getIY(), this.cam.w, this.cam.h);
	}

	@Override
	public void update() {
		
		int mx = this.input.mouse.getX();
		int my = this.input.mouse.getY();
		if(mx>= 200 && my >= 0 && mx <= 200+this.w*s && my <= 0 + this.h*s){
			int mdx = (mx - 200 + this.cam.pos.getIX()) / s;
			int mdy = (my + this.cam.pos.getIY()) / s;
			this.ts.pos = new Point(mdx * s,mdy * s);	
			if(this.input.leftMouseButton.justClicked()){
				this.lm.setTile(Tile.rock, mdx, mdy);
			}
		}
		
		if(this.input.isKeyPressed(this.input.left)){
			offX -= s;
			if(offX < 0)offX = 0;
			updateCamPos();
		}
		if(this.input.isKeyPressed(this.input.right)){
			offX += s;
			if(offX > w * s)offX = w * s;
			updateCamPos();
		}
		if(this.input.isKeyPressed(this.input.up)){
			offY -= s;
			if(offY < 0)offY = 0;
			updateCamPos();
		}
		if(this.input.isKeyPressed(this.input.down)){
			offY += s;
			if(offY > h * s)offY = h * s;
			updateCamPos();
		}
		
		if(this.input.isKeyPressed(this.input.space)){
			printLevel();
		}
		this.lm.update(input);
	}
	
	
	public void printLevel(){
		System.out.println("----- Printing level data ------");
		String fileData = "";
		for(int y = 0; y < this.h; y++){
			String row = "[";
			for(int x = 0; x < this.w; x++){
				row += this.lm.getTile(x, y).id+",";
			}
			row = row.substring(0, row.length()-1) + "]\n";
			fileData += row;
		}
		
		FileIO.instance.writeFile("levelout.txt", fileData);
		System.out.println("-----------------------");
	}
	
	private void updateCamPos(){
		this.cam.pos = new Point(offX,offY);
	}
	
	

}
