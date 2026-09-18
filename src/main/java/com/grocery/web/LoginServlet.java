package com.grocery.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email =
                request.getParameter("email");

        String password =
                request.getParameter("password");


        if (email == null ||
            password == null ||
            email.trim().isEmpty() ||
            password.trim().isEmpty()) {

            request.setAttribute(
                "error",
                "Email and password are required."
            );

            request.getRequestDispatcher(
                "login.jsp"
            ).forward(
                request,
                response
            );

            return;
        }


        String sql =
            "SELECT id, name, email " +
            "FROM users " +
            "WHERE email = ? " +
            "AND password = ?";


        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(
                1,
                email.trim()
            );

            statement.setString(
                2,
                password
            );


            try (
                ResultSet result =
                    statement.executeQuery()
            ) {

                if (result.next()) {

                    HttpSession session =
                        request.getSession(true);


                    session.setAttribute(
                        "userId",
                        result.getInt("id")
                    );

                    session.setAttribute(
                        "userName",
                        result.getString("name")
                    );

                    session.setAttribute(
                        "userEmail",
                        result.getString("email")
                    );


                    response.sendRedirect(
                        request.getContextPath()
                        + "/products"
                    );

                } else {

                    request.setAttribute(
                        "error",
                        "Invalid email or password."
                    );

                    request.getRequestDispatcher(
                        "login.jsp"
                    ).forward(
                        request,
                        response
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "error",
                "Database Error: "
                + e.getMessage()
            );

            request.getRequestDispatcher(
                "login.jsp"
            ).forward(
                request,
                response
            );
        }
    }


    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
            request.getContextPath()
            + "/login.jsp"
        );
    }
}