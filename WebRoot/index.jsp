<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="style/css/init.css" />
<link rel="stylesheet" type="text/css" href="style/css/index.css" />
<link rel="stylesheet" type="text/css" href="style/css/login.css" />
<script type="text/javascript" src="script/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="script/index.js"></script>
<script type="text/javascript"
	src="script/login.js"></script>
<title>MRE首页</title>
</head>

<body>
	<!-- 首页 -->
	<s:if test="%{#session.user == null}">
		<%@ include file="/WEB-INF/jsp/public/logoutTopPage.jspf"%>
	</s:if>
	<s:else>
		<%@ include file="/WEB-INF/jsp/public/loginTopPage.jspf"%>
	</s:else>

	<!-- 设置居中 放大缩小时，始终处于中间位置-->
	<div class="bodyPage">
		<!-- Banner 背景图与搜索框-->
		<!-- 搜素框设有 康复设备 合作医院 康复专家 康复论坛 用以搜索时的筛选 -->
		<div id="bannerPage" class="bannerPage">
			<div class="banner">
				<img src="style/images/banner.png" width="360px" height="120px" />
			</div>
			<div class="search">
				<form action="#" method="post">
					<div id="topSearch" class="topSearch">
						<div class="search_blank_h15"></div>
						<div class="searchType">
							<ul id="searchType">
								<li id="equipment" class="searchTypeChecked">康复设备</li>
								<li id="hospital">医院导航</li>
								<li id="expert">专家推荐</li>
								<li id="knownledge">康复知识库</li>
							</ul>
						</div>
					</div>
					<div id="bottomSearch" class="bottomSearch">
						<div class="search_blank_h10"></div>
						<div class="searchBox">
							<input id="searchInput" type="text" class="searchInput" /> <input
								id="searchButton" type="image"
								src="style/images/button/searchBtn.gif" class="searchButton" />
						</div>
						<div class="search_blank_h35"></div>
					</div>
				</form>
			</div>
			<div class="quickStart"></div>
		</div>

		<!-- 导航框 + 照片墙 -->
		<!--  导航栏显示，鼠标移入显示二级菜单 -->
		<!-- 照片墙功能： 1. 左侧显示推荐设备的照片，右边显示设备信息
				     2. 点击照片或者设备名称进入查看详细介绍
				    3. 鼠标放上照片，显示左右切换按钮，用于循环显示推荐的四款康复设备 -->
		<div class="naviPicPage">
			<div class="navigationDiv">
				<ul id="navi_first" class="navi_first">
					<li><a href="#">首页</a></li>
					<li><a href="equipment/equipmentMainList.html" target="_blank">康复设备</a>
						<ul class="navi_second">
							<li><a href="equipment/equipmentList.html?id=1"
								target="_blank">&gt; 运动治疗设备</a></li>
							<li><a href="equipment/equipmentList.html?id=2"
								target="_blank">&gt; 物理因子治疗设备</a></li>
							<li><a href="equipment/equipmentList.html?id=3"
								target="_blank">&gt; 作业治疗设备</a></li>
							<li><a href="equipment/equipmentList.html?id=4"
								target="_blank">&gt; 言语治疗设备</a></li>
							<li><a href="equipment/equipmentList.html?id=5"
								target="_blank">&gt; 传统康复治疗设备</a></li>
						</ul></li>
					<li><a href="hospital/hospitalMainList.html" target="_blank">医院导航</a>
						<ul class="navi_second">
							<li><a href="hospital/hospitalList.html?id=1"
								target="_blank">&gt; 上海</a></li>
							<li><a href="hospital/hospitalList.html?id=2"
								target="_blank">&gt; 北京</a></li>
							<li><a href="hospital/hospitalList.html?id=3"
								target="_blank">&gt; 江苏</a></li>
							<li><a href="hospital/hospitalList.html?id=4"
								target="_blank">&gt; 浙江</a></li>
							<li><a href="hospital/hospitalList.html?id=5"
								target="_blank">&gt; 广州</a></li>
						</ul></li>
					<li><a href="expert/expertList.html" target="_blank">专家推荐</a>
						<ul class="navi_second">
							<li><a href="expert/expertShow.html?id=1" target="_blank">&gt;
									励建安</a></li>
							<li><a href="expert/expertShow.html?id=2" target="_blank">&gt;
									喻洪流</a></li>
							<li><a href="expert/expertShow.html?id=3" target="_blank">&gt;
									cbc</a></li>
							<li><a href="expert/expertShow.html?id=4" target="_blank">&gt;
									dbc</a></li>
							<li><a href="expert/expertShow.html?id=5" target="_blank">&gt;
									ebc</a></li>
						</ul></li>
					<li><a href="assessment/assessmentList.html" target="_blank">康复知识库</a>
						<ul class="navi_second">
							<li><a href="assessment/assessmentList.html" target="_blank">&gt;
									康复评定量表</a></li>
							<!-- 康复处方只有医生登陆之后才能看 -->
							<li><a href="prescription/prescriptionList.html"
								target="_blank">&gt; 常见康复处方</a></li>
							<!-- 康复论坛，回复发帖需要登录 -->
							<li><a href="#">&gt; 站内康复论坛</a></li>
						</ul></li>
					<li><a href="#01">联系我们</a></li>
				</ul>
			</div>

			<div class="picDiv">
				<div id="picWall" class="picWall">
					<div class="pic_blank_h150"></div>
					<div class="arrows">
						<div class="arrows_pre"></div>
						<div class="arrows_blank_w580"></div>
						<div class="arrows_pos"></div>
					</div>
					<div class="pic_blank_h150"></div>
				</div>
				<div class="picDesc">
					<s:if test="%{#session.user == null}">
						<!-- 放入登录框 -->
						<div class="loginTitle_index">
							没有账号？
							<s:a action="user_registerUI">立即注册</s:a>
						</div>
						<div class="loginBox_index">
							<s:form id="loginForm" action="user_login" method="post" namespace="/">
								<div class="loginInput_index">
									<input type="text" id="loginName" name="loginName"
										placeholder="用户名" /> <input type="password" id="password"
										name="password" placeholder="密码" /> <span id="errorMsg"
										style="width:350px;font-size:12px;color:#F00;text-align:center;display:block">
									</span>
								</div>
								<div class="forgetPwd">
									<a href="#">忘记密码？</a>
								</div>
								<div class="submitBtn_index">
									<button id="loginBtn" class="submit_off_index">登
										录</button>
								</div>
							</s:form>
							<!-- 弹出提示信息 -->
							<s:if test="hasFieldErrors()">
								<s:iterator value="fieldErrors">
									<s:iterator value="value">
										<script language="JavaScript">
											alert('<s:property/>');
										</script>
									</s:iterator>
								</s:iterator>
							</s:if>
						</div>
					</s:if>
					<s:else>
						<!-- 放入头像，某某你好，上次登录时间，按键进入个人中心 -->
						<div style="margin: 30px 66px; width:300px; font-size:18px;">
							<img width="100px" height="100px"
								src="${pageContext.request.contextPath}/style/images/userIcons/${user.icon}" />
							&nbsp; &nbsp; &nbsp; 您好！ ${user.loginName}<br /> <br />

							<center>
								上次登录时间：<br />
								<s:date name="%{#session.user.lastLoginTime}"
									format="yyyy-MM-dd HH:mm:ss" />
							</center>
							<br />
							<s:a action="user_center">
								<input
									style="width:300px; height:50px; cursor:pointer; font-family:'微软雅黑'; color:#FFF; border:0px; border-radius: 5px; background-color: #0A0; font-size: 20px;"
									type="button" value="点击进入个人中心" />
							</s:a>
						</div>
					</s:else>
				</div>
			</div>
		</div>
		<!--Main Page-->
		<!-- 主页面左边显示康复设备、合作医院、康复专家的信息，右边显示康复评定量表、康复论坛以及联系我们的信息 
		其中康复评定量表与康复论坛取前 15 条数据放入主页面
		注： 没有显示康复处方是因为康复处方是医生登陆之后才可见的-->
		<div class="mainPage">
			<!-- 左边 -->
			<div id="leftPage" class="leftPage">
				<div class="leftPageTemplate">
					<div class="mainTitle">康 复 设 备</div>
					<div class="mainContent">
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="equipment/equipmentList.html" target="_blank">运动治疗</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
							</div>
							<div class="more">
								<a href="equipment/equipmentList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="equipment/equipmentList.html" target="_blank">物理因子治疗</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
							</div>
							<div class="more">
								<a href="equipment/equipmentList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="equipment/equipmentList.html" target="_blank">作业治疗</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
							</div>
							<div class="more">
								<a href="equipment/equipmentList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="equipment/equipmentList.html" target="_blank">言语治疗</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
							</div>
							<div class="more">
								<a href="equipment/equipmentList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="equipment/equipmentList.html" target="_blank">传统康复</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
								<div>
									<a href="equipment/equipmentShow.html?id=1" target="_blank">治疗床</a>
								</div>
							</div>
							<div class="more">
								<a href="equipment/equipmentList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
					</div>
				</div>
				<div class="leftPageTemplate">
					<div class="mainTitle">医 院 导 航</div>
					<div class="mainContent">
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="hospital/hospitalList.html" target="_blank">上 海</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="#">上海第一人民医院</a>
								</div>
								<div>
									<a href="#">上海第一康复医院</a>
								</div>
								<div>
									<a href="#">新华医院</a>
								</div>
								<div>
									<a href="#">华山医院</a>
								</div>
								<div>
									<a href="#">瑞金医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
							</div>
							<div class="more">
								<a href="hospital/hospitalList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="hospital/hospitalList.html" target="_blank">北 京</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
							</div>
							<div class="more">
								<a href="hospital/hospitalList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="hospital/hospitalList.html" target="_blank">江 苏</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
							</div>
							<div class="more">
								<a href="hospital/hospitalList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="hospital/hospitalList.html" target="_blank">浙 江</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
							</div>
							<div class="more">
								<a href="hospital/hospitalList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
						<div class="mainContentItems">
							<div class="mainContentItemsType">
								<a href="hospital/hospitalList.html" target="_blank">广 州</a>
							</div>
							<div class="mainContentItemsName">
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
								<div>
									<a href="#">杨浦医院</a>
								</div>
							</div>
							<div class="more">
								<a href="hospital/hospitalList.html" target="_blank">更多
									&gt;&gt;</a>
							</div>
						</div>
					</div>
				</div>
				<div class="leftPageTemplate">
					<div class="mainTitle">专 家 推 荐</div>
					<div class="mainContent">
						<div class="mainContentExpert">
							<div id="expertPhoto" class="expertPhoto">
								<a href="expert/expertShow.html?id=1" target="_blank"><img
									src="style/images/expertImgs/expert_01.gif" width="130px"
									height="130px" /></a>
							</div>
							<div class="expertInfo">
								<div id="name" class="expertInfoTemplate">
									<span>励建安大夫</span>
								</div>
								<div id="hospital" class="expertInfoTemplate">单位：
									南京医科大学第一附属医院</div>
								<div id="goodat" class="expertInfoTemplate">擅长： 心血管康复等...</div>
							</div>
							<div class="more">
								<a href="expert/expertShow.html?id=1" target="_blank">详细信息</a>
							</div>
						</div>
						<div class="mainContentExpert">
							<div id="expertPhoto" class="expertPhoto">
								<a href="expert/expertShow.html?id=1" target="_blank"><img
									src="style/images/expertImgs/expert_01.gif" width="130px"
									height="130px" /></a>
							</div>
							<div class="expertInfo">
								<div id="name" class="expertInfoTemplate">
									<span>励建安大夫</span>
								</div>
								<div id="hospital" class="expertInfoTemplate">单位：
									南京医科大学第一附属医院</div>
								<div id="goodat" class="expertInfoTemplate">擅长： 心血管康复等...</div>
							</div>
							<div class="more">
								<a href="expert/expertShow.html?id=1" target="_blank">详细信息</a>
							</div>
						</div>
						<div class="mainContentExpert">
							<div id="expertPhoto" class="expertPhoto">
								<a href="expert/expertShow.html?id=1" target="_blank"><img
									src="style/images/expertImgs/expert_01.gif" width="130px"
									height="130px" /></a>
							</div>
							<div class="expertInfo">
								<div id="name" class="expertInfoTemplate">
									<span>励建安大夫</span>
								</div>
								<div id="hospital" class="expertInfoTemplate">单位：
									南京医科大学第一附属医院</div>
								<div id="goodat" class="expertInfoTemplate">擅长： 心血管康复等...</div>
							</div>
							<div class="more">
								<a href="expert/expertShow.html?id=1" target="_blank">详细信息</a>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="rightPage" class="rightPage">
				<div class="rightPageTemplate_01">
					<div class="right_blank_h40"></div>
					<ul id="assessmentList" class="assessmentList">
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">生长激素治疗矮小症 如何正确停药</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">看懂血脂化验单，警惕血脂异常伤心脑</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">中国人如何管理血脂 预防心脑血管病</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">颈椎病显微手术 保护神经和血管</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">不孕不育该查啥</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">子宫肌瘤想保子宫，手术方法如何选择</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">支气管热成形术治疗重症/难治性哮喘</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">到底前列腺癌如何确诊？</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">直肠癌早期筛查怎么做</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">补片治疗降低疝复发率</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">治愈疝要靠开刀修补</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">治疗先心病又有微创新招</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">杜绝便秘，告别马桶上的挣扎</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">直肠癌早期筛查怎么做</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">补片治疗降低疝复发率</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">治愈疝要靠开刀修补</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">治疗先心病又有微创新招</a></li>
						<li><a href="assessment/assessmentShow.html?id=?"
							target="_blank">杜绝便秘，告别马桶上的挣扎</a></li>
					</ul>
				</div>

				<div class="rightPageTemplate_02">
					<div class="right_blank_h40"></div>
					<!-- 这里显示 -->
					<ul id="topicList" class="assessmentList" data="">
						<li><a href="forum/topicShow.html?id=?" target="_blank">生长激素治疗矮小症
								如何正确停药</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">看懂血脂化验单，警惕血脂异常伤心脑</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">中国人如何管理血脂
								预防心脑血管病</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">颈椎病显微手术
								保护神经和血管</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">不孕不育该查啥</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">子宫肌瘤想保子宫，手术方法如何选择</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">支气管热成形术治疗重症/难治性哮喘</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">到底前列腺癌如何确诊？</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">直肠癌早期筛查怎么做</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">补片治疗降低疝复发率</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">治愈疝要靠开刀修补</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">治疗先心病又有微创新招</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">杜绝便秘，告别马桶上的挣扎</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">直肠癌早期筛查怎么做</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">补片治疗降低疝复发率</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">治愈疝要靠开刀修补</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">治疗先心病又有微创新招</a></li>
						<li><a href="forum/topicShow.html?id=?" target="_blank">杜绝便秘，告别马桶上的挣扎</a></li>
					</ul>
				</div>

				<div class="contactus">
					<a name="01"></a>
					<div class="right_blank_h40"></div>
					<div class="right_blank_h200">
						<div class="usInfo">
							<div class="usName">&nbsp;单位：上海理工大学</div>
							<div class="usEmail">&nbsp;邮箱：mre.com@163.com</div>
							<div class="usTalk">
								<div>在线留言</div>
							</div>
						</div>
						<div class="weixinQR">扫一扫，加入我们</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Footer Page -->
		<%@ include file="WEB-INF/jsp/public/footerPage.jspf"%>
	</div>
</body>
</html>
