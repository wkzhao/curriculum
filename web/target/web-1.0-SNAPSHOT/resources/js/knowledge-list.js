$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
		this.bindDelete();
	},
	
	bindChangeSearchParam : function bindChangeSearchParam(){
		
		$(".pagination li a").click(function(){
			var pageId = $(this).data("id");
			if(pageId==null||pageId=="")return false;
			var genrateParamOld = question_list.genrateParamOld();
			genrateParamOld.page = pageId;
			question_list.redirectUrl(genrateParamOld);
			
		});
	},
	
	genrateParamOld :function genrateParamOld(){
		
		var page = 1;
		var data = new Object();
		data.page = page;
		
		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.page;

		document.location.href = document.getElementsByTagName('base')[0].href
				+ 'admin/knowledge-list-' + paramurl;
	},
	
	
	bindDelete : function bindDelete(){
		$(".delete-btn").click(function(){

			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "POST",
				url : "admin/delete-knowledge",
				data: JSON.stringify($(this).data("id")),
				success : function(msg) {
					if (msg.status.code == 0) {
						alert("删除成功");
						window.location.reload();
					} else {
						alert("操作失败");
					}

				},
				error : function(jqXHR, textStatus) {
					alert("操作失败请稍后尝试");
				}
			});
			
			return false;

			
			
		});
	}
};