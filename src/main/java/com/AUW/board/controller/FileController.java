package com.AUW.board.controller;

import java.io.FileNotFoundException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AUW.board.domain.board.Board;
import com.AUW.board.domain.board.FileEntity;
import com.AUW.board.dto.BoardDto;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.FileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FileController {
	private final BoardService boardService;
	private final FileService fileService;
	
	
	@GetMapping("deleteFile")
	public String deleteFile(@RequestParam(required = true) Long fileNo,Model model) throws FileNotFoundException {
		

		FileEntity entity = fileService.getFileByNo(fileNo);
		Board board = entity.getBoard();
		fileService.deleteFileEntity(entity);
		BoardDto dto = boardService.entityToDto(board);
		model.addAttribute("boardDto", dto);
		
		return "board/board_update::#fileListArea";
	}
	
	
}
