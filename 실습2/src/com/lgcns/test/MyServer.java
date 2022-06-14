package com.lgcns.test;

import java.util.HashMap;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class MyServer {

	public HashMap<String, QueueMsg> mapQueue = new HashMap<>();

	public void main(String[] args) throws Exception {
		new MyServer().start();
	}

	public void start() throws Exception {
		Server server = new Server();
		ServerConnector http = new ServerConnector(server);
		http.setHost("127.0.0.1");
		http.setPort(8080);
		server.addConnector(http);

		ServletHandler servletHandler = new ServletHandler();
//		servletHandler.addServletWithMapping(MyServlet.class, "/*");
		servletHandler.addServletWithMapping(MyServlet.class, "/CREATE/*");
		servletHandler.addServletWithMapping(MyServlet.class, "/SEND/*");
		servletHandler.addServletWithMapping(MyServlet.class, "/RECEIVE/*");
		servletHandler.addServletWithMapping(MyServlet.class, "/ACK/*");
		servletHandler.addServletWithMapping(MyServlet.class, "/FAIL/*");
//		servletHandler.addServletWithMapping(CreateServlet.class, "/CREATE/*");
//		servletHandler.addServletWithMapping(SendServlet.class, "/SEND/*");
//		servletHandler.addServletWithMapping(ReceiveServlet.class, "/RECEIVE/*");
		server.setHandler(servletHandler);

		server.start();
		System.out.println("Start Server");
		server.join();
	}

}
