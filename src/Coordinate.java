
public class Coordinate implements Cloneable {

	private int x;
	private int y;

	public Coordinate(int x, int y) {
		if(x >= 0 && x <= Environment.max_X)
			this.x = x;
		else
			System.err.println("x-Coordinate not possible: " + x + " max width: " + Environment.max_X);
		if(y >= 0 && y <= Environment.max_Y)
			this.y = y;
		else
			System.err.println("y-Coordinate not possible: " + y + " max height: " + Environment.max_Y);
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

}