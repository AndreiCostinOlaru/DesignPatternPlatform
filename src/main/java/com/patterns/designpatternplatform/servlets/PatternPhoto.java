package com.patterns.designpatternplatform.servlets;

import com.patterns.designpatternplatform.common.PatternPhotoDto;
import com.patterns.designpatternplatform.ejb.PatternBean;
import com.patterns.designpatternplatform.ejb.PhotoBean;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "PatternPhoto", value = "/PatternPhoto")
public class PatternPhoto extends HttpServlet {

    @Inject
    PhotoBean photoBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long patternPhotoId=Long.parseLong(request.getParameter("id"));//retrieve pattern photo ID from the request parameters
        PatternPhotoDto photo=photoBean.findPatternPhotosById(patternPhotoId);//find the pattern photo using the PhotoBean
        // check if the photo exists and set the response content type and length, then write the photo content to the response output stream
        if(photo !=null){
                response.setContentType(photo.getFileType());
                response.setContentLength(photo.getFileContent().length);
                response.getOutputStream().write(photo.getFileContent());
            }
        else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // if the photo is not found, send a 404 (Not Found) error
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
