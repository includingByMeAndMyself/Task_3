package ru.yandex.practicum.stellarburgers.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {
    
    // Локаторы страницы входа
    private final By emailInput = By.xpath("//input[@name='name']");
    private final By passwordInput = By.xpath("//input[@name='Пароль']");
    public static final By loginButton = By.xpath("//button[text()='Войти']");
    private final By registerLink = By.xpath("//a[text()='Зарегистрироваться']");
    private final By recoverPasswordLink = By.xpath("//a[text()='Восстановить пароль']");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Ввести email: {email}")
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.presenceOfElementLocated(emailInput));
        driver.findElement(emailInput).sendKeys(email);
    }
    
    @Step("Ввести пароль: {password}")
    public void enterPassword(String password) {
        wait.until(ExpectedConditions.presenceOfElementLocated(passwordInput));
        driver.findElement(passwordInput).sendKeys(password);
    }
    
    @Step("Нажать на кнопку 'Войти'")
    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        driver.findElement(loginButton).click();
    }
    
    @Step("Нажать на ссылку 'Зарегистрироваться'")
    public void clickRegisterLink() {
        wait.until(ExpectedConditions.elementToBeClickable(registerLink));
        driver.findElement(registerLink).click();
    }
    
    @Step("Нажать на ссылку 'Восстановить пароль'")
    public void clickRecoverPasswordLink() {
        wait.until(ExpectedConditions.elementToBeClickable(recoverPasswordLink));
        driver.findElement(recoverPasswordLink).click();
    }
    
    @Step("Выполнить вход с email: {email} и паролем: {password}")
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }
}
