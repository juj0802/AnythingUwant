package com.AUW.board.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.dto.JsonResult;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.FileService;
import com.AUW.board.service.UidService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikedController {

	private final UidService uidService;
	
	@GetMapping("/liked")
	public JsonResult<Object> process(Long boardNo, String uid, Model model,@AuthenticationPrincipal PrincipalDetail principalDetail){
		
	try {
		int totalLikes = 0;
		String field = "liked";
		
		uid = uid=="" ? uidService.getUid(boardNo, boardNo) : uid;

		totalLikes = uidService.likeProcess(uid, field);
		
		JsonResult<Object> result = new JsonResult<>();
		result.setSuccess(true);
		result.setData(totalLikes);

		return result;
		
	}catch(Exception e) {
		throw new RuntimeException("유효하지못한 접근입니다");
	}
	}
	
}
