package nishantshinde.java8.lambda;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Example1 {

	
	public static void method1() {
		
		FileFilter textFileFilter = 
				(File pathname) -> pathname.getName().endsWith(".txt");
		
		// Note: Parameter type is optional
		textFileFilter = (pathname) -> pathname.getName().endsWith(".txt");
		
		// Note: Even brackets around a single parameter type are optional
		textFileFilter = pathname -> pathname.getName().endsWith(".txt");
		
	}

	public static void method2() {
		
		List<String> listOfStrings = Arrays.asList("abc", "def", "ghi", "jkl" , "mno");
		
		Comparator<String> reverse = (String s1, String s2) -> s2.compareTo(s1);
		
		Collections.sort(listOfStrings, reverse);
		
		System.out.println(listOfStrings);
		
		// Note: Parameter type is optional
		reverse = (s1, s2) -> s2.compareTo(s1);

	}
	
	public static void method3() {
//		Predicate<String> id = Predicate.isEqual(target); 
	}

	public static void method4() {
		
		List<String> listOfStrings = Arrays.asList("abc", "def", "ghi", "jkl" , "mno");
		List<String> result = new ArrayList<String>();
		
		Consumer<String> print = System.out::println; 
		Consumer<String> augmentResult = result::add;
		
		listOfStrings.forEach(print.andThen(augmentResult));
		
	}
	
	public static void main(String[] args) {

		method1();
		method2();
		method3();	
		
	}

}
