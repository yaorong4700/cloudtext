<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>

<style type="text/css">
html, body {
	height: 100%;
	width: 100%;
	overflow: hidden;
	margin: 0;
	padding: 0;
}

.text {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
	color: #005c8a;
}

.label1 {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 15px;
	color: #858484;
}

.inputSubmit {
	height: 50px;
	width: 200px;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
	color: #005c8a;
	padding: 10px 20px;
	background: -moz-linear-gradient(top, #afd9fa 0%, #5d9bbd);
	background: -webkit-gradient(linear, left top, left bottom, from(#afd9fa),
		to(#5d9bbd));
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #7db0e3;
	-moz-box-shadow: 0px 1px 3px rgba(5, 5, 5, 0.5), inset 0px 0px 1px
		rgba(207, 114, 207, 1);
	-webkit-box-shadow: 0px 1px 3px rgba(5, 5, 5, 0.5), inset 0px 0px 1px
		rgba(207, 114, 207, 1);
	box-shadow: 0px 1px 3px rgba(5, 5, 5, 0.5), inset 0px 0px 1px
		rgba(207, 114, 207, 1);
	text-shadow: 0px -1px 0px rgba(190, 130, 199, 0.7), 0px 1px 0px
		rgba(255, 255, 255, 0.3);
}

.container {
	background-color: #7ea7e8;
	width: 100%;
	height: 100%; 
 position: relative;  
  display: inline-block;
}

.menu {
	background-color: #7ea7e8;
	width: 10%;
	float: left;
	height: 75%;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 15px;
	color: #005c8a;
		display: inline-block;
	position: relative;
}

a {
color: #005c8a;
text-decoration:none;
}

.content {
	background-color: #d9d2e9;
	width: 88%;
	float: left;
	height: 75%;
}
.footer {
	background-color: #bfafe6;
	clear: both;
	text-align: center;
	height: 25%
}
.spinner {

  width: 60px;
  height: 60px;
  background-color: #7ea7e8;
 
  margin: auto auto;
  -webkit-animation: rotateplane 1.2s infinite ease-in-out;
  animation: rotateplane 1.2s infinite ease-in-out;
}
 
@-webkit-keyframes rotateplane {
  0% { -webkit-transform: perspective(120px) }
  50% { -webkit-transform: perspective(120px) rotateY(180deg) }
  100% { -webkit-transform: perspective(120px) rotateY(180deg)  rotateX(180deg) }
}
 
@keyframes rotateplane {
  0% { 
    transform: perspective(120px) rotateX(0deg) rotateY(0deg);
    -webkit-transform: perspective(120px) rotateX(0deg) rotateY(0deg) 
  } 50% { 
    transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg);
    -webkit-transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg) 
  } 100% { 
    transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);
    -webkit-transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);
  }
}
</style>
<script type="text/javascript">
function onloading(){
	
	document.getElementById("loading").style.display=""; 
	
}
</script>
<body
	style="background-color: #E6E6FA; text-align: center; height: 100%">
<div id="loading" class="spinner"  style="display: none;">	</div>
	<div id="container" class="container">
		
		<div id="header" style="background-color: #bfafe6; margin-top: 0; margin-bottom: 0;text-align: center;height:13%;">
		
			<div style="background-color: #bfafe6; text-align: center;width:10%;float: left;">
				   <img style="margin-top: 0; margin-bottom: 0;" src="pic/hitachi.png"/>
			</div>
			<div style="background-color: #bfafe6; text-align: center; width:80%;float: left;margin-top:10px;">
			  <b><label style=" margin-bottom: 0; font-family: Arial, Helvetica, sans-serif; font-size: 40px;"  > PAAS平台</label><label style=" margin-bottom: 0; font-family: Arial, Helvetica, sans-serif; font-size: 10px;"  >（测试用）</label></b>
			</div>
			<div style="background-color: #bfafe6; text-align: center; width:10%;float: right;margin-top:20px">
			<label style="text-align: center; " class="text">welcome	${username }</label>
			</div>
		</div>

		<div id="menu" class="menu">
		<br>
           <a href="twochoices?username=${username}&usermail=${usermail}"  title="首页" style=" top:0;position: relative;"><img  src="pic/Phome.png"/><br>首页</a><br><br>
            <a href="choice?username=${username}&usermail=${usermail}" title="代码构建"style="top: 1;position: relative;"><img  src="pic/coder.png"/><br>代码构建</a><br><br>
            <a href="upload?username=${username}&usermail=${usermail}" title="镜像列表"style="top:2;position: relative;"><img src="pic/image.png"/><br>镜像列表</a><br><br>
          <a href="resource?username=${username}&usermail=${usermail}" title="资源配置"style="top:3;position: relative;"><img src="pic/resource.png"/><br>资源配置</a><br><br>
           <a href="applist?username=${username}&usermail=${usermail}" title="应用列表" style="top:4;position: relative;"><img src="pic/app.png"/><br>应用列表</a><br><br>
            <a href="servicelist?username=${username}&usermail=${usermail}" title="服务列表" style="top:4;position: relative;"><img src="pic/bell.png"/><br>服务列表</a><br> 
		</div>
<!-- 
<div style="float: left; text-align:left;width:2%;height: 75%;background-color: #d9d2e9;position: relative;display: inline-block">
<div>
	<img style="top:0;position: relative;display: inline-block" src="pic/ssss.png"/>
</div>
</div>
 -->

		<div id="content" class="content">
			<br> <br> <br>
			<br>
			<br>
			
			<form action="choice0" method="post">
				<input type="hidden" name="code" id="code" value="0" /> <input
					type="hidden" name="usermail" id="usermail" value="${usermail }" />
				<input type="hidden" name="username" id="username"
					value="${username }" /> <input type="submit" name="submit"
					id="submit" value="现有war包创建应用" class="inputSubmit"onclick="onloading();" />
			</form>
			<small> <label class="label1">*上传文件要求后缀 war</label><br>
				<label class="label1">*测试用war包的编译环境要求是jdk7环境</label><br> <label
				class="label1">*测试用war包的命名为a~z,0~9, _ , /,</label><br>
			</small> <br>
			<form action="choice1" method="post">
				<input type="hidden" name="code" id="code" value="1" /> <input
					type="hidden" name="usermail" id="usermail" value="${usermail }" />
				<input type="hidden" name="username" id="username"
					value="${username }" /> <input type="submit" name="submit"
					id="submit" value="代码持续集成" class="inputSubmit" onclick="onloading();"/>
			</form>
			<small> <label class="label1">*项目为Github上的开源项目（名称不可大写，待修复），使用者提供链接</label><br>
				<label class="label1">*应用创建成功后，Github上源码一旦改动, 将会自动更新部署。</label><br>
				<label class="label1">*项目过大需较长等待时间</label><br>
			</small> 
		</div>
		<div id="footer" class="footer">Copyright © HSCNZJ</div>
	</div>
</body>

</html>