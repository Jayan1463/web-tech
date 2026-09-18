package com.grocery.web;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/activeUsers")
public class ActiveUsersServlet
        extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static final Set<HttpSession>
        ACTIVE_SESSIONS =
        java.util.Collections.synchronizedSet(
            new java.util.HashSet<HttpSession>()
        );

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
            "activeUsers",
            ACTIVE_SESSIONS
        );

        request.getRequestDispatcher(
            "active-users.jsp"
        ).forward(
            request,
            response
        );
    }

    public static void addSession(
            HttpSession session) {

        ACTIVE_SESSIONS.add(session);
    }

    public static void removeSession(
            HttpSession session) {

        ACTIVE_SESSIONS.remove(session);
    }
}
