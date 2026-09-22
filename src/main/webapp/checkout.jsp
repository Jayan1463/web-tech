<%@ page import="java.util.List" %>
<%@ page import="com.grocery.web.CartItem" %>

<!DOCTYPE html>
<html>

<head>

    <title>Checkout - GroceryHub</title>

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


<main class="checkout">

<h1>Checkout</h1>

<%
    List<CartItem> cart =
        (List<CartItem>)
        session.getAttribute("cart");

    double total = 0;

    if (cart != null) {

        for (CartItem item : cart) {
            total += item.getSubtotal();
        }
    }
%>

<div class="checkout-card">

    <h2>
        Order Total:
        ₹<%= String.format(
                "%.2f",
                total
            ) %>
    </h2>

    <form action="order"
          method="post">

        <label>Delivery Address</label>

        <textarea
            name="address"
            rows="5"
            required
            placeholder="Enter your delivery address">
        </textarea>

        <label>Payment Method</label>

        <select name="paymentMethod">

            <option value="COD">
                Cash on Delivery
            </option>

            <option value="UPI">
                UPI
            </option>

            <option value="CARD">
                Card
            </option>

        </select>

        <button
            type="submit"
            class="btn">

            Place Order

        </button>

    </form>

</div>

</main>

</body>
</html>
