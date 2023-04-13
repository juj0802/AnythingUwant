package com.AUW.board.dto;

import lombok.AllArgsConstructor;

import lombok.Getter;



@Getter
@AllArgsConstructor
public enum BoardType {

	
	FREEBOARD("FREE_BOARD",1),
	LIKEBOARD("LIKED_BOARD",2),
	QUERYBOARD("QUERY_BOARD",3),
	NOTICEBOARD("NOTICE",4);
	
	private String type;
	private int seq;
	
	
}
