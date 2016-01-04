<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/WEB-INF/jsp/centrobotAction/public/centrobotcss.jspf" %>
<title>Centrobot上肢康复训练系统 - 使用帮助</title>
<script type="text/javascript">
$(function(){
	$("#cent_help").addClass("navi_checked");	
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
<div class="cent_helpPage">
    	<br/>
        <br/>
    	<center>
        <h2 style="color:#403E3C; font-size:20px;">欢迎您使用Centrobot智能交互式上肢康复机器人训练系统</h2>
        </center>
        <br />
        <br />
        <p style="text-indent:2em; font-size:18px; ">我们竭诚推荐您按照以下步骤使用该系统:</p>
        <br />
        <p style="text-indent:2em; font-size:16px;">
        1. <font style="color:#F00">医生</font> 或者 <font style="color:#F00">康复医师</font> 在MRE医疗网络平台的个人中心设备控制菜单中点击进入系统 
        </p>
        <br />
        <p style="text-indent:2em; font-size:16px;">
        2. 点击页面右上角 <font style="color:#090">"连接状态"</font> 的按钮，连接设备
         </p>
         <br />
        <p style="text-indent:2em; font-size:16px;">
        3. 提示 <font style="color:#090">"连接成功"</font> 后，可以正常使用该系统，如 <font style="color:#F00">"连接失败"</font> ，请检查您的网络连接。
         </p>
        <br />
        <hr />
        <br />
        <p style="text-indent:2em; font-size:18px; ">该系统向您提供一下服务:</p>
         <br />
        <p style="text-indent:2em; font-size:16px;">
        1. <font style="color:#090">"首页"</font> 为Centrobot上肢康复机器人的介绍 
        </p>
        <br />
        <p style="text-indent:2em; font-size:16px;">
        2. <font style="color:#090">"患者列表"</font> 页面可以查看当天使用该设备训练的患者及其训练报告
        </p>
        <br />
        <p style="text-indent:2em; font-size:16px;">
        3. <font style="color:#090">"计划定制"</font> 页面可以查看和设置患者训练时设备的相关参数
        </p>
        <br />
        <p style="text-indent:2em; font-size:16px;">
        4. <font style="color:#090">"康复训练"</font> 页面可以实时监测和控制训练时设备的运行状态，训练完成后，会生成训练报告，您可以
        <br/><br /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填写其中的训练效果
        </p>
        <br />
        <p style="text-indent:2em; font-size:16px;">
        5. <font style="color:#090">"设备调试"</font> 页面可以实时监测和控制训练时设备的运行状况
        </p>
        <br />
        <p style="text-indent:2em; font-size:16px;">
        6. <font style="color:#090">"使用帮助"</font> 页面显示使用的步骤及系统提供的服务
        </p>
    </div>
</div>
</body>
</html>
 