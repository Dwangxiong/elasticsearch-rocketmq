<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>
<body>
<center>
	<h2 >登录程序</h2>
	<c:if test="${param.error=='true'}">
		<span style="color:red">登录失败,错误的用户名或密码</span>
	</c:if>
	<form action="/login" method="post">
      <table>
         <tr>
            <td> 用户名：</td>
            <td><input type="text" name="username" id="username"/></td>
         </tr>
         <tr>
            <td> 密码：</td>
            <td><input type="password" name="password" id="password"/></td>
         </tr>
         <tr>
         	<td><input type="checkbox" name="remember-me" id="remember-me"/>记住我</td>
         </tr>
         <tr>
            <td colspan="2" align="center">
                <input type="submit" value=" 登录 "/>
                <input type="reset" value=" 重置 "/>
            </td>
         </tr>
      </table>
   </form>
</center>
</body>
</html>