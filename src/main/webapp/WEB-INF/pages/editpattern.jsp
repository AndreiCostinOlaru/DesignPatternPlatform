<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Edit pattern">
  <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/EditPattern">
    <div class="row g-3">
      <div class="col-sm-6">
        <label for="PatternName" class="form-label">Pattern name</label>
        <input type="text" class="form-control" id="patternName" name="patternName" placeholder="" value="${pattern.name}" required>
        <div class="invalid-feedback">
          Name is required.
        </div>
      </div>
      <div class="col-md-5">
        <label for="patternType" class="form-label">Pattern Type</label>
        <select class="form-select" id="patternType" name="patternType" required>
          <option ${pattern.type eq "Creational" ? 'selected':''}>Creational</option>
          <option ${pattern.type eq "Structural" ? 'selected':''}>Structural</option>
          <option ${pattern.type eq "Behavioral" ? 'selected':''}>Behavioral</option>
        </select>
        <div class="invalid-feedback">
          Please select a valid pattern type.
        </div>
      </div>

      <div class="col-md-5">
        <label for="patternScope" class="form-label">Pattern Scope</label>
        <select class="form-select" id="patternScope" name="patternScope" placeholder="${pattern.scope}" required>
          <option ${pattern.scope eq "Object" ? 'selected':''}>Object</option>
          <option ${pattern.scope eq "Class" ? 'selected':''}>Class</option>
        </select>
        <div class="invalid-feedback">
          Please select a valid pattern scope.
        </div>
      </div>


      <div class="col-md-5">
        <label for="owner_id" class="form-label">Author</label>
        <select class="form-select" id="owner_id" name="owner_id" required>
          <option value="" disabled>Choose...</option>
          <c:forEach var="user" items="${users}">
            <c:if test="${pageContext.request.getRemoteUser() eq user.username}">
              <option value="${user.id}">${user.username}</option>
            </c:if>
          </c:forEach>
        </select>
        <div class="invalid-feedback">
          Please select a valid pattern scope.
        </div>
      </div>
    </div>
      <div class="row g-3">
        <div class="col-md-5">
          <label for="description" class="form-label">Description</label>
          <textarea class="form-control" id="description" name="description" rows="3" required>${pattern.description}</textarea>
          <div class="invalid-feedback">
            Please provide a description for the pattern.
          </div>
        </div>
          <div class="col-md-5">
            <label for="code" class="form-label">Code example</label>
            <textarea class="form-control" id="code" name="code" rows="3" >${pattern.code}</textarea>
          </div>
      </div>
      <input type="hidden" name="patternId" value="${pattern.id}" />
      <button class="btn btn-primary btn-lg float-end" type="submit">Edit Pattern</button>
  </form>
</t:pageTemplate>