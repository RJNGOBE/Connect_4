
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.awt.Font;
import javax.swing.JOptionPane;

public class SU21583013 {

	static int X = 6;
	static int Y = 7;
	public static boolean fullColoum;
	public static final int EMPTY = 0;
	public static final int RED = 1;
	public static final int YELLOW = 2;
	public static int turnPlayer = RED;
	public static int[] p1powers = { 2, 2, 2 };
	public static int[] p2powers = { 2, 2, 2 };

	// This variable can be used to turn your debugging output on and off.
	// Preferably use
	static boolean DEBUG = true;

	public static void main(String[] args) {

		// TODO: Read in a command line argument that states whether the program
		// will be in either terminal

		// mode (T) or in GUI mode (G) [Hint: use args and the variable below]
		boolean gui = false;

		// Sets whether the game is in terminal mode or GUI mode

		if (args[0].equalsIgnoreCase("T")) {
			gui = false;
		} else if (args[0].equalsIgnoreCase("G")) {
			gui = true;
		}

		int input = 0;
		// The 6-by-7 grid that represents the game board, it is initially empty
		int[][] grid = new int[X][Y];
		// Shows which player's turn it is, true for player 1 and false for
		// player 2
		boolean player1 = true;
		// Shows the number of special tokens a player has left

		if (!gui) {
			// Terminal mode
			StdOut.println("****************************************************************************");
			StdOut.println("*  _______  _______  __    _  __    _  _______  _______  _______  _   ___  *"
					+ "\n* |       ||       ||  |  | ||  |  | ||       ||       ||       || | |   | *"
					+ "\n* |       ||   _   ||   |_| ||   |_| ||    ___||       ||_     _|| |_|   | *"
					+ "\n* |       ||  | |  ||       ||       ||   |___ |       |  |   |  |       | *"
					+ "\n* |      _||  |_|  ||  _    ||  _    ||    ___||      _|  |   |  |___    | *"
					+ "\n* |     |_ |       || | |   || | |   ||   |___ |     |_   |   |      |   | *"
					+ "\n* |_______||_______||_|  |__||_|  |__||_______||_______|  |___|      |___| *");
			StdOut.println("*                                                                          *");
			StdOut.println("****************************************************************************");

			// Array for current player's number of power storage
			int[] curppowers = new int[3];
			boolean playing = true;

			tree: while (playing) {

				if (player1) {
					StdOut.println("\nPlayer 1's turn (You are Red):");
					curppowers = p1powers;
				} else {
					StdOut.println("\nPlayer 2's turn (You are Yellow):");
					curppowers = p2powers;
				}
				StdOut.println("Choose your move: \n 1. Play Normal \n 2. Play Bomb (" + curppowers[0]
						+ " left) \n 3. Play Teleportation (" + curppowers[1] + " left) \n 4. Play Colour Changer ("
						+ curppowers[2] + " left)\n 5. Display Gameboard \n 6. Load Test File \n 0. Exit");

				// Get input from player(String
				String readinput = StdIn.readLine();
				try {

					int readin = Integer.parseInt(readinput);
					input = readin;
					if (input >= 0 && input <= 6) {

					} else {
						StdOut.println("Please choose a valid option\n");
						Display(grid);
						continue tree;
					}

					// If anything either than a number is an input
				} catch (NumberFormatException e) {
					StdOut.println("Please choose a valid option\n");
					Display(grid);

					continue tree;
				}

				switch (input) {

				case 0:

					Exit();
					break;

				case 1:
					StdOut.println("Choose a column to play in:");

					free: while (true) {
						fullColoum = false;

						String s = StdIn.readLine();

						// Checks if string length is one
						if (s.length() != 0) {

							try {
								// Checks if string s is an INT
								int temp = Integer.parseInt(s);

								if (temp >= 0 && temp <= 6 && s.length() == 1) {

									Play(temp, grid, player1);
									// Displays the grid after a new move was
									// played
									Display(grid);

									if (fullColoum) {
										// Comes here and idle until player
										// plays in non empty column
									}

								} else {
									StdOut.println("Illegal move, please input legal move:");
									continue free;
								}

							} catch (NumberFormatException e) {
								StdOut.println("Illegal move, please input legal move:");
								continue free;
							}

							break;

						} else {

							continue free;
						}
					}
					break;

				case 2:
					StdOut.println("Choose a column to play in:");
					free: while (true) {
						fullColoum = false;

						String s = StdIn.readLine();

						// Checks if string length is one
						if (s.length() != 0) {

							try {
								// Checks if string s is an INT
								int temp = Integer.parseInt(s);

								if (temp >= 0 && temp <= 6 && s.length() == 1) {

									if (p1powers[0] == 0 && player1) {
										StdOut.println("You have no bomb discs left\n");
										/*
										 * Changes player here and after switch
										 * statement, meaning it stays at the
										 * same player
										 */
										player1 = !player1;
									} else if (p2powers[0] == 0 && !player1) {
										StdOut.println("You have no bomb power discs left\n");
										/*
										 * Changes player here and after switch
										 * statement, meaning it stays at the
										 * same player
										 */
										player1 = !player1;
									} else {
										Bomb(temp, grid, player1);
									}

									// Displays the grid after a new move was
									// played
									Display(grid);

									if (fullColoum) {

										// TODO: Continue free or not check
										// project specification
									}

								} else {
									StdOut.println("Illegal move, please input legal move:");
									continue free;
								}

							} catch (NumberFormatException e) {
								StdOut.println("Illegal move, please input legal move:");
								continue free;
							}

							break;

						} else {

							continue free;
						}
					}
					break;

				case 3:
					StdOut.println("Choose a column to play in:");
					free: while (true) {
						String s = StdIn.readLine();
						// Checks if string length is one
						if (s.length() != 0) {

							try {
								// Checks if string s is an INT
								int temp = Integer.parseInt(s);

								if (temp >= 0 && temp <= 6 && s.length() == 1) {

									if (p1powers[1] == 0 && player1) {
										StdOut.println("You have no teleporter power discs left\n");
										/*
										 * Changes player here and after switch
										 * statement, meaning it stays at the
										 * same player
										 */
										player1 = !player1;
									} else if (p2powers[1] == 0 && !player1) {
										StdOut.println("You have no teleporter power discs left\n");
										/*
										 * Changes player here and after switch
										 * statement, meaning it stays at the
										 * same player
										 */
										player1 = !player1;
									} else {
										Teleport(temp, grid, player1);
									}
									// Displays the grid after a new move was
									// played
									Display(grid);
								} else {
									StdOut.println("Illegal move, please input legal move:");
									continue free;
								}

							} catch (NumberFormatException e) {
								StdOut.println("Illegal move, please input legal move:");
								continue free;
							}

							break;

						} else {

							continue free;
						}

					}

					break;

				case 4:
					StdOut.println("Choose a column to play in:");
					free: while (true) {
						String s = StdIn.readLine();
						// Checks if string length is one
						if (s.length() != 0) {

							try {
								// Checks if string s is an INT
								int temp = Integer.parseInt(s);

								if (temp >= 0 && temp <= 6 && s.length() == 1) {

									if (p1powers[2] == 0 && player1) {
										StdOut.println("You have no colour changer power discs left\n");
										/*
										 * Changes player here and after switch
										 * statement, meaning it stays at the
										 * same player
										 */
										player1 = !player1;
									} else if (p2powers[2] == 0 && !player1) {
										StdOut.println("You have no colour changer power discs left\n");
										/*
										 * Changes player here and after switch
										 * statement, meaning it stays at the
										 * same player
										 */
										player1 = !player1;
									} else {
										Colour_Changer(temp, grid, player1);
									}
									// Displays the grid after a new move was
									// played
									Display(grid);
								} else {
									StdOut.println("Illegal move, please input legal move:");
									continue free;
								}

							} catch (NumberFormatException e) {
								StdOut.println("Illegal move, please input legal move:");
								continue free;
							}

							break;

						} else {

							continue free;
						}

					}
					break;
					// This part will be used during testing, please DO NOT
					// change
					// it. This will result in loss of marks
				case 5:
					Display(grid);
					// Displays the current gameboard again, doesn't count
					// as a
					// move, so the player stays the same
					player1 = !player1;
					break;

					// This part will be used during testing, please DO NOT
					// change
					// it. This will result in loss of marks
				case 6:
					grid = Test(StdIn.readString());
					// Reads in a test file and loads the gameboard from it.
					player1 = !player1;
					break;

				case 7:
					Save(StdIn.readString(), grid);
					player1 = !player1;
					break;

				default:
					// Comes here if incorrect option was picked
					// Sorted in the first few lines of code program does reach
					// here
					continue tree;
				}
				// Checks whether a winning condition was met
				int win = Check_Win(grid);
				if (win == RED) {
					cree: while (true) {
						// Prints out options and asks what the player wants to
						// do next
						StdOut.println("\nPlayer 1 wins!");
						StdOut.println("Do you want to play again? \n 1. Yes \n 2. No");
						String s = StdIn.readLine();
						try {
							// Converts String to an Integer
							int conv = Integer.parseInt(s);
							if (conv == 1) {
								// Initialize board restore powers and play
								// again
								Initialise(grid);
								p1powers[0] = 2;
								p1powers[1] = 2;
								p1powers[2] = 2;
								p2powers[0] = 2;
								p2powers[1] = 2;
								p2powers[2] = 2;
								player1 = !player1;
								continue tree;
							} else if (conv == 2) {
								// If option two selected exit game
								System.exit(0);
							} else {
								// If incorrect option in the input
								StdOut.println("Choose either 1 for YES or 2 for NO:");
								continue cree;
							}
						} catch (NumberFormatException e) {
							StdOut.println("Choose either 1 for YES or 2 for NO:");
						}
					}
				} else if (win == YELLOW) {
					cree: while (true) {
						StdOut.println("\nPlayer 2 wins!");
						StdOut.println("Do you want to play again? \n 1. Yes \n 2. No");
						String s = StdIn.readLine();
						try {
							// Converts String to an Integer
							int conv = Integer.parseInt(s);
							if (conv == 1) {
								// Initialize board restore powers and play
								// again
								Initialise(grid);
								p1powers[0] = 2;
								p1powers[1] = 2;
								p1powers[2] = 2;
								p2powers[0] = 2;
								p2powers[1] = 2;
								p2powers[2] = 2;
								// Switch player's turn
								player1 = !player1;
								continue tree;
							} else if (conv == 2) {
								System.exit(0);
							} else {
								StdOut.println("Choose either 1 for YES or 2 for NO:");
								continue cree;
							}
						} catch (NumberFormatException e) {
							StdOut.println("Choose either 1 for YES or 2 for NO:");
						}
					}
				} else if (win == 3) {
					cree: while (true) {
						StdOut.println("\nDraw!");
						StdOut.println("Do you want to play again? \n 1. Yes \n 2. No");
						String s = StdIn.readLine();
						try {
							// Converts String to an Integer
							int conv = Integer.parseInt(s);
							if (conv == 1) {
								// Initialize board restore powers and play
								// again
								Initialise(grid);
								p1powers[0] = 2;
								p1powers[1] = 2;
								p1powers[2] = 2;
								p2powers[0] = 2;
								p2powers[1] = 2;
								p2powers[2] = 2;
								continue tree;
							} else if (conv == 2) {
								System.exit(0);
							} else {
								StdOut.println("Choose either 1 for YES or 2 for NO:");
								continue cree;
							}
						} catch (NumberFormatException e) {
							StdOut.println("Choose either 1 for YES or 2 for NO:");
						}
					}
				}
				// Switch the players turn
				player1 = !player1;
			}
		} else {
			/*
			 * Graphics mode if args[0] was G
			 * 
			 */
			GuiMode(player1, grid);
		}
	}

	/*
	 * Gui mode! All play funtions for the graphic user interface
	 * 
	 * @param player1 Initial player
	 * 
	 * @param grid the grid in its initial state
	 */
	public static void GuiMode(boolean player1, int[][] grid) {

		// Sets canvas size and scale
		StdDraw.setCanvasSize(100 * 9, 100 * 7);
		StdDraw.setXscale(0, 9);
		StdDraw.setYscale(0, 7);

		// BackGround picture
		StdDraw.picture(4, 4, "board.png", 13, 12);

		// Draw the board from the draw board funtion
		GuiBoard(grid);
		// Begin audio
		StdAudio.play("begin.wav");
		// Title
		StdDraw.picture(3.5, 6.5, "Logo.png", 5, 0.8);
		// Exit Button
		StdDraw.picture(8.2, 0.5, "Exit.jpeg", 1.5, 0.7);
		Font help = new Font("TimesRoman", Font.BOLD, 13);

		// Shows the quick tip picture and the hints to select power disk
		StdDraw.setPenColor(255, 204, 229);
		StdDraw.filledRectangle(8.1, 3, 1, 0.5);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.setFont(help);
		StdDraw.text(8, 3.4, "To Select a Power DISK:");
		StdDraw.text(8, 3.2, "Press and hold:");
		StdDraw.text(8, 3.0, "b - Bomb");
		StdDraw.text(8, 2.8, "t - Teleporter");
		StdDraw.text(8, 2.6, "c - Colour Changer");

		// Notification Panel
		Font joe = new Font("TimesRoman", Font.BOLD, 15);
		StdDraw.setPenColor(204, 153, 255);
		StdDraw.filledRectangle(8.1, 2, 1, 0.5);
		StdDraw.picture(7.35, 2.0, "Notifications.png", 0.4, 0.5);
		StdDraw.setFont(joe);
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(7.65, 2.35, "Notifications");

		while (true) {

			// Gets mouse coordinate if mouse is pressed
			if (StdDraw.isMousePressed()) {
				double mouseX = StdDraw.mouseX();
				double mouseY = StdDraw.mouseY();
				while (StdDraw.isMousePressed()) {

				}

				// If the return cordinate of mouse press is within this range
				// exit game
				if ((int) mouseX == 8 && (int) mouseY == 0) {
					StdAudio.play("Exit.wav");
					StdDraw.pause(3000);
					Exit();
				}

				if (mouseX > 0 && mouseX < 9 && mouseY < 6) {
					int col = (int) mouseX;
					if (col >= 0 && col <= 6) {
						if ((StdDraw.isKeyPressed(66) || StdDraw.isKeyPressed(98)) && !StdDraw.isKeyPressed(116)) {
							if (player1) {
								if (p1powers[0] == 0) {
									StdAudio.play("Bombsleft.wav");
									player1 = !player1;
								} else {
									StdAudio.play("Bomb.wav");
									Bomb(col, grid, player1);
								}
							} else if (!player1) {
								if (p2powers[0] == 0) {
									StdAudio.play("Bombsleft.wav");
									player1 = !player1;
								} else {
									StdAudio.play("Bomb.wav");
									Bomb(col, grid, player1);

								}
							}

						} else if (StdDraw.isKeyPressed(116) || StdDraw.isKeyPressed(84)) {
							if (player1) {
								if (p1powers[1] == 0) {
									StdAudio.play("Telsleft.wav");
									player1 = !player1;
								} else {
									StdAudio.play("Teleport.wav");
									Teleport(col, grid, player1);
								}
							} else if (!player1) {
								if (p2powers[1] == 0) {
									StdAudio.play("Telsleft.wav");
									player1 = !player1;
								} else {
									StdAudio.play("Teleport.wav");
									Teleport(col, grid, player1);
								}
							}

						}

						else if (StdDraw.isKeyPressed(99) || StdDraw.isKeyPressed(67)) {
							if (player1) {
								if (p1powers[2] == 0) {
									StdAudio.play("Colourleft.wav");
									player1 = !player1;
								} else {
									Colour_Changer(col, grid, player1);
								}
							} else if (!player1) {
								if (p2powers[2] == 0) {
									StdAudio.play("Colourleft.wav");
									player1 = !player1;
								} else {
									Colour_Changer(col, grid, player1);
								}
							}
						} else if (!StdDraw.isKeyPressed(66) && !StdDraw.isKeyPressed(116)
								&& !StdDraw.isKeyPressed(99)) {
							if (grid[0][col] != 0) {
								StdAudio.play("erro.wav");
								StdDraw.setPenColor(204, 153, 255);
								StdDraw.filledRectangle(8.1, 2, 1, 0.5);
								StdDraw.picture(7.35, 2.0, "Notifications.png", 0.4, 0.5);
								StdDraw.setFont(joe);
								StdDraw.setPenColor(StdDraw.BLACK);
								StdDraw.text(7.65, 2.35, "Notifications");
								StdDraw.setPenColor(StdDraw.BLACK);
								StdDraw.text(8.25, 2, "Column is FULL");
								player1 = !player1;
							} else {
								StdAudio.play("makeMove.wav");
								StdDraw.setPenColor(204, 153, 255);
								StdDraw.filledRectangle(8.1, 2, 1, 0.5);
								StdDraw.picture(7.35, 2.0, "Notifications.png", 0.4, 0.5);
								StdDraw.setFont(joe);
								StdDraw.setPenColor(StdDraw.BLACK);
								StdDraw.text(7.65, 2.35, "Notifications");
								Play(col, grid, player1);
							}

						}
						Font font = new Font("TimesRoman", Font.BOLD, 13);
						if (player1) {
							StdDraw.setPenColor(StdDraw.RED);
							StdDraw.filledRectangle(8.1, 5.0, 1, 0.5);
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.setPenRadius(5);
							StdDraw.setFont(font);
							StdDraw.text(7.72, 5.4, "POWERS LEFT:");
							StdDraw.text(7.73, 5.2, "Bombs left = " + p1powers[0]);
							StdDraw.text(7.87, 5.0, "Colour changer = " + p1powers[2]);
							StdDraw.text(7.72, 4.8, "Teleporter = " + p1powers[1]);
							StdDraw.text(8.0, 4.6, "Next player is YELLOW");
							StdDraw.enableDoubleBuffering();
						} else if (!player1) {
							StdDraw.setPenColor(StdDraw.YELLOW);
							StdDraw.filledRectangle(8.1, 5.0, 1, 0.5);
							StdDraw.setPenColor(StdDraw.BLACK);
							StdDraw.setFont(font);
							StdDraw.text(7.72, 5.4, "POWERS LEFT:");
							StdDraw.text(7.73, 5.2, "Bombs left = " + p2powers[0]);
							StdDraw.text(7.87, 5.0, "Colour changer = " + p2powers[2]);
							StdDraw.text(7.72, 4.8, "Teleporter = " + p2powers[1]);
							StdDraw.text(7.85, 4.6, "Next player is RED");
							StdDraw.enableDoubleBuffering();
						}
						player1 = !player1;
					}

				}
				// Default return statement of keyboard funtion is -1
			} else if (keyBoard() != -1) {
				// gets the column is pressed
				int getCol = keyBoard();
				while (keyBoard() != -1) {
					// While the key is pressed do nothing
				}
				player1 = !player1;
				pressKey(!player1, grid, getCol);
			}

			int win = Check_Win(grid);

			if (win == RED) {
				StdAudio.play("RED.wav");
				GuiBoard(grid);
				StdDraw.pause(3000);

				String strWinner = ("RED WINS");
				int butSelection = JOptionPane.showConfirmDialog(null, strWinner + "\nDo you want to play again?",
						"What next?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (butSelection == JOptionPane.NO_OPTION || butSelection == JOptionPane.CLOSED_OPTION) {
					StdAudio.play("Exit.wav");
					StdDraw.pause(3000);
					Exit(); // quit
				} else if (butSelection == JOptionPane.YES_OPTION) { // play
					// again
					Initialise(grid);
					GuiMode(player1, grid);
				}

				break;
			} else if (win == YELLOW) {
				StdAudio.play("YELLOW.wav");
				GuiBoard(grid);
				StdDraw.pause(3000);
				String strWinner = ("YELLOW WINS");
				int butSelection = JOptionPane.showConfirmDialog(null, strWinner + "\nDo you want to play again?",
						"What next?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (butSelection == JOptionPane.NO_OPTION || butSelection == JOptionPane.CLOSED_OPTION) {
					// Exit if selection is no
					StdAudio.play("Exit.wav");
					StdDraw.pause(3000);
					Exit();
				} else if (butSelection == JOptionPane.YES_OPTION) {
					// play again if selection is yes
					Initialise(grid);
					GuiMode(player1, grid);
				}

			} else if (win == 3) {
				StdAudio.play("Draw.wav");
				GuiBoard(grid);
				StdDraw.pause(1500);
				String strWinner = ("DRAW");
				int butSelection = JOptionPane.showConfirmDialog(null, strWinner + "\nDo you want to play again?",
						"What next?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (butSelection == JOptionPane.NO_OPTION || butSelection == JOptionPane.CLOSED_OPTION) {
					// Exit if selection is no
					StdAudio.play("Exit.wav");
					StdDraw.pause(3000);
					Exit();
				} else if (butSelection == JOptionPane.YES_OPTION) {
					// play again if selection is yes
					Initialise(grid);
					GuiMode(player1, grid);
				}
			}

			GuiBoard(grid);
			StdDraw.enableDoubleBuffering();

		}
	}

	/*
	 * GUI function that works for keyboard presses
	 * 
	 * @param player1 current player
	 * 
	 * @param grid modified state of the board
	 * 
	 */
	public static void pressKey(boolean player1, int[][] grid, int col1) {

		if (col1 >= 0 && col1 <= 6) {
			if ((StdDraw.isKeyPressed(66) || StdDraw.isKeyPressed(98)) && !StdDraw.isKeyPressed(116)) {
				if (player1) {
					if (p1powers[0] == 0) {
						StdAudio.play("Bombsleft.wav");
						player1 = !player1;
					} else {
						StdAudio.play("Bomb.wav");
						Bomb(col1, grid, player1);
					}
				} else if (!player1) {
					if (p2powers[0] == 0) {
						StdAudio.play("Bombsleft.wav");
						player1 = !player1;

					} else {
						StdAudio.play("Bomb.wav");
						Bomb(col1, grid, player1);

					}
				}

			} else if (StdDraw.isKeyPressed(116) || StdDraw.isKeyPressed(84)) {

				if (player1) {
					if (p1powers[1] == 0) {
						StdAudio.play("Telsleft.wav");
						player1 = !player1;

					} else {
						StdAudio.play("Teleport.wav");
						Teleport(col1, grid, player1);
					}
				} else if (!player1) {
					if (p2powers[1] == 0) {
						StdAudio.play("Telsleft.wav");
						player1 = !player1;

					} else {
						StdAudio.play("Teleport.wav");
						Teleport(col1, grid, player1);

					}
				}

			}

			else if (StdDraw.isKeyPressed(99) || StdDraw.isKeyPressed(67)) {

				if (player1) {
					if (p1powers[2] == 0) {
						StdAudio.play("Colourleft.wav");
						player1 = !player1;
					} else {
						Colour_Changer(col1, grid, player1);
					}
				} else if (!player1) {

					if (p2powers[2] == 0) {
						StdAudio.play("Colourleft.wav");
						player1 = !player1;

					} else {

						Colour_Changer(col1, grid, player1);

					}
				}

			} else if (!StdDraw.isKeyPressed(66) && !StdDraw.isKeyPressed(116) && !StdDraw.isKeyPressed(99)) {

				Font joe = new Font("TimesRoman", Font.BOLD, 15);
				if (grid[0][col1] != 0) {
					StdAudio.play("erro.wav");
					StdDraw.setPenColor(204, 153, 255);
					StdDraw.filledRectangle(8.1, 2, 1, 0.5);
					StdDraw.picture(7.35, 2.0, "Notifications.png", 0.4, 0.5);
					StdDraw.setFont(joe);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(7.65, 2.35, "Notifications");
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(8.25, 2, "Column is FULL");
					player1 = !player1;

				} else {

					StdAudio.play("makeMove.wav");
					StdDraw.setPenColor(204, 153, 255);
					StdDraw.filledRectangle(8.1, 2, 1, 0.5);
					StdDraw.picture(7.35, 2.0, "Notifications.png", 0.4, 0.5);
					StdDraw.setFont(joe);
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.text(7.65, 2.35, "Notifications");
					Play(col1, grid, player1);
				}

			}

			Font font = new Font("TimesRoman", Font.BOLD, 13);
			if (player1) {
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.filledRectangle(8.1, 5.0, 1, 0.5);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.setPenRadius(5);
				StdDraw.setFont(font);
				StdDraw.text(7.72, 5.4, "POWERS LEFT:");
				StdDraw.text(7.73, 5.2, "Bombs left = " + p1powers[0]);
				StdDraw.text(7.87, 5.0, "Colour changer = " + p1powers[2]);
				StdDraw.text(7.72, 4.8, "Teleporter = " + p1powers[1]);
				StdDraw.text(8.0, 4.6, "Next player is YELLOW");
				StdDraw.enableDoubleBuffering();
			} else if (!player1) {
				StdDraw.setPenColor(StdDraw.YELLOW);
				StdDraw.filledRectangle(8.1, 5.0, 1, 0.5);
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.setFont(font);
				StdDraw.text(7.72, 5.4, "POWERS LEFT:");
				StdDraw.text(7.73, 5.2, "Bombs left = " + p2powers[0]);
				StdDraw.text(7.87, 5.0, "Colour changer = " + p2powers[2]);
				StdDraw.text(7.72, 4.8, "Teleporter = " + p2powers[1]);
				StdDraw.text(7.85, 4.6, "Next player is RED");
				StdDraw.enableDoubleBuffering();
			}

		}

		GuiBoard(grid);

	}

	/*
	 * Converts the ASCII value of keyboard presses to normal int values
	 * 
	 */
	private static int keyBoard() {

		if (StdDraw.isKeyPressed(48)) {
			return 0;
		} else if (StdDraw.isKeyPressed(49)) {
			return 1;
		} else if (StdDraw.isKeyPressed(50)) {
			return 2;
		} else if (StdDraw.isKeyPressed(51)) {
			return 3;
		} else if (StdDraw.isKeyPressed(52)) {
			return 4;
		} else if (StdDraw.isKeyPressed(53)) {
			return 5;
		} else if (StdDraw.isKeyPressed(54)) {
			return 6;
		} else if (StdDraw.isKeyPressed(55)) {
			return 7;
		} else if (StdDraw.isKeyPressed(56)) {
			return 8;
		} else if (StdDraw.isKeyPressed(57)) {
			return 9;
		}

		return -1;
	}

	/*
	 * Draws the circles
	 */
	public static void GuiBoard(int grid[][]) {

		for (int row = 0; row < X; row++) {
			for (int col = 0; col < Y; col++) {
				int disc = grid[row][col];
				if (disc == EMPTY) {
					StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
				} else if (disc == RED) {
					StdDraw.setPenColor(StdDraw.RED);
				} else if (disc == YELLOW) {
					StdDraw.setPenColor(StdDraw.YELLOW);
				}
				StdDraw.filledCircle(col + 0.5, X - 1 - row + 0.5, 0.5);
			}
		}

		StdDraw.show();

	}

	public static int[][] Save(String name, int[][] grid) {
		try {
			FileWriter fileWriter = new FileWriter(name + ".txt");
			for (int i = 0; i < X; i++) {
				for (int j = 0; j < Y; j++) {
					fileWriter.write(Integer.toString(grid[i][j]) + " ");
				}
				fileWriter.write("\n");
			}
			fileWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return grid;
	}

	public static void Initialise(int[][] grid) {
		// Sets everything back to empty slots
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				grid[i][j] = EMPTY;
			}

		}
	}

	/**
	 * Displays the grid, empty spots as *, player 1 as R and player 2 as Y.
	 * Shows column and row numbers at the top.
	 *
	 * @param grid
	 *            The current board state
	 */

	public static void Display(int[][] grid) {
		System.out.println("  0 1 2 3 4 5 6");
		for (int i = 0; i < X; i++) {
			StdOut.print(i);
			String line = " ";
			for (int j = 0; j < Y; j++)
				if (grid[i][j] == 0)
					StdOut.print(" " + "*");
				else if (grid[i][j] == RED) {
					StdOut.print(" " + "R");
				} else if (grid[i][j] == YELLOW) {
					StdOut.print(" " + "Y");
				}
			StdOut.println(line);
		}
	}

	public static void Exit() {
		// TODO: Exit the game
		System.exit(0);
	}

	/**
	 * Plays a normal disc in the specified position (i) in the colour of the
	 * player given. Returns the modified grid or prints out an error message if
	 * the column is full.
	 *
	 * @param i
	 *            Column that the disc is going to be played in
	 * @param grid
	 *            The current board state
	 * @param player1
	 *            The current player
	 * @return grid The modified board state
	 */
	public static int[][] Play(int col, int[][] grid, boolean player1) {
		// TODO: Play a normal disc of the given colour, if the column is full,
		// print out the message: "Column is full."
		int num_piece = 0;
		for (int i = 5; i >= 0; i--) {
			// Checks if empty, if yes play!
			if (grid[i][col] == 0) {
				if (player1) {
					grid[i][col] = RED;
					break;
				} else {
					grid[i][col] = YELLOW;
					break;
				}
			} else {
				num_piece++;
				if (num_piece == 6) {
					StdOut.println("Column is full.");
					fullColoum = true;
				}
			}
		}
		return grid;
	}

	/**
	 * Checks whether a player has 4 tokens in a row or if the game is a draw.
	 *
	 * @param grid
	 *            The current board state in a 2D array of integers
	 */
	public static int Check_Win(int[][] grid) {
		// TODO: Check for all the possible win conditions as well as for a
		boolean winnerRed = false;
		boolean winnerYellow = false;
		// Checking for horizontal win
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < 4; j++) {
				if (grid[i][j] == grid[i][j + 1] && grid[i][j] == grid[i][j + 2] && grid[i][j] == grid[i][j + 3]) {
					if (grid[i][j] == RED) {
						winnerRed = true;
					}
					if (grid[i][j] == YELLOW) {
						winnerYellow = true;
					}
				}
			}
		}

		// Check Vertical win
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < Y; j++) {
				if (grid[i][j] == grid[i + 1][j] && grid[i][j] == grid[i + 2][j] && grid[i][j] == grid[i + 3][j]) {
					if (grid[i][j] == RED) {
						winnerRed = true;
					}
					if (grid[i][j] == YELLOW) {
						winnerYellow = true;
						;
					}
				}
			}
		}

		// Check South-East diagonal win
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
				if (grid[i][j] == grid[i + 1][j + 1] && grid[i][j] == grid[i + 2][j + 2]
						&& grid[i][j] == grid[i + 3][j + 3]) {
					if (grid[i][j] == RED) {
						winnerRed = true;
					}
					if (grid[i][j] == YELLOW) {
						winnerYellow = true;
					}
				}
			}
		}

		// Check North-West diagonal win
		for (int i = 5; i > 2; i--) {
			for (int j = 0; j < 4; j++) {
				if (grid[i][j] == grid[Math.abs(i - 1)][Math.abs(j + 1)]
						&& grid[i][j] == grid[Math.abs(i - 2)][Math.abs(j + 2)]
								&& grid[i][j] == grid[Math.abs(i - 3)][j + 3]) {
					if (grid[i][j] == RED) {
						winnerRed = true;
					}
					if (grid[i][j] == YELLOW) {
						winnerYellow = true;
					}
				}
			}
		}

		int count = 0;
		// Checks for a full board win
		for (int i = 0; i < X; i++) {
			for (int j = 0; j < Y; j++) {
				if (grid[i][j] != EMPTY) {
					count++;
				}
			}
		}

		if (count == 42 && winnerRed == false && winnerYellow == false) {
			return 3;
		}
		if (winnerRed == true && winnerYellow == true && count != 42) {
			return 3;
		} else if (winnerRed == true && winnerYellow == false) {
			return 1;
		} else if (winnerYellow == true && winnerRed == false) {
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * Plays a bomb disc that blows up the surrounding tokens and drops down
	 * tokens above it. Should not affect the board state if there's no space in
	 * the column. In that case, print the error message: "Column is full."
	 *
	 * @param i
	 *            Column that the disc is going to be played in
	 * @param grid
	 *            The current board state
	 * @param player1
	 *            The current player
	 * @return grid The modified board state
	 */
	public static int[][] Bomb(int col, int[][] grid, boolean player1) {

		int row = 0;
		int num_piece = 0;
		for (int i = 5; i >= 0; i--) {
			if (grid[i][col] != EMPTY) {
				num_piece++;
			}
		}
		if (num_piece == 6) {
			StdOut.println("Column is full.");
			fullColoum = true;
		}
		for (int i = 5; i >= 0; i--) {
			if (grid[i][col] == EMPTY) {
				row = i;
				break;
			}
		}

		// For co-ord [0][0]
		if (row == 0 && col == 0) {

			grid[0][1] = EMPTY;
			grid[1][1] = EMPTY;
			grid[1][0] = EMPTY;
			if (player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = RED;
			} else if (!player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = YELLOW;
			}

		}

		// For co-ord [0][6]
		else if (row == 0 && col == 6) {
			grid[0][5] = EMPTY;
			grid[1][5] = EMPTY;
			grid[1][6] = EMPTY;
			if (player1) {
				grid[row + 1][col] = RED;
			} else if (!player1) {
				grid[row + 1][col] = YELLOW;
			}
		}

		// For co-ord [5][0]
		else if (row == 5 && col == 0) {
			grid[4][0] = EMPTY;
			grid[4][1] = EMPTY;
			grid[5][1] = EMPTY;
			if (player1) {
				grid[row][col] = RED;
			} else if (!player1) {
				grid[row][col] = YELLOW;
			}
		}

		// For co-ord[5][6]
		else if (row == 5 && col == 6) {

			grid[5][5] = EMPTY;
			grid[4][5] = EMPTY;
			grid[4][6] = EMPTY;
			if (player1) {
				grid[row][col] = RED;
			} else if (!player1) {
				grid[row][col] = YELLOW;
			}
		}

		// For co-ord [1-4][0]
		else if (row >= 1 && row <= 4 && col == 0) {
			grid[row - 1][col] = EMPTY;
			grid[row - 1][col + 1] = EMPTY;
			grid[row][col + 1] = EMPTY;
			grid[row + 1][col + 1] = EMPTY;
			grid[row + 1][col] = EMPTY;
			if (player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = RED;
			} else if (!player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = YELLOW;
			}
		}

		// For co-oord [5][1-5]
		else if ((row == 5) && (col >= 1 && col <= 5)) {
			grid[row][col - 1] = EMPTY;
			grid[row - 1][col - 1] = EMPTY;
			grid[row - 1][col] = EMPTY;
			grid[row - 1][col + 1] = EMPTY;
			grid[row][col + 1] = EMPTY;
			if (player1) {
				grid[row][col] = RED;
			} else if (!player1) {
				grid[row][col] = YELLOW;
			}
		}

		// For co-oord [0][1-5]
		else if ((row == 0) && (col >= 1 && col <= 5) && grid[0][col] == EMPTY) {

			grid[row][col - 1] = EMPTY;
			grid[row + 1][col - 1] = EMPTY;
			grid[row + 1][col] = EMPTY;
			grid[row + 1][col + 1] = EMPTY;
			grid[row][col + 1] = EMPTY;
			if (player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = RED;
			} else if (!player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = YELLOW;
			}
		}

		// For co-oord[1-4][6]
		else if (row >= 1 && row <= 4 && col == 6) {
			grid[row - 1][col] = EMPTY;
			grid[row - 1][col - 1] = EMPTY;
			grid[row][col - 1] = EMPTY;
			grid[row + 1][col - 1] = EMPTY;
			grid[row + 1][col] = EMPTY;
			if (player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = RED;
			} else if (!player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = YELLOW;
			}
		}

		// For inner rectangle [1][1] to [4][5]
		else if ((col >= 1 && col <= 5)) {
			for (int i = 4; i >= 1; i--) {
				if (grid[i][col] == EMPTY) {
					grid[i - 1][col - 1] = EMPTY;
					grid[i - 1][col] = EMPTY;
					grid[i - 1][col + 1] = EMPTY;
					grid[i][col + 1] = EMPTY;
					grid[i + 1][col + 1] = EMPTY;
					grid[i + 1][col] = EMPTY;
					grid[i + 1][col - 1] = EMPTY;
					grid[i][col - 1] = EMPTY;
					break;
				}
			}
			if (player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = RED;
			} else if (!player1 && grid[row + 1][col] == EMPTY) {
				grid[row + 1][col] = YELLOW;
			}
		}

		FallBomb(grid);
		if (player1) {
			p1powers[0] = p1powers[0] - 1;
		} else if (!player1) {
			p2powers[0] = p2powers[0] - 1;
		}
		return grid;
	}

	/**
	 * Plays a teleporter disc that moves the targeted disc 3 columns to the
	 * right. If this is outside of the board boundaries, it should wrap back
	 * around to the left side. If the column where the targeted disc lands is
	 * full, destroy that disc. If the column where the teleporter disc falls is
	 * full, play as normal, with the teleporter disc replacing the top disc.
	 *
	 * @param i
	 *            Column that the disc is going to be played in
	 * @param grid
	 *            The current board state
	 * @param player1
	 *            The current player
	 * @return grid The modified board state
	 */
	public static int[][] Teleport(int col, int[][] grid, boolean player1) {
		for (int i = 5; i >= 0; i--) {
			if (grid[5][col] == EMPTY) {
				// Nothing happens if last row is empty
				if (player1) {
					grid[5][col] = EMPTY;
					break;
				} else {
					grid[5][col] = EMPTY;
					break;
				}
			} else if (grid[0][col] != EMPTY && col < 3) {
				if (player1) {
					grid[0][col] = RED;
					Play(col + 3, grid, !player1);
					break;
				} else if (!player1) {
					grid[0][col] = YELLOW;
					Play(col + 3, grid, player1);
					break;
				}
			} else if (grid[0][col] != EMPTY && col > 3) {
				if (player1) {
					grid[0][col] = RED;
					Play(col - 4, grid, !player1);
					break;
				} else if (!player1) {
					grid[0][col] = YELLOW;
					Play(col - 4, grid, player1);
					break;
				}
			} else if (col < 4) {
				if (grid[i][col] == EMPTY) {
					if (player1) {
						grid[i + 1][col] = RED;
						if (grid[i + 1][col] == YELLOW) {
							Play(col + 3, grid, player1);
							break;
						} else if (grid[i + 1][col] == RED) {
							Play(col + 3, grid, !player1);
							break;
						}
					} else if (!player1) {
						grid[i + 1][col] = YELLOW;
						if (grid[i + 1][col] == RED) {
							Play(col + 3, grid, player1);
							break;
						} else if (grid[i + 1][col] == YELLOW) {
							Play(col + 3, grid, !player1);
							break;
						}
					}
				}
			} else if (col > 3) {
				if (grid[i][col] == EMPTY) {
					if (player1) {
						grid[i + 1][col] = RED;
						if (grid[i + 1][col] == RED) {
							Play(col - 4, grid, !player1);
							break;
						} else if (grid[i + 1][col] == YELLOW) {

							Play(col - 3, grid, player1);
							break;
						}
					} else if (!player1) {
						grid[i + 1][col] = YELLOW;
						if (grid[i + 1][col] == RED) {
							Play(col - 4, grid, player1);
							break;
						} else if (grid[i + 1][col] == YELLOW) {
							Play(col - 4, grid, !player1);
							break;
						}
					}
				}
			}
		}
		if (player1) {
			p1powers[1] = p1powers[1] - 1;
		} else if (!player1) {
			p2powers[1] = p2powers[1] - 1;
		}

		return grid;
	}

	/*
	 * Drops the pieces of the board after a bomb has been played
	 * 
	 * @param grid The modified board state
	 */
	public static void FallBomb(int[][] grid) {
		for (int k = 0; k < 6; k++) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < Y; j++) {
					if (grid[i][j] != EMPTY) {
						if (grid[i + 1][j] == EMPTY) {
							grid[i + 1][j] = grid[i][j];
							grid[i][j] = EMPTY;
						}
					}
				}
			}
		}
	}

	/**
	 * Plays the colour changer disc that changes the affected disc's colour to
	 * the opposite colour
	 *
	 * @param i
	 *            Column that the disc is going to be played in
	 * @param grid
	 *            The current board state
	 * @param player1
	 *            The current player
	 * @return grid The modified board state
	 */
	public static int[][] Colour_Changer(int col, int[][] grid, boolean player1) {

		for (int i = 0; i <= 4; i++) {
			// Checks if red, if yes play!
			if (grid[i][col] == RED) {
				if (player1) {
					grid[i][col] = YELLOW;
					break;
				} else if (!player1) {
					grid[i][col] = YELLOW;
					break;
				}
			} else if (grid[i][col] == YELLOW) {
				if (player1) {
					grid[i][col] = RED;
					break;
				} else if (!player1) {
					grid[i][col] = RED;
					break;
				}
			} else if (grid[5][col] == EMPTY) {
				if (player1) {
					grid[5][col] = RED;
					break;
				} else {
					grid[5][col] = YELLOW;
					break;
				}
			} else if (grid[5][col] != EMPTY && grid[4][col] == EMPTY) {
				if (player1 && grid[5][col] == RED) {
					grid[5][col] = YELLOW;
					break;
				} else if (player1 && grid[5][col] == YELLOW) {
					grid[5][col] = RED;
					break;
				} else if (!player1 && grid[5][col] == YELLOW) {
					grid[5][col] = RED;
					break;
				} else if (!player1 && grid[5][col] == RED) {
					grid[5][col] = YELLOW;
					break;
				}
			}
		}
		if (player1) {
			p1powers[2] = p1powers[2] - 1;
		}
		if (!player1) {
			p2powers[2] = p2powers[2] - 1;
		}
		return grid;
	}

	/**
	 * Reads in a board from a text file.
	 *
	 * @param name
	 *            The name of the given file
	 * @return
	 */
	// Reads in a game state from a text file, assumes the file is a txt file
	public static int[][] Test(String name) {
		int[][] grid = new int[6][7];
		try {
			File file = new File(name + ".txt");
			Scanner sc = new Scanner(file);

			for (int i = 0; i < X; i++) {
				for (int j = 0; j < Y; j++) {
					grid[i][j] = sc.nextInt();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return grid;
	}

	/**
	 * Debugging tool, preferably use this since we can then turn off your
	 * debugging output if you forgot to remove it. Only prints out if the DEBUG
	 * variable is set to true.
	 *
	 * @param line
	 *            The String you would like to print out.
	 */
	public static void Debug(String line) {
		if (DEBUG)
			System.out.println(line);
	}
}