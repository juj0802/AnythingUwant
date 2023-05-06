package com.AUW.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.domain.board.QBoard;
import com.AUW.board.domain.board.Reply;
import com.AUW.board.dto.BoardDto;
import com.AUW.board.dto.BoardType;
import com.AUW.board.repository.BoardRepository;
import com.AUW.board.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;

import jakarta.transaction.Transactional;
import javassist.expr.Instanceof;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyService replyService;

	public Page<Board> findAllBoard(String boardType, Pageable pageable) {

		BooleanBuilder builder = new BooleanBuilder();

		QBoard board = QBoard.board;
		BoardType type = this.translateBoardType(boardType);
		if(type == BoardType.LIKEBOARD) { //추천게시판은 추천수 10개이상
			builder.and(board.totalLikes.goe(10));
		}else {
			builder.and(board.boardType.eq(type));
		}
		
		builder.and(board.deleteYn.eq("N"));

	
		Page<Board> page = boardRepository.findAll(builder, pageable);
		
		return page;
	}
	public Page<Board> findAllBoardBy(String searchKeyword,String order ,String radio,Pageable pageable,String boardType,String category){
		Sort sort = null;
	
		/*
		 * 이상하게 order == "asc"는 false 가 나온다
		 * 그래서 길이를 통해 조건문을 건다 
		 * **/
		if(order.length()==3) {
	
		 sort = Sort.by(
				Sort.Order.asc(radio),
				Sort.Order.desc("boardNo")
				);
		}else {
			
		
			 sort = Sort.by(
						Sort.Order.desc(radio),
						Sort.Order.desc("boardNo")
						);
		}
		BooleanBuilder builder = new BooleanBuilder();

		QBoard board = QBoard.board;
		BoardType type = this.translateBoardType(boardType);
		if(type == BoardType.LIKEBOARD) {
			builder.and(board.totalLikes.goe(10));
		}else {
			builder.and(board.boardType.eq(type));
		}
		
		builder.and(board.deleteYn.eq("N"));
		
		if(!searchKeyword.isBlank()) {
			if(category.length()==6) {
				builder.and(board.user.nickNm.contains(searchKeyword));
		
			}else {
				builder.and(board.title.contains(searchKeyword));	
			}
			
		}
	
	pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sort);
	Page<Board> page = boardRepository.findAll(builder, pageable);

		return page;
	}
	
	public Page<Board> findAllForAdmin(Pageable pageable){

		Page<Board> page = boardRepository.findAll(pageable);
		
		return page;
	}
	public Page<Board> findAllForAdmin(Pageable pageable,String searchKeyword){
		BooleanBuilder builder = new BooleanBuilder();

		QBoard board = QBoard.board;
		builder.and(board.title.contains(searchKeyword));
		Page<Board> page = boardRepository.findAll(builder,pageable);
		
		return page;
	}
	
	public Board getOneBoard(Long boardNo) {

		Optional<Board> _entity = boardRepository.findById(boardNo);
		Board board = _entity.orElseThrow(() -> new RuntimeException("게시글을 찾을수없습니다."));
		if (board.getDeleteYn() == "Y") {
			
			throw new RuntimeException("삭제된 게시글입니다.");
		}

		return board;
	}
	public Board boardForAdmin(Long boardNo) {
		Optional<Board> _entity = boardRepository.findById(boardNo);
		Board board = _entity.orElseThrow(() -> new RuntimeException("게시글을 찾을수없습니다."));
		

		return board;
	}
	
	//추천게시판용 page객체 
	//추천 10개이상 받으면 추천게시판에 표시된다
	
	public Page<Board> findAllbyLiked(Pageable pageable){
		BooleanBuilder builder = new BooleanBuilder();

		QBoard board = QBoard.board;
		
		builder.and(board.totalLikes.goe(10));
		
		Page<Board> list = boardRepository.findAll(builder,pageable);
		
		return list;
		
	}

	public Board insertBoard(BoardDto dto, User user) {

		Board entity = this.dtoToEntity(dto, user);

		entity = boardRepository.save(entity);

		return entity;
	}
	@Transactional
	public Board updateBoard(BoardDto dto) {
	
		Board board = this.getOneBoard(dto.getBoardNo());
		
		board.setTitle(dto.getTitle());
		board.setContent(dto.getContent());
		
		return board;
	}

	@Transactional
	public Board deleteBoard(Long no, User user) {// 게시물 삭제 메서드, 게시물db까지 지워버리면 추후 문제될수있는 상황이있을수있으므로 deletedYN 속성만 Y로
													// 바꿔주고 접근을 막게한다

		Board board = this.getOneBoard(no);
	
	
		  if(board.getUser().getUserNo()!= user.getUserNo()) {
			  throw new RuntimeException("유효하지 못한 접근입니다."); 
			  }
	

		board.setDeleteYn("Y");

		return board;
	}
	
	
	public List<Board> findByUserNo(Long no){
		BooleanBuilder builder = new BooleanBuilder();

		QBoard board = QBoard.board;
		builder.and(board.user.userNo.eq(no));
		
		
		List<Board> list = (List<Board>) boardRepository.findAll(builder);
		
		return list;
	}
	
	//관리자는 db에서 게시물 삭제가능
	public void deleteBoardForAdmin(Long no) {
		
		Board board = this.boardForAdmin(no);
		boardRepository.delete(board);
	}
	
	@Transactional
	public void updateReplies(Board board, Reply reply) {

		List<Reply> list = board.getReplies();
		list.add(reply);

		board.setReplies(list);

	}

	public String translateBoardTypeEnum(BoardType boardType) {
		switch (boardType) {
		case FREEBOARD:
			return "FREE_BOARD";
		case LIKEBOARD:
			return "LIKED_BOARD";
		case QUERYBOARD:
			return "QUERY_BOARD";
		case NOTICEBOARD:
			return "NOTICE";

		default:
			throw new RuntimeException("에러");

		}
	}

	public String translateBoardTypeKr(String boardType) {

		switch (boardType) {
		case "FREE_BOARD":
			return "자유게시판";

		case "LIKED_BOARD":
			return "추천게시판";

		case "QUERY_BOARD":
			return "질문게시판";

		case "NOTICE":
			return "공지사항";

		default:
			throw new RuntimeException("에러");

		}

	}

	public BoardType translateBoardType(String boardType) {

		switch (boardType) {
		case "FREE_BOARD":
			return BoardType.FREEBOARD;

		case "LIKED_BOARD":
			return BoardType.LIKEBOARD;

		case "QUERY_BOARD":
			return BoardType.QUERYBOARD;

		case "NOTICE":
			return BoardType.NOTICEBOARD;

		default:
			throw new RuntimeException("에러");

		}

	}

	public BoardDto entityToDto(Board entity) {
		BoardDto dto = BoardDto.builder().boardNo(entity.getBoardNo()).id(entity.getUser().getUserId())
				.nickNm(entity.getUser().getNickNm()).title(entity.getTitle()).content(entity.getContent())
				.boardType(this.translateBoardTypeEnum(entity.getBoardType()))
				.regDt(entity.getRegDt()).modDt(entity.getModDt()).boardHit(entity.getBoardHit())
				.fileList(entity.getFiles())
				.totalLikes(entity.getTotalLikes()).build();

		return dto;
	}

	public Board dtoToEntity(BoardDto dto, User user) {

		Board entity = Board.builder().user(user).title(dto.getTitle()).content(dto.getContent())
				.boardType(this.translateBoardType(dto.getBoardType()))

				.build();

		return entity;
	}

}
