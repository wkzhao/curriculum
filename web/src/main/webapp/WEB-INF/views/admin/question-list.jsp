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
		<title>试题管理</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		
		<link href="resources/css/exam.css" rel="stylesheet">
		<link href="resources/chart/morris.css" rel="stylesheet">
		<style>
			.examing-point{
				display:block;
				font-size:10px;
				margin-top:5px;
			}
			.question-name-td{
				width:300px;
			}
			.change-property{
				cursor:pointer;
			}
			.add-tag-btn{
				cursor:pointer;
			}
		</style>
	</head>
	<body>
	    <input type="hidden" class="question-list-flag" value="0">
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

						<li class="active">
							<a href="admin/question-list/0-0-0-0-1"><i class="fa fa-edit"></i>试题管理</a>
						</li>
						<li>
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
							<li class="active">
								<a href="admin/question-list/0-0-0-0-1"> <i class="fa fa-list-ul"></i> 试题管理 </a>
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
							<h1><i class="fa fa-list-ul"></i> 试题管理 </h1>
						</div>
						<div class="page-content row">

							<div id="question-filter">

								<dl id="question-filter-field">
									<dt>
										题库：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.knowledgeId == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${knowledgeList}" var="knowledge">
											<c:choose>
												<c:when test="${questionFilter.knowledgeId == knowledge.id }">
													<span data-id="${knowledge.id}" class="label label-info">${knowledge.name}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${knowledge.id}">${knowledge.name}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</dd>
								</dl>
								<dl id="question-filter-knowledge">
									<dt>
										知识分类：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.knowledgePointId == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${knowledgePointList}" var="point">
											<c:choose>
												<c:when test="${questionFilter.knowledgePointId == point.id }">
													<span data-id="${point.id}" class="label label-info">${point.name}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${point.id}">${point.name}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</dd>
								</dl>

								<dl id="question-filter-qt">
									<dt>
										试题类型：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.questionTypeId == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${questionTypeList}" var="questionType">
											<c:choose>
												<c:when test="${questionFilter.questionTypeId == questionType.id }">
													<span data-id="${questionType.id}" class="label label-info">${questionType.name}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${questionType.id}">${questionType.name}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>

									</dd>
								</dl>
								<div class="page-link-content">
									<ul class="pagination pagination-sm">
										<c:forEach  varStatus="status" begin="${pageBean.beginPageIndex }"  end="${pageBean.endPageIndex}">
											<c:if test="${status.index+pageBean.beginPageIndex-1 == pageBean.currentPage }">
												<li><a disabled="disabled" href="javascript:void(0)">${status.index+pageBean.beginPageIndex-1 }</a></li>
											</c:if>
											<c:if test="${status.index+pageBean.beginPageIndex-1 != pageBean.currentPage }">
												<li><a data-id = "${status.index+pageBean.beginPageIndex-1 }" >${status.index+pageBean.beginPageIndex-1 }</a></li>
											</c:if>
										</c:forEach>
									</ul>
								</div>
							</div>
							<div id="question-list">
								<input id="knowledge-hidden" value="${knowledge }" type="hidden">
								<input id="question-type-hidden" value="${questionType }" type="hidden">
								<input id="search-param-hidden" value="${searchParam }" type="hidden">
								<table class="table-striped table">
									<thead>
										<tr>
											<td></td><td>ID</td><td class="question-name-td" style="width:240px">试题名称</td><td style="width:60px">试题类型</td><td>知识类</td>
										</tr>
									</thead>
									<tbody>
										
										<c:forEach items="${questionList }" var="items">
											<tr>
												<td>
												<input type="checkbox" value="${items.id }">
												</td><td>${items.id }</td>
												<td>
													<a href="admin/question-preview/${items.id }" target="_blank" title="预览">${items.name }</a>
												</td>
												
												
												<td>${items.questionTypeName }</td><td>${items.pointsName }</td>
											</tr>
										</c:forEach>
										

									</tbody><tfoot></tfoot>
								</table>
							</div>
							<div class="page-link-content">
								<ul class="pagination pagination-sm">
									<c:forEach  varStatus="status" begin="${pageBean.beginPageIndex }"  end="${pageBean.endPageIndex}">
										<c:if test="${status.index+pageBean.beginPageIndex-1 == pageBean.currentPage }">
											<li><a disabled="disabled" href="javascript:void(0)">${status.index+pageBean.beginPageIndex-1 }</a></li>
										</c:if>
										<c:if test="${status.index+pageBean.beginPageIndex-1 != pageBean.currentPage }">
											<li><a data-id = "${status.index+pageBean.beginPageIndex-1 }" >${status.index+pageBean.beginPageIndex-1 }</a></li>
										</c:if>
									</c:forEach>
								</ul>
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
		<script type="text/javascript" src="resources/js/question-list.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/field-2-point.js"></script>

	</body>
</html>
