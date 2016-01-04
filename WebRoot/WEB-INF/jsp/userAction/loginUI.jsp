<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/init.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/style/css/login.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/login.js"></script>
<title>用户登录</title>
<script type="text/javascript">
	// 在被嵌套时就刷新上级窗口,处理框架里显示登录页面的问题
	if (window.parent != window) {
		window.parent.location.reload(true);
	}
</script>
</head>

<body>
	<!-- 首页 -->
	<%@ include file="/WEB-INF/jsp/public/logoutTopPage.jspf"%>
	<!-- 设置居中 放大缩小时，始终处于中间位置-->
	<div class="bodyPage">
		<div id="bannerPage" class="bannerPage">
			<div class="banner">
				<s:a action="user_index">
				<img
					src="${pageContext.request.contextPath}/style/images/banner.png"
					width="360px" height="120px" title="点击回到首页" style="cursor:pointer" />
				</s:a>	
			</div>
			<div class="denglu">登陆</div>
		</div>

		<!-- 主题 + 登录框 -->
		<div class="loginShow">
			<div class="loginPic"></div>
			<div class="loginContent">
				<div class="loginTitle">
					没有账号？
					<s:a action="user_registerUI">立即注册</s:a>
				</div>
				<div class="loginBox">
					<s:form id="loginForm" action="user_login" method="post">
						<div class="loginInput">
							<input type="text" id="loginName" name="loginName"
								placeholder="用户名" /> <input type="password" id="password"
								name="password" placeholder="密码" /> <span id="errorMsg"
								style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">
							</span>
						</div>
						<div class="forgetPwd">
							<a href="#">忘记密码？</a>
						</div>
						<div class="submitBtn">
							<button id="loginBtn" type="button" class="submit_off">登
								录</button>
						</div>
					</s:form>
					<!-- 弹出提示信息 -->
					<s:if test="hasFieldErrors()">
						<s:iterator value="fieldErrors">
							<s:iterator value="value">
								<script language="JavaScript">
									alert('<s:property/>');
								</script>
							</s:iterator>
						</s:iterator>
					</s:if>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>

</body>
</html>
