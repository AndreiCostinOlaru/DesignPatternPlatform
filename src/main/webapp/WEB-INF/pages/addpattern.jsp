<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Add pattern">
  <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/AddPattern" enctype="multipart/form-data">
    <div class="row g-3">
      <div class="col-sm-6">
        <label for="PatternName" class="form-label">Pattern name</label>
        <input type="text" class="form-control" id="patternName" name="patternName" placeholder="" value="" required>
        <div class="invalid-feedback">
          Name is required.
        </div>
      </div>
      <div class="col-md-5">
        <label for="patternType" class="form-label">Pattern Type</label>
        <select class="form-select" id="patternType" name="patternType" required>
          <option value="">Choose...</option>
          <option>Creational</option>
          <option>Structural</option>
          <option>Behavioral</option>
        </select>
        <div class="invalid-feedback">
          Please select a valid pattern type.
        </div>
      </div>

      <div class="col-md-5">
        <label for="patternScope" class="form-label">Pattern Scope</label>
        <select class="form-select" id="patternScope" name="patternScope" required>
          <option value="">Choose...</option>
          <option>Object</option>
          <option>Class</option>
        </select>
        <div class="invalid-feedback">
          Please select a valid pattern scope.
        </div>
      </div>


      <div class="col-md-5">
        <label for="owner_id" class="form-label">Author</label>
        <select class="form-select" id="owner_id" name="owner_id" required>
          <c:forEach var="user" items="${users}">
            <c:if test="${pageContext.request.getRemoteUser() eq user.username}">
              <option value="${user.id}">${user.username}</option>
            </c:if>
          </c:forEach>
        </select>
        <div class="invalid-feedback">
          Please select a valid author.
        </div>
      </div>
      <div class="row g-3">
        <div class="col-md-5">
          <label for="description" class="form-label">Description</label>
          <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
        <div class="invalid-feedback">
          Please provide a description for the pattern.
        </div>
        </div>
        <div class="col-md-5">
          <label for="code" class="form-label">Code example(optional)</label>
          <textarea class="form-control" id="code" name="code" rows="3"></textarea>
        </div>
      </div>
      <div class="row g-3">
        <div class="col-md-5">
          <label for="file">Add Photo(s)</label>
          <input type="file" name="file" id="file" multiple>
        </div>
      </div>
    <button class="btn btn-primary btn-lg" type="submit">Add Pattern</button>
    </div>
  </form>
</t:pageTemplate>