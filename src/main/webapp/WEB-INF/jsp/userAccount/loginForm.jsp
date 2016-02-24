<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<template:admin>
  <div>
    <div class ="container min-container">      
    <h2 class="basic-title">Login</h2>
      <form action="<c:url value="login"/>" method="POST">
  		<fieldset>
   			 <legend></legend>
        
   			 <label for="login">Login:</label>
     		 <input id="userAccount.userName" type="text" name="userAccount.userName" value='${userAccount.userName}'/>

   			 <label for="senha">Senha:</label>
     		 <input id="userAccount.userPwd" type="password" name="userAccount.userPwd" value='${userAccount.userPwd}'/>

    		<button type="submit">Login</button>
 		 </fieldset>
		</form>
    </div>
    <div class ="container min-container">   
    	<a href="<c:url value="/useraccount/form"/>">Register</a>
    </div>
  </div>
</template:admin>