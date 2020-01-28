import java.awt.Image;
import java.util.ArrayList;
import org.francisparker.mmaunu.gameengine.AIObject;
import org.francisparker.mmaunu.gameengine.CollisionDetector;
import org.francisparker.mmaunu.gameengine.Drawable;
import org.francisparker.mmaunu.gameengine.DrawableGameObject;
import org.francisparker.mmaunu.gameengine.GameFrame;

public abstract class MegamanObstacle extends AIObject {
	protected static ArrayList<MegamanObstacle> obstacles = new ArrayList<MegamanObstacle>();

	protected boolean touchingTop = false;
	protected boolean touchingBottom = false;
	protected boolean touchingLeft = false;
	protected boolean touchingRight = false;

	public MegamanObstacle(int x, int y, Image img, int width, int height) {
		super(x, y, img, width, height, 0);
		obstacles.add(this);
		collisionDetection();
	}

	public void act() {
		if(MegamanUnit.time>60) translate();
		collisionDetection();
	}

	private void translate() {
		MegamanUnit unit = (MegamanUnit) GameFrame.getPrimaryControllableObject();
		if(unit.getX()>=400 && unit.collideLeft==false && unit.velocity.getX()>0) {
			this.setX((int)(this.getX()-unit.getVelocity().getX()));
		}
	}
	
	private void collisionDetection() {
		//collision detection with other blocks
		for(int i=0; i<GameFrame.getDrawableList().size(); i++) {
			Drawable d = GameFrame.getDrawableList().get(i);
			if(d instanceof MegamanObstacle) {
				MegamanObstacle other = (MegamanObstacle) d;

				//detect if it's touching other obstacles
				if(other.getX()+other.getWidth()==this.getX() && other.getY()==this.getY()) touchingLeft=true;
				if(other.getX()==this.getX()+this.getWidth() && other.getY()==this.getY()) touchingRight=true;
				if(other.getY()+other.getHeight()==this.getY() && other.getX()==this.getX()) touchingTop=true;
				if(other.getY()==this.getY()+this.getHeight() && other.getX()==this.getX()) touchingBottom=true;
			}
		}


		//collision detection with MegamanUnits
		for(int i=0; i<GameFrame.getDrawableList().size(); i++) {
			Drawable d = GameFrame.getDrawableList().get(i);
			//only do collision detection with MegamanUnits
			if(d instanceof MegamanUnit) {
				//do collision detection for each MegamanUnit in the list
				MegamanUnit unit = (MegamanUnit) d;
				MegamanObstacle obstacle = this;
				double unitX = unit.getX();
				double unitY = unit.getY();

				//detect if they collide
				if(unitX+unit.getWidth()>=obstacle.getX()+1 && unitX<=obstacle.getX()+obstacle.getWidth()-1
				&& unitY+unit.getHeight()>=obstacle.getY()+1 && unitY<=obstacle.getY()+obstacle.getHeight()-1 ) {

					//detect which side it collides with, x AND y axis
					boolean bottom = false;
					boolean top = false;
					boolean left = false;
					boolean right = false;

					if(unitY<=obstacle.getY()+obstacle.getHeight()/2 && unit.velocity.getY()>0) bottom=true;
					else if(unitY>obstacle.getY()+obstacle.getHeight()/2) top=true;
					if(unitX<=obstacle.getX()+obstacle.getWidth()/2) left=true;
					else if(unitX>obstacle.getX()+obstacle.getWidth()/2) right=true;

					//detect if it's closer to x axis or y axis
					double distanceToBottom = Logic.distanceFrom(unitX+unit.getWidth()/2, unitY+unit.getHeight()/2, obstacle.getX()+obstacle.getWidth()/2, obstacle.getY());
					double distanceToTop = Logic.distanceFrom(unitX+unit.getWidth()/2, unitY+unit.getHeight()/2, obstacle.getX()+obstacle.getWidth()/2, obstacle.getY()+obstacle.getHeight());
					double distanceToLeft = Logic.distanceFrom(unitX+unit.getWidth()/2, unitY+unit.getHeight()/2, obstacle.getX(), obstacle.getY()+obstacle.getHeight()/2);
					double distanceToRight = Logic.distanceFrom(unitX+unit.getWidth()/2, unitY+unit.getHeight()/2, obstacle.getX()+obstacle.getWidth(), obstacle.getY()+obstacle.getHeight()/2);

					//only collide with side that it's closer to instead of both
					if(distanceToBottom+distanceToTop>distanceToLeft+distanceToRight) {
						bottom=false;
						top=false;
					}
					else {
						left=false;
						right=false;
					}

					//don't detect if it's connected to another obstacle
					if(touchingTop) bottom=false;
					if(touchingBottom) top=false;
					if(touchingRight) right=false;
					if(touchingLeft) left=false;

					//transfer boolean to MegamanUnit and set x and y to edge of obstacle
					if(bottom) {
						unit.collideBottom();
						unit.setY(obstacle.getY()-unit.getHeight()+1);
						if(unit.velocity.getY()>=0) unit.velocity.setY(0);
					}
					if(top) {
						unit.collideTop();
						unit.setY(obstacle.getY()+obstacle.getHeight()-1);
						if(unit.velocity.getY()<=0) unit.velocity.setY(0);
					}
					if(left) {
						unit.collideLeft();
						unit.setX(obstacle.getX()-unit.getWidth()+1);
						unit.velocity.setX(0);
					}
					if(right) { 
						unit.collideRight();
						unit.setX(obstacle.getX()+obstacle.getWidth()-1);
						unit.velocity.setX(0);
					}

				}
			}
		}
	}

	public static ArrayList<MegamanObstacle> getObstacles() {
		return obstacles;
	}
}
