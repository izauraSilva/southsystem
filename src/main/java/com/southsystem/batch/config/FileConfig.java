package com.southsystem.batch.config;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.southsystem.batch.exception.FileNotFoundException;

@Configuration
@PropertySource("classpath:application-dev.properties")
public class FileConfig {

	private static final Logger logger = LoggerFactory.getLogger("badRecordLogger");

	@Autowired
	public FileConfig(@Value("${southSystem.input}") final String filePath) throws FileNotFoundException, IOException {		
		this.existsFile(filePath);		
	}

	private void existsFile(String filePath) throws FileNotFoundException, IOException {

		File dir = new File(filePath);

		if (!dir.exists()) {
			logger.error("Path not found. filePath[" + filePath + "]");
			throw new ValidationException("Path not found. filePath[" + filePath + "]");
		}
		
		File[] files = dir.listFiles();
		if (files==null) {
			logger.info("File not found. filePath[" + filePath + "]");
			 throw new FileNotFoundException("File not found. filePath[" + filePath + "]");
		}	

	}
	
}
