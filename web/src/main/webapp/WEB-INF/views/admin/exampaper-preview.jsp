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
			}
		</style>
	</head>
	<body>
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
									
									<a href="javascript:void(0)" >${sessionScope.user.username}</a>
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
							<a href="admin/question-list/0-0-0-0-1"><i class="fa fa-edit"></i>试题管理</a>
						</li>

						<li class="active">
							<a href="admin/exampaper-list-0-1"><i class="fa fa-file-text-o"></i>试卷管理</a>
						</li>
						<li>
							<a href="admin/user-list/1"><i class="fa fa-user"></i>学生管理</a>
						</li>
						<li>
							<a href="admin/knowledge-list"><i class="fa fa-cloud"></i>课程管理</a>
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
								<a href="admin/exampaper-list-0-1"> <i class="fa fa-list-ul"></i> 试卷管理 </a>

							</li>
							<li>
								<a href="admin/exampaper-add"> <i class="fa fa-file-text-o"></i> 创建新试卷 </a>

							</li>
							<li class="active">
								<a> <i class="fa fa-bar-chart-o"></i> 预览试卷 </a>
							</li>

						</ul>

					</div>
					<div class="col-xs-9">
						<div class="page-header">
							<h1 style="margin-left: -15px;"><i class="fa fa-file-text"></i> 预览试卷 </h1>
						</div>
						<div class="page-content row">
							<div class="def-bk" id="bk-exampaper">

								<div class="expand-bk-content" id="bk-conent-exampaper">
									<div id="exampaper-header">
										<div id="exampaper-title">
											<i class="fa fa-send"></i>
											<span id="exampaper-title-name">${exampaper.name} </span>
											<span style="display:none;" id="exampaper-id">${exampaper.id}</span>
											
										</div>
										<div id="exampaper-desc-container">
											<div id="exampaper-desc" class="exampaper-filter">
												
											
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
						        <iframe  id="qt-selector-iframe" src="admin/questionfilterdialog-0-0-0-0-1.html" width="100%"></iframe>
						      </div>
						      <div class="modal-footer">
							        <button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
							        <button id="add-list-to-exampaper" type="button" class="btn btn-primary">添加选中</button>
							      </div>
						      
						    </div><!-- /.modal-content -->
						  </div><!-- /.modal-dialog -->
			</div>
			<div class="modal fade" id="question-point-modal">
						  <div class="modal-dialog">
						    <div class="modal-content">
						      <div class="modal-header">
						        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						        <h4 class="modal-title">修改分数</h4>
						      </div>
						      <div class="modal-body">
						        	<form>
										<div class="form-line qt-point-destination">
											<span class="form-label">分数：</span>
											<label></label>
											<input type="text" value=""/>
										</div>
										<div class="form-line">
											<button class="btn btn-sm btn-success" id="update-point-btn"><i class="fa fa-check"></i>仅修改第<span id="qt-point-target-index"></span>题</button>
											<button class="btn btn-sm btn-warning" id="update-point-type-btn"><i class="fa fa-random"></i>批量更新该题型</button>
										</div>
									</form> 

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
								Exam++ Copyright © <a href="javascript:void(0)" target="_blank">Exam++</a> - <a href="." target="_blank">主页</a> | <a href="javascript:void(0)" target="_blank">关于我们</a> | <a href="javascript:void(0)" target="_blank">FAQ</a> | <a href="javascript:void(0)" target="_blank">联系我们</a>
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
		<script type="text/javascript" src="resources/js/exampaper-preview.js"></script>
		
	</body>
</html>