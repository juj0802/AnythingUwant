package com.AUW.board.controller.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AUW.board.controller.user.SignValidator;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.BoardType;
import com.AUW.board.dto.SearchRequest;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	
	@GetMapping("/{boardType}/main_view")
	public String mainView(@PathVariable String boardType,Model model,
	String searchKeyword,String order ,String radio,@PageableDefault(page= 0, size = 3, sort = "boardNo",direction = Sort.Direction.DESC)Pageable pageable ) {
		
//		if(searchRequest == null) {
//			SearchRequest request = new SearchRequest();
//			model.addAttribute("searchRequest", request);
//		}else {
//			model.addAttribute("searchRequest", searchRequest);
//			System.out.println("테스트123");
//			System.out.println(searchRequest);
//		}
		
		//@PageableDefault(page= 0, size = 3, sort = "boardNo",direction = Sort.Direction.DESC)Pageable pageable,
//		int nowPage = list.getPageable().getPageNumber() + 1; // pageable 0부터 시작하기때문에 +1 해준다
//		int startPage = Math.max(nowPage - 4, 1); // 둘중 큰거 반환함.
//		int endPage = Math.min(nowPage + 5, list.getTotalPages());

		
		Page<Board> list = boardService.findAllPosts(boardType, pageable);
		
		int nowPage = list.getPageable().getPageNumber() + 1; // pageable 0부터 시작하기때문에 +1 해준다
		int startPage = Math.max(nowPage - 4, 1); // 둘중 큰거 반환함.
		int endPage = Math.min(nowPage + 5, list.getTotalPages());
		
		
		//페이지관련 속성
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		//정렬 기준
		model.addAttribute("order",order);
		model.addAttribute("radio", radio);
		model.addAttribute("searchKeyword", searchKeyword);
		
		model.addAttribute("list", list);
		model.addAttribute("addScript", new String[] {"board/board_main"});	
		model.addAttribute("boardTypeKr", boardService.translateBoardTypeKr(boardType));
		model.addAttribute("boardType", boardType);

		return "board/board_main";
	}
	
	
	
}
