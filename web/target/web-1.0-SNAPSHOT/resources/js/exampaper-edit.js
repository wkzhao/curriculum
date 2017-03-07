$(function() {
	examing.initial();
});

var examing = {
		
		initial : function initial() {
			this.bindNaviBehavior();
			this.bindOpenModal();
			this.bindAddQustionToPaper();

		},
		bindNaviBehavior : function bindNaviBehavior() {

			var nav = $("#question-navi");
			var naviheight = $("#question-navi").height() - 33;
		

			$("#exampaper-footer").height($("#question-navi").height());

			nav.css({
				position : 'fixed',
				bottom : '0px',
				"z-index" : '1'	
			});

			

			$("#question-navi-controller").click(function() {
				if($("#question-navi-content").is(":visible") ){
					$("#question-navi-content").hide();
				}else{
					$("#question-navi-content").show();
					
				}
			});

		},

		bindOpenModal : function bindOpenModal(){
				$("#add-more-qt-to-paper").click(function() {
				
					$("#question-selector-modal").modal({backdrop:true,keyboard:true});
				
				});
		},
		
		composeEntity : function composeEntity(){
			var forms = $(".question");
			var map = new Object();
			forms.each(function(){
				var question_point = $(this).find("span.question-point").text();
				var question_id = $(this).find("span.question-id").text();
				map[question_id] = question_point;
			});
			return map;
		},

		bindAddQustionToPaper : function bindAddQustionToPaper(){
			$("button#add-list-to-exampaper").click(function() {
				var values = new Array();
				var papaerId = $("#edit-paper-id").val();
				var checkboxs = $("#qt-selector-iframe").contents().find("table input:checked");
				$.each(checkboxs, function() {
					var id = $(this).val();
					values.push(id);
				});
				if (checkboxs.length == 0) {
					util.notify("请选择需要添加的试题!");
				}else{
					$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : 'admin/add-paper-question/'+papaerId,
						data : JSON.stringify(values),
						success:function(msg){
							if(msg.status.code == 0){
								util.success("添加成功");
							}else{
								util.error("操作失败")
							}
						},
                        error: function(XMLHttpRequest, textStatus, errorThrown) {
                            alert(XMLHttpRequest.status);
                            alert(XMLHttpRequest.readyState);
                            alert(textStatus);
                            return ;
                        }
					});
				}
			});
		},
};













