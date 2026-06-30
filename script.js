document.addEventListener("DOMContentLoaded", function () {
    var groceryItems = [
        { name: "Apples", category: "Fruits", price: 120, status: "Fresh stock" },
        { name: "Bananas", category: "Fruits", price: 60, status: "Available" },
        { name: "Tomatoes", category: "Vegetables", price: 45, status: "Morning stock" },
        { name: "Onions", category: "Vegetables", price: 38, status: "Available" },
        { name: "Milk", category: "Dairy", price: 28, status: "Cold storage" },
        { name: "Paneer", category: "Dairy", price: 95, status: "Limited" },
        { name: "Rice", category: "Pantry", price: 70, status: "Available" },
        { name: "Cooking Oil", category: "Pantry", price: 140, status: "Selling fast" }
    ];

    var bannerTrack = document.querySelector(".banner-track");
    var bannerSlides = document.querySelectorAll(".banner-slide");
    var previousButton = document.getElementById("prevSlide");
    var nextButton = document.getElementById("nextSlide");
    var sliderDots = document.getElementById("sliderDots");
    var activeSlide = 0;
    var sliderTimer;

    var dynamicProducts = document.getElementById("dynamicProducts");
    var searchItem = document.getElementById("searchItem");
    var categoryFilter = document.getElementById("categoryFilter");
    var productCount = document.getElementById("productCount");
    var itemSelect = document.getElementById("itemSelect");
    var quantityInput = document.getElementById("quantity");
    var orderTotal = document.getElementById("orderTotal");
    var cartList = document.getElementById("cartList");
    var cartTotal = document.getElementById("cartTotal");
    var clearCartButton = document.getElementById("clearCart");
    var rememberNameButton = document.getElementById("rememberName");
    var clearNameButton = document.getElementById("clearName");
    var welcomeMessage = document.getElementById("welcomeMessage");
    var cartItems = [];

    var registrationForm = document.getElementById("registrationForm");
    var generateRegNoButton = document.getElementById("generateRegNo");
    var registrationNumberInput = document.getElementById("registrationNumber");

    function setCookie(name, value, days) {
        var expiryDate = new Date();
        expiryDate.setTime(expiryDate.getTime() + days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + encodeURIComponent(value) + ";expires=" + expiryDate.toUTCString() + ";path=/";

        try {
            localStorage.setItem(name, value);
        } catch (error) {
            console.log("Local storage is not available.");
        }
    }

    function getCookie(name) {
        var cookieList = document.cookie.split("; ");

        for (var i = 0; i < cookieList.length; i++) {
            var cookiePart = cookieList[i].split("=");

            if (cookiePart[0] === name) {
                return decodeURIComponent(cookiePart[1]);
            }
        }

        try {
            return localStorage.getItem(name);
        } catch (error) {
            return "";
        }
    }

    function deleteCookie(name) {
        document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 UTC;path=/";

        try {
            localStorage.removeItem(name);
        } catch (error) {
            console.log("Local storage is not available.");
        }
    }

    function showSavedName() {
        var savedName = getCookie("freshBasketName");

        if (savedName) {
            welcomeMessage.textContent = "Welcome back, " + savedName + ". Here are today's available items.";
        } else {
            welcomeMessage.textContent = "Welcome to Fresh Basket Grocery. Use the buttons below to test cookies and dynamic content.";
        }
    }

    function createSliderDots() {
        sliderDots.innerHTML = "";

        bannerSlides.forEach(function (slide, index) {
            var dot = document.createElement("button");
            dot.type = "button";
            dot.className = "slider-dot";
            dot.setAttribute("aria-label", "Show offer " + (index + 1));
            dot.addEventListener("click", function () {
                goToSlide(index);
                restartSlider();
            });
            sliderDots.appendChild(dot);
        });
    }

    function goToSlide(slideNumber) {
        if (slideNumber < 0) {
            activeSlide = bannerSlides.length - 1;
        } else if (slideNumber >= bannerSlides.length) {
            activeSlide = 0;
        } else {
            activeSlide = slideNumber;
        }

        bannerTrack.style.transform = "translateX(-" + activeSlide * 33.3333 + "%)";

        document.querySelectorAll(".slider-dot").forEach(function (dot, index) {
            dot.classList.toggle("active", index === activeSlide);
        });
    }

    function nextSlide() {
        goToSlide(activeSlide + 1);
    }

    function previousSlide() {
        goToSlide(activeSlide - 1);
    }

    function startSlider() {
        stopSlider();
        sliderTimer = setInterval(nextSlide, 4000);
    }

    function stopSlider() {
        clearInterval(sliderTimer);
    }

    function restartSlider() {
        stopSlider();
        startSlider();
    }

    function renderProducts(category, searchText) {
        var filteredItems = groceryItems.filter(function (item) {
            var matchesCategory = category === "all" || item.category === category;
            var matchesSearch = item.name.toLowerCase().includes(searchText.toLowerCase());
            return matchesCategory && matchesSearch;
        });

        dynamicProducts.innerHTML = "";

        if (filteredItems.length === 0) {
            dynamicProducts.innerHTML = "<article class=\"dynamic-item\"><h3>No matching items</h3><p>Try another search or category.</p></article>";
            productCount.textContent = "0 item(s) displayed from " + groceryItems.length + " total items.";
            return;
        }

        filteredItems.forEach(function (item) {
            var productCard = document.createElement("article");
            productCard.className = "dynamic-item";
            productCard.innerHTML =
                "<span class=\"tag\">" + item.category + "</span>" +
                "<h3>" + item.name + "</h3>" +
                "<p>" + item.status + "</p>" +
                "<strong>Rs. " + item.price + "</strong>" +
                "<button type=\"button\" class=\"add-cart\" data-name=\"" + item.name + "\">Add to Cart</button>";
            dynamicProducts.appendChild(productCard);
        });

        productCount.textContent = filteredItems.length + " item(s) displayed from " + groceryItems.length + " total items.";
    }

    function refreshProducts() {
        renderProducts(categoryFilter.value, searchItem.value.trim());
    }

    function fillItemSelect() {
        itemSelect.innerHTML = "";

        groceryItems.forEach(function (item, index) {
            var option = document.createElement("option");
            option.value = index;
            option.textContent = item.name + " - Rs. " + item.price;
            itemSelect.appendChild(option);
        });
    }

    function updateTotal() {
        var selectedItem = groceryItems[Number(itemSelect.value)];
        var quantity = Number(quantityInput.value);

        if (!selectedItem || quantity < 1) {
            orderTotal.textContent = "Please choose an item and a valid quantity.";
            return;
        }

        var total = selectedItem.price * quantity;
        orderTotal.textContent = quantity + " x " + selectedItem.name + " = Rs. " + total;
    }

    function renderCart() {
        var total = 0;
        cartList.innerHTML = "";

        if (cartItems.length === 0) {
            cartList.innerHTML = "<li>No items added yet.</li>";
            cartTotal.textContent = "Cart total: Rs. 0";
            return;
        }

        cartItems.forEach(function (item) {
            var listItem = document.createElement("li");
            listItem.textContent = item.name + " - Rs. " + item.price;
            cartList.appendChild(listItem);
            total = total + item.price;
        });

        cartTotal.textContent = "Cart total: Rs. " + total;
    }

    function addToCart(itemName) {
        var selectedItem = groceryItems.find(function (item) {
            return item.name === itemName;
        });

        if (selectedItem) {
            cartItems.push(selectedItem);
            renderCart();
        }
    }

    function generateRegistrationNumber() {
        var randomNumber = Math.floor(100000 + Math.random() * 900000);
        registrationNumberInput.value = "FBG-" + randomNumber;
        registrationNumberInput.classList.add("success-field");
        alert("Your registration number is " + registrationNumberInput.value);
    }

    function showError(inputId, message) {
        var input = document.getElementById(inputId);
        var error = document.getElementById(inputId + "Error");

        if (input) {
            input.classList.add("error-field");
            input.classList.remove("success-field");
        }

        if (error) {
            error.textContent = message;
        }
    }

    function showSuccess(inputId) {
        var input = document.getElementById(inputId);
        var error = document.getElementById(inputId + "Error");

        if (input) {
            input.classList.remove("error-field");
            input.classList.add("success-field");
        }

        if (error) {
            error.textContent = "";
        }
    }

    function validateForm() {
        var isValid = true;
        var name = document.getElementById("name").value.trim();
        var email = document.getElementById("email").value.trim();
        var phone = document.getElementById("phone").value.trim();
        var registrationNumber = document.getElementById("registrationNumber").value.trim();
        var interest = document.getElementById("interest").value;
        var message = document.getElementById("message").value.trim();
        var agreeTerms = document.getElementById("agreeTerms").checked;

        if (name.length < 3) {
            showError("name", "Name must have at least 3 letters.");
            isValid = false;
        } else {
            showSuccess("name");
        }

        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            showError("email", "Enter a valid email address.");
            isValid = false;
        } else {
            showSuccess("email");
        }

        if (!/^[0-9]{10}$/.test(phone)) {
            showError("phone", "Phone number must contain exactly 10 digits.");
            isValid = false;
        } else {
            showSuccess("phone");
        }

        if (!/^FBG-[0-9]{6}$/.test(registrationNumber)) {
            showError("registrationNumber", "Generate a registration number first.");
            isValid = false;
        } else {
            showSuccess("registrationNumber");
        }

        if (interest === "") {
            showError("interest", "Please select one category.");
            isValid = false;
        } else {
            showSuccess("interest");
        }

        if (message.length < 10) {
            showError("message", "Message must have at least 10 characters.");
            isValid = false;
        } else {
            showSuccess("message");
        }

        if (!agreeTerms) {
            showError("agreeTerms", "Please tick the agreement checkbox.");
            isValid = false;
        } else {
            showSuccess("agreeTerms");
        }

        return isValid;
    }

    function clearFormStyles() {
        registrationForm.querySelectorAll("input, select, textarea").forEach(function (field) {
            field.classList.remove("error-field");
            field.classList.remove("success-field");
        });

        registrationForm.querySelectorAll(".error-message").forEach(function (message) {
            message.textContent = "";
        });
    }

    createSliderDots();
    goToSlide(0);
    startSlider();
    renderProducts("all", "");
    fillItemSelect();
    updateTotal();
    renderCart();
    showSavedName();

    previousButton.addEventListener("click", function () {
        previousSlide();
        restartSlider();
    });

    nextButton.addEventListener("click", function () {
        nextSlide();
        restartSlider();
    });

    document.querySelector(".banner-slider").addEventListener("mouseenter", stopSlider);
    document.querySelector(".banner-slider").addEventListener("mouseleave", startSlider);

    categoryFilter.addEventListener("change", refreshProducts);
    searchItem.addEventListener("input", refreshProducts);

    dynamicProducts.addEventListener("click", function (event) {
        if (event.target.classList.contains("add-cart")) {
            addToCart(event.target.dataset.name);
        }
    });

    clearCartButton.addEventListener("click", function () {
        var shouldClearCart = confirm("Do you want to clear the selected cart items?");

        if (shouldClearCart) {
            cartItems = [];
            renderCart();
        }
    });

    itemSelect.addEventListener("change", updateTotal);
    quantityInput.addEventListener("input", updateTotal);

    rememberNameButton.addEventListener("click", function () {
        var visitorName = prompt("Enter your name to save it in a cookie:");

        if (visitorName && visitorName.trim().length > 0) {
            setCookie("freshBasketName", visitorName.trim(), 7);
            showSavedName();
            alert("Your name has been saved for 7 days.");
        }
    });

    clearNameButton.addEventListener("click", function () {
        var shouldClear = confirm("Do you want to clear the saved cookie name?");

        if (shouldClear) {
            deleteCookie("freshBasketName");
            showSavedName();
            alert("Saved cookie name cleared.");
        }
    });

    generateRegNoButton.addEventListener("click", generateRegistrationNumber);

    registrationForm.addEventListener("submit", function (event) {
        event.preventDefault();

        if (!validateForm()) {
            alert("Please correct the form errors before submitting.");
            return;
        }

        var name = document.getElementById("name").value.trim();
        var shouldSubmit = confirm("Submit this registration for " + name + "?");

        if (shouldSubmit) {
            setCookie("freshBasketName", name, 7);
            document.getElementById("formResult").textContent = "Registration completed for " + name + ".";
            showSavedName();
            alert("Registration submitted successfully.");
            registrationForm.reset();
            clearFormStyles();
        }
    });
});
