document.addEventListener('click', (e) => {
	if(e.target.id == 'btn-search') {
		const category = document.getElementById('select-search');
		const keyword = document.getElementById('input-search');
		if(category.value == 'null') { alert('검색 카테고리를 선택해주세요.'); category.focus(); }
		else if(keyword.value == null || keyword.value == '') { alert('검색 키워드를 입력해주세요.'); keyword.focus(); }
		else { location.href = '/winitech/board/list.do?' + category.value + '=' + keyword.value; }
	}
});