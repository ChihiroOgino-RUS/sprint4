package core;

import org.openqa.selenium.WebDriver;

public abstract class BaseTest {
    protected WebDriver driver;

    protected void initDriver(String browser) {
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
    }

    protected void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
