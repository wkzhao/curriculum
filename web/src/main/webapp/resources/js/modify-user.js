$(function() {
	create_account.initial();
});

var create_account = {

	initial : function initial() {
		this.bindSubmitForm();
	},

	bindSubmitForm : function bindSubmitForm() {
		var form = $("form#form-change-password");

		form
				.submit(function() {
					var result = create_account.verifyInput();
					if (result) {
						var data = new Object();
						data.email = $("#email").val();
						data.tel = $("#phone").val();
						jQuery
								.ajax({
									headers : {
										'Accept' : 'application/json',
										'Content-Type' : 'application/json'
									},
									type : "POST",
									url : "student/changeUserInfo",
									data : JSON.stringify(data),
									success : function(msg) {
										if (msg.status.code == 0) {
											util.success("修改成功", function() {
												document.location.href = document.getElementsByTagName('base')[0].href + 'home';
											});
										} else {
											util.error("操作失败请稍后尝试");
										}
									},
									error : function(jqXHR, textStatus) {
										util.error("操作失败请稍后尝试");
									}
								});
					}

					return false;
				});
	},

	verifyInput : function verifyInput() {
		$(".form-message").empty();
		var result = true;
		var check_e = this.checkEmail();
		result = check_e
		return result;
	},


	checkEmail : function checkEmail() {
		var email = $(".form-email input").val();
		if (email == "") {
			$(".form-email .form-message").text("邮箱不能为空");
			return false;
		} else if (email.length > 40 || email.length < 5) {
			$(".form-email .form-message").text("请保持在5-40个字符以内");
			return false;
		} else {
			var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		   if(re.test(email)){
			   return true;
		   }else{
			   $(".form-email .form-message").text("无效的邮箱");
				return false;
		   }
			
		}
		return true;
	},


};