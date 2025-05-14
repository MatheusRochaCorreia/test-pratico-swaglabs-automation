package com.swaglab.mobile.automation.pages;

import com.swaglab.mobile.automation.core.BasePage;

import io.appium.java_client.MobileBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CheckoutPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutPage.class);

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='CHECKOUT: INFORMATION']")
    private WebElement checkoutInfoTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='CHECKOUT: OVERVIEW']")
    private WebElement checkoutOverviewTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='THANK YOU FOR YOU ORDER']")
    private WebElement checkoutCompleteTitle;

    @AndroidFindBy(accessibility = "test-First Name")
    private WebElement firstNameField;

    @AndroidFindBy(accessibility = "test-Last Name")
    private WebElement lastNameField;

    @AndroidFindBy(accessibility = "test-Zip/Postal Code")
    private WebElement postalCodeField;

    @AndroidFindBy(accessibility = "test-CONTINUE")
    private WebElement continueButton;

    @AndroidFindBy(accessibility = "test-CANCEL")
    private WebElement cancelButton;

    @AndroidFindBy(accessibility = "test-FINISH")
    private WebElement finishButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView")
    private WebElement errorMessage;

    @AndroidFindBy(xpath = "//android.widget.ScrollView[@content-desc=\"test-CHECKOUT: COMPLETE!\"]/android.view.ViewGroup/android.widget.TextView[1]")
    private WebElement orderConfirmationMessage;

    @AndroidFindBy(accessibility = "test-Item")
    private List<WebElement> checkoutItems;

    /**
     * Verifica se a página de informações de checkout está carregada
     * @return true se a página estiver carregada
     */
    public boolean isCheckoutInfoPageLoaded() {
        LOGGER.info("Verificando se a página de informações de checkout está carregada");
        return isElementDisplayed(checkoutInfoTitle);
    }

    /**
     * Verifica se a página de revisão de checkout está carregada
     * @return true se a página estiver carregada
     */
    public boolean isCheckoutOverviewPageLoaded() {
        LOGGER.info("Verificando se a página de revisão de checkout está carregada");
        return isElementDisplayed(checkoutOverviewTitle);
    }

    /**
     * Verifica se a página de confirmação de checkout está carregada
     * @return true se a página estiver carregada
     */
    public boolean isCheckoutCompletePageLoaded() {
        LOGGER.info("Verificando se a página de confirmação de checkout está carregada");
        return isElementDisplayed(checkoutCompleteTitle);
    }

    /**
     * Preenche o campo de primeiro nome
     * @param firstName primeiro nome
     */
    public void inputFirstName(String firstName) {
        LOGGER.info("Preenchendo o campo de primeiro nome: {}", firstName);
        sendKeys(firstNameField, firstName);
    }

    /**
     * Preenche o campo de sobrenome
     * @param lastName sobrenome
     */
    public void inputLastName(String lastName) {
        LOGGER.info("Preenchendo o campo de sobrenome: {}", lastName);
        sendKeys(lastNameField, lastName);
    }

    /**
     * Preenche o campo de CEP
     * @param postalCode CEP
     */
    public void inputPostalCode(String postalCode) {
        LOGGER.info("Preenchendo o campo de CEP: {}", postalCode);
        sendKeys(postalCodeField, postalCode);
    }

    /**
     * Clica no botão Continue
     */
    public void clickContinue() {
        LOGGER.info("Clicando no botão Continue");
        click(continueButton);
    }

    /**
     * Clica no botão Cancel
     */
    public void clickCancel() {
        LOGGER.info("Clicando no botão Cancel");
        click(cancelButton);
    }

    /**
     * Clica no botão Finish
     */
    public void clickFinish() {
        LOGGER.info("Clicando no botão Finish com scroll");

        WebElement finishButton = driver.findElement(
            MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().text(\"FINISH\"))"
            )
        );

        finishButton.click();
    }

    /**
     * Verifica se existe mensagem de erro
     * @return true se houver mensagem de erro
     */
    public boolean hasErrorMessage() {
        LOGGER.info("Verificando se existe mensagem de erro");
        return isElementDisplayed(errorMessage);
    }

    /**
     * Obtém o texto da mensagem de erro
     * @return texto da mensagem de erro
     */
    public String getErrorMessage() {
        LOGGER.info("Obtendo texto da mensagem de erro");
        return getText(errorMessage);
    }

    /**
     * Verifica se a mensagem de confirmação de pedido está sendo exibida
     * @return true se a mensagem estiver sendo exibida
     */
    public boolean isOrderConfirmationDisplayed() {
        return isElementDisplayed(orderConfirmationMessage);
    }

    /**
     * Preenche todos os campos do formulário de checkout
     * @param firstName primeiro nome
     * @param lastName sobrenome
     * @param postalCode CEP
     */
    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        LOGGER.info("Preenchendo formulário de checkout: {}, {}, {}", firstName, lastName, postalCode);
        inputFirstName(firstName);
        inputLastName(lastName);
        inputPostalCode(postalCode);
    }

    /**
     * Obtém a quantidade de itens na revisão do pedido
     * @return quantidade de itens
     */
    public int getCheckoutItemCount() {
        LOGGER.info("Obtendo quantidade de itens na revisão do pedido: {}", checkoutItems.size());
        return checkoutItems.size();
    }
}