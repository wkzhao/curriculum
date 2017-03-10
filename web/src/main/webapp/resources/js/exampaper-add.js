$(function() {
	exampaper_add.initial();

});
exampaper_add = {
		initial : function initial() {
			this.bindAddPoint();
			this.bindChangeCreatExamPaperType();
			this.bindSubmit();
		},
		bindSubmit : function bindSubmit() {
			$("#form-exampaper-add").submit(function() {
				

				var verify_result = exampaper_add.verifyInput();
				
				if (verify_result) {
					var question_entity = exampaper_add.composeEntity();
					$(".df-submit").attr("disabled","disabled");
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : "admin/exampaper-add",
						data : JSON.stringify(question_entity),
						success : function(msg) {
							if (msg.status.code == 0) {
								util.success("添加成功");
								document.location.href = document.getElementsByTagName('base')[0].href + 'admin/exampaper-edit/' + msg.data;
							} else {
								alert("添加失败");
								$(".df-submit").removeAttr("disabled");
							}

						},
						error : function(jqXHR, textStatus) {
							util.error("操作失败请稍后尝试");
							$(".df-submit").removeAttr("disabled");
						}
					});
				}

				return false;
			});
		},
		bindAddPoint : function bindAddPoint() {
			$("#add-point-btn").click(function() {
				var field = $("#field-select > option:selected");
				var point = $("#point-from-select > option:selected");
				if (field.length == 0 || point.length == 0) {
					util.error("请选择需要添加的知识点");
					return false;
				}

				var html = "<option value=\"" + point.attr("value") + "\">" + field.text() + " > " + point.text() + "</option>";
				var p = point.attr("value");
				if (!exampaper_add.checkPointDuplicate(p)) {
					util.error("不能重复添加");
					return false;
				}

				$("#point-to-select").append(html);
				return false;
			});

			$("#del-point-btn").click(function() {
				$("#point-to-select > option:selected").remove();
				return false;
			});

			$("#remove-all-point-btn").click(function() {
				$("#point-to-select").empty();
				return false;
			});
		},
		checkPointDuplicate : function checkPointDuplicate(p) {
			var points = $("#point-to-select option");
			for (var i = 0; i < points.length; i++) {
				var point = $(points[i]).attr("value");
				if (point == p)
					return false;
			}

			return true;
		},

		/**
		 *组卷方式切换
		 */
		bindChangeCreatExamPaperType:function bindChangeCreatExamPaperType() {
			
			$(".add-update-exampaper-creat-type select").change(function(){
				if (1 == $(this).val()) {
					$(".add-update-types").hide();
					$(".add-update-exampaper-scope").hide();
					$(".add-total-point").hide();
				} else {
					$(".add-update-types").show();
					$(".add-update-exampaper-scope").show();
					$(".add-total-point").show();
				}
				
			});
			
		},
		/**
		 * 检查输入合法性
		 */
		verifyInput : function verifyInput() {
			$(".form-message").empty();
			$(".has-error").removeClass("has-error");
			var result = true;
			var r_checkName = exampaper_add.checkName();
			var r_checkDuration = exampaper_add.checkDuration();
			var r_checkKnowledge = exampaper_add.checkKnowledge();
			
			if($(".add-update-exampaper-creat-type select").val() == 1){
				result = r_checkName && r_checkDuration && r_checkKnowledge;
			}else{
				result = r_checkName  && r_checkDuration;
			}

            result = r_checkName  && r_checkDuration; //
			
			return result;
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

		
		checkKnowledge : function checkKnowledge(){
			var result = true;

			if ($("#point-to-select option").length == 0) {
				$(".add-update-exampaper-scope .form-message").text("至少选择一个知识点");
				$("#point-to-select").addClass("has-error");
				result = false;
			} else if ($("#point-to-select option").length > 10) {
				$(".add-update-exampaper-scope .form-message").text("知识点数量不应该超过10个");
				$("#point-to-select").addClass("has-error");
				result = false;
			}

			return result;
			
		},
		
		composeEntity : function composeEntity(){
			var paperParam = new Object();
			paperParam.name = $(".add-update-exampapername input").val();
			paperParam.time = $(".add-update-duration input").val();
			paperParam.paperTypeId = $(".add-update-exampaper-creat-type select").val();
			var qt = $(".add-ques-type");
			var amountMap = new Object();
			var knowledgePoints = new Array();
			for(var i = 0 ; i< qt.length;i++){
				var itemamount = parseInt($(qt[i]).find(".add-ques-amount").val());
				var itemsid = $(qt[i]).find(".ques-id").val();
				if(isNaN(itemamount)){
					continue;
				}else{
					amountMap[itemsid] = itemamount;
				}
			}
			var points = $("#point-to-select option");
			for( var i = 0 ; i < points.length ; i++) {
                knowledgePoints[i] = $(points[i]).val();
            }
            paperParam.knowledgePoints = knowledgePoints;
            paperParam.questionTypeNumMap = amountMap;
			return paperParam;
		},
		
		getType : function getType(input) {
		    var m = (/[\d]+(\.[\d]+)?/).exec(input);
		    if (m) {
		       // Check if there is a decimal place
		       if (m[1]) { return 'float'; }
		       else { return 'int'; }          
		    }
		    return 'string';
		}
		

};














