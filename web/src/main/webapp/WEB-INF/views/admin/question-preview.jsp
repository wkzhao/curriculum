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
		<title>试题预览</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/exam.css" rel="stylesheet" type="text/css">
		<link href="resources/css/style.css" rel="stylesheet">
		
	</head>
	<body>
	    <input type="hidden" value="${question.id}" id="delete-question-id">
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
						<li class="active">
							<a href="admin/question-list/0-0-0-0-1"><i class="fa fa-edit"></i>试题管理</a>
						</li>

						<li>
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
								<a href="admin/question-list/0-0-0-0-1"> <i class="fa fa-list-ul"></i> 试题管理 </a>

							</li>
							<li class="active">
								<a> <i class="fa fa-file-text"></i> 试题预览 </a>

							</li>
							<li>
								<a href="admin/question-add"> <i class="fa fa-pencil-square-o"></i> 添加试题 </a>

							</li>
							<li>
								<a href="admin/question-import"> <i class="fa fa-cloud-upload"></i> 导入试题 </a>

							</li>
						</ul>

					</div>
					<div class="col-xs-9">
						<div class="page-header">
							<h1 style="margin-left: -15px;"><i class="fa fa-file-text"></i> 试题预览 </h1>
						</div>
						<div class="page-content row">
							<div class="def-bk" id="bk-exampaper">

								<div class="expand-bk-content" id="bk-conent-exampaper">
									<ul id="exampaper-body" style="padding:0px;">
										<div class="answer-desc-detail">
											<span>[${question.questionTypeName}]</span>
											<p class="question-body-text" >${question.questionContent.title}</p>
											<c:choose>
												<c:when test="${ question.questionTypeId == 1}">
													<ul class="question-opt-list">
														<li class="question-list-item">
															<c:forEach items="${question.questionContent.choiceList}" var="choice">
																<input type="radio" value="${choice.key}" name="question-radio1" class="question-input">
																<span class="question-li-text">${choice.key}:${choice.value}</span>
															</c:forEach>
														</li>
													</ul>
												</c:when>
												<c:when test="${ question.questionTypeId == 2}">
													<ul class="question-opt-list">
														<li class="question-list-item">
															<c:forEach items="${question.questionContent.choiceList}" var="choice">
																<input type="checkbox" value="${choice.key}" name="question-radio1" class="question-input">
																<span class="question-li-text">${choice.key}:${choice.value}</span>
															</c:forEach>
														</li>
													</ul>
												</c:when>
												<c:when test="${question.questionTypeId == 3}">
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
												</c:when>
												<c:otherwise>
													<textarea class="question-textarea"></textarea>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="answer-desc-detail">
											<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 添加人</span></label>
											<div>
												${question.creator }
											</div>
										</div>
										<div class="answer-desc-detail">
											<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 考点</span></label>
											<div>
												${question.pointsName }
											</div>
										</div>
										<div class="answer-desc-detail">
											<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 答案</span></label>
											<div>
												${question.answer }
											</div>
										</div>
										<div class="answer-desc-detail">
											<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 解析</span></label>
											<div class="answer-desc-content">
												${question.analysis }
											</div>
										</div>
									</ul>
									<div id="exampaper-footer" style="height: 187px;text-align:center;margin-top:40px;">
										<c:choose>
											<c:when test="${question.creator == sessionScope.user.username}">
												<button class="btn btn-danger" id="delete-question-btn"><i class="fa fa-trash-o"></i> 删除该题</button>
												<button class="btn btn-info" onclick="javascript:window.close();"><i class="fa fa-times"></i> 关闭页面</button>
											</c:when>
											<c:otherwise>
												<button class="btn btn-danger" id="delete-question-btn" disabled="disabled" title="您只能删除你自己添加的题"><i class="fa fa-trash-o"></i> 删除该题</button>
												<button class="btn btn-info" onclick="javascript:window.close();"><i class="fa fa-times"></i> 关闭页面</button>
												<p>您只能删除你自己添加的题</p>
											</c:otherwise>
										</c:choose>
										
										
									</div>
								</div>
								

							</div>
						</div>
					</div>
				</div>
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
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/jquery-ui-1.9.2.custom.min.js"></script>
		
		<script type="text/javascript" src="resources/js/uploadify/jquery.uploadify3.1Fixed.js"></script>
		<script type="text/javascript" src="resources/js/question-upload-img.js"></script>
		<script type="text/javascript" src="resources/js/field-2-point.js"></script>
		<script type="text/javascript" src="resources/js/question-add.js"></script>
		
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script>
								$(function(){
											$("#delete-question-btn").click(function(){
                                                alert("ggg");
                                                alert($("#delete-question-id").val());
												var result = confirm("确定删除吗？删除后将不可恢复");
												if(result == true){
													jQuery.ajax({
														headers : {
															'Accept' : 'application/json',
															'Content-Type' : 'application/json'
														},
		  												type : "POST",
														url : 'admin/delete-question/' + $("#delete-question-id").val(),
                                                        success:function(msg) {
                                                            var result = eval('(' + msg + ')');
                                                            if(result.status.code == 0){
                                                                alert("删除成功");
                                                                window.location.href="admin/question-list/0-0-0-0-1";
                                                            }else{
                                                                alert(result.status.msg);
                                                                return ;
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
											
											
										
										
									});
		</script>
	</body>
</html>