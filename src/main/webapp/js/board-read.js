let modal = document.getElementById('modal-pwd-chk');

function pwdChk(e) {
	const FL = e.dataset.flag;
	const UPDATE = 'update';
	const DELETE = 'delete';

	modal.style.display = 'block'; // 모달창 보이기(none -> block)
	let newDiv = document.createElement('div'); // 팝업 배경 레이어 클래스 추가를 위한 새로운 요소 생성
	// 'modal-backdrop show'와 같이 띄워쓰기하면 인식 x
	newDiv.classList.add('modal-backdrop'); // 부트스트랩 전용 팝업 배경 레이어 클래스 추가
	newDiv.classList.add('show'); // 부트스트랩 전용 팝업 배경 레이어 클래스 추가
	document.body.appendChild(newDiv); // body의 마지막 자식 요소로 추가

	document.getElementById('btn-success').addEventListener('click', () => {
		let pwd = document.getElementById('pwd').value;
		let iboard = document.querySelector('.title').dataset.iboard;
		let dto = { "iboard": iboard, "pwd": pwd };
		const SUCCESS = 1;

		if (!pwd) {
			alert('비밀번호를 입력해주세요.');
		} else {
			$.ajax({
				type: 'post', url: '/winitech/board/check.do', data: JSON.stringify(dto), dataType: 'text', contentType: 'application/json',
				success: (data) => {
					if (data == SUCCESS) {
						if (FL == UPDATE) { location.href = '/winitech/board/update.do?iboard=' + iboard; }
						else {
							if (confirm('(주의) 답변글이 있는 경우 답변글이 함께 삭제됩니다.\n삭제된 게시글은 복구할 수 없습니다. 정말로 삭제하시겠습니까?')) {
								$.ajax({
									type: 'post', url: '/winitech/board/delete.do', data: { "iboard": iboard },
									success: (data) => {
										if (data == 1) { alert('게시글 삭제가 완료되었습니다. 게시글 목록으로 이동합니다.'); location.href = '/winitech/board/list.do'; }
										else { alert('게시글 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.'); return false; }},
									error: (x) => { console.log(x); }
								})
							}
						}
					}
					else { document.getElementById('p-pwd-chk-msg').innerText = '비밀번호가 일치하지 않습니다.'; }
				},
				error: (x) => { alert('게시글 수정 페이지로 이동할 수 없습니다. 잠시 후 다시 시도해주세요.'); console.log(x); }
			})
		}
	});
}

document.addEventListener('click', (e) => {
	if (e.target.id == 'btn-modal-close' || e.target.className == 'btn-close') {
		document.getElementById('p-pwd-chk-msg').innerText = ''; // 메세지 초기화
		document.getElementById('pwd').value = ''; // 비밀번호 초기화
		modal.style.display = 'none';
		const removeElement = document.getElementsByClassName('modal-backdrop show');
		removeElement[0].remove(); // class는 중복 선언 가능하기 때문에 idx로 값을 찾음
	}

	if (e.target.id == 'btn-reply') {
		const el = document.getElementById('btn-reply');
		const iboard = el.dataset.iboard;
		location.href = '/winitech/board/write.do?iboard=' + iboard;
	}
});