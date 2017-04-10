$(function(){
	question_import.initial();
});

var question_import={
		initial : function initial() {
			this.questionDataProcess();
		},
		questionDataProcess : function questionDataProcess() {
            $("#question-import-button").click(function () {
					  var file = $("input[type = file]").val();
					  if (!file) {
						  $(".help-inline form-message").text("必须选择上传文件");
						  return false;
					  }
					  $.ajaxFileUpload({
							   url: "admin/question-import",
							   secureuri: false, //是否需要安全协议，一般设置为false
							   fileElementId: 'question-upload-file', //文件上传域的ID
							   dataType: 'json', //返回值类型 一般设置为json
							   success: function (msg) {
								   if (msg.status.code == 0) {
									   util.success("导入成功");
								   } else {
									   util.error("导入失败");
								   }
							   },
							   error: function (jqXHR, textStatus) {
								   util.error("操作失败请稍后尝试");
							   }
						   })
                   }
            )
        }
};
