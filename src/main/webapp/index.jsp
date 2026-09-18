<%@ page import="javax.servlet.http.Cookie" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>GroceryHub - Fresh Groceries</title>

    <link rel="stylesheet" href="style.css?v=3">
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
        <a href="feedback.jsp">Feedback</a>
        <a href="cart.jsp">Cart</a>

        <%
            if (session.getAttribute("userName") != null) {
        %>

            <a href="profile.jsp">
                <%= session.getAttribute("userName") %>
            </a>

            <a href="logout">Logout</a>

        <%
            } else {
        %>

            <a href="login.jsp">Login</a>
            <a href="register.jsp">Register</a>

        <%
            }
        %>
    </nav>

</header>


<section class="hero">

    <div class="hero-content">

        <p class="eyebrow">
            FRESH • FAST • AFFORDABLE
        </p>

        <h1>
            Fresh groceries,
            <span>delivered to you.</span>
        </h1>

        <p>
            Shop vegetables, fruits, dairy products,
            beverages and everyday essentials.
        </p>

        <a href="products" class="btn">
            Shop Now
        </a>

    </div>

</section>


<section class="categories">

    <h2>Shop by Category</h2>

    <div class="category-grid">

        <a href="products?category=Vegetables"
           class="category-card">
            <span class="category-photo category-photo--Vegetables"
                  aria-hidden="true"></span>
            <h3>Vegetables</h3>
        </a>

        <a href="products?category=Fruits"
           class="category-card">
            <span class="category-photo category-photo--Fruits"
                  aria-hidden="true"></span>
            <h3>Fruits</h3>
        </a>

        <a href="products?category=Dairy"
           class="category-card">
            <span class="category-photo category-photo--Dairy"
                  aria-hidden="true"></span>
            <h3>Dairy</h3>
        </a>

        <a href="products?category=Staples"
           class="category-card">
            <span class="category-photo category-photo--Staples"
                  aria-hidden="true"></span>
            <h3>Staples</h3>
        </a>

        <a href="products?category=Beverages"
           class="category-card">
            <span class="category-photo category-photo--Beverages"
                  aria-hidden="true"></span>
            <h3>Beverages</h3>
        </a>

        <a href="products?category=Snacks"
           class="category-card">
            <span class="category-photo category-photo--Snacks"
                  aria-hidden="true"></span>
            <h3>Snacks</h3>
        </a>

    </div>

</section>


<section class="features">

    <div>
        <h3>🚚 Fast Delivery</h3>
        <p>Quick doorstep delivery.</p>
    </div>

    <div>
        <h3>🥬 Fresh Products</h3>
        <p>Quality groceries every day.</p>
    </div>

    <div>
        <h3>🔒 Secure Shopping</h3>
        <p>Safe and simple checkout.</p>
    </div>

</section>


<section class="visit-section">

<%
    Cookie[] cookies = request.getCookies();

    int visits = 0;

    if (cookies != null) {

        for (Cookie cookie : cookies) {

            if ("visitCount".equals(cookie.getName())) {

                try {
                    visits = Integer.parseInt(cookie.getValue());
                } catch (Exception ignored) {
                }

            }
        }
    }
%>

    <p>
        You have visited this page
        <strong><%= visits %></strong>
        times.
    </p>

    <a href="visit" class="small-btn">
        Count This Visit
    </a>

    <a href="deleteCookie"
       class="small-btn secondary">
        Reset Visit Counter
    </a>

</section>


<footer>

    <p>
        © 2026 GroceryHub
    </p>

</footer>

</body>
</html>
