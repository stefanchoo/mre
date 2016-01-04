// JavaScript Document
	$(function(){
	$("#firstNavi > li ").mouseleave(function(){
		$(this).find("#secondNavi").slideUp("fast"); 
	});
	$("#firstNavi > li ").mousemove(function(){
		$(this).find("#secondNavi").slideDown();  
	});
	
	$("#firstNavi > li:not('.navi_checked')").mouseleave(function(){
		$(this).removeClass("navi_on");
		});
	$("#firstNavi > li:not('.navi_checked')").mousemove(function(){
		$(this).addClass("navi_on");
		});
	
	$("#secondNavi > li").mouseleave(function(){
		$(this).css("color", "#FFF"); 
	});
	$("#secondNavi > li").mousemove(function(){
		$(this).css("color", "#333");  
	});
	
	// 状态可以从程序中获取
	var connect = false;
	$("#centrobotConnect").click(function(){
		// 经过处理
		if(connect){
			alert("设备断开成功");
			$(this).attr('title','点击连接');
			$(this).attr('src', 'http://www.nofate.net.cn/mre/style/images/centrobot/off.jpg');
			connect = false;
			}
		else{
			alert("设备连接成功");
			$(this).attr('title','点击断开');
			$(this).attr('src', 'http://www.nofate.net.cn/mre/style/images/centrobot/on.jpg');
			connect = true;
		}
		});
	});