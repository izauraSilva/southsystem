package com.southsystem.batch.data.vo;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class FileVO {

	private File fileSelected;

	public File getFileSelected() {
		return fileSelected;
	}

	public void setFileSelected(File fileSelected) {
		this.fileSelected = fileSelected;
	}

	@Override
	public String toString() {
		return "FileVO [fileSelected=" + fileSelected + "]";
	}	

}
