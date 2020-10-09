import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solver {
	private static boolean inWrongPath = false;
	private static boolean completed = false;
	private static LinkedList<int[][]> table_list = new LinkedList<>();
	private static int[][] SudocoTable = new int[9][9];
	private static int[][] SudocoResult = new int[9][9];

	public int[][] getSudocoTable() {
		return SudocoTable;
	}

	public void setSudocoTable(int[][] sudocoTable) {
		SudocoTable = sudocoTable;
	}

	public static int[][] getSudocoResult() {
		Solve();
		return SudocoResult;
	}

	// @@@@@@@@@@@@@@@@@@@@@ Methods

	// print the table
	public static void print_table(int[][] a) {
		for (int i = 0; i < 9; i++) {
			System.out.println();
			for (int j = 0; j < 9; j++) {
				System.out.print(a[i][j]);
			}
		}
	}

	// to check if there is still unfilled cells
	public static boolean hasEmptyCell() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if (SudocoTable[i][j] == 0)
					return true;
			}
		return false;
	}

	// based on the position of a cell this function report the appropriate 3x3
	// block to check the possibles
	public static int[][] findblock(int i, int j) {
		int[][] addetives = new int[2][3];
		switch ((i + 1) % 3) {
		case 1: {
			addetives[0][0] = i;
			addetives[0][1] = i + 1;
			addetives[0][2] = i + 2;
			break;
		}
		case 2: {
			addetives[0][0] = i - 1;
			addetives[0][1] = i;
			addetives[0][2] = i + 1;
			break;
		}
		case 0: {
			addetives[0][0] = i - 2;
			addetives[0][1] = i - 1;
			addetives[0][2] = i;
			break;
		}

		default:
			break;
		}
		switch ((j + 1) % 3) {
		case 1: {
			addetives[1][0] = j;
			addetives[1][1] = j + 1;
			addetives[1][2] = j + 2;
			break;
		}
		case 2: {
			addetives[1][0] = j - 1;
			addetives[1][1] = j;
			addetives[1][2] = j + 1;
			break;
		}
		case 0: {
			addetives[1][0] = j - 2;
			addetives[1][1] = j - 1;
			addetives[1][2] = j;
			break;
		}

		default:
			break;
		}
		return addetives;

	}

	// function to return the every possibilities of a cell
	public static List<Integer> possibilities(int i, int j) {
		List<Integer> Poss = new ArrayList<Integer>();
		int[] Notposs = new int[9];
		for (int a = 0; a < 9; a++) {
			if (SudocoTable[a][j] != 0)
				Notposs[SudocoTable[a][j] - 1] = SudocoTable[a][j];
		}
		for (int b = 0; b < 9; b++) {
			if (SudocoTable[i][b] != 0)
				Notposs[SudocoTable[i][b] - 1] = SudocoTable[i][b];
		}
		int[][] block = new int[2][3];
		block = findblock(i, j);
		for (int k = 0; k < 3; k++)
			for (int l = 0; l < 3; l++) {
				if (SudocoTable[block[0][k]][block[1][l]] != 0)
					Notposs[SudocoTable[block[0][k]][block[1][l]] - 1] = SudocoTable[block[0][k]][block[1][l]];
			}
		for (int m = 0; m < 9; m++) {
			if (Notposs[m] == 0)
				Poss.add(m + 1);
		}

		return Poss;
	}

	// Check if there is still determined cell and returns the values
	public static int[] hasDetermined() {
		int[] result = new int[3];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				if (possibilities(i, j).size() == 1 && SudocoTable[i][j] == 0) {
					result[0] = i;
					result[1] = j;
					result[2] = possibilities(i, j).get(0);
					return result;
				}
			}
		return null;
	}

	// To copy the tables
	public static int[][] copytotable(int[][] b) {
		int[][] a = new int[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				a[i][j] = b[i][j];
			}
		return a;
	}

	public static void Solve() {

		/* check if the table is already filled */
		if (!hasEmptyCell()) {
			completed = true;
			SudocoResult = SudocoTable;
			return;
		} else {
			/*
			 * check for the determined cells with just one possibility and fill the table
			 * with its determined value
			 */
			while (hasDetermined() != null) {
				int k = hasDetermined()[0];
				int l = hasDetermined()[1];
				SudocoTable[k][l] = hasDetermined()[2];
			}
			if (!hasEmptyCell()) {
				completed = true;
				SudocoResult = SudocoTable;
				return;
			} else {/* there is not any other determined cell */
				int p = 0;
				int q = 0;
				while (!inWrongPath && !completed) {
					/* the number of possibilities for a single cell */
					int noOfpossibilities = 0;
					outerloop: for (p = 0; p < 9; p++) {
						for (q = 0; q < 9; q++) {
							if (SudocoTable[p][q] == 0) {
								noOfpossibilities = possibilities(p, q).size();
								if (noOfpossibilities == 0 && !inWrongPath) {
									inWrongPath = true;
									break outerloop;
								} else if (hasDetermined() == null && noOfpossibilities == 2) {
									/*
									 * facing dilemma continue with one option and save the alternate table for the
									 * case encountering block.
									 */
									Object[] s = new Object[noOfpossibilities];
									s = possibilities(p, q).toArray();
									int[][] Su = new int[9][9];
									Su = copytotable(SudocoTable);
									Su[p][q] = (int) s[0];
									table_list.addLast(Su);
									SudocoTable[p][q] = (int) s[1];
									Solve();
								}
							}
						}
					}
				}
				/* faced with block, retrieve the last reserved table. */
				if (inWrongPath && !completed) {
					if (table_list.size() > 0 && !completed) {
						SudocoTable = table_list.pollLast();
						inWrongPath = false;
						Solve();
					} else {
						System.out.println("The Sudoco table is wrong");
						return;
					}
				}
			}
			return;
		}

	}

}
