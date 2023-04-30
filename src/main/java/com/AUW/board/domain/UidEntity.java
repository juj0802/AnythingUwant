package com.AUW.board.domain;





import com.AUW.board.domain.board.Board;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="uid")
@Builder
@AllArgsConstructor
@IdClass(BoardUid.class)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class UidEntity extends BaseEntity{//좋아요&조회수 관련 엔티티
	@Id
	private String uid; //식별자
	@Id
	private String field; // 사용처
}
