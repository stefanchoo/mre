$(function(){
	 // clickme 跳动效果
	 setInterval("jump()", 100);
	$(".quickStart").click(function(){
		// 调出询问用户的信息，让用户选择
		/* 1. 用户是否是需要康复之人？
		   2. 什么样的疾病，脑中风，偏瘫等，提供选择
		   3. 根据用户的选择结果，推荐什么样的康复设备？在哪家医院可以得到康复等信息*/
		   alert("我会向您提供帮助...");
	});
	// topSearch onclick函数
	$("#searchType > li").click(function(){
		//获取li的id
		var name = $(this).attr("id");
		//alert(name);
		$(this).addClass("searchTypeChecked");
		$(this).siblings("li").removeClass("searchTypeChecked");
	});
	
	// Menu 
	$("#navi_first > li").mouseleave(function(){
		$(this).find("ul").slideUp("fast");  
	});
	$("#navi_first > li").mousemove(function(){
		$(this).find("ul").slideDown();
	});
	// 照片墙，点击可查看详细信息
	$(".pic_blank_h150,.arrows_blank_w580").click(function(){
		// 跳转到当前照片墙对应的链接要接收传递过来的id
		alert("我要跳转到我的介绍页面哦~");
	});
	// 鼠标入到图片上，显示翻转按钮
	$(".picWall").mouseleave(function(){
		$(".arrows_pre").css("visibility","hidden");
		$(".arrows_pos").css("visibility","hidden");
	});
	$(".picWall").mousemove(function(){
		$(".arrows_pre").css("visibility","visible");
		$(".arrows_pos").css("visibility","visible");
	});

	// 翻转按钮改变banner 的图片和对应的描述
	$(".arrows_pre").click(function(){
		// 改变图片
		$(".picWall").css('background-image','url(http://www.nofate.net.cn/mre/style/images/showImgs/erigo.jpg)');
		// 对应的标题和内容
		$(".name").html("Armeo Spring");
		$(".content").html("Armeo Spring 是一台独特的上肢康复训练与评估设备。适用于因中枢神经、周围神经、脊髓、肌肉或骨骼疾病引	 起的上肢功能障碍或功能受限的患者。");
	});

	$(".arrows_pos").click(function(){
		$(".picWall").css('background-image','url(http://www.nofate.net.cn/mre/style/images/showImgs/armeo.jpg)');
		$(".name").html("Armeo Power");
		$(".content").html("Armeo Power 是一台独特的上肢康复训练与评估设备。适用于因中枢神经、周围神经、脊髓、肌肉或骨骼疾病引	 起的上肢功能障碍或功能受限的患者。");
	});
	
	// 在线留言
	$(".usTalk > div").mouseleave(function(){
		$(this).css("background-color","#0C0");
	});
	
	$(".usTalk > div").mousemove(function(){
		$(this).css("background-color","#090");
	});
	
	$(".usTalk > div").click(function(){
		// 转到留言页面 或者弹出一个窗口
		alert("感谢您的宝贵意见！");
	});
	
});

/* 定义的方法要放在$(function()) 外面，因为$(function())  是页面加载完就要开始运行的 */
function jump(){
		$(".quickStart").animate({top: "-=20px"},"slow").animate({top: "+=20px"},"slow");
	}