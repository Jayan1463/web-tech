<%
    if (session.getAttribute("userName") == null) {

        response.sendRedirect("login.jsp");

        return;
    }
%>

<!DOCTYPE html>
<html>

<head>

<title>Profile - GroceryHub</title>

<link rel="stylesheet"
      href="style.css">

</head>

<body>

<header class="navbar">

    <div class="logo">
        🛒 GroceryHub
    </div>

    <nav>

        <a href="index.jsp">Home</a>

        <a href="products">
            Products
        </a>

        <a href="cart.jsp">
            Cart
        </a>

        <a href="logout">
            Logout
        </a>

    </nav>

</header>


<main class="profile">

    <h1>My Profile</h1>

    <div class="profile-card">

        <h2>
            <%= session.getAttribute(
                    "userName"
                ) %>
        </h2>

        <p>
            <strong>Email:</strong>
            <%= session.getAttribute(
                    "userEmail"
                ) %>
        </p>

    </div>

</main>

</body>
</html>
