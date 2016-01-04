<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>   
    <title>没有权限</title> 
  </head>
  
  <body>
  <div id="time"></div>
<script type="text/javascript">
var x=document.getElementById("time"),count=5;
function getCount(){
x.innerHTML="对不起， 您目前已经登录，请先退出，才能进行注册和登录操作！ "+(count--)+"秒后跳转回主页面，如没有自动跳转，请<a href='http://www.nofate.net.cn/mre'>点击这里<\/a>";
if(count>0){
window.setTimeout("getCount()",1000);
}else{
window.open("http://www.nofate.net.cn/mre","_self");
}
}
getCount();
</script>
  </body>
</html>
