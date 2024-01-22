package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.abstractClasses.ReviewTemplate;
import com.patterns.designpatternplatform.common.LikeDislikeReview;
import com.patterns.designpatternplatform.common.StarReview;
import com.patterns.designpatternplatform.ejb.CommentBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;


@WebServlet(name = "Review", value = "/Review")
public class Review extends HttpServlet {

    @Inject
    LikeDislikeReview likeDislikeReview;

    @Inject
    StarReview starReview;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //retrieve review, user ID, and post ID from the form
        int review= Integer.parseInt(request.getParameter("review"));
        long userId= Long.parseLong(request.getParameter("userId"));
        long postId= Long.parseLong(request.getParameter("postId"));
        if(review==0 || review==-1){ //check if the review is a Like/Dislike (0 or -1)

            likeDislikeReview.reviewPost(review,userId,postId);//review the post using LikeDislikeReview

            //redirect to the appropriate page based on whether the review was already submitted
            if(likeDislikeReview.isAlreadySubmitted()){
                response.sendRedirect(request.getContextPath() + "/ShowDiscussion?id=" + postId+"&msg=submitted");
            }
            else {
                response.sendRedirect(request.getContextPath() + "/ShowDiscussion?id=" + postId);
            }
        }
        else{
            //review the post using StarReview
            starReview.reviewPost(review,userId,postId);
            if(starReview.isAlreadySubmitted()){//redirect to the appropriate page based on whether the review was already submitted
                response.sendRedirect(request.getContextPath() + "/ShowPattern?id=" + postId+"&msg=submitted");
            }
            else {
                response.sendRedirect(request.getContextPath() + "/ShowPattern?id=" + postId);
            }
        }
    }

}
