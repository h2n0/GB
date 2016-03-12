package uk.fls.h2n0.main.util;

import java.awt.image.BufferedImage;

import fls.engine.main.io.FileIO;

public class Loader {
	
	public static int[] getFrameData(String data){
		return getFrameData(0,data);
	}
	
	public static int[] getFrameData(int frame,String data){
		int[] res = new int[8 * 8];
		int row = 0;
		data = FileIO.instance.loadFile("res/data/"+data);
		String[] lines = data.split("\n");		
		boolean foundFrame = false;
		for(int i = 0; i < lines.length; i++){
			String line = lines[i];
			if(line.indexOf("@") == 0){
				line = line.substring(1,line.indexOf("#")==-1?line.length():line.indexOf("#"));
				if(Integer.parseInt(line.trim()) == frame){
					foundFrame = true;
				}
			}
			
			if(line.indexOf("[") == 0 && foundFrame){//Frame line
				String needed = line.substring(1, line.length()-1);
				String[] parts = needed.split(",");
				for(int j = 0; j < parts.length; j++){
					res[j + row * 8] = Integer.parseInt(parts[j]);
				}
				row++;
				if(row == 8)break;
			}
		}
		
		if(foundFrame == false){
			System.err.println("Couldn't find fame: " + frame);
		}
		return res;
	}
	
	public static int[] loadFromImage(BufferedImage img){
		int w = img.getWidth();
		int h = img.getHeight();
		int[] res = new int[w * h];
		
		int[] col = new int[]{Util.getRGB(255, 255, 255),Util.getRGB(192, 192, 192),Util.getRGB(127, 127, 127),Util.getRGB(64, 64, 64)};
		
		int[] pixels = new int[w * h];
		img.getRGB(0, 0, w, h, pixels, 0, w);
		for(int i = 0; i < pixels.length; i++){
			int cPos = pixels[i];
			
			if(cPos == 0x00000000){
				res[i] = -1;
			}else if(cPos < col[3]){
				res[i] = 3;
			}else if(cPos >= col[3] && cPos < col[2]){
				res[i] = 2;
			}else if(cPos >= col[2] && cPos < col[1]){
				res[i] = 1;
			}else if(cPos >= col[1]){
				res[i] = 0;
			}
		}
		
		return res;
	}
}
