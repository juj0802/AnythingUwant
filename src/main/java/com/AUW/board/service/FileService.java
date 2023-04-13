package com.AUW.board.service;



import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.AUW.board.domain.FileEntity;
import com.AUW.board.domain.board.Board;
import com.AUW.board.repository.FileRepository;
import com.querydsl.core.BooleanBuilder;
import com.test.board.domain.QFileEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileService {
	
	
	@Value("${file.uploadPath}")
	private String uploadPath;
	
	private final FileRepository fileRepository;
	
	public void saveFiles(List<MultipartFile> files,Board board) throws IllegalStateException, IOException {
		
		if(files.isEmpty() || files.size()<1) {
			return;
		}
		
		for(MultipartFile file : files) {
			
			String fileName = file.getOriginalFilename();//파일 원래이름추출
			String _uuid = UUID.randomUUID().toString();//파일 이름으로 쓸 랜덤uuid생성 중복검사는 추후에
			String uuid = this.checkDuplicate(_uuid);
			String extension = fileName.substring(fileName.lastIndexOf("."));
			String savedName = uuid + extension;
			
			String path = uploadPath + File.separator + savedName;
			
			FileEntity entity = FileEntity.builder()
					.fileName(fileName)
					.savedName(savedName)
					.path(path)
					.boardNo(board)
					.build();
			entity = fileRepository.save(entity);
			file.transferTo(new File(path));
			
			
		}
		
	
	}
	
	//uuid중복검사 , querydsl 사용
	public String checkDuplicate(String uuid) {
		
		BooleanBuilder builder = new BooleanBuilder();
		QFileEntity file = QFileEntity.fileEntity;
		
		builder.and(file.savedName.contains(uuid));
		
		Optional<FileEntity> _entity = fileRepository.findOne(builder);
		
		FileEntity entity = _entity.orElse(null);
		if(entity != null) {
			//재귀함수를 통해 중복검사
			String newuuid = UUID.randomUUID().toString();
			this.checkDuplicate(newuuid);
		}
		
		return uuid;
	}
	
//	public List<FileEntity> process(MultipartFile[] files){
//		
//		return process(files,null);
//	}
//	
//	
//	
//	public List<FileEntity> process(MultipartFile[] files, String gid){
//		
//		if(gid==null || gid.isBlank()) {
//			gid = ""+System.currentTimeMillis();
//		}
//		List<FileEntity> list = new ArrayList<>();
//		//db저장
//		for(MultipartFile file : files) {
//			FileEntity entity = FileEntity.builder()
//					.gid(gid)
//					.fileName(file.getOriginalFilename())
//					.fileType(file.getContentType())
//					.build();
//			
//			entity = fileRepository.save(entity);
//			
//			//id값으로 업로드 파일 경로 설정
//			Long id = entity.getId();
//			String uploadDir = uploadPath + File.separator+(id%10);
//			File _uploadDir = new File(uploadDir);
//			if(!_uploadDir.exists()) {
//				_uploadDir.mkdirs();//폴더가 없을경우 생성
//			}
//			String uploadFilePath = uploadDir + File.separator+id;
//			
//			//파일 업로드 
//			try {
//				file.transferTo(new File(uploadFilePath));
//				list.add(entity);
//				
//			}catch(Exception e) {
//				e.printStackTrace();
//				fileRepository.deleteById(id);
//				throw new RuntimeException("업로드 실패!");
//			}
//			
//		}
//		
//		return list;
//		
//	}
//	
////	public void updateSuccess(String gid) {
////		try {
////		fileRepository.updateSuccess(gid);
////		}catch(Exception e) {
////			e.printStackTrace();
////			
////		}
////	}
////	
//	
//	public FileEntity getOneFile(Long id) {
//		
//		Optional<FileEntity> _entity = fileRepository.findById(id);
//		
//		FileEntity entity = _entity.orElseThrow(()->new RuntimeException("파일을 찾을수없습니다"));
//		
//		
//		
//		return entity;
//	}
//	//같은 gid값을 가진 엔티티들 list로 반환
////	public List<FileEntity> getFileList(String gid){
////		BooleanBuilder builder = new BooleanBuilder();
////		Qfile
////		
////		List<FileEntity> list = fileRepository.findByGid(gid);
////		if(list.size()==0 || list.isEmpty()) {
////			throw new RuntimeException("파일을 찾을수없습니다");
////		}
////		return list;
////	
////	}
////	
//	//파일삭제
//	public void reomoveOneFile(Long id) {
//		fileRepository.delete(this.getOneFile(id));
//	}
//	
//	//gid로 그룹화된 파일들 삭제
////	public void removeFileList(String gid) {
////		
////		List<FileEntity> list = this.getFileList(gid);
////		for(FileEntity entity : list) {
////			fileRepository.delete(entity);
////		}
////		
////	}
//	
//	
//	
	
	
	
	
	
}
