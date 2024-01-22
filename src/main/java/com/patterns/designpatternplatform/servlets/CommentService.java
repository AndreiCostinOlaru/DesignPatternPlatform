package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.CommentDto;
import com.patterns.designpatternplatform.ejb.CommentBean;
import com.patterns.designpatternplatform.ejb.UserBean;
import com.patterns.designpatternplatform.entities.User;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@WebServlet(name = "CommentService", value = "/CommentService")
public class CommentService extends HttpServlet {

    @Inject
    CommentBean commentBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve parameters from the form
        long discussionId = Long.parseLong(request.getParameter("discussionId"));
        String content = request.getParameter("content");
        long owner = Long.parseLong(request.getParameter("owner"));
        Date timestamp = new Date();
        String parentCommentString = request.getParameter("parentComment");
        Long parentComment = null;

        // check if a parent comment ID is provided
        if (parentCommentString != null && !parentCommentString.isEmpty()) {
            parentComment = Long.parseLong(parentCommentString);
        }
        commentBean.createComment(discussionId, content, owner, timestamp, parentComment);//create a new comment using the CommentBean
        response.sendRedirect(request.getContextPath() + "/ShowDiscussion?id=" + discussionId); //redirect to the ShowDiscussion page after successfully creating the comment

    }

}
