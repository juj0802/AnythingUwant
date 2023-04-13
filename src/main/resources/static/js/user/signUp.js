window.addEventListener("DOMContentLoaded",function(){
	const checkBoxes = document.querySelectorAll(".checkboxes");// 이용약관, 서비스안내 체크박스
	const checkAll = document.querySelector("#checkAll"); //전체 동의/해제 체크박스
	const agreements = {
		checkbox1 : false,
		checkbox2 : false
	}
	checkBoxes.forEach((item)=>{
		item.addEventListener("change",toggleCheckbox);
		
	});
	// 각 체크박스 클릭 이벤트 함수
	function toggleCheckbox(e){
		const { checked, id } = e.target;
		agreements[id] = checked;
		this.parentNode.classList.toggle('active');
		
		checkAllStatus();
	}// 체크박스 이벤트로 인한 전체 동의 체크 여부
		function checkAllStatus() {
		const { check1, check2 } = agreements;
		if(check1 && check2) {
			checkAll.checked = true;
		} else {
			checkAll.checked = false;
		}
	}
	
	// 이용약관 전체 동의 체크박스 이벤트
	checkAll.addEventListener('click', (e) => {
		const { checked } = e.target;
		if(checked) {
			checkBoxes.forEach((item) => {
				item.checked = true;
				agreements[item.id] = true;
			});
		} else {
			checkBoxes.forEach((item) => {
				item.checked = false;
				agreements[item.id] = false;
			});
		}
	});
	
	
	
});