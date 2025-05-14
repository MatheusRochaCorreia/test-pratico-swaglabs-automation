package com.swaglab.mobile.automation.steps;

import com.swaglab.mobile.automation.pages.CartPage;
import com.swaglab.mobile.automation.pages.CheckoutPage;
import com.swaglab.mobile.automation.pages.LoginPage;
import com.swaglab.mobile.automation.pages.ProductsPage;
import com.swaglab.mobile.automation.utils.ReportUtils;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutSteps.class);
    
    private final LoginPage loginPage;
    private final ProductsPage productsPage;
    private final CartPage cartPage;
    private final CheckoutPage checkoutPage;
    private final ReportUtils reportUtils;
    
    public CheckoutSteps() {
        this.loginPage = new LoginPage();
        this.productsPage = new ProductsPage();
        this.cartPage = new CartPage();
        this.checkoutPage = new CheckoutPage();
        this.reportUtils = new ReportUtils();
    }
    
    @Dado("que estou logado com o usuário {string} e senha {string}")
    public void queEstouLogadoComOUsuarioESenha(String username, String password) {
        LOGGER.info("Realizando login com usuário: {} e senha: {}", username, password);
        
        if (loginPage.isPageLoaded()) {
            loginPage.login(username, password);
            Assertions.assertTrue(productsPage.isPageLoaded(), "A tela de produtos não foi carregada após o login");
            reportUtils.takeScreenshot("Login realizado com sucesso");
        } else {
            LOGGER.info("Já estou logado, continuando o teste");
        }
    }
    
    @E("adicionei um produto ao carrinho")
    public void adicioneiUmProdutoAoCarrinho() {
        LOGGER.info("Adicionando um produto ao carrinho");
        
        if (productsPage.getCartBadgeCount() == 0) {
            productsPage.addProductToCartByIndex(0);
            int cartCount = productsPage.getCartBadgeCount();
            Assertions.assertEquals(1, cartCount, "O produto não foi adicionado ao carrinho corretamente");
            reportUtils.takeScreenshot("Produto adicionado ao carrinho");
        } else {
            LOGGER.info("Já existe produto no carrinho, continuando o teste");
        }
    }
    
    @E("naveguei para a tela do carrinho")
    public void navegueiParaATelaDoCarrinho() {
        LOGGER.info("Navegando para a tela do carrinho");
        productsPage.openCart();
        reportUtils.takeScreenshot("Tela do carrinho carregada");
    }
    
    @Quando("tocar no botão {string}")
    public void tocarNoBotao(String botao) {
        LOGGER.info("Tocando no botão {}", botao);
        
        switch (botao) {
            case "CHECKOUT":
                cartPage.clickCheckout();
                break;
            case "CONTINUE":
                checkoutPage.clickContinue();
                break;
            case "FINISH":
                checkoutPage.clickFinish();
                break;
            default:
                LOGGER.warn("Botão não reconhecido: {}", botao);
        }
        
        reportUtils.takeScreenshot("Após clicar no botão " + botao);
    }
    
    @Então("devo ser direcionado para a tela de informações do checkout")
    public void devoSerDirecionadoParaATelaDeInformacoesDoCheckout() {
        LOGGER.info("Verificando se fui direcionado para a tela de informações do checkout");
        Assertions.assertTrue(checkoutPage.isCheckoutInfoPageLoaded(), 
                "A tela de informações do checkout não foi carregada");
        reportUtils.takeScreenshot("Tela de informações do checkout");
    }
    
    @Quando("preencher o primeiro nome como {string}")
    public void preencherOPrimeiroNomeComo(String primeiroNome) {
        LOGGER.info("Preenchendo o primeiro nome: {}", primeiroNome);
        checkoutPage.inputFirstName(primeiroNome);
        reportUtils.takeScreenshot("Primeiro nome preenchido");
    }
    
    @E("preencher o sobrenome como {string}")
    public void preencherOSobrenomeComo(String sobrenome) {
        LOGGER.info("Preenchendo o sobrenome: {}", sobrenome);
        checkoutPage.inputLastName(sobrenome);
        reportUtils.takeScreenshot("Sobrenome preenchido");
    }
    
    @E("preencher o CEP como {string}")
    public void preencherOCEPComo(String cep) {
        LOGGER.info("Preenchendo o CEP: {}", cep);
        checkoutPage.inputPostalCode(cep);
        reportUtils.takeScreenshot("CEP preenchido");
    }
    
    @Então("devo ser direcionado para a tela de revisão do pedido")
    public void devoSerDirecionadoParaATelaDeRevisaoDoPedido() {
        LOGGER.info("Verificando se fui direcionado para a tela de revisão do pedido");
        Assertions.assertTrue(checkoutPage.isCheckoutOverviewPageLoaded(), 
                "A tela de revisão do pedido não foi carregada");
        reportUtils.takeScreenshot("Tela de revisão do pedido");
    }
    
    @E("devo visualizar o item adicionado na revisão")
    public void devoVisualizarOItemAdicionadoNaRevisao() {
        LOGGER.info("Verificando se o item adicionado está na revisão do pedido");
        int itemCount = checkoutPage.getCheckoutItemCount();
        Assertions.assertTrue(itemCount > 0, "Não há itens na revisão do pedido");
        reportUtils.takeScreenshot("Item na revisão do pedido");
    }
    
    @Então("devo visualizar a confirmação de pedido concluído")
    public void devoVisualizarAConfirmacaoDePedidoConcluido() {
        LOGGER.info("Verificando se a confirmação de pedido concluído está sendo exibida");
        Assertions.assertTrue(checkoutPage.isCheckoutCompletePageLoaded(), 
                "A tela de confirmação de pedido não foi carregada");
        Assertions.assertTrue(checkoutPage.isOrderConfirmationDisplayed(), 
                "A mensagem de confirmação de pedido não está sendo exibida");
        reportUtils.takeScreenshot("Confirmação de pedido concluído");
    }
    
    @Então("devo visualizar a mensagem de erro de checkout {string}")
    public void devoVisualizarAMensagemDeErroDeCheckout(String mensagemEsperada) {
        LOGGER.info("Verificando se a mensagem de erro '{}' está sendo exibida", mensagemEsperada);
        Assertions.assertTrue(checkoutPage.hasErrorMessage(), "Não há mensagem de erro sendo exibida");
        String mensagemAtual = checkoutPage.getErrorMessage();
        Assertions.assertEquals(mensagemEsperada, mensagemAtual, 
                "A mensagem de erro não corresponde ao esperado");
        reportUtils.takeScreenshot("Mensagem de erro: " + mensagemAtual);
    }
}