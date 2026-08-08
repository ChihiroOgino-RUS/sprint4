package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;

    // Группа элементов первой части
    // поле "Имя"
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");

    // поле "Фамилия"
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");

    // поле "Адрес"
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // поле "Станция метро"
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");

    // поле "Телефон"
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // кнопка "Далее"
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // Группа элементов второй части
    // поле "Когда привезти самокат"
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");

    // поле "Срок аренды"
    private final By periodField = By.xpath("//div[contains(@class, 'Dropdown-root')]");

    // Чек-бокс цвет самоката "Чёрный жемчуг"
    private final By blackCheckbox = By.id("black");

    // Чек-бокс цвет самоката "Серая безысходность"
    private final By greyCheckbox = By.id("grey");

    // поле "Комментарий для курьера"
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // кнопка "Заказать"
    private final By orderButton = By.xpath("//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");

    // Диалог подтверждения
    // Заголовок окна «Хотите оформить заказ?»
    private final By confirmModalHeader = By.xpath("//div[contains(text(), 'Хотите оформить заказ')]");

    // кнопка "Да"
    private final By confirmYesButton = By.xpath("//button[text()='Да']");

    // Заголовок окна «Заказ оформлен»
    private final By successModalHeader = By.xpath("//div[contains(text(), 'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Элемент станции метро в выпадающем списке
    private By metroOption(String stationName) {
        return By.xpath("//div[text()='" + stationName + "']");
    }

    // Выбор станции метро
    public void selectMetro(String metroStation) {
        driver.findElement(metroField).click();
        driver.findElement(metroField).sendKeys(metroStation);
        driver.findElement(metroOption(metroStation)).click();
    }

    // Элемент даты в календаре
    private By calendarDay(int day) {
        return By.xpath(
                "//div[contains(@class, 'react-datepicker__day')" +
                        " and not(contains(@class, 'react-datepicker__day--outside-month'))" +
                        " and text()='" + day + "']"
        );
    }

    // Выбор даты
    public void selectDate(int day) {
        driver.findElement(dateField).click();
        driver.findElement(calendarDay(day)).click();
    }

    // Элемент периода
    private By periodOption(String period) {
        return By.xpath("//div[text()='" + period + "']");
    }

    // Выбор периода
    public void selectPeriod(String period) {
        driver.findElement(periodField).click();
        driver.findElement(periodOption(period)).click();
    }

    public void setNameField(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    public void setSurnameField(String surname) {
        driver.findElement(surnameField).sendKeys(surname);
    }

    public void setAddressField(String address) {
        driver.findElement(addressField).sendKeys(address);
    }

    public void setPhoneField(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }

    public void fillFirstPart(String name, String surname, String address, String metro, String phone) {
        setNameField(name);
        setSurnameField(surname);
        setAddressField(address);
        selectMetro(metro);
        setPhoneField(phone);
        clickNextButton();
    }

    public void clickBlackCheckbox() {
        driver.findElement(blackCheckbox).click();
    }

    public void clickGreyCheckbox() {
        driver.findElement(greyCheckbox).click();
    }

    public void setCommentField(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void fillSecondPart(int day, String period, boolean black, boolean grey, String comment) {
        selectDate(day);
        selectPeriod(period);
        if (black) {
            clickBlackCheckbox();
        }
        if (grey) {
            clickGreyCheckbox();
        }
        setCommentField(comment);
        clickOrderButton();
    }

    public String getConfirmHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> d.findElement(confirmModalHeader).isDisplayed());
        return driver.findElement(confirmModalHeader).getText();
    }

    public void confirmOrder() {
        driver.findElement(confirmYesButton).click();
    }

    public String getSuccessHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> d.findElement(successModalHeader).isDisplayed());
        return driver.findElement(successModalHeader).getText();
    }
}
