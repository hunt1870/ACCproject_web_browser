package webCrawler;

public class editDistance {
	static int editDistance(String w1, String w2) {
		int n = w1.length();
		int m = w2.length();
	    int ed[][] = new int[n+1][m+1];
	    for (int i = 0; i <= n; i++) {
	        for (int j = 0; j <= m; j++) {
	            if (i == 0)
	                ed[i][j] = j;      
	            else if (j == 0)
	                ed[i][j] = i; 
	            else if (w1.charAt(i-1) == w2.charAt(j-1))
	                ed[i][j] = ed[i-1][j-1];	            
	            else if (i>1 && j>1  && w1.charAt(i-1) == w2.charAt(j-2) 
	            		&& w1.charAt(i-2) == w2.charAt(j-1))
	            	ed[i][j] = 1+Math.min(Math.min(ed[i-2][j-2], ed[i-1][j]), Math.min(ed[i][j-1], ed[i-1][j-1]));
	            else
	                ed[i][j] = 1 + Math.min(ed[i][j-1], Math.min(ed[i-1][j], ed[i-1][j-1])); 
	        }
	    } 
	    return ed[n][m];
	}
}
