package webCrawler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainPage {
	private static final String specurl = "https://www.javatpoint.com/"; //"https://www.wikipedia.org/"; //"https://www.uwindsor.ca/"; //"https://www.wikipedia.org/";//passing w3 url to generate repository
	public static void main(String args[]) {
		WebCrawler web=new WebCrawler();
    	System.out.println("Crawling started....");
		HashSet<String> allUrl= web.getlinks(specurl, 0);
		HashMap<String, Integer> pagerank;
		ArrayList<String> keys;
		double startTime;
		
		Freqency.readFiles(); //Creating the hash table for searching
    	
		System.out.println("Database has been created....\nNow you can start searching");
    	Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    	while(true) {
    		System.out.println("Please enter a word for searching...");
    	    String search = myObj.next().toLowerCase();
    	    if(search.equals("0")||search.isEmpty()) {System.exit(0);}
    	    
			startTime = System.currentTimeMillis();
			Hashtable<String, Integer> frequency = Freqency.searchString(search);
    	    if(frequency.isEmpty()) {
    	    	System.out.println("\n\nNo match found in the crawled webpages");
    	    	
    	    	//For text suggestion
    	    	SpellChecker spellcorrect = new SpellChecker();                                   // initialize spell checker
    			File dir = new File(FilePath.dictionaryFile);   //path of folder
    			File[] directoryList = dir.listFiles();  										 // convert files into array of files
    			String[] suggestion=null;
    			if(directoryList != null) {
    				for(File file: directoryList) {
    					spellcorrect.createDictionary(file.getAbsolutePath());      // populate dictionary with text data
    				}
    				suggestion = spellcorrect.suggestSimilarWord(search);  
    				if (suggestion == null) {										// if there is no particular text suggestion
    					System.out.println("\n\nNo similar word found");
    				} else {
    					System.out.println("\n\n--------------Top 3 text suggestions:----------------------------");
    					for (String str:suggestion) {								// display the elements in the result array
    						if (str!=null) {
    							System.out.println(str);
    						}
    					}
    				}
    			} else {
    				System.out.println("Directory not exist");						// if no such directory found
    			}
    			
    			
    	    	System.out.println("\n\nYou want to search from suggestions? \n Y --> Yes\n N --> No ");
    	    	String choice = myObj.next();
    	    	
    	    	if(choice.equals("Y")) {
    	    		System.out.println("\n\nwhich suggestion you want to search?");
    	    		System.out.println("1 --> " + suggestion[0] + "\n2 --> " + suggestion[1] + "\n3 --> " + suggestion[2]);
    	    		int textChoice = myObj.nextInt();
    	    		if(textChoice == 1 || textChoice == 2 || textChoice == 3) {
    	    			search=suggestion[textChoice - 1];
    	    			startTime = System.currentTimeMillis();
        	    		frequency=Freqency.searchString(search);
            	    	System.out.println("\nSearching for "+search);
            	    	pagerank = Ranking.rank(frequency);
            	    	keys = new ArrayList<String>(pagerank.keySet());
            	    	System.out.println("\n\n---------Search Results ("+ String.format("%.7f", (System.currentTimeMillis() - startTime)/1000) +" seconds)------------------");
            	    	System.out.println("\n|Rank|\t|Frequency|\t|WebPage|");
            	    	for(int i=pagerank.size()-1,j=1;i>=0;i--,j++) {
            	    		System.out.println(j+" \t"+pagerank.get(keys.get(i))+ " \t\t"+keys.get(i));
            	    	}
    	    		} else {
    	    			System.out.println("\n\n------INVALID INPUT------");
    	    			continue;
    	    		}
    	    	}else {
    	    		continue;
    	    	}
    	    }else {
    	    	pagerank=Ranking.rank(frequency);
    	    	keys = new ArrayList<String>(pagerank.keySet());
    	    	System.out.println("\n\n---------Search Results ("+ String.format("%.7f", (System.currentTimeMillis() - startTime)/1000) +" seconds)------------------");
    	    	System.out.println("\n|Rank|\t|Frequency|\t|WebPage|");
    	    	for(int i=pagerank.size()-1,j=1;i>=0;i--,j++) {
    	    		System.out.println(j+" \t"+pagerank.get(keys.get(i))+ " \t\t"+keys.get(i));
    	    	}
    	    
	    	}
    	   }
    	}
    }
