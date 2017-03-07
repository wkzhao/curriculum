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
		$(".question-body").hide();
		$($(".question-body")[0]).show();
        $($(".question-body")[0]).addClass("currentSelected");
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
				examing.updateProgress();
			}

			currentQuestion.addClass("qt-finished");
			currentQuestion.find(".question-body input").attr("disabled","disabled");
			$(".answer-desc").show();

			var data = new Object();
			var points = currentQuestion.find(".practice-improve-points").text();
			data.uAnswer = answer+"#"+points;
			data.questionId = currentQuestion.find(".practice-improve").text();

//			modal.showProgress();
			$(this).attr("disabled","disabled");
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				async:false,
				type : "POST",
				url : "student/practice-improve",
				data : JSON.stringify(data),
				success:function (msg) {
                    if (msg.status.code == 0) {
                        $(window).unbind('beforeunload');
                        $(".answer-desc").show();
                        var question = msg.data;
                        $(".answer-desc-content")[0].innerHTML = "<p>"+question.analysis+"</p>";
                        $(".answer_value")[0].innerHTML = question.answer;
                    } else {
                        util.error(msg.status.result);
                    }
//				modal.hideProgress();
                    $("#submit-q-btn").removeAttr("disabled");
                },
                error:function(jqXHR, textStatus) {
                    util.error("系统繁忙请稍后尝试");
//				modal.hideProgress();
                    $("#submit-q-btn").removeAttr("disabled");
                }
			});
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
                $(".answer-desc").hide();
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
                $(".answer-desc").hide();
                currentQuestion.hide();
                $(allQuestion[currentIndex + 1]).show();
                $(".current_selected").removeClass("current_selected");
                $(allQuestion[currentIndex + 1]).addClass("current_selected");
            }
        });
    },

	updateProgress : function updateProgress(){
		var total = $("li.question").length;
		var finished = 100 * $("#question-navi-content .pressed").length;
		
		$(".h-progress span").attr("style","width:" + finished/total + "%;");
		
	},
};

var modal = {
	prepare : function prepare() {
		$(".content").append("<div id=\"loading-progress\" style=\"display:none;\"><div id=\"loading-content\"> <h2>正在读取您的记录</h2><img class=\"loading-gif\" src=\"resources/images/loading.gif\"/><div> </div>");

	},
	showProgress : function showProgress() {
		$("#loading-progress").show();
	},

	hideProgress : function hideProgress() {
		$("#loading-progress").hide();
	}
};
