package com.AUW.board.controller.board;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

import com.AUW.board.config.auth.PrincipalDetail;
import com.AUW.board.controller.user.SignValidator;
import com.AUW.board.domain.FileEntity;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.BoardType;
import com.AUW.board.dto.ReplyDto;
import com.AUW.board.dto.SearchRequest;
import com.AUW.board.service.BoardService;
import com.AUW.board.service.FileService;
import com.AUW.board.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	private final FileService fileService;
	@GetMapping("/{boardType}/main_view")
	public String mainView(@PathVariable String boardType,Model model,
@RequestParam(required = false)	String searchKeyword,String order ,String radio,@PageableDefault(page= 0, size = 10, sort = "boardNo",direction = Sort.Direction.DESC)Pageable pageable ) {
		
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

		
		Page<Board> list = boardService.findAllBoard(boardType, pageable);
		
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
	
	//@{/board/board_view
	@GetMapping("/board_view")
	public String boardView(@RequestParam(required = true) Long boardNo,@AuthenticationPrincipal PrincipalDetail principalDetail,Model model) {
		ReplyDto replyDto = new ReplyDto();
		if(principalDetail != null) { 
			model.addAttribute("user", principalDetail.getUsername());
			replyDto.setUserId(principalDetail.getUsername());
			replyDto.setNickNm(principalDetail.getUser().getNickNm());
		}
		
		System.out.println(boardNo);
		Board board = boardService.getOneBoard(boardNo);
		
	//	List<FileEntity> fileList = fileService.getFilesByBoard(board);
		model.addAttribute("addScript", new String[] {"board/board_main"});	
		model.addAttribute("fileList", board.getFiles());
		model.addAttribute("board", board);
		model.addAttribute("replies", board.getReplies()); //비동기식 화면수정을위해 댓글은 따로 모델에 속성추가
		model.addAttribute("replyDto", replyDto);
		return "board/board_view";
	}
	
	@GetMapping("/attach")
	public ResponseEntity<Resource> downloadAttach(@RequestParam(required = true)Long fileNo ) throws MalformedURLException, FileNotFoundException{
		
		FileEntity file = fileService.getFileByNo(fileNo); //파일 넘버로 db에서 엔티티찾음
		UrlResource resource = new UrlResource("file:"+file.getPath()); //프로토콜을 지정하고 리소스 위치 지정
		
		String encodedFileName = UriUtils.encode(file.getFileName(), StandardCharsets.UTF_8);;// 파일명이 한글일수도있으니 이름을 utf-8인코딩처리 안하면 에러발생
		
		String contentDisposition = "attachment; filename=\"" + encodedFileName + "\""; //attachment를 붙여줌으로써 다운로드용임을 알림
		
		 return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,contentDisposition).body(resource);
	}
	
	
	
}
