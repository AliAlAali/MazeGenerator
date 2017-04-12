
public class Cell {
	private int x;
	private int y;
	private boolean visited;
	
	private boolean northWall;
	private boolean southWall;
	private boolean westWall;
	private boolean eastWall;
	
	public Cell() {
		northWall = southWall = westWall = eastWall = true;
	}
	
	
	public boolean isNorthWall() {
		return northWall;
	}
	public void setNorthWall(boolean northWall) {
		this.northWall = northWall;
	}
	public boolean isSouthWall() {
		return southWall;
	}
	public void setSouthWall(boolean southWall) {
		this.southWall = southWall;
	}
	public boolean isWestWall() {
		return westWall;
	}
	public void setWestWall(boolean westWall) {
		this.westWall = westWall;
	}
	public boolean isEastWall() {
		return eastWall;
	}
	public void setEastWall(boolean eastWall) {
		this.eastWall = eastWall;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	
}
