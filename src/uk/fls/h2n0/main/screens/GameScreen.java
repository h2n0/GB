package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Camera;
import uk.fls.h2n0.main.core.World;
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
		this.w = new World(this.rend,"res/levels/levelout.txt");
		this.p = new Player(10,10);
		this.w.addEntity(p);
		
		this.phrase = new Word(10,10,3);
		
	}

	@Override
	public void render(Graphics g) {
		this.rend.fill(3);
		this.w.render(cam);
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
		
		if(this.input.isKeyPressed(this.input.space)){
			this.p.attack();
		}
		
		this.cam.center(this.p.pos.getIX(), this.p.pos.getIY(), 4);
	}
}
