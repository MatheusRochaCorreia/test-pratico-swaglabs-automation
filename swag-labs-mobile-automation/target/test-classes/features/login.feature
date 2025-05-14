# language: pt
@Login
Funcionalidade: Login no aplicativo SwagLabs Mobile
  Como um usuário do aplicativo SwagLabs Mobile
  Eu quero realizar o login na aplicação
  Para acessar as funcionalidades do sistema

  Contexto:
    Dado que estou na tela de login do aplicativo SwagLabs Mobile

  @LoginSucesso
  Esquema do Cenario: Login válido no aplicativo
    Quando inserir o usuário "<usuario>"
    E inserir a senha "<senha>"
    E tocar no botão Login
    Então devo ser redirecionado para a tela inicial do aplicativo
    E devo visualizar os produtos disponíveis

    Exemplos:
      | usuario       | senha        |
      | standard_user | secret_sauce |
      | problem_user  | secret_sauce |

  @LoginInvalido
  Esquema do Cenario: Login inválido no aplicativo
    Quando inserir o usuário "<usuario>"
    E inserir a senha "<senha>"
    E tocar no botão Login
    Então devo visualizar a mensagem de erro "<mensagem>"

    Exemplos:
      | usuario        | senha           | mensagem                                                                |
      | locked_out_user | secret_sauce   | Sorry, this user has been locked out.                                   |
      | invalid_user    | secret_sauce   | Username and password do not match any user in this service.            |
      | standard_user   | wrong_password | Username and password do not match any user in this service.            |
      |                 | secret_sauce   | Username is required                                                   |
      | standard_user   |                | Password is required                                                   |

  @CamposObrigatorios
  Cenario: Verificar campo de usuário obrigatório
    Quando inserir a senha "secret_sauce"
    E tocar no botão Login
    Então devo visualizar a mensagem de erro "Username is required"

  @CamposObrigatorios
  Cenario: Verificar campo de senha obrigatório
    Quando inserir o usuário "standard_user"
    E tocar no botão Login
    Então devo visualizar a mensagem de erro "Password is required"


  @Logout
  Cenario: Realizar logout após login bem-sucedido
    Quando inserir o usuário "standard_user"
    E inserir a senha "secret_sauce"
    E tocar no botão Login
    Então devo ser redirecionado para a tela inicial do aplicativo
    Quando tocar no menu lateral
    E selecionar a opção de Logout
    Então devo retornar para a tela de login