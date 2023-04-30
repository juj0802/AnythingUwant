package com.AUW.board.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.AUW.board.domain.User;
import com.AUW.board.domain.board.Board;
import com.AUW.board.domain.board.FileEntity;
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
public class BoardDto {

	private Long boardNo;
	private String id;
	private String nickNm;
	@NotBlank
	private String title;
	
	@NotBlank
	private String content;
	private String boardType;
	private Integer boardHit;
	private Integer totalLikes;
	
	private String gid;
	
	private LocalDateTime regDt;
	private LocalDateTime modDt;
	private List<ReplyDto> replies;
	private List<MultipartFile> files;
	private List<FileEntity> fileList;
}
