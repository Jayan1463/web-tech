<%@ page import="java.util.List" %>
<%@ page import="com.grocery.web.CartItem" %>

<!DOCTYPE html>
<html>

<head>

    <title>Your Cart - GroceryHub</title>

    <link rel="stylesheet"
          href="style.css?v=4">

</head>

<body>

<header class="navbar">

    <div class="logo">
        🛒 GroceryHub
    </div>

    <nav>

        <a href="index.jsp">
            Home
        </a>

        <a href="products">
            Products
        </a>

        <a href="cart.jsp">
            Cart
        </a>

    </nav>

</header>


<main class="cart-container">

<h1>Your Shopping Cart</h1>

<%
    List<CartItem> cart =
        (List<CartItem>)
        session.getAttribute("cart");

    double total = 0;

    if (cart == null ||
        cart.isEmpty()) {
%>

    <div class="empty">

        <h2>Your cart is empty</h2>

        <a href="products"
           class="btn">
            Continue Shopping
        </a>

    </div>

<%
    } else {
%>

<table class="cart-table">

<thead>

<tr>

    <th>Product</th>
    <th>Price</th>
    <th>Quantity</th>
    <th>Subtotal</th>

</tr>

</thead>

<tbody>

<%
        for (CartItem item : cart) {

            double subtotal =
                    item.getSubtotal();

            total += subtotal;
%>

<tr>

    <td>
        <%= item.getProduct().getName() %>
    </td>

    <td>
        ₹<%= item.getProduct().getPrice() %>
    </td>

    <td>
        <%= item.getQuantity() %>
    </td>

    <td>
        ₹<%= String.format(
                "%.2f",
                subtotal
            ) %>
    </td>

</tr>

<%
        }
%>

</tbody>

</table>


<div class="cart-total">

    <h2>
        Total:
        ₹<%= String.format(
                "%.2f",
                total
            ) %>
    </h2>

    <a href="checkout.jsp"
       class="btn">
        Proceed to Checkout
    </a>

</div>

<%
    }
%>

</main>

</body>
</html>
