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
import io.qameta.allure.junit4.DisplayName;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class RegistrationTest extends BaseTest {
    
    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][] {
            {Config.Browser.CHROME},
            {Config.Browser.YANDEX}
        });
    }
    
    public RegistrationTest(Config.Browser browser) {
        super(browser);
    }
    
    @Test
    @DisplayName("Успешная регистрация нового пользователя")
    public void testSuccessfulRegistration() {
        String email = "test_" + UUID.randomUUID().toString() + "@test.ru";
        String password = "password123";
        String name = "Test User";
        
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();
        
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(name, email, password);
        
        LoginPage loginPageAfterRegister = new LoginPage(driver);
        loginPageAfterRegister.login(email, password);
        
        HomePage homePageAfterLogin = new HomePage(driver);
        homePageAfterLogin.clickPersonalAccountButton();
        
        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        assertTrue("Пользователь должен быть авторизован", personalAccountPage.isProfileLinkDisplayed());
        
        String accessToken = UserApi.loginUser(email, password);
        if (accessToken != null) {
            UserApi.deleteUser(accessToken);
        }
    }
    
    @Test
    @DisplayName("Ошибка при регистрации с некорректным паролем (менее 6 символов)")
    public void testRegistrationWithInvalidPassword() {
        String email = "test_" + UUID.randomUUID().toString() + "@test.ru";
        String password = "12345";
        String name = "Test User";
        
        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.clickLoginAccountButton();
        
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegisterLink();
        
        RegisterPage registerPage = new RegisterPage(driver);
        registerPage.register(name, email, password);
        
        assertTrue("Должна отображаться ошибка пароля", registerPage.isPasswordErrorDisplayed());
        String errorText = registerPage.getPasswordErrorText();
        assertTrue("Текст ошибки должен содержать информацию о минимальной длине пароля", 
                   errorText.contains("шесть") || errorText.contains("6") || errorText.toLowerCase().contains("минимальн"));
    }
}
