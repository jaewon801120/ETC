package com.lgcns.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String cmd = req.getServletPath();
		System.out.println(cmd);
		String name = req.getPathInfo();
		System.out.println(name);

//		for (String key : jsonObj.keySet()) {
//			System.out.print("Key : " + key + " / Value Type : ");
//			JsonElement je = jsonObj.get(key);
//			if (je.isJsonPrimitive()) {
//				if (je.getAsJsonPrimitive().isString()) {
//					System.out.println("String");
//					System.out.println(je.getAsString());
//				} else if (je.getAsJsonPrimitive().isNumber()) {
//					System.out.println("Number");
//					System.out.println(je.getAsInt());
//				} else if (je.getAsJsonPrimitive().isBoolean()) {
//					System.out.println("Boolean");
//				} else if (je.getAsJsonPrimitive().isJsonNull()) {
//					System.out.println("null");
//				}
//			} else if (je.isJsonArray()) {
//				System.out.println("Array");
//			} else if (je.isJsonObject()) {
//				System.out.println("Object");
//			} else if (je.isJsonNull()) {
//				System.out.println("null");
//			}
//		}

		JsonObject resJson = new JsonObject();
		if (cmd.equals("/CREATE")) {

			String content = getBody(req);
			//System.out.println("req : " + content);

			Gson gson = new Gson();
			JsonObject jsonObj = gson.fromJson(content, JsonObject.class);

			int size = jsonObj.get("QueueSize").getAsInt();

			if (!MapQueueMsg.CreateQue(name, size)) {
				resJson.addProperty("Result", "Queue Exist");
			} else {
				resJson.addProperty("Result", "Ok");
			}
		} else if (cmd.equals("/SEND")) {

			String content = getBody(req);
			//System.out.println("req : " + content);

			Gson gson = new Gson();
			JsonObject jsonObj = gson.fromJson(content, JsonObject.class);

			String message = jsonObj.get("Message").getAsString();

			if (!MapQueueMsg.SendQue(name, message)) {
				resJson.addProperty("Result", "Queue Full");
			} else {
				resJson.addProperty("Result", "Ok");
			}
		} else if (cmd.equals("/ACK")) {
			
			int n = name.lastIndexOf("/");
			String msgID = name.substring(n + 1);
			MapQueueMsg.RemoveQue(msgID);
			
			resJson.addProperty("Result", "Ok");
		} else if (cmd.equals("/FAIL")) {
			
			int n = name.lastIndexOf("/");
			String name_org = name.substring(0, n);
			String msgID = name.substring(n + 1);
			MapQueueMsg.RestoreQue(name_org, msgID);
			name = name_org;
			
			resJson.addProperty("Result", "Ok");
		} else {
			resJson.addProperty("Result", "Fail");
		}
		
		MapQueueMsg.Trace(name);

		String ret = resJson.toString();
		//System.out.println("res : " + ret);

		res.setStatus(200);
		res.setContentType("application/json");
		res.getWriter().print(ret);
		res.getWriter().flush();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String cmd = req.getServletPath();
		System.out.println(cmd);
		String name = req.getPathInfo();
		System.out.println(name);

		//String content = getBody(req);
		//System.out.println(content);

		JsonObject resJson = new JsonObject();
		if (cmd.equals("/RECEIVE")) {
			MsgObj msg = MapQueueMsg.ReceiveQue(name);
			if (msg != null) {
				String message = msg.getMessage();
				int msgID = msg.getMsgID();
				
				resJson.addProperty("Result", "Ok");
				resJson.addProperty("MessageID", String.valueOf(msgID));
				resJson.addProperty("Message", message);
			} else {
				resJson.addProperty("Result", "No Message");
			}
		} else {
			resJson.addProperty("Result", "Fail");
		}
		
		MapQueueMsg.Trace(name);

		String ret = resJson.toString();
		System.out.println("res : " + ret);

		res.setStatus(200);
		res.setContentType("application/json");
		res.getWriter().print(ret);
		res.getWriter().flush();
	}

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();

		return body;
	}
}
