package com.lgcns.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class RunManager {

	public static Map<String, String> mapRegistry;

	public static void main(String[] args) {

		try {
			LoadRegistry();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		String name = "";
		int param = 0;

		Scanner sc = new Scanner(System.in);
		name = sc.next();

		param = sc.nextInt();

		String SI = getRegistry(name);
		if (SI.equals("")) {
			return;
		}

		//System.out.println("Registry : " + SI);

		String service = "";;
		try {
			service = getService(SI, param);
			System.out.println(service);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getRegistry(String name) {
		return mapRegistry.get(name);
	}

	public static String getService(String SI, int param) throws IOException {

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

		StringBuilder stringBuilder;
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;

		try {
			mapRegistry = new LinkedHashMap<>();

			stringBuilder = new StringBuilder();
			fileReader = new FileReader(code);
			bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				// stringBuilder.append(line).append('\n');
				int find = line.indexOf("#");
				String name = line.substring(0, find);
				String SI = line.substring(find + 1);
				mapRegistry.put(name, SI);
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
