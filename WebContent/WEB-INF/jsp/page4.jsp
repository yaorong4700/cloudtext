<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,java.io.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="js/jquery-3.0.0.js"></script>
<script type="text/javascript" src="js/jquery-3.0.0.min.js"></script>
<script type="text/javascript" src="js/jQuery-prompt21.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务列表</title>
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

.choice {
	width: 49%;
	height: 100%;
	float: left;
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
	function onloading() {
		document.getElementById("loading").style.display = "";
	}
	function saving() {
		document.getElementById("loading").style.display = "";
		$
				.ajax({
					type : "POST",
					url : 'save',
					data : $('#form1').serialize(),// 你的formid
					error : function(request) {
					},
					success : function(data) {
						document.getElementById("loading").style.display = "none";
						window.location.href = 'resource?username=${username}&usermail=${usermail}';
					}
				});
	}
</script>

<body
	style="background-color: #E6E6FA; text-align: center; height: 100%">
	<div id="loading" class="spinner" style="display: none;"></div>
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
				src="pic/home.png" /><br>首页</a><br> <br> <a
				href="choice?username=${username}&usermail=${usermail}" title="代码构建"
				style="top: 1; position: relative;"><img src="pic/coder.png" /><br>代码构建</a><br>
			<br> <a href="upload?username=${username}&usermail=${usermail}"
				title="镜像列表" style="top: 2; position: relative;"><img
				src="pic/image.png" /><br>镜像列表</a><br> <br> <a
				href="resource?username=${username}&usermail=${usermail}"
				title="资源配置" style="top: 3; position: relative;"><img
				src="pic/resource.png" /><br>资源配置</a><br> <br> <a
				href="applist?username=${username}&usermail=${usermail}"
				title="应用列表" style="top: 4; position: relative;"><img
				src="pic/app.png" /><br>应用列表</a><br><br> <a
				href="servicelist?username=${username}&usermail=${usermail}"
				title="服务列表" style="top: 4; position: relative;"><img
				src="pic/Pbell.png" /><br>服务列表</a><br>
		</div>


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
			String tableName2 = "resourceall";
			//联结字符串   
			String url2 = "jdbc:mysql://192.168.1.89:3306/" + dbName2 + "?user=" + userName2 + "&password=" + userPasswd2;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connection2 = DriverManager.getConnection(url2);
			Statement statement2 = connection2.createStatement();

			String sql2 = "SELECT * FROM " + tableName2;
			ResultSet rs2 = statement2.executeQuery(sql2);
			double rest_cpus = 0;
			double all_cpus = 0;
			double rest_mem = 0;
			double all_mem = 0;
			while (rs2.next()) {
				rest_cpus = rs2.getDouble(2);
				rest_mem = rs2.getDouble(3);
				all_cpus = rs2.getDouble(4);
				all_mem = rs2.getDouble(5);
			}
		%>

		<div id="content" class="content">

			<div class="choice">
				<label class="text">服务配置</label><br> <label class="label1">请根据项目需要合理选择<br></label>
				<br>
				<br>
				<br>

				<div id=form
					style="display: none; POSITION: absolute; left: 50%; top: 50%; width: auto; height: auto; margin-left: -200px; margin-top: -200px; border: 10px solid  #bfafe6; background-color: #7ea7e8; text-align: left;r">
					<br>
					<form style="float: left;" action="dbtask_save_deploy" method="post">
						<input type="hidden" id="username" name="username"
							value="${username}"> 
							<input type="hidden" id="servicetype" name="servicetype"
							> 
							<input type="hidden" id="usermail"
							name="usermail" value="${usermail}"> 
							<input type="hidden"	id="from" name="from" value="create">
							服务名称：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input	type="text" id="servicename" name="servicename" value=""><br>
						    DB用户名：&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="db_user" name="db_user" value=""><br>
					    	DB登陆密码：<input type="password" id="db_passd" name="db_passd"value=""><br> 
							内存：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="mem_db"name="mem_db" value=""><br> 
							存储：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text"id="disk_db" name="disk_db" value=""><br> 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" id="submit" name="submit" value="部署">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input onclick="javascript:closediv();"type="button" id="button" name="button" value="取消" >
				<br>
				<br>
					</form>
				</div>
				<script>
					function opendiv(obj) {
						
						var id=$(obj).attr("id");
					
				    	document.getElementById("servicetype").value=id;
						document.getElementById("form").style.display = "";
					}
					function closediv() {
						document.getElementById("form").style.display = "none";
					}
				</script>

				<button onclick="javascript:opendiv(this);" id="Mysql">
					<img src="pic/Mysql.png">
				</button>
				<button onclick="javascript:opendiv(this);" id="Mongodb">
					<img src="pic/Mongodb.png">
				</button>
				<br>
				<br>
				<button onclick="javascript:opendiv(this);" id="PostgreSQL">
					<img src="pic/PostgreSQL.png">
				</button>
				<button onclick="javascript:opendiv(this);" id="InfluxDB">
					<img src="pic/InfluxDB.png">
				</button>
				<button onclick="javascript:opendiv(this);" id="Redis">
					<img src="pic/Redis.png">
				</button>



			</div>

			<div class="choice">
				<br> <br> <br>
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
					String tableName = "servicelist";
					//联结字符串   
					String url = "jdbc:mysql://192.168.1.89:3306/" + dbName + "?user=" + userName + "&password=" + userPasswd;
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					Connection connection = DriverManager.getConnection(url);
					Statement statement = connection.createStatement();
					String sql = "SELECT * FROM " + tableName;
					ResultSet rs = statement.executeQuery(sql);
				%>
				<TABLE
					style="BORDER-RIGHT: #005c8a 1px dashed; BORDER-TOP: #005c8a 1px dashed; BORDER-LEFT: #005c8a 1px dashed; BORDER-BOTTOM: #005c8a 1px dashed; BORDER-COLLAPSE: collapse"
					borderColor=#000000 height=40 cellPadding=1 width=auto align=center
					border=5>
					<tr>
						<td>服务名称</td>
						<td>DB类型</td>
						<td>DB用户名</td>
						<td>DB密码</td>
						<td>DB访问网址</td>
						<td>DB内存</td>
						<td>DB存储</td>
					</tr>
					<%
						while (rs.next()) {
					%>
					<tr>
						<td><%out.print(rs.getString(1));%></td>
						<td><%out.print(rs.getString(2));%></td>
						<td><%out.print(rs.getString(3));%></td>
						<td><%out.print(rs.getString(4));%></td>
						<td><%out.print(rs.getString(5));%></td>
						<td><%out.print(rs.getString(6));%></td>
						<td><%out.print(rs.getString(7));%></td>
						<td> 
						
						<form action="dbtask_save_deploy" method="post">
						<input type="hidden" id="username" name="username"value="${username}"> 
							<input type="hidden" id="servicetype" name="servicetype"	> 
							<input type="hidden" id="usermail"name="usermail" value="${usermail}"> 
							<input type="hidden" id="servicename" name="servicename" value="<%out.print(rs.getString(1));%>">
						   <input type="hidden" id="db_user" name="db_user" value="<%out.print(rs.getString(3));%>">
						   <input type="hidden" id="db_passd" name="db_passd"	value="<%out.print(rs.getString(4));%>">
						   <input type="hidden" id="mem_db"name="mem_db" value="<%out.print(rs.getString(6));%>">
							<input type="hidden"	id="disk_db" name="disk_db" value="<%out.print(rs.getString(7));%>">
							<input type="hidden"	id="from" name="from" value="fresh">
                           <input type="submit" id="submit" name="submit" value="刷新">
					</form>
					
						 <form action="db_delete" method="post">
							<input type="hidden"	id="filename" name="filename" value="<%=rs.getString(1)%>">
							<input type="hidden"	id="username" name="username" value="${username}"> 
							<input type="hidden" id="usermail" name="usermail" value="${usermail}">
						<input type="hidden"	id="from" name="from" value="stop">
                        <input type="submit" id="submit" name="submit" value="删除">
					</form>
					
						</td>
					</tr>
					<%
						}
					%>

				</table>
			</div>

		</div>

		<div id="footer" class="footer">Copyright © HSCNZJ</div>
	</div>
</body>
</html>