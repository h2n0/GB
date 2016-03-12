package uk.fls.h2n0.main;

import java.awt.image.BufferedImage;

import fls.engine.main.Init;
import fls.engine.main.input.Input;
import uk.fls.h2n0.main.screens.GameScreen;

@SuppressWarnings("serial")
public class Game extends Init{
	
	public final static int w = 160;
	public final static int h = 144;
	public static final int s = 4;
	public Game(){
		super("GB 2",w * s,h * s);
		this.useCustomBufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		this.setImageScale(1);
		this.setInput(new Input(this, Input.KEYS));
		setScreen(new GameScreen());
		//this.setDestieredAmtOfTicks(10);
		this.skipInit();
		this.showFPS();
	}
	
	public static void main(String[] args){
		new Game().start();
	}

}
