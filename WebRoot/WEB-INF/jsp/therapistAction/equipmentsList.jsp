<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/doctorcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#equipmentsList").addClass('li_checked');
	});
</script>
<title>设备管理</title>
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
					<div class="centerTitle">设备管理 &gt; 设备列表</div>
					<!-- 这里还没有做分页 -->
					<!-- 设备列表, 字段如下：
    						名称 编号 康复训练师 状态 操作
   					 -->
					<div class="equipmentsList">
						<table width="95%" border="0" align="center" cellpadding="0"
							cellspacing="0" id="equipments">
							<tr>
								<th width="30%" scope="col">名称</th>
								<th width="20%" scope="col">编号</th>
								<th width="15%" scope="col">康复训练师</th>
								<th width="15%" scope="col">状态</th>
								<th width="20%" scope="col">操作</th>
							</tr>
							<!--  这里是要循环的 -->
							<s:iterator value="%{recordList}">
								<tr style="border-top: 1px solid #999;">
									<td>${name}</td>
									<td>${number}</td>
									<td>${therapist.trueName}</td>
									<s:if test="%{status == 0}">
									<td>未运转</td>
									</s:if>
									<s:else>
									<td><span style="color:#090;">运转中</span></td>
									</s:else>
									<td><s:a cssStyle="color:#090; cursor:pointer;"
											action="therapist_equipmentShowUI?equipment.id=%{id}" target="_blank">查看</s:a>
									</td>
								</tr>
							</s:iterator>
						</table>
						<!-- 分页信息 -->
						<%@ include file="/WEB-INF/jsp/public/turnPage.jspf"%>
						<s:form id="pageForm" action="therapist_equipmentsList"
							method="post"></s:form>	
					</div>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
