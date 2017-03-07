$(function() {
	add_knowledge.initial();
});

var add_knowledge = {

	initial : function initial() {
		this.bindSubmitForm();
	},

	bindSubmitForm : function bindSubmitForm() {
		var form = $("form#field-add-form");

		form.submit(function() {
			var result = add_knowledge.verifyInput();
			if (result) {
				var data = new Object();
				data.name = $("#name").val();
				data.description = $("#memo").val();
				jQuery.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : "admin/add-knowledge",
					data : JSON.stringify(data),
					success : function(msg) {
						if (msg.status.code == 0) {
							util.success("添加成功");
							document.location.href = document
									.getElementsByTagName('base')[0].href
									+ "admin/knowledge-list";
						} else {
							util.error("添加失败");
						}
					}
				});
			}

			return false;
		});
	},

	verifyInput : function verifyInput() {
		$(".form-message").empty();
		var result = true;
		var check_n = this.checkName();
		// var check_e = this.checkEmail();
		var check_m = this.checkDescription();
		
		result = check_n && check_m;
		return result;
	},

	checkName : function checkName() {
		var f_name = $(".form-name input").val();
		if (f_name == "") {
			$(".form-name .form-message").text("题库名不能为空");
			return false;
		} else if (f_name.length > 40 || f_name.length < 2) {
			$(".form-name .form-message").text("请保持在2-40个字符以内");
			return false;
		} 
		return true;
	},
	
	checkDescription : function checkDescription() {
		var memo = $(".form-memo input").val();
		if (memo == "") {
			$(".form-memo .form-message").text("描述不能为空");
			return false;
		} else if (memo.length > 40 || memo.length < 5) {
			$(".form-memo .form-message").text("请保持在5-40个字符以内");
			return false;
		} 
		return true;
	}

};