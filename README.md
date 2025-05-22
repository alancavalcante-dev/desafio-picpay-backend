
# 💸 Desafio Backend - PicPay

O PicPay Simplificado é uma plataforma de pagamentos desenvolvida para o desafio técnico publicado pelo PicPay. O objetivo do projeto é simular a arquitetura de um sistema financeiro inspirado no funcionamento do PicPay, permitindo operações básicas de movimentação financeira entre usuários.

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
- **JUnit - Teste Automatizado**
- **Lombok**
- **jBCrypt - Para criptografar senhas**
- **REST APIs**
- **Swagger**
- **Actuator - métricas e observabilidade**
- **Docker**
- **Git**
- **Validation**
- **Geração de Logs**
- **Pool de conexões com HikariCp**


---

## 📁 Estrutura de Pastas

```
io.github.alancavalcante_dev.desafio_picpay_backend
│
├── common
│   └── ExceptionHandlerGlobal.java         # Tratamento global de exceções
│
├── controller
│   ├── dto                                 # DTOs de entrada
│   ├── TransactionController.java        
│   └── UserController.java               
│
├── domain
│   ├── Transaction.java       
│   │   TransactionNotificationError.java   # Entidade de erro de notificação           
│   └── User.java                         
│
├── repository
│   ├── TransactionRepository.java        
│   ├── UserRepository.java               
│   └── TransactionNotificationErrorRepository.java  # Registro de erros de notificação
│
├── service
│   ├── TransactionService.java             # Lógica principal de transações
│   ├── UserService.java                    # Cadastro de usuário
│   ├── AuthorizationTransaction.java       # Verificação externa de autorização
│   └── NotificationTransactionSender.java  # Envio de notificações
│   
│
└── DesafioPicpayBackendApplication.java  
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

Ou utilizar o Docker
```bash
# Clonar o projeto
git clone https://github.com/alancavalcante-dev/desafio-picpay-backend.git

# Acessar a pasta
cd desafio-picpay-backend

# Criar a imagem
docker build -t desafio-picpay-backend .

# Criar e executar o container da imagem gerada
docker run -p 8080:8080 -p 9090:9090 desafio-picpay-backend
```
---

## 🛠 Endpoints Principais

| Método | Rota             | Descrição                 |
|--------|------------------|---------------------------|
| POST   | `/user`          | Cadastro de novo usuário  |
| POST   | `/transfer`      | Realiza uma transação     |

---

## 🔒 Segurança e Tratamento de Erros

A aplicação possui um `@RestControllerAdvice` que captura `RuntimeException` e retorna um status HTTP 400, impedindo a exposição de mensagens sensíveis ao cliente.

---

## 📋 Possíveis Melhorias

- Implementar autenticação JWT.
- Bloquear valor monetário na criação do usuário

---

## 🌐 URLs

🔗 http://localhost:8080/transfer <br>
🔗 http://localhost:8080/user <br>
🔗 http://localhost:9090/actuator <br>
🔗 http://localhost:8080/swagger-ui/index.html


---

## 📬 Contato

Desenvolvido por **Alan Pereira Cavalcante**

📧 alan.cavalcante.dev@gmail.com <br>
📞 (11) 986815754

---
