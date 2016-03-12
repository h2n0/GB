package uk.fls.h2n0.main.core.tiles;

import uk.fls.h2n0.main.util.Loader;

public class WallTile extends Tile {

	public WallTile(int id) {
		super(id);
		this.data = Loader.getFrameData(1, dataPos);
		this.canPassThrough = false;
	}

}
