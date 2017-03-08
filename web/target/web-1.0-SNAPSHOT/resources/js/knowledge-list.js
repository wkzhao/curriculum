$(function() {

	question_list.initial();
});

var question_list = {
	initial : function initial() {
		this.bindChangeSearchParam();
		this.bindChangeProperty();
		this.bindUpdateKnowledge();
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

    bindChangeProperty : function bindChangeProperty(){
        $(".change-property").click(function(){
            $("#change-knowledge-property-modal").modal({backdrop:true,keyboard:true});

            var tr = $(this).parent().parent();
            var name = tr.find(".td-knowledge-name").text();
            var description  = tr.find(".td-knowledge-description").text();
            var id =  tr.find(".td-knowledge-id").text();
            $(".add-update-knowledgeName input").val(name);
            $(".add-update-knowledgeDescription input").val(description);
            $("#add-update-knowledgeId").text(id);
        });
    },

    bindUpdateKnowledge : function bindUpdateKnowledge(){
        $("#update-knowledge-btn").click(function(){
            var verify_result = question_list.verifyInput();
            var id = $("#add-update-knowledgeId").text();
            if (verify_result) {

                var data = new Object();
                data.id = id;
                data.name = $(".add-update-knowledgeName input").val();
                data.description = $(".add-update-knowledgeDescription input").val();
                $.ajax({
                    headers : {
                        'Accept' : 'application/json',
                        'Content-Type' : 'application/json'
                    },
                    type : "POST",
                    url : "admin/changeKnowledgeProperty",
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
        var name = $(".add-update-knowledgeName input").val();
        if (name == "") {
            $(".add-update-knowledgeName .form-message").text("请输入知识库名称");
            $(".add-update-knowledgeName input").focus();
            $(".add-update-knowledgeName input").addClass("has-error");
            return false;
        } else if (name.length > 10) {
            $(".add-update-knowledgeName .form-message").text("内容过长，请保持在10个字符以内");
            $(".add-update-knowledgeName input").focus();
            $(".add-update-knowledgeName input").addClass("has-error");
            return false;
        } else {
            return true;
        }
    },

    checkDescription : function checkDescription() {
        var description = $(".add-update-knowledgeDescription input").val();
        if (name.length > 10) {
            $(".add-update-knowledgeDescription .form-message").text("内容过长，请保持在10个字符以内");
            $(".add-update-knowledgeDescription input").focus();
            $(".add-update-knowledgeDescription input").addClass("has-error");
            return false;
        } else {
            return true;
        }
    },
};