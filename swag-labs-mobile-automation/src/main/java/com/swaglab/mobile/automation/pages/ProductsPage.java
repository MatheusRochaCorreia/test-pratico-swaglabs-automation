package com.swaglab.mobile.automation.pages;

import com.swaglab.mobile.automation.core.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ProductsPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsPage.class);

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart']")
    private WebElement cartIcon;
    
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart']/android.view.ViewGroup/android.widget.TextView")
    private WebElement cartBadge;

    @AndroidFindBy(accessibility = "test-Menu")
    private WebElement menuButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='PRODUCTS']")
    private WebElement productsTitle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Item']")
    private List<WebElement> productItems;

    /**
     * Verifica se a página de produtos está carregada
     * @return true se a página estiver carregada
     */
    public boolean isPageLoaded() {
        LOGGER.info("Verificando se a página de produtos está carregada");
        return isElementDisplayed(productsTitle) && isElementDisplayed(cartIcon) && isElementDisplayed(menuButton);
    }

    /**
     * Verifica se existem produtos listados
     * @return true se houver produtos
     */
    public boolean hasProducts() {
        LOGGER.info("Verificando se existem produtos listados");
        return !productItems.isEmpty();
    }

    /**
     * Abre o menu lateral
     */
    public void openMenu() {
        LOGGER.info("Abrindo menu lateral");
        click(menuButton);
    }

    /**
     * Obtém a quantidade de produtos exibidos
     * @return quantidade de produtos
     */
    public int getProductCount() {
        LOGGER.info("Obtendo quantidade de produtos: {}", productItems.size());
        return productItems.size();
    }
    
    /**
     * Clica no ícone do carrinho
     */
    public void openCart() {
        LOGGER.info("Clicando no ícone do carrinho");
        click(cartIcon);
        // Adicionando um pequeno tempo de espera para a transição da tela
        waitForSeconds(1);
    }
    
    /**
     * Obtém o número de itens no contador do carrinho
     * @return número de itens ou 0 se o contador não estiver visível
     */
    public int getCartBadgeCount() {
        try {
            if (isElementDisplayed(cartBadge)) {
                String countText = getText(cartBadge);
                LOGGER.info("Contador do carrinho: {}", countText);
                return Integer.parseInt(countText);
            } else {
                LOGGER.info("Contador do carrinho não está visível");
                return 0;
            }
        } catch (Exception e) {
            LOGGER.warn("Erro ao obter contador do carrinho: {}", e.getMessage());
            return 0;
        }
    }
    
    /**
     * Adiciona um produto ao carrinho pelo índice
     * @param index índice do produto (0-based)
     */
    public void addProductToCartByIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            LOGGER.info("Adicionando produto ao carrinho no índice: {}", index);
            try {
                WebElement addButton = productItems.get(index).findElement(
                    By.xpath(".//android.view.ViewGroup[@content-desc='test-ADD TO CART']"));
                click(addButton);
                // Aguardar a atualização da UI
                waitForSeconds(1);
            } catch (Exception e) {
                LOGGER.error("Erro ao adicionar produto ao carrinho: {}", e.getMessage());
                // Tenta uma abordagem alternativa
                try {
                    WebElement addButton = driver.findElement(
                        By.xpath("(//android.view.ViewGroup[@content-desc='test-ADD TO CART'])[" + (index + 1) + "]"));
                    click(addButton);
                    waitForSeconds(1);
                } catch (Exception ex) {
                    LOGGER.error("Falha completa ao adicionar produto: {}", ex.getMessage());
                }
            }
        } else {
            LOGGER.warn("Índice inválido para adicionar produto: {}", index);
        }
    }
    
    /**
     * Remove um produto do carrinho pelo índice na lista de produtos
     * @param index índice do produto (0-based)
     */
    public void removeProductFromCartByIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            LOGGER.info("Removendo produto do carrinho no índice: {}", index);
            try {
                WebElement removeButton = productItems.get(index).findElement(
                    By.xpath(".//android.view.ViewGroup[@content-desc='test-REMOVE']"));
                click(removeButton);
                // Aguardar a atualização da UI
                waitForSeconds(1);
            } catch (Exception e) {
                LOGGER.error("Erro ao remover produto do carrinho: {}", e.getMessage());
                // Tenta uma abordagem alternativa
                try {
                    WebElement removeButton = driver.findElement(
                        By.xpath("(//android.view.ViewGroup[@content-desc='test-REMOVE'])[" + (index + 1) + "]"));
                    click(removeButton);
                    waitForSeconds(1);
                } catch (Exception ex) {
                    LOGGER.error("Falha completa ao remover produto: {}", ex.getMessage());
                }
            }
        } else {
            LOGGER.warn("Índice inválido para remover produto: {}", index);
        }
    }
    
    /**
     * Verifica se o botão de um produto está como "ADD TO CART" ou "REMOVE"
     * @param index índice do produto (0-based)
     * @return texto do botão ("ADD TO CART" ou "REMOVE")
     */
    public String getProductButtonTextByIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            LOGGER.info("Verificando texto do botão do produto no índice: {}", index);
            // Primeiro tenta encontrar o botão ADD TO CART
            try {
                productItems.get(index).findElement(By.xpath(".//android.view.ViewGroup[@content-desc='test-ADD TO CART']"));
                return "ADD TO CART";
            } catch (Exception e) {
                // Se não encontrar, provavelmente é o botão REMOVE
                try {
                    productItems.get(index).findElement(By.xpath(".//android.view.ViewGroup[@content-desc='test-REMOVE']"));
                    return "REMOVE";
                } catch (Exception ex) {
                    LOGGER.warn("Não foi possível determinar o texto do botão do produto");
                    return "UNKNOWN";
                }
            }
        }
        LOGGER.warn("Índice inválido para verificar botão do produto: {}", index);
        return "UNKNOWN";
    }
    
    /**
     * Obtém o nome de um produto pelo índice
     * @param index índice do produto (0-based)
     * @return nome do produto
     */
    public String getProductNameByIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            LOGGER.info("Obtendo nome do produto no índice: {}", index);
            WebElement nameElement = productItems.get(index).findElement(By.xpath(".//android.widget.TextView[@content-desc='test-Item title']"));
            return getText(nameElement);
        }
        LOGGER.warn("Índice inválido para obter nome do produto: {}", index);
        return null;
    }
    
    /**
     * Obtém o preço de um produto pelo índice
     * @param index índice do produto (0-based)
     * @return preço do produto
     */
    public String getProductPriceByIndex(int index) {
        if (index >= 0 && index < productItems.size()) {
            LOGGER.info("Obtendo preço do produto no índice: {}", index);
            WebElement priceElement = productItems.get(index).findElement(By.xpath(".//android.widget.TextView[@content-desc='test-Price']"));
            return getText(priceElement);
        }
        LOGGER.warn("Índice inválido para obter preço do produto: {}", index);
        return null;
    }
    
    /**
     * Espera um número específico de segundos
     * @param seconds número de segundos para esperar
     */
    private void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Interrupção durante espera: {}", e.getMessage());
        }
    }
}