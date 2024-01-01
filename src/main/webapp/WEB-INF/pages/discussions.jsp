<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
  .link-unstyled {
text-decoration: none;
color: maroon;
display: inline;
background-color: transparent;
border: none;
}
  #myInput {
    /*background-image: url('/css/searchicon.png'); !* Add a search icon to input *!*/
    background-position: 10px 12px; /* Position the search icon */
    background-repeat: no-repeat; /* Do not repeat the icon image */
    width: 100%; /* Full-width */
    font-size: 16px; /* Increase font-size */
    padding: 12px 20px 12px 40px; /* Add some padding */
    border: 1px solid #ddd; /* Add a grey border */
    margin-bottom: 12px; /* Add some space below the input */
  }

  #myTable {
    border-collapse: collapse; /* Collapse borders */
    width: 100%; /* Full-width */
    border: 1px solid #ddd; /* Add a grey border */
    font-size: 18px; /* Increase font-size */
  }

  #myTable th, #myTable td {
    text-align: left; /* Left-align text */
    padding: 12px; /* Add padding */
  }

  #myTable tr {
    /* Add a bottom border to all table rows */
    border-bottom: 1px solid #ddd;
  }

  #myTable tr.header, #myTable tr:hover {
    /* Add a grey background color to the table header and on hover */
    background-color: #f1f1f1;
  }
</style>

<t:pageTemplate pageTitle="Discussions">
  <form method="POST" action="${pageContext.request.contextPath}/Discussions">
    <c:if test="${pageContext.request.getRemoteUser() != null}">
      <div class="container float-end">
        <div class="row float-end">
          <div class="col-auto">
            <a class="btn btn-primary btn-lg" href="${pageContext.request.contextPath}/CreateDiscussion">Create discussion</a>
          </div>
          <div class="col-auto">
            <button class="btn btn-danger btn-lg ml-auto" type="submit">Delete discussion(s)</button>
          </div>
        </div>
      </div>
    </c:if>
    <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Search for discussion by name...">
      <table id="myTable">
        <thead>
          <tr>
            <th style="width:20%;">&nbsp;</th>
            <th style="width:20%;">Name</th>
            <th style="width:20%;">Pattern</th>
            <th style="width:20%;">Type</th>
            <th style="width:20%;">Author</th>
            <th style="width:20%;">&nbsp;</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach var="discussion" items="${discussions}">
          <tr>
            <td>
              <c:if test="${pageContext.request.getRemoteUser() eq 'admin' || pageContext.request.getRemoteUser() eq discussion.author}">
                <input type="checkbox" name="discussion_ids" value="${discussion.id}">
              </c:if>
            </td>
            <td>
                <a class="link-unstyled" href="${pageContext.request.contextPath}/ShowDiscussion?id=${discussion.id}">${discussion.name}</a>
            </td>
            <td>${discussion.pattern.getName()}</td>
            <td>${discussion.pattern.getType()}</td>
            <td>${discussion.author}</td>
            <td>
              <c:if test="${pageContext.request.getRemoteUser() eq 'admin' || pageContext.request.getRemoteUser() eq discussion.author}">
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/EditDiscussion?id=${discussion.id}">Edit Discussion</a>
              </c:if>
            </td>
          </tr>
        </c:forEach>
        </tbody>
    </table>
  </form>


  <script>
    function myFunction() {
      let input, filter, table, tr, td, i, txtValue;
      input = document.getElementById("myInput");
      filter = input.value.toUpperCase();
      table = document.getElementById("myTable");
      tr = table.getElementsByTagName("tr");

      for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[1]; // Column index of Name

        if (td) {
          txtValue = td.textContent || td.innerText;

          if (txtValue.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
          } else {
            tr[i].style.display = "none";
          }
        }
      }
    }
  </script>

</t:pageTemplate>