<!DOCTYPE html>
<html>

<head>

    <title>Feedback - GroceryHub</title>

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


<main class="feedback-container">

    <div class="feedback-card">

        <h1>Customer Feedback</h1>

        <p>
            Tell us about your GroceryHub experience.
        </p>

        <img class="feedback-image"
             src="images/category-strip.png"
             alt="Fresh grocery products">

        <form action="feedback"
              method="post">

            <label>Name</label>

            <input
                type="text"
                name="name"
                required>

            <label>Email</label>

            <input
                type="email"
                name="email"
                required>

            <label>Rating</label>

            <select name="rating"
                    required>

                <option value="">
                    Select Rating
                </option>

                <option value="1">
                    ⭐ 1
                </option>

                <option value="2">
                    ⭐⭐ 2
                </option>

                <option value="3">
                    ⭐⭐⭐ 3
                </option>

                <option value="4">
                    ⭐⭐⭐⭐ 4
                </option>

                <option value="5">
                    ⭐⭐⭐⭐⭐ 5
                </option>

            </select>

            <label>Category</label>

            <select name="category"
                    required>

                <option value="Products">
                    Products
                </option>

                <option value="Delivery">
                    Delivery
                </option>

                <option value="Website">
                    Website
                </option>

                <option value="Support">
                    Customer Support
                </option>

            </select>

            <label>Comment</label>

            <textarea
                name="comment"
                rows="6"
                required>
            </textarea>

            <button
                type="submit"
                class="btn">

                Submit Feedback

            </button>

        </form>

        <section class="xml-reports" aria-label="XML reports">
            <h2>XML Database Reports</h2>
            <a class="small-btn" href="xml-report?source=feedback3">DTD 3-Field Summary</a>
            <a class="small-btn" href="xml-report?source=feedback5">XSD 5-Field Summary</a>
            <a class="small-btn" href="xml-report?source=feedback5-high">XPath Ratings Above 3</a>
            <a class="small-btn" href="xml-report?source=products">Product XML Summary</a>
            <button
                type="button"
                class="small-btn secondary"
                onclick="if (window.history.length > 1) { window.history.back(); } else { window.location.href = 'feedback.jsp'; }">
                Go Back
            </button>
        </section>

    </div>

</main>

</body>
</html>
