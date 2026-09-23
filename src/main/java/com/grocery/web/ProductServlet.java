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

    private static final Product[] FALLBACK_PRODUCTS = {
        product(
            1,
            "Fresh Tomato",
            "Vegetables",
            "Fresh farm tomatoes",
            40.00,
            100,
            "tomato.jpg"
        ),
        product(
            2,
            "Potato",
            "Vegetables",
            "Fresh potatoes",
            35.00,
            150,
            "potato.jpg"
        ),
        product(
            3,
            "Red Apple",
            "Fruits",
            "Fresh Kashmiri apples",
            180.00,
            50,
            "apple.jpg"
        ),
        product(
            4,
            "Banana",
            "Fruits",
            "Fresh bananas",
            60.00,
            100,
            "banana.jpg"
        ),
        product(
            5,
            "Fresh Milk",
            "Dairy",
            "Full cream milk",
            60.00,
            80,
            "milk.jpg"
        ),
        product(
            6,
            "Curd",
            "Dairy",
            "Fresh natural curd",
            50.00,
            70,
            "curd.jpg"
        ),
        product(
            7,
            "Basmati Rice",
            "Staples",
            "Premium basmati rice",
            120.00,
            100,
            "rice.jpg"
        ),
        product(
            8,
            "Wheat Flour",
            "Staples",
            "Whole wheat flour",
            55.00,
            100,
            "flour.jpg"
        ),
        product(
            9,
            "Orange Juice",
            "Beverages",
            "Fresh orange juice",
            90.00,
            60,
            "juice.jpg"
        ),
        product(
            10,
            "Potato Chips",
            "Snacks",
            "Crispy potato chips",
            30.00,
            120,
            "chips.jpg"
        )
    };


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

        if (products.isEmpty()) {

            products =
                getFallbackProducts(
                    category,
                    search
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


    private static List<Product> getFallbackProducts(
            String category,
            String search) {

        List<Product> products =
            new ArrayList<Product>();

        String normalizedCategory =
            normalize(category);

        String normalizedSearch =
            normalize(search);

        for (Product product : FALLBACK_PRODUCTS) {

            if (!normalizedCategory.isEmpty()
                    && !normalize(product.getCategory()).equals(
                        normalizedCategory
                    )) {

                continue;
            }

            if (!normalizedSearch.isEmpty()
                    && !normalize(product.getName()).contains(
                        normalizedSearch
                    )
                    && !normalize(product.getDescription()).contains(
                        normalizedSearch
                    )) {

                continue;
            }

            products.add(product);
        }

        return products;
    }


    private static String normalize(String value) {

        if (value == null) {

            return "";
        }

        return value.trim().toLowerCase();
    }


    private static Product product(
            int id,
            String name,
            String category,
            String description,
            double price,
            int stock,
            String image) {

        Product product =
            new Product();

        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setImage(image);

        return product;
    }
}
