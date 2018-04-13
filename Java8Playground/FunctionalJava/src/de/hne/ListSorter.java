package de.hne;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Demonstriert folgene neue Java 8 Features:
 * - Lambdas
 * - Aggregat Operationen (z.B. for-each)
 * - Methodenreferenzen
 * @author 057530
 */
public class ListSorter {
	
	public static void main(String args[]) {
		ListSorter sorter = new ListSorter();
		
		List<String> values = sorter.createList();
		sorter.sortWithInnerClass(values);
		sorter.printList(values, "\nList sorted with inner class:");
		
		values = sorter.createList();
		sorter.sortWithLambda(values);
		sorter.printList(values, "\nList sorted with lambda:");
		
		values = sorter.createList();
		Collections.sort(values, Comparator.reverseOrder());
		sorter.printList(values, "\nReversed list:");
	}
	
	private List<String> createList() {
		return Arrays.asList("Alphacentauri", "Beta", "Gamma", "Delta");		
	}
	
	/**
	 * Sortierung einer Collection nach Stringlänge mit einer inneren Klasse.
	 * @param values
	 * @return
	 */
	private List<String> sortWithInnerClass(final List<String> values) {	
		Collections.sort(values, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return Integer.compare(o1.length(), o2.length());
			}			
		});
		
		return values;		
	}
	
	/**
	 * Sortierung einer Collection mit einer Lambda Funktion.
	 * @param values
	 * @return
	 */
	private List<String> sortWithLambda(final List<String> values) {		
		values.sort( (o1, o2) -> Integer.compare(o1.length(), o2.length()));
		return values;
	}
	
	/**
	 * Ausgabe einer Collection mit for-each und einer Methodenreferenz.
	 * @param values
	 * @param txt
	 */
	private void printList(List<String> values, String txt) {
		if(txt != null) {
			System.out.println(txt);
		}		
		
		// Verwendung von Methodenreferenzen anstelle von Lambdas, hier
		// könnte auch stehen values.forEach(value -> System.out.println(value))
		values.forEach(System.out::println);
	}

}
