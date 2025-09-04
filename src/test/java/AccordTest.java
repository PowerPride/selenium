import org.junit.jupiter.api.*;
import pom.DriverFactory;
import pom.MainPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

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
    @ParameterizedTest
    @MethodSource("accordText")
    public void checkAccordByIndexHeadingAndAnswer(int index, String expectedHeading, String expectedFullAnswer) {

        String actualHeading = main.getFaqQuestionTextByIndex(index);
        assertEquals(expectedHeading, actualHeading);

        main.openFaqByIndex(index);
        String actualAnswer = main.getFaqAnswerTextByIndex(index);
        assertEquals(expectedFullAnswer, actualAnswer.trim());
    }

    private static Stream<Arguments> accordText() {
        return Stream.of(
                Arguments.of(0,
                        "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                ),
                Arguments.of(1,
                        "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."
                ),
                Arguments.of(2,
                        "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                ),
                Arguments.of(3,
                        "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                ),
                Arguments.of(4,
                        "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."
                ),
                Arguments.of(5,
                        "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                ),
                Arguments.of(6,
                        "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."
                ),
                Arguments.of(7,
                        "Я жизу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                )
        );
    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }
}