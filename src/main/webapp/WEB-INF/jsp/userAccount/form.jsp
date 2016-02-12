<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="add" method="POST">
    First Name:
    <input type="text" name="userAccount.firstName" id="userAccount.firstName" value='${userAccount.firstName}' /><br/>
    Last Name:
    <input type="text" name="userAccount.lastName" id="userAccount.lastName" value='${userAccount.lastName}'/><br/>
    User Name:
    <input type="text" name="userAccount.userName" id="userAccount.userName" value='${userAccount.userName}'/><br/>
    Password:
    <input type="text" name="userAccount.userPwd" id="userAccount.userPwd" value='${userAccount.userPwd}'/><br/>
    Email:
    <input type="text" name="userAccount.email" id="userAccount.email" value='${userAccount.email}'/><br/>
    Born Date:
    <input type="text" name="userAccount.bornDate" id="userAccount.bornDate" value='${userAccount.bornDate}'/><br/>
    Mobile:
    <input type="text" name="userAccount.mobilePhone" id="userAccount.mobilePhone" value='${userAccount.mobilePhone}'/><br/>
    Home Phone:
    <input type="text" name="userAccount.homePhone" id="userAccount.homePhone" value='${userAccount.homePhone}'/><br/>
    Work Phone:
    <input type="text" name="userAccount.workPhone" id="userAccount.workPhone" value='${userAccount.workPhone}'/><br/>
     Admin Account:
    <input type="checkbox" name="userAccount.adminAccount" id="userAccount.adminAccount" checked="checked"/><br/>

    <input type="submit" value="Save" />
</form>
</body>
</html>