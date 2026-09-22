package com.grocery.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/visit")
public class VisitCounterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int count = 0;

        Cookie[] cookies =
                request.getCookies();

        if (cookies != null) {

            for (Cookie cookie : cookies) {

                if ("visitCount".equals(
                        cookie.getName())) {

                    count =
                        Integer.parseInt(
                            cookie.getValue()
                        );
                }
            }
        }

        count++;

        Cookie visitCookie =
            new Cookie(
                "visitCount",
                String.valueOf(count)
            );

        visitCookie.setPath(
                request.getContextPath()
        );

        visitCookie.setMaxAge(
                60 * 60 * 24 * 30
        );

        response.addCookie(
                visitCookie
        );

        request.setAttribute(
                "visitCount",
                count
        );

        request.getRequestDispatcher(
                "index.jsp"
        ).forward(request, response);
    }
}
