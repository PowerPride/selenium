package pom;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;

public class MainPage {
    private final WebDriver driver;
    private final By cookie = By.id("rcc-confirm-button");
    private final By orderTop = By.cssSelector("button.Button_Button__ra12g");
    private final By orderLower = By.cssSelector("button.Button_Middle__1CSJM");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        acceptCookiesIfVisible();
    }

    //падало если не закрывал куку, решил прожать чтобы забыть
    public void acceptCookiesIfVisible() {
        try {
            WebElement cookieButton = driver.findElement(cookie);
            if (cookieButton.isDisplayed()) {
                cookieButton.click();
            }
        } catch (Exception ignored) {}
    }
    //верхняя кнопка заказа
    public void clickTopOrder() {
        driver.findElement(orderTop).click();
    }
    //нижняя кнопка заказа
    public void clickButtonOrder() {
        driver.findElement(orderLower).click();
    }

    //клик по заголовку
    public void openFaqByIndex(int index) {
        String headingId = "accordion__heading-" + index;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement heading = wait.until(ExpectedConditions.elementToBeClickable(By.id(headingId)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", heading);
        heading.click();
    }
    //получение вопроса
    public String getFaqQuestionTextByIndex(int index) {
        String headingId = "accordion__heading-" + index;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement heading = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(headingId)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", heading);
        return heading.getText().trim();
    }
    //получение ответа
    public String getFaqAnswerTextByIndex(int index) {
        String panelId = "accordion__panel-" + index;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(panelId)));
        return driver.findElement(By.id(panelId)).getText().trim();
    }
}