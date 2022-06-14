package com.lgcns.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {
	
	public static <T> void PrintArray(ArrayList<T> array) {

		System.out.print("arraylist : ");
		for (int i=0; i<array.size(); i++) {
			System.out.print(array.get(i) + ", ");
		}
		System.out.println();

	}
	
	public static <T> void PrintArray(List<T> array) {

		System.out.print("list : ");
		for (int i=0; i<array.size(); i++) {
			System.out.print(array.get(i) + ", ");
		}
		System.out.println();

	}
	
	public static <T> void PrintArray(Map<T, T> map) {

		System.out.print("map : ");
		for (Entry<T, T> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
		System.out.println();

	}
	
	public static void PrintArray(int[] array) {

		System.out.print("array : ");
		for (int i=0; i<array.length; i++) {
			System.out.print(array[i] + ", ");
		}
		System.out.println();

	}
	
	public static boolean unAuthorized(String user) throws IOException {
		File path = new File(".");
		String code = path.getAbsolutePath() + "/CUSTOMER.TXT";
		
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		
		fileReader = new FileReader(code);
		bufferedReader = new BufferedReader(fileReader);
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			// stringBuilder.append(line).append('\n');
			if (line.equals(user)) {
				return true;
			}
		}
		return false;
	}
}
