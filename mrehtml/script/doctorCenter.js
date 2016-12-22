// JavaScript Document
$(function(){
	$('#connect').click(function(){
		alert("正在连接，请稍后...");
		// 这里要检测是否已经连接完毕
		/*
		if('连接成功'){}
		else{alert('连接失败，请重试！');}
		*/
		$(this).removeClass('abledBtn').addClass('disabledBtn').attr('disabled','disabled');
		alert("连接成功!");
		$("#check").addClass('abledBtn').removeAttr('disabled');
		$("#stop").addClass('abledBtn').removeAttr('disabled');
		});
		
	$('#check').click(function(){
		// 这里要检测是否已经连接完毕
		/*
		if('测试成功'){}
		else{alert('测试失败，请重试！');}
		*/
		$(this).removeClass('abledBtn').addClass('disabledBtn').attr('disabled','disabled');
		alert("测试成功!");
		});	
		
	$('#stop').click(function(){
		// 这里要检测是否已经连接完毕
		/*
		if('测试成功'){}
		else{alert('测试失败，请重试！');}
		*/
		$(this).removeClass('abledBtn').addClass('disabledBtn').attr('disabled','disabled');
		alert("停止成功!");
		$("#connect").addClass('abledBtn').removeAttr('disabled');
		});	
		
	$('#date').datetimepicker();
	//var dateFormat = $( "#datepicker" ).datepicker( "option", "dateFormat" );//get dateFormat 
    //$("#datepicker").datepicker('option', {dateFormat: 'yy-mm-dd'});//set dateFormat 
	
	// 试一下使用属性来选择标签 格式为 button[name='submit']
	$("button[name='submit']").mouseleave(function(){
		$(this).css('background','#0C0');
		});
	$("button[name='submit']").mouseover(function(){
		$(this).css('background','#666');
		});
		
	$("#psearch").mouseleave(function(){
		$(this).css('background', 'url(../style/images/button/search.png)');
		});
		
	$("#psearch").mouseover(function(){
		$(this).css('background', 'url(../style/images/button/search_on.png)');
		});
	});