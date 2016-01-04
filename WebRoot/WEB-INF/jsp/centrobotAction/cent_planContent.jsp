<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/style/css/jquery-ui.min.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery-ui.min.js"></script>
<title>参数设置</title>
<style type="text/css">
@import url("${pageContext.request.contextPath}/style/css/init.css");

html {
	overflow-x: hidden;
}

.cent_planContent {
	width: 800px;
	height: 400px;
	border: 1px solid #CCC;
	border-radius: 3px;
}

.cent_paramSetup {
	margin: 25px;
	width: 349px;
	height: 349px;
	float: left;
}

.cent_trainingMode {
	margin: 5px;
	width: 389px;
	height: 389px;
	float: right;
	border-left: 1px solid #CCC;
	font-size: 16px;
}

.cent_w350h175 {
	width: 349px;
	height: 175px;
	font-size: 16px;
	line-height: 60px;
}

.cent_lw120 {
	width: 120px;
	height: 200px;
	float: left;
	text-align: right;
}

.cent_rw195 {
	width: 215px;
	height: 200px;
	float: right;
	text-align: left;
}

div input {
	width: 160px;
	height: 30px;
	text-align: center;
	font-size: 16px;
	font-family: "微软雅黑"
}

.radioStyle1 {
	width: 160px;
	height: 20px;
}

.radioStyle2 {
	width: 50px;
	height: 20px;
}

.trainingTypeSelect {
	margin: 10px auto;
	width: 350px;
	height: 40px;
	float: left;
}

.trainingModeSelect {
	margin: 25px;
	width: 340px;
	height: 110px;
	float: left;
}

.operateArea {
	width: 349px;
	height: 50px;
	margin: 10px;
	text-align: right;
	float: right;
}

select {
	width: 200px;
	height: 30px;
	line-height: 30px;
	font-size: 16px;
	font-family: "微软雅黑";
	color: #000;
}

#slider {
	width: 200px;
	height: 8px;
	background: #CCC;
}

.cent_patientRehabPlan {
	margin-top: 10px;
	width: 800px;
	height: auto;
	float: left;
}

.cent_patientRehabPlanList {
	width: 800px;
	height: 346px;
	border: 1px solid #CCC;
	border-radius: 3px;
	float: left;
}

#cent_rehabPlans {
	display: block;
	margin-top: 20px;
	border-top: 1px solid #000;
	border-bottom: 1px solid #000;
	font-size: 14px;
	line-height: 30px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#saveBtn").mouseleave(function() {
			$(this).css('background', "#666");
		});
		$("#saveBtn").mousemove(function() {
			$(this).css('background', "#090");
		});
		$("#slider").slider({
			value : $("#forceCount").val(),
			range : "min",
			min : 0,
			max : 10,
			slide : function(event, ui) {
				$("#forceCount").val(ui.value);
			}
		});
		$("#forceCount").val($("#slider").slider("value"));

		// 默认医生制订的训练类型不可更换
		/* $("input[type=radio][name='model.trainingType']").click(
				function(){
					$("#centrobotPlanForm").submit();
				}); */
	});
</script>
</head>

<body>
	<div class="cent_planContent">
		<s:form id="centrobotPlanForm" action="centrobot_updateRehabPlan"
			method="post">
			<s:hidden name="model.id" />
			<s:hidden name="patientId">${rehabPlan.patient.id}</s:hidden>
			<div class="cent_paramSetup">
				<div class="cent_w350h175">
					<div class="cent_lw120">
						训练时间：<br /> 训练次数：<br /> 每次时长：<br /> <br /> 训练类型：<br />

					</div>
					<div class="cent_rw195">
						<s:date name="model.rehabPlan.time" format="yyyy-MM-dd HH:mm" />
						&nbsp; <br />
						<s:textfield name="model.trainingCount" type="number" />
						次<br />
						<s:textfield name="model.timePerTraining" type="number" />
						分钟<br /> <br />
						<s:textfield name="model.trainingType" readonly="true"
							cssStyle="border:0px; height:50px; font-size:28px" />
					</div>
				</div>
				<div class="cent_w350h175">
					<!-- 提交的时候，被动训练提交 主动训练提交 助力/ 阻力 -->
					<%-- <s:radio id="trainingType" name="model.trainingType" list="{'被动训练','主动训练'}" cssClass="radioStyle1"></s:radio> --%>
					<!--  <div class="trainingTypeSelect">
      <input id="trainingType1" name="model.trainingType" type="radio" class="radioStyle1" checked="checked"/> <label for="trainingType1">被动训练</label>
      </div>
       <div class="trainingTypeSelect">
      <input id="trainingType2" name="model.trainingType" type="radio" class="radioStyle1"/> <label for="trainingType2">主动训练</label>
      </div> -->
				</div>
			</div>

			<div class="cent_trainingMode">
				<s:if test="%{#model.trainingType == '被动训练'}">
					<!-- 被动训练选择 -->
					<div class="trainingModeSelect">
						动作选择：
						<s:select name="model.trainingMotion"
							list="{'预定义被动训练动作1','预定义被动训练动作2','预定义被动训练动作3',
      '自定义被动训练动作1','自定义被动训练动作2','自定义被动训练动作2'}">
						</s:select>
					</div>
				</s:if>
				<s:else>
					<!-- 主动训练选择 -->
					<div class="trainingModeSelect" style="height:270px;">
						<s:radio name="model.subTrainingType"
							list="{'方向助力训练','肌电助力训练','自由模式训练','阻力训练'}" cssClass="radioStyle2" />
						<br /> <br /> 动作选择：
						<s:select name="model.trainingMotion"
							list="{'预定义助力训练动作1','预定义助力训练动作2','预定义助力训练动作3','预定义阻力训练动作1','预定义阻力训练动作2','预定义阻力训练动作3'}">
						</s:select>
						<br /> <br />
						<p>
							<label for="forceCount">施力大小:</label>
							<s:textfield type="text" id="forceCount" name="model.forceValue"
								readonly="true"
								cssStyle="border:0; width:40px; color:#090; font-weight:bold;" />
						</p>
						<br />
						<div id="slider"></div>
						<br />
						<p>
							<label for="forceValue">施力强度：</label> <input type="text"
								id="forceValue" readonly="readonly"
								style="border:0px; width:40px; color:#090; font-weight:bold" />
						</p>
						<br />
						<s:radio name="model.forceRange" list="{'小','中','大'}"
							cssClass="radioStyle2" />
					</div>
				</s:else>

				<div class="operateArea">
					<input id="saveBtn" type="submit" value="保存修改"
						style="width:80px; font-family:'微软雅黑';cursor:pointer; background:#666; color:#FFF; border: none; border-radius:2px;" />
				</div>
			</div>
		</s:form>
	</div>
	<div class="cent_patientRehabPlan">
		<span
			style="display:block; float:left; width:800px; font-size:18px; height: 40px;">患者待完成计划</span>
		<div class="cent_patientRehabPlanList">
			<table width="95%" border="0" align="center" cellpadding="0"
				cellspacing="0" id="cent_rehabPlans">
				<tr>
					<th width="20%" scope="col">训练时间</th>
					<th width="10%" scope="col">训练类型</th>
					<th width="20%" scope="col">训练次数/每次时长(min)</th>
					<th width="20%" scope="col">动作名称</th>
					<th width="10%" scope="col">施力大小</th>
					<th width="10%" scope="col">施力强度</th>
					<th width="10%" scope="col">操作</th>
				</tr>
				<s:iterator value="modelList">
					<s:if test="%{id == #model.id}">
						<tr style="border-top: 1px solid #999; background-color:#CCC;">
					</s:if>
					<s:else>
						<tr style="border-top: 1px solid #999;">
					</s:else>
					<td><s:date name="rehabPlan.time" format="yyyy-MM-dd HH:mm" /></td>
					<td>${trainingType}</td>
					<td>${trainingCount}/ ${timePerTraining}</td>
					<td>${trainingMotion}</td>
					<td>${forceValue}</td>
					<td>${forceRange}</td>
					<td><s:a
							action="centrobot_cent_planContent?id=%{id}&patientId=%{rehabPlan.patient.id}"
							cssStyle="color:#090; cursor:pointer;">查看</s:a></td>
					</tr>
				</s:iterator>
			</table>
		</div>
	</div>
</body>
</html>
