package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.DiscussionDto;
import com.patterns.designpatternplatform.common.DiscussionPhotoDto;
import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.ejb.PhotoBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "DiscussionPhoto", value = "/DiscussionPhoto")
public class DiscussionPhoto extends HttpServlet {

    @Inject
    PhotoBean photoBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long discussionPhotoId=Long.parseLong(request.getParameter("id"));
        DiscussionPhotoDto photo=photoBean.findDiscussionPhotosById(discussionPhotoId);
        if(photo !=null){
                response.setContentType(photo.getFileType());
                response.setContentLength(photo.getFileContent().length);
                response.getOutputStream().write(photo.getFileContent());
            }
        else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
