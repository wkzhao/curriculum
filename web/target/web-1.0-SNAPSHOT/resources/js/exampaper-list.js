$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
		this.bindChangeProperty();
		this.bindUpdateExampaper();
		this.publishPaper();
		this.deletePaper();
	},

	bindChangeSearchParam : function bindChangeSearchParam(){
		$("#question-filter dl dd span").click(function(){
			if($(this).hasClass("label"))return false;


			var genrateParamOld = question_list.genrateParamOld();

			if($(this).parent().parent().attr("id") == "question-filter-pagetype" ){
				genrateParamOld.status = $(this).data("id");
				question_list.redirectUrl(genrateParamOld);

			}else return false;

		});

		$(".pagination li a").click(function(){
			var pageId = $(this).data("id");
			if(pageId == null || pageId == "")return false;
			var genrateParamOld = question_list.genrateParamOld();
			genrateParamOld.page = pageId;
			question_list.redirectUrl(genrateParamOld);

		});
	},


	publishPaper : function publishPaper(){
		var param = new Object();
		$(".publish-paper").click(function(){
			var paperId = $(this).parent().parent().find("input").val();
            param.pageId = paperId;
            param.status = 2;
			if (confirm("确定上线吗？上线后的试卷将可以进行考试")) {
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : "admin/changePaperStatus",
					data : JSON.stringify(param),
                    success : function(msg) {
                        if (msg.status.code == 0) {
                            util.success("上线成功");
                            window.location.reload();
                        } else {
                            util.error(msg.status.msg);
                        }

                    },
					error : function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					}
				});
			}
		});
	},

	deletePaper : function deletePaper(){
		$(".delete-paper").click(function(){
			var paperId = $(this).parent().parent().find("input").val();
			if (confirm("确定删除？")) {
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : "admin/paper-delete",
					data : JSON.stringify(paperId),
					success : function(msg) {
						if (msg.status.code == 0) {
							util.success("删除成功");
							window.location.reload();
						} else {
							util.error(msg.status.msg);
						}
	
					},
					error : function() {
						util.error("操作失败请稍后尝试");
					}
				});
			}
		});
	},

	genrateParamOld :function genrateParamOld(){
		var status = $("#question-filter-pagetype dd .label").data("id");
		var page = 1;

		var data = new Object();
		data.status= status;
		data.page = page;

		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.status;
		paramurl = paramurl + "-" + newparam.page;
		paramurl = paramurl;

		document.location.href = document.getElementsByTagName('base')[0].href
				+ 'admin/exampaper-list-' + paramurl;
	},

	bindChangeProperty : function bindChangeProperty(){
		$(".change-property").click(function(){
			$("#change-property-modal").modal({backdrop:true,keyboard:true});
			
			var tr = $(this).parent().parent();
			var paper_name = tr.find(".td-paper-name").text();
			var paper_duration  = tr.find(".td-paper-duration").text();
			var paper_id =  tr.find(".td-paper-id").text();
			$(".add-update-exampapername input").val(paper_name);
			$(".add-update-duration input").val(paper_duration);
			$("#add-update-exampaperid").text(paper_id);
		});
	},
	bindUpdateExampaper : function bindUpdateExampaper(){
		$("#update-exampaper-btn").click(function(){
			var verify_result = question_list.verifyInput();
			var paper_id = $("#add-update-exampaperid").text();
			if (verify_result) {

				var data = new Object();
				data.id = paper_id;
				data.name = $(".add-update-exampapername input").val();
				data.time = $(".add-update-duration input").val();
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : "admin/changePaperProperty",
					data : JSON.stringify(data),
					success : function(msg) {
						if (msg.status.code == 0) {
							util.success("修改成功", function(){
								window.location.reload();
							});
						} else {
							util.error("操作失败请稍后尝试");
						}

					},
					error : function() {
						util.error("操作失败请稍后尝试");
					}
				});
			}
		});
	},
	
	verifyInput : function verifyInput() {
		$(".form-message").empty();
		$(".has-error").removeClass("has-error");
		var result = true;
		var r_checkName = question_list.checkName();
		var r_checkDuration = question_list.checkDuration();
		result = r_checkName && r_checkDuration;
		return result;
	},
	checkDuration : function checkDuration() {
		var duration = $(".add-update-duration input").val();
		if (duration == "") {
			$(".add-update-duration .form-message").text("请输入考试时长（如：120）");
			return false;
		} else if (isNaN(duration)) {
			$(".add-update-duration .form-message").text("请输入数字");
			return false;
		} else if (!(duration > 30 && duration < 241)) { 
			$(".add-update-duration .form-message").text("数字范围无效，考试的时长必须设置在30到240的范围内");
			return false;
		} else {
			return true;
		}
	},
	
	checkName : function checkName() {
		var name = $(".add-update-exampapername input").val();
		if (name == "") {
			$(".add-update-exampapername .form-message").text("请输入试卷名称");
			$(".add-update-exampapername input").focus();
			$(".add-update-exampapername input").addClass("has-error");
			return false;
		} else if (name.length > 10) {
			$(".add-update-exampapername .form-message").text("内容过长，请保持在10个字符以内");
			$(".add-update-exampapername input").focus();
			$(".add-update-exampapername input").addClass("has-error");
			return false;
		} else {
			return true;
		}
	},
};