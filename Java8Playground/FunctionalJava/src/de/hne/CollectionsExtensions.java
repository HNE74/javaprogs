package de.hne;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Demonstriert folgene neue Java 8 Features:
 * - Bulk Operationen
 * - Prädikate
 * - Interne Iteration: removeIf mit Prädikat
 * - Unaere Operatoren
 */
public class CollectionsExtensions {

	public static void main(String args[]) {
		CollectionsExtensions colExt= new CollectionsExtensions();
		colExt.predicateExamples();
		colExt.unaryExamples();
	}
	
	private void predicateExamples() {
		final Predicate<String> isNull = str -> str == null;
		final Predicate<String> isEmpty = String::isEmpty;
		final Predicate<Person> isAdult = p -> p.getAge() > 17;
		
		System.out.println("Prädikate:");
		System.out.println("null Test: " + isNull.test(""));
		System.out.println("null Test: " + isNull.test(null));
		System.out.println("empty Test: " + isEmpty.test(""));
		System.out.println("empty Test: " + isEmpty.test("Bla"));
		System.out.println("adult Test: " + isAdult.test(new Person(15)));
		System.out.println("adult Test: " + isAdult.test(new Person(18)));
		System.out.println("adult Test negate: " + isAdult.negate().test(new Person(18)));	
		
		System.out.println("\nPrädikate zur Enfernung von Elementen aus Liste:");
		List<Integer> values = createIntList();
		final Predicate<Integer> smallerTen = val -> val < 10;
		values.removeIf(smallerTen);
		values.forEach(System.out::println);
	}

	private List<Integer> createIntList() {
		List<Integer> values = new ArrayList<>();
		values.addAll(Arrays.asList( 5, 10, 15, 30, 2 , 1 ));
		
		return values;
	}
	
	private void unaryExamples() {
		final UnaryOperator<Integer> setNullToZero = val -> val == null ? val = 0 : val;
		List<Integer> intList = createIntListWithNull();
		
		// Das funkioniert nicht
		//intList.forEach(val -> setNullToZero.apply(val));
		
		// Aber damit geht es
		intList.replaceAll(setNullToZero);
		
		System.out.println("\nUnaere Operatoren zur Manipulation von Listelementen:");
		intList.forEach(System.out::println);
	}

	private List<Integer> createIntListWithNull() {
		List<Integer> values = new ArrayList<>();
		values.addAll(Arrays.asList( 5, null, 15, null, 2 , 1 ));
		
		return values;
	}
	
	
	private class Person {		
		int age;
		
		public Person(int age) {
			this.age = age;
		}
		
		public int getAge() {
			return this.age;
		}
	}
}
