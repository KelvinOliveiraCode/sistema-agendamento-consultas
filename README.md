# Sistema de Agendamento de Consultas / Appointment Scheduling System

Agendamento de consultas médicas com Java Spring Boot 3 (backend) e React/TypeScript + Tailwind (frontend).
Medical appointment scheduling with Java Spring Boot 3 (backend) and React/TypeScript + Tailwind (frontend).

Autor / Author: Kelvin Oliveira
Licença / License: MIT

---

## 🇧🇷 Português (PT-BR)

### Descrição
Sistema full-stack MVP para agendamento, listagem e cancelamento de consultas médicas. O backend expõe uma API REST em Java 21 com Spring Boot 3, Spring Data JPA e Jakarta Validation, persistindo em H2 (banco embutido). O frontend é uma aplicação React 18 com TypeScript e Tailwind CSS, construída com Vite.

### Escopo do MVP
- Agendar consulta — `POST /consultas`
- Listar consultas — `GET /consultas`
- Cancelar consulta — `DELETE /consultas/{id}`

### Como usar

**Pré-requisitos:** Java 21, Maven e Node.js.

**Backend**
```bash
./mvnw spring-boot:run
# ou: mvn spring-boot:run
```
A API sobe em `http://localhost:8080`.

**Frontend**
```bash
cd frontend
npm install
npm run dev
```
O frontend roda em `http://localhost:5173` e encaminha `/consultas` para o backend em `:8080` (proxy do Vite, somente em desenvolvimento).

### Endpoints

| Método | Caminho | Descrição |
|--------|---------|-----------|
| `POST` | `/consultas` | Agendar consulta (body: `pacienteNome`, `tipo`, `dataHora`) |
| `GET` | `/consultas` | Listar todas as consultas |
| `DELETE` | `/consultas/{id}` | Cancelar consulta pelo ID |

### Testes
```bash
./mvnw test
```
Resultado: **27 testes passando** (service 11, controller 9, repository 7), com build bem-sucedido.

### Licença
MIT

---

## 🇺🇸 English (EN)

### Description
Full-stack MVP for scheduling, listing, and cancelling medical appointments. The backend exposes a REST API in Java 21 with Spring Boot 3, Spring Data JPA, and Jakarta Validation, persisting to H2 (embedded database). The frontend is a React 18 app with TypeScript and Tailwind CSS, built with Vite.

### MVP Scope
- Schedule appointment — `POST /consultas`
- List appointments — `GET /consultas`
- Cancel appointment — `DELETE /consultas/{id}`

### How to use

**Prerequisites:** Java 21, Maven, and Node.js.

**Backend**
```bash
./mvnw spring-boot:run
# or: mvn spring-boot:run
```
The API starts at `http://localhost:8080`.

**Frontend**
```bash
cd frontend
npm install
npm run dev
```
The frontend runs at `http://localhost:5173` and proxies `/consultas` to the backend on `:8080` (Vite dev proxy only).

### Endpoints

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/consultas` | Schedule appointment (body: `pacienteNome`, `tipo`, `dataHora`) |
| `GET` | `/consultas` | List all appointments |
| `DELETE` | `/consultas/{id}` | Cancel appointment by ID |

### Tests
```bash
./mvnw test
```
Result: **27 tests passing** (service 11, controller 9, repository 7), successful build.

### License
MIT

---

### Estrutura do projeto / Project structure
```
sistema-agendamento-consultas/
├── pom.xml
├── mvnw / mvnw.cmd
├── src/main/java/...        # backend (controller, service, repository, dto, model, exception)
├── src/test/java/...         # testes JUnit 5
├── frontend/                 # frontend React + TypeScript + Tailwind (Vite)
│   ├── package.json
│   ├── src/
│   │   ├── App.tsx, api.ts, types.ts
│   │   ├── ConsultaList.tsx, ConsultaForm.tsx
│   │   └── main.tsx, index.css
│   └── dist/                 # build de produção
├── README.md
└── BRIEF.md
```
