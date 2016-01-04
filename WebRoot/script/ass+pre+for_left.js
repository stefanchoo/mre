// JavaScript Document

$(function(){
		// 展开按钮
		$(".foldBtn1, .foldBtn2, .foldBtn3").mouseleave(function(){
			$(this).css('background-image', 'url(../style/images/button/foldBtn.gif)');
		});
		
		$(".foldBtn1, .foldBtn2, .foldBtn3").mousemove(function(){
			$(this).css('background-image', 'url(../style/images/button/foldBtn_on.gif)');
		});

		var rotateValue1 = 0;
		$('.foldBtn1').click(function(){
			rotateValue1 += 180;
			$(this).rotate({animateTo: rotateValue1});
			if(rotateValue1 % 360 == 0){
				$(this).next('ul').slideDown();
				}
			else{
				$(this).next('ul').slideUp();
			}
			});
			
		var rotateValue2 = 0;
		$('.foldBtn2').click(function(){
			rotateValue2 += 180;
			$(this).rotate({animateTo: rotateValue2});
			if(rotateValue2 % 360 == 0){
				$(this).next('ul').slideDown();
				}
			else{
				$(this).next('ul').slideUp();
			}
			});
			
		var rotateValue3 = 0;
		$('.foldBtn3').click(function(){
			rotateValue3 += 180;
			$(this).rotate({animateTo: rotateValue3});
			if(rotateValue3 % 360 == 0){
				$(this).next('ul').slideDown();
				}
			else{
				$(this).next('ul').slideUp();
			}
			});
		$('li:not(.checked)').mouseleave(function(){
				$(this).css("background-color","#FFF");
			});	
		$('li:not(.checked)').mousemove(function(){
				$(this).css("background-color","#DFDFDF");
			});	
		});