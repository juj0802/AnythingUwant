package com.AUW.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.AUW.board.domain.BoardUid;
import com.AUW.board.domain.UidEntity;

public interface UidRepository extends JpaRepository<UidEntity, BoardUid>, QuerydslPredicateExecutor{
	
	//좋아요 총 개수
	//@Query("select count(u) from UidEntity u where u.field=:field and u.uid like concat(:boardNo, '\\_', '%')")
	@Query("select count(u) from UidEntity u where u.field=:field and u.uid like concat(:boardNo, '\\_', '%')")
	int countByUid(@Param("field")String field, @Param("boardNo")long boardNo);
	//좋아요 목록
	@Query("select u from UidEntity u where u.field=:field and u.uid like concat(:boardNo, '\\_', '%', '\\_', :userNo)")
	Optional<UidEntity> findByNo(@Param("field")String field, @Param("boardNo")Long boardNo, @Param("userNo")Long userNo);

	Optional<UidEntity> findByFieldAndUid(String field, String uid);
}
