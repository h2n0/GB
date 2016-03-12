package uk.fls.h2n0.main.core.tiles;

import uk.fls.h2n0.main.util.Loader;

public class RockTile extends Tile {

	public RockTile(int id) {
		super(id);
		this.data = Loader.getFrameData(2, dataPos);
		this.canPassThrough = false;
	}

}
