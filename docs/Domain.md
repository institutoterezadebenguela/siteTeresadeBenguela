# Relatório de Classes de Domínio - Instituto Tereza de Benguela (v2)

A camada do **Domínio (`br.com.instituto.teresa.domain`)** é o coração da regra de negócios em aplicações robustas feitas em Java.
Na versão **v2**, estas classes consolidaram-se estritamente como *Modelos de Banco de Dados*, pois todo o tráfego da rede é agora interceptado pelos **DTOs**. Isso removeu das entidades a carga tática de validar requisições JSON, servindo agora à tradução pura da abstração para o SQL pelo Hibernate!

Abaixo, detalhamos o papel dentro de cada núcleo.

---

## 1. Classe `Project.java`
- **Definidor Lógico:** Entidade Mãe (`@Entity`)
- **Papel na v2:** Exclusivamente persistente. Seus atributos complexos alimentam as projeções limpas do `ProjectResponseDTO` via map do Service.
- **Características Notáveis:**
  - Faz uso de **`@ElementCollection`**. Em vez de `OneToMany`, atrelamos listas e mapas na classe base, permitindo o JPA gerar tabelas associativas transparentes.

---

## 2. Classe `ProjectFeature.java`
- **Definidor Lógico:** Componente Embutido (`@Embeddable`)
- **Papel:** Molde programático que amarra a classe do "ícone de apresentação". Na v2, migra para `ProjectFeatureDTO`.

---

## 3. Classe `DiscographyTrack.java`
- **Definidor Lógico:** Entidade Puritana Independente (`@Entity`)
- **Papel:** Abstrai os nomes de reprodução e caminhos de disco (`./assets/discografia/00 - X`). Desassociada da rede pelas rotas seguras do DTO.

---

## 4. Classe `Volunteer.java`
- **Definidor Lógico:** Entidade Receptiva (`@Entity`)
- **Papel (v2 Evolução):** Antigamente, esta classe utilizava `Jakarta Bean Validation` internamente para se blindar. Com a introdução da `VolunteerRequestDTO`, a Entidade `Volunteer` voltou a seu formato limpo de repositório, delegando segurança das requisições para a borda da aplicação. Suas restrições agora garantem conformidade pura com a Injeção pelo Banco de Dados.
