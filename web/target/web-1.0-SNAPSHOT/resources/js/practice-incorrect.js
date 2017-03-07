$(function() {
	modal.prepare();
	examing.initial();

});

var examing = {
	initial : function initial() {
		this.initialModel();
		this.bindSwitchQuestion();
		this.bindSubmitQuestion();
	},
	
	examModel : examModel = true,
	
	initialModel : function initialModel(){
		$(".answer-desc").hide();
		$(".question-point-content").hide();
		
		$("#switch-model-btn").click(function(){
			if($(this).data("exam") == true){
				$(this).data("exam",false);
				examing.examModel = false;
				$(this).removeClass("btn-success");
				$(this).addClass("btn-info");
				$(this).text("背题模式");
//				$("#bk-conent-comment").show();
				$(".answer-desc").show();
				
				$(".question-body input").attr("disabled","disabled");
				
				$("#submit-q-btn").hide();
				
			}else{
				$(this).data("exam",true);
				examing.examModel = true;
				$(this).removeClass("btn-info");
				$(this).addClass("btn-success");
				$(this).text("答题模式");

				$(".answer-desc").hide();
				$(".qt-finished .answer-desc").show();
				
				$(".question-body input").removeAttr("disabled");
				$(".qt-finished .question-body input").attr("disabled","disabled");
				$("#submit-q-btn").show();
			}
		});
	},

	
	bindSubmitQuestion : function bindSubmitQuestion(){
		$("#submit-q-btn").click(function(){
			var currentQuestion  = $(".current_selected");
			
			if(currentQuestion.hasClass("qt-finished")){
				util.error("此题已经做完");
				return false;
			}
			
			var answer = examing.getAnswerValue();
			
			if(answer == "" || answer== null){
				util.error("只有完成该题后才能提交答案！");
				return false;
			}else{
				if(answer == $(currentQuestion).find(".answer_value").text()){
					$(currentQuestion).find(".answer-desc-summary").addClass("answer-desc-success");
					
				}else{
					$(currentQuestion).find(".answer-desc-summary").addClass("answer-desc-error");
				}
				
			}
			
			currentQuestion.addClass("qt-finished");
            currentQuestion.find(".question-body input").attr("disabled","disabled");
            currentQuestion.find(".answer-desc").show();
		});
		
		
	},
	getAnswerValue : function(){
		var currentQuestion  = $(".current_selected");
		
		var answer;

		var name = currentQuestion.name;

		if(currentQuestion[0].name == 1 || currentQuestion[0] .name == 3){
			var radio_checked = $(currentQuestion).find("input[type=radio]:checked");
			var radio_all = $(currentQuestion).find("input[type=radio]");
			if(radio_checked.length == 0){
				answer = "";
			}else{
				answer = radio_checked.val();
			}
			
		}else 	if( currentQuestion[0].name == 2){
			
			var checkbox_checked = $(currentQuestion).find("input[type=checkbox]:checked");
			var checkbox_all = $(currentQuestion).find("input[type=checkbox]");
			if(checkbox_checked.length == 0){
				answer = "";
			}else{
				var tm_answer = "";
				for(var l = 0 ; l < checkbox_checked.length; l++){
					tm_answer = tm_answer + $(checkbox_checked[l]).val();
				}
				answer = tm_answer;
			}
		} else{
			answer = $(currentQuestion).find("textarea").val();
		}
		return answer;
	},


	
	
	bindSwitchQuestion : function bindSwitchQuestion(){
		$(".question-body").hide();
		$($(".question-body")[0]).show();
        $($(".question-body")[0]).addClass("current_selected");

			
		$("#previous-q-btn").click(function(){
			var allQuestion = $(".question-body");
//			var currentQuestion  = $(".current_selected");
            var currentIndex = -1;
            var currentQuestion ;
            for( var i = 0 ;i<allQuestion.length ;i++){
                if( $(allQuestion[i]).hasClass("current_selected")){
                    currentIndex = i;
                    currentQuestion = $(allQuestion[i]);
                }
            }
			if(currentIndex == 0 || currentIndex == -1){
				return false;
			}else{
				currentQuestion.hide();
				$(allQuestion[currentIndex - 1]).show();
				$(".current_selected").removeClass("current_selected");
				$(allQuestion[currentIndex - 1]).addClass("current_selected");
			}
		});
		
		$("#next-q-btn").click(function(){
            var allQuestion = $(".question-body");
       //     var currentQuestion  = $(".current_selected");
			var currentQuestion;
            var currentIndex = -1;
            for( var i = 0 ;i<allQuestion.length ;i++){
                if( $(allQuestion[i]).hasClass("current_selected")){
                    currentIndex = i;
                    currentQuestion = $(allQuestion[i]);
                }
			}
			var allQuestionLength = allQuestion.length;
			if(currentIndex == allQuestionLength - 1 || currentIndex == -1){
				return false;
			}else{
                currentQuestion.hide();
                $(allQuestion[currentIndex + 1]).show();
                $(".current_selected").removeClass("current_selected");
                $(allQuestion[currentIndex + 1]).addClass("current_selected");
			}
		});
	},

};

var modal = {
	prepare : function prepare() {
		$(".content").append("<div id=\"loading-progress\" style=\"display:none;\"><div id=\"loading-content\"> <h2>正在提交您的答案</h2><img class=\"loading-gif\" src=\"resources/images/loading.gif\"/><div> </div>");

	},
	showProgress : function showProgress() {
		$("#loading-progress").show();
	},

	hideProgress : function hideProgress() {
		$("#loading-progress").hide();
	}
};
