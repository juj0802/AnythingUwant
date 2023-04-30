package com.AUW.board.controller.board;

import java.io.IOException;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.BoardDto;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.FileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board_update")
public class BoardUpdateController {
	private final BoardService boardService;
	private final FileService fileService;
	//게시물 수정 매핑
	@GetMapping("update/{no}")
	public String updateBoard(@PathVariable Long no,Model model,@AuthenticationPrincipal PrincipalDetail principalDetail) {
	
		User user = principalDetail.getUser();
		Board board = boardService.getOneBoard(no);
		
		if(user.getUserNo() != board.getUser().getUserNo()) {
			throw new RuntimeException("유효하지못한 접근입니다.");
		}
		BoardDto dto = boardService.entityToDto(board);
		
		model.addAttribute("boardDto", dto);
		model.addAttribute("addScript", new String[] {"ckeditor/ckeditor","board/boardForm"});	
		
		return "board/board_update";
	}
	
	@PostMapping("update/ps")
	public String boardUpdatePs(@Valid BoardDto boardDto,Errors errors,Model model ) throws IllegalStateException, IOException {
		
		if(errors.hasErrors()) {
		
			model.addAttribute("boardDto", boardDto);
			model.addAttribute("addScript", new String[] {"ckeditor/ckeditor","board/boardForm"});	
			
			return "board/board_update";
		}
	
			Board board = boardService.updateBoard(boardDto);
			if(!boardDto.getFiles().isEmpty() && boardDto.getFiles().size()>0) {
				for(MultipartFile file : boardDto.getFiles()) {
					fileService.saveFiles(file, board);
				}
			}
		String boardType = boardService.translateBoardTypeEnum(board.getBoardType());
		String url = "board_view?boardNo="+board.getBoardNo();
	
	
		model.addAttribute("scripts", " alert('수정되었습니다.'); location.replace('/board/"+url+"');");
		return "common/excution";
	}
	
	
}
