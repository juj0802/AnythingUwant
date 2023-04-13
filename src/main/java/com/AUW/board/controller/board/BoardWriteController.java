package com.AUW.board.controller.board;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.BoardDto;
import com.AUW.board.dto.BoardType;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
@RequestMapping("/board_write")
public class BoardWriteController {
	
	private final BoardService boardService;
	private final FileService fileService;
	@GetMapping("/{boardType}")
	public String boardWrite(@PathVariable String boardType,@AuthenticationPrincipal PrincipalDetail principalDetail,Model model) {
		
		BoardDto boardDto = new BoardDto();
		boardDto.setBoardType(boardType);
		boardDto.setGid(""+System.currentTimeMillis());
		model.addAttribute("boardDto", boardDto);
		model.addAttribute("addScript", new String[] {"ckeditor/ckeditor","board/boardForm"});	
		return "board/board_write";
	}
	
	@PostMapping("/{boardType}/ps")
	public String boardWritePs(@Valid BoardDto boardDto,Errors errors,Model model,@PathVariable String boardType,@AuthenticationPrincipal PrincipalDetail principalDetail ) throws IllegalStateException, IOException {
		System.out.println("dto====="+boardDto);
		System.out.println("파일은 ==="+boardDto.getFiles());
		if(errors.hasErrors()) {
		
			model.addAttribute("boardDto", boardDto);
			model.addAttribute("addScript", new String[] {"ckeditor/ckeditor","board/boardForm"});	
			
			return "board/board_write";
		}
		
	
		Board board = boardService.insertBoard(boardDto, principalDetail.getUser());
	fileService.saveFiles(boardDto.getFiles(),board);
		return "redirect:/board/"+boardDto.getBoardType()+"/main_view";
	}
	
	
	
	
}
