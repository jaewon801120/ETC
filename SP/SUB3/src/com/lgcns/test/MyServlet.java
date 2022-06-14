package com.lgcns.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String user = req.getHeader("x-api-key");
		boolean bUser = Util.unAuthorized(user);
		if (bUser == false) {
			res.setStatus(200);
			res.getWriter().write("{\"result\":\"unauthorized\"}");
			return;
		}
		
		String pathInfo = req.getPathInfo();
		String[] path = pathInfo.split("/");
		ArrayList<String[]> arrSC = RunManager.mapRegistry.get(path[1]);
		
		for (int i=0; i<arrSC.size(); i++) {
			String[] arrSI = arrSC.get(i);
			for (String url : arrSI) {
				try {
					RunManager.send(url + "/" + path[1] + "/" + path[2]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		res.setStatus(200);
		res.getWriter().write("{\"result\" : \"ok\"}");
	}
}
