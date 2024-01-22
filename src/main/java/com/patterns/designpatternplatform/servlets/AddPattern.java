package com.patterns.designpatternplatform.servlets;
import com.patterns.designpatternplatform.common.PatternDto;
import com.patterns.designpatternplatform.ejb.PatternBean;
import com.patterns.designpatternplatform.ejb.PhotoBean;
import com.patterns.designpatternplatform.ejb.UserBean;
import com.patterns.designpatternplatform.entities.Pattern;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.patterns.designpatternplatform.common.UserDto;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@MultipartConfig
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_PATTERN"}))
@WebServlet(name = "AddPattern", value = "/AddPattern")
public class AddPattern extends HttpServlet {

    @Inject
    UserBean userBean;
    @Inject
    PhotoBean photoBean;

    @Inject
    PatternBean patternBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserDto> users=userBean.findAllUsers(); //retrieve all users and set them as an attribute for the JSP page
        request.setAttribute("users",users);
        request.getRequestDispatcher("/WEB-INF/pages/addpattern.jsp").forward(request,response);// forward the request to the addpattern.jsp page

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve parameters from the form
        String name=request.getParameter("patternName");
        String type=request.getParameter("patternType");
        String scope=request.getParameter("patternScope");
        String description=request.getParameter("description");
        String code=request.getParameter("code");
        long id=Long.parseLong(request.getParameter("owner_id"));

        Pattern createdPattern=patternBean.createPattern(name, type, scope, id,description,code);// create a pattern using the PatternBean

        // retrieve file parts from the request
        List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList());

        for (Part filePart : fileParts) {// process each file part and create a photo using the PhotoBean
            String fileName = filePart.getSubmittedFileName();
            String fileType = filePart.getContentType();
            long fileSize = filePart.getSize();
            byte[] fileContent = new byte[(int) fileSize];
            filePart.getInputStream().read(fileContent);
            photoBean.createPhoto(fileName,fileContent,fileType,createdPattern);
        }
        //redirect to the Patterns page after successful creation
        response.sendRedirect(request.getContextPath() + "/Patterns");
    }
}
