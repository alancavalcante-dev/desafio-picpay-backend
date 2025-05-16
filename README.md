
# 💸 Desafio PicPay - Backend



O PicPay Simplificado é uma plataforma de pagamentos desenvolvida para resolver o desafio técnico proposto pelo o mesmo, com o objetivo de simular a arquitetura de um sistema financeiro, inspirado no funcionamento do PicPay.
A proposta do projeto é permitir depósitos e transferências de dinheiro entre usuários, com duas categorias de conta: usuários comuns e lojistas. Ambos possuem carteiras digitais com saldo e podem interagir entre si conforme regras definidas.
---

## 🧠 Motivação e Raciocínio

A ideia foi simular um ambiente de produção com as seguintes preocupações:

- Isolamento das responsabilidades (SOLID).
- Camadas bem definidas (Controller, Service, Repository, Domain).
- Transações seguras com controle de rollback.
- Comunicação com serviços externos (validação e notificação).
- Registro de falhas de notificação sem impedir a transação.
- Tratamento global de exceções para segurança na resposta da API.

---

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.5**
- **Spring Data JPA**
- **H2 Database (em memória)**
- **Lombok**
- **jBCrypt**
- **REST APIs**
- **Swagger**
- **Docker**
- **Docker**

---

## 📁 Estrutura de Pastas

```
io.github.alancavalcante_dev.desafio_picpay_backend
│
├── common
│   └── ExceptionHandlerGlobal.java       # Tratamento global de exceções
│
├── controller
│   ├── dto                               # DTOs de entrada
│   ├── TransactionController.java        # Endpoint de transações
│   └── UserController.java               # Endpoint de usuários
│
├── domain
│   ├── Transaction.java                  # Entidade de transação
│   └── User.java                         # Entidade de usuário
│
├── repository
│   ├── TransactionRepository.java        # Acesso à tabela de transações
│   ├── UserRepository.java               # Acesso à tabela de usuários
│   └── TransactionNotificationErrorRepository.java  # Registro de erros de notificação
│
├── service
│   ├── TransactionService.java           # Lógica principal de transações
│   ├── UserService.java                  # Cadastro de usuário
│   ├── AuthorizationTransaction.java     # Verificação externa de autorização
│   ├── NotificationTransactionSender.java # Envio de notificações
│   └── TransactionNotificationError.java # Entidade de erro de notificação
│
└── DesafioPicpayBackendApplication.java  # Classe principal
```

---

## 🔐 Regras de Negócio

- Lojistas não podem enviar dinheiro.
- Um usuário não pode transferir para si mesmo.
- O pagador precisa ter saldo suficiente.
- Todas as transações são autorizadas por um serviço externo.
- Se a notificação falhar, o erro é salvo, mas a transação não é desfeita.

---

## 🔄 Fluxo da Transação

1. O cliente envia os dados da transação via `POST /transfer`.
2. A `TransactionService` valida os dados.
3. Verifica-se saldo e tipo de usuário.
4. O saldo é atualizado e a transação é persistida.
5. Um serviço externo é chamado para autorizar a transação.
6. Se autorizado, são enviadas notificações para pagador e recebedor.
7. Em caso de erro no envio da notificação, ele é salvo na tabela de erros.

---

## 🧪 Como Executar

```bash
# Clonar o projeto
git clone https://github.com/seu-usuario/desafio-picpay-backend.git

# Acessar a pasta
cd desafio-picpay-backend

# Rodar a aplicação
./mvnw spring-boot:run
```

---

## 🛠 Endpoints Principais

| Método | Rota             | Descrição                 |
|--------|------------------|---------------------------|
| POST   | `/user`          | Cadastro de novo usuário  |
| POST   | `/transfer`      | Realiza uma transação     |

---

## 🔒 Segurança e Tratamento de Erros

A aplicação possui um `@RestControllerAdvice` que captura `RuntimeException` e retorna um status HTTP 422, impedindo a exposição de mensagens sensíveis ao cliente.

---

## 📋 Possíveis Melhorias

- Implementar autenticação JWT.
- Adicionar testes unitários e de integração.
- Criar uma interface Swagger.
- Internacionalização de mensagens de erro.
- Logs estruturados com mais detalhes.

---

## 🧑‍💻 Desenvolvido por

Alan Cavalcante  
Estudante de ADS, Desenvolvedor Backend em formação.

---
