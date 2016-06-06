package com.lankr.server;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.thread.QueuedThreadPool;

public class JettyServer {
	
	private static Log logger = LogFactory.getLog(JettyServer.class);

	public static void main(String[] args) {
		Server server = new Server();
		QueuedThreadPool threads = new QueuedThreadPool(100);
		server.setThreadPool(threads);
		Connector connector = new SelectChannelConnector();
		// connector.setHost("127.0.0.1");
		connector.setPort(8899);
		server.setConnectors(new Connector[] { connector });
		server.setSendDateHeader(false);
		server.setSendServerVersion(false);
		server.setStopAtShutdown(true);
		WebAppContext webapp = new WebAppContext("./WebContent", "");
		server.addHandler(webapp);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}
}

