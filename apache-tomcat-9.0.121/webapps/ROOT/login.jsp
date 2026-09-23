<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Login - GroceryHub</title>

    <link rel="stylesheet"
          href="style.css?v=4">

</head>

<body>

<div class="auth-container">

    <div class="auth-card">

        <h1>🛒 GroceryHub</h1>

        <h2>Login</h2>

        <%
            String error =
                (String)
                request.getAttribute("error");

            String registered =
                request.getParameter("registered");

            if ("true".equals(registered)) {
        %>

            <div class="success-message">
                Registration successful.
                You can now login.
            </div>

        <%
            }

            if (error != null) {
        %>

            <div class="error-message">
                <%= error %>
            </div>

        <%
            }
        %>


        <form action="login"
              method="post">

            <label>Email</label>

            <input
                type="email"
                name="email"
                placeholder="Enter your email"
                required>


            <label>Password</label>

            <input
                type="password"
                name="password"
                placeholder="Enter your password"
                required>


            <button type="submit">

                Login

            </button>

        </form>


        <div class="demo-login">

            <p>
                <strong>Demo Account</strong>
            </p>

            <p>
                Email:
                user@groceryhub.com
            </p>

            <p>
                Password:
                user123
            </p>

        </div>


        <p>

            Don't have an account?

            <a href="register.jsp">
                Register
            </a>

        </p>

    </div>

</div>

</body>
</html>
