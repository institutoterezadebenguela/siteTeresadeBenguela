# Plano de Implementação - Backend do Instituto Tereza de Benguela (v2 Consolidada)

Esta é a análise do projeto atual e a lista de tarefas propostas para transformar o site estático atual em uma aplicação web totalmente funcional com backend Java + Spring Boot e banco de dados.

## Análise do Estado Atual (Frontend)

Atualmente, o projeto é composto por arquivos estáticos (`index.html`, `projetos.html`, `voluntario.html`) que utilizam TailwindCSS. O comportamento dinâmico é baseado em JavaScript vanilla com dados mockados (hardcoded). Identificamos os seguintes domínios de dados que precisam de backend:
1. **Voluntários**: Atualmente submetidos por `mailto:` através de um formulário em `voluntario.html`.
2. **Projetos**: Atualmente hardcoded em `scripts/projects.js` e em `projetos.html`.
3. **Discografia**: Atualmente hardcoded em `scripts/discography.js`.
4.  *Observação*: `scripts/news.js` está referenciado no `index.html` porém não compõe o projeto atual. Podemos removê-lo ou mapear uma futura rota `News` se desejar.

## User Review Required

> [!IMPORTANT]
> Precisamos confirmar onde o código-fonte do backend deve ser alocado. 
> Minha recomendação para simplificar o desenvolvimento é transformar a pasta do projeto `teresa` na estrutura padrão de um projeto Spring Boot e mover os arquivos atuais do frontend (HTML, CSS, JS, assets) para dentro de `src/main/resources/static`.
> Você aprova esta reestruturação estrutural do projeto ou prefere manter o projeto Backend em uma pasta separada (como uma API) do Frontend?

## Proposed Changes

### Estrutura do Backend (Spring Boot)

#### [NEW] `pom.xml` ou `build.gradle`
Criação do projeto via Spring Initializr incluindo as dependências essenciais:
- Spring Web (para construção das chamadas REST)
- Spring Data JPA (para o mapeamento objeto-relacional)
- H2 Database (para o DB de desenvolvimento em memória/arquivo local)
- PostgreSQL Driver (já como dependência para a migração futura)
- Lombok (para redução de código boilerplate - Getters/Setters)
- Bean Validation (para validar inputs do formulário de voluntário)

#### [NEW] `application.properties` (ou `application.yml`)
Configuração para rodar inicialmente em **H2**, deixando as propriedades do PostgreSQL apenas comentadas ou em um perfil separado (ex: `application-prod.yml`), o que atende perfeitamente à sua necessidade.

### Camada de Domínio / Dados (Entities e Repositories)

#### [NEW] `domain/Volunteer`, `domain/Project`, `domain/DiscographyTrack`
- **Volunteer**: Guardará os interessados (`id`, `name`, `email`, `phone`, `age`, `motivation`).
- **Project**: Listagem de projetos (`id`, `title`, `subtitle`, `description`, `impact`, `image`, etc).
- **DiscographyTrack**: Lista de músicas do Quilombo (`id`, `title`, `artist`, `audioFileName`).

#### [NEW] `repositories/*Repository`
Interfaces que herdarão de `JpaRepository` para interagir com o H2/Postgres.

### Camada de Serviços e Controladores (API REST)

#### [NEW] `controllers/ProjectController.java`
Criará a rota `GET /api/projects` para listar os projetos para o frontend.

#### [NEW] `controllers/DiscographyController.java`
Criará a rota `GET /api/discography` para buscar as músicas que abastecerão o carrossel.

#### [NEW] `controllers/VolunteerController.java`
Criará a rota `POST /api/volunteers` mapeando a recepção da candidatura preenchida no site (salvando no DB).

### População Inicial do Banco de Dados

#### [NEW] `config/DataSeeder.java` ou `data.sql`
Script/Estratégia para criar os projetos e discografias automaticamente no banco H2 ao iniciar o servidor, a partir dos dados já existentes no código front-end antigo, garantindo que o site funcione sem ficar vazio na primeira inicialização.

### Integração do Frontend (Refatoração JavaScript)

Caso sigamos a via de "Backend serve o Frontend":
#### [MODIFY] `scripts/projects.js`
Removeremos a lista de projetos constante (hardcoded) e faremos via requisição assíncrona para nosso backend:
`fetch('/api/projects').then(res => res.json())...`

#### [MODIFY] `scripts/discography.js`
Remover a lista na constante `discographyTracks` usando os dados obtidos da chamada de requisição:
`fetch('/api/discography').then(res => res.json())...`

#### [MODIFY] `voluntario.html`
Removeremos o bloqueio visual do `mailto` no JavaScript embutido para criar uma requisição `fetch('/api/volunteers', { method: 'POST', body: ... })` usando multipart/JSON e enviando dados pro backend para salvar, mostrando assim uma mensagem de sucesso no formulário.

---

## Open Questions

1. O banco PostgreSQL já está disponível no seu ambiente local (se precisar que eu teste nele), ou vamos trabalhar **exclusivamente com o H2** por agora enquanto testamos as APIs?
2. Concorda em mover todo o frontend para a pasta `src/main/resources/static` do repositório Java para simplificar o servidor que subirá a aplicação, ou prefere os serviços em portas/apps diferentes (ex: Front na porta 5500 via live server e back na 8080)?

## Verification Plan

### Manual Verification
- Iniciar o servidor Spring Boot localmente no terminal (`./mvnw spring-boot:run` ou pela IDE).
- Acessar o `localhost:8080` no navegador web e validar se o front abre com exito.
- Conferir no console do navegador se as listas de projetos/discografias originam da requisições nas rotas `/api/*`.
- Enviar um teste pelo formulário da guia Voluntário no site e verificar o preenchimento bem sucedido checando o Console do banco H2 (`http://localhost:8080/h2-console`) se os dados foram persistidos.

---

## [v2] Evolução da Arquitetura (Enterprise Grade)

Após a estabilização do modelo acima, foi implantada uma **versão 2** da base do projeto, separando firmemente os componentes lógicos e inserindo novas tecnologias voltadas a sistemas massivos de dados:
- **Services:** Isolantes que atuam como orquestradores blindados, desparelhando Controllers de Repositórios diretos.
- **Records (DTOs):** Os Data-Transfer-Objects foram gerados como fronteira de tráfego, impedindo que requisições modifiquem entidades relativas ao banco indevidamente (Prevenção clássica de Mass-Assignment e Injeções HTTP).
- **Tratamento Global de Exceção (AOP):** Introdução de classes anotadas como `@RestControllerAdvice` no pacote `/exception` que formatam inteligentemente logs de violação (originários de Bean Validation maliciosos do frontend) num formato serializado confortável 400 Bad Request polido pro cliente HTML.
- **CORS Centralizado:** O CORS se esvaziou dos Controllers virando uma política global isolada de forma limpa pela interface de `WebMvcConfigurer` no pacote de Configurações da aplicação.
- **Mocks Reavaliados Configurados:** Configuração complexa do Mockito no artefato POM `maven-surefire-plugin` habilitando o Mockito experimental para espelhar a classe limpa rodando sobre a robusta plataforma Java 25.
