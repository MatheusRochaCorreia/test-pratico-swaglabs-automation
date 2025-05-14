# language: pt
@Carrinho
Funcionalidade: Gerenciamento de itens no carrinho do SwagLabs Mobile
  Como um usuário do aplicativo SwagLabs Mobile
  Eu quero adicionar, visualizar e remover produtos do carrinho
  Para poder gerenciar minha compra

  Contexto:
    Dado que estou logado no aplicativo SwagLabs Mobile com o usuário "standard_user" e senha "secret_sauce"
    E estou na tela de lista de produtos

  @AdicionarProduto
  Cenario: Adicionar um produto ao carrinho
    Quando tocar no botão "ADD TO CART" do primeiro produto
    Então o contador do carrinho deve exibir "1"
    E o botão do produto deve mudar para "REMOVE"

  @VisualizarCarrinho
  Cenario: Visualizar produtos no carrinho
    Quando adicionar um produto ao carrinho
    E tocar no ícone do carrinho
    Então devo ser redirecionado para a tela do carrinho
    E devo visualizar o produto adicionado no carrinho
    E devo visualizar o preço correto do produto

  @RemoverProdutoCarrinho
  Cenario: Remover um produto da tela do carrinho
    Quando adicionar um produto ao carrinho
    E tocar no ícone do carrinho
    E tocar no botão "REMOVE" do produto no carrinho
    Então o carrinho deve ficar vazio
    E devo visualizar a mensagem "No Items"

  @RemoverProdutoTelaLista
  Cenario: Remover um produto da lista de produtos
    Quando tocar no botão "ADD TO CART" do primeiro produto
    E o contador do carrinho deve exibir "1"
    E tocar no botão "REMOVE" do produto
    Então o contador do carrinho deve exibir "0"
    E o botão do produto deve mudar para "ADD TO CART"

  @AdicionarMultiplosProdutos
  Cenario: Adicionar múltiplos produtos ao carrinho
    Quando adicionar 2 produtos diferentes ao carrinho
    Então o contador do carrinho deve exibir "2"
    Quando tocar no ícone do carrinho
    Então devo visualizar os 2 produtos no carrinho