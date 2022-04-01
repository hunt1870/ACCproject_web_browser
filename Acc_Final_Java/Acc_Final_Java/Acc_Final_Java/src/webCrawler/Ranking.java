package webCrawler;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ranking {
	public static HashMap<String,Integer> rank(Hashtable<String, Integer> frequency) {
		List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(frequency.entrySet());  // Create a list from elements of HashMap
    	Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {  // Sort the list using lambda expression
    	public int compare(Map.Entry<String, Integer> o1,   //Compare the two arguments 
    	Map.Entry<String, Integer> o2)
    	{
    	return (o1.getValue()).compareTo(o2.getValue());
    	}
    	});
    	HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); // put data from sorted list to hashmap
    	for (Map.Entry<String, Integer> bb : list) {
    	temp.put(bb.getKey(), bb.getValue());
    	}
    	return temp;
	}
}
