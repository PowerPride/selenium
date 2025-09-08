import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pom.DriverFactory;
import pom.MainPage;
import pom.OrderPage;
import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private static WebDriver driver;
    private static MainPage main;
    private static OrderPage order;

    @BeforeAll
    public static void setup() {
        String browser = System.getProperty("browser", "chrome");
        driver = DriverFactory.create(browser);
        main = new MainPage(driver);
        order = new OrderPage(driver);
    }

    @BeforeEach
    public void openMain() {
        main.open();
    }

    @ParameterizedTest
    @CsvSource({
            "Халк,Хоган,ул. Полевая 1,Технопарк,+79829999991,10.09.2025,двое суток,черный,Не звоните в домофон я зол,top",
            "Джейсон,Стетхам,ул. Брутальная 10,Черкизовская,+79124444442,20.09.2025,трое суток,серый,Лучше с друзьями на велике, чем с чертями на гелике,button"
    })
    public void positiveOrderFlow(String name, String surname, String address, String metro, String phone,
                                  String date, String rentPeriod, String color, String comment, String entryPoint) {

        if ("button".equalsIgnoreCase(entryPoint)) {
            main.clickButtonOrder();
        } else {
            main.clickTopOrder();
        }

        order.fillCustomerForm(name, surname, address, metro, phone);
        order.fillRentForm(date, rentPeriod, color, comment);

        String confirmation = order.getOrderConfirmationText();
        assertNotNull(confirmation);
        assertFalse(confirmation.isEmpty());
        assertTrue(confirmation.toLowerCase().contains("заказ") || confirmation.toLowerCase().contains("оформлен"),confirmation);
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) driver.quit();
    }
}