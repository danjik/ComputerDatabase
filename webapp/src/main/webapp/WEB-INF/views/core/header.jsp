
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container nav">
  <a class="navbar-brand" href="dashboard?page=0&column=${ options.sort }&search=${options.search }&nbObject=${nbObjectPerPage}"> 
  	Application - Computer Database 
  </a>
  <sec:authorize access="isAnonymous()">
    <form method="POST" action="<c:url value='/login'/>" >
    <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
        Username: <input name="username" type="text" /> 
        Password: <input name="password" type="password" /> 
        <input type="submit" value="Sign in" />
    </form>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
    <a href="<c:url value="/logout" />">Logout</a>
</sec:authorize>
</div>
