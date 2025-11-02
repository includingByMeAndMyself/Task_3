package ru.yandex.practicum.stellarburgers.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellarburgers.api.UserApi;
import ru.yandex.practicum.stellarburgers.config.Config;
import ru.yandex.practicum.stellarburgers.pageobject.HomePage;
import ru.yandex.practicum.stellarburgers.pageobject.LoginPage;
import ru.yandex.practicum.stellarburgers.pageobject.PersonalAccountPage;
import io.qameta.allure.junit4.DisplayName;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NavigationTest extends BaseTest {
    
    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][] {
            {Config.Browser.CHROME},
            {Config.Browser.YANDEX}
        });
    }
    
    public NavigationTest(Config.Browser browser) {
        super(browser);
    }
    
    private String email;
    private String password;
    private String name;
    private String accessToken;
    
    @org.junit.Before
    public void createTestUser() {
        email = "test_" + UUID.randomUUID().toString() + "@test.ru";
        password = "password123";
        name = "Test User";
        
        UserApi.createUser(email, password, name);
        accessToken = UserApi.loginUser(email, password);
    }
    
    @org.junit.After
    public void deleteTestUser() {
        if (accessToken != null) {
            UserApi.deleteUser(accessToken);
        }
    }
    
    @Test
    @DisplayName("Переход в личный кабинет по клику на 'Личный кабинет'")
    public void testNavigateToPersonalAccount() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Должна открыться страница личного кабинета", personalAccountPage.isProfileLinkDisplayed());
    }
    
    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на 'Конструктор'")
    public void testNavigateFromPersonalAccountToConstructor() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Должна открыться страница личного кабинета", personalAccountPage.isProfileLinkDisplayed());
        
        HomePage homePageFromAccount = new HomePage(driver);
        homePageFromAccount.clickConstructorButton();
        
        HomePage constructorPage = new HomePage(driver);
        assertTrue("Должна открыться главная страница конструктора", constructorPage.isBunsSectionActive());
    }
    
    @Test
    @DisplayName("Переход из личного кабинета в конструктор по клику на логотип Stellar Burgers")
    public void testNavigateFromPersonalAccountToConstructorByLogo() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Должна открыться страница личного кабинета", personalAccountPage.isProfileLinkDisplayed());
        
        HomePage homePageFromAccount = new HomePage(driver);
        homePageFromAccount.clickLogoButton();
        
        HomePage constructorPage = new HomePage(driver);
        assertTrue("Должна открыться главная страница конструктора", constructorPage.isBunsSectionActive());
    }
}
