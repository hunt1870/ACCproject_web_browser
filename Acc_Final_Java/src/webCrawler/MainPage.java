package webCrawler;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class MainPage {
	private static final String specurl = "https://www.javatpoint.com/";//"https://www.wikipedia.org/";//passing w3 url to generate repository
	public static void main(String args[]) {
		WebCrawler web=new WebCrawler();
    	System.out.println("Crawling started....");
		HashSet<String> allUrl= web.getlinks(specurl, 0);
    	System.out.println("Database has been created....\nNow you can start searching");
    	Scanner myObj = new Scanner(System.in);  // Create a Scanner object
    	while(true) {
    		System.out.println("Please enter a word for searching...");
    	    String search = myObj.next();
    	    if(search.equals("0")||search.isEmpty()) {System.exit(0);}
    	    SpellChecker spellcorrect = new SpellChecker();                                   // initialize spellchecker
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
    	    Hashtable<String, Integer> frequency = Freqency.searchString(search);
    	    if(frequency.isEmpty()) {
    	    	System.out.println("\n\nNo match found in the crawled webpages");
    	    	System.out.println("\n\nYou want to search from suggestions? \n Y --> Yes\n N --> No ");
    	    	String choice = myObj.next();
    	    	
    	    	if(choice.equals("Y")) {
    	    		System.out.println("\n\nwhich suggestion you want to search?");
    	    		System.out.println("1 --> " + suggestion[0] + "\n2 --> " + suggestion[1] + "\n3 --> " + suggestion[2]);
    	    		int textChoice = myObj.nextInt();
    	    		if(textChoice == 1 || textChoice == 2 || textChoice == 3) {
    	    			search=suggestion[textChoice - 1];
        	    		frequency=Freqency.searchString(search);
            	    	System.out.println("\nSearching for "+search);
            	    	Enumeration<String> e = frequency.keys();
        	    	    System.out.println("\n\n---------Frequency of "+search+" in the WebPage ------------------");
        	    	    while (e.hasMoreElements()) {
        	    			String key = (String) e.nextElement();
        	    			System.out.println(key + " : " + frequency.get(key));
        	    		}	
    	    		} else {
    	    			System.out.println("\n\n------INVALID INPUT------");
    	    			continue;
    	    		}
    	    	}else {
    	    		continue;
    	    	}
    	    }else {
	    	    List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(frequency.entrySet());
	    	    Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
	                public int compare(Map.Entry<String, Integer> o1,
	                                   Map.Entry<String, Integer> o2)
	                {
	                    return (o1.getValue()).compareTo(o2.getValue());
	                }
	            });
	    	    HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
	            for (Map.Entry<String, Integer> aa : list) {
	                temp.put(aa.getKey(), aa.getValue());
	            }
	    	    System.out.println("\n\n---------Frequency of "+search+" in the WebPage ------------------");
	    	    for (Map.Entry<String, Integer> en : temp.entrySet()) {
	                System.out.println(en.getKey() + " " + en.getValue());
	            }
    	    }
    	}
    }
}
