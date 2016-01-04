<%@page import="com.mre.domain.User"%>
<%@page import="com.mre.util.Formatter"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#todayPlan").addClass('li_checked');
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
					<div class="centerTitle">我的工作 &gt; 今日计划</div>
					<!-- 这里支持分页！
    					  字段为： 时间 姓名 康复设备 训练模式 训练时间 操作（删 查）
     				-->
					<div class="planList">
						<table width="95%" border="0" align="center" cellpadding="0"
							cellspacing="0" id="plans">
							<tr>
								<th width="12%" scope="col">时间</th>
								<th width="12%" scope="col">姓名</th>
								<th width="25%" scope="col">康复设备</th>
								<!-- 这里其实还应该包括训练次数 -->
								<th width="12%" scope="col">训练模式</th>
								<th width="12%" scope="col">训练时间</th>
								<th width="12%" scope="col">康复训练师</th>
								<th width="15%" scope="col">操作</th>
							</tr>
							<!--  这里是要循环的 -->
							<s:iterator value="%{#rehabPlansList}">
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
						</table>
					</div>
				</div>

			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
