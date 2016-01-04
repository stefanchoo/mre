<%@page import="com.mre.domain.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/public/patientcssjs.jspf"%>
<script type="text/javascript">
	$(function() {
		$("#sendBox").addClass('li_checked');
		KindEditor.ready(function(K) {
			window.editor = K.create('#editor_content', {
				resizeType : 0,
				readonlyMode : true,
				items : []
			});
		});
	});
</script>
<title>站内信</title>
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
					<div class="centerTitle">站内信息 &gt; 发件箱</div>
					<div
						style="width:800px; height:auto; font-size:16px; margin:20px 40px; background:#DDD; padding:10px;">
						<p style="height:30px">
							<strong>收件人</strong>：${message.toUser.trueName}
							<span style="display:block;float:right;">
							<s:a
									cssClass="delete"
									action="patient_deleteSendMessage?message.id=%{message.id}"
									onclick="javascript:return window.confirm('确定要删除此邮件？');">删除</s:a></span>
						</p>
						<p style="height:30px;">
							<strong>时间：</strong>
							<s:date name="%{#message.postTime}"
								format="yyyy-MM-dd HH:mm:ss" />
						</p>

						<p style="height:30px;"><strong>主题：</strong>${message.title}</p>

						<p style="height:30px;"><strong>内容：</strong></p>
						
						<div class="Content">
						<textarea id="editor_content" style="width:780px;height:350px;" readonly="readonly">${message.content}</textarea>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<!-- Footer Page -->
		<%@ include file="/WEB-INF/jsp/public/footerPage.jspf"%>
	</div>

</body>
</html>