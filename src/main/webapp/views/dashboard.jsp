<!DOCTYPE html>
<html>
<head>
  <title>Computer Database</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta charset="utf-8">
  <!-- Bootstrap -->
  <link href="resources/css/bootstrap.min.css" rel="stylesheet"
        media="screen">
  <link href="resources/css/font-awesome.css" rel="stylesheet"
        media="screen">
  <link href="resources/css/main.css" rel="stylesheet" media="screen">
  <link href="resources/css/toaster.css" rel="stylesheet" media="screen">

</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
  <%@ include file="core/header.jsp" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  <%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
  <%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
  <%@ taglib prefix="page" uri="/WEB-INF/pagination.tld" %>
</header>

<section id="main">
  <div class="container">
    <h1 id="homeTitle">${ nbComputerDto }
      Computers found
    </h1>

    <div id="actions" class="form-horizontal">
      <div class="pull-left">
        <form id="searchForm" action="dashboard" method="GET" class="form-inline">
          <input type="hidden" name="action" value="option">
          <input type="hidden" name="param" value="search">
          <input type="search" id="searchbox" name="value"
                 class="form-control" placeholder="Search name"/> <input
          type="submit" id="searchsubmit" value="Filter by name"
          class="btn btn-primary"/>
        </form>
      </div>
      <div class="pull-right">
        <a class="btn btn-success" id="addComputer" href="addComputer">Add
          Computer</a> <a class="btn btn-default" id="editComputer" href="#"
                          onclick="$.fn.toggleEditMode();">Edit</a>
      </div>
    </div>
  </div>

  <form id="deleteForm" action="dashboard?action=deleteComputer"
        method="POST">
    <input type="hidden" name="selection" value="">
  </form>

  <div class="container" style="margin-top: 10px;">
    <table class="table table-striped table-bordered">
      <thead>
      <tr>
        <th class="editMode" style="width: 60px; height: 22px;"><input
          type="checkbox" id="selectall"/> <span
          style="vertical-align: top;"> - <a href="#"
                                             id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
          class="fa fa-trash-o fa-lg"></i>
                </a>
              </span></th>
        <th>Computer name</th>
        <th>Introduced date</th>
        <th>Discontinued date</th>
        <th>Company</th>

      </tr>
      </thead>
      <tbody id="results">
      <c:forEach items="${listComputerDto}" var="computerDto">
        <tr>
          <td class="editMode"><input type="checkbox" name="cb"
                                      class="cb" value="${computerDto.id }"></td>
          <td><a href="editComputer?id=${computerDto.id }" onclick="">${ computerDto.name }</a>
          </td>
          <td>${ computerDto.introduced }</td>
          <td>${ computerDto.discontinued }</td>
          <td>${ computerDto.companyDto.name }</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</section>
<span id="top-link-block" class="hidden fab-goto-top"> <span
  class="fab-content"
  onclick="$('html,body').animate({scrollTop:0},'slow');return false;">
        <i class="glyphicon glyphicon-chevron-up"></i>
    </span>
    </span>

<footer class="navbar-fixed-bottom">
  <%@ include file="core/footer.jsp" %>
  <div class="container text-center">
    <ul class="pagination">
      <page:link numPage="0" label="&laquo;"/>
      <page:link numPage="${ page-1 }" label="&lt;"/>
      <page:pagination numPage="${ page }" maxPage="${ maxPage }"/>
      <page:link numPage="${ page+1 }" label="&gt;"/>
      <page:link numPage="${ maxPage }" label="&raquo;"/>
    </ul>

    <div class="pull-right" role="group">
      <ul class="pagination">
        <c:forEach items="${nbObjectAvailablePerPage}" var="nbObject">
          <li
            <c:if test="${ nbObject  == nbObjectPerPage}">
              <c:out value='class=active'/>
            </c:if>><a
            href="dashboard?action=changeNbComputer&nbObject=${ nbObject }">${ nbObject }</a></li>
        </c:forEach>
      </ul>
    </div>
  </div>
</footer>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/dashboard.js"></script>
<script src="resources/js/main.js"></script>
<script>
  if (($(window).height() + 100) < $(document).height()) {
    $('#top-link-block').removeClass('hidden').affix({
      offset: {
        top: 100
      }
    });
  }
</script>

</body>
</html>
