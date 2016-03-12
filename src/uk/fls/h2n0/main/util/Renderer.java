package uk.fls.h2n0.main.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Renderer {
	
	public BufferedImage image;
	public int[] pixels;
	public int[] colors;
	public final int w;
	public final int h;
	
	public Renderer(BufferedImage i){
		this.image = i;
		this.pixels = ((DataBufferInt)i.getRaster().getDataBuffer()).getData();
		this.w = i.getWidth();
		this.h = i.getHeight();
		this.useDefaultColor();
	}
	
	public boolean isValid(int x,int y){
		if(x < 0 || y < 0 || x >= w || y >= h)return false;
		else return true;
	}
	
	public void setPixel(int x,int y, int shade){
		if(shade == -1 || !isValid(x,y)){
			return;
		}
		
		if(shade > 3 || shade < -1)shade = 3;
		this.pixels[x + y * w] = colors[shade];
	}
	
	public int getPixel(int x,int y){
		if(!isValid(x,y))return -1;
		return this.pixels[x + y * w];
	}
	
	public void setMonoColor(int r,int g,int b){
		this.colors = new int[4];
		int passes = 4;
		for(int i = 0; i < passes; i++){
			float amt = 0.2f;
			float shade = 1 - (amt * i);
			r *= shade;
			g *= shade;
			b *= shade;
			this.colors[i] = Util.getRGB(r, g, b);
			
		}
	}
	
	public int getCol(int s){
		if(s < 0 || s > 3)s = 3;
		return this.colors[s];
	}
	
	public void fill(int r,int g,int b){
		int rgb = (255 << 24) | (r << 16) | (g << 8) | b;
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				pixels[x + y * w] = rgb;
			}
		}
	}
	
	public void fill(int s){
		for(int x = 0; x < w; x++){
			for(int y = 0; y < h; y++){
				pixels[x + y * w] = this.colors[s];
			}
		}
	}
	
	public void render(){
		this.image.setRGB(0, 0, w, h, pixels, 0, w);
	}
	
	public int getShade(int x,int y){
		int col = this.getPixel(x, y);
		for(int i = 0; i < this.colors.length; i++){
			if(col == this.colors[i])return i;
		}
		return -1;
	}
	
	public void useDefaultColor(){
		this.setMonoColor(175,203,70);
	}
	
}
