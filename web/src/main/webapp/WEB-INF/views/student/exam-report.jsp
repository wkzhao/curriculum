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

</head>
<body>

<c:if test="${ !isAdmin }">
<header>
	<div class="container">
		<div class="row">
			<div class="col-xs-5">
				<div class="logo">
					<h1><a href="#"><img alt="" src="resources/images/logo.png"></a></h1>
				</div>
			</div>
			<div class="col-xs-7" >
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
					<a href="home"><i class="fa fa-home"></i>课程学习</a>
				</li>
				<li class="active">
					<a href="start-exam"><i class="fa fa-edit"></i>试题练习</a>
				</li>
				<li>
					<a href="student/user-center"><i class="fa fa-dashboard"></i>知识统计</a>
				</li>
				<li>
					<a href="student/setting"><i class="fa fa-cogs"></i>个人设置</a>
				</li>
			</ul>
		</nav>
	</div>
</div>
</c:if>
<!-- Navigation bar ends -->

<div class="content" style="margin-bottom: 100px;">

	<div class="container">
		<div class="row">
			<div class="col-xs-9" style="padding-right: 0px;">
				<div class="def-bk" >

					<div class="expand-bk-content" >
						<div >
							<div >
								<i class="fa fa-send"></i>
								<span > 模拟试卷 </span>
								<span style="display:none;" id="exampaper-id">1</span>
							</div>
							<div id="exampaper-desc-container">
								<div  class="exampaper-filter">

								</div>
							</div>

						</div>
						<ul>
							<c:forEach items="${singleQuestions}" var="item">
								<c:if test="${status.index == 0}">
									<span>[单选题]</span>
								</c:if>
								<form class="question-body" name="${item.questionTypeId}">
									<span class="question-id" style="display:none;">${item.id}</span>
									<span class="question-id" style="display: none">${item.points}</span>
									<p class="question-body-text">${item.questionContent.title}</p>
									<ul class="question-opt-list">
										<li class="question-list-item">
											<c:forEach items="${item.questionContent.choiceList}" var="choice">
												<span class="question-li-text">${choice.key}:${choice.value}</span>
											</c:forEach>
										</li>
									</ul>
								</form>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 学生答案</span></label>
									<div>
											${item.uAnswer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 参考答案</span></label>
									<div>
											${item.answer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 解析</span></label>
									<div class="answer-desc-content">
											${item.analysis }
									</div>
								</div>
							</c:forEach>
							<c:forEach items="${multiQuestions}" var="item">
								<c:if test="${status.index == 0}">
									<span>[多选题]</span>
								</c:if>
								<form class="question-body" name="${item.questionTypeId}">
									<span class="question-id" style="display:none;">${item.id}</span>
									<span class="question-id" style="display: none">${item.points}</span>
									<p class="question-body-text">${item.questionContent.title}</p>
									<ul class="question-opt-list">
										<li class="question-list-item">
											<c:forEach items="${item.questionContent.choiceList}" var="choice">
												<span class="question-li-text">${choice.key}:${choice.value}</span>
											</c:forEach>
										</li>
									</ul>
								</form>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 学生答案</span></label>
									<div>
											${item.uAnswer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 参考答案</span></label>
									<div>
											${item.answer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 解析</span></label>
									<div class="answer-desc-content">
											${item.analysis }
									</div>
								</div>
							</c:forEach>
							<c:forEach items="${judgeQuestions}" var="item">
								<c:if test="${status.index == 0}">
									<span>[判断题]</span>
								</c:if>
								<form class="question-body" name="${item.questionTypeId}">
									<span class="question-id" style="display:none;">${item.id}</span>
									<span class="question-id" style="display: none">${item.points}</span>
									<p class="question-body-text">${item.questionContent.title}</p>
									<ul class="question-opt-list">
										<li class="question-list-item">
											<span class="question-li-text">正确</span>
										</li>
										<li class="question-list-item">
											<span class="question-li-text">错误</span>
										</li>
									</ul>
								</form>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 学生答案</span></label>
									<div>
											${item.uAnswer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 参考答案</span></label>
									<div>
											${item.answer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 解析</span></label>
									<div class="answer-desc-content">
											${item.analysis }
									</div>
								</div>
							</c:forEach>
							<c:forEach items="${otherQuestions}" var="item">
								<c:if test="${status.index == 0}">
									<span>[主观题]</span>
								</c:if>
								<form class="question-body" name="${item.questionTypeId}">
									<span class="question-id" style="display:none;">${item.id}</span>
									<span class="question-id" style="display:none;">${item.points}</span>
									<p class="question-body-text">${item.questionContent.title}</p>
								</form>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 学生答案</span></label>
									<div>
											${item.uAnswer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 参考答案</span></label>
									<div>
											${item.answer }
									</div>
								</div>
								<div class="answer-desc-detail">
									<label class="label label-primary"><i class="fa fa-check-square-o"></i><span> 解析</span></label>
									<div class="answer-desc-content">
											${item.analysis }
									</div>
								</div>
							</c:forEach>
						</ul>
					</div>

				</div>
			</div>
		</div>
	</div>

</div>

<c:if test="${ !isAdmin }">
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
</c:if>
<!-- Slider Ends -->

<!-- Javascript files -->
<!-- jQuery -->
<script type="text/javascript" src="resources/js/jquery/jquery-1.9.0.min.js"></script>
<!-- Bootstrap JS -->
<script type="text/javascript" src="resources/bootstrap/js/bootstrap.min.js"></script>

</body>
</html>