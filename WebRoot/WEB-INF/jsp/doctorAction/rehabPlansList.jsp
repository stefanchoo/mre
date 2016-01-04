<%@page import="com.mre.domain.User"%>
<%@page import="com.mre.util.Formatter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#makePlans").addClass('li_checked');
	});
</script>
<title>我的工作</title>
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
					<div class="centerTitle">我的工作 &gt; 康复计划</div>
					<!-- 这里支持分页！
    					  字段为： 时间 姓名 康复设备 训练模式 训练时间 操作（删 查）
     				-->
					<div class="orderBy">
						<s:form action="doctor_rehabPlansList" method="post">
							<div class="selectPatients">
								患者 &nbsp;
								<s:select id="patients" name="patient.id"
									list="%{#patientsList}" listKey="id" listValue="trueName"
									headerKey="0" headerValue="请选择">
								</s:select>
							</div>
							<div class="selectEquipments">
								设备 &nbsp;
								<s:select id="equipments" name="equipment.id"
									list="%{#equipmentUsedsList}" listKey="id" listValue="name"
									headerKey="0" headerValue="请选择">
								</s:select>
							</div>
							<div class="selectTime">
								<img
									src="${pageContext.request.contextPath}/style/images/calendar.png" />
								 <span style="height:0px;visibility: hidden;">
									<s:date name="fromDate" format="yyyy-MM-dd" var="fromDate"/>
									<s:date name="toDate" format="yyyy-MM-dd" var="toDate"/>
								</span>
								<s:textfield type="text" cssStyle="width:100px;" id="fromDate" name="fromDate" value="%{#fromDate}"/>
								&nbsp;到
								<s:textfield type="text" cssStyle="width:100px;" id="toDate" name="toDate" value="%{#toDate}"/>
							</div>

							<div class="orderSubmit">
								<button type="submit">提交</button>
							</div>

						</s:form>
					</div>
					<div class="planList">
						<table width="95%" border="0" align="center" cellpadding="0"
							cellspacing="0" id="plans">
							<tr>
								<th width="12%" scope="col">时间</th>
								<th width="12%" scope="col">姓名</th>
								<th width="25%" scope="col">康复设备</th>
								<!-- 这里其实还应该包括训练次数 -->
								<th width="14%" scope="col">训练模式</th>
								<th width="11%" scope="col">训练时间</th>
								<th width="11%" scope="col">康复训练师</th>
								<th width="15%" scope="col">操作</th>
							</tr>
							<!--  这里是要循环的 -->
							<s:iterator value="recordList">
								<tr style="border-top: 1px solid #999;">
									<td><s:date name="time" format="yyyy-MM-dd HH:mm" /></td>
									<td>${patient.trueName}</td>
									<td>${equipment.name}</td>
									<td>${type}</td>
									<td>${exerciseTime}分钟</td>
									<td>${equipment.therapist.trueName}</td>
									<td><s:a cssStyle="color:#F00; cursor:pointer;"
											action="doctor_deleteRehabPlan?rehabPlan.id=%{id}"
											onclick="javascript: return window.confirm('确认要删除该计划吗？');">删除</s:a>
										&nbsp; &nbsp; &nbsp; <s:a
											cssStyle="color:#090; cursor:pointer;"
											action="doctor_rehabPlanShowUI?rehabPlan.id=%{id}">查看</s:a></td>
								</tr>
							</s:iterator>
							<tr style="border-top: 1px solid #999;">
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td><s:a cssStyle="color:#090; cursor:pointer;"
										action="doctor_addRehabPlanUI">制定康复计划</s:a></td>
							</tr>
						</table>
						<!-- 分页信息 -->
						<%@ include file="/WEB-INF/jsp/public/turnPage.jspf" %>
					</div>
				</div>

			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
		<s:form id="pageForm" action="doctor_rehabPlansList" method="post"></s:form>
	</div>
</body>
</html>
