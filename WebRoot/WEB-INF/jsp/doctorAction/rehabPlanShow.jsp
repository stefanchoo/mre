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
     			--> <div class="rehabContent">
					<div class="fieldArealh42">
						<strong>
						训练名称<br /> 患者<br /> 疾患<br /> 开始时间<br /> 训练设备<br />  训练类型<br /> 训练时长(min)<br /> 
						实际时长(min)<br />治疗师<br/>主治医师<br/> 完成状态<br />
						</strong>
					</div>
					<s:form action="doctor_updateRehabPlan">
		 			    <s:textfield id="rehabPlanId" type="hidden" name="rehabPlan.id"/>
						<s:textfield type="hidden" name="rehabPlan.patient.id"/>
						<s:textfield type="hidden" name="rehabPlan.doctor.id"/>
					<div class="rehabPropertyArea">
						<s:textfield name="rehabPlan.name" cssClass="inputStyle2" readonly="%{completed}"></s:textfield><br/>
						<s:textfield name="rehabPlan.patient.trueName" cssClass="inputStyle2" readonly="true"></s:textfield><br/>
						<s:textfield name="rehabPlan.patient.illness" cssClass="inputStyle2" readonly="true"></s:textfield><br/>
						 <span style="height:0px;visibility: hidden;"><s:date name="rehabPlan.time" format="yyyy-MM-dd HH:mm" var="rDateString"/></span>
						<s:textfield id="rehabPlanTime" name="rehabPlanTime" value="%{#rDateString}" cssClass="inputStyle2" disabled="%{completed}"/><br/>
						<s:select id="selectEquipment" name="rehabPlan.equipment.id" list="%{#equipmentUsedsList}" listKey="id" listValue="name"
									headKey="equipment.id" headValue="equipment.name" cssClass="inputStyle2"
									disabled="%{completed}" >
						</s:select> 
						<!-- 这里传递一个rehabPlanId 根据这个得到patientId 和  equipmentUsed, 进而选择进入哪一个设备页面 -->
						<s:if test="%{!completed}">
						<s:a id="setParams" cssStyle="cursor:pointer;color:#0C0;height:30px; line-height:38px; display:block; float:right;" action="doctor_setEquipmentParams?rehabPlan.id=%{rehabPlan.id}" target="_blank">参数设置</s:a><br/>
						</s:if>
						<s:select name="rehabPlan.type" list="%{#typeList}" cssClass="inputStyle2" disabled="%{completed}"></s:select><br/>
						<s:textfield type="number" name="rehabPlan.exerciseTime" cssClass="inputStyle2" readonly="%{completed}"></s:textfield><br/> 	
						<s:textfield name="rehabPlan.actualExerciseTime" cssClass="inputStyle2" readonly="true"></s:textfield><br/>
						<s:textfield name="rehabPlan.equipment.therapist.trueName" cssClass="inputStyle2" readonly="true"></s:textfield>
						<s:textfield name="rehabPlan.doctor.trueName" cssClass="inputStyle2" readonly="true"></s:textfield>
						<s:textfield name="completedStatus" cssClass="inputStyle2" readonly="true" value="%{completed ? '已完成' : '未完成'}"></s:textfield>
					</div>
					<div class="rehabPlanContent">
						<strong>训练内容</strong>
						<s:textarea name="rehabPlan.content" cssClass="rehabPlanContentStyle" readonly="%{completed}" ></s:textarea>
						<br/>
						<strong>训练结果</strong>
						<s:textarea name="rehabPlan.result" cssClass="rehabPlanContentStyle" readonly="%{completed}" ></s:textarea>
					</div>	
					<div class="saveArea">
						<s:if test="%{!completed}">
						<input type="submit" value="保存修改" class="saveStyle" onclick="javascript:window.confirm('确定保存修改后的内容');"/>
						</s:if>
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
