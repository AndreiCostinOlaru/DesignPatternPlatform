<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <style>
    .header-logo {
      font-family: "Playfair Display", Georgia, "Times New Roman", serif/*rtl:Amiri, Georgia, "Times New Roman", serif*/;
      font-size: 2.25rem;
    }
  </style>
</head>

<body>
  <div class="container">
    <header class="border-bottom lh-1 py-3">
      <div class="row flex-nowrap justify-content-between align-items-center">
        <div class="col-4 pt-1">
<%--          <a class="link-secondary" href="#">Subscribe</a>--%>
        </div>
        <div class="col-4 text-center">
          <a class="header-logo text-body-emphasis text-decoration-none" href="${pageContext.request.contextPath}/Index">Design Pattern Platform</a>
        </div>
        <div class="col-4 d-flex justify-content-end align-items-center">
          <c:choose>
            <c:when test="${pageContext.request.getRemoteUser() == null}">
              <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/CreateAccount">Create Account</a>
              <span style="margin-left: 10px;"></span>
              <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/Login">Login</a>
            </c:when>
            <c:otherwise>
              <a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/Logout">Logout</a>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </header>

    <div class="nav-scroller py-1 mb-3 border-bottom">
      <nav class="nav nav-underline justify-content-between">
        <a class="nav-item nav-link link-body-emphasis ${pageContext.request.requestURI.endsWith('/about.jsp') ? 'active' : ''}" href="${pageContext.request.contextPath}/about.jsp">About</a>
        <a class="nav-item nav-link link-body-emphasis ${pageContext.request.requestURI.endsWith('/patterns.jsp') ? 'active' : ''}" href="${pageContext.request.contextPath}/Patterns">Patterns</a>
        <a class="nav-item nav-link link-body-emphasis ${pageContext.request.requestURI.endsWith('/discussions.jsp') ? 'active' : ''}" href="${pageContext.request.contextPath}/Discussions">Discussions</a>
      </nav>
    </div>
  </div>
</header>
</body>
</html>
