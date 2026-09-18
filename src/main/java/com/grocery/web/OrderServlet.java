package com.grocery.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");

            return;
        }

        Object cartAttribute =
            session.getAttribute("cart");

        List<CartItem> cart =
            new java.util.ArrayList<CartItem>();

        if (cartAttribute instanceof List<?>) {

            for (Object cartEntry :
                    (List<?>) cartAttribute) {

                if (cartEntry instanceof CartItem) {
                    cart.add((CartItem) cartEntry);
                }
            }
        }

        if (cart == null ||
            cart.isEmpty()) {

            response.sendRedirect("cart.jsp");

            return;
        }

        int userId =
            (Integer)
            session.getAttribute("userId");

        String address =
            request.getParameter("address");

        String paymentMethod =
            request.getParameter("paymentMethod");

        Connection con = null;

        try {

            con =
                DBConnection.getConnection();

            con.setAutoCommit(false);

            double total = 0;

            for (CartItem item : cart) {

                total +=
                    item.getSubtotal();
            }

            PreparedStatement orderPs =
                con.prepareStatement(
                    "INSERT INTO orders " +
                    "(user_id,total,status,address,payment_method) " +
                    "VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
                );

            orderPs.setInt(
                1,
                userId
            );

            orderPs.setDouble(
                2,
                total
            );

            orderPs.setString(
                3,
                "PLACED"
            );

            orderPs.setString(
                4,
                address
            );

            orderPs.setString(
                5,
                paymentMethod
            );

            orderPs.executeUpdate();

            ResultSet keys =
                orderPs.getGeneratedKeys();

            int orderId = 0;

            if (keys.next()) {
                orderId =
                    keys.getInt(1);
            }

            PreparedStatement itemPs =
                con.prepareStatement(
                    "INSERT INTO order_items " +
                    "(order_id,product_id,quantity,price) " +
                    "VALUES (?,?,?,?)"
                );

            PreparedStatement stockPs =
                con.prepareStatement(
                    "UPDATE products " +
                    "SET stock=stock-? " +
                    "WHERE id=? AND stock>=?"
                );

            for (CartItem item : cart) {

                itemPs.setInt(
                    1,
                    orderId
                );

                itemPs.setInt(
                    2,
                    item.getProduct().getId()
                );

                itemPs.setInt(
                    3,
                    item.getQuantity()
                );

                itemPs.setDouble(
                    4,
                    item.getProduct().getPrice()
                );

                itemPs.executeUpdate();


                stockPs.setInt(
                    1,
                    item.getQuantity()
                );

                stockPs.setInt(
                    2,
                    item.getProduct().getId()
                );

                stockPs.setInt(
                    3,
                    item.getQuantity()
                );

                stockPs.executeUpdate();
            }

            con.commit();

            session.removeAttribute("cart");

            session.setAttribute(
                "lastOrderId",
                orderId
            );

            session.setAttribute(
                "lastOrderTotal",
                total
            );

            response.sendRedirect(
                "confirmation.jsp"
            );

        } catch (Exception e) {

            try {

                if (con != null) {
                    con.rollback();
                }

            } catch (Exception ignored) {
            }

            throw new ServletException(e);

        } finally {

            try {

                if (con != null) {
                    con.close();
                }

            } catch (Exception ignored) {
            }
        }
    }
}
