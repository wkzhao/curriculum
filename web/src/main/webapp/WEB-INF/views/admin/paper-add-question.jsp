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


		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-9">
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
								<input id="field-id-hidden" value="${fieldId }" type="hidden">
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
