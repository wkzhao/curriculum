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
		<title>练习历史</title>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
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
							<a href="home"><i class="fa fa-home"></i>课程学习</a>
						</li>
						<li>
							<a href="start-exam"><i class="fa fa-edit"></i>试题练习</a>
						</li>
						<li class="active">
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

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">
				<div class="row">
					<c:if test="${ !isAdmin }">
					<div class="col-xs-3">
						<ul class="nav default-sidenav">
							<li>
								<a href="student/user-center"> <i class="fa fa-dashboard"></i> 用户中心 </a>
							</li>
							<li>
								<a href="student/analysis/${sessionScope.user.id}"> <i class="fa fa-bar-chart-o"></i> 统计分析 </a>
							</li>
							<li class="active">
								<a> <i class="fa fa-history"></i> 考试历史 </a>
							</li>
						</ul>
					</div>
					</c:if>
					<div class="col-xs-9">
						<div class="page-header">
							<h1><i class="fa fa-history"></i> 考试历史</h1>
						</div>
						<div class="page-content row">
							<div id="question-list">
								<table class="table-striped table">
									<thead>
										<tr>
											<td>试卷名称</td><td>参加时间</td><td>操作</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${userPaperList}" var="item">
											<tr>
											
												<td>
													${item.paperName}
												<td>
													<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd"/>
												</td>
												<td>
													<c:if test="${isAdmin}">
														<a href="student/exam-report-${item.paperId}-${userId}?isAdmin=true">查看报告</a>
													</c:if>
													<c:if test="${!isAdmin}">
													    <a href="student/exam-report-${item.paperId}-${sessionScope.user.id}">查看报告</a>
													</c:if>
												</td>
											</tr>
										</c:forEach>
											
									</tbody><tfoot></tfoot>
								</table>
							</div>
							<div id="page-link-content">
								<ul class="pagination pagination-sm">${pageStr}</ul>
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
		<script type="text/javascript" src="resources/chart/raphael-min.js"></script>
		<script type="text/javascript" src="resources/chart/morris.js"></script>
		<script type="text/javascript" src="resources/js/exam-finished.js"></script>
	</body>
</html>