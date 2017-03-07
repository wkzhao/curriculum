$(function(){
		var selection = $("#aq-course1").find("select");
		// alert("111");
		var point_list = $("#aq-course2").find("select");
		selection.change(function(){
			$.ajax({
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				type : "POST",
				url : "admin/get-knowledge-point/" + selection.val(),
				success : function(message,tst,jqXHR) {
					if(!util.checkSessionOut(jqXHR))return false;
					if (message.status.code == 0) {
						point_list.empty();
						for( var i = 0 ; i < message.data.length ; i++){
                            point_list.append("<option value=\"" + message.data[i].id + "\">" + message.data[i].name + "</option>");
						}
					} else {
						util.error("操作失败请稍后尝试");
					}
				},
				error : function(xhr) {
					util.error("操作失败请稍后尝试");
				}
			});
		});
});

