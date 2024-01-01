<%@tag description="base page template" pageEncoding="UTF-8"%>
<%@attribute name="pageTitle" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</head>
<body>
<jsp:include page="/WEB-INF/pages/menu.jsp" />
<div class="container mt-5">
    <h1>  ${pageTitle}</h1>
    <main class="lh-1 py3">
        <jsp:doBody/>
    </main>
</div>
<jsp:include page="/WEB-INF/pages/footer.jsp" />
<script src="${pageContext.request.contextPath}/form-validation.js"></script>
</body>
</html>