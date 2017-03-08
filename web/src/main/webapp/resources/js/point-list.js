$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
		this.bindChangeProperty();
		this.bindUpdateKnowledgePoint();
	},
	
	bindChangeSearchParam : function bindChangeSearchParam(){
		$("#question-filter dl dd span").click(function(){
			if($(this).hasClass("label"))return false;
			var genrateParamOld = question_list.genrateParamOld();
			if($(this).parent().parent().attr("id") == "question-filter-field" ){
				genrateParamOld.field = $(this).data("id");
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
		
		var field = $("#question-filter-field dd .label").data("id");
		var page = 1;
		var data = new Object();
		data.field = field;
		data.page = page;
		
		return data;
	},

	redirectUrl : function(newparam) {
		var paramurl = newparam.field;
		paramurl = paramurl + "-" + newparam.page;

		document.location.href = document.getElementsByTagName('base')[0].href
				+ 'admin/point-list-' + paramurl;
	},

    bindChangeProperty : function bindChangeProperty(){
        $(".change-property").click(function(){
            $("#change-knowledgePoint-property-modal").modal({backdrop:true,keyboard:true});

            var tr = $(this).parent().parent();
            var name = tr.find(".td-knowledgePoint-name").text();
            var description  = tr.find(".td-knowledgePoint-description").text();
            var id =  tr.find(".td-knowledgePoint-id").text();
            var knowledgeId = tr.find(".td-knowledgePoint-knowledge").data("id");
            $(".add-update-knowledgePointName input").val(name);
            $(".add-update-knowledgePointDescription input").val(description);
            $("#add-update-knowledgePointId").text(id);
            $(".add-update-knowledgePointKnowledgeId select").val(knowledgeId);
        });
    },

    bindUpdateKnowledgePoint : function bindUpdateKnowledgePoint(){
        $("#update-knowledgePoint-btn").click(function(){
            var verify_result = question_list.verifyInput();
            var id = $("#add-update-knowledgePointId").text();
            if (verify_result) {

                var data = new Object();
                data.id = id;
                data.name = $(".add-update-knowledgePointName input").val();
                data.description = $(".add-update-knowledgePointDescription input").val();
                data.knowledgeId = $(".add-update-knowledgePointKnowledgeId select").val();
                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    type : "POST",
                    url : "admin/changeKnowledgePointProperty",
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
        var r_checkDescription = question_list.checkDescription();
        result = r_checkName && r_checkDescription;
        return result;
    },

    checkName : function checkName() {
        var name = $(".add-update-knowledgePointName input").val();
        if (name == "") {
            $(".add-update-knowledgePointName .form-message").text("请输入知识点名称");
            $(".add-update-knowledgePointName input").focus();
            $(".add-update-knowledgePointName input").addClass("has-error");
            return false;
        } else if (name.length > 10) {
            $(".add-update-knowledgePointName .form-message").text("内容过长，请保持在10个字符以内");
            $(".add-update-knowledgePointName input").focus();
            $(".add-update-knowledgePointName input").addClass("has-error");
            return false;
        } else {
            return true;
        }
    },

    checkDescription : function checkDescription() {
        var description = $(".add-update-knowledgePointDescription input").val();
        if (name.length > 10) {
            $(".add-update-knowledgePointDescription .form-message").text("内容过长，请保持在10个字符以内");
            $(".add-update-knowledgePointDescription input").focus();
            $(".add-update-knowledgePointDescription input").addClass("has-error");
            return false;
        } else {
            return true;
        }
    },
};