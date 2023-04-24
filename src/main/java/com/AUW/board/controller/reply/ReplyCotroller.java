package com.AUW.board.controller.reply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.domain.board.Reply;
import com.AUW.board.dto.ReplyDto;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.ReplyService;
import com.AUW.board.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyCotroller {

	private final ReplyService replyService;
	private final BoardService boardService;
	private final UserService userService;
	
	
	@PostMapping("/submit")
	public String submitReply(ReplyDto replyDto, Model model) {
	
		User user = userService.findOneByUserId(replyDto.getUserId());
		
		
		Reply reply = replyService.insertReply(replyDto, replyDto.getBoardNo(), user); //replyEntity 생성
		
	
		Board board = boardService.getOneBoard(replyDto.getBoardNo());
		model.addAttribute("user", user.getUserId());
		model.addAttribute("replies", board.getReplies());
	
		
		
		 
		return "board/board_view::#replies_area"; // 비동기식으로 화면수정
	}
	
	@PostMapping("/deleteReply")
	public String deleteReply(ReplyDto replyDto,Model model) {
		
		replyService.deleteReply(replyDto.getReplyNo());
		Board board = boardService.getOneBoard(replyDto.getBoardNo());
	
		User user = userService.findOneByUserId(replyDto.getUserId());
		model.addAttribute("user", user.getUserId());
		model.addAttribute("replies", board.getReplies());
		
		return "board/board_view::#replies_area";
	}
	
	@PostMapping("/updateReply")
	public String updateReply(ReplyDto replyDto,Model model) {
		
		replyService.updateReply(replyDto, replyDto.getReplyNo());
		
		Board board = boardService.getOneBoard(replyDto.getBoardNo());
		
		User user = userService.findOneByUserId(replyDto.getUserId());
		model.addAttribute("user", user.getUserId());
		model.addAttribute("replies", board.getReplies());
		
		return "board/board_view::#replies_area";
	}



}
