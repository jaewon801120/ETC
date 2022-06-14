package com.lgcns.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class RunManager {

	public static Map<String, ArrayList<String[]>> mapRegistry;
	public static ArrayList<String[]> arrSC;

	public static void main(String[] args) {

		try {
			LoadRegistry();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			return;
		}

		String name = "";
		int param = 0;

		Scanner sc = new Scanner(System.in);
		name = sc.next();

		param = sc.nextInt();

		ArrayList<String[]> arrSC = getRegistry(name);
		if (arrSC.size() == 0) {
			return;
		}
		
		String result = "";
		for (int i=0; i<arrSC.size(); i++) {
			String[] arrTemp = arrSC.get(i);
			for (String str : arrTemp) {
				try {
					result += runService(str, param) + ",";
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		}
		
		System.out.println(result);

	}

	public static ArrayList<String[]> getRegistry(String name) {
		return mapRegistry.get(name);
	}

	public static String runService(String SI, int param) throws IOException {

		File path = new File(".");
		String code = path.getAbsolutePath() + "/SERVICE/" + SI;

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			mapRegistry = new LinkedHashMap<>();

			fileReader = new FileReader(code);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// stringBuilder.append(line).append('\n');
				int find = line.indexOf("#");
				String temp = line.substring(0, find);
				int nParam = Integer.parseInt(temp);
				if (nParam == param) {
					String sv = line.substring(find + 1);
					return sv;
				}
			}

		} finally {
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (Exception ex) {
					/* Do Nothing */ }
			if (fileReader != null)
				try {
					fileReader.close();
				} catch (Exception ex) {
					/* Do Nothing */ }
		}

		return "";
	}

	public static void LoadRegistry() throws IOException {

		File path = new File(".");
		String code = path.getAbsolutePath() + "/REGISTRY.TXT";

		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			mapRegistry = new LinkedHashMap<>();

			fileReader = new FileReader(code);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				ArrayList<String[]> arrSC = new ArrayList<String[]>();

				int find = line.indexOf("#");
				String name = line.substring(0, find);
				String temp = line.substring(find + 1);
				String[] arrTemp = temp.split(";");
				for (int i = 0; i < arrTemp.length; i++) {
					String strSC = arrTemp[i];
					String[] arrSI = strSC.split(",");
					arrSC.add(arrSI);
				}

				mapRegistry.put(name, arrSC);
			}

		} finally {
			if (bufferedReader != null)
				try {
					bufferedReader.close();
				} catch (Exception ex) {
					/* Do Nothing */ }
			if (fileReader != null)
				try {
					fileReader.close();
				} catch (Exception ex) {
					/* Do Nothing */ }
		}
	}

}
