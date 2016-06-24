package nishantshinde.java8.collectors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author José
 */
public class CollectorsExample {

    public static void main(String... args)  {
        
        List<Person> persons = new ArrayList<>();
        
        try (
            BufferedReader reader = 
                new BufferedReader(
                    new InputStreamReader(
                        CollectorsExample.class.getResourceAsStream("people.txt")));

            Stream<String> stream = reader.lines();
        ) {
            stream.map(line -> {
	                String[] s = line.split(" ");
	                return new Person(s[0].trim(), Integer.parseInt(s[1]), s[2]);
                })
        		.forEach(person->persons.add(person));
            
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        
//        method1(persons);
//        method2(persons);
        method3(persons);
                
    }
    
    private static void method1(List<Person> persons) {

    	Optional<Person> opt = 
        persons.stream().filter(p -> p.getAge() >= 20)
                .min(Comparator.comparing(Person::getAge));
        System.out.println(opt);
        
        Optional<Person> opt2 = 
        persons.stream().max(Comparator.comparing(Person::getAge));
        System.out.println(opt2);

    }
    
    private static void method2(List<Person> persons) {
        Map<Integer, String> map = 
        persons.stream()
                .collect(
                        Collectors.groupingBy(
                                Person::getAge, 
                                Collectors.mapping(
                                        Person::getName, 
                                        Collectors.joining(", ")
                                )
                        )
                );
        System.out.println(map);
    }

    private static void method3(List<Person> persons) {
    	
        List<Person> list1 = persons.subList(0, 10); 
		List<Person> list2 = persons.subList(10, persons.size());
		
		System.out.println("Map 1 -> ");
        Map<Integer, List<Person>> map1 = mapByAge(list1);
        map1.forEach((age, people)->System.out.println(age+" - "+people));

        System.out.println("Map 2 -> ");
        Map<Integer, List<Person>> map2 = mapByAge(list2);
        map2.forEach((age, people)->System.out.println(age+" - "+people));

        map2.entrySet().stream()
        	.forEach( 
        			entry ->
        			map1.merge(
        					entry.getKey(), 
        					entry.getValue(), 
        					(l1,l2)->{
        						l1.addAll(l2);
        						return l1;
        					})
        	);
        
        System.out.println("Merged Map 1 -> ");
        map1.forEach((age, people)->System.out.println(age+" - "+people));
        
        
        Map<Integer,Map<String,List<Person>>> bimap = new HashMap<>();
        
        persons.forEach(
        	person ->
        	bimap.computeIfAbsent(
    			person.getAge(), 
    			HashMap::new        			
        	).merge(
        		person.getGender(), 
    			new ArrayList<>(Arrays.asList(person)), 
    			(l1, l2) -> {
    				l1.addAll(l2);
    				return l1;
    			}
        	)
        );
        
        System.out.println("BiMap -> ");
        bimap.forEach(
        	(key,value) -> System.out.println(key+" -> "+value)
        );
    }
    
    private static Map<Integer, List<Person>> mapByAge(List<Person> list) {
    	
    	Map<Integer, List<Person>> map = 
    			list.stream().collect(
    					Collectors.groupingBy(
								Person::getAge
						)
    			);
    	
    	return map;
    }
}
