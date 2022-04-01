package webCrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.TreeMap;

public class SpellChecker{

	Map<String, Integer> dict = new HashMap<>();
	
	public void createDictionary(String filename) {	
		try {
			File file = new File(filename);
			Scanner reader = new Scanner(file);	                                          // initialize file reader      
			String readline = null;										  
			while (reader.hasNextLine()) { 								                  // read lines in the file
				readline = reader.nextLine();
				String word = readline.toLowerCase();
				if (!readline.contains(" ")) {
					dict.put(word, dict.getOrDefault(word, 0)+1); 		   				  // stores string in the dictionary along with frequency
				} else {
					String[] strs= readline.split("\\s");								  //if line contains multiple strings
					for(String str: strs) {
						str = str.toLowerCase();   										
						str = str.replaceAll("[^a-z]", "");								// if string contains non-alphabet character at the end and the beginning
						dict.put(str, dict.getOrDefault(str, 0)+1);
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found! Please check the file name correctly");   // throw exception if file not found
		}
	}

	public String[] suggestSimilarWord(String inputWord) {
		if (inputWord.length()==0 || inputWord==null )
			return null;
		String s = inputWord.toLowerCase();                                 			  // convert input to lower character string in order to compare it with dictionary
		String[] res = new String[3];
		TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> map = new TreeMap<>(); 		  // initialize tree 

		for (String w: dict.keySet()) {			
			int eDist = editDistance.editDistance(w, s);		                          // edit distance calculate with each word in the dictionary
			int frequency = dict.get(w);					                              // get the frequency of each word and store it in the below tree
			TreeMap<Integer,TreeSet<String>> freqMap = map.getOrDefault(eDist, new TreeMap<>());
			TreeSet<String> set = freqMap.getOrDefault(frequency, new TreeSet<>());       // set the treeset
			set.add(w);			                                                          // add word in the treeset
			freqMap.put(frequency, set);												  // add frequency in treemap
			map.put(eDist, freqMap);													  // add editdistance in treemap
			
		}
		int i = 0,j = 0;
		int k = map.firstEntry().getValue().lastEntry().getKey();							// last entry in the tree
		Object[] temp = null;
		String[] tempString = null;
		while(i<3) {																		// whole loop to find the topmost suggestions by
			try{																			// traversing the tree from bottom to up
				temp = map.ceilingEntry(j).getValue().floorEntry(k).getValue().toArray();			
			} catch(Exception e) {
				j++;
				k = map.ceilingEntry(j).getValue().lastEntry().getKey();
			}
			tempString = Arrays.copyOf(temp, temp.length , String[].class);					// converting object array to string array
			
			for(String str: tempString) {													// inserting array elements in result
				if(str!=null) {
					if(i>0 && res[i-1]==str) {
						break;
					}
					}
					res[i]=str;
					if(i==2) {
						return res;
					}
					i++;
				}
			k--;
		}
			
			return res;
		}


}
