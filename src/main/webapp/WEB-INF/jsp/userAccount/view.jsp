<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags/template" prefix="template" %>
<template:admin>
<jsp:attribute name="extraStyles">
<link rel="stylesheet" href="<c:url value='/assets/css/pagination/jqpagination.css'/>" />
</jsp:attribute>
<jsp:attribute name="extraScripts">
<script src="<c:url value='/assets/js/jquery.jqpagination.js'/>"></script>
</jsp:attribute>
<jsp:body>
  <div>
    <div class ="container min-container">
    <c:if test="${userSession.userAuthenticated}">
      Hello, ${user.firstName} ${user.lastName}! <br />
    	<a href="<c:url value="/useraccount/logout"/>">Logout</a>
    </c:if>
    
      <h2 class="basic-title">Connection History</h2>
        <div class="well">
          <table class="table table-condensed table-bordered table-striped table-hover">
          		  <thead>
	                  <tr>
	                  	<td>Connection ID</td>
		                <td>IP Connection</td>
		                <td>Date</td>
		                <td>Valid Connection</td>
	                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items='${history}' var='history'>         		
	                  <tr>
						<td>${history.idConnection}</td>
		                <td>${history.ipConnection}</td>
		                <td>${history.timestampCreation}</td>
		                <td>${history.validConnection}</td>		               
					  </tr>
                  </c:forEach>
                  </tbody>
          </table>		  
        </div>
    </div>
  </div>
</jsp:body>
</template:admin>
