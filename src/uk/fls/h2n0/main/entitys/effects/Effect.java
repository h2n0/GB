package uk.fls.h2n0.main.entitys.effects;

import uk.fls.h2n0.main.entitys.Entity;

public abstract class Effect extends Entity{
	
	protected int life = 0;

	@Override
	public void update() {
		tick();
		if(life-- <= 0)this.alive = false;
	}
	
	public abstract void tick();

}
