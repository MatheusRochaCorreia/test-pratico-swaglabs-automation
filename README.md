# 📱 Swag Labs Mobile Automation - Teste Prático Sênior

Este projeto foi desenvolvido como parte do Teste Prático para a vaga de **Automatizador de Testes Sênior**. Ele realiza a automação de testes funcionais em uma aplicação mobile Android — o aplicativo **Swag Labs Mobile** — utilizando tecnologias robustas como **Appium**, **Java**, **Cucumber (BDD)**, **JUnit 5** e **Page Object Model (POM)**.

---

## 🧰 Tecnologias e Ferramentas Utilizadas

| Ferramenta/Tecnologia | Versão / Descrição                    |
| --------------------- | ------------------------------------- |
| Java                  | 17                                    |
| Maven                 | 3.8+                                  |
| Appium Server         | 8.6.0                                 |
| Selenium              | 4.13.0                                |
| Cucumber (BDD)        | 7.13.0                                |
| JUnit                 | 5.9.3                                 |
| SLF4J (Log)           | 2.0.7                                 |
| iTextPDF              | 5.5.13.3 – geração de PDF com imagens |
| IDE                   | Eclipse                               |
| Dispositivo Android   | ✅ Real device (USB)                   |
| Android Version       | ✅ Android 11 (API 30)                 |
| Automação             | Appium + UI Automator                 |

---

## 🎯 Objetivo do Projeto

Automatizar o fluxo completo de uma aplicação mobile de e-commerce, garantindo:

* Validação de login/logout
* Manipulação de produtos e carrinho
* Finalização de compras (checkout)
* Geração automática de **relatórios em PDF com evidência visual (screenshot)** de cada passo

---

## 📊 Funcionalidades Testadas

### 🔐 Login

* Login válido e inválido
* Validação de campos obrigatórios
* Visibilidade da senha
* Logout com retorno à tela de login

### 🛒 Carrinho de Compras

* Adição de produtos ao carrinho
* Contador de itens
* Remoção na lista e na tela de carrinho
* Validação de valores e nomes dos produtos

### 💳 Checkout

* Preenchimento de formulário de entrega
* Validações de obrigatoriedade de campos (primeiro nome, sobrenome, CEP)
* Revisão de pedido
* Confirmação de pedido finalizado com sucesso

---

## 📂 Estrutura do Projeto

```
📆 swag-labs-mobile-automation
🔹 src
🔹 main
🔹 java
🔹 com.swaglab.mobile.automation
🔹 core         # Configurações e BasePage
🔹 pages        # Page Object Model
🔹 utils        # Geração de relatórios PDF
🔹 listeners    # Hooks e eventos 
🔹 test
🔹 java
🔹 steps        # Step Definitions
🔹 resources
🔹 features     # Features 
pom.xml
README.md
```

---

## 🖼️ Geração de Evidência em PDF (Automática por Cenário)

Uma das grandes funcionalidades deste projeto é a **geração automática de relatórios em PDF com screenshots de cada etapa executada em cada cenário BDD**.

### Como funciona:

* Cada cenário definido em `.feature` dispara a captura de screenshots automaticamente após cada passo (`Step`) relevante.
* As imagens são organizadas cronologicamente com descrição.
* Um arquivo `.pdf` é gerado por **cenário individual**, com:

  * Nome do cenário
  * Data/Hora da execução
  * Capturas de tela com legendas
  * Resultado visual da execução

### Exemplo de estrutura de evidência:

```
target/
🔹 evidence/
   🔹 Login_válido_standard_user_2025-05-14_20-30-45.pdf
   🔹 Checkout_simples_Teste_Automação_2025-05-14_20-31-22.pdf
```

---

## ⚙️ Pré-Requisitos

* Java 17
* Maven 3.8+
* Appium Server (`npm install -g appium`)
* Android Studio (para SDK e emuladores)
* Dispositivo Android físico ou emulador com **Android 11**
* APK da aplicação disponível em:
  `src/test/resources/app/SwagLabs.apk`

---

## ▶️ Executando os Testes

1. **Start no Appium Server:**

```bash
appium
```

2. **Conecte seu dispositivo Android (modo depuração ativado).**

3. **Executar todos os testes:**

```bash
mvn clean test
```

4. **Executar testes por tag (ex: apenas carrinho):**

```bash
mvn test -Dcucumber.filter.tags="@Carrinho"
```

5. **Verifique as evidências geradas:**

```bash
target/evidence/*.pdf
```

---

## 💡 Padrões e Boas Práticas Adotadas

* ✅ **Page Object Model (POM)** para separação de responsabilidades
* ✅ Uso de **Cucumber BDD** com Gherkin em português
* ✅ **Anotações Cucumber organizadas por contexto, cenário e exemplos**
* ✅ Logs ricos com **SLF4J**
* ✅ Assertivas robustas com **JUnit 5**
* ✅ Modularidade e reutilização de código
* ✅ Evidência visual em PDF para cada cenário

---

## 📱 Detalhes do Ambiente Mobile

| Item             | Valor                    |
| ---------------- | ------------------------ |
| Dispositivo      | Xiaomi redmi note 9 pro  |
| Versão Android   | 11 (API 30)              |
| Tipo             | Físico conectado via USB |
| App testado      | SwagLabs.apk             |
| Modo de execução | Native App com Appium    |
| Plataforma       | Android                  |


---

## 📌 Link do APK

* O APK do Swag Labs Mobile está incluído na pasta:

  ```
  src/test/resources/app/SwagLabs.apk
  ```

---

## 👤 Autor

| Nome                    | Email                                                           |
| ----------------------- | --------------------------------------------------------------- |
| *Matheus Rocha Correia* | [matheus.rcorreia@gmail.com](mailto:matheus.rcorreia@gmail.com) |

---

## 🏁 Considerações Finais

Este projeto foi desenvolvido com muito cuidado para atender aos requisitos de um cenário real de automação mobile. Durante sua construção, busquei usar as melhores práticas, garantindo não apenas a qualidade do código, mas também a legibilidade, manutenção dos testes.
\*\*
