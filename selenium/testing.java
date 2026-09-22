import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;

public class testing {
    private static final String BASE_URL = System.getenv().getOrDefault(
            "GROCERYHUB_BASE_URL",
            "http://127.0.0.1:8080/Grocery_shop/");

    public static void main(String[] args) {
        WebDriver driver = browser(args.length == 0 ? "chrome" : args[0]);

        try {
            driver.get(BASE_URL);
            System.out.println("URL: " + driver.getCurrentUrl());
            System.out.println("Title: " + driver.getTitle());
            System.out.println("Source length: " + driver.getPageSource().length());
            driver.navigate().refresh();

            WebElement hero = driver.findElement(By.className("hero"));
            check(hero.isDisplayed(), "Home hero should be displayed");

            List<WebElement> navLinks = driver.findElements(By.tagName("a"));
            check(navLinks.size() > 0, "findElements should locate page links");

            driver.findElement(By.linkText("Shop Now")).click();
            driver.navigate().back();
            driver.navigate().forward();

            WebElement search = driver.findElement(By.name("search"));
            check(search.isEnabled(), "Search input should be enabled");
            search.clear();
            search.sendKeys("milk");
            check("milk".equals(search.getAttribute("value")), "sendKeys/getAttribute should work");

            WebElement searchButton = driver.findElement(By.cssSelector(".search-form button[type='submit']"));
            check("Search".equals(searchButton.getText().trim()), "getText should read Search button");
            searchButton.click();

            driver.navigate().to(BASE_URL + "feedback.jsp");
            check(driver.findElement(By.xpath("//h1[contains(.,'Customer Feedback')]")).isDisplayed(),
                    "XPath should locate feedback heading");

            Select rating = new Select(driver.findElement(By.name("rating")));
            rating.selectByValue("5");
            check(rating.getFirstSelectedOption().isSelected(), "isSelected should work on rating option");

            String mainWindow = driver.getWindowHandle();
            driver.switchTo().newWindow(WindowType.TAB);
            driver.navigate().to(BASE_URL + "register.jsp");
            check(driver.findElement(By.id("name")).isDisplayed(), "ID locator should find name field");
            driver.close();
            driver.switchTo().window(mainWindow);

            System.out.println("PASS: Java Selenium assignment covers browser basics, elements and locators.");
        } finally {
            driver.quit();
        }
    }

    private static WebDriver browser(String name) {
        switch (name.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefox = new FirefoxOptions();
                firefox.addArguments("-headless");
                return new FirefoxDriver(firefox);
            case "edge":
                EdgeOptions edge = new EdgeOptions();
                edge.addArguments("--headless=new", "--window-size=1366,900");
                return new EdgeDriver(edge);
            default:
                ChromeOptions chrome = new ChromeOptions();
                chrome.addArguments("--headless=new", "--window-size=1366,900");
                return new ChromeDriver(chrome);
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
