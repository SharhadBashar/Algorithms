/**Sharhad Bashar
 * ID: 20474328
 * CS 341
 * Assignment 5**/
import java.util.*;

public class poem {
	private static int inf = Integer.MAX_VALUE;
	private static int[][][] M;
	private static int n, m;
	private static int[] lines;
	private static String[] words;
	private static int[] wordsRS;
	private static int[] position;

	/** Function that calculates the sum of the length of words 
	 * starting from 0 till a certain point. Outputs an array of 
	 * size n with the running sum of words
	 * input: none
	 * output: int[] wordsRS*/
	private static int[] wordsRunningSum() {
		int[] wordsRS = new int[n];
		wordsRS[0] = words[0].length();
		for (int i = 1; i < n; i++) {
			wordsRS[i] = wordsRS[i - 1] + words[i].length();
		}
		return wordsRS;
	}
	
	
	/**Function that calculates max gap value in O(n^2m) time
	 * input: none
	 * output: int gapVal */
	private static int gapVal() {
		int[] lineLoc = new int[n];
		int[] gaps = new int[n];
		position = new int[n];
		lineLoc[0] = 0;
		position[0] = 0;
		
		//Create M
		for (int k = 0; k < m; k++) {
			for (int i = 0; i < n; i++) {
				if (lines[k] != wordsRS[i]) {
					M[i][i][k] = inf;
				} else {
					M[i][i][k] = 0;
				}
				for (int j = i + 1; j < n; j++) {
					int gap;
					if (i == 0) {
						gap = lines[k] - wordsRS[j]; 
					} else {
						gap = lines[k] - (wordsRS[j] - wordsRS[i - 1]);
					}
					if (gap / (j - i) == 0) {
						gap = inf;
					} else if (j == n - 1 && k != m - 1) {
						gap = inf;
					} else {
						int temp = gap % (j - i) != 0 ? 1 : 0;
						gap = gap / (j - i) + temp;
					}
					if (gap <= 0) {
						gap = inf;
					}
					M[i][j][k] = gap;
				}

				if (i >= 1 && lines[k] != wordsRS[i] - wordsRS[i - 1]) {
					M[i][i][k] = inf;
				} else if (i == 0 && lines[k] != wordsRS[i]) {
					M[i][i][k] = inf;
				} else {
					M[i][i][k] = lines[k];
				}
			}
		}

		for (int j = 1; j < n; j++) {
			lineLoc[j] = 0;
			position[j] = 0;
			gaps[j] = inf;
		}
		gaps[0] = M[0][0][0];

		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				for (int k = 0; k < m; k++) {
					if (i >= 1) {
						if (gaps[i - 1] != inf && M[i][j][k] != inf && Math.max(gaps[i - 1], M[i][j][k]) < gaps[j] && lineLoc[i - 1] == k - 1) {
							gaps[j] = Math.max(gaps[i - 1], M[i][j][k]);
							position[j] = i;
							lineLoc[j] = k;
						}

					} else if (k == 0) {
						gaps[j] = M[i][j][k];
						lineLoc[j] = k;
						position[j] = i;
					}
				}
			}
		}
		return gaps[n - 1];
	}

	/**Function that prints the lines with correct gap values
	 * input: int [] position -> position of word
	 * 		  int i -> integer counter
	 * 		  int line -> line from the array of lines
	 * output: null	*/
	private static void print(int[] position, int i, int line) {
		int k = position[i];
		int width = lines[line];
		int widthLeft = 0;
		if (k == 0) {
			widthLeft = width - wordsRS[i];
		}
		else {
			widthLeft = width - wordsRS[i] + wordsRS[k - 1];
		}
		
		if (k > 0) {
			print(position, k - 1, line - 1);
		}
		
		for (int j = k; j < i; j++) {
			int temp = (widthLeft % (i - j)) != 0 ? 1 : 0;
			System.out.print(words[j]);
			if (j == i - 1) {
				for (int gaps = 0; gaps < widthLeft / (i - j); gaps++) {
					System.out.print(" ");
				}
			} else {
				for (int gaps = 0; gaps < widthLeft / (i - j) + temp; gaps++) {
					System.out.print(" ");
				}
			}
			widthLeft -= widthLeft / (i - j) + temp;
		}
		System.out.println(words[i]);
	}
	
	
	/**Main function**/
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		m = input.nextInt();
		n = input.nextInt();
		lines = new int[m];
		words = new String[n];
		for (int i = 0; i < m; i++) {
			lines[i] = input.nextInt();
		}
		for (int i = 0; i < n; i++) {
		    words[i] = input.next();
		}
		M = new int[n][n][m];
		wordsRS = wordsRunningSum();
		int maxGap = gapVal();
		if (maxGap != inf) {
			print(position, n - 1, m - 1);
			System.out.println(maxGap);
		} else {
			System.out.println("No valid solution.");
		}
	}
}