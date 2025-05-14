package com.swaglab.mobile.automation.pages;

import com.swaglab.mobile.automation.core.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CartPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartPage.class);

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='YOUR CART']")
    private WebElement cartTitle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart Content']")
    private WebElement cartContent;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Item']")
    private List<WebElement> cartItems;

    @AndroidFindBy(accessibility = "test-CHECKOUT")
    private WebElement checkoutButton;

    @AndroidFindBy(accessibility = "test-CONTINUE SHOPPING")
    private WebElement continueShoppingButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='No Items']")
    private WebElement noItemsMessage;

    /**
     * Verifica se a página do carrinho está carregada
     * @return true se a página estiver carregada
     */
    public boolean isPageLoaded() {
        LOGGER.info("Verificando se a página do carrinho está carregada");
        return isElementDisplayed(cartTitle) && isElementDisplayed(cartContent);
    }

    /**
     * Verifica se o carrinho está vazio
     * @return true se o carrinho estiver vazio
     */
    public boolean isCartEmpty() {
        LOGGER.info("Verificando se o carrinho está vazio");
        return isElementDisplayed(noItemsMessage);
    }

    /**
     * Verifica se a mensagem "No Items" está sendo exibida
     * @return true se a mensagem estiver sendo exibida
     */
    public boolean isNoItemsMessageDisplayed() {
        LOGGER.info("Verificando se a mensagem 'No Items' está sendo exibida");
        return isElementDisplayed(noItemsMessage);
    }

    /**
     * Obtém a quantidade de itens no carrinho
     * @return quantidade de itens
     */
    public int getItemCount() {
        LOGGER.info("Obtendo quantidade de itens no carrinho: {}", cartItems.size());
        return cartItems.size();
    }

    /**
     * Remove um item do carrinho pelo índice
     * @param index índice do item (0-based)
     */
    public void removeItemByIndex(int index) {
        if (index >= 0 && index < cartItems.size()) {
            LOGGER.info("Removendo item do carrinho no índice: {}", index);
            WebElement removeButton = cartItems.get(index).findElement(
                    By.xpath(".//android.view.ViewGroup[@content-desc='test-REMOVE']"));
            click(removeButton);
        } else {
            LOGGER.warn("Índice inválido para remover item: {}", index);
        }
    }

    /**
     * Clica no botão Checkout
     */
    public void clickCheckout() {
        LOGGER.info("Clicando no botão Checkout");
        click(checkoutButton);
    }

    /**
     * Clica no botão Continue Shopping
     */
    public void clickContinueShopping() {
        LOGGER.info("Clicando no botão Continue Shopping");
        click(continueShoppingButton);
    }

    /**
     * Obtém o nome de um item pelo índice
     * @param index índice do item (0-based)
     * @return nome do item
     */
    public String getItemNameByIndex(int index) {
        if (index >= 0 && index < cartItems.size()) {
            LOGGER.info("Obtendo nome do item no índice: {}", index);
            WebElement nameElement = cartItems.get(index).findElement(
                    By.xpath(".//android.widget.TextView[@content-desc='test-Item title']"));
            return getText(nameElement);
        }
        LOGGER.warn("Índice inválido para obter nome do item: {}", index);
        return null;
    }

    /**
     * Obtém o preço de um item pelo índice
     * @param index índice do item (0-based)
     * @return preço do item
     */
    public String getItemPriceByIndex(int index) {
        if (index >= 0 && index < cartItems.size()) {
            LOGGER.info("Obtendo preço do item no índice: {}", index);
            WebElement priceElement = cartItems.get(index).findElement(
                    By.xpath(".//android.widget.TextView[@content-desc='test-Price']"));
            return getText(priceElement);
        }
        LOGGER.warn("Índice inválido para obter preço do item: {}", index);
        return null;
    }

    /**
     * Verifica se um item específico está no carrinho pelo nome
     * @param itemName nome do item
     * @return true se o item estiver no carrinho
     */
    public boolean isItemInCart(String itemName) {
        LOGGER.info("Verificando se o item '{}' está no carrinho", itemName);
        for (int i = 0; i < cartItems.size(); i++) {
            String name = getItemNameByIndex(i);
            if (itemName.equals(name)) {
                LOGGER.info("Item '{}' encontrado no carrinho", itemName);
                return true;
            }
        }
        LOGGER.info("Item '{}' não foi encontrado no carrinho", itemName);
        return false;
    }

    /**
     * Obtém o índice de um item pelo nome
     * @param itemName nome do item
     * @return índice do item ou -1 se não encontrado
     */
    public int getItemIndexByName(String itemName) {
        LOGGER.info("Buscando índice do item '{}' no carrinho", itemName);
        for (int i = 0; i < cartItems.size(); i++) {
            String name = getItemNameByIndex(i);
            if (itemName.equals(name)) {
                LOGGER.info("Item '{}' encontrado no índice {}", itemName, i);
                return i;
            }
        }
        LOGGER.info("Item '{}' não foi encontrado no carrinho", itemName);
        return -1;
    }
    
    /**
     * Remove um item do carrinho pelo nome
     * @param itemName nome do item
     * @return true se o item foi removido com sucesso
     */
    public boolean removeItemByName(String itemName) {
        int index = getItemIndexByName(itemName);
        if (index >= 0) {
            removeItemByIndex(index);
            return true;
        }
        return false;
    }
}