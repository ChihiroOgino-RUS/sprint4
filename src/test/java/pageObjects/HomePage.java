package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;

    // Кнопка «Заказать» в header (верх страницы)
    private final By topOrderButton = By.xpath("//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");

    // Кнопка «Заказать» внизу страницы
    private final By bottomOrderButton = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");


    // Ответы
    private final By answerVisible = By.xpath("//div[contains(@class, 'accordion__panel') and not(@hidden)]");


    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Клик по верхней кнопке "Заказать"
    public void clickTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    // Клик по нижней кнопке "Заказать"
    public void clickBottomOrderButton() {
        driver.findElement(bottomOrderButton).click();
    }

    // Вопросы
    private By questionElement(String text) {
        return By.xpath("//div[contains(@class, 'accordion__button') and text()='" + text + "']");
    }

    public void clickQuestion(String question) {
        WebElement element = driver.findElement(questionElement(question));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    public String getAnswer(String question) {
        clickQuestion(question);
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> d.findElement(answerVisible).isDisplayed());
        return driver.findElement(answerVisible).getText();
    }
}
