# DWE-APP-AUTH-JAVA

API de autenticação desenvolvida com **Java 17**, **Spring Boot 3**, **Spring Security**, **JWT**, **Swagger** e **banco de dados H2**.

---

## 📌 Funcionalidades Implementadas

### ✅ Cadastro de Usuários
- Endpoint: `POST /auth`
- Cria um novo usuário com:
  - Nome
  - E-mail
  - Senha (criptografada com BCrypt)
  - Perfil (INVESTIDOR ou ASSESSOR)

### ✅ Listagem, Atualização e Exclusão
- `GET /auth` – Lista todos os usuários
- `GET /auth/{id}` – Busca usuário por ID
- `PUT /auth/{id}` – Atualiza nome, e-mail e perfil
- `DELETE /auth/{id}` – Remove usuário

### ✅ Login com Geração de Token JWT
- Endpoint: `POST /login`
- Autentica o usuário com e-mail e senha
- Gera e retorna um token JWT válido por 10 horas

### ✅ Segurança com Spring Security
- Token JWT é obrigatório para acessar endpoints protegidos
- O token deve ser enviado via header:
  ```
  Authorization: Bearer <token>
  ```

### ✅ Tratamento Global de Erros
- Validações de entrada com mensagens claras
- Respostas padronizadas para:
  - 400 – Dados inválidos
  - 404 – Não encontrado
  - 500 – Erros internos

### ✅ Documentação com Swagger/OpenAPI
- Acesso: `http://localhost:8080/swagger-ui.html`
- Geração automática via SpringDoc

---

## ⚙️ Tecnologias Utilizadas

| Tecnologia        | Versão         |
|-------------------|----------------|
| Java              | 17             |
| Spring Boot       | 3.x            |
| Spring Security   | ✅              |
| JWT (jjwt)        | ✅              |
| H2 Database       | ✅              |
| Spring Data JPA   | ✅              |
| Lombok            | ✅              |
| SpringDoc OpenAPI | 2.1.0          |

---

## 🗂️ Estrutura de Pacotes

```
br.com.fiap.auth
├── config           → Configurações do projeto (segurança, Swagger, exceptions)
├── controller       → Controllers REST
├── dto              → Objetos de transferência (request/response)
├── model            → Entidades JPA
├── repository       → Interfaces JPA
├── security         → Filtros JWT
├── service          → Lógica de negócio
├── util             → Utilitários de JWT
└── resources
    └── application.properties
```

---

## 🔐 Perfis de Acesso

- Enum: `Perfil`
  - `INVESTIDOR`
  - `ASSESSOR`

---

## 🧪 Testando a API com Postman ou Insomnia

### 1. Cadastrar usuário (`POST /auth`)
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "perfil": "ASSESSOR"
}
```

### 2. Login (`POST /login`)
```json
{
  "email": "joao@email.com",
  "senha": "123456"
}
```

### 3. Enviar token JWT em chamadas protegidas
```
Authorization: Bearer <token>
```

---

## ▶️ Executando a Aplicação

```bash
./mvnw spring-boot:run
```

Ou via IntelliJ: botão "Run"

Acesse:
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- H2 Console (se habilitado): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

---

## 🧠 Autor

Desenvolvido para fins acadêmicos e de prática com arquitetura de microserviços e segurança em APIs.