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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String name =
            request.getParameter("name");

        String email =
            request.getParameter("email");

        String password =
            request.getParameter("password");

        String confirmPassword =
            request.getParameter("confirmPassword");


        if (name == null ||
            email == null ||
            password == null ||
            confirmPassword == null ||
            name.trim().isEmpty() ||
            email.trim().isEmpty() ||
            password.trim().isEmpty()) {

            request.setAttribute(
                "error",
                "All fields are required."
            );

            request.getRequestDispatcher(
                "register.jsp"
            ).forward(
                request,
                response
            );

            return;
        }


        if (name.trim().length() < 2) {

            forwardWithError(
                request,
                response,
                "Name must contain at least 2 characters."
            );

            return;
        }


        if (!email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {

            forwardWithError(
                request,
                response,
                "Enter a valid email address."
            );

            return;
        }


        if (password.length() < 6) {

            request.setAttribute(
                "error",
                "Password must contain at least 6 characters."
            );

            request.getRequestDispatcher(
                "register.jsp"
            ).forward(
                request,
                response
            );

            return;
        }


        if (!password.equals(confirmPassword)) {

            forwardWithError(
                request,
                response,
                "Passwords do not match."
            );

            return;
        }


        try (
            Connection connection =
                DBConnection.getConnection()
        ) {

            String checkSql =
                "SELECT id FROM users WHERE email = ?";


            try (
                PreparedStatement check =
                    connection.prepareStatement(checkSql)
            ) {

                check.setString(
                    1,
                    email.trim()
                );

                try (
                    ResultSet result =
                        check.executeQuery()
                ) {

                    if (result.next()) {

                        request.setAttribute(
                            "error",
                            "This email is already registered."
                        );

                        request.getRequestDispatcher(
                            "register.jsp"
                        ).forward(
                            request,
                            response
                        );

                        return;
                    }
                }
            }


            String insertSql =
                "INSERT INTO users " +
                "(name, email, password) " +
                "VALUES (?, ?, ?)";


            try (
                PreparedStatement statement =
                    connection.prepareStatement(insertSql)
            ) {

                statement.setString(
                    1,
                    name.trim()
                );

                statement.setString(
                    2,
                    email.trim()
                );

                statement.setString(
                    3,
                    password
                );

                statement.executeUpdate();
            }


            response.sendRedirect(
                request.getContextPath()
                + "/login.jsp?registered=true"
            );


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "error",
                "Registration could not be completed. Please try again."
            );

            request.getRequestDispatcher(
                "register.jsp"
            ).forward(
                request,
                response
            );
        }
    }


    private void forwardWithError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws ServletException, IOException {

        request.setAttribute(
            "error",
            message
        );

        request.getRequestDispatcher(
            "register.jsp"
        ).forward(
            request,
            response
        );
    }


    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
            request.getContextPath()
            + "/register.jsp"
        );
    }
}
