function pwdChk() { const pwd = document.getElementById('pwd').value; }

document.addEventListener('click', (e) => {
	if(e.target.id == 'btn-reply') {
		const el = document.getElementById('btn-reply');
		const iboard = el.dataset.iboard;
		location.href = '/winitech/board/write.do?iboard=' + iboard;
	}
	
	if(e.target.id == 'btn-update') {
		const pwd = prompt('게시글 비밀번호를 입력해주세요.');
		const el = document.getElementById('btn-delete');
		const iboard = el.dataset.iboard;
		const dto = {"iboard" : iboard, "pwd" : pwd};
		
	    $.ajax({
	        type: 'post',
	        url: '/winitech/board/check.do',
	        data: JSON.stringify(dto),
	        dataType: 'text',
	        contentType: 'application/json',
	        success: (data) => {
	        	if(data == 1) {
		        	location.href = '/winitech/board/update.do?iboard=' + iboard;
	        	} else {
	        		alert('비밀번호가 일치하지 않습니다. 확인 후 다시 시도해주세요.');
	        	}
	        },
	        error: (x) => { console.log(x); }
	    })
	}
	
	if(e.target.id == 'btn-delete') {
		const pwd = prompt('게시글 비밀번호를 입력해주세요.');
		const el = document.getElementById('btn-delete');
		const iboard = el.dataset.iboard;
		const dto = {"iboard" : iboard, "pwd" : pwd};
		
	    $.ajax({
	        type: 'post',
	        url: '/winitech/board/check.do',
	        data: JSON.stringify(dto),
	        dataType: 'text',
	        contentType: 'application/json',
	        success: (data) => {
	        	if(data == 1) {
		        	if(confirm('(주의) 답변글이 있는 경우 답변글이 함께 삭제됩니다.\n삭제된 게시글은 복구할 수 없습니다. 정말로 삭제하시겠습니까?')) {
		        	    $.ajax({
		        	        type: 'post',
		        	        url: '/winitech/board/delete.do',
		        	        data: {"iboard" : iboard},
		        	        success: (data) => {
		        	        	if(data == 1) {
		        	        		alert('게시글 삭제가 완료되었습니다. 게시글 목록으로 이동합니다.');
		        	        		location.href = '/winitech/board/list.do';
		        	        	} else {
		        	        		alert('게시글 삭제에 실패했습니다. 잠시 후 다시 시도해주세요.');
		        	        		return false;
		        	        	}
		        	        },
		        	        error: (x) => { console.log(x); }
		        	    })
		        	}
	        	} else {
	        		alert('비밀번호가 일치하지 않습니다. 비밀번호 확인 후 다시 시도해주세요.');
	        	}
	        },
	        error: (x) => { console.log(x); }
	    })
	}
});