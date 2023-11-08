package com.danaga.upload;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStore {
	private final String fileDir = System.getProperty("user.dir") + "/src/main/resources/static/images/updated/";
	
	public String getFullPath(String fileName) {
		return fileDir + fileName;
	}
	
	public List<String> storeFile(MultipartFile multipartFile) throws IOException {
		if(multipartFile.isEmpty()) {
			return null;
		}
		
		String originalFileName = multipartFile.getOriginalFilename();
		String storeFileName = UUID.randomUUID() + "." + extractExt(originalFileName);
		
		multipartFile.transferTo(new File(getFullPath(storeFileName)));
		
		List<String> fileName = new ArrayList<>();
		fileName.add(originalFileName);
		fileName.add(storeFileName);
		
		
        return fileName;
	}
	
	public List<String> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
		List<String> fileNames = new ArrayList<>();
		String[] counters = {"A","B","C"};
		int i = 0;
		for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
            	String originalFileName = multipartFile.getOriginalFilename();
        		String storeFileName = UUID.randomUUID() + counters[i++] + "." + extractExt(originalFileName);
        		
        		multipartFile.transferTo(new File(getFullPath(storeFileName)));
        		
        		fileNames.add(originalFileName);
        		fileNames.add(storeFileName);
            }
        }
		
		return fileNames;
	}
	
	
	private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
	
}
