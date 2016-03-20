package uk.fls.h2n0.main.screens;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Point;
import uk.fls.h2n0.main.core.Dungeon;
import uk.fls.h2n0.main.core.World;
import uk.fls.h2n0.main.core.tiles.Tile;
import uk.fls.h2n0.main.entitys.Player;
import uk.fls.h2n0.main.screens.artifacts.Word;
import uk.fls.h2n0.main.util.Renderer;

public class GameScreen extends Screen{
	
	private Renderer rend;
	private Camera cam;
	private World w;
	private Player p;
	private Word phrase;
	
	public void postInit(){
		rend = new Renderer(this.game.image);
		for(int x = 0; x < this.rend.w; x++){
			for(int y = 0; y < this.rend.h; y++){
				rend.setPixel(x, y, (x + y) % 4);
			}
		}
		this.cam = new Camera(160,144);
		this.w = new Dungeon(this.rend,9);
		
		Point ps = this.w.findSpawn(Tile.grass);
		this.p = new Player(ps.getIX() * 8,ps.getIY() * 8);
		this.w.addEntity(p);
		
		this.phrase = new Word(10,10,2);
	}

	@Override
	public void render(Graphics g) {
		this.rend.fill(3);
		this.w.render(cam);
		//this.phrase.render(rend, "Y!" +this.p.pos.getIY()/8);
		this.rend.render();
	}
	
	@Override
	public void update() {
		this.w.update(input);
		
		
		if(this.input.isKeyHeld(this.input.w) || this.input.isKeyHeld(this.input.up)){
			this.p.move(0, -1);
		}else if(this.input.isKeyHeld(this.input.a) || this.input.isKeyHeld(this.input.left)){
			this.p.move(-1, 0);
		}else if(this.input.isKeyHeld(this.input.s) || this.input.isKeyHeld(this.input.down)){
			this.p.move(0, 1);
		}else if(this.input.isKeyHeld(this.input.d) || this.input.isKeyHeld(this.input.right)){
			this.p.move(1, 0);
		}
		
		if(this.input.isKeyPressed(this.input.q)){
			JOptionPane.showMessageDialog(null, null,"Image",JOptionPane.OK_OPTION,new ImageIcon(this.w.getImage(p).getScaledInstance(512, 512, Image.SCALE_AREA_AVERAGING)));
		}
		
		if(this.input.isKeyPressed(this.input.space)){
			this.p.attack();
		}
		
		this.cam.center(this.p.pos.getIX(), this.p.pos.getIY(), 4);
	}
}
