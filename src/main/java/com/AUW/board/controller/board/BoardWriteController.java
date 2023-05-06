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
import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.BoardDto;
import com.AUW.board.dto.BoardType;
import com.AUW.board.dto.UserType;
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
		
		if(boardType == "NOTICE") {//공지사항은 관리자만 작성가능
			if(principalDetail.getUser().getUserType() != UserType.ADMIN) {
				throw new RuntimeException("유효하지않은 접근입니다.");
			}
			
		}
		if(boardType == "LIKED_BOARD") {//추천게시판은 작성이 불가능
			throw new RuntimeException("유효하지않은 접근입니다");
		}
		BoardDto boardDto = new BoardDto();
		boardDto.setBoardType(boardType);
		
		model.addAttribute("boardDto", boardDto);
		model.addAttribute("addScript", new String[] {"ckeditor/ckeditor","board/boardForm"});	
		return "board/board_write";
	}
	
	@PostMapping("/{boardType}/ps")
	public String boardWritePs(@Valid BoardDto boardDto,Errors errors,Model model,@PathVariable String boardType,@AuthenticationPrincipal PrincipalDetail principalDetail ) throws IllegalStateException, IOException {
	
		if(errors.hasErrors()) {
		
			model.addAttribute("boardDto", boardDto);
			model.addAttribute("addScript", new String[] {"ckeditor/ckeditor","board/boardForm"});	
			
			return "board/board_write";
		}
	
			Board board = boardService.insertBoard(boardDto, principalDetail.getUser());
			if(!boardDto.getFiles().isEmpty() && boardDto.getFiles().size()>0) {
				for(MultipartFile file : boardDto.getFiles()) {
					fileService.saveFiles(file, board);
				}
			}
		
		
	
	
		return "redirect:/board/"+boardDto.getBoardType()+"/main_view";
	}
	

	
	
	
	
}
