# Relatório de Data Transfer Objects (DTOs) - Instituto Tereza de Benguela (v2)

Os **DTOs** (Objetos de Tranferência de Dados) são os novos guardiões do sistema na versão **v2**. O uso desses pacotes resolveu nativamente vulnerabilidades atreladas à sobre-exposição de atributos de banco em protocolos JSON (Ex: Mass Assignment).

Implementados usando o moderno paradigma do Java chamado `Records`, eles provêm imutabilidade absoluta e construtores orgânicos nativos com zero Boilerplate sem uso do Lombok.

---

## 1. VolunteerRequestDTO
- **Finalidade:** Assinatura exata do fluxo Web enviado pelo frontend.
- **Isolamento e Segurança:** O DTO assumiu pesadas validações de entrada por anotações como `@NotBlank` e `@Email` preservando as checagens críticas de cadastro contra submissão robótica nula ou ataques de desestruturação relacional.

## 2. ProjectResponseDTO & ProjectFeatureDTO
- **Finalidade:** Encapsula respostas das requisições REST da listagem de Projeto e Features. Retém apenas campos que servem visualmente à apresentação.

## 3. DiscographyTrackResponseDTO
- **Finalidade:** Projeta listagens de Metadados e Arquivos Estáticos Musicais, desassociando de forma explícita das tabelas persistentes com controle de versionamento limpo das vias de serialização (Jackson API).
