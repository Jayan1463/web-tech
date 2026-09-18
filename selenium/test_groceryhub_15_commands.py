import os
from urllib.parse import urljoin

from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait


BASE_URL = os.environ.get("GROCERYHUB_BASE_URL", "http://127.0.0.1:8080/Grocery_shop/")


def app_url(path):
    return urljoin(BASE_URL, path)


def main():
    options = Options()
    options.add_argument("--headless=new")
    options.add_argument("--window-size=1366,900")

    driver = webdriver.Chrome(options=options)
    wait = WebDriverWait(driver, 10)

    try:
        # 1. Open the GroceryHub home page.
        driver.get(app_url(""))

        # 2. Assert the home page title.
        assert driver.title == "GroceryHub - Fresh Groceries"

        # 3. Assert the hero heading is visible.
        hero_heading = wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR, ".hero h1")))
        assert "Fresh groceries" in hero_heading.text

        # 4. Click the Shop Now button.
        driver.find_element(By.LINK_TEXT, "Shop Now").click()

        # 5. Assert the products page title.
        wait.until(EC.title_is("Products - GroceryHub"))

        # 6. Click the Fruits category filter.
        wait.until(EC.element_to_be_clickable((By.LINK_TEXT, "Fruits"))).click()

        # 7. Assert the category URL was applied.
        wait.until(EC.url_contains("category=Fruits"))

        # 8. Type a grocery search term.
        search_box = wait.until(EC.visibility_of_element_located((By.NAME, "search")))
        search_box.clear()
        search_box.send_keys("milk")

        # 9. Click the products search button.
        driver.find_element(By.CSS_SELECTOR, ".search-form button[type='submit']").click()

        # 10. Assert the search URL was applied.
        wait.until(EC.url_contains("search=milk"))

        # 11. Click the Feedback navigation link.
        wait.until(EC.element_to_be_clickable((By.LINK_TEXT, "Feedback"))).click()

        # 12. Assert the feedback heading is visible.
        feedback_heading = wait.until(EC.visibility_of_element_located((By.CSS_SELECTOR, ".feedback-card h1")))
        assert feedback_heading.text == "Customer Feedback"

        # 13. Open the registration page.
        driver.get(app_url("register.jsp"))

        # 14. Type a sample name into the registration form.
        name_input = wait.until(EC.visibility_of_element_located((By.ID, "name")))
        name_input.send_keys("Selenium Tester")

        # 15. Assert the typed value is present.
        assert name_input.get_attribute("value") == "Selenium Tester"

        print("PASS: GroceryHub Selenium test completed 15 commands.")
    finally:
        driver.quit()


if __name__ == "__main__":
    main()





