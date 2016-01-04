// JavaScript Document

$(function(){
		$(".foldBtn1, .foldBtn2, .foldBtn3, .foldBtn4, .foldBtn5, .foldBtn6").mouseleave(function(){
			$(this).css('background-image','url(http://www.nofate.net.cn/mre/style/images/patientCenterBgs/arrow_carrot-down.png)');
			});
			
		$(".foldBtn1, .foldBtn2, .foldBtn3, .foldBtn4, .foldBtn5, .foldBtn6").mousemove(function(){
			$(this).css('background-image','url(http://www.nofate.net.cn/mre/style/images/patientCenterBgs/arrow_carrot-on.png)');
			});
			
		var rotateValue1 = 0;
		$('.foldBtn1').click(function(){
			rotateValue1 += 180;
			$(this).rotate({animateTo: rotateValue1});
			if(rotateValue1 % 360 == 0){
				$(this).next('ul').show();
				}
			else{
				$(this).next('ul').hide();
			}
			});
			
		var rotateValue2 = 0;
		$('.foldBtn2').click(function(){
			rotateValue2 += 180;
			$(this).rotate({animateTo: rotateValue2});
			if(rotateValue2 % 360 == 0){
				$(this).next('ul').show();
				}
			else{
				$(this).next('ul').hide();
			}
			});
			
		var rotateValue3 = 0;
		$('.foldBtn3').click(function(){
			rotateValue3 += 180;
			$(this).rotate({animateTo: rotateValue3});
			if(rotateValue3 % 360 == 0){
				$(this).next('ul').show();
				}
			else{
				$(this).next('ul').hide();
			}
			});	
			
		var rotateValue4 = 0;
		$('.foldBtn4').click(function(){
			rotateValue4 += 180;
			$(this).rotate({animateTo: rotateValue4});
			if(rotateValue4 % 360 == 0){
				$(this).next('ul').show();
				}
			else{
				$(this).next('ul').hide();
			}
			});	
			
		var rotateValue5 = 0;
		$('.foldBtn5').click(function(){
			rotateValue5 += 180;
			$(this).rotate({animateTo: rotateValue5});
			if(rotateValue5 % 360 == 0){
				$(this).next('ul').show();
				}
			else{
				$(this).next('ul').hide();
			}
			});	
			
			var rotateValue6 = 0;
		$('.foldBtn6').click(function(){
			rotateValue6 += 180;
			$(this).rotate({animateTo: rotateValue6});
			if(rotateValue6 % 360 == 0){
				$(this).next('ul').show();
				}
			else{
				$(this).next('ul').hide();
			}
			});				
			
		$("li > ul > li:not(.li_checked, .li_blank)").mouseleave(function(){
			$(this).removeClass('li_on');
			});
			
		$("li > ul > li:not(.li_checked, .li_blank)").mousemove(function(){
			$(this).addClass('li_on');
		});	
			
		});