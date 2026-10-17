# BRIEF — sistema-agendamento-consultas

## Entregas
Projeto full-stack "sistema-agendamento-consultas" (área Full-Stack): agendamento de consultas com Java Spring Boot 3 + React/TypeScript.

Arquivos criados:
- Backend (Java 21, Spring Boot 3.4.3, Maven):
  - `pom.xml`
  - `src/main/resources/application.properties`
  - `src/main/java/com/kelvinoliveira/sistemaagendamentoconsultas/SistemaAgendamentoConsultasApplication.java`
  - `model/Consulta.java`, `model/StatusConsulta.java`
  - `dto/ConsultaRequest.java`, `dto/ConsultaResponse.java`
  - `repository/ConsultaRepository.java`
  - `service/ConsultaService.java`
  - `controller/ConsultaController.java`
  - `exception/ConsultaNotFoundException.java`, `exception/ConsultaInvalidaException.java`, `exception/GlobalExceptionHandler.java`
- Testes JUnit 5 (27 testes, todos passando via `./mvnw test`):
  - `src/test/java/.../service/ConsultaServiceTest.java` (11)
  - `src/test/java/.../controller/ConsultaControllerTest.java` (9)
  - `src/test/java/.../repository/ConsultaRepositoryTest.java` (7)
- Frontend (React 18 + TypeScript + Tailwind CSS + Vite):
  - `frontend/package.json`, `vite.config.ts`, `tailwind.config.js`, `postcss.config.js`, `index.html`, `tsconfig.json`
  - `frontend/src/main.tsx`, `index.css`, `types.ts`, `api.ts`, `App.tsx`, `ConsultaList.tsx`, `ConsultaForm.tsx`
- Documentação:
  - `README.md` (bilíngue PT-BR + EN: título, descrição, como usar, endpoints, testes, licença MIT, autor Kelvin Oliveira)
  - `BRIEF.md` (este arquivo)

## Comando de teste usado
`./mvnw test` (executado na raiz do projeto com o Maven Wrapper gerado por `mvn wrapper:wrapper`).

## Resultado dos testes
**BUILD SUCCESS — 27 testes passando, 0 falhas, 0 erros:**
- `ConsultaServiceTest`: 11
- `ConsultaControllerTest`: 9
- `ConsultaRepositoryTest`: 7

## Como verificar
1. Backend: `./mvnw spring-boot:run` → API em `http://localhost:8080/consultas`
2. Testes: `./mvnw test` → deve reportar BUILD SUCCESS com 27 testes OK
3. Frontend: `cd frontend && npm install && npm run dev` → `http://localhost:5173` (proxy `/consultas` → `:8080`)
4. Build do frontend: `cd frontend && npm run build` → gera `dist/` sem erros de TypeScript

Critérios de aceitação atendidos: CRUD completo (agendar/listar/cancelar), testes passando, README PT/EN completo.

Autor: Kelvin Oliveira — Licença: MIT
