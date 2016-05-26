package edu.utdallas.fileio;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;


/** Utility Class contains utility functions for sorting maps, creating list from maps etc.
 * @author ramesh
 *
 */
public class Utility {

	/** this function takes unsorted map as input and returns a sorted map
	 * @param unSortedMap
	 * @return
	 */
	public static LinkedHashMap<String, Integer> sortedMap(
			HashMap<String, Integer> unSortedMap) {

		LinkedList<Entry<String, Integer>> mapList = new LinkedList<Entry<String, Integer>>(
				unSortedMap.entrySet());
		Collections.sort(mapList, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().compareTo(o2.getValue());
			}

		});

		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

		for (Entry<String, Integer> entrySet : mapList) {
			sortedMap.put(entrySet.getKey(), entrySet.getValue());
		}

		return sortedMap;

	}

	/** This function creates a sorted list using the maps
	 * @param unSortedMap
	 * @return
	 */
	public static LinkedList<Entry<String, Integer>> mapTosortedList(
			HashMap<String, Integer> unSortedMap) {

		LinkedList<Entry<String, Integer>> mapList = new LinkedList<Entry<String, Integer>>(
				unSortedMap.entrySet());
		Collections.sort(mapList, new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().compareTo(o2.getValue());
			}

		});

		return mapList;

	}

	/**
	 *  This function takes a sorted List as input and  print top N elements
	 * @param list
	 * @param n
	 */
	public static void printTopNTokens(LinkedList<Entry<String, Integer>> list,
			int n) {
		Iterator<Entry<String, Integer>> iterator = list.descendingIterator();
		int size = list.size() - 1;
		if(size<0){
			System.out.println("Empty Set");
			return;
		}
		System.out.format("\n%-20s%20s\n","Token","Frequency");
		System.out.println("---------------------------------------------------------------------------------");
		for (int i = 0; i < n; i++) {
			System.out.format("\n%-20s%20s",list.get(size - i).getKey(),list.get(size - i).getValue());
		}
		System.out.println();

	}

	/**
	 * This function prints the list
	 * @param list
	 */
	public static void printList(LinkedList<Entry<String, Integer>> list) {
		System.out.println("");
		for (Entry<String, Integer> entry : list) {
			System.out.println(entry.getKey() + "<==>" + entry.getValue());
		}

	}

	/**
	 * This function returns the count of tokens that occurred only once
	 * @param list
	 * @return
	 */
	public static int getCountTokenOccuredOnce(
			LinkedList<Entry<String, Integer>> list) {
		int count = 0;
		for (Entry<String, Integer> entry : list) {

			if (entry.getValue() > 1) {
				break;
			}
			count++;
		}
		//System.out.println("The number of words that occur only once:"+count);
		return count;
	}
	
	
	
	/** this function takes unsorted map as input and returns a sorted map
	 * @param unSortedMap
	 * @return
	 */
	public static LinkedHashMap<Long, Double> sortedScoreMap(
			HashMap<Long, Double> unSortedMap) {

		LinkedList<Entry<Long, Double>> mapList = new LinkedList<Entry<Long, Double>>(
				unSortedMap.entrySet());
		Collections.sort(mapList, new Comparator<Entry<Long, Double>>() {

			@Override
			public int compare(Entry<Long, Double> o1,
					Entry<Long, Double> o2) {
				// TODO Auto-generated method stub
				return o1.getValue().compareTo(o2.getValue());
			}

		});

		LinkedHashMap<Long, Double> sortedMap = new LinkedHashMap<Long, Double>();

		for (Entry<Long, Double> entrySet : mapList) {
			sortedMap.put(entrySet.getKey(), entrySet.getValue());
		}

		return sortedMap;

	}

	/** This function creates a sorted list using the maps
	 * @param unSortedMap
	 * @return
	 */
	public static LinkedList<Entry<Long, Double>> mapTosortedScoreList(
			HashMap<Long, Double> unSortedMap) {

		LinkedList<Entry<Long, Double>> mapList = new LinkedList<Entry<Long, Double>>(
				unSortedMap.entrySet());
		Collections.sort(mapList, new Comparator<Entry<Long, Double>>() {

			@Override
			public int compare(Entry<Long, Double> o1,
					Entry<Long, Double> o2) {
				// TODO Auto-generated method stub
				return o2.getValue().compareTo(o1.getValue());
			}

		});

		return mapList;

	}

	

}
