package com.AUW.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final ReplyService replyService;
	
	
	public Page<Board> findAllBoard(String boardType,Pageable pageable){
		
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard board = QBoard.board;
		BoardType type = this.translateBoardType(boardType);
		builder.and(board.boardType.eq(type));
		
		Page<Board> page = boardRepository.findAll(builder,pageable);
		
		
		
		
		return page;
	}
	public Board getOneBoard(Long boardNo) {
		
		Optional<Board> _entity = boardRepository.findById(boardNo);
		Board board = _entity.orElseThrow(()-> new RuntimeException("게시글을 찾을수없습니다."));
		
		
		return board;
	}

	
	public Board insertBoard(BoardDto dto,User user) {
		
		Board entity = this.dtoToEntity(dto, user);
		
		entity = boardRepository.save(entity);
		
		return entity;
	}
	
	
	@Transactional
	public void updateReplies(Board board, Reply reply) {
		
		List<Reply> list = board.getReplies();
		list.add(reply);
		
		board.setReplies(list);
		
		
	}
	
	public String translateBoardTypeKr(String boardType) {
		
		switch(boardType) {
		case "FREE_BOARD" : return "자유게시판";
		
		case "LIKED_BOARD" : return "추천게시판";
		
		case "QUERY_BOARD" : return "질문게시판";
		
		case "NOTICE" : return "공지사항";
		
		default : throw new RuntimeException("에러");
		
		}
		
		
		
	}
	
	public BoardType translateBoardType(String boardType) {
		
		switch(boardType) {
		case "FREE_BOARD" : return BoardType.FREEBOARD;
		
		case "LIKED_BOARD" : return BoardType.LIKEBOARD;
		
		case "QUERY_BOARD" : return BoardType.QUERYBOARD;
		
		case "NOTICE" : return BoardType.NOTICEBOARD;
		
		default : throw new RuntimeException("에러");
		
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	public BoardDto entityToDto(Board entity) {
		BoardDto dto = BoardDto.builder()
				.boardNo(entity.getBoardNo())
				.id(entity.getUser().getUserId())
				.nickNm(entity.getUser().getNickNm())
				.title(entity.getTitle())
				.content(entity.getContent())
				.gid(entity.getGid())
				.regDt(entity.getRegDt())
				.modDt(entity.getModDt())
				.boardHit(entity.getBoardHit())
				.replies(replyService.getReplyDtoList(entity.getReplies()))
				.totalLikes(entity.getTotalLikes())
				.build();
				
				return dto;
	}
	
	public Board dtoToEntity(BoardDto dto,User user) {
		
		Board entity = Board.builder()
				.user(user)
				.title(dto.getTitle())
				.content(dto.getContent())
				.boardType(this.translateBoardType(dto.getBoardType()))
				.gid(dto.getGid())
				
				.build();
		
		
		return entity;
	}
	
	
	
}
