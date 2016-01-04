<%@page import="antlr.JavaCodeGeneratorPrintWriterManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link
	href="${pageContext.request.contextPath}/style/css/jquery.jqplot.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery-1.8.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jquery.jqplot.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/script/jqplot.json2.min.js"></script>
<title>病人报告</title>
<style type="text/css">
@import url("${pageContext.request.contextPath}/style/css/init.css");
html { overflow-x:hidden;}
.cent_reportSelect{margin:20px; width: 760px; height: 50px; float:left; font-size:16px; border-bottom: 1px dashed #CCCCCC;}
select{width:250px; height:30px; line-height: 30px;font-size:16px; font-family:"微软雅黑"; color:#000;}
.cent_patientReport {width: 800px; height:auto; font-size:16px;}
.cent_patientReport p{text-indent:2em;}
#cent_trainingResult{border-top: 1px solid #000; border-bottom: 1px solid #000; font-size: 14px; line-height:28px;}
</style>

<script type="text/javascript" >
	$(function(){
		$("#saveBtn").mouseleave(function(){
			$(this).css('background',"#666");
			});
		$("#saveBtn").mousemove(function(){
			$(this).css('background',"#090");
			});
		var date = new Date();
		var curDate = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
		$("#curDate").html(curDate);
		initAndShowGraph();	 
	});
	
	/**
		* 初始化并获取数据展示图表	
	**/
	function initAndShowGraph(){
		$.when(ajaxPost()).done(function(json){
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
			
			var position_X = [[]];
			var position_Y = [[]];
			var position_Z = [[]];
			var velocity = [[],[],[]];
			var angle = [[],[],[]];
			//alert("Data gottach");
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
			showGraph('chart1', '水平面位置曲线', [], position_X);                   
			showGraph('chart2', '冠状面位置曲线', [], position_Y);                   
			showGraph('chart3', '矢状面位置曲线', [], position_Z);                   
			showGraph('chart4', '三关节角速度曲线', [ '大关节', '肩关节','肘关节' ], velocity);
			showGraph('chart5', '三关节角位移曲线', [ '大关节', '肩关节','肘关节' ], angle);   
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
			         { markerOptions: {show : false}}
			         ],
			axes : {
				xaxis : {
					pad : 0,
					show : false,
					showTicks : false
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
			                        markerOptions: {show : false}
			                    },
			                    {
			                        label: ylabel[1],
			                        //lineWidth: 4, //线条粗细 
			                       //markerOptions: { size: 9, style: "circle" }  // 节点配置
			                        markerOptions: {show : false}
			                    },
			                    {
			                        label: ylabel[2],
			                        //lineWidth: 4, //线条粗细 
			                       //markerOptions: { size: 9, style: "circle" }  // 节点配置
			                        markerOptions: {show : false}
			                    }
			               ],  
					axes : {
						xaxis : {
							pad : 0,
							show : false,
							showTicks : false
						}
					}
				});
	}

	 /**
		 * 使用Ajax更新数据
		 * 使用JQuery的deferred對象來保存状态，进行函数的回调
			@return : json
		 **/
		function ajaxPost() {
			var defer = $.Deferred();
			var url = "http://www.nofate.net.cn/mre/dev/centrobotData_ajaxDataReport.action";
			$.ajax({
				type : 'POST',
				url : url, // !注意这里需使用全称!
				cache : false,
				//async : false,     // ！设置成同步状态，意思是直到服务器成功返回数据，才退出！目前不支持在主线程中使用同步，因为在数据返回之前，线程会一直阻塞
				data : 'clientData=' + '{"rehabPlanId":'+ $("#rehabPlanId").val() + '}',
				dataType : 'json',
				success : function(severData) {
					defer.resolve(severData);
				}
			});
			return defer.promise();
		}
</script>

</head>

<body>

<div class="cent_reportSelect">
<s:form action="centrobot_cent_patientReport" method="post" >
<s:hidden name="patientId"  value="%{#model.rehabPlan.patient.id}"/>
    <strong>报告选择：</strong>
	<s:select name="model.id" list="%{#modelList}" listKey="id" listValue="basicShow()"/>
    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
    <input id="saveBtn" type="submit" value="确定" style="width:60px; height:30px; font-size:14px; font-family:'微软雅黑';cursor:pointer; background:#666; color:#FFF; border: none; border-radius:2px;"/>
</s:form>
</div>

<div class="cent_patientReport">
	<center><h2 style="font-size:20px;">Centrobot上肢康复机器人训练报告</h2></center>
    <br /><br />
    <p>病历号： <span style="text-decoration:underline;"> 1001 </span></p>
    <br />
    <p>康复医师： <span style="text-decoration:underline;"> ${model.rehabPlan.equipment.therapist.trueName} </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       训练日期： <span style="text-decoration:underline;"> <s:date name="%{#model.rehabPlan.time}" format="yyyy-MM-dd" /> </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       打印日期： <span id="curDate" style="text-decoration:underline;">  </span>
    </p>
    <br />
    <p>患者姓名：<span style="text-decoration:underline;"> ${model.rehabPlan.patient.trueName} </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       性别： <span style="text-decoration:underline;"> ${model.rehabPlan.patient.gender} </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       年龄： <span style="text-decoration:underline;"> ${model.rehabPlan.patient.age()} </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       临床诊断： <span style="text-decoration:underline;"> ${model.rehabPlan.patient.illness} </span>
    </p>
    <br />
    <p style="font-weight:bold">训练效果</p>
    <br />
    <s:hidden id="rehabPlanId" value="%{#model.id}"></s:hidden>
    <table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" id="cent_trainingResult">
      <tr>
        <th width="20%" scope="col">训练类型</th>
        <th width="30%" scope="col">训练次数/每次用时(min)</th>
        <th width="15%" scope="col">开始时间</th>
        <!-- 这里其实还应该包括训练次数 -->
        <th width="15%" scope="col">结束时间</th>
        <th width="20%" scope="col">效果</th>
      </tr>
       <tr style="border-top: 1px solid #999;">
        <s:if test="%{#model.subTrainingType != null}">
        <td>${model.subTrainingType}</td>
        </s:if>
        <s:else>
          <td>被动训练</td>
        </s:else>
        <td>${model.trainingCount} / ${model.timePerTraining}</td>
        <td><s:date name="%{#model.startTime}" format="yyyy-MM-dd HH:mm"/></td>
        <td><s:date name="%{model.endTime}" format="yyyy-MM-dd HH:mm"/></td>
        <td>${model.rehabPlan.result}</td>
      </tr>
    </table>
    <br />
    <p style="font-weight:bold">训练曲线图</p>
    <div style="margin: 20px;">
    <div id="chart1" style="width:700px;height:300px; float: left;"></div>
	<div id="chart2" style="width:700px;height:300px; float: left;"></div>
	<div id="chart3" style="width:700px;height:300px; float: left;"></div>
	<div id="chart4" style="width:700px;height:300px; float: left;"></div>
	<div id="chart5" style="width:700px;height:300px; float: left;"></div>
	</div>
</div>
</body>
</html>
