package com.AUW.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.AUW.board.domain.QUidEntity;
import com.AUW.board.domain.UidEntity;
import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.UidDto;
import com.AUW.board.repository.BoardRepository;
import com.AUW.board.repository.ReplyRepository;
import com.AUW.board.repository.UidRepository;
import com.querydsl.core.BooleanBuilder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UidService {

	private final UidRepository uidRepository;
	private final BoardRepository boardRepository;
	
	
	public void hitProcess(String uid, String field) {
		
		//중복확인후 없으면 db등록
		UidEntity uidEntity = uidRepository.findByFieldAndUid(field, uid).orElse(null);
		
		if(uidEntity == null) {
			uidEntity = this.dtoToEntity(uid, field);
			uidEntity = uidRepository.save(uidEntity);
			
			Long boardNo = Long.parseLong(uid.split("_")[0]);
			
			boardRepository.updateBoardHit(boardNo);
			
			
		}
		
	}
	
	
	@Transactional
	public int likeProcess(String uid, String field) {
		//중복확인후 없으면 db등록, 있으면 삭제
		BooleanBuilder builder = new BooleanBuilder();
		QUidEntity quid = QUidEntity.uidEntity;

		UidEntity uidEntity = uidRepository.findByFieldAndUid(field, uid).orElse(null);
		
		try {
			if(uidEntity==null) {
				
				uidEntity = this.dtoToEntity(uid, field);
				uidEntity = uidRepository.save(uidEntity);
			}else {
				
				uidRepository.delete(uidEntity);
			}
			
		}catch(RuntimeException e) {
			throw new RuntimeException("에러 발생!");
		}
		
		Long boardNo = Long.parseLong(uid.split("_")[0]);
		String test = ""+boardNo+"_";
		builder.and(quid.field.eq(field));
		builder.and(quid.uid.contains(test));
		
		List<UidEntity> list = (List<UidEntity>) uidRepository.findAll(builder);
		int totalLikes = list.size();
	
		boardRepository.updateTotalLikes(totalLikes, boardNo);
		return totalLikes;
				
	}
	
	
	
	public UidEntity getOneUid(String field, Long boardNo,Long userNo) {
		
		UidEntity entity = uidRepository.findByNo(field, boardNo, userNo).orElse(null);
		
		return entity;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getUid(Long boardNo, Long userNo) {
		//컨트롤러 메서드 파라미터로 선언하지않고 Request 서블릿 객체 가져오기
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
		
		//요청 헤더의 user-agent 와 브라우저 ip를 합쳐 해쉬코드 생성
		String userAgent = httpServletRequest.getHeader("User-Agent");
		String userIP = httpServletRequest.getRemoteAddr();
		int hash = (userAgent+userIP).hashCode();
		String uid = "";
		
		if(userNo != null) {//로그인 상태일 경우 userNo포함 아니면 0
			uid = boardNo + "_" + hash + "_" + userNo;
		}else {
			uid = boardNo + "_" + hash + "_" + 0;
		}
		
		return uid;
	}
	
	public UidDto entityToDto(UidEntity entity) {
		
		UidDto dto = UidDto.builder()
					.uid(entity.getUid())
					.field(entity.getField())
					.build();
		
		return dto;
	}
	public UidEntity dtoToEntity(String uid, String field) {
		
		UidEntity uidEntity = UidEntity.builder()
				.uid(uid)
				.field(field)
				.build();
		
		return uidEntity;
	}
	
	
}
