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
	
	
	// >> end: 训练完成之后要做的几件事：
	// >> 1. ajax 传递后台数据：  {"rehabPlanId":"", "status" : "finished", "actualTime": ""}
	// >> 2. 点击生成报告后，跳转到报告页面，此时，取出数据，jqplot画出表格，保存为图片，以 !时间命名 ! 存入 rehabPlan
	
 	// warning: 当服务器没有任何输出时，而ajax访问正确时，必定是服务器代码出错，可以通过查看浏览器post url对应的response来查看错误情况

	$(function() {
		//initChart();
		// jquery在iframe中查找父类id进行操作
		// [格式] $('#id', parent.document)
		$('#start', parent.document).click(function() {
			startSampling();
		});

		$('#pause', parent.document).click(function() {
			pauseSampling();
		});

		$('#stop', parent.document).click(function() {
			stopSampling();
		});
	});

	/**
	 * 点击开始训练后的的 2s之后开始采集数据
	 * 暂停训练后立即暂停采集数据（要保存此时的samplingTime）
	 * 停止训练后立即停止采集数据
	 **/
	function startSampling() {
		if (lastQueryTime == 0) {
			setTimeout(initChart, 1000);
		} else {
			loop = setInterval(updateChart, 1000); // 开启定时器
		} 
		/* 测试一下时间 */
		/* startTime = new Date();
		for(var i = 0; i<1000000000; i++){	
		}
		endTime = new Date();
		alert(endTime - startTime); 
		*/
	}

	function pauseSampling() {
		//alert("暂停训练");
		clearInterval(loop);
	}

	function stopSampling() {
		//alert("停止训练");
		clearInterval(loop);
		lastQueryTime = 0;
		clearData();
		jqplot1 = null;
		jqplot2 = null;
		jqplot3 = null;
		jqplot4 = null ;
		jqplot5 = null;
		
	}

	// 声明全局变量，用来存储更新的数据,实验证明不可行，没有搞清楚为甚,数据加载出错
	var position_X = [ [] ]; //  x-t
	var position_Y = [ [] ]; //  y-t
	var position_Z = [ [] ]; //  z-t
	var velocity = [ [], [], [] ]; //  三关节角速度曲线
	var angle = [ [], [], [] ]; //  三关节角位移曲线  
	var jqplot1;
	var jqplot2;
	var jqplot3;
	var jqplot4;
	var jqplot5;

	var loop;
	var lastQueryTime = 0;
	var startTime = 0;                 // 开始训练时间
	var endTime = 0;                   // 结束训练时间
	
	
	var trainingCount = $("#gtrainingCount", parent.document).val();
	var trainingPerTime = $("#gtimePerTraining", parent.document).val(); // 单位为min
	var finishedCount = 0;
	/**
	 * 使用Ajax更新数据
	 * 使用JQuery的deferred對象來保存状态，进行函数的回调
		@return : json
	 **/
	function ajaxPost() {
		console.log("发送采样点：" + lastQueryTime);
		var defer = $.Deferred();
		var url = "http://www.nofate.net.cn/mre/dev/centrobotData_ajaxDataProcess.action";
		$.ajax({
			type : 'POST',
			url : url, // !注意这里需使用全称!
			cache : false,
			//async : false,     // ！设置成同步状态，意思是直到服务器成功返回数据，才退出！目前不支持在主线程中使用同步，因为在数据返回之前，线程会一直阻塞
			data : 'clientData=' + '{"rehabPlanId":'+ $("#rehabPlanId").val() + ',"samplingTime":'+ lastQueryTime +'}',
			dataType : 'json',
			success : function(severData) {
				defer.resolve(severData);
			}
		});
		return defer.promise();
	}

	/**
	 **  返回 boolean 对象
	 **/
	function initChart() {
		// 当 ... 函数执行完之后进入
		$.when(ajaxPost()).done(
				function(json) {
					// 判断json是否为空
					if(undefined == json){
						setTimeout(initChart, 1000);
						return;
					}
					var positionX = 0;
					var positionY = 0;
					var positionZ = 0;
					var armVelocity = 0;
					var shoulderVelocity = 0;
					var elbowVelocity = 0;
					var armAngle = 0;
					var shoulderAngle = 0;
					var elbowAngle = 0;
					var samplingTime = 0;
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

					$.each(json, function(index, item) {
						// 要将String类型强转成float类型
						positionX = json[index].positionX;
						positionY = json[index].positionY;
						positionZ = json[index].positionZ;
						armVelocity = json[index].armSpeed;
						shoulderVelocity = json[index].shoulderSpeed;
						elbowVelocity = json[index].elbowSpeed;
						armAngle = json[index].armDegree;
						shoulderAngle = json[index].shoulderDegree;
						elbowAngle = json[index].elbowDegree;
						samplingTime = json[index].samplingTime;

						lastQueryTime = samplingTime;
						
						// 填入数组
						position_X[0].push([ samplingTime, positionX ]);
						position_Y[0].push([ samplingTime, positionY ]);
						position_Z[0].push([ samplingTime, positionZ ]);

						velocity[0].push([ samplingTime, armVelocity ]);
						velocity[1].push([ samplingTime, shoulderVelocity ]);
						velocity[2].push([ samplingTime, elbowVelocity ]);

						angle[0].push([ samplingTime, armAngle ]);
						angle[1].push([ samplingTime, shoulderAngle ]);
						angle[2].push([ samplingTime, elbowAngle ]);
					});
					console.log("采样时间:" + lastQueryTime);
					console.log("初始化开始...");
					startTime = new Date();
					jqplot1 = showGraph('chart1', '水平面位置曲线', [], position_X);
					jqplot2 = showGraph('chart2', '冠状面位置曲线', [], position_Y);
					jqplot3 = showGraph('chart3', '矢状面位置曲线', [], position_Z);
					jqplot4 = showGraph('chart4', '三关节角速度曲线', [ '大关节', '肩关节','肘关节' ], velocity);
					jqplot5 = showGraph('chart5', '三关节角位移曲线', [ '大关节', '肩关节','肘关节' ], angle);
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
					console.log("删除成功...");
					// 初始化完成，直接调用loop();
					loop = setInterval(updateChart, 1000); // 开启定时器
				});

	}

	function updateChart() {
		/* if(undefined == lastQueryTime){
			// 数据传输完毕，清楚loop
			alert("数据采集完毕！");
			clearInterval(loop);
			return;
		} */
		
		// 计算一下 第  i 组结束没有
		if((lastQueryTime > (finishedCount+1)*trainingPerTime*60)){
			finishedCount++;
			if( finishedCount < trainingCount){
				pauseSampling();
				// 发送暂停指令
				$.post('dev/centrobotData_browserCommandProcess.action',
						'clientData={"devUID": "' + $("#devUID", parent.document).val() + '", "command": "'
								+ command + '", "data": "pause"}', function() {
							alert('第 ' + finishedCount + ' 组训练结束！');
						});
				$.post('dev/centrobotData_browserCommandProcess.action',
						'clientData={"devUID": "' + $("#devUID", parent.document).val() + '", "command": "'
								+ command + '", "data": "toPosition"}', function() {
						});
				setTimeout(startSampling, 3*60*1000);   // 休息三分钟,后开始下一组
			} 	
			else {
				endTime = new Date();  // 记录训练结束的时间
				clearInterval(loop);  // 数据采集完成，停止采集
				// 发送停止指令
				$.post('dev/centrobotData_browserCommandProcess.action',
						'clientData={"devUID": "' + $("#devUID", parent.document).val() + '", "command": "'
								+ command + '", "data": "stop"}', function() {
							alert("训练已经完成！");
						});
				garbageCollection();  // 这个函数实际上要使用ajax告诉服务器，采集结束之后要做的事情
				return;
			}
		}
		
		$.when(ajaxPost()).done(function(json) {
			if (undefined == json && lastQueryTime != 0 ) {
				endTime = new Date(); // 记录训练结束的时间
				clearInterval(loop);  // 数据采集完成，停止采集
				garbageCollection();  // 这个函数实际上要使用ajax告诉服务器，采集结束之后要做的事情
				alert("数据采集完毕！");
				return;
			}
			var positionX = 0;
			var positionY = 0;
			var positionZ = 0;
			var armVelocity = 0;
			var shoulderVelocity = 0;
			var elbowVelocity = 0;
			var armAngle = 0;
			var shoulderAngle = 0;
			var elbowAngle = 0;
			var samplingTime = 0;
			
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

			$.each(json, function(index, item) {
				// 要将String类型强转成float类型
				positionX = json[index].positionX;
				positionY = json[index].positionY;
				positionZ = json[index].positionZ;
				armVelocity = json[index].armSpeed;
				shoulderVelocity = json[index].shoulderSpeed;
				elbowVelocity = json[index].elbowSpeed;
				armAngle = json[index].armDegree;
				shoulderAngle = json[index].shoulderDegree;
				elbowAngle = json[index].elbowDegree;
				samplingTime = json[index].samplingTime;

				// 拿到最后的采样时间值
				lastQueryTime = samplingTime;
				position_X[0].push([ samplingTime, positionX ]);
				position_Y[0].push([ samplingTime, positionY ]);
				position_Z[0].push([ samplingTime, positionZ ]);

				velocity[0].push([ samplingTime, armVelocity ]);
				velocity[1].push([ samplingTime, shoulderVelocity ]);
				velocity[2].push([ samplingTime, elbowVelocity ]);

				angle[0].push([ samplingTime, armAngle ]);
				angle[1].push([ samplingTime, shoulderAngle ]);
				angle[2].push([ samplingTime, elbowAngle ]);
			});
			console.log("采样时间："+lastQueryTime);
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
	 * @param: ylabel -- 曲线的label
	 * @param: data -- function 数据来源
	 * $.jqplot(chartId, [], {dataRenderer : dataRenderer});
	 * 这里的 [] 位置上为空，意思是由dataRenderer来填充，但是必须
	 * 传入function的指针地址，自行计算。
	 * $.jqplot(chartId, data, {});
	 * 这里的data识别float型，原生数据传递过去就ok
	 * 直接传入data,需要格式为 [[1, 2], [2, 3], [3, 4]]这种
	 * 
	 **/
	function showGraph(chartId, title, ylabel, data) {
		 if(ylabel.length == 0)
			return $.jqplot(chartId, data, {
			title : title,
			series: [
			         { markerOptions: {show : true}}
			         ],
			axes : {
				xaxis : {
					pad : 0,
					show : true,
					showTicks : true
				}
			}
		});
		 else 
			 return $.jqplot(chartId, data, {
					title : title,
					legend: { show: true, location: 'ne' }, 
					series: [
			                    {
			                        label: ylabel[0],
			                        //lineWidth: 4, //线条粗细 
			                       //markerOptions: { size: 9, style: "circle" }  // 节点配置
			                        markerOptions: {show : true}
			                    },
			                    {
			                        label: ylabel[1],
			                        //lineWidth: 4, //线条粗细 
			                       //markerOptions: { size: 9, style: "circle" }  // 节点配置
			                        markerOptions: {show : true}
			                    },
			                    {
			                        label: ylabel[2],
			                        //lineWidth: 4, //线条粗细 
			                       //markerOptions: { size: 9, style: "circle" }  // 节点配置
			                        markerOptions: {show : true}
			                    }
			               ],  
					axes : {
						xaxis : {
							pad : 0,
							show : true,
							showTicks : true
						}
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
			//  更新data的值
			data : newData
		});
	}
	
	/**
		* 停止训练后，将采集到的数据清空（这里认为点击停止训练表示动作训练有问题，需重新开始）
	**/
	function clearData() {
		$.post(
				'dev/centrobotData_ajaxDataClearData.action',
				'clientData={"rehabPlanId":'+ $("#rehabPlanId").val()+'}', 
				function() {
					
				});
	}
	
	/**
	* 训练完成之后的一系列事物逻辑
	**/
	function garbageCollection(){
		if(endTime - startTime != 0){
			var url = "http://www.nofate.net.cn/mre/dev/centrobotData_ajaxDataFinish.action";
		// 1. 得到开始和结束时间，格式为localString
		    startTime = startTime.getTime();
			endTime = endTime.getTime();
		// 2. ajax 发送数据到服务器告知
			$.ajax({
				type : 'POST',
				url : url, // !注意这里需使用全称!
				cache : false,
				//async : false,     // ！设置成同步状态，意思是直到服务器成功返回数据，才退出！目前不支持在主线程中使用同步，因为在数据返回之前，线程会一直阻塞
				data : 'clientData=' + '{"rehabPlanId":'+ $("#rehabPlanId").val() + ',"startTime":'+ startTime +',"endTime":'+endTime+'}',
				dataType : 'json',
				success: function(){
					// alert(ok);
				}
			});
		}	
		
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



