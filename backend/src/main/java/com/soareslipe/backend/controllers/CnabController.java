package com.soareslipe.backend.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soareslipe.backend.services.CnabService;

@RestController
@RequestMapping("/cnab")
public class CnabController {
	
	@Autowired
	private CnabService cnabService;
	
	@PostMapping
	public String upload(@RequestParam("file") MultipartFile multipartFile) throws IllegalStateException, IOException {
		cnabService.upload(multipartFile);
		return "Processo iniciado";
	}

}
