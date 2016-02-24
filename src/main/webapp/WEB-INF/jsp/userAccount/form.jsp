
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<template:admin>
  <div>
    <div class ="container min-container">      
    <h2 class="basic-title">Login</h2>
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
    </div>
    <div class ="container min-container">   
    	<a href="<c:url value="/useraccount/form"/>">Register</a>
    </div>
  </div>
</template:admin>