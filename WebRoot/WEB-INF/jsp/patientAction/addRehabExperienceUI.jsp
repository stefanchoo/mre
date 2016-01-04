<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#rehabExperience").addClass('li_checked');
		KindEditor.ready(function(K) {
			window.editor = K.create('#editor_id', {
				resizeType : 0,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [ 'fontname', 'fontsize', '|', 'forecolor',
						'hilitecolor', 'bold', 'italic', 'underline',
						'removeformat', '|', 'justifyleft', 'justifycenter',
						'justifyright', 'insertorderedlist',
						'insertunorderedlist', '|', 'emoticons',
						'link' ]

			});
		});
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
					<div class="centerTitle">医疗状况 &gt; 康复经验 &gt; 撰写经验</div>
					<!-- 康复经验 -->
					<div class="editorBox">
					<s:form action="patient_addRehabExperience">
						<div class="editorKey">
							<div class="eidtorTitle">标题：</div>
							<div class="eidtorContent">内容：</div>
						</div>
						<div class="editorValue">
							<div class="titleValue"><s:textfield name="rehabExperience.title" cssStyle="display:block; width:650px; height:25px; margin-top:10px;"></s:textfield></div>
							<div class="contentValue"><s:textarea name="rehabExperience.content" id="editor_id" cssStyle="width:650px; height:500px;"></s:textarea></div>
							<div class="editorAddBtn"><button type="submit" style="width:50px;display:block; margin-top:10px; cursor:pointer">提交</button></div>
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
