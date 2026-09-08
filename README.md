# 💸 PIX Desafio

Backend desenvolvido em **Java com Spring Boot** como um desafio prático para estudar a implementação de um sistema de transferências inspirado no funcionamento do **PIX**.

O projeto tem como objetivo explorar conceitos importantes de desenvolvimento backend, como **transações financeiras, persistência de dados, validação, concorrência, processamento assíncrono e organização de domínio**.

> ⚠️ **Projeto de estudo:** esta aplicação não representa uma implementação oficial do PIX e não deve ser utilizada em ambientes financeiros reais.

---

## 🚀 Tecnologias

* ☕ **Java 21**
* 🌱 **Spring Boot**
* 🌐 **Spring Web MVC**
* 🗄️ **Spring Data JPA**
* 🛢️ **H2 Database**
* 🔄 **Hibernate**
* ✅ **Jakarta Validation**
* 🧩 **Lombok**
* 📦 **Maven**

As dependências e versões utilizadas podem ser encontradas no [`pom.xml`](./pom.xml).

---

## 🎯 Objetivo

A ideia do desafio é construir uma API capaz de representar um fluxo simplificado de transferências entre contas.

O domínio principal é composto por:

```text
┌──────────────┐
│    Account   │
│              │
│ - id         │
│ - balance    │
│ - ...        │
└──────┬───────┘
       │
       │ transfers
       ▼
┌──────────────┐
│   Transfer   │
│              │
│ - id         │
│ - payer      │
│ - receiver   │
│ - amount     │
│ - status     │
│ - ...        │
└──────────────┘
```

A aplicação separa as responsabilidades de contas e transferências em módulos próprios.

---

## 🏗️ Estrutura do projeto

A estrutura principal está organizada da seguinte forma:

```text
src/
└── main/
    ├── java/
    │   └── org/example/pixdesfio/
    │       │
    │       ├── account/
    │       │   ├── dto/
    │       │   ├── Account.java
    │       │   ├── AccountController.java
    │       │   ├── AccountRepository.java
    │       │   └── AccountService.java
    │       │
    │       ├── transfer/
    │       │   ├── dto/
    │       │   ├── Transfer.java
    │       │   ├── TransferController.java
    │       │   ├── TransferRepository.java
    │       │   ├── TransferService.java
    │       │   └── TransferStatus.java
    │       │
    │       ├── worker/
    │       │   └── Worker.java
    │       │
    │       ├── health/
    │       │
    │       ├── shared/
    │       │   └── exception/
    │       │
    │       └── PixdesfioApplication.java
    │
    └── resources/
```

A organização atual separa os componentes por **domínio/feature**, evitando concentrar toda a aplicação em pacotes genéricos de `controller`, `service` e `repository`.

---

## 💳 Contas

O módulo `account` é responsável pelo gerenciamento das contas utilizadas nas transferências.

Atualmente existem:

* Entidade `Account`
* `AccountRepository`
* `AccountService`
* `AccountController`
* DTOs relacionados a contas

### Criar conta

```http
POST /accounts
```

A criação recebe os dados da conta através de um DTO validado.

Em caso de sucesso, a API retorna HTTP `201 Created`.

### Extrato da conta

```http
GET /accounts/{id}/statement
```

Retorna os dados da conta juntamente com suas transferências.

---

## 💸 Transferências

O módulo `transfer` concentra a lógica relacionada às transferências entre contas.

Ele possui:

* Entidade `Transfer`
* `TransferRepository`
* `TransferService`
* `TransferController`
* `TransferStatus`
* DTOs de transferência

A entidade de transferência possui relacionamento com as contas envolvidas na operação.

### Criar transferência

```http
POST /transfers
```

Exemplo:

```json
{
  "payerId": "account-id",
  "receiverId": "account-id",
  "amount": 100.00
}
```

O payload real deve seguir os campos definidos pelo `CreateTransferDto`.

A requisição é validada antes de ser processada pela camada de serviço.

### Consultar transferência

```http
GET /transfers/{id}
```

Permite consultar uma transferência específica pelo seu identificador.

---

## 🔄 Fluxo simplificado

O fluxo esperado da aplicação pode ser representado por:

```text
             ┌─────────────┐
             │   Cliente   │
             └──────┬──────┘
                    │
                    │ POST /transfers
                    ▼
          ┌────────────────────┐
          │ TransferController │
          └──────────┬─────────┘
                     │
                     ▼
            ┌────────────────┐
            │ TransferService│
            └───────┬────────┘
                    │
          ┌─────────┴──────────┐
          │                    │
          ▼                    ▼
   ┌──────────────┐     ┌──────────────┐
   │    Payer     │     │   Receiver   │
   │   Account    │     │   Account    │
   └──────────────┘     └──────────────┘
          │                    │
          └─────────┬──────────┘
                    ▼
             ┌─────────────┐
             │  Transfer   │
             │  persisted  │
             └─────────────┘
```

---

## 🗄️ Banco de dados

O projeto utiliza **H2 Database** para facilitar o desenvolvimento e os testes locais.

A configuração permite executar o projeto sem a necessidade de instalar um banco de dados externo.

O projeto também possui a dependência do console do H2, permitindo utilizar a interface do banco durante o desenvolvimento.

---

## ▶️ Executando o projeto

### Pré-requisitos

Antes de começar, tenha instalado:

* Java 21+
* Git
* Maven (opcional, pois o projeto possui Maven Wrapper)

### Clone

```bash
git clone https://github.com/marlonnx/pixdesafio.git

cd pixdesafio
```

### Executando com Maven Wrapper

#### Linux / macOS

```bash
./mvnw spring-boot:run
```

#### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

Também é possível executar através da sua IDE.

---

## 🧪 Executando os testes

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```powershell
.\mvnw.cmd test
```

---

## 🔌 API

### Endpoints disponíveis

| Método | Endpoint                   | Descrição                       |
| ------ | -------------------------- | ------------------------------- |
| `POST` | `/accounts`                | Cria uma conta                  |
| `GET`  | `/accounts/{id}/statement` | Consulta o extrato de uma conta |
| `POST` | `/transfers`               | Cria uma transferência          |
| `GET`  | `/transfers/{id}`          | Consulta uma transferência      |

Os endpoints de contas e transferências são implementados respectivamente por `AccountController` e `TransferController`.

---


## 📚 Sobre o desafio

Este projeto é uma implementação experimental para estudar como problemas comuns de sistemas financeiros podem ser modelados em uma aplicação backend.

O objetivo não é reproduzir toda a infraestrutura real do PIX, mas utilizar o domínio de pagamentos como cenário para trabalhar problemas interessantes de engenharia de software, especialmente **consistência, concorrência, atomicidade e processamento de transações**.

---

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais.

Consulte o repositório para informações sobre a licença e utilização do código.

---

## 👨‍💻 Autor

**Marlon**

GitHub: [@marlonnx](https://github.com/marlonnx)

---

⭐ Se este projeto foi útil para seus estudos, considere deixar uma estrela no repositório.

**Repositório:** https://github.com/marlonnx/pixdesafio
