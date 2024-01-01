<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="height: 100vh;">

<form class="form-signin text-center p-4 bg-white rounded shadow" method="POST" action="j_security_check" style="width: 300px;">
  <h1 class="h3 mb-3 font-weight-normal">Login</h1>

  <c:if test="${message != null}">
    <div class="alert alert-warning" role="alert">
        ${message}
    </div>
  </c:if>

  <label for="username" class="sr-only">Username</label>
  <input type="text" class="form-control mb-2" id="username" name="j_username" placeholder="Username" value="" required autofocus>

  <label for="password" class="sr-only">Password</label>
  <input type="password" class="form-control mb-2" id="password" name="j_password" placeholder="Password" value="" required>

  <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
  <a href="#" onclick="history.back();" class="btn btn-sm btn-secondary mt-2">Back</a>
</form>

<!-- Bootstrap JS and jQuery -->
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
