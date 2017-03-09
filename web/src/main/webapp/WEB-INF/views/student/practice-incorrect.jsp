<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<base href="<%=basePath%>">

		<!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame
		Remove this if you use the .htaccess -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Exam++</title>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/exam.css" rel="stylesheet" type="text/css">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<style type="text/css">
			.question-body {
				padding: 30px 30px 20px 30px;
				background: #FFF;
			}
			
			ul#exampaper-body{
				margin-bottom: 0px;
			}
			
			ul#exampaper-body li{
				padding-bottom:0px;
			}
			.question-body{
				min-height:300px;
			}
			
		</style>
	</head>
	<body>
		<header>
			<div class="container">
				<div class="row">
					<div class="col-xs-5">
						<div class="logo">
							<h1><a href="#"><img alt="" src="resources/images/logo.png"></a></h1>
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
							<a href="home"><i class="fa fa-home"></i>主页</a>
						</li>
						<li class="active">
							<a href="start-exam"><i class="fa fa-edit"></i>试题练习</a>
						</li>
						<li>
							<a href="student/user-center"><i class="fa fa-dashboard"></i>会员中心</a>
						</li>
						<li>
							<a href="student/setting"><i class="fa fa-cogs"></i>个人设置</a>
						</li>
					</ul>
				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div class="row">
					<div class="col-xs-3" style="padding-right: 0px;padding-bottom:15px;">
						<div class="def-bk" id="bk-exam-control">

							<div class="def-bk-content" id="exam-control">

								<div>

									<span  style="color:#428bca;">知识类型：</span><span>${knowledgePointName }</span>
									<div>
										<span>[ 共 <span class="pt-tno">${amount }</span> 题 ]</span>
									</div>

								</div>
								<div id="question-submit">
									<button class="btn-success btn" style="width:100%;" id="switch-model-btn" data-exam="true"> 
										答题模式
									</button>
								</div>
								<div id="exam-info" style="display:none;">
									<span id="answer-hashcode"></span>
								</div>
							</div>

						</div>

					</div>
					<div class="col-xs-9" style="padding-right: 0px;">
						<div class="def-bk" id="bk-exampaper">
							<div class="expand-bk-content" id="bk-conent-exampaper">
								<div id="exampaper-header">
									<div id="exampaper-title"  style="margin-bottom:15px;">
										<i class="fa fa-paper-plane"></i>
										<span id="exampaper-title-name"> ${fieldName } - ${practiceName } </span>

									</div>

								</div>
								<ul id="exampaper-body">
									<div class="question-title">
										<div class="question-title-icon"></div>
										<span class="question-no"></span>
									</div>
									<c:forEach items="${questionList}" var="item">
										<form class="question-body" name ="${item.questionTypeId}">
											<span>[${item.questionTypeName}]</span>
											<p class="question-body-text" >${item.questionContent.title}</p>
											<c:choose>
												<c:when test="${ item.questionTypeId == 1}">
													<ul class="question-opt-list">
														<li class="question-list-item">
															<c:forEach items="${item.questionContent.choiceList}" var="choice">
																<input type="radio" value="${choice.key}" name="question-radio1" class="question-input">
																<span class="question-li-text">${choice.key}:${choice.value}</span>
															</c:forEach>
														</li>
													</ul>
												</c:when>
												<c:when test="${ item.questionTypeId == 2}">
													<ul class="question-opt-list">
														<li class="question-list-item">
															<c:forEach items="${item.questionContent.choiceList}" var="choice">
																<input type="checkbox" value="${choice.key}" name="question-radio1" class="question-input">
																<span class="question-li-text">${choice.key}:${choice.value}</span>
															</c:forEach>
														</li>
													</ul>
												</c:when>
												<c:when test="${item.questionTypeId == 3}">
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
											<div class="answer-desc">
												<div class="answer-desc-summary">
													<span>正确答案：</span><span class="answer_value">${item.answer}</span><br>
												</div>
												<div class="answer-desc-detail"><label class="label label-warning"><i class="fa fa-flag"></i><span> 解析</span></label>
													<div class="answer-desc-content"><p></p></div>
												</div>
												<div class="answer-desc-detail"><label class="label label-success"><i class="fa fa-bookmark"></i><span> 考点</span></label>
													<div class="answer-desc-content"></div>
												</div>
											</div>
										</form>
									</c:forEach>

								</ul>
								<div id="exampaper-footer">
										
									
									<div id="question-switch">
										<button class="btn-success btn" id="previous-q-btn" style="width:160px;">
												<i class="fa fa-chevron-circle-left"></i>上一题

										</button>
										<button class="btn-success btn" id="next-q-btn" style="margin-left:60px;width:160px;">
												下一题 <i class="fa fa-chevron-circle-right"></i>
										</button>
										<button class="btn-warning btn" id="submit-q-btn" style="width:160px;float:right;">
												<i class="fa fa-check-circle-o"></i>提交答案

										</button>
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
		<script type="text/javascript" src="resources/js/all.js?v=0712"></script>
		<script type="text/javascript" src="resources/js/practice-incorrect.js"></script>

	</body>
</html>
