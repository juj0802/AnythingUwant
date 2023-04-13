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
import com.AUW.board.repository.ReplyRepository;
import com.AUW.board.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {

	private final ReplyRepository replyRepository;
	
	public Reply findOneByReplyNo(Long no) {
		Optional<Reply> _entity = replyRepository.findById(no);
		Reply entity = _entity.orElse(null);
		
		return entity;
	}
	
	
	@Transactional
	public boolean updateReply(ReplyDto dto,Long no) {
		try {
		Reply entity = this.findOneByReplyNo(no);
		entity.setCotent(dto.getContent());
		
		return true;
		
		}catch (Exception e) {
			return false;
		}
	}
	@Transactional
	public boolean updateParent(Reply parent,Long no) {
		
		try {
			Reply entity = this.findOneByReplyNo(no);
			entity.setParent(parent);
			
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
						.id(_reply.getUser().getUserId())
						.nickNm(_reply.getUser().getNickNm())
						.content(_reply.getCotent())
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
				.id(entity.getUser().getUserId())
				.nickNm(entity.getUser().getNickNm())
				.content(entity.getCotent())
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
				.cotent(dto.getContent())
				.parent(parent)
	
				.build();
		
		
		return entity;
		
	}
	/**toDto, toEntity e*/		
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public List<ReplyDto> getChildrenDtoList(Reply entity){
//
//		List<ReplyDto> list = new ArrayList<>();
//		if(entity.getChildren() != null && entity.getChildren().size()>0) {
//			
//			ReplyDto dto = this.entityToDto(entity);
//			list.add(dto);
//			
//			
//		}
//		
//		return list;
//	}
	
	
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
