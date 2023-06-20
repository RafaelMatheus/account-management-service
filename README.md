# Account Management Service

O serviço Account Management é responsável por gerenciar as contas no contexto de uma solução de carteira digital.

## Descrição

O Account Management Service é um componente central que oferece funcionalidades de contas de usuários dentro do sistema de carteira digital. 

## Arquitetura

O serviço Account Management é projetado como um microserviço independente e segue os princípios de arquitetura de microsserviços. Ele é desenvolvido usando tecnologias como Spring Boot, Java rabbitMq e Banco de Dados mongoDb para armazenar os dados das contas.

![desenhoarquitetural](https://github.com/RafaelMatheus/account-management-service/assets/25590639/dfbb865b-3530-446b-b407-ecb7844490d1)

## Integrações

O Account Management Service se integra com os seguintes serviços:

- [Transaction Service](https://github.com/RafaelMatheus/transaction-management-service): responsável histórico transações financeiras.
- [Payment Service](https://github.com/RafaelMatheus/payment-management-service): responsável pelo processamento de pagamentos e recebimentos.

## Requisitos do Sistema

Para executar o Account Management Service, verifique se você possui os seguintes requisitos instalados:

- Java 17: [Instalar Java 17](https://www.oracle.com/java/technologies/downloads/)
- Maven: [Instalar Maven](https://maven.apache.org/install.html)
- Docker: [Instalar Docker](https://docs.docker.com/get-docker/)
- Docker Compose: [Instalar Docker Compose](https://docs.docker.com/compose/install/)

Certifique-se de que o Java 17 e o Maven estejam configurados corretamente em seu ambiente.

Antes de construir a imagem Docker do Account Management Service, é necessário executar o Maven para compilar o projeto. Navegue até a raiz do projeto e execute o seguinte comando:

```bash
mvn clean package
```

Antes de iniciar o ambiente com o `docker-compose`, é necessário construir a imagem Docker do Account Management Service executando o seguinte comando na raiz do projeto:

```bash
docker build -t account-management .
````
Observação: Certifique-se de ter construido as imagens das dependências antes de executar o docker-compose, [Transaction Service](https://github.com/RafaelMatheus/transaction-management-service) e [Payment Service](https://github.com/RafaelMatheus/payment-management-service).

```bash
docker-compose up -d
```
Observação: Certifique-se que está executando o comando docker-compose no diretório principal do account-management-service. Isso garantirá que o Docker Compose encontre e utilize o arquivo correto para iniciar todos os serviços necessários.

## Documentação do Swagger

O Account Management Service possui uma documentação do Swagger que descreve os endpoints disponíveis e fornece informações detalhadas sobre como consumir a API.

Para acessar a documentação do Swagger, siga as etapas abaixo:

1. Verifique se o docker-compose foi corretamente executado.
2. Abra o navegador e vá para a URL: [http://localhost/account/swagger-ui.html](http://localhost/account/swagger-ui.html).

Isso abrirá a interface do Swagger, onde você poderá explorar os endpoints, enviar solicitações e visualizar as respostas.

Certifique-se de que o serviço esteja em execução para acessar a documentação do Swagger.

## Funcionalidades Principais

- Criação de novas contas de usuário.
- Consulta de saldo disponível em uma conta.
- Executar transações de Deposito, saque, transferência e pagamento.

## Exemplos de Uso (Curl)

Aqui estão alguns exemplos de como usar as funcionalidades do Account Management Service com curl:

Antes de qualquer coisa é necessário criar uma nova conta, você utilizará até o final do tutorial.
- Criar uma nova conta:

  ```bash
  curl -X POST "http://localhost/account/api/accounts" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"holderName\": \"Teste\", \"holderTaxId\": \"01234567890\", \"phoneNumber\": \"81999999999\"}"
  ```
- Consultar informações da conta (guarde o número da conta criado anteriormente):

  ```bash
  curl -X GET "http://localhost/account/api/accounts?accountNumber={numeroDaContaRespondidoNaSolucitacaoAnterior}" -H "accept: */*"
  ```
- Realizar deposito:
  ```bash
  curl -X POST "http://localhost/account/api/accounts/transaction" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"originAccountNumber\": \"numeroDaContaRespondidoNaSolucitacaoAnterior\", \"type\": \"DEPOSIT\", \"value\": 10}"
  ```
  
- Efetuar pagamento (necessário saldo em conta):

  ```bash
  curl -X POST "http://localhost/account/api/accounts/transaction" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"barcode\": \"xpto\", \"originAccountNumber\": \"numeroDaContaRespondidoNaSolucitacaoAnterior\", \"type\": \"PAYMENT\", \"value\": 10}"
  ```
- Realizar saque (necessário saldo em conta):
  ```bash
    curl -X POST "http://localhost/account/api/accounts/transaction" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"originAccountNumber\": \"numeroDaContaRespondidoNaSolucitacaoAnterior\", \"type\": \"WITHDRAW\", \"value\": 10}"
  ```
- Realizar transferência:
  ```bash
  curl -X POST "http://localhost/account/api/accounts/transaction" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"destinationAccountNumber\": \"contaInformada\", \"originAccountNumber\": \"outraConta\", \"type\": \"TRANSFER\", \"value\": 10}"
  ```
- Histórico de transaferência por conta:
    ```bash
    curl -X GET "http://localhost/transaction/api/transactions?accountNumber=d5fe117d-8fa9-49b8-80bd-fb7b509847b6&pageNumber=0&size=4" -H "accept: */*"
    ```


