<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,java.io.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery-3.0.0.js"></script>
<script type="text/javascript" src="js/jquery-3.0.0.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>镜像列表</title>
</head>
<style type="text/css">
html, body {
	height: 100%;
	width: 100%;
	overflow: hidden;
	margin: 0;
	padding: 0;
}

select {
	border: solid 1px #000;
	appearance: none;
	padding-right: 14px;
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
	font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
	color: #005c8a;

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
	position: relative;
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
	text-decoration: none;
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

@
-webkit-keyframes rotateplane { 0% {
	-webkit-transform: perspective(120px)
}

50%
{
-webkit-transform
:
 
perspective
(120px)
 
rotateY
(180deg)
 
}
100%
{
-webkit-transform
:
 
perspective
(120px)
 
rotateY
(180deg)
  
rotateX
(180deg)
 
}
}
@
keyframes rotateplane { 0% {
	transform: perspective(120px) rotateX(0deg) rotateY(0deg);
	-webkit-transform: perspective(120px) rotateX(0deg) rotateY(0deg)
}
50%
{
transform
:
 
perspective
(120px)
 
rotateX
(-180
.1deg
)
 
rotateY
(0deg);

    
-webkit-transform
:
 
perspective
(120px)
 
rotateX
(-180
.1deg
)
 
rotateY
(0deg)
 
  
}
100%
{
transform
:
 
perspective
(120px)
 
rotateX
(-180deg)
 
rotateY
(-179
.9deg
);

    
-webkit-transform
:
 
perspective
(120px)
 
rotateX
(-180deg)
 
rotateY
(-179
.9deg
);

  
}
}
</style>
<script type="text/javascript">
	function onloading() {

		document.getElementById("loading").style.display = "";

	}
</script>

<body
	style="background-color: #E6E6FA; text-align: center; height: 100%">
	<div id="loading" class="spinner" style="display: none;"></div>
	<script type="text/javascript">
		function tt() {
			var val = $("#code1").text();
			if (val == "1") {
				$("#status").text("wait");
				$.ajax({
					type : "POST",
					url : 'marathon',
					data : $('#form1').serialize(),// 你的formid
					error : function(request) {
						$("#status").text("ok");
					},
					success : function(data) {
						$("#status").text("ok");
					}
				});
			} else
				alert("非持续集成方式，此控件无效");
		}
	</script>
<body
	style="background-color: #E6E6FA; text-align: center; height: 100%">

	<div id="container" class="container">

		<div id="header"
			style="background-color: #bfafe6; margin-top: 0; margin-bottom: 0; text-align: center; height: 13%;">

			<div
				style="background-color: #bfafe6; text-align: center; width: 10%; float: left;">
				<img style="margin-top: 0; margin-bottom: 0;" src="pic/hitachi.png" />
			</div>
			<div
				style="background-color: #bfafe6; text-align: center; width: 80%; float: left; margin-top: 10px;">
				<b><label
					style="margin-bottom: 0; font-family: Arial, Helvetica, sans-serif; font-size: 40px;">
						PAAS平台</label><label
					style="margin-bottom: 0; font-family: Arial, Helvetica, sans-serif; font-size: 10px;">（测试用）</label></b>
			</div>
			<div
				style="background-color: #bfafe6; text-align: center; width: 10%; float: right; margin-top: 20px">
				<label style="text-align: center;" class="text">welcome
					${username }</label>
			</div>
		</div>

		<div id="menu" class="menu">
			<br> <a
				href="twochoices?username=${username}&usermail=${usermail}"
				title="首页" style="top: 0; position: relative;"><img
				src="pic/home.png" /><br>首页</a><br>
			<br> <a href="choice?username=${username}&usermail=${usermail}"
				title="代码构建" style="top: 1; position: relative;"><img
				src="pic/coder.png" /><br>代码构建</a><br>
			<br> <a href="upload?username=${username}&usermail=${usermail}"
				title="镜像列表" style="top: 2; position: relative;"><img
				src="pic/Pimage.png" /><br>镜像列表</a><br>
			<br> <a
				href="resource?username=${username}&usermail=${usermail}"
				title="资源配置" style="top: 3; position: relative;"><img
				src="pic/resource.png" /><br>资源配置</a><br>
			<br> <a href="applist?username=${username}&usermail=${usermail}"
				title="应用列表" style="top: 4; position: relative;"><img
				src="pic/app.png" /><br>应用列表</a><br><br> <a
				href="servicelist?username=${username}&usermail=${usermail}"
				title="服务列表" style="top: 4; position: relative;"><img
				src="pic/bell.png" /><br>服务列表</a><br>

		</div>
		<!--	
		<div
			style="float: left; text-align: left; width: 2%; height: 75%; background-color: #d9d2e9;">
			<img style="top:192px;position: relative;display: inline-block" src="pic/ssss.png"/>
		</div>
		  -->

		<%
			//驱动程序名   
			String driverName = "com.mysql.jdbc.Driver";
			//数据库用户名   
			String userName = "root";
			//密码   
			String userPasswd = "123456";
			//数据库名   
			String dbName = "helloclouddb";
			//表名   
			String tableName = "imagelist";
			//联结字符串   
			String url = "jdbc:mysql://10.97.144.83:3306/" + dbName + "?user=" + userName + "&password=" + userPasswd;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection = DriverManager.getConnection(url);
			Statement statement = connection.createStatement();
			String sql = "SELECT * FROM " + tableName;
			ResultSet rs = statement.executeQuery(sql);
		%>



		<div id="content" class="content">
			<br> <label class="text">镜像仓库</label><br>
			<label class="label1">此处的镜像，由前一步的本地上传war包生成，未作创建镜像，此处为空<br>资源选择一栏
				在为保存配置之前为空，部署前请先设置配置<br>点击部署后，后台调用marathon工具将镜像实例成容器
			</label> <br> <br> <br>
			<TABLE id="table1"
				style="BORDER-RIGHT: #005c8a 1px dashed; BORDER-TOP: #005c8a 1px dashed; BORDER-LEFT: #005c8a 1px dashed; BORDER-BOTTOM: #005c8a 1px dashed; BORDER-COLLAPSE: collapse"
				borderColor=#000000 height=40 cellPadding=1 width=500 align=center
				border=5>
				<tr>
					<td>状态</td>
					<td>项目名称</td>
					<td>镜像名称</td>
					<td>资源选择</td>
					<td>服务选择</td>
				</tr>

				<%
					while (rs.next()) {
				%>
				<tr>
					<td><%out.print(rs.getString(2));%></td>
					<td><%out.print(rs.getString(3));%></td>
					<td><%out.print(rs.getString(4));%></td>		
					<td>
						<%
							//驱动程序名   
								String driverName1 = "com.mysql.jdbc.Driver";
								//数据库用户名   
								String userName1 = "root";
								//密码   
								String userPasswd1 = "123456";
								//数据库名   
								String dbName1 = "helloclouddb";
								//表名   
								String tableName1 = "resourcelist";
								//联结字符串   
								String url1 = "jdbc:mysql://10.97.144.83:3306/" + dbName1 + "?user=" + userName1 + "&password="
										+ userPasswd1;
								Class.forName("com.mysql.jdbc.Driver").newInstance();
								Connection connection1 = DriverManager.getConnection(url1);
								Statement statement1 = connection1.createStatement();
								String sql1 = "SELECT * FROM " + tableName1;
								ResultSet rs1 = statement1.executeQuery(sql1);
						%> <select>
							<%
								while (rs1.next()) {
							%>
							<option><%out.print(rs1.getString(2));%></option>
							<%
								}
							%>
					</select>
					</td>
						<td>
					<select>
					<option>无</option>
						<%
							//驱动程序名   
								String driverName2 = "com.mysql.jdbc.Driver";
								//数据库用户名   
								String userName2 = "root";
								//密码   
								String userPasswd2 = "123456";
								//数据库名   
								String dbName2 = "helloclouddb";
								//表名   
								String tableName2 = "servicelist";
								//联结字符串   
								String url2 = "jdbc:mysql://10.97.144.83:3306/" + dbName2 + "?user=" + userName2 + "&password="
										+ userPasswd2;
								Class.forName("com.mysql.jdbc.Driver").newInstance();
								Connection connection2 = DriverManager.getConnection(url2);
								Statement statement2= connection2.createStatement();
								String sql2 = "SELECT * FROM " + tableName2;
								ResultSet rs2= statement2.executeQuery(sql2);
						%> 
							<%
								while (rs2.next()) {
							%>
							<option><%out.print(rs2.getString(1));%></option>
							<%
								}
							%>
					</select>
					</td>
					<td>
						<form id="form1" method="post">
							<input type="hidden" id="imagename" name="imagename"
								value="<%=rs.getString(4)%>"> <input type="hidden"
								id="filename" name="filename" value="<%=rs.getString(3)%>">
							<input type="hidden" id="filename_0" name="filename_0"
								value="${filename_0}"> <input type="hidden"
								id="username" name="username" value="${username}"> <input
								type="hidden" id="usermail" name="usermail" value="${usermail}">
											<input type="hidden"	id="from" name="from" value="create">
							<input type="hidden" id="code" name="code" value="0"> <input
								type="hidden" id="resource" name="resource" value="0"> <input
								type="button" name="submit" id="submit" class="inputSubmit"
								value="部署" onclick="" />
						</form>
					</td>
					<td> 
					 <form action="image_delete" method="post">
							<input type="hidden" id="imagename" name="imagename"	value="<%=rs.getString(4)%>"> 
							<input type="hidden"	id="filename" name="filename" value="<%=rs.getString(3)%>">
							<input type="hidden" id="filename_0" name="filename_0" value="<%=rs.getString(3)%>"> 
							<input type="hidden"	id="username" name="username" value="${username}"> 
							<input type="hidden" id="usermail" name="usermail" value="${usermail}">

						
							<input type="hidden" id="code" name="code" value="0"> 
					    <input type="hidden" id="resource" name="resource" value="00">
						<input type="hidden"	id="from" name="from" value="stop">
                        <input type="submit" id="submit" name="submit" value="删除">
					</form>
					
					</td>
				</tr>
				<%
					}
				%>

			</table>
			<script type="text/javascript">
				$('.inputSubmit')
						.click(
								function() {

									var $td = $(this).parents('tr').children(
											'td');
								//	alert($td.eq(1).text())
									var datas = {
										filename : $td.eq(1).text(),
										imagename : $td.eq(2).text(),
										resource : $(this).parents('tr').find(
												'select').val(),
										username : $('#username').val(),
										usermail : $('#usermail').val(),
										code : $('#code').val(),
										from : $('#from').val()
									}

									document.getElementById("loading").style.display = "";
									$
											.ajax({
												type : "POST",
												url : 'marathon',
												data : datas,
												success : function(data) {
													document
															.getElementById("loading").style.display = "none";
													window.location = "applist?username=${username}&usermail=${usermail}"
												}

											});

								})
			</script>
		</div>
		<div id="footer" class="footer">Copyright © HSCNZJ</div>
	</div>
</body>
</html>