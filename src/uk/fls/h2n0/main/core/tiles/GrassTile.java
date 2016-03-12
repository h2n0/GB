package uk.fls.h2n0.main.core.tiles;

import uk.fls.h2n0.main.util.Loader;

public class GrassTile extends Tile {

	public GrassTile(int id) {
		super(id);
		this.data = Loader.getFrameData(0, dataPos);
	}

}
