package ru.yandex.practicum.stellarburgers.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    
    public static WebDriver createDriver(Config.Browser browser) {
        WebDriver driver;
        
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                break;
            case YANDEX:
                WebDriverManager.chromedriver().setup();
                ChromeOptions yandexOptions = new ChromeOptions();
                String yandexBrowserPath = System.getenv("YANDEX_BROWSER_PATH");
                if (yandexBrowserPath == null || yandexBrowserPath.isEmpty()) {
                    yandexBrowserPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";
                }
                try {
                    yandexOptions.setBinary(yandexBrowserPath);
                } catch (Exception e) {
                    System.out.println("Yandex браузер не найден, используется Chrome");
                }
                driver = new ChromeDriver(yandexOptions);
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }
        
        driver.manage().window().maximize();
        return driver;
    }
}
