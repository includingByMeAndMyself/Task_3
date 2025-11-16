package ru.yandex.practicum.stellarburgers.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

    private static final String CHROMEDRIVER_VERSION = "141.0.7390.122";
    private static final String CHROMEDRIVER_VERSION_YANDEX = "138.0.7214.57";

    public static WebDriver createDriver(Config.Browser browser) {
        WebDriver driver;

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver()
                        .driverVersion(CHROMEDRIVER_VERSION)
                        .setup();
                driver = new ChromeDriver();
                break;

            case YANDEX:
                ChromeOptions yandexOptions = new ChromeOptions();
                String yandexBrowserPath = System.getenv("YANDEX_BROWSER_PATH");
                if (yandexBrowserPath == null || yandexBrowserPath.isEmpty()) {
                    yandexBrowserPath = "C:\\Users\\" + System.getProperty("user.name") +
                            "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";
                }
                yandexOptions.setBinary(yandexBrowserPath);
                
                try {
                    WebDriverManager.chromedriver()
                            .browserVersion("138")
                            .setup();
                } catch (Exception e) {
                    try {
                        WebDriverManager.chromedriver()
                                .driverVersion("138.0.7214.57")
                                .setup();
                    } catch (Exception e2) {
                        System.out.println("Предупреждение: Используется последняя версия ChromeDriver для Yandex Browser. " +
                                         "Может быть несовместимость с версией браузера 138.");
                        WebDriverManager.chromedriver().clearDriverCache().setup();
                    }
                }

                driver = new ChromeDriver(yandexOptions);
                break;


            default:
                WebDriverManager.chromedriver()
                        .driverVersion(CHROMEDRIVER_VERSION)
                        .setup();
                driver = new ChromeDriver();
                break;
        }

        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            if (browser == Config.Browser.YANDEX) {
                System.out.println("Предупреждение: Не удалось максимизировать окно Yandex Browser, продолжаем работу");
            } else {
                throw e;
            }
        }
        return driver;
    }
}