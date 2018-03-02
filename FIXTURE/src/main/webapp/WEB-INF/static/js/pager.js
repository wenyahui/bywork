function check(){
	var p = $.trim($("#toPage").val());
	if(isNaN(p)){
		$("#toPage").select();
	}else{
		turnPage(p);
	}
}

function pageList(url,page,params){
	if(isNaN(page) || page < 1){
		page = 1;
	}
	var totalPages = $("#totalPages").val();
	if(Number(page) > Number(totalPages)){
		page = totalPages;
	}
	if(params==null){
		params = "";
	}
	window.location=url+"?pageNum="+page+params;
}
