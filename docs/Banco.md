# Relatório do Banco de Dados - Instituto Tereza de Benguela

A estrutura do banco de dados deste sistema é inteiramente gerenciada pelo modelo ORM (**Hibernate / Spring Data JPA**), através do conceito *Code-First*. A aplicação mapeia as Classes Java Orientadas a Objetos automaticamente para as Tabelas Relacionais do banco de dados.

Durante as rodadas em modo **H2** e na subida final em **PostgreSQL**, o Hibernate foi instruído para auto gerar ou atualizar o esquema por meio da propriedade `spring.jpa.hibernate.ddl-auto=update`.

Abaixo, detalhamos o esquema resultante formado por 5 tabelas no banco de dados.

---

## 1. Tabela `projects`
Corresponde ao repositório central dos Projetos socioculturais abrigados pelo portal.

| Coluna | Tipo Nativo | Constraints | Descrição |
| --- | --- | --- | --- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Identificador único de cada projeto. |
| `code` | VARCHAR(255) | | Slug ou código legível usado em URLs invisíveis internas (Ex: 'projeto-1'). |
| `title` | VARCHAR(255) | | Nome principal e público do projeto. |
| `subtitle` | VARCHAR(255) | | Subtítulo ou slogan com um breve apanhado público. |
| `description` | TEXT | | Área massiva de texto contendo a história inteira sobre o projeto. |
| `impact` | TEXT | | Segunda área massiva de texto atestando os impactos globais do projeto. |
| `image` | VARCHAR(255) | | Pasta/URL virtual da foto de capa (ex: '/assets/img.png'). |

---

## 2. Tabela `project_features`
Como os Projetos possuem listas de *Características (Features)*, isto quebra uma regra normal relacionacional se salvo na mesma tabela. O JPA construiu uma sub-tabela dependente que linca N características em um único Projeto da tabela mãe.

| Coluna | Tipo Nativo | Constraints | Descrição |
| --- | --- | --- | --- |
| `project_id` | BIGINT | FOREIGN KEY (`projects.id`) | Relaciona e atrela essa feature com a ID da Tabela Projects. |
| `icon` | VARCHAR(255) | | Classe do ícone visual para compor o FrontEnd (Ex: 'fas fa-home'). |
| `text` | VARCHAR(255) | | Texto contendo o benefício ou marca do detalhe daquele projeto. |

---

## 3. Tabela `project_details`
Uma terceira tabela acessória, oriunda do envio da estrutura Key-Value de Dicíonários (Map). Esta tabela mapeia os parágrafos de cada detalhe longo de um projeto.

| Coluna | Tipo Nativo | Constraints | Descrição |
| --- | --- | --- | --- |
| `project_id` | BIGINT | FOREIGN KEY (`projects.id`) | Relaciona o detalhe ao id nativo do seu projeto parente. |
| `detail_key` | VARCHAR(255) | | Título invisível da subseção / chave do item iterado (ex: 'dancaChorado'). |
| `detail_value` | TEXT | | Texto descritivo massivo da subseção em questão. |

---

## 4. Tabela `discography_tracks`
Guarda de maneira isolada todos os arquivos que compõe o player do Instituto. Desvinculada e independente.

| Coluna | Tipo Nativo | Constraints | Descrição |
| --- | --- | --- | --- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Índice incrementador numérico da faixa isolada. |
| `code` | VARCHAR(255) | | O Id visual no player ou nome do index da ordem da track. |
| `title` | VARCHAR(255) | | Título visual exibido na linha da música. |
| `artist` | VARCHAR(255) | | Conjunto, Banda ou Quilombo autorizante da música. |
| `audio_file` | VARCHAR(255) | | Caminho do diretório que o FrontEnd deve usar de SRC para tocar a track. |

---

## 5. Tabela `volunteers`
Tabela designada para captar e centralizar as submissões de pessoas interessadas no Frontend.

| Coluna | Tipo Nativo | Constraints | Descrição |
| --- | --- | --- | --- |
| `id` | BIGINT | PRIMARY KEY, AUTO_INCREMENT | Sequência do protocolo único do candidato. |
| `name` | VARCHAR(255) | NOT NULL | Nome do usuário validado. Não permite vazios. |
| `email` | VARCHAR(255) | NOT NULL | E-mail do usuário validadado para respostas com `@`. |
| `phone` | VARCHAR(255) | | Telefone/WhatsApp opcional do candidato. |
| `age` | VARCHAR(255) | | Seleção preenchida pelo combobox da tela para estatísticas. |
| `motivation`| TEXT | NOT NULL | Feito no tipo TEXT para suportar campos gigantescos (cartas). |

> [!TIP]
> **Modelo Automático Inteligente:** Graças ao Hibernate (`ddl-auto=update`), ao aplicarmos em produção, **nenhum código SQL precisará ser executado pelo administrador no servidor**. Quando a aplicação iniciar remotamente o motor reconstruirá exatamente estas estruturas SQL perfeitamente em modo nativo no PostgreSQL!
