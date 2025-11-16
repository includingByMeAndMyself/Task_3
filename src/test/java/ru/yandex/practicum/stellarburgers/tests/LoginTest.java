package ru.yandex.practicum.stellarburgers.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellarburgers.api.UserApi;
import ru.yandex.practicum.stellarburgers.config.Config;
import ru.yandex.practicum.stellarburgers.pageobject.HomePage;
import ru.yandex.practicum.stellarburgers.pageobject.LoginPage;
import ru.yandex.practicum.stellarburgers.pageobject.PersonalAccountPage;
import ru.yandex.practicum.stellarburgers.pageobject.RegisterPage;
import ru.yandex.practicum.stellarburgers.pageobject.RecoverPasswordPage;
import io.qameta.allure.junit4.DisplayName;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class LoginTest extends BaseTest {
    
    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][] {
            {Config.Browser.CHROME},
            {Config.Browser.YANDEX}
        });
    }
    
    public LoginTest(Config.Browser browser) {
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
        
        // Создание пользователя через API
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
    @DisplayName("Вход через кнопку 'Войти в аккаунт' на главной странице")
    public void testLoginFromHomePageButton() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Пользователь должен быть авторизован", personalAccountPage.isProfileLinkDisplayed());
    }
    
    @Test
    @DisplayName("Вход через кнопку 'Личный кабинет' на главной странице")
    public void testLoginFromPersonalAccountButton() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickPersonalAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        
        // После логина нужно дождаться перехода на главную страницу
        HomePage homePageAfterLogin = new HomePage(driver);
        // Проверяем, что мы на главной странице (конструктор активен)
        assertTrue("После логина должна открыться главная страница", homePageAfterLogin.isBunsSectionActive());
        
        // Теперь переходим в личный кабинет
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Пользователь должен быть авторизован", personalAccountPage.isProfileLinkDisplayed());
    }
    
    @Test
    @DisplayName("Вход через кнопку 'Войти' в форме регистрации")
    public void testLoginFromRegisterPage() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();
        
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.clickLoginLink();
        
        LoginPage loginPageFromRegister = new LoginPage(driver);
        loginPageFromRegister.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Пользователь должен быть авторизован", personalAccountPage.isProfileLinkDisplayed());
    }
    
    @Test
    @DisplayName("Вход через кнопку 'Войти' в форме восстановления пароля")
    public void testLoginFromRecoverPasswordPage() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoverPasswordLink();
        
        RecoverPasswordPage recoverPasswordPage = new RecoverPasswordPage(driver);
        recoverPasswordPage.clickLoginLink();
        
        LoginPage loginPageFromRecover = new LoginPage(driver);
        loginPageFromRecover.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Пользователь должен быть авторизован", personalAccountPage.isProfileLinkDisplayed());
    }
}
