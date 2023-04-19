 // Smooth scroll for the menu and links with .scrollto classes
  
  
  
  
/*  $('.nav-menu a, #mobile-nav a, .scrollto').on('click', function() {
    if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') && location.hostname == this.hostname) {
      var target = $(this.hash);
      if (target.length) {
        var top_space = 0;

        if ($('#header').length) {
          top_space = $('#header').outerHeight();

          if( ! $('#header').hasClass('header-fixed') ) {
            top_space = top_space - 20;
          }
        }

        $('html, body').animate({
          scrollTop: target.offset().top - top_space
        }, 1500, 'easeInOutExpo');

        if ($(this).parents('.nav-menu').length) {
          $('.nav-menu .menu-active').removeClass('menu-active');
          $(this).closest('li').addClass('menu-active');
        }

        if ($('body').hasClass('mobile-nav-active')) {
          $('body').removeClass('mobile-nav-active');
          $('#mobile-nav-toggle i').toggleClass('fa-times fa-bars');
          $('#mobile-body-overly').fadeOut();
        }
        return false;
      }
    }
  });*/
  

  
  function replySubmit(parent){ //비동기로 댓글 요청 
	  if(parent){
		  const parentNo = parent.value;
	  }
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
	  


 /*   $.ajax({
        url: _url,
        data: formData,
        type: "POST",
    }).done(function (fragment) {
        $("#replies_area").replaceWith(fragment);
    })
    .fail(function (error){
		
		console.log(error);
	})
    ;*/

	  
	  
	     
	   
	   
	   
	   
	  
  }
  
  
  
  
  
  
  function ask(self){//파일 다운로드 전에 confirm 실행
		
	  
		return confirm(`${self.id} 를 다운로드 하시겠습니까?`);
	  
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  