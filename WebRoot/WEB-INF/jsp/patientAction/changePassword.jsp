<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#changePassword").addClass('li_checked');
	});
</script>
<title>我的账户</title>
</head>

<body>
	<!-- 首页 -->
	<%@ include file="/WEB-INF/jsp/public/loginTopPage.jspf"%>
	<!-- 设置居中 放大缩小时，始终处于中间位置-->
	<div class="bodyPage">
		<!-- Banner 背景图与搜索框-->
		<!-- 搜素框设有 康复设备 合作医院 康复专家 康复论坛 用以搜索时的筛选 -->
		<%@ include file="/WEB-INF/jsp/public/bannerPage.jspf"%>

		<div class="mainPage">
			<!-- 患者中心左边目录 -->
			<%@ include file="/WEB-INF/jsp/public/patientLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
				<div class="rightSide">
					<div class="centerTitle">我的账户 &gt; 修改密码</div>
					<!-- 修改密码 -->
					<div class="infoContent">
						<div class="fieldArea">
							<span style="color:#F00;">*</span>请输入原密码<br /> <span
								style="color:#F00;">*</span>请输入新密码<br /> <span
								style="color:#F00;">*</span>确认新密码<br />
						</div>
						<s:form id="cpForm" action="patient_changePassword" method="post">
							<div class="passwordPropertyArea">
								<input type="hidden" name="id" />
								<s:textfield type="password" name="password" value="" />
								<br />
								<s:textfield type="password" name="newPassword" />
								<br />
								<s:textfield id="confirmNewPassword" type="password" />
								<br /> <span id="errorMsg"
									style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">
									<!-- 弹出提示信息 --> <s:if test="hasFieldErrors()">
										<s:iterator value="fieldErrors">
											<s:iterator value="value">
												<script language="JavaScript">
													alert('<s:property/>');
												</script>
											</s:iterator>
										</s:iterator>
									</s:if>
								</span>
							</div>
							<div class="confirmArea">
								<input id="cpSubmit" type="button" value="确认修改" />
							</div>
						</s:form>
					</div>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
