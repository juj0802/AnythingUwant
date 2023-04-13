package com.AUW.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.AUW.board.domain.FileEntity;
import com.AUW.board.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

	private final FileService fileService;
	
	@PostMapping("/file/upload")
	public List<FileEntity> process(String gid, MultipartFile[] files){
		
//		List<FileEntity> list = fileService.process(files, gid);
		
		
		return null;
	}
	
	
	
}
