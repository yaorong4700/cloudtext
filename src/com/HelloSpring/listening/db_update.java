package com.HelloSpring.listening;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class db_update implements ServletContextListener {

	 private mythread myThread;  
	  
	    public void contextDestroyed(ServletContextEvent e) {  
	        if (myThread != null && myThread.isInterrupted()) {  
	            myThread.interrupt();  
	        }  
	    }  
	  
	    public void contextInitialized(ServletContextEvent e) {  
	        String str = null;  
	        if (str == null && myThread == null) {  
	            myThread = new mythread();  
	            myThread.start(); // servlet 上下文初始化时启动 socket  
	        }  
	    }  

}
