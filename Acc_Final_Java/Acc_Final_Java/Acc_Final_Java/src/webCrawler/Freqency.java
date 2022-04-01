package webCrawler;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Freqency {

	private static final int freq = 1;	
	private static Hashtable<String, Hashtable<String, Integer>> map = new Hashtable<String, Hashtable<String, Integer>>();
	
	public static Hashtable<String, Integer> searchString(String search) {
		
		Map <String, Integer> frequencyFromInnerMap; 
		Hashtable <String, Integer> frequency = new Hashtable<String, Integer>();
		Enumeration<String> e = map.keys();
		Integer value;
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			frequencyFromInnerMap = map.get(key);
			try {
				value = frequencyFromInnerMap.get(search);
				frequency.put(key.replace(".txt", " "), value);
			} catch(NullPointerException n) {
				continue;
			}
		}
		
		return frequency;
		
	}
	
	public static void readFiles() {
		
		Hashtable<String, Integer> innerMap; 
		File dir = new File(FilePath.textFile);
        File[] files = dir.listFiles();
        
        // Fetching all the files
        
        for (File file : files) {
            if(file.isFile()) {
            	
            	innerMap = new Hashtable<String, Integer>();
            	//map.put(file.getName(), null);  // Storing Filename as key into hash table
            	FileReader fr = null;
				try {
					fr = new FileReader(file);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
        		try (BufferedReader br = new BufferedReader(fr)) {
        			String line;
        			while ((line = br.readLine()) != null) {
        				processLine(line, innerMap);
        			}
        		} catch (IOException e1) {
					e1.printStackTrace();
				}
        		map.put(file.getName(), innerMap);
            }
        }
	}

	public static void processLine(String line, Hashtable<String, Integer> innerMap) {
		StringTokenizer st = new StringTokenizer(line);
		while (st.hasMoreTokens()) {
			String token  =st.nextToken();  //getting the word from the whole line
			
			Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");  //pattern to find special character
			Pattern word = Pattern.compile("([a-zA-Z0-9]+)");  //pattern to recognize only words
			Pattern wordWithHyphen = Pattern.compile("(.*[a-zA-Z0-9][-][a-zA-Z0-9.]+)");  //pattern to recognize words with hyphen
			Pattern nonIntNum = Pattern.compile("([0-9]+[.][0-9]+)");  //pattern to recognize floating point numbers
			Pattern IntNumWithComma = Pattern.compile("([0-9]+[,][0-9]+)");  //pattern to recognize numbers with comma
			
			Matcher matcher = pattern.matcher(token);
			Matcher wordMatcher = word.matcher(token);
			Matcher wordWithHyphenMatcher = wordWithHyphen.matcher(token);
			Matcher nonIntNummatcher = nonIntNum.matcher(token);
			Matcher IntNumWithCommaMatcher = IntNumWithComma.matcher(token);
			
			boolean isStringContainsSpecialCharacter = matcher.find(); //give true/false based on regex
			
			if(isStringContainsSpecialCharacter) {
				
				while(nonIntNummatcher.find()) {
					addWord(innerMap, nonIntNummatcher.group());
				}
				
				while(wordMatcher.find()) {
					addWord(innerMap, wordMatcher.group());
				}
				
				while(wordWithHyphenMatcher.find()) {
					addWord(innerMap, wordWithHyphenMatcher.group());
				}
				
				while(IntNumWithCommaMatcher.find()) {
					addWord(innerMap, IntNumWithCommaMatcher.group());
				}
			} else
				addWord(innerMap, token);  //adding the word if it do not contain any special character or do not need any pre-processing

		}
	}

	public static void addWord(Hashtable<String, Integer> innerMap, String word) {
		Object obj = innerMap.get(word);  //To get the object for the specific word
		if (obj == null) {  //checking whether the object is already created or not
			innerMap.put(word, freq);  //If object is not created than creating the new object
		} else {
			int i = ((Integer) obj).intValue() + 1;  //Object is already created so just increasing the frequency by one
			innerMap.put(word, i);  //updating the frequency of the object
		}
	}
}