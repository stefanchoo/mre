<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#therapistInfo").addClass('li_checked');
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
		<%@ include file="/WEB-INF/jsp/public/bannerPage.jspf" %>

		<div class="mainPage">
			<!-- 患者中心左边目录 -->
			<%@ include file="/WEB-INF/jsp/public/therapistLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
				<div class="rightSide">
					<div class="centerTitle">我的账户 &gt; 个人资料</div>
					<div class="infoContent">
							<div class="userPic">
								<img src="${pageContext.request.contextPath}/style/images/userIcons/${user.icon}" title="点击修改头像"
									style="cursor:pointer;" />
							</div>
							<div class="fieldArea">
								用户名<br /> 真实姓名<br /> 性别<br /> 出生年月<br /> 所在城市<br /> 邮箱<br />
								手机<br /> 微信<br />  单位<br/> 等级<br /> 在线时间<br /> 注册时间<br />
							</div>
							<s:form id="basicInfoForm" action="therapist_updateBasicInfo" method="post">
							<div class="fieldPropertyArea">
								<s:hidden name="id" />
								<s:textfield type="text" name="loginName" readonly="true" cssClass="inputStyle"/><br />
								<s:textfield type="text" name="trueName" cssClass="inputStyle" /><br /> 
								<!-- 构建一个list -->		
                                <s:radio name="gender" list="{'男', '女'}" cssClass="gender"/> <br />
                                <span style="height:0px;visibility: hidden;"><s:date name="birthday" format="yyyy-MM-dd" var="brd"/></span>
								<s:textfield id="birthday" name="birthday" value="%{#brd}" cssClass="inputStyle"/>
								<br /> 
								<s:textfield id="city" type="text" name="city" readonly="true" cssClass="inputStyle" placeholder="请选择城市"/><br />
								<script type="text/javascript">
									LazyLoad.css(
										[ "http://www.nofate.net.cn/mre/style/css/cityStyle.css" ],
										function() {
											LazyLoad.js(
														[ "http://www.nofate.net.cn/mre/script/cityScript.js" ],
														function() {
															 new citySelector.cityInit(
																	"city");
														});
									});
								</script>
								<s:textfield type="text" name="email" cssClass="inputStyle"/><br />
								<s:textfield type="text" name="telephone" cssClass="inputStyle"/><br /> 
								<s:textfield type="text" name="weixin" cssClass="inputStyle"/><br /> 
								<s:textfield type="text" value="%{hospital.name}" readonly="true" cssClass="inputStyle"/><br/>
								<s:textfield type="text" name="level" readonly="true" cssClass="inputStyle"/><br />
								<s:textfield type="text" name="onlineTime"  readonly="true" cssClass="inputStyle"/><br />
								<span style="height:0px;visibility: hidden;"><s:date name="registerTime" format="yyyy-MM-dd HH:mm:ss" var="rt"/></span>
								<s:textfield type="text" name="registerTime"  value="%{#rt}" readonly="true" cssClass="inputStyle"></s:textfield>		
							</div>
							
							<div class="operateArea">
								<input id="basicSubmit" type="button" value="保存" />
								<input type="reset" value="取消" />
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
