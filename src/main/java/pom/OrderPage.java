package pom;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By nameOrder = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameOrder = By.xpath("//input[@placeholder='* Фамилия']");
    private final By adressOrder = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroInputOrder = By.xpath("//input[@placeholder='* Станция метро']");
    private final By metroSelectorOrder = By.className("select-search__select");
    private final By phoneOrder = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By buttonNextOrder = By.cssSelector("button.Button_Button__ra12g.Button_Middle__1CSJM") ;
    private final By datapickerWhenBringOrder = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By selectorRentOrder = By.className("Dropdown-placeholder");
    private final By commentCourierOrder = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By buttonToOrder = By.xpath("//button[contains(@class,'Button_Middle__1CSJM') and contains(text(),'Заказать')]");
    private final By modalWindowOrder = By.cssSelector("div.Order_Modal__YZ-d3");
    private final By acceptButtonModalOrder = By.xpath("//button[contains(text(),'Да')]") ;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }
    //заполнение персональных данных
    public void fillCustomerForm(String name, String surname, String address, String metro, String phone) {
        //имя
        driver.findElement(nameOrder).sendKeys(name);
        //фамилия
        driver.findElement(surnameOrder).sendKeys(surname);
        //адрес
        driver.findElement(adressOrder).sendKeys(address);
        //метро
        driver.findElement(metroInputOrder).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(metroSelectorOrder));
        driver.findElement(By.xpath(".//*[text()='" + metro + "']")).click();
        //телефон
        driver.findElement(phoneOrder).sendKeys(phone);
        //кнопка далее
        driver.findElement(buttonNextOrder).click();
    }

    //заполнение данных заказа
    public void fillRentForm(String date, String rentPeriod, String color, String comment) {
        //датапикер когда привезти заказ
        WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(datapickerWhenBringOrder));
        dateInput.sendKeys(date);

        //прожали интер чтобы перейти к следующему шагу иначе тест падает
        dateInput.sendKeys(Keys.ENTER);

        //ждем и кликаем по сроку аренды
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(selectorRentOrder));
        dropdown.click();

        //выбираем срок периода
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'Dropdown-option') and text()='" + rentPeriod + "']")));
        option.click();

        //выбираем цвет самоката
        if (color != null && !color.isBlank()) {
            String id = color.toLowerCase().contains("черный") ? "black" : "grey";
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
        }

        //коментарий для курьера
        driver.findElement(commentCourierOrder).sendKeys(comment);
        //кнопка заказать
        driver.findElement(buttonToOrder).click();
        //кнопка да
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(acceptButtonModalOrder));
        confirmButton.click();
    }

    public String getOrderConfirmationText() {
        //возвращает еткст из модалки
        return wait.until(ExpectedConditions.visibilityOfElementLocated(modalWindowOrder)).getText();
    }
}