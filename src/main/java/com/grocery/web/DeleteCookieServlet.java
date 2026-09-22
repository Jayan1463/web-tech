package com.grocery.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteCookie")
public class DeleteCookieServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Cookie cookie =
                new Cookie("visitCount", "");

        cookie.setPath(
                request.getContextPath()
        );

        cookie.setMaxAge(0);

        response.addCookie(cookie);

        response.sendRedirect(
                "index.jsp"
        );
    }
}
