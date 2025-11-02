package ru.yandex.practicum.stellarburgers.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.yandex.practicum.stellarburgers.config.Config;
import io.qameta.allure.Step;

public class HomePage extends BasePage {
    
    // Локаторы главной страницы
    private final By loginAccountButton = By.xpath("//button[text()='Войти в аккаунт']");
    private final By personalAccountButton = By.xpath("//p[text()='Личный кабинет']");
    private final By constructorButton = By.xpath("//p[text()='Конструктор']");
    private final By logoButton = By.xpath("//div[contains(@class, 'AppHeader')]//a[1] | //div[contains(@class, 'logo')] | //a[contains(@href, '/')][1]");
    
    // Локаторы конструктора
    private final By bunsSectionButton = By.xpath("//span[text()='Булки']");
    private final By saucesSectionButton = By.xpath("//span[text()='Соусы']");
    private final By fillingsSectionButton = By.xpath("//span[text()='Начинки']");
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Открыть главную страницу")
    public void open() {
        open(Config.BASE_URL);
    }
    
    @Step("Нажать на кнопку 'Войти в аккаунт'")
    public void clickLoginAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginAccountButton));
        driver.findElement(loginAccountButton).click();
    }
    
    @Step("Нажать на кнопку 'Личный кабинет'")
    public void clickPersonalAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(personalAccountButton));
        driver.findElement(personalAccountButton).click();
    }
    
    @Step("Нажать на кнопку 'Конструктор'")
    public void clickConstructorButton() {
        wait.until(ExpectedConditions.elementToBeClickable(constructorButton));
        driver.findElement(constructorButton).click();
    }
    
    @Step("Нажать на логотип Stellar Burgers")
    public void clickLogoButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoButton));
        driver.findElement(logoButton).click();
    }
    
    @Step("Нажать на раздел 'Булки'")
    public void clickBunsSection() {
        wait.until(ExpectedConditions.elementToBeClickable(bunsSectionButton));
        driver.findElement(bunsSectionButton).click();
    }
    
    @Step("Нажать на раздел 'Соусы'")
    public void clickSaucesSection() {
        wait.until(ExpectedConditions.elementToBeClickable(saucesSectionButton));
        driver.findElement(saucesSectionButton).click();
    }
    
    @Step("Нажать на раздел 'Начинки'")
    public void clickFillingsSection() {
        wait.until(ExpectedConditions.elementToBeClickable(fillingsSectionButton));
        driver.findElement(fillingsSectionButton).click();
    }
    
    @Step("Проверить, что активен раздел 'Булки'")
    public boolean isBunsSectionActive() {
        wait.until(ExpectedConditions.presenceOfElementLocated(bunsSectionButton));
        WebElement bunsElement = driver.findElement(bunsSectionButton);
        String classAttribute = bunsElement.getAttribute("class");
        WebElement parent = bunsElement.findElement(By.xpath("./parent::div"));
        String parentClass = parent.getAttribute("class");
        return classAttribute != null && classAttribute.contains("current") || 
               parentClass != null && parentClass.contains("current");
    }
    
    @Step("Проверить, что активен раздел 'Соусы'")
    public boolean isSaucesSectionActive() {
        wait.until(ExpectedConditions.presenceOfElementLocated(saucesSectionButton));
        WebElement saucesElement = driver.findElement(saucesSectionButton);
        String classAttribute = saucesElement.getAttribute("class");
        WebElement parent = saucesElement.findElement(By.xpath("./parent::div"));
        String parentClass = parent.getAttribute("class");
        return classAttribute != null && classAttribute.contains("current") || 
               parentClass != null && parentClass.contains("current");
    }
    
    @Step("Проверить, что активен раздел 'Начинки'")
    public boolean isFillingsSectionActive() {
        wait.until(ExpectedConditions.presenceOfElementLocated(fillingsSectionButton));
        WebElement fillingsElement = driver.findElement(fillingsSectionButton);
        String classAttribute = fillingsElement.getAttribute("class");
        WebElement parent = fillingsElement.findElement(By.xpath("./parent::div"));
        String parentClass = parent.getAttribute("class");
        return classAttribute != null && classAttribute.contains("current") || 
               parentClass != null && parentClass.contains("current");
    }
}
