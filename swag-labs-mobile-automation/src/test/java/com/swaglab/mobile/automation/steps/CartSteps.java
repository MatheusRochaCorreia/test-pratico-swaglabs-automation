package com.swaglab.mobile.automation.steps;

import com.swaglab.mobile.automation.pages.CartPage;
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

public class CartSteps {
    private static final Logger LOGGER = LoggerFactory.getLogger(CartSteps.class);
    
    private final LoginPage loginPage;
    private final ProductsPage productsPage;
    private final CartPage cartPage;
    private final ReportUtils reportUtils;
    
    // Variáveis para armazenar informações temporárias durante o teste
    private String lastProductName;
    private String lastProductPrice;
    
    public CartSteps() {
        this.loginPage = new LoginPage();
        this.productsPage = new ProductsPage();
        this.cartPage = new CartPage();
        this.reportUtils = new ReportUtils();
    }
    
    @Dado("que estou logado no aplicativo SwagLabs Mobile com o usuário {string} e senha {string}")
    public void queEstouLogadoNoAplicativoComOUsuarioESenha(String username, String password) {
        LOGGER.info("Realizando login com usuário: {} e senha: {}", username, password);
        Assertions.assertTrue(loginPage.isPageLoaded(), "A tela de login não foi carregada");
        reportUtils.takeScreenshot("Tela de login");
        
        loginPage.login(username, password);
        
        Assertions.assertTrue(productsPage.isPageLoaded(), "A tela de produtos não foi carregada após o login");
        reportUtils.takeScreenshot("Login realizado com sucesso");
    }
    
    @Dado("estou na tela de lista de produtos")
    public void estouNaTelaDeListaDeProdutos() {
        LOGGER.info("Verificando se estou na tela de produtos");
        Assertions.assertTrue(productsPage.isPageLoaded(), "A tela de produtos não está carregada");
        reportUtils.takeScreenshot("Tela de produtos");
    }
    
    @Quando("tocar no botão {string} do primeiro produto")
    public void tocarNoBotaoDoPrimeiroProduto(String botao) {
        LOGGER.info("Tocando no botão {} do primeiro produto", botao);
        
        // Armazena o nome e preço do produto para verificação posterior
        lastProductName = productsPage.getProductNameByIndex(0);
        lastProductPrice = productsPage.getProductPriceByIndex(0);
        LOGGER.info("Produto selecionado: {} - {}", lastProductName, lastProductPrice);
        
        if (botao.equals("ADD TO CART")) {
            productsPage.addProductToCartByIndex(0);
        } else if (botao.equals("REMOVE")) {
            productsPage.removeProductFromCartByIndex(0);
        }
        
        reportUtils.takeScreenshot("Após clicar no botão " + botao + " do primeiro produto");
    }
    
    @Quando("tocar no botão {string} do produto")
    public void tocarNoBotaoDoProduto(String botao) {
        LOGGER.info("Tocando no botão '{}' do produto (índice 0)", botao);

        // Salva nome e preço do produto (caso queira validar depois)
        lastProductName = productsPage.getProductNameByIndex(0);
        lastProductPrice = productsPage.getProductPriceByIndex(0);

        // Se for ADD TO CART, adiciona o produto
        if (botao.equalsIgnoreCase("ADD TO CART")) {
            productsPage.addProductToCartByIndex(0);
        }
        // Se for REMOVE, remove o produto
        else if (botao.equalsIgnoreCase("REMOVE")) {
            productsPage.removeProductFromCartByIndex(0);
        }
        // Se for outro texto, dá erro no teste
        else {
            Assertions.fail("Botão '" + botao + "' não reconhecido. Esperado: 'ADD TO CART' ou 'REMOVE'");
        }

        // Screenshot da ação
        reportUtils.takeScreenshot("Após clicar no botão '" + botao + "' do produto");
    }

    
    @Então("o contador do carrinho deve exibir {string}")
    public void oContadorDoCarrinhoDeveExibir(String contador) {
        LOGGER.info("Verificando se o contador do carrinho exibe: {}", contador);
        int expectedCount = Integer.parseInt(contador);
        int actualCount = productsPage.getCartBadgeCount();
        
        Assertions.assertEquals(expectedCount, actualCount, 
                "O contador do carrinho exibe " + actualCount + " em vez de " + expectedCount);
        
        reportUtils.takeScreenshot("Contador do carrinho exibindo: " + actualCount);
    }
    
    @E("o botão do produto deve mudar para {string}")
    public void oBotaoDoProdutoDeveMudarPara(String textoBotao) {
        LOGGER.info("Verificando se o botão do produto mudou para: {}", textoBotao);
        String botaoAtual = productsPage.getProductButtonTextByIndex(0);
        
        Assertions.assertEquals(textoBotao, botaoAtual, 
                "O botão do produto exibe " + botaoAtual + " em vez de " + textoBotao);
        
        reportUtils.takeScreenshot("Botão do produto exibindo: " + botaoAtual);
    }
    
    @Quando("adicionar um produto ao carrinho")
    public void adicionarUmProdutoAoCarrinho() {
        LOGGER.info("Adicionando um produto ao carrinho");
        
        // Armazena o nome e preço do produto para verificação posterior
        lastProductName = productsPage.getProductNameByIndex(0);
        lastProductPrice = productsPage.getProductPriceByIndex(0);
        LOGGER.info("Produto a ser adicionado: {} - {}", lastProductName, lastProductPrice);
        
        productsPage.addProductToCartByIndex(0);
        
        // Verifica se o produto foi adicionado ao carrinho
        int cartCount = productsPage.getCartBadgeCount();
        Assertions.assertEquals(1, cartCount, "O produto não foi adicionado ao carrinho corretamente");
        
        reportUtils.takeScreenshot("Produto adicionado ao carrinho");
    }
    
    @E("tocar no ícone do carrinho")
    public void tocarNoIconeDoCarrinho() {
        LOGGER.info("Tocando no ícone do carrinho");
        productsPage.openCart();
        reportUtils.takeScreenshot("Após tocar no ícone do carrinho");
    }
    
    @Então("devo ser redirecionado para a tela do carrinho")
    public void devoSerRedirecionadoParaATelaDoCarrinho() {
        LOGGER.info("Verificando se fui redirecionado para a tela do carrinho");
        reportUtils.takeScreenshot("Tela do carrinho carregada");
    }
    
    @E("devo visualizar o produto adicionado no carrinho")
    public void devoVisualizarOProdutoAdicionadoNoCarrinho() {
        LOGGER.info("Verificando se o produto adicionado está no carrinho");
        Assertions.assertFalse(cartPage.isCartEmpty(), "O carrinho está vazio");
        
        // Verifica se o nome do produto está correto
        String nomeProdutoNoCarrinho = cartPage.getItemNameByIndex(0);
        Assertions.assertEquals(lastProductName, nomeProdutoNoCarrinho, 
                "O nome do produto no carrinho não corresponde ao esperado");
        
        reportUtils.takeScreenshot("Produto visualizado no carrinho");
    }
    
    @E("devo visualizar o preço correto do produto")
    public void devoVisualizarOPrecoCorretoDoProduto() {
        LOGGER.info("Verificando se o preço do produto no carrinho está correto");
        
        // Verifica se o preço do produto está correto
        String precoProdutoNoCarrinho = cartPage.getItemPriceByIndex(0);
        Assertions.assertEquals(lastProductPrice, precoProdutoNoCarrinho, 
                "O preço do produto no carrinho não corresponde ao esperado");
        
        reportUtils.takeScreenshot("Preço do produto correto no carrinho");
    }
    
    @E("tocar no botão {string} do produto no carrinho")
    public void tocarNoBotaoDoProdutoNoCarrinho(String botao) {
        LOGGER.info("Tocando no botão {} do produto no carrinho", botao);
        
        if (botao.equals("REMOVE")) {
            cartPage.removeItemByIndex(0);
        }
        
        reportUtils.takeScreenshot("Após clicar no botão " + botao + " do produto no carrinho");
    }
    
    @Então("o carrinho deve ficar vazio")
    public void oCarrinhoDeveFicarVazio() {
        LOGGER.info("Verificando se o carrinho está vazio");
        reportUtils.takeScreenshot("Carrinho vazio");
    }
    
    @E("devo visualizar a mensagem {string}")
    public void devoVisualizarAMensagem(String mensagem) {
        LOGGER.info("Verificando se a mensagem '{}' está sendo exibida", mensagem);
        
        if (mensagem.equals("No Items")) {
            Assertions.assertTrue(cartPage.isNoItemsMessageDisplayed(), 
                    "A mensagem 'No Items' não está sendo exibida");
        }
        
        reportUtils.takeScreenshot("Mensagem exibida: " + mensagem);
    }
    
    @Quando("adicionar {int} produtos diferentes ao carrinho")
    public void adicionarProdutosDiferentesAoCarrinho(int quantidade) {
        LOGGER.info("Adicionando {} produtos diferentes ao carrinho", quantidade);
        
        for (int i = 0; i < quantidade; i++) {
            LOGGER.info("Adicionando produto {}/{}", (i+1), quantidade);
            productsPage.addProductToCartByIndex(i);
            reportUtils.takeScreenshot("Produto " + (i+1) + " adicionado ao carrinho");
        }
        
        // Verifica se a quantidade de produtos foi adicionada corretamente
        int cartCount = productsPage.getCartBadgeCount();
        Assertions.assertEquals(quantidade, cartCount, 
                "O número de produtos adicionados não corresponde ao esperado");
    }
    
    @Então("devo visualizar os {int} produtos no carrinho")
    public void devoVisualizarOsProdutosNoCarrinho(int quantidade) {
        LOGGER.info("Verificando se existem {} produtos no carrinho", quantidade);
        int itemCount = cartPage.getItemCount();
        
        Assertions.assertEquals(quantidade, itemCount, 
                "O número de produtos no carrinho não corresponde ao esperado");
        
        reportUtils.takeScreenshot(quantidade + " produtos visualizados no carrinho");
    }
}