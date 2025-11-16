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
public class LogoutTest extends BaseTest {
    
    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][] {
            {Config.Browser.CHROME},
            {Config.Browser.YANDEX}
        });
    }
    
    public LogoutTest(Config.Browser browser) {
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
    @DisplayName("Выход из аккаунта по кнопке 'Выйти' в личном кабинете")
    public void testLogout() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Должна открыться страница личного кабинета", personalAccountPage.isProfileLinkDisplayed());
        
        personalAccountPage.clickLogoutButton();
        
        LoginPage loginPageAfterLogout = new LoginPage(driver);
        // Проверяем, что открылась страница входа
        assertTrue("После выхода должна открыться страница входа", loginPageAfterLogout.isLoginPageDisplayed());
    }
}
