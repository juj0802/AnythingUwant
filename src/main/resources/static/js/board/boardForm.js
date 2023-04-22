window.addEventListener("DOMContentLoaded",function(){
	//CKEDITOR 적용
	CKEDITOR.replace("content");
	CKEDITOR.config.height=500;
	
	const filesEl = document.getElementById("files");
	
	filesEl.addEventListener("change",function(e){
		
	let list = e.target.files;
	let fileList = document.getElementById("fileList");
	fileList.innerHTML=``;
	
		for(item of list){
			fileList.innerHTML +=`<div><span>${item.name}</span> <button type="button" onclick=" deleteAttach('${item.name}') ">
			<span class="material-symbols-outlined">
backspace
</span>
			</button></div>`;
			
		}
		fileList.classList.add("h-auto");
		

		

		
	});

	

	
});


function deleteAttach(itemName){


	const filesEl = document.getElementById("files").files;
		let fileList = document.getElementById("fileList");
	const dataTranster = new DataTransfer();
	     Array.from(filesEl)
                    .filter(file => file.name != itemName)
                    .forEach(file => {
                    dataTranster.items.add(file);
                 });
            document.querySelector('#files').files = dataTranster.files;
              
			fileList.innerHTML=``;
	
		for(item of filesEl){
			fileList.innerHTML +=`<div><span>${item.name}</span> <button type="button" onclick=" deleteAttach('${item.name}') ">
				<span class="material-symbols-outlined">
backspace
</span>
			 </button></div>`;
			
		}
			if(filesEl.length==0){
				fileList.classList.remove("h-auto");
			}


}


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


























