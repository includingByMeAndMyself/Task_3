package ru.yandex.practicum.stellarburgers.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class RecoverPasswordPage extends BasePage {
    
    // Локаторы страницы восстановления пароля
    private final By loginLink = By.xpath("//a[text()='Войти']");
    
    public RecoverPasswordPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Нажать на ссылку 'Войти'")
    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        driver.findElement(loginLink).click();
    }
}
