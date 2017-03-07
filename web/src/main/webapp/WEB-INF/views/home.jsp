<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<!-- Always force latest IE rendering engine (even in intranet) & Chrome Frame
		Remove this if you use the .htaccess -->
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Exam++</title>
		<meta name="viewport"
		content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css"
		rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css"
		rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">

		<style>
			.question-number{
				color: #5cb85c;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}

			.question-number-2{
				color: #5bc0de;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}
			.question-number-3{
				color: #d9534f;
				font-weight: bolder;
				display: inline-block;
				width: 30px;
				text-align: center;
			}

			a.join-practice-btn{
				margin:0;
				margin-left:20px;
			}

			.content ul.question-list-knowledge{
				padding:8px 20px;
			}

			.knowledge-title{
				background-color:#EEE;
				padding:2px 10px;
				margin-bottom:20px;
				cursor:pointer;
				border:1px solid #FFF;
				border-radius:4px;
			}

			.knowledge-title-name{
				margin-left:8px;
			}

			.point-name{
				width:200px;
				display:inline-block;
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
						<li class="active">
							<a href="home"><i class="fa fa-home"></i>主页</a>
						</li>
						<li>
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

		<!-- Slider starts -->

		<div class="full-slider">
			<!-- Slider (Flex Slider) -->

			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="flexslider">
							<div class="flex-caption">
								<!-- Left column -->
								<div class="col-l">
									<p style="text-indent:2em;">Exam++是国内首款一款基于JAVA与MYSQL开发的网络考试系统。它可以稳定、顺畅的运行在Windows与Linux平台上。您可以通过它快捷方便的创建试题和题库，发布试卷，组织考试，系统自动批改。高度的可配置性和灵活性使得它可以被应用于很多领域。</p>
									<p style="text-indent:2em;">软件采用GPL协议，完全开放且免费，并且有固定的开发团队提供技术支持</p>
								</div>
								<!-- Right column -->
								<div class="col-r">

									<!-- Use the class "flex-back" to add background inside flex slider -->

									<!-- <img alt="" src="resources/images/ad.png"> -->
									<p>如果您对软件有任何反馈和建议，加入我们的QQ群152258375一起讨论吧</p>

									<!-- Button -->
										<a class="btn btn-default btn-cta" href="user-register"><i class="fa fa-arrow-circle-down"></i> 马上加入我们吧</a>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div>
					<h3>课程学习</h3>
					<p>
						选择您想要学习的知识，开始学习吧
					</p>
					<div class="row">
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-cloud-upload"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">线性表</h3>
									<a class="btn btn-primary" data-toggle="modal" data-target=".levelup-practice-modal"><i class="fa fa-arrow-right"></i>开始学习</a>
									<div class="modal fade levelup-practice-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
									</div>
									  </div>
									</div>
								</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-eraser"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">二叉树</h3>
									<!-- <a class="btn btn-primary" href="student/practice-incorrect"><i class="fa fa-arrow-right"></i>参加练习</a> -->
									<a class="btn btn-primary" data-toggle="modal" data-target=".incorrect-modal"><i class="fa fa-arrow-right"></i>开始学习</a>
									  </div>
									</div>
								</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-superscript"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">图论</h3>
									<a class="btn btn-primary" href="student/practice-test"><i class="fa fa-arrow-right"></i>开始学习</a>
								</div>
								<!--//content-->

							</div>
						</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-file-text"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">排序</h3>
									<a class="btn btn-primary" data-toggle="modal" data-target=".practice-exampaper-modal"><i class="fa fa-arrow-right"></i>开始学习</a>
									</div>
								</div>
								<!--//content-->
							</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-book"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">拓扑矩阵</h3>
									<a class="btn btn-primary" data-toggle="modal" data-target=".history-exampaper-modal" disabled="disabled"><i class="fa fa-arrow-right"></i>开始学习</a>
								<!--//content-->
								</div>
							</div>
						</div>
						<div class="select-test col-xs-6">
							<div style="height: 100px;">
								<div class="select-test-icon">
									<i class="fa fa-rocket"></i>
								</div>
								<div class="select-test-content">
									<h3 class="title">算法</h3>
									<a class="btn btn-primary" data-toggle="modal" data-target=".expert-exampaper-modal" disabled="disabled"><i class="fa fa-arrow-right"></i>开始学习</a>
									  </div>
									</div>
								</div>
								<!--//content-->

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
		<script type="text/javascript"
		src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript"
		src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script>
		$(function(){
			bindQuestionKnowledage();
			var result = checkBrowser();
			if (!result){
				alert("请至少更新浏览器版本至IE8或以上版本");
			}
		});

		function checkBrowser() {
			var browser = navigator.appName;
			var b_version = navigator.appVersion;
			var version = b_version.split(";");
			var trim_Version = version[1].replace(/[ ]/g, "");
			if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0") {
				return false;
			} else if (browser == "Microsoft Internet Explorer"
					&& trim_Version == "MSIE6.0") {
				return false;
			} else
				return true;
		}

		function bindQuestionKnowledage(){
			$(".knowledge-title").click(function(){
				var ul = $(this).parent().find(".question-list-knowledge");

				if(ul.is(":visible")){
					$(this).find(".fa-chevron-down").hide();
					$(this).find(".fa-chevron-up").show();

					$(".question-list-knowledge").slideUp();

				}else{
					$(".fa-chevron-down").hide();
					$(".fa-chevron-up").show();

					$(this).find(".fa-chevron-up").hide();
					$(this).find(".fa-chevron-down").show();

					$(".question-list-knowledge").slideUp();
					ul.slideDown();

				}


			});
		}
		</script>
		<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1252987997'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s19.cnzz.com/z_stat.php%3Fid%3D1252987997' type='text/javascript'%3E%3C/script%3E"));</script>
	</body>
</html>