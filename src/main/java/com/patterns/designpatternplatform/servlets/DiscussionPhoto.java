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
        //retrieve discussion photo ID from the request parameters
        Long discussionPhotoId=Long.parseLong(request.getParameter("id"));
        // Find the discussion photo using the PhotoBean
        DiscussionPhotoDto photo=photoBean.findDiscussionPhotosById(discussionPhotoId);
        //check if the photo exists
        if(photo !=null){
                //set response content type and length, then write the photo content to the response output stream
                response.setContentType(photo.getFileType());
                response.setContentLength(photo.getFileContent().length);
                response.getOutputStream().write(photo.getFileContent());
            }
        else{
            // if the photo is not found, send a 404 (Not Found) error
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
