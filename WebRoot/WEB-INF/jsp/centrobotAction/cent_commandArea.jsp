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
<title>命令控制区</title>
<style type="text/css">
@import url("${pageContext.request.contextPath}/style/css/init.css");

html {
	overflow-x: hidden;
}

.btnStyle {
	width: 100px;
	height: 50px;
	font-size: 18px;
	font-family: "微软雅黑";
	text-align: center;
	line-height: 50px;
	color: #FFF;
	font-size: bold;
	border: none;
	border-radius: 2px;
	background: #666;
	cursor: pointer;
	border: none;
	margin: 20px 0;
}

.firstLine {
	width: 780px;
	height: 60px;
	margin-top: 20px;
	margin-bottom: 20px;
}

.secondLine {
	width: 780px;
	height: 600px;
}

.graphIframeArea {
	width: 600px;
	height: 520px;
	border: 1px dashed #CCC;
	margin: 20px;
	float: left;
}

.sunCtrArea {
	width: 120px;
	height: 500px;
	float: right;
}

.thirdLine {
	width: 780px;
	height: 200px;
}
/* 遮罩层 */
#mask {
	display: none;
	position: fixed;
	left: 0;
	top: 0;
	right: 0;
	bottom: 0;
	background-color: #FFF;
	opacity: 0.8;
	z-index: 98;
}
/* 训练计划内容 */
#planContent {
	display: none;
	position: absolute;
	width: 400px;
	height: 500px;
	left: 50%;
	top: 50%;
	z-index: 99;
	margin-left: -250px;
	margin-top: -250px;
	border: 1px solid #999;
	color: #333;
	font-size: 16px;
}

#planContent ul li {
	width: 180px;
	height: 40px;
	display: block;
	font-size: 16px;
	color: #000;
	float: left;
}

#leftLabel li {
	text-align: right;
}

#rightValue li {
	padding-left: 18px;
	text-align: left;
}
</style>
<script type="text/javascript">
	$(function() {
		$(".btnStyle").mouseover(function() {
			$(this).css("background-color", "#0E9B07");
		});
		$(".btnStyle").mouseleave(function() {
			$(this).css("background-color", "#666");
		});
		letstart();
		// 可以尝试使用Ajax 来实现
		//setInterval(iframeReload, 2000);
	});

	function showtime() {
		var now = new Date();
		$("#currentTime").html(now.toLocaleString());
	}
	function letstart() {
		taskId = setInterval(showtime, 500);
	}
	function iframeReload() {
		graphIframe.location.reload();
	}
	
	/**
		* 遮罩层的代码实现
	**/
	function showMask() {
		$("#mask").css("display", "block");
	}
	function hideMask() {
		$("#mask").css("display", "none");
	}
	function showPlanContent() {
		$("#planContent").css("display", "block");
	}
	function hidePlanContent() {
		$("#planContent").css("display", "none");
	}
	// 使用Ajax 来与后台进行交互
	function transCommand(ob) {
		var devUID = $('#devUID').val();
		//alert(devUID);
		var command = ob.id;
		var data = $('#rehabPlanId').val();
		//alert(data);
		$.post('dev/centrobotData_browserCommandProcess.action',
				'clientData={"devUID": "' + devUID + '", "command": "'
						+ command + '", "data": "' + data + '"}', function() {
					alert('发送成功');
				});
	}
</script>
</head>

<body> 
	<s:hidden id="devUID" value="%{#devUID}"/>
	<s:hidden id="rehabPlanId" value="%{#rehabPlan.id}"/>
	<s:hidden id="gtrainingCount" value="%{#rehabPlan.centrobotRehabPlan.trainingCount}"/>
	<s:hidden id="gtimePerTraining" value="%{#rehabPlan.centrobotRehabPlan.timePerTraining}"/>
	<!-- 遮罩层 -->
	<div id="mask"></div>
	<!-- 内容提示框 -->
	<div id="planContent">
		<br />
		<center>
			<h3>计 划 内 容</h3>
		</center>
		<div style="float:left; width:198px; height:390px; margin-top: 20px;">
			<ul id="leftLabel">
				<li>姓名 :</li>
				<li>计划名称 :</li>
				<li>训练时间 :</li>
				<li>计划类型 :</li>
				<li>训练动作 :</li>
				<li>训练组数 :</li>
				<li>每组时长 :</li>
				<li>施力大小 :</li>
				<li>施力强度 :</li>
			</ul>
		</div>
		<div style="float:left; width:198px; height:390px; margin-top: 20px;">
			<ul id="rightValue">
				<li>${rehabPlan.patient.trueName}</li>
				<li>${rehabPlan.name}</li>
				<li><s:date name="%{#rehabPlan.time}" format="yyyy-MM-dd HH:mm" /></li>
				<li><s:property
						value="%{#rehabPlan.centrobotRehabPlan.subTrainingType != null ? #rehabPlan.centrobotRehabPlan.subTrainingType : #rehabPlan.centrobotRehabPlan.trainingType}" /></li>
				<li>${rehabPlan.centrobotRehabPlan.trainingMotion}</li>
				<li>${rehabPlan.centrobotRehabPlan.trainingCount }组</li>
				<li>${rehabPlan.centrobotRehabPlan.timePerTraining }分钟</li>
				<li>${rehabPlan.centrobotRehabPlan.forceValue}</li>
				<li>${rehabPlan.centrobotRehabPlan.forceRange}</li>
			</ul>
		</div>
		<div
			style="float:left; width:400px; height:40px;border-top:1px solid #CCC;">
			<center>
				<button onclick="hideMask();hidePlanContent();"
					style="margin-top:5px; width:100px; font-size:16px;height: 30px; cursor:pointer">确
					定</button>
			</center>
		</div>
	</div>
	<div class="firstLine">
		<div style="float:left">
			<button id="planContents" class="btnStyle" style="margin:5px 20px;"
				title="与设备同步训练计划的参数" onclick="transCommand(this);">同步计划</button>
			<span style="font-size:20px">&gt;&gt;</span>
			<button id="toPosition" class="btnStyle" style="margin:5px 20px;"
				title="机械臂移动到初始位置" onclick="transCommand(this);">就 位</button>
			<span style="font-size:20px">&gt;&gt;</span>
			<button id="start" class="btnStyle" style="margin:5px 20px;"
				title="训练计划开始" onclick="transCommand(this);">开始训练</button>
		</div>
		<div id="currentTime"
			style="width:220px; height:60px; line-height:60px; margin-right:10px; font-size:18px; font-size:bold; float:right">
		</div>
	</div>
	<div class="secondLine">
		<div class="graphIframeArea">
			<iframe name="graphIframe" frameborder="0" scrolling="no"
				height="100%" width="100%"
				src="www.nofate.net.cn/mre/centrobot_graphShow.action?rehabPlanId=${rehabPlan.centrobotRehabPlan.id}"></iframe>
		</div>
		<div class="sunCtrArea">
			<button class="btnStyle" title="查看训练计划"
				onclick="showMask();showPlanContent();">查看计划</button>
			<button id="pause" class="btnStyle" title="训练计划暂停"
				onclick="transCommand(this);">暂停训练</button>
			<button id="stop" class="btnStyle" title="训练计划停止"
				onclick="transCommand(this);">停止训练</button>
			<button id="toZero" class="btnStyle" title="机械臂回到零点"
				onclick="transCommand(this);">归 零</button>
			<button class="btnStyle" style="visibility: hidden"></button>
			<button class="btnStyle" title="生成报告" onclick="">生成报告</button>
		</div>
	</div>
	<div class="thirdLine">
		<button id="upForce" class="btnStyle" title="主动训练时的增加助力或阻力"
			style="margin: 0 20px;" onclick="transCommand(this);">增 力</button>
		<button id="downForce" class="btnStyle" title="主动训练时的减小助力或阻力"
			style="margin: 0 20px;" onclick="transCommand(this);">减 力</button>
		<button id="upSpeed" class="btnStyle" title="增加机器运转速度"
			style="margin: 0 20px;" onclick="transCommand(this);">加 速</button>
		<button id="downSpeed" class="btnStyle" title="减小机器运转速度"
			style="margin: 0 20px;" onclick="transCommand(this);">减 速</button>
	</div>
</body>
</html>
