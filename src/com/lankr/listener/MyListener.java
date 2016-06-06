package com.lankr.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lankr.consumer.Consumer;
//import com.lankr.rebuild.Rebuild;

public class MyListener implements ServletContextListener{
	
	public MyListener(){
		super();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		//System.out.println("------------------97");
        //Rebuild rebuild = new Rebuild() ; 
		Consumer con = new Consumer() ;
		//Thread thread1  = new Thread(rebuild, "myThread1") ;
		Thread thread2 = new Thread(con, "myThread2") ;
		//thread1.start();
		thread2.start();
	}

}
