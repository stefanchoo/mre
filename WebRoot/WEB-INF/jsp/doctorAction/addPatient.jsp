<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#patientsList").addClass('li_checked');
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
		<%@ include file="/WEB-INF/jsp/public/newBannerPage.jspf"%>

		<div class="mainPage">
			<!-- 患者中心左边目录 -->
			<%@ include file="/WEB-INF/jsp/public/doctorLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
				<div class="rightSide">
					<div class="centerTitle">患者管理 &gt; 患者列表&gt; 添加患者</div>
					<!--！这里可以搜索（在添加的时候，可以搜索用户名并且关联其为病人）、循环、支持分页！
    	字段为： 姓名 性别 疾患 训练计划 入院时间 操作（增 删 查）
        点击查看页面出现康复评估的状况，可以再对患者进行评估（量表的选择来自于康复量表管理）
     -->

					<div class="addContent">
						<div class="addFieldArea">
							用户名<br /> 真实姓名<br /> 性别<br /> 出生年月<br /> 所在城市<br />
						</div>
						<!--  根据搜索的结果，提交时判断是都要添加用户？  -->
						<s:form
							action="doctor_addPatient?type=%{#session.type}"
							id="addForm"
							method="post">
							<div class="addFieldPropertyArea">
								<s:if test="%{#session.type == 'add'}">
								<s:textfield id="pLoginName" name="patient.loginName" cssClass="inputStyle1" />
								</s:if>
								<s:else>
								<s:textfield id="pLoginName" name="patient.loginName" cssClass="inputStyle1" readonly="true" />
								</s:else>
								<input type="button" class="psearch" title="点击搜索用户" ></input><br />
								<s:textfield id="pTrueName" name="patient.trueName" cssClass="inputStyle1" />
								<br />		
								<div style="width:300px;height:40px;float:left;">
									<s:radio name="patient.gender" list="{'男', '女'}" cssClass="gender5" />
								</div>
								<br /> <span style="height:0px;visibility: hidden;"><s:date
										name="patient.birthday" format="yyyy-MM-dd" var="pbrd" /></span>
								<s:textfield id="birthday" type="text" name="patient.birthday"
									value="%{#pbrd}" cssClass="inputStyle1" />
								<br />
								<s:textfield id="city" type="text" name="patient.city" readonly="true"
									cssClass="inputStyle1" placeholder="请选择城市" />
								<br />
								<script type="text/javascript">
									LazyLoad
											.css(
													[ "http://www.nofate.net.cn/mre/style/css/cityStyle.css" ],
													function() {
														LazyLoad
																.js(
																		[ "http://www.nofate.net.cn/mre/script/cityScript.js" ],
																		function() {
																			new citySelector.cityInit(
																					"city");
																		});
													});
								</script>
								<span id="errorMsg"
									style="width:300px;font-size:12px; margin-top:10px;color:#F00;text-align:left;display:block;float:left;">
									<s:fielderror></s:fielderror>
								</span>
							</div>
							<div class="addOperateArea">
								<input id="addSubmit" type="button" value="添加" /> 
								<s:if test="%{#session.type == 'add' }">
								<input type="reset" value="取消" />
								</s:if>
								<s:else>
								<input type="reset" value="取消" disabled="disabled" />
								</s:else>
							</div>
						</s:form>
						<br /> <br />
					</div>
				</div>
				<div style="width:800px;height:300px; margin:50px; float:left; font-size:14px;">
					<span style="color:red;">*提示：</span>
					<p>若患者在本站有账号，您可以输入 <span style="color:red;">用户名</span> 和 <span style="color:red;">真实姓名</span>，点击搜索，搜索完成后，直接点击添加即可；</p>
					<p>若患者没有账号，您需要填写表中信息，点击添加，就可以为患者申请一个初始密码为 <span style="color:red;">123456</span> 的账号。</p>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
