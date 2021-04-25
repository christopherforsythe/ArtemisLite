/**
 * 
 */
package game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author simon
 *
 */
public class DevelopmentSystem {

	private Systems systemName;
	private List<Integer> squaresInSystem = new ArrayList<Integer>();
	private boolean isOwned;

	// default constructor
	public DevelopmentSystem() {
		// defaulted to unowned at first
		this.setOwned(false);
	}

	// constructor with args
	public DevelopmentSystem(Systems name) {
		this.systemName = name;

	}

	public Systems getSystemName() {
		return systemName;
	}

	public void setSystemName(Systems name) {
		this.systemName = name;
	}

	public List<Integer> getSquaresInSystem() {
		return squaresInSystem;
	}

	public boolean isOwned() {
		return isOwned;
	}

	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}

	public void addSquareToSystem(int index) {
		this.squaresInSystem.add(index);
	}

}
