<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/logincssjs.jspf"%>
<title>用户注册</title>
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
					width="360px" height="120px"  title="点击回到首页" style="cursor:pointer" />
				</s:a>
			</div>
			<div class="denglu">注册</div>
		</div>

		<!-- 主题 + 注册框 -->
		<div class="loginShow">
			<div class="loginPic"></div>
			<div class="loginContent">
				<div class="registerTitle">
					已有账号？
					<s:a action="user_loginUI">立即登陆</s:a>
				</div>
				<div class="registerBox">
					<s:form id="registerForm" action="user_register" method="post">
						<div class="registerInput">
							<input type="text" id="loginName" name="patient.loginName"
								placeholder="用户名" /> <input type="password" id="password"
								name="patient.password" placeholder="密码: 6~20数字和字母" /> <input
								type="password" id="confirmPassword" placeholder="重复密码" /> <span
								id="errorMsg"
								style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">
							</span>
						</div>
						<div class="contract">
							<input type="checkbox" id="contractApproved" checked="checked">&nbsp;同意<a
								href="" style="color:#09C">《MRE在线服务条款》</a></input>
						</div>
						<div class="submitBtn">
							<button id="registerBtn" type="button" class="submit_off">注
								册</button>
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
