<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<t:pageTemplate pageTitle="Edit pattern">
  <form class="needs-validation" novalidate method="POST" action="${pageContext.request.contextPath}/EditDiscussion">
    <div class="row g-3">
      <div class="col-sm-6">
        <label for="discussionName" class="form-label">Discussion name</label>
        <input type="text" class="form-control" id="discussionName" name="discussionName" placeholder="" value="${discussion.name}" required>
        <div class="invalid-feedback">
          Name is required.
        </div>
      </div>
      <div class="col-md-5">
        <label for="pattern" class="form-label">Pattern</label>
        <select class="form-select" id="pattern" name="pattern" required>
          <c:forEach var="pattern" items="${patterns}">
            <option value="${pattern.id}" ${pattern.name eq discussion.pattern.name ? 'selected':''}>${pattern.name}</option>
          </c:forEach>
        </select>
        <div class="invalid-feedback">
          Please select a valid pattern.
        </div>
      </div>
    </div>
    <div class="row g-3">
      <div class="col-md-5">
          <label for="description" class="form-label">Description</label>
          <textarea class="form-control" id="description" name="description" rows="3" required>${discussion.description}</textarea>
          <div class="invalid-feedback">
            Please provide a description for the pattern.
      </div>
      </div>
        <div class="col-md-5">
            <label for="code" class="form-label">Code example</label>
            <textarea class="form-control" id="code" name="code" rows="3" >${discussion.code}</textarea>
        </div>
      </div>
      <input type="hidden" name="discussionId" value="${discussion.id}" />
      <button class="btn btn-primary btn-lg float-end" type="submit">Edit Discussion</button>
  </form>
</t:pageTemplate>