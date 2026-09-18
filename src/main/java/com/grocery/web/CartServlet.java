package com.grocery.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        int productId;

        int quantity;

        try {

            productId =
                    Integer.parseInt(
                        request.getParameter("productId")
                    );

            quantity =
                    Integer.parseInt(
                        request.getParameter("quantity")
                    );

        } catch (Exception e) {

            response.sendRedirect("products");

            return;
        }

        if (quantity <= 0) {

            response.sendRedirect("products");

            return;
        }

        try (Connection con =
                     DBConnection.getConnection()) {

            PreparedStatement ps =
                    con.prepareStatement(
                        "SELECT * FROM products WHERE id=?"
                    );

            ps.setInt(1, productId);

            ResultSet rs =
                    ps.executeQuery();

            if (!rs.next()) {

                response.sendRedirect("products");

                return;
            }

            Product product =
                    new Product();

            product.setId(
                    rs.getInt("id")
            );

            product.setName(
                    rs.getString("name")
            );

            product.setCategory(
                    rs.getString("category")
            );

            product.setDescription(
                    rs.getString("description")
            );

            product.setPrice(
                    rs.getDouble("price")
            );

            product.setStock(
                    rs.getInt("stock")
            );

            product.setImage(
                    rs.getString("image")
            );

            if (quantity > product.getStock()) {

                quantity =
                        product.getStock();
            }

            HttpSession session =
                    request.getSession();

            List<CartItem> cart =
                    new ArrayList<CartItem>();

            Object cartAttribute =
                    session.getAttribute("cart");

            if (cartAttribute instanceof List<?>) {

                for (Object cartEntry :
                        (List<?>) cartAttribute) {

                    if (cartEntry instanceof CartItem) {
                        cart.add((CartItem) cartEntry);
                    }
                }
            }

            boolean found = false;

            for (CartItem item : cart) {

                if (item.getProduct()
                       .getId() == productId) {

                    int newQuantity =
                            item.getQuantity()
                            + quantity;

                    if (newQuantity >
                        product.getStock()) {

                        newQuantity =
                            product.getStock();
                    }

                    item.setQuantity(
                            newQuantity
                    );

                    found = true;

                    break;
                }
            }

            if (!found) {

                cart.add(
                    new CartItem(
                        product,
                        quantity
                    )
                );
            }

            session.setAttribute(
                    "cart",
                    cart
            );

            response.sendRedirect(
                    "cart.jsp"
            );

        } catch (Exception e) {

            throw new ServletException(e);
        }
    }


    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("cart.jsp");
    }
}
