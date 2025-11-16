package ru.yandex.practicum.stellarburgers.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class PersonalAccountPage extends BasePage {
    
    private final By logoutButton = By.xpath("//button[text()='Выход']");
    private final By profileLink = By.xpath("//a[contains(@class, 'Account_link') and text()='Профиль']");
    
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    private final By logoButton = By.xpath("//div[contains(@class, 'AppHeader')]//a[1] | //div[contains(@class, 'logo')] | //a[contains(@href, '/')][1]");
    
    public PersonalAccountPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Нажать на кнопку 'Выход'")
    public void clickLogoutButton() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.className("Modal_modal_overlay__x2ZCr")));
        } catch (Exception e) {
        }
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        driver.findElement(logoutButton).click();
    }
    
    @Step("Проверить, что пользователь находится на странице личного кабинета")
    public boolean isProfileLinkDisplayed() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(profileLink),
                    ExpectedConditions.presenceOfElementLocated(logoutButton)
            ));
            if (driver.findElements(profileLink).size() > 0) {
                return driver.findElement(profileLink).isDisplayed();
            }
            return driver.findElements(logoutButton).size() > 0 &&
                   driver.findElement(logoutButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    @Step("Нажать на кнопку 'Конструктор' в хедере")
    public void clickConstructorButton() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        driver.findElement(constructorButton).click();
    }
    
    @Step("Нажать на логотип Stellar Burgers в хедере")
    public void clickLogoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoButton));
        driver.findElement(logoButton).click();
    }
}
