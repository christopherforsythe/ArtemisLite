/**
 * 
 */
package game;

import java.util.ArrayList;

/**
 * Class to represent players
 * 
 * @author Dan Brown, Chris Forsythe, Simon Harte, Danielle Neill,
 *
 */
public class Player {

	private String playerName; // need validation - no names can be the same
	private int resourceBalance;
	private int boardPosition;
	private ArrayList<Integer> ownedSquares = new ArrayList<Integer>();
	private ArrayList<Systems> ownedSystems = new ArrayList<Systems>();

	
	
	

	/**
	 * Default constructor to create player
	 */
	public Player() {
	}

	/**
	 * Constructor with args to create player
	 * 
	 * @param playerName
	 * @param resourceBalance
	 * @param boardPosition
	 * @param ownedSquares
	 */
	public Player(String playerName, int resourceBalance, int boardPosition) {

		this.playerName = playerName;
		this.resourceBalance = resourceBalance;
		this.boardPosition = boardPosition;

	}

	// add index to player object's arraylist of owned squares...
	public void addToSquares(int index) {
		this.ownedSquares.add(index);
	}

	// iterate through list of owned squares in player object to see if it matches a
	// given index...
	public boolean ownsSquare(int index) {

		return ownedSquares.contains(index);
		
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the resourceBalance
	 */
	public int getResourceBalance() {
		return resourceBalance;
	}

	/**
	 * @param resourceBalance the resourceBalance to set
	 */
	public void setResourceBalance(int resourceBalance) {
		this.resourceBalance = resourceBalance;
	}
	

	/**
	 * @return the boardPosition
	 */
	public int getBoardPosition() {
		return boardPosition;
	}

	/**
	 * @param boardPosition the boardPosition to set
	 */
	public void setBoardPosition(int boardPosition) {
		this.boardPosition = boardPosition;
	}
	
	public ArrayList<Integer> getOwnedSquares() {
		return ownedSquares;
	}

	public ArrayList<Systems> getOwnedSystems() {
		return ownedSystems;
	}

	public void addOwnedSystem(Systems sysName) {
		this.ownedSystems.add(sysName);
	}

	public boolean ownsSystem(DevelopmentSystem devSys) {
		return this.ownedSquares.containsAll(devSys.getSquaresInSystem());
	}
	
	public void displayAllStats() {
		
		System.out.println( this.getPlayerName());
		System.out.println("Squares developed : " +this.getOwnedSquares());
		System.out.println("Systems owned : " + this.getOwnedSystems());
		System.out.println("Remaining balance : " + this.getResourceBalance());
		System.out.println();
	}

}
