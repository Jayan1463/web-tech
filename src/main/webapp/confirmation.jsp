<!DOCTYPE html>
<html>

<head>

    <title>Order Confirmation</title>

    <link rel="stylesheet"
          href="style.css?v=4">

</head>

<body>

<div class="confirmation">

    <div class="confirmation-card">

        <div class="success-icon">
            ✓
        </div>

        <h1>
            Order Placed Successfully!
        </h1>

        <p>
            Thank you for shopping with GroceryHub.
        </p>

        <p>
            Order ID:
            <strong>
                #<%= session.getAttribute(
                        "lastOrderId"
                    ) %>
            </strong>
        </p>

        <p>
            Total:
            <strong>
                ₹<%= session.getAttribute(
                        "lastOrderTotal"
                    ) %>
            </strong>
        </p>

        <a href="products"
           class="btn">
            Continue Shopping
        </a>

    </div>

</div>

</body>
</html>
