package com.AUW.board.domain.board;

import java.util.ArrayList;
import java.util.List;

import com.AUW.board.domain.BaseEntity;
import com.AUW.board.domain.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reply extends BaseEntity{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long replyNo; //PK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="boardNo")
	private Board board;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userNo")
	private User user; //댓글 작성자
	@Column(nullable=false, columnDefinition="char(1) default 'N'", insertable=false)
	private String deleteYn; 	//삭제여부
	@Lob
	@Column(nullable=false)
	private String content; // 댓글내용
	@Lob
	private String deletedContent;  //삭제된 댓글의 원본
	
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="parent_No")
	private Reply parent; //부모댓글 셀프조인
	
	@OneToMany(mappedBy = "parent",orphanRemoval = true)
	@Builder.Default
	private List<Reply> children = new ArrayList<>(); //자식 댓글
	
	
	
	
	
}












