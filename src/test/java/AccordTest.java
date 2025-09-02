import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pom.DriverFactory;
import pom.MainPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AccordTest {
    private static org.openqa.selenium.WebDriver driver;
    private static MainPage main;

    @BeforeAll
    public static void setup() {
        String browser = System.getProperty("browser", "chrome");
        driver = DriverFactory.create(browser);
        main = new MainPage(driver);
    }

    @BeforeEach
    public void openMain() {
        main.open();
    }

    @Test
    public void checkPriceFaq() {
        int index = 0;
        String expectedSnippet = "Сутки — 400 рублей. Оплата курьеру — наличными или картой.";
        main.clickQuestionToggle(index);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        String panelId = "accordion__panel-" + index;
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId)));
        String actual = main.getAnswerText(index).trim();
        assertTrue(actual.toLowerCase().contains(expectedSnippet.toLowerCase()));
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}