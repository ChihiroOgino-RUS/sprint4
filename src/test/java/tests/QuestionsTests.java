package tests;

import core.BaseTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageObjects.HomePage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class QuestionsTests extends BaseTest {

    private final String browser;
    private final String question;
    private final String expectedAnswer;

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public QuestionsTests(String browser, String question, String expectedAnswer) {
        this.browser = browser;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters(name = "{0}: {1}")
    public static Object[][] getData() {
        String[] browsers = {"chrome", "firefox"};

        String[][] questions = {
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };

        Object[][] data = new Object[browsers.length * questions.length][3];

        int index = 0;
        for (String browser : browsers) {
            for (String[] qa : questions) {
                data[index][0] = browser; // браузер
                data[index][1] = qa[0];   // вопрос
                data[index][2] = qa[1];   // ответ
                index++;
            }
        }

        return data;
    }

    @Before
    public void setUp() {
        initDriver(browser);
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {
        quitDriver();
    }

    @Test
    public void checkFAQAnswer() {
        HomePage homePage = new HomePage(driver);
        homePage.closeCookieBanner();

        String actualAnswer = homePage.getAnswer(question);

        assertEquals("Текст ответа не совпадает для вопроса: " + question,
                expectedAnswer, actualAnswer);
    }
}
