<%@page import="com.mre.domain.User"%>
<%@page import="com.mre.util.Formatter"%>
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
		<%@ include file="/WEB-INF/jsp/public/newBannerPage.jspf" %>

		<div class="mainPage">
			<!-- 患者中心左边目录 -->
			<%@ include file="/WEB-INF/jsp/public/doctorLeftSidePage.jspf"%>
			<!-- 患者中心首页 -->
			<div class="rightSideTemplate">
				<div class="rightSide">
  	<div class="centerTitle">患者管理 &gt; 患者列表</div>
    <!--！这里可以搜索（在添加的时候，可以搜索用户名并且关联其为病人）、循环、支持分页！
    	字段为： 姓名 性别 疾患 训练计划 入院时间 操作（增 删 查）
        点击查看页面出现康复评估的状况，可以再对患者进行评估（量表的选择来自于康复量表管理）
     -->
    <div class="patientsList">
    <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="patients">
      <tr>
        <th width="15%" scope="col">姓名</th>
        <th width="15%" scope="col">性别</th>
        <th width="15%" scope="col">疾患</th>
        <!-- 这里其实还应该包括训练次数 -->
        <th width="20%" scope="col">训练计划（今日）</th>
        <th width="15%" scope="col">入院时间</th>
        <th width="15%" scope="col">操作</th>
      </tr>
      <!--  这里是要循环的 -->
      <s:iterator value="%{recordList}" status="status">
      <tr style="border-top: 1px solid #999;">
        <td>${trueName}</td>
        <td>${gender}</td>
        <td>${illness}</td>
        <td>
        	<s:if test="#todayRehabPlansList[#status.getIndex()] != 0">
        	<s:a action="doctor_todayRehabPlanUI">
        	<s:property value="#todayRehabPlansList[#status.getIndex()]"/>
        	</s:a>
        	</s:if>
        	<s:else>
        	0
        	</s:else>
        </td>
        <td><s:date name="beAdmissionTime" format="yyyy-MM-dd"/></td>
        <td><s:a cssStyle="color:#F00; cursor:pointer;" action="doctor_deletePatient?patient.id=%{id}" onclick="javascript:return window.confirm('确认要该删除此患者吗？')">删除</s:a> 
        &nbsp; &nbsp; &nbsp;
         <s:a cssStyle="color:#090; cursor:pointer;" action="doctor_patientShowUI?patient.id=%{id}">查看</s:a></td>
      </tr>
      </s:iterator>
       <tr style="border-top: 1px solid #999;">
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><s:a cssStyle="color:#090; cursor:pointer;" action="doctor_addPatientUI">添加患者</s:a></td>
      </tr>
    </table>
    <!-- 分页信息 -->
	<%@ include file="/WEB-INF/jsp/public/turnPage.jspf"%>
	<s:form id="pageForm" action="doctor_patientsList"
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
