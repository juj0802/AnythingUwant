package com.AUW.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AUW.board.domain.board.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

	
	
}
