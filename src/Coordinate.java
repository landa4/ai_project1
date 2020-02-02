
public class Coordinate implements Cloneable {

	private int x;
	private int y;
	private int errorValue = -1; // Error Coordinate : for example when testing, if a step outside the field is possible
	private boolean is_legal = true; // is legal if it is inside of the field

	public Coordinate(int x, int y) {
		if(x >= 0 && x <= Environment.max_X)
			this.x = x;
		else{
			this.x = errorValue;
			is_legal = false;
		}
		if(y >= 0 && y <= Environment.max_Y)
			this.y = y;
		else{
			this.y = errorValue;
			is_legal = false;
		}

	}
	
	public boolean equals(Object o) {
		Coordinate c = (Coordinate)o;
		return c.x == x && c.y == y;
	}
	
	public int hashCode() {
		return x ^ y + x*31 + y*53;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) { return null; }
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public boolean get_is_legal(){
		return is_legal;
	}

}