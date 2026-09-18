package com.grocery.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/sessionData")
public class SessionDataServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name =
                request.getParameter("name");

        String email =
                request.getParameter("email");

        HttpSession session =
                request.getSession();

        session.setAttribute(
                "formName",
                name
        );

        session.setAttribute(
                "formEmail",
                email
        );

        response.sendRedirect(
                "confirmation.jsp"
        );
    }
}
