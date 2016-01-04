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
					<div class="centerTitle">我的工作 &gt; 康复计划 &gt; 制定康复计划</div>
					<!-- 这里支持分页！
    			字段为： 时间 姓名 康复设备 训练模式 训练时间 操作（删 查）
     			--> <div class="rehabContent">
					<div class="rehabFieldArea">
						<strong>
						训练名称<br /> 患者<br />  开始时间<br /> 训练设备<br />  训练类型<br /> 训练时长(min)<br /> 
						主治医师<br/>
						</strong>
					</div>
					<s:form action="doctor_addRehabPlan">
					<div class="addRehabPropertyArea">
						<s:textfield name="rehabPlan.name" cssClass="inputStyle2"></s:textfield><br/>
						<s:select name="rehabPlan.patient.id" list="%{#patientsList}" listKey="id" listValue="trueName" cssClass="inputStyle2"></s:select><br/>
						 <span style="height:0px;visibility: hidden;"><s:date name="rehabPlan.time" format="yyyy-MM-dd HH:mm" var="rDateString"/></span>
						<s:textfield id="rehabPlanTime" name="rehabPlanTime" value="%{#rDateString}" cssClass="inputStyle2"/><br/>
						<s:select id="selectEquipment" name="rehabPlan.equipment.id" list="%{#equipmentUsedsList}" listKey="id" listValue="name"
									cssClass="inputStyle2">
						</s:select>
						<s:select name="rehabPlan.type" list="%{#typeList}" cssClass="inputStyle2"></s:select><br/>
						<s:textfield type="number" name="rehabPlan.exerciseTime" cssClass="inputStyle2"></s:textfield><br/> 	
						<s:textfield name="rehabPlan.doctor.trueName" value="%{#session.user.trueName}" cssClass="inputStyle2" readonly="true"></s:textfield>
					</div>
					<div class="addRehabPlanContent">
						<strong>训练内容</strong><br/>
						<s:textarea name="rehabPlan.content" cssClass="addRehabPlanContentStyle" readonly="%{isCompleted}"></s:textarea>
						<br/>
					</div>	
					<div class="saveArea">
						<input type="submit" value="保存" class="rehabSaveStyle" onclick="javascript:window.confirm('确定保存修改后的内容');"/>
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
