package com.AUW.board.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.domain.board.Reply;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplyDto {
	
	private Long replyNo;//식별자
	private Long parentNo;//부모 댓글 번호
	private Long boardNo;//게시글번호
	
	private String userId;//유저아이디
	private String nickNm;//유저닉네임
	@NotBlank
	private String content;//댓글내용
	private LocalDateTime regDt;//작성일
	private LocalDateTime modDt;//수정일
	private List<ReplyDto> children;//자식댓글
	private String deleteYn;//삭제여부
	
	
}
