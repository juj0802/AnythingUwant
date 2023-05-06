package com.AUW.board.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.controller.user.UpdateValidator;
import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.LongList;
import com.AUW.board.dto.UserDto;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final UserService userService;
	private final BoardService boardService;
	@GetMapping("/main")
	public String adminMain(@AuthenticationPrincipal PrincipalDetail principal) {
		
		return "admin/main";
	}
	
	@GetMapping("/user")
	public String userMangement(@PageableDefault(page= 0, size = 10,sort = "userNo", direction = Sort.Direction.DESC)Pageable pageable ,String searchKeyword,Model model) {
		
		Page<User> list = userService.findAllUsers(pageable);
		int nowPage = list.getPageable().getPageNumber() + 1; // pageable 0부터 시작하기때문에 +1 해준다
		int startPage = Math.max(nowPage - 4, 1); // 둘중 큰거 반환함.
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		
		//페이지관련 속성
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);	
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("list", list);
		model.addAttribute("addScript", new String[] {"admin/admin"});	
		return "admin/user";
	}
	
	@PostMapping("/user")
	public String deleteUsers(LongList users,Model model) {
		
		System.out.println("유저 확인차");
		System.out.println(users);
		if(users.getNums().size()>0 && !users.getNums().isEmpty()) {
			
			for(Long userNo : users.getNums()) {
				userService.deleteUser(userNo);
			}
		}
	
		model.addAttribute("scripts", " alert('삭제되었습니다.'); location.replace('/admin/user');");
		return "common/excution";
	}
	
	@GetMapping("/boards")
	public String boardManagement(@PageableDefault(page= 0, size = 10,sort = "boardNo", direction = Sort.Direction.DESC)Pageable pageable ,String searchKeyword,Model model) {
		Page<Board> list = null;
		if(searchKeyword != null&& !searchKeyword.isBlank()) {
			list = boardService.findAllForAdmin(pageable, searchKeyword);
		}
		else {
			list = boardService.findAllForAdmin(pageable);
		} 
		
		int nowPage = list.getPageable().getPageNumber() + 1; // pageable 0부터 시작하기때문에 +1 해준다
		int startPage = Math.max(nowPage - 4, 1); // 둘중 큰거 반환함.
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);	
		model.addAttribute("endPage", endPage);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("list", list);
		model.addAttribute("addScript", new String[] {"admin/admin"});	
		
		return "admin/boards";
	}
	@PostMapping("/boards")
	public String deleteBoards(LongList boards,Model model) {
		
		System.out.println("유저 확인차");
		System.out.println(boards);
		if(boards.getNums().size()>0 && !boards.getNums().isEmpty()) {
			
			for(Long boardNo : boards.getNums()) {
				boardService.deleteBoardForAdmin(boardNo);
			}
		}
	
		model.addAttribute("scripts", " alert('삭제되었습니다.'); location.replace('/admin/boards');");
		return "common/excution";
	}
}
