package uk.fls.h2n0.main.util.leveledit;

import fls.engine.main.Init;
import fls.engine.main.input.Input;

@SuppressWarnings("serial")
public class LevelEdit extends Init{
	
	
	public LevelEdit(){
		super("GB-Level Editor",800,600);
		this.skipInit();
		this.setInput(new Input(this,Input.KEYS,Input.MOUSE));
		this.setScreen(new LevelEditScreen());
	}
	
	
	public static void main(String[] args){
		new LevelEdit().start();
	}
}
