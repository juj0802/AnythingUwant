
window.addEventListener("DOMContentLoaded",function(){
	
	/*클릭한 좋아요 상태처리 */
	const uidEl = document.getElementById("uid");
	if(uidEl){
		document.getElementsByClassName("toggleLiked")[0].className = "fa-solid fa-heart toggleLiked on";
	}
	
	/*좋아요 토글 처리 */
	const toggleLikedEls = document.getElementsByClassName("toggleLiked");
	
	for(const el of toggleLikedEls){
		el.addEventListener("click",function(){
			try{
				const classList = this.classList;
				if(classList.contains("login_required")){
				throw new Error("로그인 후 이용가능합니다.");	
				}
				console.log(classList);
				const boardNo = document.getElementById("boardNo").value;
				const uidEl = document.getElementById("uid");
				const uid = uidEl? uidEl.value : "";
				const liked = classList.contains("on") ? false : true;
				const url = `/liked?boardNo=${boardNo}&uid=${uid}`;
				
				const xhr = new XMLHttpRequest();
				xhr.open("GET", url);
				
				xhr.onreadystatechange = function(){
						if (xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE) {
						const result = JSON.parse(xhr.responseText);
						console.log(result);
						if(!result.success){
							alert("에러발생! 다시 시도해주세요");
							return;
						}
						if(liked){
								el.className = "fa-solid fa-heart toggleLiked";
								classList.add("on");
						}else{
								el.className = "fa-regular fa-heart toggleLiked";
								classList.remove("on");
						}
						document.getElementById("totalLikes").innerText = `추천수 : ` + result.data;
					
					
				}
				
				};
				xhr.send();
				
			} catch(error){
				alert(error.message);
			}
		});
	}
		
});






function moveToWrite(no){ //게시판 수정화면으로 이동하는 함수
	
	const boardNo = no.value;
	const url = `/board_update/update/${boardNo}`;
	location.href = url;
}

function moveToDelete(no){
	const boardNo = no.value;
	const url = `/board_delete/delete/${boardNo}`;
	location.href = url;
}





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
				<textarea class='form-control border-radius-none mt-1 w-85' rows="3" placeholder="답글을 입력하세요" id="childContent"></textarea>
				<button class='btn btn-primary border-radius-none mt-1 btn-outline w-15' type="button" value=${parentNo} onclick="childSubmit(this)">등록</button>
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
   function updateArea(item){ //수정 댓글창을 만들어주기위한 함수, newArea함수에서 약간수정
	 
	
	  const userId = document.getElementById("userId").value;
	   
	 
		const updatetNo = item.id; //수정 할 댓글 번호
		const content = item.value; // 수정 전 댓글 내용
		 if(content==="삭제된 댓글입니다."){
		  alert("삭제된 댓글은 수정할수없습니다");
		  return;
	  }
	  
		const area = `${updatetNo},${userId}`; 
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
				<textarea class='form-control mt-1 border-radius-none w-85' rows="3" placeholder="수정할 내용을 입력해주세요" id="childContent"></textarea>
				<button class='btn btn-primary mt-1 btn-outline border-radius-none w-15' type="button" id=${updatetNo} value=${content} onclick="updateReply(this)">수정 완료</button>
					</div>
			`;//대댓글 영역으로 활용
			
			parentReply.append(childReply);//부모 영역안에 자식으로 삽입
 }
  function updateReply(updateItem){
	  	
	   const replyNo = updateItem.id;  //댓글번호
	  const boardNo = document.getElementById("boardNo").value; //게시판 번호
	  const userId = document.getElementById("userId").value; //유저 아이디
	  const content = updateItem.value; //수정할 내용
	  const newContent = document.getElementById("childContent").value;
	 
	 if(content === newContent){
		 alert("변동된 사항이없습니다");
		 return;
	 }
	  
	    const formData = new FormData(); //formdata 객체를 생성후 request보낸다
	  		  formData.append("replyNo",replyNo);
			  formData.append("userId",userId);
			  formData.append("content",newContent);
			  formData.append("boardNo",boardNo);
	  		
		      let url = `/reply/updateReply`;

			  const xhr = new XMLHttpRequest();
	  xhr.open("POST",url,true);
	  xhr.send(formData);
	  
	  xhr.onreadystatechange = function(){
		if(xhr.status == 200 && xhr.readyState == XMLHttpRequest.DONE){
			
			$("#replies_area").replaceWith(xhr.responseText);
			alert("수정이 완료되었습니다.");
			
		}  
	  };
	  xhr.onerror = function(err){
		  console.log(err);
	  };
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  