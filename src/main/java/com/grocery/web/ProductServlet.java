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

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String category =
            request.getParameter("category");

        String search =
            request.getParameter("search");


        List<Product> products =
            new ArrayList<Product>();


        StringBuilder sql =
            new StringBuilder(
                "SELECT id, name, category, " +
                "description, price, stock, image " +
                "FROM products WHERE 1=1"
            );


        List<String> parameters =
            new ArrayList<String>();


        if (category != null &&
            !category.trim().isEmpty()) {

            sql.append(
                " AND category = ?"
            );

            parameters.add(
                category.trim()
            );
        }


        if (search != null &&
            !search.trim().isEmpty()) {

            sql.append(
                " AND (name LIKE ? " +
                "OR description LIKE ?)"
            );

            String searchValue =
                "%" + search.trim() + "%";

            parameters.add(searchValue);

            parameters.add(searchValue);
        }


        sql.append(
            " ORDER BY name ASC"
        );


        try (
            Connection connection =
                DBConnection.getConnection();

            PreparedStatement statement =
                connection.prepareStatement(
                    sql.toString()
                )
        ) {

            for (
                int i = 0;
                i < parameters.size();
                i++
            ) {

                statement.setString(
                    i + 1,
                    parameters.get(i)
                );
            }


            try (
                ResultSet result =
                    statement.executeQuery()
            ) {

                while (result.next()) {

                    Product product =
                        new Product();


                    product.setId(
                        result.getInt("id")
                    );

                    product.setName(
                        result.getString("name")
                    );

                    product.setCategory(
                        result.getString("category")
                    );

                    product.setDescription(
                        result.getString("description")
                    );

                    product.setPrice(
                        result.getDouble("price")
                    );

                    product.setStock(
                        result.getInt("stock")
                    );

                    product.setImage(
                        result.getString("image")
                    );


                    products.add(product);
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                "error",
                "Database Error: "
                + e.getMessage()
            );
        }


        request.setAttribute(
            "products",
            products
        );


        request.getRequestDispatcher(
            "products.jsp"
        ).forward(
            request,
            response
        );
    }
}