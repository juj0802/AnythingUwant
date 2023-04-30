package com.AUW.board.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.AUW.board.domain.board.Board;
import com.AUW.board.domain.board.FileEntity;



public interface FileRepository extends JpaRepository<FileEntity, Long>, QuerydslPredicateExecutor<FileEntity>{
	
	
	
	
//
//	@Modifying
//	FileEntity save(@Param("fileEntity") FileEntity fileEntity);
	
//	@Query("select f from FileEntity f where f.id=:id and f.success=1")
//	Optional<FileEntity> findById(@Param("id") Long id);
//	
//	@Query("select f from FileEntity f where f.gid=:gid and f.success=1 order by f.regDt desc")
//	List<FileEntity> findByGid(@Param("gid") String gid);
//	
////	//id에 의한 삭제
////	@Modifying
////	@Transactional
////	void deleteById(Long id);
////	//gid에의한 삭제
////	@Modifying
////	@Transactional
////	void deleteByGid(String gid);
//	
//	//수정
//	@Modifying
//	@Transactional
//	@Query("update FileInfo f set f.success=1 where f.gid=:gid")
//	void updateSuccess(@Param("gid") String gid);
	
	
}
