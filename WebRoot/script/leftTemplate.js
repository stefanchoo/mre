// JavaScript Document
$(function(){
	$("#equipmentList > li, #hospitalList > li, #expertList > li").mouseleave(function(){
		$(this).css("background-color","#F7F7F7");
	});
	
	$("#equipmentList > li, #hospitalList > li, #expertList > li").mouseover(function(){
		$(this).css("background-color","#CCC");
	});
	// 设置其中某个参考设备的超链接
	$("#equipmentList > li").click(function(){
		// 跳转到具体的信息页面，需要传递设备的id
		window.open("equipmentShow.html?id=1", "_blank");
	});
	
	// 设置医院的超链接
	$("#hospitalList > li").click(function(){
		// 跳转到具体的信息页面，需要传递设备的id
		window.open("hospitalShow.html?id=1", "_blank");
	});
	
	// 设置专家的超链接
	$("#expertList > li").click(function(){
		// 跳转到具体的信息页面，需要传递设备的id
		window.open("expertShow.html?id=1", "_blank");
	});
	
	// goBtn 的属性
	$(".goBtn").mouseleave(function(){
		$(this).css("background-color", "#0C0");
		});
	$(".goBtn").mousemove(function(){
		$(this).css("background-color", "#999");
		});
});