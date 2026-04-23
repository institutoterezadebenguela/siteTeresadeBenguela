# Relatório da Camada de Repositórios (Repositories) - Instituto Tereza de Benguela (v2)

A camada de Acesso a Dados (Data Access Layer) deste sistema é inteiramente fundamentada no ecossistema **Spring Data JPA**. Ela é composta pelas nossas interfaces dentro do pacote `br.com.instituto.teresa.repository`.

Diferente de sistemas legados em que o programador precisava escrever conexões massivas e exaustivas consultas *`SELECT * FROM`* no braço, nossa arquitetura delega a intersecção pro Spring Data.

---

## 1. ProjectRepository
*Contrato direto entre o banco de Projetos Sociais e o Backend.*

- **Tipo Estrutural:** Interface Java.
- **Herança Principal:** Extende nativamente de `JpaRepository<Project, Long>`.
- **(v2) Mudança de Injeção:** Na v2, não acoplamos mais Repositórios nos Controllers. Pelo contrário, aplicamos via injeção por construtor unicamente no `ProjectService`, onde regras de abstração atuam sem vazar acesso livre do H2/PostgreSQL pras chamadas Web.

---

## 2. DiscographyTrackRepository
*Ponte para puxarmos a música da roça e do Quilombo.*

- **Tipo Estrutural:** Interface Java.
- **Herança Principal:** Extende nativamente de `JpaRepository<DiscographyTrack, Long>`.
- **(v2) Mudança de Injeção:** Agora injetado puramente na classe `DiscographyService` blindando as ações automáticas da query estática.

---

## 3. VolunteerRepository
*O Escudo persistente focado em salvar envios e intenções do front.*

- **Tipo Estrutural:** Interface Java.
- **Herança Principal:** Extende nativamente de `JpaRepository<Volunteer, Long>`.
- **(v2) Mudança de Injeção:** Gerenciado pela `VolunteerService`. Com esta segurança adicional de arquitetura na v2, o Repositório interage apenas com Modelos internos criados pelo serviço e jamais corre perigo de ser diretamente serializado provindo da rede pública.
