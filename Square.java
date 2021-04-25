/**
 * 
 */
package game;

/**
 * @author Danielle Neill, Dan Brown, Simon Harte, Chris Forysthe
 *
 */
public abstract class Square {
	
	private String squareName;

	/**
	 * @return the squareName
	 */
	public String getSquareName() {
		return squareName;
	}

	/**
	 * @param squareName the squareName to set
	 */
	public void setSquareName(String squareName) {
		this.squareName = squareName;
	}
	
	public void displaySquareDetails() {
		System.out.printf("%s\n", getSquareName());
	}

}
