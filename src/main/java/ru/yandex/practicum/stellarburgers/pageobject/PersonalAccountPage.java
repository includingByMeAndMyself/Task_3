package ru.yandex.practicum.stellarburgers.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.qameta.allure.Step;

public class PersonalAccountPage extends BasePage {
    
    // Локаторы страницы личного кабинета
    private final By logoutButton = By.xpath("//button[text()='Выход']");
    private final By profileLink = By.xpath("//a[contains(@class, 'Account_link') and text()='Профиль']");
    
    public PersonalAccountPage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Нажать на кнопку 'Выход'")
    public void clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
        driver.findElement(logoutButton).click();
    }
    
    @Step("Проверить, что пользователь находится на странице личного кабинета")
    public boolean isProfileLinkDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(profileLink));
            return driver.findElement(profileLink).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
