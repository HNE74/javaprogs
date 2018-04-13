package de.hne;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamBasics {
	
	public static void main(String args[]) {
		StreamBasics streamBasics = new StreamBasics();
		streamBasics.streamCreationExamples();
	}
	
	private void streamCreationExamples() {		
		System.out.println("\nString Filterung:");
		final String[] names = { "Jan", "Helga", "Frank", "Bianka", "Melanie", "Janis" };
		final Predicate<String> startsWithJPred = txt -> txt.toLowerCase().startsWith("j");
		final Stream<String> namesString = Arrays.stream(names);
		List<String> jNamesList = namesString.filter(startsWithJPred).collect(Collectors.toList());
		jNamesList.forEach(System.out::println);

		System.out.println("\nInteger Filterung:");		
		IntStream integerStream = IntStream.range(20, 40);
		final IntPredicate divByThreePred = val -> val % 3 == 0;
		integerStream.filter(divByThreePred).boxed().forEach(System.out::println);
		
		System.out.println("\nMapping:");
		integerStream = IntStream.range(0, 10);
		integerStream.map(x -> x * 2).boxed().forEach(System.out::println);
		
		System.out.println("\nFlat Mapping von verschachtelten Streams:");
		final Stream<String> senctencesStream = Arrays.asList( "Dies ist ein Java 8 Beispiel.",
				                                      "Nur an Linienfreitagen auszufuehren.",
				                                      "Java 8 ist toll!").stream();
		senctencesStream.flatMap(line -> Stream.of(line.split(" "))).filter(txt -> txt.length() > 10).
				collect(Collectors.toList()).forEach(System.out::println);
		
		System.out.println("\nPeek auf Elemente eines Streams:");
		final Stream<String> txtStream = Arrays.stream(new String[] { "ab4grth", "55itgg", "hgiwer6", "4HgJ55" });
		txtStream.map(txt -> txt.toUpperCase()).peek(System.out::println).map(txt -> txt.replaceAll("[0-9]", "")).
								collect(Collectors.toList()).forEach(System.out::println);
		
		System.out.println("\nParalleler Stream mit Zustand unsynchronisiert:");		
	    for (int i = 0; i < 5; i++) {
	        Set<Integer> seen = new HashSet<>();       	    	
            System.out.println(sumUpUniqueNumbers(seen));
	    }	    
		
		System.out.println("\nParalleler Stream mit Zustand synchronisiert:");		
	    for (int i = 0; i < 5; i++) {
	        Set<Integer> seen = Collections.synchronizedSet(new HashSet<>());       	    	
            System.out.println(sumUpUniqueNumbers(seen));
	    }	    
	}
	
	private int sumUpUniqueNumbers(Set<Integer> seen) {
        IntStream stream = IntStream.of(1, 2, 1, 2, 3, 4, 4, 5); 
        int sum = stream.parallel().map(
            //stateful behavioral parameter.
            e -> {
                  if (seen.add(e))
                      return e;
                  else
                      return 0;
              }).sum();	
        
        return sum;
	}

}
