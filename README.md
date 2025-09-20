DWE-APP-AUTH-JAVA

API de autenticação desenvolvida com Java 17, Spring Boot 3, Spring Security, JWT, Swagger, logs com SLF4J, H2 e pipeline de segurança CI/CD com SAST (Semgrep), DAST (OWASP ZAP) e SCA (Dependency Review + OSV-Scanner).

---

🤝 *Autores*

- Eduardo Akira Murata (RM98713)
- Deivison Pertel (RM550803)
- Wesley Souza de Oliveira (RM97874)

---

⸻

📌 Funcionalidades Implementadas

✅ Cadastro de Usuários
	•	Endpoint: POST /auth
	•	Cria um novo usuário com: Nome, E-mail, Senha (BCrypt) e Perfil (INVESTIDOR/ASSESSOR)
	•	Validação com @NotBlank, @Email, @Valid

✅ Listagem, Atualização e Exclusão
	•	GET /auth – Lista usuários
	•	GET /auth/{id} – Busca por ID
	•	PUT /auth/{id} – Atualiza nome/e-mail/perfil
	•	DELETE /auth/{id} – Remove usuário
	•	Todas as ações logadas com SLF4J

✅ Login com Geração de Token JWT
	•	Endpoint: POST /login
	•	Autentica com e-mail e senha
	•	Gera token JWT (10h)
	•	Tentativas de login (sucesso/falha) registradas em log

✅ Segurança com Spring Security
	•	Token JWT obrigatório em endpoints protegidos
	•	Header: Authorization: Bearer <token>

✅ Tratamento Global de Erros
	•	Captura e responde: validação (400), e-mail duplicado, enum inválido, regras de negócio e exceções internas (500)
	•	Todos os erros são logados

✅ Documentação com Swagger/OpenAPI
	•	http://localhost:8080/swagger-ui.html
	•	SpringDoc OpenAPI

✅ Logs e Observabilidade
	•	Logging com SLF4J em:
	•	LoginController, AuthController, AuthService, UserDetailsServiceImpl, GlobalExceptionHandler

⸻

🔒 Segurança no CI/CD

Integração completa de segurança no pipeline usando GitHub Actions:

1) SAST – Análise estática com Semgrep 
	•	Workflow publica SARIF em Security ▸ Code scanning (severidade + recomendações)
	•	Regras utilizadas: p/owasp-top-ten, p/security-audit, p/secrets
	•	Gating no pipeline unificado: deploy é bloqueado quando há findings level==error no SARIF

Arquivos relevantes:
	•	./github/workflows/ci-cd-security.yml (job sast_semgrep)
	•	Relatório: Security ▸ Code scanning e artefato semgrep-report/semgrep.sarif

2) DAST – Teste dinâmico com OWASP ZAP (Baseline) 
	•	Executa contra o ambiente de staging (ou fallback https://example.com)
	•	Gera zap-report.html e zap-report.json com evidências
	•	Gate por severidade fora do container (bloqueia se houver HIGH)

Como configurar o alvo:
	•	Preferencial: Settings ▸ Secrets/Variables ▸ STAGING_URL
	•	Alternativas: input manual ao rodar via workflow_dispatch ou fallback automático

Arquivos:
	•	./github/workflows/ci-cd-security.yml (jobs deploy_staging e dast_zap)
	•	Artefatos: zap-report/ (HTML + JSON)

3) SCA – Análise de componentes de terceiros 
	•	Dependency Review (no Pull Request): CVEs e política de licenças com comentário/resumo automático
	•	OSV-Scanner (via Docker): gera SARIF e envia para Code scanning

Arquivos:
	•	./github/workflows/sca-dependency-review.yml (opcional, rápido em PR)
	•	./github/workflows/ci-cd-security.yml (job sca_osv)
	•	Relatórios: Security ▸ Code scanning (OSV) e artefato osv-report/osv.sarif

4) Pipeline CI/CD Unificado 
	•	./github/workflows/ci-cd-security.yml integra SAST + SCA + DAST
	•	Gatilhos: push, pull_request e workflow_dispatch
	•	Políticas:
	•	Bloqueio de deploy se: SAST tiver findings error ou DAST detectar HIGH
	•	Comentário de status no PR (job notify)
	•	Artefatos: SARIF do Semgrep/OSV, HTML/JSON do ZAP

Observação: existem workflows auxiliares (ex.: dast-zap.yml e sca-osv.yml) para executar checagens de forma isolada quando necessário.

⸻

🔭 Onde ver os resultados
	•	Security ▸ Code scanning: Semgrep (SAST) e OSV (SCA)
	•	Pull Requests: comentário do Dependency Review com CVEs/versões/licenças
	•	Actions ▸ Run ▸ Artifacts: semgrep-report/, osv-report/, zap-report/

⸻

⚙️ Tecnologias Utilizadas

Tecnologia	Versão/Info
Java	17
Spring Boot	3.x
Spring Security	
JWT (jjwt)	
H2 Database	
Spring Data JPA	
Lombok
SpringDoc OpenAPI	2.1.0
SLF4J (Logging)
Semgrep (SAST)	SARIF no Code scanning
OWASP ZAP (DAST)	Baseline, HTML/JSON com evidências
Dependency Review	PR, CVEs + licenças
OSV-Scanner (SCA)	SARIF no Code scanning


⸻

🗂️ Estrutura de Pacotes

br.com.fiap.auth
├── config           → Segurança, Swagger, exceptions
├── controller       → Controllers REST com log
├── dto              → DTOs (request/response)
├── model            → Entidades JPA com validação
├── repository       → Interfaces JPA
├── security         → Filtros/JWT
├── service          → Regras de negócio com log
├── util             → Geração/verificação de JWT
└── resources
    └── application.properties


⸻

🔐 Perfis de Acesso

Enum Perfil: INVESTIDOR | ASSESSOR

⸻

🧪 Testando a API

1) Cadastrar usuário (POST /auth)

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456",
  "perfil": "ASSESSOR"
}

2) Login (POST /login)

{
  "email": "joao@email.com",
  "senha": "123456"
}

3) Usar token em endpoints protegidos

Authorization: Bearer <token>


⸻

▶️ Executando a Aplicação

./mvnw spring-boot:run

Acesse:
	•	Swagger UI: http://localhost:8080/swagger-ui.html
	•	H2 Console (se habilitado): http://localhost:8080/h2-console

⸻

🛡️ Workflows de Segurança
	•	Unificado: ./github/workflows/ci-cd-security.yml
	•	DAST isolado (opcional): ./github/workflows/dast-zap.yml
	•	SCA OSV isolado (opcional): ./github/workflows/sca-osv.yml
	•	Dependency Review (PR): ./github/workflows/sca-dependency-review.yml

Para DAST, configure STAGING_URL como Secret ou Repository variable (ou informe manualmente em workflow_dispatch).

⸻

📝 Entregáveis (Trabalho)
	•	SAST: SARIF do Semgrep em Code scanning (+ artefato semgrep-report)
	•	DAST: zap-report.html/zap-report.json com payloads/evidências
	•	SCA: comentário do Dependency Review (PR) + SARIF do OSV em Code scanning
	•	Integração: pipeline unificado com gatilhos, bloqueio de deploy e notificação no PR

⸻

Desenvolvido para fins acadêmicos e prática de segurança em pipelines CI/CD e APIs RESTful.
