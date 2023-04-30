package com.AUW.board.domain.board;

import com.AUW.board.domain.BaseEntity;
import com.AUW.board.dto.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class FileEntity extends BaseEntity{

	@Id @GeneratedValue
	private Long fileNo;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "boardNo")
	private Board board;
	
//	@Column(length=45, nullable= false)
//	private String gid;
	
	private String fileName;
	
	private String savedName;
	
	private String path;
//	@Column(columnDefinition = "TINYINT(1) default '0'")
//	private Integer success;
}
