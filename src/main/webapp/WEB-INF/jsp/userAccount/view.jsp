<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<template:admin>
  <div>
    <div class ="container min-container">      
    <c:if test="${userSession.userAuthenticated}">
      Hello, ${userSession.name }! 
    	<a href="<c:url value="/logout"/>">Logout</a>
    </c:if>
    </div>
    <div class ="container min-container">   
    	<a href="<c:url value="/useraccount/form"/>">Register</a>
    </div>
  </div>
</template:admin>