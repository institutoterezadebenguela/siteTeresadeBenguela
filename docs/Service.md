# Relatório da Camada de Serviço (Services) - Instituto Tereza de Benguela (v2)

Implementada na **v2**, a camada de **Service** surge para blindar os Controladores (APIs Públicas) das manipulações físicas de banco de dados (`Repositórios`). 

A regra da aplicação consiste em forçar que todos os controladores confiem apenas nos Services para regras de negócio e manipulações lógicas.

---

## 1. ProjectService
Isola a busca dos projetos.
- **Característica:** Ao utilizar a injeção do `ProjectRepository`, ele não devolve a classe original (o que acoplaria banco ao Front Web), ele converte a lista para listagens puras de `ProjectResponseDTO`.

## 2. DiscographyService
- **Característica:** Responsável por encapsular `DiscographyTrackRepository` e devolver vetores enxutos na record do tipo `DiscographyTrackResponseDTO`.

## 3. VolunteerService
Centraliza o processo de salvamento de voluntários.
- **Característica:** O método `processVolunteer` consome instâncias validadas unicamente do `VolunteerRequestDTO` oriundos do `Controller`. Internamente instancia o objeto Entidade seguro `Volunteer`, transfere as propriedades validadas a ele e orquestra a submissão transacional para o banco.

> [!TIP]
> **Injeção de Dependências Pura (!):** As classes injetam dependências por **Construtor** privativo, removendo o bad pattern visualizado anteriormente através de Field Injection `@Autowired`. A injeção em construtor garante o encapsulamento em runtime imutável, o que beneficiou drasticamente o mocking no ambiente de testes da aplicação.
