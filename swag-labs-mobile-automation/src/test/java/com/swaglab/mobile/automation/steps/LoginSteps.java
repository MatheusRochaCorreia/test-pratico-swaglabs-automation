package com.swaglab.mobile.automation.steps;

import com.swaglab.mobile.automation.pages.LoginPage;
import com.swaglab.mobile.automation.pages.MenuPage;
import com.swaglab.mobile.automation.pages.ProductsPage;
import com.swaglab.mobile.automation.utils.ReportUtils;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginSteps.class);
    
    private final LoginPage loginPage;
    private final ProductsPage productsPage;
    private final MenuPage menuPage;
    private final ReportUtils reportUtils;
    
    public LoginSteps() {
        this.loginPage = new LoginPage();
        this.productsPage = new ProductsPage();
        this.menuPage = new MenuPage();
        this.reportUtils = new ReportUtils();
    }

    @Dado("que estou na tela de login do aplicativo SwagLabs Mobile")
    public void queEstouNaTelaDeLoginDoAplicativoSwagLabsMobile() {
        LOGGER.info("Verificando se estou na tela de login");
        Assertions.assertTrue(loginPage.isPageLoaded(), "A tela de login não foi carregada");
        reportUtils.takeScreenshot("Tela de Login carregada");
    }

    @Quando("inserir o usuário {string}")
    public void inserirOUsuario(String username) {
        LOGGER.info("Inserindo usuário: {}", username);
        loginPage.inputUsername(username);
        reportUtils.takeScreenshot("Usuário '" + username + "' inserido");
    }

    @E("inserir a senha {string}")
    public void inserirASenha(String password) {
        LOGGER.info("Inserindo senha");
        loginPage.inputPassword(password);
        reportUtils.takeScreenshot("Senha inserida");
    }

    @E("tocar no botão Login")
    public void tocarNoBotaoLogin() {
        LOGGER.info("Clicando no botão Login");
        reportUtils.takeScreenshot("Antes de clicar no botão Login");
        loginPage.clickLoginButton();
        // Pequena pausa para garantir a transição de tela
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Então("devo ser redirecionado para a tela inicial do aplicativo")
    public void devoSerRedirecionadoParaATelaInicialDoAplicativo() {
        LOGGER.info("Verificando se fui redirecionado para a tela inicial");
        Assertions.assertTrue(productsPage.isPageLoaded(), "A tela de produtos não foi carregada após o login");
        reportUtils.takeScreenshot("Tela inicial carregada com sucesso");
    }

    @E("devo visualizar os produtos disponíveis")
    public void devoVisualizarOsProdutosDisponiveis() {
        LOGGER.info("Verificando se existem produtos disponíveis");
        Assertions.assertTrue(productsPage.hasProducts(), "Não foram encontrados produtos na tela principal");
        int productCount = productsPage.getProductCount();
        LOGGER.info("Quantidade de produtos encontrados: {}", productCount);
        reportUtils.takeScreenshot("Produtos listados (" + productCount + " produtos encontrados)");
    }
    
    @Então("devo visualizar a mensagem de erro {string}")
    public void devoVisualizarAMensagemDeErro(String mensagemEsperada) {
        LOGGER.info("Verificando mensagem de erro: {}", mensagemEsperada);
        Assertions.assertTrue(loginPage.hasErrorMessage(), "Não foi exibida mensagem de erro");
        String mensagemAtual = loginPage.getErrorMessage();
        Assertions.assertEquals(mensagemEsperada, mensagemAtual, "A mensagem de erro não corresponde ao esperado");
        reportUtils.takeScreenshot("Mensagem de erro: '" + mensagemAtual + "'");
    }
    
    @E("tocar no ícone de visualização de senha")
    public void tocarNoIconeDeVisualizacaoDeSenha() {
        LOGGER.info("Clicando no ícone de visualização de senha");
        loginPage.togglePasswordVisibility();
        reportUtils.takeScreenshot("Após clicar no ícone de visualização de senha");
    }
    
    @Então("a senha deve ficar visível")
    public void aSenhaDeveFicarVisivel() {
        LOGGER.info("Verificando se a senha está visível");
        boolean senhaVisivel = loginPage.isPasswordVisible();
        Assertions.assertTrue(senhaVisivel, "A senha não ficou visível após clicar no ícone");
        reportUtils.takeScreenshot("Senha visível");
    }
    
    @Então("a senha deve ficar oculta")
    public void aSenhaDeveFicarOculta() {
        LOGGER.info("Verificando se a senha está oculta");
        boolean senhaVisivel = loginPage.isPasswordVisible();
        Assertions.assertFalse(senhaVisivel, "A senha ainda está visível após clicar no ícone novamente");
        reportUtils.takeScreenshot("Senha oculta");
    }
    
    @Quando("tocar no menu lateral")
    public void tocarNoMenuLateral() {
        LOGGER.info("Tocando no menu lateral");
        productsPage.openMenu();
        reportUtils.takeScreenshot("Menu lateral aberto");
        Assertions.assertTrue(menuPage.isMenuOpen(), "O menu lateral não foi aberto");
    }
    
    @E("selecionar a opção de Logout")
    public void selecionarAOpcaoDeLogout() {
        LOGGER.info("Selecionando opção de Logout");
        menuPage.logout();
        reportUtils.takeScreenshot("Após clicar em Logout");
    }
    
    @Então("devo retornar para a tela de login")
    public void devoRetornarParaATelaDeLogin() {
        LOGGER.info("Verificando se retornou para a tela de login");
        Assertions.assertTrue(loginPage.isPageLoaded(), "Não retornou para a tela de login após o logout");
        reportUtils.takeScreenshot("Tela de login após logout");
    }
}