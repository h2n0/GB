package uk.fls.h2n0.main.screens.artifacts;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.util.Renderer;

public class Word{

	private Letter[] letters;
	private String last;
	private Point pos;
	private int shade;
	public Word(int x,int y, int s){
		this.last = "";
		this.pos = new Point(x,y);
		this.shade = s;
	}
	
	public void render(Renderer r, String newPhrase){
		if(newPhrase == this.last)return;
		else{
			int shade = r.getShade(this.pos.getIX(), this.pos.getIY());
			this.letters = new Letter[newPhrase.length()];
			for(int i = 0; i < letters.length; i++){
				this.letters[i] = new Letter(newPhrase.substring(i,i+1), this.pos.getIX() + i * 8, this.pos.getIY(), this.shade);
			}
			for(int i = 0; i < this.letters.length; i++){
				this.letters[i].render(r);
			}
		}
	}
}
