# language: pt
@Checkout
Funcionalidade: Processo de checkout do SwagLabs Mobile
  Como um usuário do aplicativo SwagLabs Mobile
  Eu quero finalizar minha compra através do processo de checkout
  Para concluir minha transação

  Contexto:
    Dado que estou logado com o usuário "standard_user" e senha "secret_sauce"
    E adicionei um produto ao carrinho
    E naveguei para a tela do carrinho

  @CheckoutSimples
  Cenario: Checkout simples com dados válidos
    Quando tocar no botão "CHECKOUT"
    Então devo ser direcionado para a tela de informações do checkout
    Quando preencher o primeiro nome como "Teste"
    E preencher o sobrenome como "Automação"
    E preencher o CEP como "12345"
    E tocar no botão "CONTINUE"
    Então devo ser direcionado para a tela de revisão do pedido
    E devo visualizar o item adicionado na revisão
    Quando tocar no botão "FINISH"
    Então devo visualizar a confirmação de pedido concluído

  @CheckoutValidacoes
  Esquema do Cenario: Validações do formulário de checkout
    Quando tocar no botão "CHECKOUT"
    E preencher o primeiro nome como "<primeiro_nome>"
    E preencher o sobrenome como "<sobrenome>"
    E preencher o CEP como "<cep>"
    E tocar no botão "CONTINUE"
    Então devo visualizar a mensagem de erro de checkout "<mensagem>"

    Exemplos:
      | primeiro_nome | sobrenome | cep    | mensagem                  |
      |               | Automação | 12345  | First Name is required    |
      | Teste         |           | 12345  | Last Name is required     |
      | Teste         | Automação |        | Postal Code is required   |