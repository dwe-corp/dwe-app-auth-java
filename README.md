# DWE-APP-AUTH-JAVA

API de autenticação desenvolvida com **Java 17**, **Spring Boot 3**, **Spring Security**, **JWT**, **Swagger**, **análise estática com Semgrep**, **logs com SLF4J** e **banco de dados H2**.

---

## 📌 Funcionalidades Implementadas

### ✅ Cadastro de Usuários
- Endpoint: `POST /auth`
- Cria um novo usuário com:
  - Nome
  - E-mail
  - Senha (criptografada com BCrypt)
  - Perfil (INVESTIDOR ou ASSESSOR)
- Validação com anotações `@NotBlank`, `@Email`, `@Valid`

### ✅ Listagem, Atualização e Exclusão
- `GET /auth` – Lista todos os usuários
- `GET /auth/{id}` – Busca usuário por ID
- `PUT /auth/{id}` – Atualiza nome, e-mail e perfil
- `DELETE /auth/{id}` – Remove usuário
- Todas as ações são **logadas com SLF4J**

### ✅ Login com Geração de Token JWT
- Endpoint: `POST /login`
- Autentica o usuário com e-mail e senha
- Gera e retorna um token JWT válido por 10 horas
- Toda tentativa de login (sucesso ou falha) é **registrada em log**

### ✅ Segurança com Spring Security
- Token JWT obrigatório para endpoints protegidos
- O token deve ser enviado via header:
  ```
  Authorization: Bearer <token>
  ```

### ✅ Tratamento Global de Erros
- Captura e responde:
  - `@Valid` (400)
  - E-mail duplicado
  - Enum inválido
  - Erros de regra de negócio
  - Exceções internas (500)
- **Todos os erros são logados com SLF4J**

### ✅ Documentação com Swagger/OpenAPI
- Acesso: `http://localhost:8080/swagger-ui.html`
- Geração automática via SpringDoc OpenAPI

### ✅ Logs e Observabilidade
- Implementação completa de logging com **SLF4J** e **LoggerFactory**
- Logs presentes em:
  - `LoginController`
  - `AuthController`
  - `AuthService`
  - `UserDetailsServiceImpl`
  - `GlobalExceptionHandler`

### ✅ Análise Estática de Código (SAST)
- Workflow GitHub Actions com **Semgrep**
- Detecta falhas de segurança, más práticas e violações de padrões

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
| SLF4J (Logging)   | ✅              |
| Semgrep (SAST)    | ✅              |

---

## 🗂️ Estrutura de Pacotes

```
br.com.fiap.auth
├── config           → Configurações de segurança, Swagger, exceptions
├── controller       → Controllers REST com log
├── dto              → Objetos de transferência (request/response)
├── model            → Entidades JPA com validação
├── repository       → Interfaces JPA
├── security         → Filtros de autenticação JWT
├── service          → Lógica de negócio com log
├── util             → Geração e verificação de token JWT
└── resources
    └── application.properties
```

---

## 🔐 Perfis de Acesso

Enum: `Perfil`
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

### 3. Usar token JWT em endpoints protegidos
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

## 🛡️ Análise de Segurança com Semgrep

O repositório possui workflow automático para análise estática (SAST) com [Semgrep](https://semgrep.dev/):

```yaml
.github/workflows/semgrep.yml
```

---

## 🧠 Autor

Desenvolvido para fins acadêmicos e de prática com arquitetura de microserviços e segurança em APIs RESTful.