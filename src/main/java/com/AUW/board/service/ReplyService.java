package com.AUW.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.domain.board.Reply;
import com.AUW.board.dto.ReplyDto;
import com.AUW.board.repository.BoardRepository;
import com.AUW.board.repository.ReplyRepository;
import com.AUW.board.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final BoardRepository boardRepository;
	
	
	public Reply insertReply(ReplyDto dto,Long boardNo, User user) {
		Reply parent = null;
		Optional<Board> _entity = boardRepository.findById(boardNo);
		Board board = _entity.orElseThrow(()-> new RuntimeException("게시글을 찾을수없습니다."));
		
		if(dto.getParentNo()!=null) {
			parent = this.findOneByReplyNo(dto.getParentNo());
			
		}
		
		Reply entity = Reply.builder()
				.board(board)
				.user(user)
				.parent(parent)
				.content(dto.getContent())
				.build();
		entity = replyRepository.save(entity);
		if(entity.getParent()!=null) {
	//		this.updateChildren(parent, entity);
		}
		return entity;
	}
	
	@Transactional
	public void updateChildren(Reply parent,Reply child) {
		
		
		
		 parent.getChildren().add(child);
		
	
		
		
	}
	
	

	
	
	
	
	
	
	public Reply findOneByReplyNo(Long no) {
		
		Optional<Reply> _entity = replyRepository.findById(no);
		Reply entity = _entity.orElseThrow(()->new RuntimeException("댓글을 찾을수없습니다."));
		
		return entity;
	}
	
	@Transactional
	public void deleteReply(Long replyNo) {
		
		try {
		Reply entity = this.findOneByReplyNo(replyNo);
		
		entity.setDeleteYn("Y");
		if(entity.getDeletedContent()==null || !entity.getDeletedContent().isEmpty()) {
			entity.setDeletedContent(entity.getContent());
			entity.setContent("삭제된 댓글입니다.");
		}
		
	
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("댓글삭제에러");
		}
	}
	
	
	@Transactional
	public boolean updateReply(ReplyDto dto,Long no) {
		try {
		Reply entity = this.findOneByReplyNo(no);
		entity.setContent(dto.getContent());
		
		return true;
		
		}catch (Exception e) {
			return false;
		}
	}

	
/**toDto, toEntity s*/	
	public ReplyDto entityToDto(Reply entity) {
		Long parentNo = null;
		List<ReplyDto> childrens = null;
		if(entity.getParent() != null) {
			parentNo = entity.getParent().getReplyNo();
		}
		
		if(entity.getChildren() != null && entity.getChildren().size()>0) {
			childrens = new ArrayList<>();
		
			for(Reply _reply : entity.getChildren()) {
				
				ReplyDto _children = ReplyDto.builder()
						.replyNo(_reply.getReplyNo())
						.boardNo(_reply.getBoard().getBoardNo())
						.userId(_reply.getUser().getUserId())
						.nickNm(_reply.getUser().getNickNm())
						.content(_reply.getContent())
						.regDt(_reply.getRegDt())
						.modDt(_reply.getModDt())
						.deleteYn(_reply.getDeleteYn())
						.build();
				
				childrens.add(_children);
				
				
			}
		}
		
		ReplyDto dto = ReplyDto.builder()
				.replyNo(entity.getReplyNo())
				.parentNo(parentNo)
				.boardNo(entity.getBoard().getBoardNo())
				.userId(entity.getUser().getUserId())
				.nickNm(entity.getUser().getNickNm())
				.content(entity.getContent())
				.regDt(entity.getRegDt())
				.modDt(entity.getModDt())
				.deleteYn(entity.getDeleteYn())
				.children(childrens)
				.build();
		
		
		
		return dto;
		}	

	
	public Reply dtoToEntity(ReplyDto dto, User user,Board board) {
		
		Long parentNo = null;
		Reply parent =null;
		if(dto.getParentNo() != null) {
			parentNo = dto.getParentNo();
			parent = this.findOneByReplyNo(parentNo);
		}
		
		
		Reply entity = Reply.builder()
				.board(board)
				.user(user)
				.deleteYn(dto.getDeleteYn())
				.content(dto.getContent())
				.parent(parent)
	
				.build();
		
		
		return entity;
		
	}
	/**toDto, toEntity e*/		
	
	

	

	
	public List<ReplyDto> getReplyDtoList(List<Reply> replies){
		
		List<ReplyDto> list = null;
		
		
		if(replies != null && replies.size()>0) {
			list = new ArrayList<>();
		for(Reply entity : replies) {
			
			ReplyDto dto= this.entityToDto(entity);
			
			list.add(dto);
		}
		}
		
		return list;
	}

	
	
	
}
