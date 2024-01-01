<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:pageTemplate pageTitle="Create Account">
  <div class="container mt-4">
    <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/CreateAccount">
      <div class="row">
        <div class="col-md-6 mb-3">
          <label for="username" class="form-label">Username</label>
          <input type="text" class="form-control" id="username" name="username" placeholder="Enter username" value="" required autofocus>
          <div class="invalid-feedback">
            Username is required.
          </div>
        </div>
        <div class="col-md-6 mb-3">
          <label for="email" class="form-label">Email</label>
          <input type="email" class="form-control" id="email" name="email" placeholder="Enter email" value="" required>
          <div class="invalid-feedback">
            Email is required.
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6 mb-3">
          <label for="password" class="form-label">Password</label>
          <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" value="" required>
          <div class="invalid-feedback">
            Password is required.
          </div>
        </div>
      </div>
      <c:forEach var="user_group" items="${userGroups}" varStatus="status">
        <input type="hidden" name="user_groups" id="user_groups" value="${user_group}"/>
      </c:forEach>
      <div class="text-center">
        <button class="btn btn-primary" type="submit">Create Account</button>
      </div>
    </form>
  </div>
</t:pageTemplate>
