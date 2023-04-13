window.addEventListener("DOMContentLoaded",function(){
	//CKEDITOR 적용
	CKEDITOR.replace("content");
	CKEDITOR.config.height=500;
	
	
	
	
	
	
	
	
	//파일 선택 처리
/*	const filesEl = document.getElementById("files");
	if(filesEl){
		filesEl.addEventListener("change",function(e){
			
		const gidEl = document.getElementById("gid");
			if(!gidEl){return ;}
			
			const gid = gidEl.value;
			filesEl.dataset.gid = gid;
			const files = e.target.files;
			fileUpload.process(gid, files);
			console.log(e.target.files);
			
		});
	}*/
	
	
	
	
	
	
	
});



const fileUpload = {
	/**
	 * 파일 업로드 처리
	 * @params gid:그룹아이디
	 * @params files = 업로드할 파일들
	 * @params isImage = 이미지인지 체크
	 */
	
	process(gid,files){
		console.log("프로세스");
		try{
			if(!gid){throw "잘못된접근입니다."}
			if(!files || files.length == 0){throw "업로드할 파일을 선택하세요"}
			//formData객체 생성후 비동기로 파일,gid 전송
			const formData= new FormData();
			formData.append("gid",gid);
			
			for(const file of files){
			formData.append("file",file);
			}
			
			const xhr = new XMLHttpRequest();
			xhr.open("POST","/file/upload");
			xhr.onload = ()=>{
					console.log("프로세스2");	
				const result = JSON.parse(xhr.responseText);
				console.log(result);
				if(result){
					const fileInfos = result
					
					fileUploadCallback(fileInfos);
					
				}
				else{throw "파일 업로드 에러발생";}
			
			};
			xhr.send(formData);
			
			
		}catch(error){alert(error);}
		
	}
	
	
}

function fileUploadCallback(files){
	if(files.length == 0 ){
		return;
	}
	let uploadURL = "/uploads";
	const pathname = location.pathname;
	if(pathname.split("/").length>=3){
		uploadURL = `../${uploadURL}`;
		
	}
	
	for(const file of files){
		const id = file.id;
		
		const url = `${uploadURL}/${id % 10}/${id}`;
		const img = `<img src='${url}'>`;
		CKEDITOR.instances.reviewContent.insertHtml(img);
	}
	
	
}


























