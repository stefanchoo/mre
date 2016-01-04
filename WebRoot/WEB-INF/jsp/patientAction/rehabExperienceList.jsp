<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#rehabExperience").addClass('li_checked');
	});
</script>
<title>医疗状况</title>
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
					<div class="centerTitle">医疗状况 &gt; 康复经验</div>
					<!-- 康复经验 -->
					<!--  类似于医疗日记 呈现形式为：上面显示日记记录, 下面可以快速编辑  -->
					<div class="rehabExperience">
						<div class="experienceList">
							<ul>
								<s:iterator value="%{recordList}">
									<li><s:a
											action="patient_rehabExperienceShowUI?rehabExperience.id=%{id}">${title}</s:a>
										<span style="text-align:right;"><s:date
												name="%{postTime}" format="yyyy-MM-dd HH:mm:ss" /></span></li>
								</s:iterator>
							</ul>
							<div class="patientBtnStyle">
								<s:a action="patient_addRehabExperienceUI">撰写康复经验</s:a>
							</div>
						</div>
						<!-- 分页信息 -->
						<%@ include file="/WEB-INF/jsp/public/turnPage.jspf"%>
						<s:form id="pageForm" action="patient_rehabExperienceList"
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