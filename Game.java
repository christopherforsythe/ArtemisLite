/**
 * 
 */
package game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/**
 * Class to play ArtemisLite virtual boardgame The game is simulated using text
 * based input and output
 * 
 * @author Dan Brown, Chris Forsythe, Simon Harte, Danielle Neill,
 *
 */

/*
 * TODO v v v v v CLASS DOWN BELOW v v v v v
 * 
 * give squares proper details
 * 
 * Main game loop:
 * 
 * pop next player into currentPlayer
 * 
 * dice roll - one return or two? a method will only allow one returned value so
 * maybe a dice roll method that rolls one 6 sided die, but we just call it
 * twice to display to the player, then add results
 * 
 * move player to new square
 * 
 * if player passed go (i'll have a look at this algorithm): update their
 * resources and notify them
 * 
 * if square is playable:
 * 
 * if square is unowned: if player has enough resources: CHRIS
 * 
 * prompt for player to purchase if player declines:
 * 
 * auction to other players prompt them to purchase
 * 
 * else: play moves to next step
 * 
 * 
 * 
 * else:
 * 
 * calculate ownership fee
 * 
 * ....check current players resources - display info - 'current player has
 * insufficient resources, payment of fee will bankrupt current player and end
 * the game..' Promt square owner with options 'Accept 'current players'
 * resources? Yes (end game) / No (waive fee & continue game)...
 * 
 * If square owner types 'No' play moves to next step
 * 
 * else: player is charged fee and gameplay ends.
 * 
 * else :
 * 
 * notify player they have landed on a non-playable square
 * 
 * if player has a fully owned system: prompt for development if player
 * declines: play moves to next step else: (display squares player owns player
 * chooses square to develop) loop until player declines to develop any more
 * squares
 * 
 * end of main loop
 * 
 */
public class Game {

	/**
	 * The minimum number of players allowed in gameplay
	 */
	public static final int MINIMUM_NUMBER_PLAYERS = 2;
	/**
	 * The maximum number of players allowed in gameplay
	 */
	public static final int MAXIMUM_NUMBER_PLAYERS = 4;

	/**
	 * The default resource balance per player
	 */
	public static final int DEFAULT_BALANCE = 10000; // check later re: what resources we design
	/**
	 * The default board position for each player
	 */
	public static final int DEFAULT_POSITION = 0; // pass this as initial index in board array?

	/**
	 * The maximum level of square development
	 */
	public static final int MAXIMUM_SQUARE_DEVELOPMENT = 5;
	/**
	 * The efault resource update when a player passes GO
	 */
	public static final int PASS_GO_AMOUNT = 200;

	/**
	 * The sides of the die used in the game.
	 */
	public static final int DIE_SIDES = 6;
	/**
	 * Queue to hold the list of players, set as a LinkedList
	 */
	public static Queue<Player> players = new LinkedList<Player>();

	/**
	 * The scanner used for input across the game
	 */
	public static Scanner scanner;
	/**
	 * The global board object
	 */
	public static Board myBoard;
	/**
	 * The array used to output the square development levels in a human-friendly
	 * format
	 */
	public static String[] developmentLevels = { "unowned", "owned", "at Level 1", "at Level 2", "at Level 3",
			"fully developed!" };
	/**
	 * A boolean used to determine whether gameplay should continue
	 */
	public static boolean gameActive;
	/**
	 * A boolean used to determine whether the game has been won
	 */
	public static boolean win = false;

	// Start of main method
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		myBoard = new Board();
		// 1.Display 'welcome to artemislite'

		System.out.println(

				"		   _____ ________________________________   _____  .___  _________\r\n"
						+ "		  /  _  \\\\______   \\__    ___/\\_   _____/  /     \\ |   |/   _____/\r\n"
						+ "		 /  /_\\  \\|       _/ |    |    |    __)_  /  \\ /  \\|   |\\_____  \\ \r\n"
						+ "		/    |    \\    |   \\ |    |    |        \\/    Y    \\   |/        \\\r\n"
						+ "		\\____|__  /____|_  / |____|   /_______  /\\____|__  /___/_______  /\r\n"
						+ "		        \\/       \\/                   \\/         \\/            \\/ \r\n"
						+ "		.____    ._________________________\r\n"
						+ "		|    |   |   \\__    ___/\\_   _____/\r\n"
						+ "		|    |   |   | |    |    |    __)_ \r\n"
						+ "		|    |___|   | |    |    |        \\\r\n"
						+ "		|_______ \\___| |____|   /_______  /\r\n"
						+ "		        \\/                      \\/ ");

		// 2.display game summary - '
		System.out
				.println("\n\t\t| ArtemisLite is a virtual board game for 2-4 players that simulates NASA's real    |\n"
						+ "\t\t| lunar mission 'Artemis' to land the first woman and next man on the moon by 2024. |\n\n"
						+ "\t| The board represents 4 systems, each consisting of elements that can be purchased and developed. |\n"
						+ "\t| The systems are: ARTEMIS GENERATION SPACESUITS, LUNAR LANDER, SPACE LAUNCH SYSTEM, GATEWAY.      |\n"
						+ "\t| The aim is to work as a team, applying technical and logistal skills to develop all 4 systems.   |\n"
						+ "\t| With each completed system, your team gets one step closer to lift off!!                         |\n"
						+ "\t| Do you have what it takes to get to the moon?                                                    |\n");

		// 3. display game instructions - is there a way to display all for start of
		// game?
		displayInstructions();

		String response;
		do {
			System.out.println("Are you ready to play? Y/N");
			response = scanner.nextLine();
			if (response.equalsIgnoreCase("Y")) {
				// Proceed to player set up
				break;
			}
		} while (!response.equalsIgnoreCase("Y"));
		playerSetup();

		// 6.initialise game based on player array being populated...
		startGame();// game status

		// turn method called to test it
		do {
			turn();

		} while (gameActive);
		scanner.close();
		// WE'RE IN THE ENDGAME NOW
		if (win) {
			System.out.println(
					"MISSION ACCOMPLISHED!! NASA ARTEMISLITE HAS BEEN A SUCCESS. WE HAVE MADE IT TO THE MOON!\n");
			System.out.println("Player stats\n");

			for (Player p : players) {
				p.displayAllStats();
			}

		} else {
			System.out.println("MISSION FAILED... BETTER LUCK NEXTIME!");
		}
	}

	/**
	 * Method that displays game instructions to user
	 */
	public static void displayInstructions() {
		int menuOption = 0;
		do {
			try {
				System.out.println("\n\tINSTRUCTIONS" + "\n\t------------\n\t"
						+ "Select one of the below options to find out more:\t");
				System.out.println("\t1. Roll Dice");
				System.out.println("\t2. Passing GO");
				System.out.println("\t3. Purchasing a square");
				System.out.println("\t4. Developing a square");
				System.out.println("\t5. Landing on an owned square");
				System.out.println("\t6. Landing on the Black Hole");
				System.out.println("\t7. Exit instructions and return to play\n");

				menuOption = scanner.nextInt();

				switch (menuOption) {
				case 1:
					System.out.println("\t| ROLLING THE DICE |\n\t"
							+ "On each turn, player will be given the option to roll two dice.\n\t"
							+ "Based on the sum of the two values, the player moves forward on the board.\n");
					break;

				case 2:
					System.out.println(
							"\t| PASSING GO |\n\t" + "Every time a player completes a full iteration of the board,\n\t"
									+ "passing go, their resources are updated by " + PASS_GO_AMOUNT + ".\n");
					break;

				case 3:
					System.out.println("\t| PURCHASING A SQUARE |\n\t"
							+ "When a player lands on an unowned square they are given the opportunity to gain ownership\n\t"
							+ "by donating their resources. Cost varies depending on the system.  \n\t"
							+ "If player chooses not to buy it, it is auctioned to other players.\n");
					break;

				case 4:
					System.out.println("\t| DEVELOPING A SQUARE |\n\t"
							+ "Once a player owns all of the squares in a system, they are given the opportunity to\n\t"
							+ "begin devlopment by donating more of their resources. There are three levels of development\n\t"
							+ "per square. The player does not have to land on the specific square they want to develop.\n\t"
							+ "On each turn they will be given the option to develop any squares in the owned system.\n");
					break;

				case 5:
					System.out.println("\t| LANDING ON AN OWNED SQUARE |\n\t"
							+ "If a player lands on a square owned by another player, a fee is expected to be paid to the owner.\n\t"
							+ "The fee is calculated depending on the level of development.    \n\t"
							+ "If player has insufficient resources to pay the fee, the square owner has the option to wave the fee.\n\t"
							+ "If owner does not wave the fee and a player is made bankrupt the game will end for all players!\n");
					break;

				case 6:
					System.out.println(
							"\t| BLACK HOLE |\n\t" + "The Black Hole is a null square, if a player lands on it\n\t"
									+ "their turn is void and play continues to next player.\n");
					break;
				case 7:
					// return to game
					break;

				default:
					break;
				}

			} catch (InputMismatchException inputException) {
				System.out.println("Input error, please try again using options listed above!");
				scanner.nextLine();
			}
		} while (menuOption != 7);
		scanner.nextLine();
	}

	/**
	 * Method that triggers the game to start
	 */
	public static void startGame() {
		// haven't set END GAME condition yet, let's *roll* with this for now...
		if (!players.isEmpty()) {

			for (Player player : players) {
				player.setResourceBalance(DEFAULT_BALANCE);
			}
			gameActive = true;

		} else {
			System.err.println("Game cannot start without player setup, please set up players!");
		}

	}

	/**
	 * Method to handle player turns
	 */
	public static void turn() {

		// add current player to the currentplayer var
		Player currentPlayer = players.poll();

		System.out.println("\nIt is " + currentPlayer.getPlayerName() + "'s turn!\n");

		System.out.println("You are currently on "
				+ myBoard.getSquares().get(currentPlayer.getBoardPosition()).getSquareName() + " square");

		displayMenu(currentPlayer);

	}

	/**
	 * This utility method returns a player's new index on the board.
	 * 
	 * @param rollResult     int the result of players dice rolls
	 * @param playerPosition int players current position on the board
	 * @return int players updated index
	 */
	public static int movePlayer(int rollResult, Player currentPlayer) {

		int newIndex;
		int playerPosition = currentPlayer.getBoardPosition();
		int boardLength = myBoard.getSquares().size();
		// check if the player has completed full lap of the board
		if ((playerPosition + rollResult) < boardLength) {
			newIndex = playerPosition + rollResult;

			// invoke pass go
			passGo(currentPlayer);

		} else {
			newIndex = (playerPosition + rollResult) - boardLength;
		}

		return newIndex;
	}

	/**
	 * Dice rolling method. Returns the result of rolling one six-sided die.
	 * 
	 * @return int dice roll result
	 */
	public static int rollDice() {
		return (new Random().nextInt(DIE_SIDES)) + 1;
	}

	/**
	 * Method to set up players. Takes in the player details and populates the
	 * players Queue.
	 */
	public static void playerSetup() {

		int playerCount = 0;

		do {
			System.out.println("How many players are playing today?\n\nPlease enter a number between 2 and 4:");
			try {
				// take the player input
				playerCount = scanner.nextInt();

			} catch (InputMismatchException e) {
				// HANDLING THE EXCEPTION IN CASE OF LETTERS ENTERED
				scanner.nextLine();
				System.out.println("Please enter an integer between 2 and 4!\n");
				// begin do ... while again
				continue;
			}

			// check if playerCount is invalid - remind player to enter correct values
			if (playerCount < MINIMUM_NUMBER_PLAYERS || playerCount > MAXIMUM_NUMBER_PLAYERS) {
				System.out.println("Please enter an integer between 2 and 4!\n");
			}
			scanner.nextLine();
		} while (playerCount < MINIMUM_NUMBER_PLAYERS || playerCount > MAXIMUM_NUMBER_PLAYERS);

		// Add the players
		for (int loop = 0; loop < playerCount; loop++) {

			String playerName;

			// using a do ... while because it needs to happen at least once
			do {

				System.out.println("What is the name of player " + (loop + 1) + "?");
				playerName = scanner.nextLine();

				// using the utility method below, check if the player's name is unique
				if (!uniquePlayerName(playerName)) {

					// if name is not unique, return to the start
					System.out.println("Players must have unique names!\n");
					continue;
				} else {

					// instantiate new player object
					Player player = new Player();
					// set the name
					player.setPlayerName(playerName);

					// add the player to the players Queue
					players.add(player);
					break;
				}
			} while (!uniquePlayerName(playerName));

		}
		System.out.println();

		// iterate through to show the players
		System.out.println("The players are: \n");
		int count = 0;
		for (Player player : players) {
			count++;
			System.out.println("Player " + count + ": " + player.getPlayerName());
		}
	}

	/**
	 * This is a utility method to ensure the players names are unique
	 * 
	 * @param name a String of the players name
	 * @return true if the name is unique to the players Queue, false if it already
	 *         exists
	 */
	public static boolean uniquePlayerName(String name) {
		// set a boolean flag
		boolean uniqueName = true;
		// iterate through players in the queue
		for (Player player : players) {
			// if any players have the entered name, the flag gets set to false
			if (player.getPlayerName().trim().equalsIgnoreCase(name)) {
				uniqueName = false;
			}
		}
		return uniqueName;
	}

	/**
	 * Utility method to update players balance when they have passed go
	 * 
	 * @param player
	 */
	public static void passGo(Player player) {

		int updatePlayerBalance = player.getResourceBalance() + PASS_GO_AMOUNT; // player will always default pass go at
																				// start of game?
		player.setResourceBalance(updatePlayerBalance);

		System.out.println(player.getPlayerName() + ", your new balance is : " + player.getResourceBalance());

	}

	/**
	 * Utility method to check whether a square is playable or not
	 * 
	 * @param int index - players current location on the board as an int
	 * @return boolean value, true if matching square index is an instance of a
	 *         playable square I.E, not the go square or null square
	 */
	public static boolean squareIsPlayable(int index) {

		if (myBoard.getSquares().get(index) instanceof PlayableSquare) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Utility method to check if a square is owned by any players
	 * 
	 * @param int index - players current location on the board as an int
	 * @return boolean value, 'true' if any players in the player arraylist have
	 *         matching index in their owned squares
	 */
	public static boolean squareIsOwned(int index) {

		for (Player player : players) {
			if (player.ownsSquare(index)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Utility method to check what player owns a specific square
	 * 
	 * @param int index - players current location on the board as an int
	 * @return String literal, name of player from players arraylist that has
	 *         matching index in their owned squares
	 */
	public static Player whoOwnsSquare(int index) {

		String notFound = "Owner not found.";
		Player playerOwner = null;

		for (Player player : players) {
			if (player.ownsSquare(index)) {
				playerOwner = player;
			}
		}

		if (playerOwner == null) {
			System.out.println(notFound);
		}

		return playerOwner;

	}

	/**
	 * Method enables player to purchase an unowned square
	 * 
	 * @param currentPlayer
	 */
	public static void purchaseSquare(Player currentPlayer) {

		// NEED TO FIX FLOW - IF USER SELECTS 'N' WHAT HAPPENS?

		String confirmPurchase;
		int currentPosition = currentPlayer.getBoardPosition();
		Square currentSquare = myBoard.getSquares().get(currentPosition);
		int squareCost = myBoard.getSquare(currentPosition).getSquareCost();
		int playerBalance = currentPlayer.getResourceBalance();

		System.out.println(myBoard.getSquares().get(currentPosition).getSquareName() + " costs " + squareCost);
		System.out
				.println("Do you want to purchase " + myBoard.getSquares().get(currentPosition).getSquareName() + "?");

		System.out.println("Y/N");
		confirmPurchase = scanner.nextLine();

		if (confirmPurchase.equalsIgnoreCase("Y")) {
			// Proceed to purchase
			// Thread pauseBeforeMenu = new Thread();

			System.out.println("Your current balance is " + playerBalance);

			// System.err.println("You are purchasing " +
			// myBoard.getSquare(currentPosition).getSquareName() + ".");

			// Check if player has enough resources to continue
			if (squareCost <= playerBalance) {

				System.out.printf("\nYour new balance will be %d\n", currentPlayer.getResourceBalance() - squareCost);
				System.out.println("Do you wish to proceed with purchase?");
				System.out.println("Y/N");

				confirmPurchase = scanner.nextLine();

				if (confirmPurchase.equalsIgnoreCase("Y")) {

					System.out.println("Purchasing square...");

					// update balance
					currentPlayer.setResourceBalance(playerBalance - squareCost);

					// update player collection
					currentPlayer.addToSquares(currentPosition);

					// increment square development level
					myBoard.getSquare(currentPosition).incrementDevelopmentStatus();

					System.out.println("\nYou currently own :        \t");
					for (Integer ind : currentPlayer.getOwnedSquares()) {

						myBoard.getSquare(ind).displaySquareDetails();

					}

				} else {
					// return to normal game flow

				}

			} else {

				// purchaseFlag = false;
				System.out.println(currentPlayer.getPlayerName() + " - You have insufficient funds for this purchase.");

			}

		} else {

			System.out.println("Auctioning square to other players...");

			// auction to other players? - STILL TO DO

			auction(currentSquare, currentPlayer);
		}

	}

	/**
	 * Method that auctions the current square to the other players
	 * 
	 * @param currentSquare
	 * @param currentPlayer
	 */
	public static void auction(Square currentSquare, Player currentPlayer) {

		String confirmPurchase;
		int currentPosition = currentPlayer.getBoardPosition();
		String squareName = currentSquare.getSquareName();
		int price = myBoard.getSquare(currentPosition).getSquareCost();

		boolean purchased = false;
		int count = 0;

		do {
			Player customer = players.poll();

			if (customer.getResourceBalance() >= price) {

				System.out.println(squareName + " costs " + price + " tokens");
				System.out.println("Your current resource balance is " + customer.getResourceBalance());
				System.out.println(customer.getPlayerName() + " would you like to purchase " + squareName + "?\n");
				System.out.println("Y/N?");
				confirmPurchase = scanner.next();

				if (confirmPurchase.equalsIgnoreCase("Y")) {

					System.out.println("Purchasing square...");

					// update balance
					customer.setResourceBalance(customer.getResourceBalance() - price);

					// update player collection
					customer.addToSquares(currentPosition);

					System.out.println("\nYou currently own :        \t");
					for (Integer ind : customer.getOwnedSquares()) {

						myBoard.getSquare(ind).displaySquareDetails();

					}
					purchased = true;

				} else {
					System.out.println(
							customer.getPlayerName() + " has declined the offer. Auctioning to next player...");
					count++;
				}
			} else {
				System.out.println(
						customer.getPlayerName() + " you have insufficient funds! Auctioning to next player...");
				count++;
			}

			players.add(customer);

		} while (!(purchased || count >= players.size()));

		shuffle(players.size(), count);

		for (Player player : players) {
			System.out.println(player.getPlayerName());
		}
	}

	/**
	 * Method to shuffle players
	 * 
	 * @param size  The size of the remaining players Queue
	 * @param count How many iterations of the auction loop have occurred
	 */
	public static void shuffle(int size, int count) {

		// the amount of shuffle required
		int shuffle = (size - count) + 1;

		if (shuffle != 0) {

			// This rotates the queue by polling and adding players until the
			// queue is back in order
			for (int loop = 0; loop <= shuffle; loop++) {
				Player player = players.poll();
				players.add(player);
			}
		}
	}

	/**
	 * Iterates through the systems checking each against the player's owned
	 * squares. If all the squares from the system are found within a player's owned
	 * squares, then that player owns the system and its Enum Systems name value is
	 * added to the player's owned systems arraylist. The system is updated with an
	 * owned flag.
	 * 
	 * If no ownership is found, play continues
	 * 
	 * @param currentPlayer
	 */
	public static void setPlayerOwnedSystems(Player currentPlayer) {
		for (int loop = 0; loop < myBoard.getBoardSystems().size(); loop++) {
			if (currentPlayer.ownsSystem(myBoard.getBoardSystems().get(loop))) {
				currentPlayer.addOwnedSystem(myBoard.getBoardSystems().get(loop).getSystemName());
				myBoard.getBoardSystems().get(loop).setOwned(true);
			}
		}
	}

	/**
	 * Menu is displayed to current player at beginning of turn
	 */
	public static void displayMenu(Player currentPlayer) {

		// the flag for having landed on a purchasable square
		boolean purchaseValid = false;

		// the flag for the dice roll
		boolean diceRolled = false;

		// have to initialise this for the do.. while to work
		int menuOption = 0;

		/*
		 * for (int count = 0; count < myBoard.getSquares().size(); count++) { if
		 * (myBoard.getSquares().get(count) instanceof PlayableSquare) {
		 * currentPlayer.addToSquares(count);
		 * myBoard.getSquare(count).incrementDevelopmentStatus(); }
		 * 
		 * }
		 */

		do {
			// A check to automatically set owned systems when they happen
			setPlayerOwnedSystems(currentPlayer);
			try {
				int currentPosition = currentPlayer.getBoardPosition();
				System.out.println();
				System.out.println(currentPlayer.getPlayerName() + ", please select an option below:\n");
				System.out.println("1. Roll Dice");
				System.out.println("2. Manage Developments");
				System.out.println("3. End Turn");
				System.out.println("4. End Game");
				System.out.println("5. Display Instructions");
				if (!squareIsOwned(currentPosition) && squareIsPlayable(currentPosition)
						&& !(currentPlayer.ownsSquare(currentPosition))) {
					System.out.println("6. Purchase Square");
					purchaseValid = true;

				}

				menuOption = scanner.nextInt();

				// clear the buffer
				scanner.nextLine();
				switch (menuOption) {
				case 1:
					if (!diceRolled) {
						// set the flag true
						diceRolled = true;

						// roll the dice - the rollDice method just returns the roll of one dice
						int rollOne = rollDice();
						int rollTwo = rollDice();

						// add them
						int totalDiceRoll = rollOne + rollTwo;

						// inform the player
						System.out.println(currentPlayer.getPlayerName() + ", you rolled a " + rollOne + " and a "
								+ rollTwo + ". This gives you a total of " + totalDiceRoll + ".");

						// create the new index
						int newIndex = movePlayer(totalDiceRoll, currentPlayer);

						// set the players new position with the updated index
						currentPlayer.setBoardPosition(newIndex);

						System.out.println(currentPlayer.getPlayerName() + " is now on "
								+ myBoard.getSquares().get(newIndex).getSquareName() + " square.");
						if (squareIsOwned(newIndex)) {
							payFee(currentPlayer);
						}
						break;

					} else {

						System.out.println("You have already rolled the dice!\n");
						break;
					}

				case 2:
					manageDevelopments(currentPlayer);
					myBoard.getBoardSystems();
					break;
				case 3:
					break;
				case 4:
					System.out.println("You have decided to end the game, interrupt the countdown to cancel.");
					quitGame();
					break;
				case 5:
					displayInstructions();
				case 6:
					if (purchaseValid) {
						purchaseSquare(currentPlayer);
					}
					break;
				default:
					break;
				}

			} catch (InputMismatchException inputException) {
				System.err.println("Input error, please try again using options listed above!");
				scanner.nextLine();
			}

			// check to see if any players have gone bust
			if (arePlayersBankrupt(currentPlayer)) {
				menuOption = 3;
				// if a player has gone bankrupt the game is lost, and the win boolean stays
				// false
				gameActive = false;
			}

			// check if all systems are developed
			if (myBoard.allSystemsDeveloped()) {

				menuOption = 3;
				gameActive = false;
				win = true;
			}

		} while (menuOption != 3);

		players.add(currentPlayer);

	}

	public static boolean arePlayersBankrupt(Player currentPlayer) {
		boolean bankrupt = false;
		for (Player player : players) {
			if (player.getResourceBalance() < 0) {
				bankrupt = true;
			}
		}
		if (currentPlayer.getResourceBalance() < 0) {
			bankrupt = true;
		}
		return bankrupt;
	}

	/**
	 * This method allows the current player to manage the squares and systems they
	 * own
	 * 
	 * @param currentPlayer
	 */
	public static void manageDevelopments(Player currentPlayer) {

		// refers to the list of systems
		List<DevelopmentSystem> systems = myBoard.getBoardSystems();

		// refers to the list of squares
		List<Square> squares = myBoard.getSquares();

		// if the player doesn't own any systems nothing happens
		if (currentPlayer.getOwnedSystems().isEmpty()) {
			System.out.println(currentPlayer.getPlayerName() + " does not fully own any systems!");
		} else {

			// This is how the player selects the square they want to develop
			int squareSelect;
			do {
				System.out.println(currentPlayer.getPlayerName()
						+ "'s developments:\n\nPlease enter the number of the square you want to develop or 0 to exit:");

				// iterate through the systems
				for (int loop = 0; loop < systems.size(); loop++) {
					// if the current player owns the system, display information
					if (currentPlayer.ownsSystem(systems.get(loop))) {
						// display the system name
						System.out.println(systems.get(loop).getSystemName());
						// output all the possible squares in the system and their index for the
						// current player to choose
						for (Integer squareIndex : systems.get(loop).getSquaresInSystem()) {
							int developmentLevel = myBoard.getSquare(squareIndex).getDevelopmentStatus();
							System.out.println("\t" + squareIndex + ". " + squares.get(squareIndex).getSquareName()
									+ " is " + developmentLevels[developmentLevel]);
						}
					}
				}

				// exit clause
				System.out.println("\n\t0 to exit");
				squareSelect = scanner.nextInt();
				scanner.nextLine();
				// check the square is not already fully developed
				if (myBoard.getSquares().get(squareSelect) instanceof PlayableSquare) {
					if (myBoard.getSquare(squareSelect).getDevelopmentStatus() == MAXIMUM_SQUARE_DEVELOPMENT) {
						System.out.println(
								myBoard.getSquare(squareSelect).getSquareName() + " is already fully developed!");
					} else {
						// call the method to develop the square
						developSquare(currentPlayer, squareSelect);
					}
				}				
				

			} while (squareSelect != 0);
		}

	
	}

	/**
	 * Allows development of the chosen square
	 * 
	 * @param currentPlayer the player object reference
	 * @param index         the integer index of the square to develop
	 */
	public static void developSquare(Player currentPlayer, int index) {

		String confirmDevelopment;

		// reference to current square for simplicity
		// as a playablesquare to allow access to all methods
		PlayableSquare squareToDevelop = myBoard.getSquare(index);
		int squareCost = squareToDevelop.getSquareCost();
		int playerBalance = currentPlayer.getResourceBalance();
		String squareName = squareToDevelop.getSquareName();

		System.out.println("Your current balance is " + playerBalance);
		System.out.println(squareName + " costs " + squareCost);
		System.out.println("Do you want to develop " + squareName + "?");

		// trigger to end loop
		boolean developed = false;
		System.out.println("Y/N");
		confirmDevelopment = scanner.nextLine();
		do {
			try {

				if (confirmDevelopment.equalsIgnoreCase("Y")) {

					// Check if player has enough resources to continue
					if (squareCost <= playerBalance) {

						System.out.printf("\nYour new balance will be %d\n", playerBalance - squareCost);
						System.out.println("Do you wish to proceed with development?");
						System.out.println("Y/N");

						confirmDevelopment = scanner.nextLine();

						if (confirmDevelopment.equalsIgnoreCase("Y")) {

							System.out.println("Developing square...");

							// update balance
							currentPlayer.setResourceBalance(playerBalance - squareCost);

							// increment the square's development
							squareToDevelop.incrementDevelopmentStatus();
							developed = true;
							System.out.println("\n" + squareName + " is now "
									+ developmentLevels[squareToDevelop.getDevelopmentStatus()]);

						} else {
							developed = true;
						}

					} else {

						System.out.println(
								currentPlayer.getPlayerName() + " - You have insufficient funds for this development.");

						// kinda cheating with these bools but it allows for exiting the loop
						developed = true;
					}
				} else {
					developed = true;
				}
			} catch (InputMismatchException exception) {
				System.out.println("Incorrect input!");
			}
		} while (!developed);
	}

	public static void payFee(Player currentPlayer) {

		int currentPosition = currentPlayer.getBoardPosition();
		Player owner = whoOwnsSquare(currentPosition);
		int fee = myBoard.getSquare(currentPosition).getSquareFee();
		int ownerBalance = whoOwnsSquare(currentPosition).getResourceBalance();
		int playerBalance = currentPlayer.getResourceBalance();

		// check who owns square
		System.out.println("This square is owned by " + owner.getPlayerName());
		// inform player of fee
		System.out.println("The fee for this square is : " + fee);
		// adjust player balance accordingly
		System.out.println("Your new balance is : " + (playerBalance - fee));
		currentPlayer.setResourceBalance(playerBalance - fee);
		// update owners balance
		System.out.println(owner.getPlayerName() + "'s new balance is : " + (ownerBalance + fee));
		owner.setResourceBalance(ownerBalance + fee);

	}

	public static void quitGame() {

		EndGame endGame = new EndGame();
		Thread thread = new Thread(endGame);

		// countdown to end game state...
		thread.start();

		if (thread.isAlive() && scanner.next() != null) {
			thread.interrupt();
			System.err.println("Good choice! Let's keep playing...");
		} else {
			players.removeAll(players);
			gameActive = false;
		}

		// LIFT OFF
		// System.out.println("\r\n"+" ``` ``` `` `` ``` ``` `` `` ``` ``` `` `` `` ```
		// `` `` `` ``` `` `` ``\r\n"+"`` `` `` ``` `` `` `` ``` `` `` `` ``` `` `` ``
		// ``` ``` `` `` ``` ``` \r\n"+" ``` ``` ``+yyssoo+/:-.``` `` `` ``` `` `` `` ``
		// ``` `` `` `` ``` `` `` ``\r\n"+" ``` `` ``sMhhhhdddmmmNmdys+:.` `` `` `` ``
		// `` ``` `` `` `` `` `` `` `` ``\r\n"+"`` `` `` /MhyyyyyyyyyyyyhhdmmNdyo/.` ``
		// `` ``` `` `` `` ``` ``` `` `` ``` ``` \r\n"+" ``` ```
		// ```NmyyyyyyyyyyyyyyyyyyyhdmNmho- ``` `` `` `` ``` `` `` `` ``` `` ``
		// ``\r\n"+"`` `` `` `oMhyyyyyyyyyyyyyyyyyyyyyyyhdmNh+.`` ``` ``` `` `` ``` ```
		// `` `` ``` ``` \r\n"+" ``` ``` `` `mmyyyyyyyyyyyyyyyyyyyyyyyyyyyhdNmo-` `` ``
		// ``` `` `` `` ``` `` `` ``\r\n"+"`` `` ``
		// ``/MdyyyyyyyyyyyyyyyyyyyyyyyyyyyyyhdNmo.` `` `` `` ``` ``` `` `` ``` ```
		// \r\n"+" ``` ``` `` `sMhyyyyyyyyyyydmNNMNNmdhyyyyyyyyyyhmNh:` ``` ``` `` `` ``
		// ``` `` `` ``\r\n"+" `` ``` `` `hNhyyyyyyyyhNMMmyoshNMMmyyyyyyyyyyyydNm/` ``
		// ``` `` `` `` ``` `` `` ``\r\n"+"`` `` `` ``` `dNhyyyyyyymMMs`
		// `..sMMmyyyyyyyyyyyyyhNmoo+++/////:-:-` ``` `` `` ``` ``` \r\n"+" ``` ``` ``
		// ```dNhyyyyyymMM+ `....MMNyyyyyyyyyyyyyyyhNMMNNNNNNNNNNM+`` `` ``` `` ``
		// ``\r\n"+"`` `` `` ```
		// `.hNhyyyyyhNMNo....-yMMdyyyyyyyyyyyyyyyyydMMNNNNNNNNNNM: ``` `` `` ``` ```
		// \r\n"+" ``` ``` `` `` `yMdyyyyyhmMMMmdmMMMdyyyyyyyyyyyyyyyyyyyhmMNNNNNNNNNNN-
		// `` ``` `` `` ``\r\n"+"`` `` `` ``` ``
		// /NmyyyyyyydmmNmmdyyyyyyyyyyyyyyyyyyyyyyydMNNNNNNNNNMN.`` `` `` ``` ```
		// \r\n"+"`` `` ``` ` `` -dNhyyyyyyyyyyyyyyyyyydMMmhyyyyyyyyyyyyyhMMNNNNNNNNMm`
		// ``` `` `` `` \r\n"+" ``` `` `` `` ```
		// `oNmyyyyyyyyyyyyyyyymMNMMMNdyyyyyyyyyyyyhNMNNNNNNNNMh``` ``` `` ``
		// ``\r\n"+"`` `` `` ``` `` ``
		// .hNdyyyyyyyyyyyyyhNMNNMMNMNdhyyyyyyyyyyhNMNNNNNNNNMy `` `` ``` ``` \r\n"+"
		// ``` ``` `` `` ``` ```:dNhyyyyyyyyyyyyhNMNNNMNNNMmhyyyyyyyyyhMMNNNNNNNNMo` ```
		// `` `` ``\r\n"+"`` `` `` ``` `` ``
		// ``/mNhyyyyyyyyyyyymMNNNNMMNNMmhyyyyyhmNmdyyhhddmNNM-`` `` ``` ``` \r\n"+" ```
		// ``` `` `` ``` ``` ``/dNdyyyyyyyyyyydNMNNNNMMNNMNdhmNdsooso+:``` ``` ``` `` ``
		// ``\r\n"+"`` `` `` ``` `` `` `` ``:dNdhyyyyyyyyyyhmNMNNNNMMNMMm/::::/+oso+:.``
		// `` `` ``` ``` \r\n"+"`` `` `` ``` `` `` ``
		// ```/MMMmdyyyyyyyyyyydmMNNNNMMMM/::::::::/+os+:` `` `` ``` `` \r\n"+" ``` ``
		// `` `` ``` ``` `` :MNNNMNmhyyyyyyyyyyhmMMNNNMMo::::::::::/+sss+- ``` `` ``
		// ``\r\n"+"`` `` `` ``` `` `` ``
		// ```.MNNNNNNMNmhyyyyyyydNh+hNMNo::::::::+osssssssso- `` ``` ``` \r\n"+" ```
		// ``` `` `` ``` ``` `` .MNNNNNNNNNMNmdhyymMo::::o/:::::::::osssssssssss+` `` ``
		// ``\r\n"+"`` `` `` ``` `` `` `` ```
		// MNNNNNNNNNNNNNMNNMy/:::::::::::+/:::+oso--.-::::``` ``` ``` \r\n"+" ``` ```
		// `` `` ``` ``` `` `NMNNNNNNNNNNNNNNMNso::::o/:::::+sso+/osso:`` ``` `` ``
		// ``\r\n"+" `` ` `` ``` `` `` ```
		// `odMNNNNNNNNNNNNNNMos+:::osso/:::+ssssssssso:` ` ``` `` ``\r\n"+"`` `` `` ```
		// `` `` `` ``` ``:ohNMNNNNNNNNNM:os+::ossssoo/:+sssssssssso:` `` ``` ```
		// \r\n"+" ``` ``` `` `` ``` ``` `` `` ```
		// -+ymMNNNNNM/-osoosssssssssossso//+osssso:` `` `` ``\r\n"+"`` `` `` ``` `` ``
		// `` ``` `` `` ./sdNMMo :sssssssssssssssso.```.-/++o:`` ``` ``` \r\n"+" ``` ```
		// `` `` ``` ``` `` `` ``` ``` ```-+-` :osssso/osssssssso` `` ``` `` ``
		// ``\r\n"+"`` `` `` ``` `` `` `` ``` `` `` `` ``` ``-ossss:`:+sssssss+`` `` ``
		// ``` ``` \r\n"+" ``` ``` `` `` ``` ``` `` `` ``` ``` `` `` ``/oss:`
		// ./osssss+``` ``` `` `` ``\r\n"+" `` ``` `` `` ``` ``` `` `` ``` `` `` ``
		// ```-/`` ```-/osss-`` ``` `` `` ``\r\n"+"`` `` `` ``` `` `` `` ``` `` `` ``
		// ``` `` `` `` ``` .:/. `` `` ``` ``` \r\n"+" ``` ``` `` `` ``` ``` `` `` ```
		// ``` `` `` `` ``` `` `` `` ``` `` `` ``\r\n"+"`` `` `` ``` `` `` `` ``` `` ``
		// `` ``` ``` `` `` ``` ``` `` `` ``` ``` \n"+"\r\n"+"'|| |''||''| '||''''|
		// |''||''| .|''''|, '||''''| '||''''| \r\n"+" || || || . || || || || . || .
		// \r\n"+" || || ||''| || || || ||''| ||''| \r\n"+" || || || || || || || ||
		// \r\n"+".||...| |..||..| .||. .||. `|....|' .||. .||. \r\n"+" ");
	}

	// LIFT OFF
	// System.out.println("\r\n"+" ``` ``` `` `` ``` ``` `` `` ``` ``` `` `` `` ```
	// `` `` `` ``` `` `` ``\r\n"+"`` `` `` ``` `` `` `` ``` `` `` `` ``` `` `` ``
	// ``` ``` `` `` ``` ``` \r\n"+" ``` ``` ``+yyssoo+/:-.``` `` `` ``` `` `` `` ``
	// ``` `` `` `` ``` `` `` ``\r\n"+" ``` `` ``sMhhhhdddmmmNmdys+:.` `` `` `` ``
	// `` ``` `` `` `` `` `` `` `` ``\r\n"+"`` `` `` /MhyyyyyyyyyyyyhhdmmNdyo/.` ``
	// `` ``` `` `` `` ``` ``` `` `` ``` ``` \r\n"+" ``` ```
	// ```NmyyyyyyyyyyyyyyyyyyyhdmNmho- ``` `` `` `` ``` `` `` `` ``` `` ``
	// ``\r\n"+"`` `` `` `oMhyyyyyyyyyyyyyyyyyyyyyyyhdmNh+.`` ``` ``` `` `` ``` ```
	// `` `` ``` ``` \r\n"+" ``` ``` `` `mmyyyyyyyyyyyyyyyyyyyyyyyyyyyhdNmo-` `` ``
	// ``` `` `` `` ``` `` `` ``\r\n"+"`` `` ``
	// ``/MdyyyyyyyyyyyyyyyyyyyyyyyyyyyyyhdNmo.` `` `` `` ``` ``` `` `` ``` ```
	// \r\n"+" ``` ``` `` `sMhyyyyyyyyyyydmNNMNNmdhyyyyyyyyyyhmNh:` ``` ``` `` `` ``
	// ``` `` `` ``\r\n"+" `` ``` `` `hNhyyyyyyyyhNMMmyoshNMMmyyyyyyyyyyyydNm/` ``
	// ``` `` `` `` ``` `` `` ``\r\n"+"`` `` `` ``` `dNhyyyyyyymMMs`
	// `..sMMmyyyyyyyyyyyyyhNmoo+++/////:-:-` ``` `` `` ``` ``` \r\n"+" ``` ``` ``
	// ```dNhyyyyyymMM+ `....MMNyyyyyyyyyyyyyyyhNMMNNNNNNNNNNM+`` `` ``` `` ``
	// ``\r\n"+"`` `` `` ```
	// `.hNhyyyyyhNMNo....-yMMdyyyyyyyyyyyyyyyyydMMNNNNNNNNNNM: ``` `` `` ``` ```
	// \r\n"+" ``` ``` `` `` `yMdyyyyyhmMMMmdmMMMdyyyyyyyyyyyyyyyyyyyhmMNNNNNNNNNNN-
	// `` ``` `` `` ``\r\n"+"`` `` `` ``` ``
	// /NmyyyyyyydmmNmmdyyyyyyyyyyyyyyyyyyyyyyydMNNNNNNNNNMN.`` `` `` ``` ```
	// \r\n"+"`` `` ``` ` `` -dNhyyyyyyyyyyyyyyyyyydMMmhyyyyyyyyyyyyyhMMNNNNNNNNMm`
	// ``` `` `` `` \r\n"+" ``` `` `` `` ```
	// `oNmyyyyyyyyyyyyyyyymMNMMMNdyyyyyyyyyyyyhNMNNNNNNNNMh``` ``` `` ``
	// ``\r\n"+"`` `` `` ``` `` ``
	// .hNdyyyyyyyyyyyyyhNMNNMMNMNdhyyyyyyyyyyhNMNNNNNNNNMy `` `` ``` ``` \r\n"+"
	// ``` ``` `` `` ``` ```:dNhyyyyyyyyyyyyhNMNNNMNNNMmhyyyyyyyyyhMMNNNNNNNNMo` ```
	// `` `` ``\r\n"+"`` `` `` ``` `` ``
	// ``/mNhyyyyyyyyyyyymMNNNNMMNNMmhyyyyyhmNmdyyhhddmNNM-`` `` ``` ``` \r\n"+" ```
	// ``` `` `` ``` ``` ``/dNdyyyyyyyyyyydNMNNNNMMNNMNdhmNdsooso+:``` ``` ``` `` ``
	// ``\r\n"+"`` `` `` ``` `` `` `` ``:dNdhyyyyyyyyyyhmNMNNNNMMNMMm/::::/+oso+:.``
	// `` `` ``` ``` \r\n"+"`` `` `` ``` `` `` ``
	// ```/MMMmdyyyyyyyyyyydmMNNNNMMMM/::::::::/+os+:` `` `` ``` `` \r\n"+" ``` ``
	// `` `` ``` ``` `` :MNNNMNmhyyyyyyyyyyhmMMNNNMMo::::::::::/+sss+- ``` `` ``
	// ``\r\n"+"`` `` `` ``` `` `` ``
	// ```.MNNNNNNMNmhyyyyyyydNh+hNMNo::::::::+osssssssso- `` ``` ``` \r\n"+" ```
	// ``` `` `` ``` ``` `` .MNNNNNNNNNMNmdhyymMo::::o/:::::::::osssssssssss+` `` ``
	// ``\r\n"+"`` `` `` ``` `` `` `` ```
	// MNNNNNNNNNNNNNMNNMy/:::::::::::+/:::+oso--.-::::``` ``` ``` \r\n"+" ``` ```
	// `` `` ``` ``` `` `NMNNNNNNNNNNNNNNMNso::::o/:::::+sso+/osso:`` ``` `` ``
	// ``\r\n"+" `` ` `` ``` `` `` ```
	// `odMNNNNNNNNNNNNNNMos+:::osso/:::+ssssssssso:` ` ``` `` ``\r\n"+"`` `` `` ```
	// `` `` `` ``` ``:ohNMNNNNNNNNNM:os+::ossssoo/:+sssssssssso:` `` ``` ```
	// \r\n"+" ``` ``` `` `` ``` ``` `` `` ```
	// -+ymMNNNNNM/-osoosssssssssossso//+osssso:` `` `` ``\r\n"+"`` `` `` ``` `` ``
	// `` ``` `` `` ./sdNMMo :sssssssssssssssso.```.-/++o:`` ``` ``` \r\n"+" ``` ```
	// `` `` ``` ``` `` `` ``` ``` ```-+-` :osssso/osssssssso` `` ``` `` ``
	// ``\r\n"+"`` `` `` ``` `` `` `` ``` `` `` `` ``` ``-ossss:`:+sssssss+`` `` ``
	// ``` ``` \r\n"+" ``` ``` `` `` ``` ``` `` `` ``` ``` `` `` ``/oss:`
	// ./osssss+``` ``` `` `` ``\r\n"+" `` ``` `` `` ``` ``` `` `` ``` `` `` ``
	// ```-/`` ```-/osss-`` ``` `` `` ``\r\n"+"`` `` `` ``` `` `` `` ``` `` `` ``
	// ``` `` `` `` ``` .:/. `` `` ``` ``` \r\n"+" ``` ``` `` `` ``` ``` `` `` ```
	// ``` `` `` `` ``` `` `` `` ``` `` `` ``\r\n"+"`` `` `` ``` `` `` `` ``` `` ``
	// `` ``` ``` `` `` ``` ``` `` `` ``` ``` \n"+"\r\n"+"'|| |''||''| '||''''|
	// |''||''| .|''''|, '||''''| '||''''| \r\n"+" || || || . || || || || . || .
	// \r\n"+" || || ||''| || || || ||''| ||''| \r\n"+" || || || || || || || ||
	// \r\n"+".||...| |..||..| .||. .||. `|....|' .||. .||. \r\n"+" ");
}
