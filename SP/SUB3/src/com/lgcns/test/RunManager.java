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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class RunManager {

	public static Map<String, ArrayList<String[]>> mapRegistry;
	public static ArrayList<String[]> arrSC;

	public static void main(String[] args) throws Exception {

		try {
			LoadRegistry();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return;
		}
		
		RunManager.start();

//		String name = "";
//		int param = 0;
//
//		Scanner sc = new Scanner(System.in);
//		name = sc.next();
//		param = sc.nextInt();
//		String strURL = "http://127.0.0.1:8080/" + name + "/" + param;
//
//		HttpClient httpClient = new HttpClient();
//		httpClient.start();
//		Request request = httpClient.newRequest(strURL);
//		request.header("x-api-key", "APIKEY1");
//		request.method("GET");
//		ContentResponse result = request.send();
//
//		System.out.println(result.getContentAsString());
//        System.out.println(result.getStatus());
		//ContentResponse contentRes = httpClient.newRequest(strURL).method(HttpMethod.GET).send();

//		ArrayList<String[]> arrSC = getRegistry(name);
//		if (arrSC.size() == 0) {
//			return;
//		}
//
//		String result = "";
//		for (int i = 0; i < arrSC.size(); i++) {
//			String[] arrTemp = arrSC.get(i);
//			for (String str : arrTemp) {
//				try {
//					result += runService(str, param) + ",";
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					// e.printStackTrace();
//				}
//			}
//		}
//
//		System.out.println(result);

	}
	
	public static void start() throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(8080);
		server.addConnector(http);

		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(MyServlet.class, "/*");
//		for (Map.Entry<String, ArrayList<String[]>> entry : mapRegistry.entrySet()) {
//			servletHandler.addServletWithMapping(MyServlet.class, "/" + entry.getKey() + "/" + entry.getValue());
//		}
		server.setHandler(servletHandler);

		server.start();
		server.join();
	}
	
	public static void send(String strURL) throws Exception {
		HttpClient httpClient = new HttpClient();
		httpClient.start();
		Request request = httpClient.newRequest(strURL);
		request.header("x-api-key", "APIKEY1");
		request.method("GET");
		ContentResponse result = request.send();

		System.out.println(result.getContentAsString());
        System.out.println(result.getStatus());
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
				
				find = temp.indexOf("#");
				String fee = temp.substring(0, find);
				String temp2 = temp.substring(find + 1);
				String[] arrTemp = temp2.split(";");
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
