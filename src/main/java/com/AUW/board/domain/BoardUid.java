package com.AUW.board.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BoardUid {//복합키 매핑을 위한클래스

	private String uid;
	private String field;
}
