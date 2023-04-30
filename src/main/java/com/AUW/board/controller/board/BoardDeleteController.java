package com.AUW.board.controller.board;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.FileService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board_delete")
public class BoardDeleteController {
	private final BoardService boardService;
	private final FileService fileService;
	
	@GetMapping("delete/{no}")
	public String updateBoard(@PathVariable Long no,Model model,@AuthenticationPrincipal PrincipalDetail principalDetail) {
	
		User user = principalDetail.getUser();
		
		Board board = boardService.deleteBoard(no, user);
		String boardType = boardService.translateBoardTypeEnum(board.getBoardType());
		String url = boardType+"/main_view";
		//@GetMapping("/{boardType}/main_view")
		model.addAttribute("scripts", " alert('삭제되었습니다.'); location.replace('/board/"+url+"');");
		return "common/excution";
	}
	
	
}
