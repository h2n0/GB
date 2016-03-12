package uk.fls.h2n0.main.util.paint;

import fls.engine.main.Init;
import fls.engine.main.input.Input;

public class Painter extends Init{
	
	public Painter(){
		super("Painter",800,600);
		skipInit();
		setInput(new Input(this,Input.MOUSE, Input.KEYS));
		setScreen(new PaintScreen());
	}
	
	public static void main(String[] args){
		
		
		new Painter().start();
	}

}
