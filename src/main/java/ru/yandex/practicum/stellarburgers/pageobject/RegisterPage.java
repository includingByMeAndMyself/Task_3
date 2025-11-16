package ru.yandex.practicum.stellarburgers.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.TimeoutException;
import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage extends BasePage {

    private final By nameInput = By.xpath("//fieldset[1]//input[@name='name']");
    private final By emailInput = By.xpath("//fieldset[2]//input[@name='name']"); // На странице регистрации email поле тоже имеет name='name'
    private final By passwordInput = By.xpath("//input[@name='Пароль']");        // Используем кириллицу для пароля
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final By passwordError = By.xpath("//p[contains(@class, 'input__error')]");

    private final By yandexBalloonOverlay = By.className("b_KlBalloonClass");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Дождаться исчезновения Yandex-оверлея (если есть)")
    private void waitForYandexBalloonToDisappear() {
        try {
            new WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                    .until(ExpectedConditions.invisibilityOfElementLocated(yandexBalloonOverlay));
        } catch (TimeoutException e) {
        }
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
        waitForYandexBalloonToDisappear();
        WebElement button = driver.findElement(registerButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
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
        try {
            WebElement passwordField = driver.findElement(passwordInput);
            driver.findElement(nameInput).click();
            Thread.sleep(500);
        } catch (Exception e) {
        }
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
            WebDriverWait errorWait = new WebDriverWait(driver, java.time.Duration.ofSeconds(3));
            errorWait.until(ExpectedConditions.presenceOfElementLocated(passwordError));
            WebElement errorElement = driver.findElement(passwordError);
            return errorElement.isDisplayed() && !errorElement.getText().isEmpty();
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(registerButton));
                By passwordFieldError = By.xpath("//input[@name='Пароль']/following-sibling::p[contains(@class, 'error')] | //input[@name='Пароль']/../p[contains(@class, 'error')] | //input[@name='Пароль']/ancestor::div[contains(@class, 'input')]//p[contains(@class, 'error')]");
                try {
                    WebDriverWait errorWait2 = new WebDriverWait(driver, java.time.Duration.ofSeconds(2));
                    errorWait2.until(ExpectedConditions.presenceOfElementLocated(passwordFieldError));
                    return driver.findElement(passwordFieldError).isDisplayed();
                } catch (Exception e2) {
                    return driver.findElements(passwordError).size() > 0;
                }
            } catch (Exception e2) {
                return false;
            }
        }
    }
}