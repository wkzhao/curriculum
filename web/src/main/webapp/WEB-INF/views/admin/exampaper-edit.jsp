<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- <%@taglib uri="spring.tld" prefix="spring"%> --%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Exam++</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style>
			#add-more-qt-to-paper{
				cursor: pointer;
				color: #1ba1e2;
			}
			#add-more-qt-to-paper:hover{
				color:#ff7f74;
			}
			#add-more-qt-to-paper i{
				color: #47a447;
				cursor: pointer;
				margin-right:5px;	
			}
			
			#qt-selector-iframe{
				border:none;
				height:600px;
			}
			.tmp-ques-remove{
				margin-left:10px;
			}
			
			.question-point{
				padding:0 8px;
				margin:0 2px;
				border-bottom: 1px solid;
				cursor:pointer;
			}
			.question-point:hover{
				color:#ff7f74;
			}
		
		</style>
	</head>
	<body>
	    <input type="hidden" id="edit-paper-id" value="${paperId}">
		<header>
			<div class="container">
				<div class="row">
					<div class="col-xs-5">
						<div class="logo">
							<h1><a href="#">网站管理系统</a></h1>
							<div class="hmeta">
								专注互联网在线考试解决方案
							</div>
						</div>
					</div>
					<div class="col-xs-7" id="login-info">
						<c:choose>
							<c:when test="${not empty sessionScope.user.username}">
								<div id="login-info-user">
									
									<a href="user-detail/${sessionScope.user.username}" id="system-info-account" target="_blank">${sessionScope.user.username}</a>
									<span>|</span>
									<a href="user-logout"><i class="fa fa-sign-out"></i> 退出</a>
								</div>
							</c:when>
							<c:otherwise>
								<a class="btn btn-primary" href="user-register">用户注册</a>
								<a class="btn btn-success" href="user-login">登录</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</header>
		<!-- Navigation bar starts -->

		<div class="navbar bs-docs-nav" role="banner">
			<div class="container">
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
					<ul class="nav navbar-nav">
						<li>
							<a href="#"><i class="fa fa-home"></i>网站首页</a>
						</li>
						<li>
							<a href="admin/question-list/0-0-0-0-1"><i class="fa fa-edit"></i>试题管理</a>
						</li>

						<li class="active">
							<a href="admin/exampaper-list"><i class="fa fa-file-text-o"></i>试卷管理</a>
						</li>
						<li>
							<a href="admin/user-list/1"><i class="fa fa-user"></i>会员管理</a>
						</li>
						<li>
							<a href="admin/knowledge-list"><i class="fa fa-cloud"></i>题库管理</a>
						</li>
						<li>
							<a href="admin/sys-backup"><i class="fa fa-cogs"></i>网站设置</a>
						</li>
					</ul>
				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-3">
						<ul class="nav default-sidenav">
							<li>
								<a href="admin/exampaper-list"> <i class="fa fa-list-ul"></i> 试卷管理 </a>

							</li>
							<li>
								<a href="admin/exampaper-add"> <i class="fa fa-file-text-o"></i> 创建新试卷 </a>

							</li>
							<li class="active">
								<a> <i class="fa fa-bar-chart-o"></i> 修改试卷 </a>
							</li>

						</ul>

					</div>
					<div class="col-xs-9">
						<div class="page-header">
							<h1 style="margin-left: -15px;"><i class="fa fa-file-text"></i> 修改试卷 </h1>
						</div>
						<div class="page-content row">
							<div class="def-bk" id="bk-exampaper">

								<div class="expand-bk-content" id="bk-conent-exampaper">
									<div id="exampaper-header">
										<div id="exampaper-title">
											<i class="fa fa-send"></i>
											<span id="exampaper-title-name"> ${exampaper.name} </span>
											<span style="display:none;" id="exampaper-id">${exampaper.id}</span>
											
										</div>
										<div id="exampaper-desc-container">
											<div id="exampaper-desc" class="exampaper-filter">
												
											
											</div>
											<div style="margin-top: 5px;">
												<span>试卷总分：</span><span id="exampaper-total-point" style="margin-right:20px;"></span>
												<span id="add-more-qt-to-paper"><i class="small-icon fa fa-plus-square" title="添加选项"></i><span>增加更多题目</span></span>
											</div>
										</div>
										
										
									</div>
									<ul id="exampaper-body" style="padding:0px;">
										<c:forEach items="${singleQuestions}" var="item" varStatus="status">
											<c:if test="${status.index == 0}">
												<span>[单选题]</span>
											</c:if>
										    <form class="question-body">
												<span class="question-id" style="display:none;">${item.id}</span>
												<p class="question-body-text">${item.questionContent.title}
													<a href="javascript:void (0)" name="${item.id}" class="tmp-ques-remove" onclick="removeQuestion(this)">删除此题</a>
												</p>
												<ul class="question-opt-list">
													<li class="question-list-item">
														<c:forEach items="${item.questionContent.choiceList}" var="choice">
														  <input type="radio" value="${choice.key}" name="question-radio1" class="question-input">
															<span class="question-li-text">${choice.key}:${choice.value}</span>
														</c:forEach>
													</li>
												</ul>
											</form>
										</c:forEach>
										<c:forEach items="${multiQuestions}" var="item" varStatus="status">
											<c:if test="${status.index == 0}">
												<span>[多选题]</span>
											</c:if>
											<form class="question-body">
												<span class="question-id" style="display:none;">${item.id}</span>
												<p class="question-body-text">${item.questionContent.title}
													<a href="javascript:void (0)" name="${item.id}" class="tmp-ques-remove" onclick="removeQuestion(this)">删除此题</a>
												</p>
												<ul class="question-opt-list">
													<li class="question-list-item">
														<c:forEach items="${item.questionContent.choiceList}" var="choice">
															<input type="checkbox" value="${choice.key}" name="question-radio1" class="question-input">
															<span class="question-li-text">${choice.key}:${choice.value}</span>
														</c:forEach>
													</li>
												</ul>
											</form>
										</c:forEach>
										<c:forEach items="${judgeQuestions}" var="item" varStatus="status">
											<c:if test="${status.index == 0}">
												<span>[判断题]</span>
											</c:if>
											<form class="question-body">
												<span class="question-id" style="display:none;">${item.id}</span>
												<p class="question-body-text">${item.questionContent.title}
													<a href="javascript:void (0)" name="${item.id}" class="tmp-ques-remove" onclick="removeQuestion(this)">删除此题</a>
												</p>
												<ul class="question-opt-list">
													<li class="question-list-item">
														<input type="radio" value="T" name="question-radio2" class="question-input">
														<span class="question-li-text">正确</span>
													</li>
													<li class="question-list-item">
														<input type="radio" value="F" name="question-radio2" class="question-input">
														<span class="question-li-text">错误</span>
													</li>
												</ul>
											</form>
										</c:forEach>
										<c:forEach items="${otherQuestions}" var="item" varStatus="status">
											<c:if test="${status.index == 0}">
												<span>[主观题]</span>
											</c:if>
											<form class="question-body">
												<span class="question-id" style="display:none;">${item.id}</span>
												<p class="question-body-text">${item.questionContent.title}
													<a href="javascript:void (0)" name="${item.id}" class="tmp-ques-remove" onclick="removeQuestion(this)">删除此题</a>
												</p>
												<textarea class="question-textarea"></textarea>
											</form>
										</c:forEach>
									</ul>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal fade" id="question-selector-modal">
						  <div class="modal-dialog modal-lg">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						        <h4 class="modal-title">选择试题添加到试卷中</h4>
						      </div>
						      <div class="modal-body">
						        <iframe  id="qt-selector-iframe" src="admin/question-list/0-0-0-1-1" width="100%"></iframe>
						      </div>
						      <div class="modal-footer">
							        <button type="button" class="btn btn-default" data-dismiss="modal" id = "edit-paper-close-window" onclick="window.location.reload()">关闭窗口</button>
							        <button id="add-list-to-exampaper" type="button" class="btn btn-primary">添加选中</button>
							      </div>
						      
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
			</div>
			
		</div>

		<footer>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="copy">
							<p>
								Exam++ Copyright © <a href="http://www.examxx.net/" target="_blank">Exam++</a> - <a href="." target="_blank">主页</a> | <a href="http://www.examxx.net/" target="_blank">关于我们</a> | <a href="http://www.examxx.net/" target="_blank">FAQ</a> | <a href="http://www.examxx.net/" target="_blank">联系我们</a>
							</p>
						</div>
					</div>
				</div>

			</div>

		</footer>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript" src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/exampaper-edit.js"></script>
	    <script type="text/javascript">
            function removeQuestion(obj){
				var data = new Object();
				data.paperId = $("#edit-paper-id").val();
				data.questionId = obj.name;
				$.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : 'admin/delete-paper-question/',
						data : JSON.stringify(data),
						success:function(msg){
							if(msg.status.code == 0){
								util.success("删除成功");
								window.location.reload();
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
					}
				)
            }
		</script>
	</body>
</html>