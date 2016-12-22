// JavaScript Document
/*  转到本页面是，焦点定在第一个输入框上 */
$(function(){
	$("input:first").focus();
	$(".submit_off").mousemove(function(){
		$(this).css("background-color", "#060");
	});
	$(".submit_off").mouseleave(function(){
		$(this).css("background-color", "#090");
	});
	
	
		var ok1=false;
		var ok2=false;
		var ok3=false;
		var ok4=false;
		var errorMsg = "";
	// 对用户注册表的验证
	$('#registerForm :input').blur(function(){
		 var $parent = $(this).parent();
		 $parent.find("#errorMsg").remove();
		// 验证用户名
		if($(this).is('#loginName')){
		 if( this.value=="" || this.value.length < 6 ){
                  errorMsg = '请输入至少6位数字、字母的用户名';
                 $parent.append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">'+
					errorMsg+'</span>');
					ok1 = false;
           }else{
		    ok1 = true;
		   }
		}
		if($(this).is('#password')){
			if(this.value=="" || this.value.length < 6 || this.value.length > 20){
				  errorMsg = '密码位数不正确';
                  $parent.append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">'+
					errorMsg+'</span>');
					ok2 = false;
				}else{
				 ok2 = true;	
				}
			}
		if($(this).is('#confirmPassword')){
			if(!(this.value == $('#password').attr('value'))){
				  errorMsg = '两次输入密码不一致';
                 $parent.append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">'+
					errorMsg+'</span>');
					ok3 = false;
				}else{
				 ok3 = true;	
				}
			}
		}).keyup(function(){
			$(this).triggerHandler("blur");
		}).focus(function(){
			$(this).triggerHandler("blur");
			});
	// 所有验证通过才能提交按钮
	$('#submitBtn').click(function(){
		if($('#contractApproved').attr('checked') == 'checked'){
			ok4 = true;
			}else{
			ok4 = false;
				}
		// 这里还需校验一次密码是否一致，因为有可能密码框的规则只是位数
		if($('#password').attr('value') == $('#confirmPassword').attr('value')){
			ok3 = true;
			}else{
				ok3 = false;
				errorMsg = '两次输入密码不一致';
                $('.registerInput').append('<span id="errorMsg" style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">'+
					errorMsg+'</span>');
			}
		if(ok1 && ok2 && ok3 && ok4){
			$('#registerForm').submit();
			}else{
				return false;
				}
		});
	
});

