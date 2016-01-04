// JavaScript Document
$(function(){
	
	$("#birthday").datepicker({
		changeMonth: true,
		changeYear: true
	});
	
	$(".doctorBtnStyle > a").mouseleave(function() {
		$(this).css("background-color", "#666");
	});
	$(".doctorBtnStyle > a").mouseover(function() {
		$(this).css("background-color", "#00BD00");
	});
	$('.messages > ul > a > li').mouseleave(function(){
		$(this).css("background-color","#FFF");
	});	
	$('.messages > ul > a > li').mousemove(function(){
		$(this).css("background-color","#DFDFDF");
	});
	
	$("#fromDate").datepicker();
	$("#toDate").datepicker();
	$("#rehabPlanTime").datetimepicker();
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
	
	// 试一下使用属性来选择标签 格式为 button[name='submit']
	$("button[name='submit']").mouseleave(function(){
		$(this).css('background','#0C0');
		});
	$("button[name='submit']").mouseover(function(){
		$(this).css('background','#666');
		});
	
	var ok1=true;
	var ok2=true;
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
	$("#basicSubmit").click(function(){
		if(ok1 && ok2 && (window.confirm('确定要保存修改后的个人资料吗？'))){
			$('#basicInfoForm').submit();
			}else{
				return false;
				}
		});
	
	
	var ok3 = false;
	var ok4 = false;
	$("#cpForm :input").blur(function(){
		 $('.passwordPropertyArea').find("#errorMsg").remove();
		 if($(this).is("input[name='newPassword']")){
				if(this.value=="" || this.value.length < 6 || this.value.length > 20){
					  errorMsg = '密码位数不正确';
		              $('.passwordPropertyArea').append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">'+
						errorMsg+'</span>');
						ok3 = false;
					}else{
					 ok3 = true;	
					}
				}
			if($(this).is("#confirmNewPassword")){
				if(!(this.value == $("input[name='newPassword']").attr('value'))){
					  errorMsg = '两次输入密码不一致';
		             $('.passwordPropertyArea').append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">'+
						errorMsg+'</span>');
						ok4 = false;
					}else{
					 ok4 = true;	
					}
				}
		}).keyup(function(){
			$(this).triggerHandler("blur");
		}).focus(function(){
			$(this).triggerHandler("blur");
			});
	
	$("#cpSubmit").click(function(){
		 $('.passwordPropertyArea').find("#errorMsg").remove();
		// 这里还需校验一次密码是否一致，因为有可能密码框的规则只是位数
		if($("input[name='newPassword']").attr('value') == $("#confirmNewPassword").attr('value')){
			ok4 = true;
			}else{
				ok4 = false;
				errorMsg = '两次输入密码不一致';
	            $('.passwordPropertyArea').append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">'+
					errorMsg+'</span>');
			}
		if(ok3 && ok4 && (window.confirm('确定要修改密码吗？'))){
			$('#cpForm').submit();
			}else{
				return false;
				}
		});	
	
	var ok5=true;
	/*验证用户名的大小*/
	$("#addForm :input").blur(function(){
		 if($(this).is("input[name='loginName']")){
			 if( this.value=="" || this.value.length < 6 ){
	              errorMsg = '请输入至少6位数字、字母的用户名';
	              $("#errorMsg").html(errorMsg);
					ok5 = false;
	       }else{
	    	   $("#errorMsg").html(""); 
		    ok5 = true;
		   }
		 }
	}).keyup(function(){
		$(this).triggerHandler("blur");
	}).focus(function(){
		$(this).triggerHandler("blur");
		});
	
	$("#addSubmit").click(function(){
		if(ok5){
			$("#addForm").submit();
		}else{
			return false;
		}
	});
	
	/* 点击搜索提交一用户名和真实姓名 */
	$('.psearch').click(function(){
		var loginName = $("#pLoginName").attr("value");
		var trueName = $("#pTrueName").attr("value");
		window.open("http://www.nofate.net.cn/mre/doctor_validatePatient.action?patient.loginName="+loginName+"&patient.trueName="+trueName, "_self");
	});
	
	/* 点击参数设置进入设备计划定制页面 
	$("#setParams").click(function(){
		// 注意这种层次选择方式  $("#selectEquipment option:selected")
		var equipmentId = $("#selectEquipment option:selected").val();
		var rehabPlanId = $("#rehabPlanId").attr("value");
		window.open("http://www.nofate.net.cn/mre/doctor_setEquipmentParams.action?equipmentId="+equipmentId+"&rehabPlanId="+rehabPlanId, "_blank");
	});*/
	});



