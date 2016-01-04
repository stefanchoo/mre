<%@page import="antlr.JavaCodeGeneratorPrintWriterManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 设置清楚缓存，对于比较吃内存的页面是非常重要的！！！ -->
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<title>训练曲线</title>
<link
	href="${pageContext.request.contextPath}/style/css/jquery.jqplot.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery.jqplot.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jqplot.json2.min.js"></script>

<style type="text/css">
@import url("${pageContext.request.contextPath}/style/css/init.css");

html {
	overflow-x: hidden;
}
</style>

<script type="text/javascript">
	// 这里动态显示时吃内存的问题 drives me crazy
	// 1. empty() 与 remove()的区别和联系
	// -- remove() 删除所有子对象事件和数据包括自己，有返回值（被删除的内容），可恢复
	// -- empty() 清空所有子对象、事件和数据
	// 2. jqplot 画图的数据可以通过dataRenderer，自己动态给
	// 3. jqplot 重复创建会导致内存溢出
	// 4. 只是动态的更新数据，貌似可以避免内存溢出的问题
	// 5. 怎么实现，还不知道？--> 重绘表的函数中 replot:{data: "填入更新后的数据"}，问题解决
	// 6. 预测出现问题： 使用Ajax-json 进行前后台数据交换时，每次请求都会创建xmlHttpRequest对象，这个也是很吃内存的，而jquery无法删除，得想办法删除。
	// 7. 填入更新的数据

	// warning: 当服务器没有任何输出时，而ajax访问正确时，必定是服务器代码出错，可以通过查看浏览器post url对应的response来查看错误情况
	$(function() {
		/* jqplot1 = showGraph('chart1', '水平面位置曲线', sineRenderer);
		jqplot2 = showGraph('chart2', '冠状面位置曲线', sineRenderer);
		jqplot3 = showGraph('chart3', '矢状面位置曲线', sineRenderer);
		jqplot4 = showGraph('chart4', '三关节角速度曲线', sin1Renderer);
		jqplot5 = showGraph('chart5', '三关节角位移曲线', sin1Renderer); */
		initChart();
	});

	// 声明全局变量，用来存储更新的数据,实验证明不可行，没有搞清楚为甚,数据加载出错
	var position_X = [[]]; //  x-t
	var position_Y = [[]]; //  y-t
	var position_Z = [[]]; //  z-t
	var velocity = [ [], [], [] ]; //  三关节角速度曲线
	var angle = [ [], [], [] ]; //  三关节角位移曲线  
	var jqplot1;
	var jqplot2;
	var jqplot3;
	var jqplot4;
	var jqplot5;
	
	/**
	使用Ajax更新数据
	@return : json
	 **/
	function ajaxPost() {
		var rehabId = $("#rehabPlanId").val(); // 取出要传递的centrobotRehabPlanId
		var defer = $.Deferred();
		$.ajax({
					type : 'GET',
					url : 'http://www.nofate.net.cn/mre/json/centrobotData_ajaxDataProcess.action?clientData={"rehabPlanId" : "'
							+ rehabId + '"}', // !注意这里需使用全称!
					cache : false,
					//	async : false,     // ！设置成同步状态，意思是直到服务器成功返回数据，才退出！目前不支持在主线程中使用同步，因为在数据返回之前，线程会一直阻塞
					dataType : 'json',
					success : function(severData) {
						defer.resolve(severData);
					}
				});
		return defer.promise();
	}

	/**
	 **  返回json对象
	 **/
	function initChart() {
		$.when(ajaxPost()).done(function(json) {
			$.each(json, function(index, item) {
			if (undefined == json[index].result) {
				// 清空数组
				position_X[0] = [];
				position_Y[0] = [];
				position_Z[0] = [];
				velocity[0] = [];
				velocity[1] = [];
				velocity[2] = [];
				angle[0] = [];
				angle[1] = [];
				angle[2] = [];
				// 要将String类型强转成float类型
				var positionX = parseFloat(json[index].positionX).toFixed(2);
				var positionY = parseFloat(json[index].positionY).toFixed(2);
				var positionZ = parseFloat(json[index].positionZ).toFixed(2);
				var armVelocity = parseFloat(json[index].armSpeed).toFixed(2);
				var shoulderVelocity = parseFloat(json[index].shoulderSpeed).toFixed(2);
				var elbowVelocity = parseFloat(json[index].elbowSpeed).toFixed(2);
				var armAngle = parseFloat(json[index].armDegree).toFixed(2);
		        var shoulderAngle = parseFloat(json[index].shoulderDegree).toFixed(2);
		        var elbowAngle = parseFloat(json[index].elbowDegree).toFixed(2);
				var samplingTime = parseFloat(json[index].samplingTime).toFixed(2);
				
				position_X[0].push([ samplingTime, positionX ]);
				position_Y[0].push([ samplingTime, positionY ]);
				position_Z[0].push([ samplingTime, positionZ ]);
				
				velocity[0].push([ samplingTime, armVelocity ]);
				velocity[1].push([ samplingTime, shoulderVelocity ]);
				velocity[2].push([ samplingTime, elbowVelocity ]);
				
				angle[0].push([ samplingTime, armAngle ]);
				angle[1].push([ samplingTime, shoulderAngle ]);
				angle[2].push([ samplingTime, elbowAngle ]);
			}
		});	
			//position_X = sineRenderer();
			console.log("初始化开始...");
			jqplot1 = showGraph('chart1', '水平面位置曲线',  position_X);
			jqplot2 = showGraph('chart2', '冠状面位置曲线',  position_Y);
			jqplot3 = showGraph('chart3', '矢状面位置曲线',  position_Z);
		 	jqplot4 = showGraph('chart4', '三关节角速度曲线', velocity);
			jqplot5 = showGraph('chart5', '三关节角位移曲线', angle); 
			console.log("初始化结束...");
			
			// destroy object， free memory
			delete positionX;
			delete positionY;
			delete positionZ;
			delete armVelocity;  
			delete shoulderVelocity; 
			delete elbowVelocity; 
			delete armAngle;
			delete shoulderAngle;
			delete elbowAngle; 
			delete samplingTime; 
			return true;
		});
		return false;
	}
	
	function updateChart() {
		$.when(ajaxPost()).done(function(json) {	
			$.each(json, function(index, item) {
				if (undefined == json[index].result) {
					// 清空数组
					position_X[0] = [];
					position_Y[0] = [];
					position_Z[0] = [];
					velocity[0] = [];
					velocity[1] = [];
					velocity[2] = [];
					angle[0] = [];
					angle[1] = [];
					angle[2] = [];
					// 要将String类型强转成float类型
					var positionX = parseFloat(json[index].positionX).toFixed(2);
					var positionY = parseFloat(json[index].positionY).toFixed(2);
					var positionZ = parseFloat(json[index].positionZ).toFixed(2);
					var armVelocity = parseFloat(json[index].armSpeed).toFixed(2);
					var shoulderVelocity = parseFloat(json[index].shoulderSpeed).toFixed(2);
					var elbowVelocity = parseFloat(json[index].elbowSpeed).toFixed(2);
					var armAngle = parseFloat(json[index].armDegree).toFixed(2);
			        var shoulderAngle = parseFloat(json[index].shoulderDegree).toFixed(2);
			        var elbowAngle = parseFloat(json[index].elbowDegree).toFixed(2);
					var samplingTime = parseFloat(json[index].samplingTime).toFixed(2);
					
					position_X[0].push([ samplingTime, positionX ]);
					position_Y[0].push([ samplingTime, positionY ]);
					position_Z[0].push([ samplingTime, positionZ ]);
					
					velocity[0].push([ samplingTime, armVelocity ]);
					velocity[1].push([ samplingTime, shoulderVelocity ]);
					velocity[2].push([ samplingTime, elbowVelocity ]);
					
					angle[0].push([ samplingTime, armAngle ]);
					angle[1].push([ samplingTime, shoulderAngle ]);
					angle[2].push([ samplingTime, elbowAngle ]);

				}
			});	
			console.log("更新数据开始...");
			updateGraph(jqplot1, position_X);
			updateGraph(jqplot2, position_Y);
			updateGraph(jqplot3, position_Z);
			updateGraph(jqplot4, velocity);
			updateGraph(jqplot5, angle);
			console.log("更新数据结束...");
			
			// destroy object， free memory
			delete positionX;
			delete positionY;
			delete positionZ;
			delete armVelocity;  
			delete shoulderVelocity; 
			delete elbowVelocity; 
			delete armAngle;
			delete shoulderAngle;
			delete elbowAngle; 
			delete samplingTime;
		});
	}
	
	/**
	 * @param: chartId -- 图表区div的id
	 * @param: title -- 图表名称
	 * @param: dataRenderer -- function 数据来源
	 **/
	function showGraph(chartId, title, dataRenderer) {
		return $.jqplot(chartId, [], {
			title : title,
			data : dataRenderer,
			axes : {
				xaxis : {
					pad : 0

				}
			/* numberTicks : 5, // 横坐标的刻度个数
			tickInterval : 10 */
			// 每个刻度大小 ，貌似这里没有什么作用
			/* ,
							yaxis : {
								min : -3.5,
								max : 2
							} */
			}
		});
	}

	/**
	 * @param: plot -- 画图的句柄对象
	 * @param: newData -- 新的data值
	 **/
	function updateGraph(plot, newData) {
		//重绘图表               
		plot.replot({
			data : newData
		//  更新data的值
		//resetAxes : true
		});
	}
	
	
	/* $.each(json, function(index, item) {
	if (undefined == json[index].result) {
		// 清空数组
		position_X[0] = [];
		position_Y[0] = [];
		position_Z[0] = [];
		velocity[0] = [];
		velocity[1] = [];
		velocity[2] = [];
		angle[0] = [];
		angle[1] = [];
		angle[2] = [];
		// 要将String类型强转成float类型
		var positionX = parseFloat(json[index].positionX).toFixed(2);
		var positionY = parseFloat(json[index].positionY).toFixed(2);
		var positionZ = parseFloat(json[index].positionZ).toFixed(2);
		var armVelocity = parseFloat(json[index].armSpeed).toFixed(2);
		var shoulderVelocity = parseFloat(json[index].shoulderSpeed).toFixed(2);
		var elbowVelocity = parseFloat(json[index].elbowSpeed).toFixed(2);
		var armAngle = parseFloat(json[index].armDegree).toFixed(2);
        var shoulderAngle = parseFloat(json[index].shoulderDegree).toFixed(2);
        var elbowAngle = parseFloat(json[index].elbowDegree).toFixed(2);
		var samplingTime = parseFloat(json[index].samplingTime).toFixed(2);
		
		position_X[0].push([ samplingTime, positionX ]);
		position_Y[0].push([ samplingTime, positionY ]);
		position_Z[0].push([ samplingTime, positionZ ]);
		
		velocity[0].push([ samplingTime, armVelocity ]);
		velocity[1].push([ samplingTime, shoulderVelocity ]);
		velocity[2].push([ samplingTime, elbowVelocity ]);
		
		angle[0].push([ samplingTime, armAngle ]);
		angle[1].push([ samplingTime, shoulderAngle ]);
		angle[2].push([ samplingTime, elbowAngle ]);

	}
}); */
	/************************************************************************************/
	/**
	 * 获取position_x的位置坐标
	 * @param : json : 服务器返回的数据
	 **/
	function getNewData_X(json) {
		 position_X[0] = [];
		// 封装数据到数组
		$.each(json, function(index, item) {
			if (undefined == json[index].result) {
				// 要将String类型强转成float类型
				var positionX = parseFloat(json[index].positionX).toFixed(2);
				var samplingTime = parseFloat(json[index].samplingTime)
						.toFixed(2);
				position_X[0].push([ samplingTime, positionX ]);
			}
		});
		return position_X;
	}

	/**
	 * 获取position_Y的位置坐标
	 * @param : json : 服务器返回的数据
	 **/
	function getNewData_Y(json) {
		position_Y[0] = [];
		// 封装数据到数组
		$.each(json, function(index, item) {
			if (undefined == json[index].result) {
				// 要将String类型强转成float类型
				var positionY = parseFloat(json[index].positionY).toFixed(2);
				var samplingTime = parseFloat(json[index].samplingTime)
						.toFixed(2);
				position_Y[0].push([ samplingTime, positionY ]);
			}
		});
		return position_Y;
	}

	/**
	 * 获取position_Z的位置坐标
	 * @param : json : 服务器返回的数据
	 **/
	function getNewData_Z(json) {
		position_Z[0] = [];
		// 封装数据到数组
		$.each(json, function(index, item) {
			if (undefined == json[index].result) {
				// 要将String类型强转成float类型
				var positionZ = parseFloat(json[index].positionZ).toFixed(2);
				var samplingTime = parseFloat(json[index].samplingTime)
						.toFixed(2);
				position_Z[0].push([ samplingTime, positionZ ]);
			}
		});
		return position_Z;
	}

	/**
	 * 获取三关节的角速度
	 * @param : json : 服务器返回的数据
	 **/
	function getNewData_V(json) {
		velocity[0] = [];
		velocity[1] = [];
		velocity[2] = [];
		// 封装数据到数组
		$.each(json, function(index, item) {
			if (undefined == json[index].result) {
				// 要将String类型强转成float类型
				var armVelocity = parseFloat(json[index].armSpeed).toFixed(2);
				var shoulderVelocity = parseFloat(json[index].shoulderSpeed)
						.toFixed(2);
				var elbowVelocity = parseFloat(json[index].elbowSpeed).toFixed(
						2);
				var samplingTime = parseFloat(json[index].samplingTime)
						.toFixed(2);
				velocity[0].push([ samplingTime, armVelocity ]);
				velocity[1].push([ samplingTime, shoulderVelocity ]);
				velocity[2].push([ samplingTime, elbowVelocity ]);
			}
		});
		return velocity;
	}

	/**
	 * 获取三关节的角位移
	 * @param : json : 服务器返回的数据
	 **/
	function getNewData_A(json) {
		angle[0] = [];
		angle[1] = [];
		angle[2] = [];
		// 封装数据到数组
		$.each(json,
				function(index, item) {
					if (undefined == json[index].result) {
						// 要将String类型强转成float类型
						var armAngle = parseFloat(json[index].armDegree)
								.toFixed(2);
						var shoulderAngle = parseFloat(
								json[index].shoulderDegree).toFixed(2);
						var elbowAngle = parseFloat(json[index].elbowDegree)
								.toFixed(2);
						var samplingTime = parseFloat(json[index].samplingTime)
								.toFixed(2);
						angle[0].push([ samplingTime, armAngle ]);
						angle[1].push([ samplingTime, shoulderAngle ]);
						angle[2].push([ samplingTime, elbowAngle ]);
					}
				});
		return angle;
	}

 	var j = 0;
	function sineRenderer() {
		var data = [ [] ];
		for (var i = j; i < j + 15; i += 0.5) {
			data[0].push([ i, Math.sin(i) ]);
		}
		delete i;
		return data;
	}
	function sin1Renderer() {
		var data = [ [], [], [] ];
		for (var i = j; i < j + 15; i += 0.5) {
			data[0].push([ i, Math.sin(i) ]);
			data[1].push([ i, Math.sin(i) - 1 ]);
			data[2].push([ i, Math.sin(i) - 2 ]);
		}
		delete i;
		return data;
	}
 
	function freeMemory() {
		// 清除点之前的div,注意 empty删除子内容，remove是删除所有包括自己
		$("#chart1").empty();
		$("#chart2").empty();
		$("#chart3").empty();
		$("#chart4").empty();
		$("#chart5").empty();
	} 
	
/******************************************************************************/	
</script>
</head>
<body>
	<div id="chart">
		<s:hidden id="rehabPlanId" value="%{#rehabPlanId}" />
		<div id="chart1" style="width:200px;height:220px; float: left;">
		</div>
		<div id="chart2" style="width:200px;height:220px; float: left;">
		</div>
		<div id="chart3" style="width:200px;height:220px; float: left;">
		</div>
		<div id="chart4" style="width:300px;height:300px; float: left;">
		</div>
		<div id="chart5" style="width:300px;height:300px; float: left;">
		</div>
	</div>
</body>
</html>



