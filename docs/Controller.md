# Relatório de Controladores (APIs REST) - Instituto Tereza de Benguela (v2)

A camada de controle da aplicação é estruturada como uma API RESTful utilizando o **Spring Web**. Seu propósito é expor de maneira padronizada e descentralizada dados no formato **JSON** para o Front-End consumir.

Na **v2**, os controladores sofreram grande evolução arquitetural:
- **CORS Centralizado:** A anotação `@CrossOrigin` foi removida em favor de uma configuração global (`WebConfig`).
- **Desacoplamento:** Repositórios foram removidos dos Controladores. Eles agora agem apenas como maestros, encaminhando o tráfego para a camada de `Service`.
- **Injeção por Construtor:** A anotação `@Autowired` em campos foi abandonada, promovendo segurança e imutabilidade injeção nativa pelo construtor.
- **DTOs:** As Entidades não chegam mais aos Controladores e vice-versa, utilizamos DTOs puros (`Records`) bloqueando o Mass Assignment.

---

## 1. ProjectController (v2)
*Responsável pelas requisições referentes aos Projetos Socioculturais.*

- **Rota Base:** `/api/projects`
- **Injeção Atrelada:** `ProjectService`

### `GET /api/projects`
- **Método Interno Declarado:** `getAllProjects()`
- **Objetivo:** Responder com um array de projetos usando a blindagem do DTO.
- **Retorno Esperado:** Status `200 OK`. Retorna a lista de `ProjectResponseDTO`.

---

## 2. DiscographyController (v2)
*Alimenta metadados do player musical.*

- **Rota Base:** `/api/discography`
- **Injeção Atrelada:** `DiscographyService`

### `GET /api/discography`
- **Método Interno Declarado:** `getAllTracks()`
- **Objetivo:** Puxar dados sanitizados musicais pela camada de serviço.
- **Retorno Esperado:** Status `200 OK`. Retorna uma lista de `DiscographyTrackResponseDTO`.

---

## 3. VolunteerController (v2)
*O interceptador de novos cadastros com validação de ponta.*

- **Rota Base:** `/api/volunteers`
- **Injeção Atrelada:** `VolunteerService`

### `POST /api/volunteers`
- **Método Interno Declarado:** `createVolunteer(@Valid @RequestBody VolunteerRequestDTO volunteerDTO)`
- **Objetivo de Ação:** Recebe de forma estrita o DTO. Os atributos excessivos da entidade original são inacessíveis, provendo alta barreira de segurança.
- **Formato de Entrada Escutado (`@RequestBody`):** Baseado na estrutura enxuta do `Volvo RequestDTO`.
- **Retorno Esperado:** Status HTTP `201 CREATED`. Envia de volta `{"message": "Candidatura enviada com sucesso!"}`.
- **Validações (`@Valid` em v2):** Erros disparam interativamente o **GlobalExceptionHandler**, prevenindo dumps genéricos do Spring Boot.

> [!TIP]
> **Ambiente de Testes (MockMvc) v2:** Os testes não operam mais acessando repósitorios no banco de dados. Eles invocam puramente o `Mockito` simulando as sub-rotinas interativas da camada de Service, isolando o controlador de forma pura (Unit Testing real)!
