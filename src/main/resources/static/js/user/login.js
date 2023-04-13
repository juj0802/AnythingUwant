/* 로그인 시도 시 아이디 저장 JS */
window.addEventListener("DOMContentLoaded", function() {
	const userId = getCookie("Cookie_userId");
	
	if(userId) {
		document.getElementById("userId").value = userId;
		document.getElementById("idCheck").checked = true;
	}
});

function loginProcess() {
	const userId = document.getElementById("userId").value;
	const idCheck = document.getElementById("idCheck").checked;
	
	console.log(idCheck);
	
	if(idCheck) {
		setCookie("Cookie_userId", userId, 7);
	} else {
		deleteCookie("Cookie_userId");
	}
	loginForm.submit();
}

function setCookie(cookieName, value, exdays) {
	const exdate = new Date();
	exdate.setDate(exdate.getDate() + exdays);
	const cookieValue = escape(value) + ((exdays == null) ? "" : "; expires=" + exdate.toGMTString());
	document.cookie = cookieName + "=" + cookieValue;
}

function getCookie(cookieName) {
  cookieName = cookieName + '=';
  var cookieData = document.cookie;
  var start = cookieData.indexOf(cookieName);
  var cookieValue = '';
  
  if(start != -1){
    start += cookieName.length;
    var end = cookieData.indexOf(';', start);
  if(end == -1)end = cookieData.length;
  	cookieValue = cookieData.substring(start, end);
  }
  return unescape(cookieValue);
}

function deleteCookie(cookieName){
  var expireDate = new Date();
  expireDate.setDate(expireDate.getDate() - 1);
  document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString();
}
