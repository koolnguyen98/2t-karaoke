package com.karaoke.management.api;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WriterLog {
	
	private static String file = "./logs/log-{{fileName}}.log";
	
	private static WriterLog writerLog;
	
	private static FileHandler fileHandler;
	
	private static Logger logger;
	
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
		logger = Logger.getLogger(logLocation);
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
	
	@Scheduled(cron="0 0 0 * * *")
	private void updateLogFile() {
		try {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String name = now.format(dateFormatter);
			String fileName = file.replace("{{fileName}}", name);
			fileHandler = new FileHandler(fileName, true);
			logger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
