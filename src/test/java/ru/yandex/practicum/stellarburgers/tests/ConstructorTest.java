package ru.yandex.practicum.stellarburgers.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.stellarburgers.config.Config;
import ru.yandex.practicum.stellarburgers.pageobject.HomePage;
import io.qameta.allure.junit4.DisplayName;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ConstructorTest extends BaseTest {
    
    @Parameterized.Parameters(name = "Браузер: {0}")
    public static Collection<Object[]> browsers() {
        return Arrays.asList(new Object[][] {
            {Config.Browser.CHROME},
            {Config.Browser.YANDEX}
        });
    }
    
    public ConstructorTest(Config.Browser browser) {
        super(browser);
    }
    
    @Test
    @DisplayName("Переход к разделу 'Булки'")
    public void testNavigateToBunsSection() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        
        homePage.clickSaucesSection();
        homePage.clickBunsSection();
        
        assertTrue("Должен быть активен раздел 'Булки'", homePage.isBunsSectionActive());
    }
    
    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    public void testNavigateToSaucesSection() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        
        homePage.clickSaucesSection();
        
        assertTrue("Должен быть активен раздел 'Соусы'", homePage.isSaucesSectionActive());
    }
    
    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    public void testNavigateToFillingsSection() {
        HomePage homePage = new HomePage(driver);
        homePage.open();
        
        homePage.clickFillingsSection();
        
        assertTrue("Должен быть активен раздел 'Начинки'", homePage.isFillingsSectionActive());
    }
}
