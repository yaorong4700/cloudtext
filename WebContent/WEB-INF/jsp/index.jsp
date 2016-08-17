<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
.text{
font-family: Arial, Helvetica, sans-serif;
	font-size: 20px;
	color: #005c8a;
}

.inputSubmit {
	height:50px;
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
</style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> Paas 平台（内部测试用）</title>
</head>
<br><br> <br> <br> <br> <br>
<body style="background-color: #E6E6FA;text-align:center">
<h1><label class="test">Paas 平台</label><label style=" font-size: 10px;"  >（内部测试用）</label></h1>

  <form action="twochoices" method="post" >  
        <label >用户名:</label><input type="text" name="username"  />  
        <p>  
          <label>密码:</label><input type="password" name="password"/>  
        <p>  
        <label>邮箱:</label><input type="text" name="usermail"/>  
        <p>  
        <input type="submit" value="登录" class="inputSubmit"/>  
        <input type="submit" value="注册"  class="inputSubmit"/>  
    </form>  

</body>
</html>