<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="UTF-8">

    <title>Create Account - GroceryHub</title>

    <link rel="stylesheet"
          href="style.css?v=4">

</head>

<body>

<div class="auth-container">

    <div class="auth-card">

        <h1>🛒 GroceryHub</h1>

        <h2>Create Account</h2>

        <%
            String error = (String) request.getAttribute("error");
            if (error != null) {
        %>
            <p class="form-status form-status--error"><%= error %></p>
        <%
            }
        %>

        <form id="registration-form"
              action="register"
              method="post"
              data-validation-url="http://localhost:18082/validate-registration.php">

            <label>Name</label>

            <input
                type="text"
                name="name"
                id="name"
                required>

            <label>Email</label>

            <input
                type="email"
                name="email"
                id="email"
                required>

            <label>Password</label>

            <input
                type="password"
                name="password"
                id="password"
                minlength="6"
                required>

            <label>Confirm Password</label>

            <input
                type="password"
                name="confirmPassword"
                id="confirmPassword"
                required>

            <p id="registration-validation"
               class="form-status"
               role="status"
               aria-live="polite"></p>

            <button type="submit">
                Create Account
            </button>

        </form>

        <p>
            Already have an account?
            <a href="login.jsp">Login</a>
        </p>

    </div>

</div>

<script>

const registrationForm = document.getElementById("registration-form");
const validationMessage = document.getElementById("registration-validation");

function showValidationMessage(message, valid) {

    validationMessage.textContent = message;
    validationMessage.className = valid
        ? "form-status form-status--success"
        : "form-status form-status--error";
}

registrationForm.addEventListener("submit", async function (event) {

    event.preventDefault();

    if (!registrationForm.checkValidity()) {
        registrationForm.reportValidity();
        return;
    }

    const submitButton = registrationForm.querySelector("button[type='submit']");
    submitButton.disabled = true;
    showValidationMessage("Validating registration details...", true);

    try {
        const response = await fetch(registrationForm.dataset.validationUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
            },
            body: new URLSearchParams(new FormData(registrationForm))
        });
        const result = await response.json();

        if (!response.ok || !result.valid) {
            showValidationMessage(result.message || "Please review your registration details.", false);
            return;
        }

        showValidationMessage(result.message, true);
        registrationForm.submit();
    } catch (error) {
        showValidationMessage("Registration validation service is unavailable. Start the PHP service and try again.", false);
    } finally {
        submitButton.disabled = false;
    }
});

</script>

</body>
</html>
