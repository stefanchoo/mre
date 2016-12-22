// JavaScript Document
$(function(){
	$(".operateArea > input, .confirmArea > input").mouseleave(function(){
		$(this).css('background-color', '#0C0');
		});
	$(".operateArea > input, .confirmArea > input").mousemove(function(){
		$(this).css('background-color', '#090');
		});		
	$("input[name='birthday']").datepicker();
	
	var ok1=false;
	var ok2=false;
	var errorMsg = "";
	$("#basicInfoForm :input").blur(function(){
		 var $parent = $(this).parent();
		 $parent.find("#errorMsg").remove();
		 var myEmailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/ ;
		 var myPhoneReg =  /^0?1[3|4|5|8][0-9]\d{8}$/;
		// 验证邮箱
		if($(this).is("input[name='email']")){
		 if(!myEmailReg.test(this.value)){
                  errorMsg = '请输入正确的邮箱地址';
                    $parent.append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;display:block">'+
					errorMsg+'</span>');
					ok1 = false;
           }else{
		    ok1 = true;
		   }
		}
		// 验证手机号码
		if($(this).is("input[name='telephone']")){
			if(!myPhoneReg.test(this.value)){
				  errorMsg = '请输入正确的手机号';
                  $parent.append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;display:block">'+
					errorMsg+'</span>');
					ok2 = false;
				}else{
				 ok2 = true;	
				}
			}
		}).keyup(function(){
			$(this).triggerHandler("blur");
		}).focus(function(){
			$(this).triggerHandler("blur");
			});
		
	// 所有验证通过才能提交按钮
	$("#submit").click(function(){
		if(ok1 && ok2){
			return window.confirm('确定要保存修改后的个人资料吗？');
			$('#basicInfoForm').submit();
			}else{
				return false;
				}
		});
	
	});