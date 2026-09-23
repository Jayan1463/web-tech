package com.grocery.test;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GroceryApplicationTest {

    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String targetUrl = "http://127.0.0.1:8080/";

        try {
            System.out.println("--- Module A: Browser Basics ---");
            driver.manage().window().maximize();
            driver.get(targetUrl);

            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page Title: " + driver.getTitle());
            System.out.println("Page Source Length: " + driver.getPageSource().length());

            driver.navigate().refresh();
            driver.navigate().to(targetUrl + "cart.jsp");
            driver.navigate().back();

            String mainWindow = driver.getWindowHandle();
            driver.switchTo().newWindow(WindowType.TAB);
            driver.get(targetUrl + "register.jsp");
            driver.close();
            driver.switchTo().window(mainWindow);

            System.out.println("\n--- Module C: Locating Elements ---");
            WebElement categoryByClass = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.className("category-card")));
            WebElement countByLinkText = driver.findElement(By.linkText("Count This Visit"));
            WebElement firstAnchorByTag = driver.findElement(By.tagName("a"));
            WebElement categoryByCss = driver.findElement(By.cssSelector(".category-card"));
            WebElement categoryByXpath = driver.findElement(
                    By.xpath("//a[contains(@class,'category-card')]//h3[text()='Vegetables']"));

            System.out.println("className: " + categoryByClass.getText());
            System.out.println("linkText: " + countByLinkText.getText());
            System.out.println("tagName href: " + firstAnchorByTag.getAttribute("href"));
            System.out.println("cssSelector: " + categoryByCss.getText());
            System.out.println("xpath: " + categoryByXpath.getText());
            System.out.println("getAttribute class: " + categoryByClass.getAttribute("class"));
            System.out.println("isDisplayed: " + categoryByClass.isDisplayed());
            System.out.println("isEnabled: " + countByLinkText.isEnabled());

            System.out.println("\n--- Module B: Web Elements ---");
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    countByLinkText);
            try {
                countByLinkText.click();
            } catch (ElementNotInteractableException error) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", countByLinkText);
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".visit-section strong")));
            String visitText = (String) ((JavascriptExecutor) driver).executeScript(
                    "return document.querySelector('.visit-section strong').textContent.trim();");
            System.out.println("getText: " + visitText);

            driver.get(targetUrl + "products");
            WebElement searchBox = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.id("search")));
            WebElement searchByName = driver.findElement(By.name("search"));
            searchBox.clear();
            searchBox.sendKeys("Rice");
            System.out.println("sendKeys value: " + searchByName.getAttribute("value"));

            List<WebElement> productCards = driver.findElements(By.className("product-card"));
            System.out.println("findElements product count: " + productCards.size());
            WebElement firstProduct = driver.findElement(By.cssSelector(".product-card h3"));
            System.out.println("findElement first product: " + firstProduct.getText());

            driver.get(targetUrl + "feedback.jsp");
            Select rating = new Select(wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.name("rating"))));
            rating.selectByValue("5");
            System.out.println("isSelected: " + rating.getFirstSelectedOption().isSelected());

            System.out.println("\nPASS: Firefox Selenium test completed.");
        } finally {
            driver.quit();
        }
    }
}
