public class Solution {

	public static void main(String[] args) {
		ReadExcelFile reader = new ReadExcelFile();
		int[][] sudoku = new int[9][9];
		sudoku = reader.fillTable();
		Solver s = new Solver();
		s.setSudocoTable(sudoku);
		print_table(s.getSudocoResult());

	}

	// print the table
	public static void print_table(int[][] a) {
		for (int i = 0; i < 9; i++) {
			System.out.println();
			for (int j = 0; j < 9; j++) {
				System.out.print(a[i][j]);
			}
		}
	}
}