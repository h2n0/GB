package uk.fls.h2n0.main.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import fls.engine.main.input.Input;
import fls.engine.main.io.FileIO;
import fls.engine.main.util.Camera;
import fls.engine.main.util.Point;
import uk.fls.h2n0.main.core.tiles.Tile;
import uk.fls.h2n0.main.entitys.Entity;
import uk.fls.h2n0.main.entitys.Ghost;
import uk.fls.h2n0.main.entitys.Player;
import uk.fls.h2n0.main.entitys.Slime;
import uk.fls.h2n0.main.util.Renderer;

public class World {

	private Renderer r;
	private List<Entity> entitys;
	private byte[] tiles;
	private byte[] data;
	private final int w, h;
	private Random rand;
	public boolean spawn;

	public class EntitySorter implements Comparator<Entity> {
		@Override
		public int compare(Entity e1, Entity e2) {
			if (e1.pos.getIY() > e2.pos.getIY())
				return 1;
			else if (e1.pos.getIY() < e2.pos.getIY())
				return -1;
			else
				return 0;
		}

	}

	private EntitySorter sorter;

	public World(Renderer r) {
		this(r, 32, 32, false);
	}

	public World(Renderer r, int w, int h, boolean fill) {
		this.r = r;
		this.entitys = new ArrayList<Entity>();
		this.w = w;
		this.h = h;
		this.tiles = new byte[w * h];
		this.data = new byte[w * h];
		this.rand = new Random();
		if (fill) {
			for (int i = 0; i < tiles.length; i++) {
				int x = i % w;
				int y = i / w;
				Tile t = Tile.grass;
				double ra = Math.random() * 50;
				if (x == 0 || y == 0 || x == w - 1 || y == h - 1) {
					t = Tile.wall;
				} else {
					if (ra <= 2) {
						t = Tile.rock;
					} else if (ra >= 48) {
						t = Tile.water;
					}
				}
				setTile(t, x, y);
			}
		} else {
			for (int i = 0; i < tiles.length; i++) {
				int x = i % w;
				int y = i / w;
				Tile t = Tile.grass;
				if (x == 0 || y == 0 || x == w - 1 || y == h - 1) {
					t = Tile.wall;
				}
				setTile(t, x, y);
			}
		}

		this.sorter = new EntitySorter();
		this.spawn = true;

	}

	public World(Renderer r, String fileData) {
		if (fileData == null || fileData == "") {
			throw new RuntimeException("You need to give World file data");
		} else {
			String file = FileIO.instance.loadFile(fileData);
			String[] lines = file.split("\n");
			int my = lines.length;
			int mx = 0;
			for (int i = 0; i < lines.length; i++) {
				String[] line = lines[i].substring(1, lines[i].length()-1).split(",");
				int cmx = 0;
				for(int x = 0; x < line.length; x++){
					cmx ++;
				}
				if(cmx > mx)mx = cmx;
			}
			System.out.println(mx);
			this.r = r;
			this.entitys = new ArrayList<Entity>();
			this.w = mx;
			this.h = my;
			this.tiles = new byte[w * h];
			this.data = new byte[w * h];
			this.rand = new Random();
			
			for (int i = 0; i < lines.length; i++) {
				String[] line = lines[i].substring(1, lines[i].length()-1).split(",");
				int cmx = 0;
				for(int x = 0; x < line.length; x++){
					String c = line[x];
					int f = Integer.parseInt(c);
					setTile(Tile.getById(f), x, i);
					cmx ++;
				}
				if(cmx > mx)mx = cmx;
			}
		}

		this.sorter = new EntitySorter();
		this.spawn = true;
	}

	public void render(Camera c) {
		if (c.ww != this.w * 8) {
			c.ww = this.w * 8;
			c.wh = this.h * 8;
		}

		int cx = c.pos.getIX();
		int cy = c.pos.getIY();
		
		int tiles = 0;
		
		for (int x = 0; x <= c.w; x++) {
			for (int y = 0; y <= c.h; y++) {
				int px = (x * 8) - cx;
				int py = (y * 8) - cy;
				if(onScreen(px,py)){
					for (int i = 0; i < 8 * 8; i++) {
						int dx = (i / 8);
						int dy = (i % 8);
	
						int tdx = px + dx;
						int tdy = py + dy;
						this.r.setPixel(tdx, tdy, getTile(x, y).render(this, x, y)[dx + dy * 8]);
					}
				}
			}
		}

		sortEntitys();

		for (int i = 0; i < this.entitys.size(); i++) {
			Entity e = entitys.get(i);
			int x = e.pos.getIX() - cx;
			int y = e.pos.getIY() - cy;
			int s = 8;

			if(!onScreen(x,y))continue;
			boolean xf = (e.getData() & 0x2) > 0 ? true : false;
			boolean yf = (e.getData() & 0x4) > 0 ? true : false;

			if (e.shadeBase) {// If the entity has a shadow beneath it run the
								// part of code
				for (int xx = 0; xx < e.shadeW; xx++) {
					int px = x + (xf ? 7 - xx + e.shadeX : e.shadeX + xx);
					int py = y + 7;
					int sa = (this.r.getShade(px, py)) ^ 1;
					if (sa > 3)
						sa = 3;
					this.r.setPixel(px, py, sa);

					int div = 0;
					if (Math.abs(e.z) > 0) {
						div += (int) (7 - e.z);
						if (div < 0)
							div = 0;
						if (xx > div && xx < (7 - div)) {
							this.r.setPixel(px, py - 1, sa);
							this.r.setPixel(px, py + 1, sa);
						}
					}
				}
			}

			for (int xx = 0; xx < s; xx++) {// Render the entity after shadow
											// check
				for (int yy = 0; yy < s; yy++) {
					int pix = (xf ? 7 - xx : xx) + (yf ? 7 - yy : yy) * s;
					int col = e.frames[e.getCurrentFrame()][pix];
					this.r.setPixel(x + xx, (y - (int) e.z) + yy, col);
				}
			}
		}
	}

	public void update(Input i) {
		trySpawn(1);
		for (int j = 0; j < w * h / 40; j++) {
			int x = rand.nextInt(w);
			int y = rand.nextInt(w);
			getTile(x, y).update(this, x, y);
		}
		for (int j = 0; j < this.entitys.size(); j++) {
			Entity e = this.entitys.get(j);
			e.update();
			if (!e.alive) {
				e.die();
				this.entitys.remove(j);
			}
		}
	}

	public void addEntity(Entity e) {
		this.entitys.add(e);
		e.w = this;
	}

	public void setTile(Tile t, int x, int y) {
		if (!isValid(x, y) || this.getTile(x, y) == t)
			return;
		this.tiles[x + y * w] = t.id;
	}

	public Tile getTile(int x, int y) {
		if (!isValid(x, y))
			return Tile.grass;
		return Tile.tiles[this.tiles[x + y * w]];
	}

	public void setData(int x, int y, int b) {
		if (!isValid(x, y))
			return;
		this.data[x + y * w] = (byte) b;
	}

	public int getData(int x, int y) {
		if (!isValid(x, y))
			return -1;
		return this.data[x + y * w] & 0xff;
	}

	public boolean isValid(int x, int y) {
		if (x < 0 || y < 0 || x >= w || y >= h)
			return false;
		else
			return true;
	}

	public List<Entity> getEntitysInTile(int x, int y) {
		List<Entity> res = new ArrayList<Entity>();
		for (int i = 0; i < this.entitys.size(); i++) {
			Entity e = this.entitys.get(i);
			if (e instanceof Player)
				continue;
			int ex = e.pos.getIX() / 8;
			int ey = e.pos.getIY() / 8;
			if (ex == x / 8 && ey == y / 8) {
				res.add(e);
			}
		}
		return res;
	}

	public void sortEntitys() {
		Collections.sort(this.entitys, this.sorter);
	}

	public void trySpawn(int amt) {
		if (this.entitys.size() < 25 && this.spawn) {
			Entity e = this.rand.nextFloat() > 0.5 ? new Slime() : new Ghost();
			int x = rand.nextInt(w * 8);
			int y = rand.nextInt(w * 8);

			e.pos = new Point(x, y);

			if (e instanceof Ghost) {
				e.tx = x / 8;
				e.ty = y / 8;
			}
			this.addEntity(e);
		}
	}
	
	public boolean onScreen(int x,int y){
		int s = 8;
		if(x < -s || y < -s || x > 160 + s || y >144 + s)return false;
		return true;
	}
}
