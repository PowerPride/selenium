package pom;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    public static WebDriver create(String browser) {
        if (browser == null) browser = "chrome";
        switch (browser.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "C:\\Users\\Professional\\Downloads\\geckodriver-v0.36.0-win64\\geckodriver.exe");
                return new FirefoxDriver();
            case "chrome":
            default:
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\Professional\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
                ChromeOptions options = new ChromeOptions();
                return new ChromeDriver(options);
        }
    }
}