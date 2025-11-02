package ru.yandex.practicum.stellarburgers.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class RegisterPage extends BasePage {
    
    // Локаторы страницы регистрации
    private final By nameInput = By.xpath("//fieldset[1]//input[@name='name']");
    private final By emailInput = By.xpath("//fieldset[2]//input[@name='name']");
    private final By passwordInput = By.xpath("//input[@name='Пароль']");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final By passwordError = By.xpath("//p[contains(@class, 'input__error')]");
    
    public RegisterPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Ввести имя: {name}")
    public void enterName(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(nameInput));
        driver.findElement(nameInput).sendKeys(name);
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
    
    @Step("Нажать на кнопку 'Зарегистрироваться'")
    public void clickRegisterButton() {
        wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        driver.findElement(registerButton).click();
    }
    
    @Step("Нажать на ссылку 'Войти'")
    public void clickLoginLink() {
        wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        driver.findElement(loginLink).click();
    }
    
    @Step("Регистрация пользователя: имя={name}, email={email}, пароль={password}")
    public void register(String name, String email, String password) {
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        clickRegisterButton();
    }
    
    @Step("Получить текст ошибки пароля")
    public String getPasswordErrorText() {
        wait.until(ExpectedConditions.presenceOfElementLocated(passwordError));
        return driver.findElement(passwordError).getText();
    }
    
    @Step("Проверить наличие ошибки пароля")
    public boolean isPasswordErrorDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(passwordError));
            return driver.findElement(passwordError).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
