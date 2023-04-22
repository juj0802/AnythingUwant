
 function newArea(e){
	 
	
	  const userId = document.getElementById("userId").value;
	   
	 
		const parentNo = e.value; //부모댓글 번호
		const area = `${parentNo},${userId}`; 
		let parentReply = document.getElementById(area); 
		let childReply = document.getElementById("childReply");  //childReply라는 아이디를 가진 엘리먼트가있는지 확인
			if(!childReply){ //존재하지않는다면 새로운 div만든다
				childReply = document.createElement("div");
				childReply.id = "childReply";
			} 
		 
		   	if(childReply.innerHTML){ // 엘리먼트 초기화
				childReply.innerHTML="";
			}	
			
			childReply.innerHTML=`
					<div class='flex'>
				<textarea class='form-control w-85' rows="3" placeholder="답글을 남겨보세요" id="childContent"></textarea>
				<button class='btn btn-primary btn-outline w-15' type="button" value=${parentNo} onclick="childSubmit(this)">등록</button>
					</div>
			`;//대댓글 영역으로 활용
			
			parentReply.append(childReply);//부모 영역안에 자식으로 삽입
 }
 
  
function childSubmit(parent){
	const parentNo = parent.value; //부모댓글 번호
	const userId = document.getElementById("userId").value; //유저 아이디
	const childContent = document.getElementById("childContent").value; // 댓글 내용

	if(childContent.trim()===""){
		 alert("내용을 입력해주세요");
		  return;
	};
	 const boardNo = document.getElementById("boardNo").value; //게시판 번호
	 const formData = new FormData(); //formdata 객체를 생성후 request보낸다
	  formData.append("userId",userId);
	  formData.append("content",childContent);
	  formData.append("boardNo",boardNo);
	  formData.append("parentNo",parentNo);
	
		
 	  let url = `/reply/submit`;
	  const xhr = new XMLHttpRequest();
	  xhr.open("POST",url,true);
	  xhr.send(formData);
	  
	  xhr.onreadystatechange = function(){
		if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE){
			$("#replies_area").replaceWith(xhr.responseText);
			
			 
			
		}  
	  };
	  xhr.onerror = function(err){
		  console.log(err);
	  };
	
}
  
  function replySubmit(){ //비동기로 댓글 요청 
	
	  const userId = document.getElementById("userId").value; //유저 아이디
	
	  const content = document.getElementById("mainContent").value; //댓글내용
	  if(content.trim() ===""){
		  alert("내용을 입력해주세요");
		  return;
	  }
	  const boardNo = document.getElementById("boardNo").value; //게시판 번호
	  
	  
	  const formData = new FormData(); //formdata 객체를 생성후 request보낸다
	  formData.append("userId",userId);
	  formData.append("content",content);
	  formData.append("boardNo",boardNo);
	  let url = `/reply/submit`;
	  const xhr = new XMLHttpRequest();
	  xhr.open("POST",url,true);
	  xhr.send(formData);
	  
	  xhr.onreadystatechange = function(){
		if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE){
			$("#replies_area").replaceWith(xhr.responseText);
			document.getElementById("mainContent").value = "";
			
			
		}  
	  };
	  xhr.onerror = function(err){
		  console.log(err);
	  };
	  
  }
  
  function deleteReply(deleteItem){

	
	
	  if(deleteItem.value ==="삭제된 댓글입니다."){
		  alert("이미 삭제된 댓글입니다.");
		  return;
	  }
	  if(confirm("정말 삭제하시겠습니까?")){
		  
		    const replyNo = deleteItem.id;  //댓글번호
	  const boardNo = document.getElementById("boardNo").value; //게시판 번호
	  const userId = document.getElementById("userId").value; //유저 아이디
	
	  	
	  		  const formData = new FormData(); //formdata 객체를 생성후 request보낸다
	  		  formData.append("replyNo",replyNo);
			  formData.append("userId",userId);
		
			  formData.append("boardNo",boardNo);
	  		
		      let url = `/reply/deleteReply`;

			  const xhr = new XMLHttpRequest();
	  xhr.open("POST",url,true);
	  xhr.send(formData);
	  
	  xhr.onreadystatechange = function(){
		if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE){
			
			$("#replies_area").replaceWith(xhr.responseText);
		
			
		}  
	  };
	  xhr.onerror = function(err){
		  console.log(err);
	  };
		  
		  
	  }else{
		  return;
	  }
	  


	  
	  
	     
	   
	   
	   
	   
	  
  }
  
  
  
  
  
  
  function ask(self){//파일 다운로드 전에 confirm 실행
		
	  
		return confirm(`${self.id} 를 다운로드 하시겠습니까?`);
	  
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  