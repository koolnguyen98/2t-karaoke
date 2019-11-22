package com.karaoke.management.api;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriterLog {
	
	private static String file = "./logs/log-{{fileName}}.log";
	
	private static WriterLog writerLog;
	
	private static FileHandler fileHandler;
	
	private WriterLog() {
		super();
	}
	
	public static void getWriterLog() {
		if(writerLog == null) {
			writerLog = new WriterLog();
		}
	}

	public static Logger getLogger(String logLocation) {
		
		getWriterLog();
		
		File logDir = new File("./logs/");
		if (!(logDir.exists())) {
			logDir.mkdir();
		}
		
		Logger logger = Logger.getLogger("test");
		
	    try {
	    	
		    if(fileHandler == null) {
		    	LocalDateTime now = LocalDateTime.now();
		    	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				String name = now.format(dateFormatter);
		    	String fileName = file.replace("{{fileName}}", name);
		    	fileHandler = new FileHandler(fileName, true);
		        logger.addHandler(fileHandler);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fileHandler.setFormatter(formatter);
	    	}

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    
	    return logger;
		
	}
	
	
	
}
