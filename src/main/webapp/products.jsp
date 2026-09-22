<%@ page import="java.util.List" %>
<%@ page import="com.grocery.web.Product" %>

<!DOCTYPE html>
<html>

<head>

    <title>Products - GroceryHub</title>

    <link rel="stylesheet"
          href="style.css?v=4">

    <script src="app.js" defer></script>

</head>

<body>

<header class="navbar">

    <div class="logo">
        🛒 GroceryHub
    </div>

    <nav>
        <a href="index.jsp">Home</a>
        <a href="products">Products</a>
        <a href="cart.jsp">Cart</a>
        <a href="feedback.jsp">Feedback</a>
    </nav>

</header>


<section class="products-header">

    <h1>Our Products</h1>

    <form action="products"
          method="get"
          class="search-form">

        <input
            type="text"
            name="search"
            placeholder="Search groceries...">

        <button type="submit">
            Search
        </button>

    </form>

</section>


<div class="category-filter">

    <a href="products">All</a>

    <a href="products?category=Vegetables">
        Vegetables
    </a>

    <a href="products?category=Fruits">
        Fruits
    </a>

    <a href="products?category=Dairy">
        Dairy
    </a>

    <a href="products?category=Staples">
        Staples
    </a>

    <a href="products?category=Beverages">
        Beverages
    </a>

</div>


<main class="product-grid">

<%
    List<Product> products =
        (List<Product>)
        request.getAttribute("products");

    if (products != null &&
        !products.isEmpty()) {

        for (Product product : products) {
%>

<div class="product-card">

    <div class="product-image product-photo
                product-photo--<%= product.getCategory() %>"
         role="img"
         aria-label="<%= product.getName() %>"></div>

    <h3>
        <%= product.getName() %>
    </h3>

    <p class="category">
        <%= product.getCategory() %>
    </p>

    <p>
        <%= product.getDescription() %>
    </p>

    <div class="product-bottom">

        <strong>
            ₹<%= String.format(
                    "%.2f",
                    product.getPrice()
                ) %>
        </strong>

        <span>
            Stock:
            <%= product.getStock() %>
        </span>

    </div>

    <form action="cart"
          method="post">

        <input
            type="hidden"
            name="productId"
            value="<%= product.getId() %>">

        <input
            type="number"
            name="quantity"
            min="1"
            max="<%= product.getStock() %>"
            value="1"
            required>

        <button
            type="submit"
            class="btn">

            Add to Cart

        </button>

    </form>

</div>

<%
        }

    } else {
%>

    <div class="empty">

        <h2>No products found</h2>

        <p>
            Try another search or category.
        </p>

    </div>

<%
    }
%>

</main>

</body>
</html>
