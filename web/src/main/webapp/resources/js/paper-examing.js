$(function() {
	modal.prepare();
	examing.initial();

});

var examing = {
	initial : function initial() {
		this.startTimer();
		this.bindSubmit();
	},

	securityHandler : function securityHandler() {
		// 右键禁用
		if (document.addEventListener) {
			document.addEventListener("contextmenu", function(e) {
				 e.preventDefault();
			 }, false);
		} else {
			document.attachEvent("contextmenu", function(e) {
				 e.preventDefault();
			 });
		}

		$(window).bind('beforeunload', function() {
			return "考试正在进行中...";
		});
	},



	/**
	 * 开始倒计时
	 */
	startTimer : function startTimer() {
        var timestamp ;
		var int = setInterval(function() {
            if( examing.getCookie("examTimeStamp") == "" ){
                timestamp = parseInt($("#exam-timestamp").text());
            }else{
                timestamp = parseInt(examing.getCookie("examTimeStamp"));
            }
			$("#exam-timestamp").text(timestamp);
			$("#exam-clock").text(examing.toHHMMSS(timestamp));
			if(timestamp < 600){
				var exam_clock = $("#question-time");
				exam_clock.removeClass("question-time-normal");
				exam_clock.addClass("question-time-warning");
			}

            if(timestamp--){
                examing.setCookie("examTimeStamp",timestamp);
            }else {
                examing.delCookie("examTimeStamp");
                examing.examTimeOut(int);
            }
		}, 1000);
	},

   setCookie: function setCookie(name,value) {
		var Days = 30;
		var exp = new Date();
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
	},

   getCookie : function getCookie(cookieName) {
		var strCookie = document.cookie;
		var arrCookie = strCookie.split("; ");
		for(var i = 0; i < arrCookie.length; i++){
			var arr = arrCookie[i].split("=");
			if(cookieName == arr[0]){
				return arr[1];
			}
		}
		return "";
   },

   delCookie: function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
   },

/**
	 * 考试时间到
	 * @param int
	 */
	examTimeOut : function examTimeOut (int){
		clearInterval(int); 
		examing.finishExam();
	},

	/**
	 * 时间formater
	 *
	 * @param timestamp
	 * @returns {String}
	 */
	toHHMMSS : function toHHMMSS(timestamp) {
		var sec_num = parseInt(timestamp, 10);
		var hours = Math.floor(sec_num / 3600);
		var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
		var seconds = sec_num - (hours * 3600) - (minutes * 60);

		if (hours < 10) {
			hours = "0" + hours;
		}
		if (minutes < 10) {
			minutes = "0" + minutes;
		}
		if (seconds < 10) {
			seconds = "0" + seconds;
		}
		var time = hours + ':' + minutes + ':' + seconds;
		return time;
	},


	bindSubmit : function bindSubmit() {
		$("#question-submit button").click(function() {
			if (confirm("确认交卷吗？")) {
				examing.finishExam();
			}
		});
	},

	finishExam : function finishExam() {
		modal.showProgress();
		var data = new Object();
		var exam_history_id = $("#hist-id").val();
		data.answer = examing.generateAnswer();
		data.paperId = $("#examing-paper-id").val();
		var timeStr = $("#exam-clock").text();
		var time = timeStr.split(":");
		var hours = parseInt(time[0]);
		var minutes = parseInt(time[1]);
		var seconds = parseInt(time[2]);
		data.duration = hours * 3600 + minutes * 60 + seconds;
		$("#question-submit button").attr("disabled", "disabled");
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "student/exam-finished",
			data : JSON.stringify(data)
		});

		request.done(function(msg) {
			if (msg.status.code  == 0) {
				$(window).unbind('beforeunload');
				util.success("交卷成功！", function() {
					window.location.replace(document.getElementsByTagName('base')[0].href + 'home');
				});
			} else {
				util.error("交卷失败");
				$("#question-submit button").removeAttr("disabled");
			}
			modal.hideProgress();
		});
		request.fail(function(jqXHR, textStatus) {
			alert("系统繁忙请稍后尝试");
			$("#question-submit button").removeAttr("disabled");
			modal.hideProgress();
		});
	},

    generateAnswer:function generateAnswer() {
        var questions = $(".question-body");
        var question;
        var answer;
        var answers = {};
        for( i = 0 ;i<questions.length; i++){
        	answer = "";
        	question = questions[i];
        	if(question.name == 1 || question.name == 3){
                var radio_checked = $(questions[i]).find("input[type=radio]:checked");
                var radio_all = $(questions[i]).find("input[type=radio]");
                if (radio_checked.length == 0) {
                    answer = "";
                } else {
                    answer = radio_checked.val();
                }
			}else if(question.name == 2){
                var checkbox_checked = $(questions[i]).find("input[type=checkbox]:checked");
                var checkbox_all = $(questions[i]).find("input[type=checkbox]");
                if (checkbox_checked.length == 0) {
                   answer = "";
                } else {
                    var tm_answer = "";
                    for (var l = 0; l < checkbox_checked.length; l++) {
                    	var t = checkbox_checked[l];
                        tm_answer = tm_answer + $(checkbox_checked[l]).val();
                    }
                    answer = tm_answer;
                }
			}else {
                answer = $(questions[i]).find("textarea").val();
			}
            var questionsId = $(questions[i]).find(".question-id")[0].innerText;
            answers[questionsId] = answer+"#"+$(questions[i]).find(".question-id")[1].innerText;
        }
        return answers;
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
