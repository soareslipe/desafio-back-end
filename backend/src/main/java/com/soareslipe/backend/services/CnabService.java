package com.soareslipe.backend.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CnabService {
	
	private final Path fileStorageLocation;
	
	public CnabService(@Value("${upload-dir}") String uploadDir) {
		this.fileStorageLocation = Paths.get(uploadDir);
	}
	
	public void upload(MultipartFile multipartFile) throws IllegalStateException, IOException {
		String originalName = multipartFile.getOriginalFilename();
		Path tarrgetLocation = fileStorageLocation.resolve(originalName);
		multipartFile.transferTo(tarrgetLocation);
	}

}
