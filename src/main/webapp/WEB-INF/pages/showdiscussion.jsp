<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://use.fontawesome.com/fe459689b4.js"></script>
<style>
    .link-unstyled {
        text-decoration: none;
        color: maroon;
        display: inline;
        background-color: transparent;
        border: none;
    }
    .reply-button{
        padding: 8px 16px;
    }
    button{
        cursor: pointer;
        outline: 0;
        color: #AAA;

    }

    .btn:focus {
        outline: none;
    }

    .green{
        color: green;
    }

    .red{
        color: red;
    }
</style>

<t:pageTemplate pageTitle="${discussion.name}">
    <c:if test="${pageContext.request.getRemoteUser() ne null && pageContext.request.getRemoteUser() ne ''}">
        <form id="votingForm" method="POST" action="${pageContext.request.contextPath}/Review">
            <label for="green" class="btn">
                <i class="fa fa-thumbs-up fa-lg" aria-hidden="true"></i>
                <input id="green" name="review" type="radio" value="0" style="display: none;">
            </label>
            <label for="red" class="btn">
                <i class="fa fa-thumbs-down fa-lg" aria-hidden="true"></i>
                <input id="red" name="review" type="radio" value="-1" style="display: none;">
            </label>
            <input type="hidden" value="${discussion.id}" name="postId">
            <input type="submit" class="btn btn-primary" style="display: none" value="Submit">
            <c:forEach var="user" items="${users}">
                <c:if test="${pageContext.request.getRemoteUser() eq user.username}">
                    <input type="hidden" name="userId" value="${user.id}">
                </c:if>
            </c:forEach>
        </form>
    </c:if>
    <c:if test="${message != null}">
        <div class="alert alert-warning" role="alert">
                ${message}
        </div>
    </c:if>
    <c:if test="${discussion.publicOpinion ne null && discussion.publicOpinion ne ''}">
        Public Opinion: ${discussion.publicOpinion}
    </c:if>
    <div class="row g-3">
        <div class="col-md-5">
            Pattern: <a class="link-unstyled" href="${pageContext.request.contextPath}/ShowPattern?id=${pattern.id}">${pattern.name}</a>
        </div>
    </div>
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
            Pattern author: ${pattern.author}
        </div>
    </div>
    <div class="row g-3">
        <div class="col-md-5">
            Discussion creator: ${discussion.author}
        </div>
    </div>
    <br><br>
    <h2>Description</h2>
    <hr>
    <div class="container d-flex justify-content-center align-items-center">
        <pre><p>${discussion.description}</p></pre>
    </div>

    <div class="row justify-content-center">
        <c:forEach items="${discussion.photos}" var="photo">
            <div class="col-md-5 mb-3">
                <img src="${pageContext.request.contextPath}/DiscussionPhoto?id=${photo.id}" class="img-fluid" alt="Discussion Photo">
            </div>
        </c:forEach>
    </div>


    <c:if test="${discussion.code ne '' && discussion.code ne null}">
        <h2>Code example</h2>
        <hr>
        <div class="container d-flex justify-content-center align-items-center">
            <div class="row">
                <div class="col">
                    <pre><code class="language-html">${discussion.code}</code></pre>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${(pageContext.request.getRemoteUser() ne null && pageContext.request.getRemoteUser() ne '') || comments ne null}">
        <h2>Comments</h2>
        <hr>
    </c:if>
    <c:if test="${comments ne null}">
        <c:forEach items="${comments}" var="comment">
            <div class="comment" style="margin-left:${comment.indentLevel}">
                    <span class="comment-author">${comment.author}</span>
                    <span class="comment-timestamp">${comment.timestamp}</span>
                    <p>${comment.content}</p>
                <c:if test="${pageContext.request.getRemoteUser() ne null && pageContext.request.getRemoteUser() ne ''}">
                    <div class="reply-button">
                        <button class="btn btn-primary" onclick="toggleReplyForm(event)">Reply</button>
                    </div>
                    <div style="display: none;">
                        <form action="${pageContext.request.contextPath}/CommentService" method="POST">
                            <c:forEach var="user" items="${users}">
                                <c:if test="${pageContext.request.getRemoteUser() eq user.username}">
                                    <input type="hidden" name="owner" value="${user.id}">
                                </c:if>
                            </c:forEach>
                            <input type="hidden" value="${discussion.id}" name="discussionId">
                            <input type="hidden" name="parentComment" value="${comment.id}">
                            <textarea name="content" placeholder="Your reply" style="width: 100%;"></textarea>
                            <button class="btn btn-primary" style="float:right" type="submit">Submit Reply</button>
                        </form>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </c:if>
    <br>
    <c:if test="${pageContext.request.getRemoteUser() ne null && pageContext.request.getRemoteUser() ne ''}">
        <div class="add-comment">
            <form id="commentForm" action="${pageContext.request.contextPath}/CommentService" method="POST">
                <input type="hidden" value="${discussion.id}" name="discussionId">
                <c:forEach var="user" items="${users}">
                    <c:if test="${pageContext.request.getRemoteUser() eq user.username}">
                        <input type="hidden" name="owner" value="${user.id}">
                    </c:if>
                </c:forEach>
                <textarea name="content" placeholder="Add your comment" style="width: 100%;"></textarea>
                <button class="btn btn-primary" type="submit" style="float:right">Submit</button>
            </form>
        </div>
    </c:if>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            var submitButton = document.querySelector('input[type="submit"]');
            var thumbsUpIcon = document.querySelector('.fa-thumbs-up');
            var thumbsDownIcon = document.querySelector('.fa-thumbs-down');
            var greenRadio = document.getElementById('green');
            var redRadio = document.getElementById('red');
            submitButton.style.display = 'none';

            greenRadio.addEventListener('change', function() {
                if (this.checked) {
                    thumbsUpIcon.classList.add('green');
                    thumbsDownIcon.classList.remove('red');
                    submitButton.style.display = 'block';
                }
            });

            redRadio.addEventListener('change', function() {
                if (this.checked) {
                    thumbsDownIcon.classList.add('red');
                    thumbsUpIcon.classList.remove('green');
                    submitButton.style.display = 'block';
                }
            });
        });
    </script>
    <script>
        function toggleReplyForm(commentId) {
            const button = event.target;
            const replyForm = button.parentNode.nextElementSibling;
            if (replyForm) {
                if (replyForm.style.display === 'none') {
                    replyForm.style.display = 'block';
                } else {
                    replyForm.style.display = 'none';
                }
            }
        }
    </script>
</t:pageTemplate>
