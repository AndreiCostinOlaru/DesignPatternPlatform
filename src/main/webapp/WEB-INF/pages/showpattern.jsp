<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    *{
        margin: 0;
        padding: 0;
    }
    .rate {
        float: left;
        height: 46px;
        padding: 0 10px;
    }
    .form-container {
        display: flex;
        flex-direction: column;
        align-items: flex-end;
    }

    .rate {
        text-align: right;
    }
    .rate:not(:checked) > input {
        position:absolute;
        top:-9999px;
    }
    .rate:not(:checked) > label {
        float:right;
        width:1em;
        overflow:hidden;
        white-space:nowrap;
        cursor:pointer;
        font-size:30px;
        color:#ccc;
    }
    .rate:not(:checked) > label:before {
        content: '★ ';
    }
    .rate > input:checked ~ label {
        color: #ffc700;
    }
    .rate:not(:checked) > label:hover,
    .rate:not(:checked) > label:hover ~ label {
        color: #deb217;
    }
    .rate > input:checked + label:hover,
    .rate > input:checked + label:hover ~ label,
    .rate > input:checked ~ label:hover,
    .rate > input:checked ~ label:hover ~ label,
    .rate > label:hover ~ input:checked ~ label {
        color: #c59b08;
    }
</style>
<t:pageTemplate pageTitle="${pattern.name}">
    <c:if test="${pageContext.request.getRemoteUser() ne null && pageContext.request.getRemoteUser() ne ''}">
        <div class="form-container">
            <form action="${pageContext.request.contextPath}/Review" method="POST">
                <div class="rate">
                    <input type="radio" id="star5" name="review" value="5" />
                    <label for="star5" title="text">5 stars</label>
                    <input type="radio" id="star4" name="review" value="4" />
                    <label for="star4" title="text">4 stars</label>
                    <input type="radio" id="star3" name="review" value="3" />
                    <label for="star3" title="text">3 stars</label>
                    <input type="radio" id="star2" name="review" value="2" />
                    <label for="star2" title="text">2 stars</label>
                    <input type="radio" id="star1" name="review" value="1" />
                    <label for="star1" title="text">1 star</label>
                </div>
                <input type="hidden" value="${pattern.id}" name="postId">
                <c:forEach var="user" items="${users}">
                    <c:if test="${pageContext.request.getRemoteUser() eq user.username}">
                        <input type="hidden" name="userId" value="${user.id}">
                    </c:if>
                </c:forEach>
                <input type="submit" class="btn btn-primary" style="display: none" value="Submit">
            </form>
        </div>
        <br><br><br><br>
    </c:if>
    <c:if test="${message != null}">
        <div class="alert alert-warning" role="alert">
                ${message}
        </div>
    </c:if>
    <c:if test="${pattern.publicOpinion ne null && pattern.publicOpinion ne ''}">
        Public Opinion: ${pattern.publicOpinion}
    </c:if>
    <div class="row g-3">
        <div class="col-md-5">
            Pattern Type: ${pattern.type}
        </div>
    </div>
    <div class="row g-3">
        <div class="col-md-5">
            Pattern Scope: ${pattern.scope}
        </div>
    </div>
    <div class="row g-3">
        <div class="col-md-5">
            Author: ${pattern.author}
        </div>
    </div>
    <br><br>
    <h2>Description</h2>
    <hr>
    <div class="container d-flex justify-content-center align-items-center">
        <pre style="white-space: pre-wrap;">${pattern.description}</pre>
    </div>

    <div class="row justify-content-center">
        <c:forEach items="${pattern.photos}" var="photo">
            <div class="col-md-5 mb-3">
                <img src="${pageContext.request.contextPath}/PatternPhoto?id=${photo.id}" class="img-fluid" alt="Pattern Photo">
            </div>
        </c:forEach>
    </div>
    <c:if test="${pattern.code ne null && pattern.code ne ''}">
        <h2>Code example</h2>
        <hr>
        <div class="container">
            <div class="row">
                <div class="col-md-6 offset-md-3">
                <pre><code>${pattern.code}</code></pre>
                </div>
            </div>
        </div>
    </c:if>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        var submitButton = document.querySelector('input[type="submit"]');
        submitButton.style.display = 'none';
        var star5 = document.getElementById('star5');
        var star4 = document.getElementById('star4');
        var star3 = document.getElementById('star3');
        var star2 = document.getElementById('star2');
        var star1 = document.getElementById('star1');
        star5.addEventListener('change',toggleSubmitButtonVisibility);
        star4.addEventListener('change',toggleSubmitButtonVisibility);
        star3.addEventListener('change',toggleSubmitButtonVisibility);
        star2.addEventListener('change',toggleSubmitButtonVisibility);
        star1.addEventListener('change',toggleSubmitButtonVisibility);
        function toggleSubmitButtonVisibility() {
            if (star5.checked || star4.checked || star3.checked || star2.checked || star1.checked) {
                submitButton.style.display = 'block';
            } else {
                submitButton.style.display = 'none';
            }
        }
    });
</script>
</t:pageTemplate>
