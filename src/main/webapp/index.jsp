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
</style>
<t:pageTemplate pageTitle="Design Pattern Platform">
    <div class="container text-center">
        <div class="row mt-4">
            <div class="col-md-6 mb-4">
                <h2>Creational Patterns</h2>
                <div class="row">
                    <div class="col-md-6">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Class</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${patterns}" var="pattern">
                                <c:if test="${pattern.type eq 'Creational' && pattern.scope eq 'Class'}">
                                    <tr>
                                        <td><a class="link-unstyled" href="${pageContext.request.contextPath}/ShowPattern?id=${pattern.id}">${pattern.name}</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Object</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${patterns}" var="pattern">
                                <c:if test="${pattern.type eq 'Creational' && pattern.scope eq 'Object'}">
                                    <tr>
                                        <td><a class="link-unstyled" href="${pageContext.request.contextPath}/ShowPattern?id=${pattern.id}">${pattern.name}</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-md-6 mb-4">
                <h2>Structural Patterns</h2>
                <div class="row">
                    <div class="col-md-6">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Class</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${patterns}" var="pattern">
                                <c:if test="${pattern.type eq 'Structural' && pattern.scope eq 'Class'}">
                                    <tr>
                                        <td><a class="link-unstyled" href="${pageContext.request.contextPath}/ShowPattern?id=${pattern.id}">${pattern.name}</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Object</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${patterns}" var="pattern">
                                <c:if test="${pattern.type eq 'Structural' && pattern.scope eq 'Object'}">
                                    <tr>
                                        <td><a class="link-unstyled" href="${pageContext.request.contextPath}/ShowPattern?id=${pattern.id}">${pattern.name}</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        <div class="row justify-content-center mt-4">
            <div class="col-md-6 mb-4">
                <h2>Behavioral Patterns</h2>
                <div class="row">
                    <div class="col-md-6">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Class</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${patterns}" var="pattern">
                                <c:if test="${pattern.type eq 'Behavioral' && pattern.scope eq 'Class'}">
                                    <tr>
                                        <td><a class="link-unstyled" href="${pageContext.request.contextPath}/ShowPattern?id=${pattern.id}">${pattern.name}</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table">
                            <thead>
                            <tr>
                                <th>Object</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${patterns}" var="pattern">
                                <c:if test="${pattern.type eq 'Behavioral' && pattern.scope eq 'Object'}">
                                    <tr>
                                        <td><a class="link-unstyled" href="${pageContext.request.contextPath}/ShowPattern?id=${pattern.id}">${pattern.name}</a></td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</t:pageTemplate>