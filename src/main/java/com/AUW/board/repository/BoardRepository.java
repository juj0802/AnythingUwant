package com.AUW.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.AUW.board.domain.board.Board;

import jakarta.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Long>, QuerydslPredicateExecutor<Board>{

	@Modifying
	@Transactional
	@Query("update Board b set b.boardHit=(b.boardHit+1) where b.boardNo=:boardNo")
	int updateBoardHit(@Param("boardNo") Long boardNo);
	@Modifying
	@Transactional
	@Query("update Board b set b.totalLikes=:totalLikes where b.boardNo=:boardNo")
	int updateTotalLikes(int totalLikes, Long boardNo);
}
