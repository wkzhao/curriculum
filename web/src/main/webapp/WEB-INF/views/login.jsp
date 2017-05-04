<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>Exam++ 登录系统</title>
		
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>resources/images/favicon.ico" />
		<link href="resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="resources/css/style.css" rel="stylesheet">
		<style type="text/css">
			.form-group {
				margin-bottom: 5px;
				height: 59px;
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
									<a href="javascript:void(0)" >${sessionScope.user.username}</a>
									<span>|</span>
									<a href="user_logout"><i class="fa fa-sign-out"></i> 退出</a>
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
				<nav class="collapse navbar-collapse bs-navbar-collapse"
				role="navigation">
					<ul class="nav navbar-nav">
						<li>
							<a href="home"><i class="fa fa-home"></i>课程学习</a>
						</li>
						<li>
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

		<!-- Navigation bar ends -->

		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div class="row">

					<div class="col-md-12">
						<div class="lrform">
							<h5>登陆Exam++</h5>
							<div class="form">
								<!-- Login form -->
								<form class="form-horizontal"  method="post">
									<!-- Username -->
									<div class="form-group">
										<label class="control-label col-md-3" >用户名</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id ="login_username" name="username" >
											<span id = "login-username-info" class="form-message"></span>
										</div>
									</div>
									<!-- Password -->
									<div class="form-group">
										<label class="control-label col-md-3" >密码</label>
										<div class="col-md-9">
											<input type="password" class="form-control" name="password" id="login_password" >
											<span id = "login-password-info" class="form-message"></span>
										</div>
									</div>

									<!-- Buttons -->
									<div class="form-group">
										<!-- Buttons -->
										<div class="col-md-9 col-md-offset-3">
											<label onclick="check()" class="btn btn-default">
												登陆
											</label>
											<button type="reset" class="btn btn-default">
												取消
											</button>
											<span class="form-message">${result}</span>
										</div>
									</div>
								</form>
								没有账号? <a href="user-register">注册</a>
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
		<script type="text/javascript"
				src="resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript"
				src="resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="resources/js/all.js"></script>
		<script type="text/javascript" src="resources/js/md5.js"></script>
		<script type="text/javascript">
			function check(){
                var flag = true;
                var username = $("#login_username").val();
                var password = $("#login_password").val();
                if(!username){
                    $("#login-username-info").text("用户名不能为空");
                    flag = false;
                }
                if(!password){
                    $("#login-password-info").text("密码不能为空");
                    flag = false;
				}
				if( flag == true){
                    var user = new Object();
                    user.username = username;
                    user.password = hex_md5(password);
                    $.ajax(
                        {
                            headers : {
                                'Accept' : 'application/json',
                                'Content-Type' : 'application/json'
                            },
                            type:"POST",
                            url:"user-login",
                            data:JSON.stringify(user),
                            success:function(msg) {
                                if(msg.status.code == 0){
                                    window.location.href = document.getElementsByTagName('base')[0].href +"home";
                                }else{
                                    util.error(msg.status.msg);
                                    return ;
                                }
                            },
                            error: function(XMLHttpRequest, textStatus, errorThrown) {
                                util.error("操作失败，请稍后再试")
                                return ;
                            }
                        }
                    );
				}
            };
		</script>
	</body>
</html>