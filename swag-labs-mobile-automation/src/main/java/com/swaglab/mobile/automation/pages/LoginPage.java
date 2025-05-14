package com.swaglab.mobile.automation.pages;

import com.swaglab.mobile.automation.core.BasePage;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage extends BasePage {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    // Mapeamento dos elementos da tela de login
    @AndroidFindBy(accessibility = "test-Username")
    private WebElement usernameField;

    @AndroidFindBy(accessibility = "test-Password")
    private WebElement passwordField;

    @AndroidFindBy(accessibility = "test-LOGIN")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView")
    private WebElement errorMessage;
    
    @AndroidFindBy(accessibility = "test-standard_user")
    private WebElement standardUserOption;
    
    @AndroidFindBy(accessibility = "test-locked_out_user")
    private WebElement lockedOutUserOption;
    
    @AndroidFindBy(accessibility = "test-problem_user")
    private WebElement problemUserOption;
    
    @AndroidFindBy(accessibility = "test-performance_glitch_user")
    private WebElement performanceGlitchUserOption;
    
    @AndroidFindBy(accessibility = "test-eye")
    private WebElement visibilityToggle;
    
    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='test-Password' and @password='false']")
    private WebElement visiblePasswordField;

    /**
     * Verifica se a página de login está carregada
     * @return true se a página estiver carregada
     */
    public boolean isPageLoaded() {
        LOGGER.info("Verificando se a página de login está carregada");
        return isElementDisplayed(usernameField) && isElementDisplayed(passwordField) && isElementDisplayed(loginButton);
    }

    /**
     * Insere o nome de usuário no campo
     * @param username nome de usuário
     */
    public void inputUsername(String username) {
        LOGGER.info("Inserindo nome de usuário: {}", username);
        sendKeys(usernameField, username);
    }

    /**
     * Insere a senha no campo
     * @param password senha
     */
    public void inputPassword(String password) {
        LOGGER.info("Inserindo senha");
        sendKeys(passwordField, password);
    }

    /**
     * Clica no botão de login
     */
    public void clickLoginButton() {
        LOGGER.info("Clicando no botão Login");
        click(loginButton);
    }

    /**
     * Realiza o login completo
     * @param username nome de usuário
     * @param password senha
     */
    public void login(String username, String password) {
        inputUsername(username);
        inputPassword(password);
        clickLoginButton();
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
     * Seleciona um usuário pré-definido (se disponível na interface)
     * @param userType tipo de usuário (standard, locked_out, problem, performance_glitch)
     */
    public void selectPredefinedUser(String userType) {
        LOGGER.info("Selecionando usuário pré-definido: {}", userType);
        switch (userType) {
            case "standard":
                if (isElementDisplayed(standardUserOption)) {
                    click(standardUserOption);
                }
                break;
            case "locked_out":
                if (isElementDisplayed(lockedOutUserOption)) {
                    click(lockedOutUserOption);
                }
                break;
            case "problem":
                if (isElementDisplayed(problemUserOption)) {
                    click(problemUserOption);
                }
                break;
            case "performance_glitch":
                if (isElementDisplayed(performanceGlitchUserOption)) {
                    click(performanceGlitchUserOption);
                }
                break;
            default:
                LOGGER.warn("Tipo de usuário não reconhecido: {}", userType);
        }
    }
    
    /**
     * Clica no ícone para mostrar/ocultar a senha
     */
    public void togglePasswordVisibility() {
        LOGGER.info("Alternando visibilidade da senha");
        if (isElementDisplayed(visibilityToggle)) {
            click(visibilityToggle);
        } else {
            LOGGER.warn("Ícone de visibilidade não encontrado");
        }
    }
    
    /**
     * Verifica se a senha está visível
     * @return true se a senha estiver visível
     */
    public boolean isPasswordVisible() {
        LOGGER.info("Verificando se a senha está visível");
        try {
            return isElementDisplayed(visiblePasswordField);
        } catch (Exception e) {
            LOGGER.info("Campo de senha visível não encontrado");
            return false;
        }
    }
    
    /**
     * Limpa os campos de login
     */
    public void clearFields() {
        LOGGER.info("Limpando campos de login");
        clearElement(usernameField);
        clearElement(passwordField);
    }
}