package uk.fls.h2n0.main.core.tiles;

import uk.fls.h2n0.main.util.Loader;

public class VoidTile extends Tile {

	public VoidTile(int id) {
		super(id);
		this.data = Loader.getFrameData(5, this.dataPos);
	}

}
