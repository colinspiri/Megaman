import java.awt.Image;
import java.util.ArrayList;

import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.GameFrame;

public class MegamanDropper extends MegamanEnemy {
	protected int attackCooldown = 0;
	protected boolean falling = false;

	public MegamanDropper(int x, int y, Image img, int width, int height) {
		super(x, y, img, width, height, 1, 20, 500);
	}

	public void enemyact() {
		velocity.setY(0); //to take out gravity
		
		attackCooldown();
		if(attackCooldown==0) attack();

	}

	private void attackCooldown() {
		attackCooldown--;
		if(attackCooldown<=0) {
			attackCooldown=0;
		}
		else velocity.setY(-2);
	}
	private void attack() {
		MegamanMegaman player = (MegamanMegaman)GameFrame.getPrimaryControllableObject();
		if(player.getX()+player.getWidth()<getX()) velocity.setX(-1*getSpeed());
		else if(player.getX()>getX()+getWidth()) velocity.setX(getSpeed());
		else if(Logic.distanceFrom(pos.getX(), pos.getY(), player.getX(), player.getY())<=300){
			falling=true;
		}
		if(falling) {
			velocity.setY(5);
			velocity.setX(0);
		}

		if(collideBottom) {
			//removeBlock();
			rise();
		}
	}
	private void removeBlock() {
		ArrayList<Drawable> drawables = GameFrame.getDrawableList();
		double minDistance = Double.MAX_VALUE;
		MegamanBlock closest = null;
		for(int i=0; i<drawables.size(); i++) {
			if(drawables.get(i) instanceof MegamanBlock) {
				MegamanBlock block = (MegamanBlock) drawables.get(i);
				if(Logic.distanceFrom(block.getX()+block.getWidth()/2, block.getY()+block.getHeight()/2, pos.getX()+getWidth()/2, pos.getY()+getHeight()/2)<minDistance) {
					minDistance = Logic.distanceFrom(block.getX(), block.getY(), pos.getX(), pos.getY());
					closest = block;
				}
			}
		}
		GameFrame.removeDrawableObject(closest);
	}
	
	protected void collideCollisionDetection() {
		MegamanMegaman m = (MegamanMegaman) GameFrame.getPrimaryControllableObject();
	
		if(m.getX()+m.getWidth()>=this.getX()+this.getWidth()/5.2667 && m.getX()<=this.getX()+this.getWidth()-this.getWidth()/5.2667 &&
				m.getY()+m.getHeight()>=this.getY()+this.getHeight()/1.26 && m.getY()<=this.getY()+this.getHeight()) {
			m.takeDamage(collideDamage);
			if(this instanceof MegamanDropper) {
				MegamanDropper dropper = (MegamanDropper) this;
				dropper.rise();
			}
		}
	}
	
	public void rise() {
		attackCooldown=100;
		falling=false;
	}
}
