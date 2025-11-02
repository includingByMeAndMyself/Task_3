package ru.yandex.practicum.stellarburgers.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import ru.yandex.practicum.stellarburgers.config.Config;
import ru.yandex.practicum.stellarburgers.config.DriverFactory;
import io.qameta.allure.Step;

public class BaseTest {
    
    protected WebDriver driver;
    protected Config.Browser browser;
    
    public BaseTest() {
        this.browser = Config.Browser.CHROME;
    }
    
    public BaseTest(Config.Browser browser) {
        this.browser = browser;
    }
    
    @Before
    @Step("Инициализация драйвера")
    public void setUp() {
        driver = DriverFactory.createDriver(browser);
    }
    
    @After
    @Step("Закрытие драйвера")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
