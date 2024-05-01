let deleteIfileList = [];

document.addEventListener('click', (e) => {
	if(e.target.id == 'btn-file-delete') {
		if(confirm('해당 첨부파일을 삭제하시겠습니까?')) {
			// 타겟 이벤트 부모 노드 삭제
			let target = e.target;
			let parentNode = target.parentNode.parentNode;
			parentNode.remove();

			alert('첨부파일이 삭제되었습니다.');
			
			// 클라이언트가 삭제한 첨부파일 pk
			let deleteFileId = target.dataset.ifile;
			// 배열에 담아 저장
			deleteIfileList.push(deleteFileId);
			
			console.log('deleteIfileList = ', deleteIfileList);
		}
	}
	
	if(e.target.id == 'btn-update') {
		if(confirm('게시글을 수정하시겠습니까?')) {
			let pwd = prompt('게시글 수정을 위해 비밀번호를 입력해주세요.');
			let el = document.getElementById('btn-update');
			let iboard = el.dataset.iboard;
			let dto = {"iboard" : iboard, "pwd" : pwd};
			
		    $.ajax({
		        type: 'post',
		        url: '/winitech/board/check.do',
		        data: JSON.stringify(dto),
		        dataType: 'text',
		        contentType: 'application/json',
		        success: (data) => {
		        	if(data == 1) {
		        		let formData = new FormData(); // multipart/form-data
		        		let title = $('#title').val();
		        		let contents = $('#contents').val();
		        		let dto = {"iboard" : iboard, "title" : title, "contents" : contents, "deleteIfileList" : deleteIfileList };
		        		let files = document.getElementById('input-file').files;
			    		
						console.log('dto = ', dto);
						console.log('files = ', files);
	
			    		for(let i = 0; i < files.length; i++) {
			    			let file = files[i];
			    			formData.append("files", file);	
			    		}
			    		
			    		formData.append("dto", new Blob([JSON.stringify(dto)], {type: "application/json"}));
			    		
			        	// 첨부파일 받을 수 있게 수정
		    		    $.ajax({
		    		        type: 'post',
		    		        url: '/winitech/board/update.do',
		    		        data: formData,
		        	        contentType: false, // 전달 데이터 형식 / formData로 보낼 경우 명시 필수
		        	        processData: false, // string 변환 여부 / formData로 보낼 경우 명시 필수
		    		        success: (data) => {
		    		        	if(data == 1) {
		    		        		if(confirm('게시글이 수정되었습니다. 수정된 게시글을 확인하러 가시겠습니까?')) { location.href = '/winitech/board/view.do?iboard=' + iboard; }
		    		        		else { alert('게시글 목록으로 이동합니다.'); location.href = '/winitech/board/list.do'; }}
		    		        	else { alert('게시글 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.'); }
		    		        },
		    		        error: (x) => { console.log(x); }
		    		    })} 
		        	else { alert('비밀번호가 일치하지 않습니다. 확인 후 다시 시도해주세요.'); }},
		        error: (x) => { console.log(x); alert('게시글 수정에 실패하였습니다. 잠시 후 다시 시도해주세요.'); }
		    })
		}
	}
	
    if(e.target.id == 'btn-cancle') {
    	if(confirm('작성을 취소하시겠습니까?')) {
    		const NAME = document.getElementById('btn-cancle').dataset.name;
    		const TITLE = document.getElementById('btn-cancle').dataset.title;
    		let suffixUrl = 'null';
    		alert('작성이 취소되었습니다.');
    		if(NAME != undefined) { suffixUrl = 'name=' + NAME; }
    		else if(TITLE != undefined) { suffixUrl = 'title=' + TITLE; }
    		location.href = '/winitech/board/list.do?' + suffixUrl;
    	}
    }
    
    if(e.target.id == 'btn-insert') {
    	let name = $('#name');
    	let pwd = $('#pwd');
    	let title = $('#title');
    	let contents = $('#contents');
    	let el = document.getElementById('btn-insert');
    	let code = el.dataset.iboard;
    	
    	if(!name.val()) { alert('닉네임을 입력해주세요.'); name.focus(); }
    	else if(!pwd.val()) { alert('패스워드를 입력해주세요.'); pwd.focus(); }
    	else if(!title.val()) { alert('제목을 입력해주세요.'); title.focus(); }
    	else if(!contents.val()) { alert('내용을 입력해주세요.'); contents.focus(); }
    	else {
    		let formData = new FormData();
    		let dto = {code : code, name : name.val(), pwd : pwd.val(), title : title.val(), contents : contents.val()};
//    		let file = document.getElementById('input-file').files[0]; // 단일 파일 업로드 시
    		let files = document.getElementById('input-file').files;
    		
    		const LIMIT_FILE_COUNT = 3;
    		let fileLength = files.length;
    		
    		if(fileLength > LIMIT_FILE_COUNT) {
    			alert('첨부파일 최대 개수는 3개입니다. 수정 후 다시 등록해주세요.\n현재 첨부파일 개수: ' + fileLength);
				return false;
    		} else {
        		for(let i = 0; i < fileLength; i++) {
        			let file = files[i];
        			
        			// 예외처리
        			const FILE = file;
        			const FILE_NAME = file.name;
        			const FILE_SIZE = file.size;
        			const FILE_MAX_SIZE = 10 * 1024 * 1024;
        			const FILE_TYPE = file.type;
        			
        			// console.log('FILE_SIZE = ', FILE_SIZE, 'FILE_MAX_SIZE = ', FILE_MAX_SIZE);
        			
        			if(FILE_SIZE > FILE_MAX_SIZE) {
        				alert('최대 파일 크기를 초과했습니다.\n최대 파일 크기는 ' + FILE_MAX_SIZE + 'byte입니다.\n\n해당 파일명: ' + FILE_NAME + '\n해당 파일 크기: ' + FILE_SIZE + 'byte');
        				return false;
        			}
        			
	    			formData.append("files", file);	
        		}
        		
        		formData.append("dto", new Blob([JSON.stringify(dto)], {type: "application/json"}));
//        		formData.append("file", file); // 단일 파일 업로드 시
        		
        		$.ajax({
        	        type: 'post',
        	        url: '/winitech/board/write.do',
        	        contentType: false, // 전달 데이터 형식 / formData로 보낼 경우 명시 필수
        	        processData: false, // string 변환 여부 / formData로 보낼 경우 명시 필수
        	        data: formData,
        	        success: (data) => {
						// data - ajax 반환 값으로 넘어온 HashMap<key - property, value - MSG>로 구성        	        	
        	        	const MSG = data.MSG;
						const INTERNAL_SERVER_ERROR = -5;
//						const PASHED_PASSWORD_FAIL_ERROR = -1;
						const VALIDATION_ERROR = -2;
						
						console.log('연결 성공');
						console.log('data = ', data);
						
       	        		if(MSG > 0) {
        	        			if(confirm('게시글 등록이 완료되었습니다. 등록한 글을 확인하러가시겠습니까?')) {
        	        				const IBOARD = data.IBOARD;
        	        				location.href = '/winitech/board/view.do?iboard=' + IBOARD;
        	        			} else {
            	        			alert('게시판 목록으로 이동합니다.');
    	       	            		const NAME = document.getElementById('btn-insert').dataset.name;
    	       	            		const TITLE = document.getElementById('btn-insert').dataset.title;
    	       	            		let suffixUrl = '?null';
    	       	            		
    	       	            		if(NAME != undefined) { suffixUrl = 'name=' + NAME; }
    	       	            		else if(TITLE != undefined) { suffixUrl = 'title=' + TITLE; }
    	       	            		location.href = '/winitech/board/list.do' + suffixUrl;
        	        			}
        	        	} else {
        	        		if(MSG == -1) {
        	        			alert('[' + MSG + '] 패스워드 암호화에 실패했습니다. 잠시 후 다시 시도해주세요.');
        	        			return false;
        	        		} else if(MSG == -2) {
            	        		const ERRORS = data.ERRORS;
            	        		let alertMsg = '[' + MSG + '] 유효성 검증에 실패했습니다.\n\n';
            	        		for(let item in ERRORS) { alertMsg += item + ': ' + ERRORS[item] + '\n'; }
            	        		alert(alertMsg); // 유효성 검증 실패한 필드 + 메세지 출력
            	        		return false;
        	        		} else if(MSG == -4) {
        	        			alert('[' + MSG + '] 일시적 오류로 인해 게시글 등록에 실패했습니다. 잠시 후 다시 시도해주세요.');
        	        			return false;
        	        		} else if(MSG == -5) {
        	        			alert('[' + MSG + '] 일시적 오류로 인해 게시글 등록에 실패했습니다. 잠시 후 다시 시도해주세요.');
        	        			return false;
        	        		}
        	        	}
        	        }, 
        	        error: (x) => { console.log(x); }
        	     })
        	 }
    	}
    }
});