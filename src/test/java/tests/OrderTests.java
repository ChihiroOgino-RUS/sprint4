package tests;

import core.BaseTest;
import models.OrderData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pageObjects.HomePage;
import pageObjects.OrderPage;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTests extends BaseTest {

    private final String browser;
    private final String entryPoint;
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final int day;
    private final String period;
    private final boolean black;
    private final boolean grey;
    private final String comment;

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public OrderTests(String browser, String entryPoint, String name, String surname, String address, String metro, String phone, int day, String period, boolean black, boolean grey, String comment) {
        this.browser = browser;
        this.entryPoint = entryPoint;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.day = day;
        this.period = period;
        this.black = black;
        this.grey = grey;
        this.comment = comment;
    }

    @Parameterized.Parameters(name = "{0}: {1}")
    public static Object[][] getData() {
        String[] browsers = {"chrome", "firefox"};

        String[] entryPoints = {"top", "bottom"};

        int day = LocalDate.now().getDayOfMonth();

        OrderData[] users = {
                new OrderData(
                        "Алиса",
                        "Селезнева",
                        "Москва",
                        "Комсомольская",
                        "79165555555",
                        day,
                        "сутки",
                        true,
                        true,
                        "Желательно побыстрее"
                ),
                new OrderData(
                        "Николай",
                        "Герасимов",
                        "Москва",
                        "Сокольники",
                        "79152222222",
                        day,
                        "двое суток",
                        true,
                        false,
                        "Не забудь миелофон"
                ),
        };

        Object[][] data = new Object[browsers.length * entryPoints.length * users.length][12];

        int index = 0;
        for (String browser : browsers) {
            for (String entryPoint : entryPoints) {
                for (OrderData user : users) {
                    data[index][0] = browser;
                    data[index][1] = entryPoint;
                    data[index][2] = user.name;
                    data[index][3] = user.surname;
                    data[index][4] = user.address;
                    data[index][5] = user.metro;
                    data[index][6] = user.phone;
                    data[index][7] = user.day;
                    data[index][8] = user.period;
                    data[index][9] = user.black;
                    data[index][10] = user.grey;
                    data[index][11] = user.comment;
                    index++;
                }
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
    public void checkOrder() {
        HomePage homePage = new HomePage(driver);

        if (entryPoint.equals("top")) {
            homePage.clickTopOrderButton();
        } else {
            homePage.clickBottomOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstPart(name, surname, address, metro, phone);
        orderPage.fillSecondPart(day, period, black, grey, comment);

        String confirmHeader = orderPage.getConfirmHeader();
        assertTrue("Не появилось окно подтверждения",
                confirmHeader.contains("Хотите оформить заказ"));

        orderPage.confirmOrder();

        String successHeader = orderPage.getSuccessHeader();
        assertTrue("Не появилось окно успешного заказа",
                successHeader.contains("Заказ оформлен"));
    }
}
