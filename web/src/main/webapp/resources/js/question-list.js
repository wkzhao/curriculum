$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
	},
	
	bindChangeSearchParam : function bindChangeSearchParam(){
		$("#question-filter dl dd span").click(function(){
			if($(this).hasClass("label"))return false;
			
			
			var genrateParamOld = question_list.genrateParamOld();
			
			if($(this).parent().parent().attr("id") == "question-filter-field" ){
				genrateParamOld.knowledge = $(this).data("id");
				question_list.redirectUrl(genrateParamOld);
				
			}else if($(this).parent().parent().attr("id") == "question-filter-knowledge" ){
				genrateParamOld.knowledgePoint = $(this).data("id");
				question_list.redirectUrl(genrateParamOld);
				
			}else{
				genrateParamOld.questionType = $(this).data("id");
				question_list.redirectUrl(genrateParamOld);
			}
		});
		
		$(".pagination li a").click(function(){
			var pageId = $(this).data("id");
			if(pageId==null||pageId=="")return false;
			var genrateParamOld = question_list.genrateParamOld();
			genrateParamOld.page = pageId;
			question_list.redirectUrl(genrateParamOld);
			
		});
	},
	
	genrateParamOld :function genrateParamOld(){
		
		var knowledge = $("#question-filter-field dd .label").data("id");
		var knowledgePoint = $("#question-filter-knowledge dd .label").data("id");
		var questionType = $("#question-filter-qt dd .label").data("id");
		var searchParam = 1;
		var page = 1;
		
		var data = new Object();
		data.knowledge = knowledge == null?0:knowledge;
		data.knowledgePoint = knowledgePoint==null?0:knowledgePoint;
		data.questionType= questionType == null?0:questionType;
		data.searchParam = searchParam;
		data.page = page;
		
		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.knowledge;
		paramurl = paramurl + "-" + newparam.knowledgePoint;
		paramurl = paramurl + "-" + newparam.questionType;
		paramurl = paramurl + "-" + newparam.searchParam;
		paramurl = paramurl + "-" + newparam.page;

		document.location.href = document.getElementsByTagName('base')[0].href
				+ 'admin/question-list/' + paramurl;
	}
};