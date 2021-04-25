/**
 * 
 */
package game;

/**
 * @author Danielle Neill
 *
 */
public class PlayableSquare extends Square {
	
	//private String squareSystemName;
	private int squareCost;
	private int squareFee;
	private double ratio;
	private int developmentStatus;
	
	private Systems system;

	/**
	 * Default constructor
	 */
	public PlayableSquare() {
	}
	
	

	public PlayableSquare(String squareName, int squareCost, int squareFee, double ratio, Systems system) {
		super.setSquareName(squareName);

		this.squareCost = squareCost;
		this.squareFee = squareFee;
		this.ratio = ratio;
		this.developmentStatus = 0;
		this.system = system;
	}


	/*
	 * @return the squareSystemName
	public String getSquareSystemName() {
		return squareSystemName;
	}

	 * @param squareSystemName the squareSystemName to set
	 
	public void setSquareSystemName(String squareSystemName) {
		this.squareSystemName = squareSystemName;
	}
	*/

	/**
	 * @return the squareCost
	 */
	public int getSquareCost() {
		return squareCost;
	}

	/**
	 * @param squareCost the squareCost to set
	 */
	public void setSquareCost(int squareCost) {
		this.squareCost = squareCost;
	}

	/**
	 * @return the squareFee
	 */
	public int getSquareFee() {
		return squareFee;
	}

	/**
	 * @param squareFee the squareFee to set
	 */
	public void setSquareFee(int squareFee) {
		this.squareFee = squareFee;
	}

	/**
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
	public int getDevelopmentStatus() {
		return developmentStatus;
	}

	public void setDevelopmentStatus(int status) {
		this.developmentStatus = status;
	}

	public void incrementDevelopmentStatus() {

			// increment the dev status
			this.developmentStatus++;
			// update the square costs and fees
			this.setSquareCost((int) Math.floor(this.getRatio() * this.getSquareCost()));
			this.setSquareFee((int) Math.floor(this.getRatio() * this.getSquareFee()));
	
	}



	public void displaySquareDetails() {
		super.displaySquareDetails();
	}



	public Systems getSystem() {
		return system;
	}



	public void setSystem(Systems system) {

		this.system = system;
	}

}
