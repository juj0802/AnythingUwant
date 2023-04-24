package com.AUW.board.domain.board;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import com.AUW.board.domain.BaseEntity;
import com.AUW.board.domain.FileEntity;
import com.AUW.board.domain.User;
import com.AUW.board.dto.BoardType;
import com.AUW.board.dto.UserType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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
@DynamicInsert
public class Board extends BaseEntity{//게시판 엔티티

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo; //PK
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="userNo")
	private User user; // 연관관계 매핑
	
	@Column(nullable = false,length = 100)
	private String title; //제목
	
	@Lob
	@Column(nullable=false)
	private String content; //내용
	
	/*
	 * @Column(nullable=false, updatable = false) private String gid;//파일 그룹id
	 */	
//	@Column(columnDefinition = "int default '0'")
	//,insertable = false, updatable = false
	@ColumnDefault("0")
	private Integer boardHit = 0;//조회수
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private BoardType boardType;
	
	
	@OrderBy("regDt asc")
	@OneToMany(mappedBy = "board", orphanRemoval = true)
	private List<Reply> replies = new ArrayList<>();//댓글 연관관계
	
	@OrderBy("regDt desc")
	@OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
	private List<FileEntity> files = new ArrayList<>();
	
	//@Column(columnDefinition = "int default '0'")
	@ColumnDefault("0")
	private Integer totalLikes = 0;//총 추천수
	
	
	
	
	
	
}
