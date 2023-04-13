package com.AUW.board.domain;

import com.AUW.board.domain.board.Board;
import com.AUW.board.dto.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileEntity extends BaseEntity{

	@Id @GeneratedValue
	private Long fileNo;
	@ManyToOne
	@JoinColumn(name = "boardNo")
	private Board boardNo;
	
//	@Column(length=45, nullable= false)
//	private String gid;
	
	private String fileName;
	
	private String savedName;
	
	private String path;
//	@Column(columnDefinition = "TINYINT(1) default '0'")
//	private Integer success;
}
