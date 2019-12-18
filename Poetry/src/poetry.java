public class poetry {
	public static int len(int j, int k, String [] words) {
		int len = 0;
		for (; j <= k; j++) {
			len += words[j].length();
		}
		return len;
	}
	
	public static int func(int j, int i, int n, int m, int [] lines, String [] words) {
		int optimal = Integer.MAX_VALUE;
		for (int k = j; k < n; k++) {
			int sumOfWords = len(j, k, words);
			int costForward = 0;
			int whiteSpace = lines[i] - sumOfWords - 1;
			
			int lineCost = (int) Math.ceil(whiteSpace / (k - j + 1));
			if (i < m) {
				costForward = func(k + 1, i + 1, n, m, lines, words);
			}
			else {costForward = 0;}
			int effectiveCost = Math.max(lineCost, costForward);
			
			if (effectiveCost < optimal) {
				optimal = effectiveCost;
			}
			
		}
		
		return optimal;
		
	}
		
	public static void main(String[] args) {
		int n = 5;
		int m = 3;
		String [] words = {"a", "cat", "and", "a", "dog"};
		int [] lines = {7, 3, 5};
		int [][] M = new int [m][n];
		for(int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				M[j][i] = func(j, i, n, m, lines, words);
			}
		}
	}

}