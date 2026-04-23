# 🤎 Instituto Tereza de Benguela

![Instituto Tereza de Benguela](https://img.shields.io/badge/Status-Concluído-success)
![Java](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.0-brightgreen?logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Ready-blue?logo=postgresql)

O **Instituto Tereza de Benguela** é um sistema completo, construído para gerenciar o portal web cultural da instituição. Baseado em **Java e Spring Boot**, o projeto contempla um robusto back-end capaz de suprir listagem de projetos, discografia quilombola, além de possibilitar captação de novos voluntários, tudo isso aliado a um front-end fluido desenhado com **Vanilla JavaScript e Tailwind CSS**.

## ✨ Tecnologias Utilizadas

### No Back-end:
- **Java 21**
- **Spring Boot 3.4.0** (Starters: Web, Data JPA, Validation, Test)
- **H2 Database** (Banco padrão de Memória/Desenvolvimento)
- **PostgreSQL** (Driver incluso/properties configuradas para Deploy / Produção)
- **Maven** (Para empacotamento e dependências automáticas)
- **JUnit 5 / MockMvc** (Para bateria de Testes Unitários)

### No Front-end (Estáticos servidos via `src/main/resources/static`):
- **HTML 5 / CSS 3 / JavaScript Assíncrono**
- **Tailwind CSS** (Aplicado via script CDN)
- **FontAwesome** (Para iconografia)
- Requisições REST puras conectadas através da `Fetch API`.

---

## 🚀 Como a Arquitetura está desenhada

A aplicação funciona no modelo de **Servidor Incorporado Mono-repositório**, onde o Spring Boot não apenas expõe a Interface de Programação REST em JSON, como também atua como servidor estático do front-end na raiz do serviço.

O sistema também incorpora um componente padrão **DataSeeder (`CommandLineRunner`)**. Ao inicializar a aplicação, esse motor injeta dinamicamente no banco H2 todos os pacotes das páginas estáticas do front-end puxando dados iniciais. 

*(Por razões de compatibilidade na estabilidade da árvore do compilador do Java 21, **não** utilizamos bibliotecas externas invisíveis como Lombok – as Entidades e classes foram construídas em Java explícito usando pura Orientação a Objetos e boas práticas visíveis!)*

---

## 🛠️ Endpoints Disponíveis pela API

A interface principal REST escuta nas seguintes rotas:

- `GET /api/projects` - Retorna array JSON com informações profundas de cada Projeto Social do Instituto (incluindo mapas, features, descrições extensas e imagens).
- `GET /api/discography` - Retorna listagem com todas as trilhas musicais armazenadas ("Discografia"), essenciais para montar o tocador de áudio interativo.
- `POST /api/volunteers` - Endpoint base para recrutamento de Voluntários. Recebe do front-end JSON com nome, email, telefone e motivações. Este endpoint é validado estritamente por parâmetros do ecossistema `@Valid` do Spring.

---

## ⚙️ Como Executar e Clonar Localmente

### Pré-requisitos
- Ter o **JDK 21** ou superior em sua máquina.
- Maven (Opcional, pois a aplicação já dispõe de wrapper `./mvnw` embutido).

### Passos:

**1. Clone e Acesse o Repositório:**
```bash
git clone https://seu-repositorio-git/instituto-tereza-benguela.git
cd instituto-tereza-benguela
```

**2. Inicie o Servidor:**
Construa o projeto e suba a base local no H2 usando o utilitário nativo de wrapper do maven no Linux/Mac:
```bash
./mvnw spring-boot:run
```

**3. Teste a Plataforma Visualmente:**
- Abra seu navegador na url local: [http://localhost:8080](http://localhost:8080)
- Endpoints JSON diretamente no Browser: [http://localhost:8080/api/projects](http://localhost:8080/api/projects)

---

## 🧪 Rodando os Testes Unitários

Classes completas de validação `ControllerTest` via interceptador de requisições `MockMvc` garantem a imunidade a falhas das rotas construídas.

Rode as coberturas do projeto inteiro de forma limpa pelo comando:
```bash
./mvnw clean test
```
*Toda validação de input inválido na API de voluntariado e persistência de requisição tem garantia validada por meio mecânico antes das compilações finais.*

---

## 🛢️ Migrando para Produção (PostgreSQL)

O projeto está otimizado para rodar de primeira na máquina sem instalar SGBDs porque o Spring invoca o H2 Database por padrão. Contudo, suas credenciais de subida para **PostgreSQL em produção** já estão pré-configuradas e adormecidas!

Modifique o comportamento no arquivo em `/src/main/resources/application.properties`:
1. Comente o bloco inteiro com título `Configurações H2 (Desenvolvimento)`.
2. Descomente o bloco `Configurações PostgreSQL (Produção)`.
3. Ajuste `username` e `password` para refletir sua infraestrutura. O driver nativo de Postgres será chamado e atrelará todas as tabelas via Hibernate imediatamente.

---
*Feito com ❤️ visando preservação cultural e identidade afro-brasileira.*
