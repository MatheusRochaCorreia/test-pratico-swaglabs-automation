package com.swaglab.mobile.automation.pages;

import com.swaglab.mobile.automation.core.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuPage.class);

    @AndroidFindBy(accessibility = "test-LOGOUT")
    private WebElement logoutButton;

    @AndroidFindBy(accessibility = "test-ALL ITEMS")
    private WebElement allItemsButton;

    @AndroidFindBy(accessibility = "test-ABOUT")
    private WebElement aboutButton;

    @AndroidFindBy(accessibility = "test-RESET APP STATE")
    private WebElement resetAppStateButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Close']/android.widget.ImageView")
    private WebElement closeButton;

    /**
     * Verifica se o menu está aberto
     * @return true se o menu estiver aberto
     */
    public boolean isMenuOpen() {
        LOGGER.info("Verificando se o menu está aberto");
        return isElementDisplayed(logoutButton) && isElementDisplayed(closeButton);
    }

    /**
     * Realiza o logout
     */
    public void logout() {
        LOGGER.info("Realizando logout");
        click(logoutButton);
    }

    /**
     * Fecha o menu
     */
    public void closeMenu() {
        LOGGER.info("Fechando menu");
        click(closeButton);
    }
    
    /**
     * Navega para "All Items"
     */
    public void goToAllItems() {
        LOGGER.info("Navegando para All Items");
        click(allItemsButton);
    }
    
    /**
     * Navega para "About"
     */
    public void goToAbout() {
        LOGGER.info("Navegando para About");
        click(aboutButton);
    }
    
    /**
     * Reseta o estado do aplicativo
     */
    public void resetAppState() {
        LOGGER.info("Resetando estado do aplicativo");
        click(resetAppStateButton);
    }
}