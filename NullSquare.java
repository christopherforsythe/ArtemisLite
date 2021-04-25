/**
 * 
 */
package game;

/**
 * @author Danielle Neill
 *
 */
public class NullSquare extends Square {

	/*
	 * default constructor for a NullSquare using superclass setter...
	 */
	public NullSquare(String squareName) {
		this.setSquareName(squareName);
	}

	public void displaySquareDetails() {
		super.displaySquareDetails();
	}

}
