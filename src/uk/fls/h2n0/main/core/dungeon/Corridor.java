package uk.fls.h2n0.main.core.dungeon;

import uk.fls.h2n0.main.core.Dungeon;
import uk.fls.h2n0.main.core.tiles.Tile;

public class Corridor {

	private Room r1, r2;

	public Corridor(Room r1, Room r2) {
		this.r1 = r1;
		this.r2 = r2;
	}

	public void decorate(Dungeon dun) {
		if (r1.isVertSplit()) {// Vertical split so corridors along the horz
			int r1miy = r1.y + r1.iy;
			int r1may = r1miy + r1.ih + 1;

			int r2miy = r2.y + r2.iy;
			int r2may = r2miy + r2.ih + 1;

			int d = (r2miy - r1may);
			int offX = (r1.x + r1.ix) + r1.w / 2;

		} else {// Horizontal split so corridors along the vert
			int r1mix = r1.x + r1.ix;
			int r1max = r1mix + r1.iw + 1;

			int r2mix = r2.x + r2.ix;
			int r2max = r2mix + r2.iw + 1;

			int d = (r2mix - r1max);
			int offY = r1.y + r1.iy;
			int ex = r2.h/4;
			while(offY <= r2.y + r2.iy + ex)offY++;
			
			if(d > 0){
				for (int i = -1; i <= d; i++) {
					dun.setTile(Tile.rock, r1max + i, offY);
				}
			}
		}
	}
}
