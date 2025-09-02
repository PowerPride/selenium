package pom;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

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
    //клик по аккордеону
    public void clickQuestionToggle(int index) {
        driver.findElement(By.id("accordion__heading-" + index)).click();
    }

    //возвращаем текст вложенный из аккордеона
    public String getAnswerText(int index) {
        return driver.findElement(By.id("accordion__panel-" + index)).getText();
    }
}