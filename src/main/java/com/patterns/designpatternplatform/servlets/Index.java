package com.patterns.designpatternplatform.servlets;
import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.ejb.PatternBean;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "Index", value = "/Index")
public class Index extends HttpServlet {

   @Inject
   PatternBean patternBean;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PatternDto> patterns=patternBean.findAllPatterns();
        request.setAttribute("patterns",patterns);
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
