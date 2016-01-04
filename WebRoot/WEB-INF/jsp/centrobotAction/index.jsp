<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/centrobotAction/public/centrobotcss.jspf" %>
<title>Centrobot上肢康复训练系统 - 首页</title>
<script type="text/javascript">
	$(function(){
		$("#cent_index").addClass("navi_checked");	
		});
</script>
</head>
<!--  SSH + jFreeChart画图 -->
<!-- SSH + highcharts + jQuery -->
<body>
  
<!-- 首页 -->
<%@ include file="/WEB-INF/jsp/public/loginTopPage.jspf"%>

<!-- 设置居中 放大缩小时，始终处于中间位置-->
<div class="bodyPage">
<!-- bannerPage -->
<%@ include file="/WEB-INF/jsp/centrobotAction/public/centrobotBannerPage.jspf" %>
<div class="cent_indexPage">
</div>
</div>
</body>
</html>
 